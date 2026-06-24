package Controllers;

import DAO.UserDAO;
import Models.User;
import Models.Customer;
import Utility.InputValidator;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "UserServlet", urlPatterns = {"/user/*"})
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        if (path == null) {
            path = "/list";
        }

        UserDAO userDao = new UserDAO();
        HttpSession session = req.getSession(false);
        User currentUser = (session != null) ? (User) session.getAttribute("currentUser") : null;

        switch (path) {
            case "/login":
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
                break;

            case "/register":
                req.getRequestDispatcher("/register.jsp").forward(req, resp);
                break;

            case "/profile":
                if (currentUser == null) {
                    req.setAttribute("error", "Vui lòng đăng nhập hệ thống!");
                    req.getRequestDispatcher("/login.jsp").forward(req, resp);
                    return;
                }
                req.getRequestDispatcher("/profile.jsp").forward(req, resp);
                break;

            case "/profile-edit":
                if (currentUser == null) {
                    req.setAttribute("error", "Vui lòng đăng nhập hệ thống!");
                    req.getRequestDispatcher("/login.jsp").forward(req, resp);
                    return;
                }
                // Mở trang chứa Form nhập liệu 
                req.getRequestDispatcher("/profile-edit.jsp").forward(req, resp);
                break;
            case "/list":
                // Chỉ ADMIN hoặc MANAGER mới được xem danh sách nhân viên
                if (currentUser == null || (!currentUser.getRole().equalsIgnoreCase("ADMIN") && !currentUser.getRole().equalsIgnoreCase("MANAGER"))) {
                    req.setAttribute("error", "Vui lòng đăng nhập bằng tài khoản quản lý!");
                    req.getRequestDispatcher("/login.jsp").forward(req, resp);
                    return;
                }
                List<User> listU = userDao.getUsersAdvanced(null, null, null);
                req.setAttribute("list", listU);
                req.getRequestDispatcher("/ListUser.jsp").forward(req, resp);
                break;

            case "/listCustomer":
                // Chỉ ADMIN hoặc MANAGER mới được xem danh sách khách hàng
                if (currentUser == null || (!currentUser.getRole().equalsIgnoreCase("ADMIN") && !currentUser.getRole().equalsIgnoreCase("MANAGER"))) {
                    resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền xem trang này!");
                    return;
                }
                List<Customer> listC = userDao.getCustomersAdvanced(null, null);
                req.setAttribute("customerList", listC);
                req.getRequestDispatcher("/ListCustomer.jsp").forward(req, resp);
                break;

            case "/toggle":
                if (currentUser == null || (!currentUser.getRole().equalsIgnoreCase("ADMIN") && !currentUser.getRole().equalsIgnoreCase("MANAGER"))) {
                    resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
                try {
                    int id = Integer.parseInt(req.getParameter("id"));
                    int status = Integer.parseInt(req.getParameter("status"));
                    userDao.toggleUserStatus(id, status, "Users");
                } catch (NumberFormatException e) {
                    System.out.println("Lỗi tham số toggle user: " + e.getMessage());
                }
                resp.sendRedirect(req.getContextPath() + "/user/list");
                break;

            case "/toggleCustomer":
                if (currentUser == null || (!currentUser.getRole().equalsIgnoreCase("ADMIN") && !currentUser.getRole().equalsIgnoreCase("MANAGER"))) {
                    resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
                try {
                    int id = Integer.parseInt(req.getParameter("id"));
                    int status = Integer.parseInt(req.getParameter("status"));
                    userDao.toggleUserStatus(id, status, "Customers");
                } catch (NumberFormatException e) {
                    System.out.println("Lỗi tham số toggle customer: " + e.getMessage());
                }
                resp.sendRedirect(req.getContextPath() + "/user/listCustomer");
                break;

            case "/login-google":
                String code = req.getParameter("code");
                if (code == null || code.isEmpty()) {
                    resp.sendRedirect(req.getContextPath() + "/login.jsp");
                    return;
                }

                try {
                    String accessToken = Utility.GoogleUtils.getToken(code);
                    Customer googleUser = Utility.GoogleUtils.getUserInfo(accessToken);

                    UserDAO db = new UserDAO();
                    Customer existingCustomer = db.checkEmailCustomerExist(googleUser.getEmail());

                    HttpSession googleSession = req.getSession();
                    User sessionUser = new User();

                    if (existingCustomer == null) {
                        String defaultUsername = googleUser.getEmail().split("@")[0];
                        googleUser.setUsername(defaultUsername);
                        googleUser.setPhone("0000000000");
                        googleUser.setPasswordHash("LOGIN_VIA_GOOGLE_NO_PASS");

                        db.registerCustomer(googleUser);
                        existingCustomer = db.checkEmailCustomerExist(googleUser.getEmail());
                    }

                    if (existingCustomer != null) {
                        if (existingCustomer.getActive() == 0) {
                            req.setAttribute("error", "Tài khoản liên kết với Gmail này đã bị khóa!");
                            req.getRequestDispatcher("/login.jsp").forward(req, resp);
                            return;
                        }

                        sessionUser.setId(existingCustomer.getId());
                        sessionUser.setUsername(existingCustomer.getUsername() != null ? existingCustomer.getUsername() : googleUser.getEmail());
                        sessionUser.setFullName(existingCustomer.getFullName());
                        sessionUser.setRole("CUSTOMER");

                        googleSession.setAttribute("currentUser", sessionUser);
                        googleSession.setAttribute("userRole", "CUSTOMER");

                        resp.sendRedirect(req.getContextPath() + "/profile.jsp");
                        return;
                    } else {
                        throw new Exception("Không thể khởi tạo hoặc tìm thấy tài khoản sau khi quét hệ thống.");
                    }

                } catch (Exception e) {
                    System.out.println("--- LỖI ĐĂNG NHẬP GOOGLE TẠI SERVLET ---");
                    e.printStackTrace();
                    resp.sendRedirect(req.getContextPath() + "/login.jsp");
                }
                break;

            case "/logout":
                if (session != null) {
                    session.invalidate();
                }
                resp.sendRedirect(req.getContextPath() + "/user/login");
                break;

            case "/forgot-password":
                // GET hiển thị giao diện nhập email
                req.getRequestDispatcher("/forgot-password.jsp").forward(req, resp);
                break;

            case "/verify-and-reset":
                // GET hiển thị giao diện nhập mã OTP và mật khẩu mới
                req.getRequestDispatcher("/verify-and-reset.jsp").forward(req, resp);
                break;

            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        if (path == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        UserDAO userDao = new UserDAO();
        HttpSession session = req.getSession(false);
        User currentUser = (session != null) ? (User) session.getAttribute("currentUser") : null;

        switch (path) {
            case "/login":
                String usernameLog = req.getParameter("username");
                String passLog = req.getParameter("password");

                if (!InputValidator.isValidString(usernameLog) || !InputValidator.isValidString(passLog)) {
                    req.setAttribute("error", "Vui lòng nhập đầy đủ Username và Mật khẩu!");
                    req.getRequestDispatcher("/login.jsp").forward(req, resp);
                    return;
                }

                HttpSession newSession = req.getSession();
                String encryptedLoginPass = InputValidator.hashPassword(passLog);

                User user = userDao.checkLogin(usernameLog, encryptedLoginPass);
                if (user != null) {
                    newSession.setAttribute("currentUser", user);
                    newSession.setAttribute("userRole", user.getRole());
                    resp.sendRedirect(req.getContextPath() + "/user/profile");
                    return;
                }

                Customer customer = userDao.checkLoginCustomer(usernameLog, encryptedLoginPass);
                if (customer != null) {
                    User guest = new User();
                    guest.setId(customer.getId());
                    guest.setUsername(customer.getUsername());
                    guest.setFullName(customer.getFullName());
                    guest.setRole("CUSTOMER");

                    newSession.setAttribute("currentUser", guest);
                    newSession.setAttribute("userRole", "CUSTOMER");
                    resp.sendRedirect(req.getContextPath() + "/user/profile");
                    return;
                }

                req.setAttribute("error", "Tài khoản hoặc mật khẩu không chính xác hoặc đã bị khóa!");
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
                break;

            case "/register":
                String nameReg = req.getParameter("fullName");
                String phoneReg = req.getParameter("phone");
                String emailReg = req.getParameter("email");
                String usernameReg = req.getParameter("username");
                String passReg = req.getParameter("password");

                if (!InputValidator.isValidString(usernameReg) || !InputValidator.isValidString(passReg) || !InputValidator.isValidString(emailReg)) {
                    req.setAttribute("error", "Tài khoản, Email và Mật khẩu không được để trống!");
                    req.getRequestDispatcher("/register.jsp").forward(req, resp);
                    return;
                }

                String cleanName = InputValidator.formatNominalName(nameReg);
                String encryptedRegisterPass = InputValidator.hashPassword(passReg);

                Customer newCustomer = new Customer(cleanName, phoneReg, emailReg, usernameReg, encryptedRegisterPass, 1);

                if (userDao.registerCustomer(newCustomer)) {
                    resp.sendRedirect(req.getContextPath() + "/user/login");
                } else {
                    req.setAttribute("error", "Đăng ký thất bại! Username hoặc Email có thể đã tồn tại.");
                    req.getRequestDispatcher("/register.jsp").forward(req, resp);
                }
                break;

            case "/list":
                if (currentUser == null || (!currentUser.getRole().equalsIgnoreCase("ADMIN") && !currentUser.getRole().equalsIgnoreCase("MANAGER"))) {
                    resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
                String txtSearchUser = req.getParameter("txtSearch");
                String statusUser = req.getParameter("selStatus");
                String roleUser = req.getParameter("selRole");

                List<User> filteredUsers = userDao.getUsersAdvanced(txtSearchUser, statusUser, roleUser);
                req.setAttribute("list", filteredUsers);
                req.setAttribute("oldSearch", txtSearchUser);
                req.setAttribute("oldStatus", statusUser);
                req.setAttribute("oldRole", roleUser);

                req.getRequestDispatcher("/ListUser.jsp").forward(req, resp);
                break;

            case "/listCustomer":
                if (currentUser == null || (!currentUser.getRole().equalsIgnoreCase("ADMIN") && !currentUser.getRole().equalsIgnoreCase("MANAGER"))) {
                    resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
                String txtSearchCus = req.getParameter("txtSearch");
                String statusCus = req.getParameter("selStatus");

                List<Customer> filteredCustomers = userDao.getCustomersAdvanced(txtSearchCus, statusCus);
                req.setAttribute("customerList", filteredCustomers);
                req.setAttribute("oldSearch", txtSearchCus);
                req.setAttribute("oldStatus", statusCus);

                req.getRequestDispatcher("/ListCustomer.jsp").forward(req, resp);
                break;

            case "/forgot-password":
                String username = req.getParameter("username");
                String email = req.getParameter("email");

                UserDAO db = new UserDAO();
                Customer forgotCustomer = db.checkUsernameAndEmailExist(username, email);

                if (forgotCustomer != null) {
                    if (forgotCustomer.getActive() == 0) {
                        req.setAttribute("error", "Tài khoản này đang bị khóa, không thể đổi mật khẩu!");
                        req.getRequestDispatcher("/forgot-password.jsp").forward(req, resp);
                        return;
                    }
                    try {
                        String otp = Utility.EmailUtils.generateOTP();
                        Utility.EmailUtils.sendOTP(email, otp);

                        HttpSession resetSession = req.getSession();
                        resetSession.setAttribute("resetOtp", otp);
                        resetSession.setAttribute("resetEmail", email);

                        resp.sendRedirect(req.getContextPath() + "/user/verify-and-reset");
                    } catch (Exception e) {
                        e.printStackTrace();
                        req.setAttribute("error", "Gửi mail thất bại, vui lòng kiểm tra lại cấu hình SMTP Google!");
                        req.getRequestDispatcher("/forgot-password.jsp").forward(req, resp);
                    }
                } else {
                    req.setAttribute("error", "Thông tin Tài khoản hoặc Email không chính xác!");
                    req.getRequestDispatcher("/forgot-password.jsp").forward(req, resp);
                }
                break;

           case "/profile":
                try {
                    // 1. Lấy dữ liệu từ Form gửi lên
                    int id = Integer.parseInt(req.getParameter("id"));
                    String fullName = req.getParameter("fullName");
                    String phone = req.getParameter("phone");
                    email = req.getParameter("email"); 

                    // 2. Lấy role hiện tại từ session để biết cần update vào bảng nào
                    String userRole = "CUSTOMER"; // Mặc định phòng hờ
                    if (session != null && session.getAttribute("userRole") != null) {
                        userRole = (String) session.getAttribute("userRole");
                    }

                    // 3. Gọi DAO thực hiện cập nhật database 
                    db = new UserDAO();
                    boolean isUpdated = db.updateProfile(id, fullName, phone, email, userRole);

                    if (isUpdated) {
                        // 4. Cập nhật lại thông tin mới trực tiếp vào Session "currentUser" hiện tại để hiển thị ngay
                        if (session != null && session.getAttribute("currentUser") != null) {
                            User activeUser = (User) session.getAttribute("currentUser");
                            activeUser.setFullName(fullName);
                            activeUser.setPhone(phone);
                            activeUser.setEmail(email);
                            
                            session.setAttribute("currentUser", activeUser);
                        }
                        
                        // Đặt thông báo thành công vào Session để không bị mất khi chuyển hướng
                        req.getSession().setAttribute("success", "Cập nhật hồ sơ cá nhân thành công!");
                        
                        //  Dùng sendRedirect để ép trình duyệt tải lại trang profile (Tránh lặp dữ liệu)
                        resp.sendRedirect(req.getContextPath() + "/user/profile");
                        return; // Ngắt luồng tại đây để không chạy xuống lệnh forward cuối case
                    } else {
                        req.setAttribute("error", "Cập nhật thất bại, vui lòng kiểm tra lại dữ liệu!");
                        req.getRequestDispatcher("/profile-edit.jsp").forward(req, resp);
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    req.setAttribute("error", "Đã xảy ra lỗi hệ thống: " + e.getMessage());
                    req.getRequestDispatcher("/profile-edit.jsp").forward(req, resp);
                    return;
                }
                // Bỏ hoặc không cho luồng đi tiếp xuống lệnh forward cũ để tránh lỗi IllegalStateException

            case "/verify-and-reset":
                String inputOtp = req.getParameter("otp");
                String newPassword = req.getParameter("newPassword");
                String confirmPassword = req.getParameter("confirmPassword");

                if (session == null) {
                    req.setAttribute("error", "Phiên làm việc đã hết hạn, vui lòng thao tác lại!");
                    req.getRequestDispatcher("/forgot-password.jsp").forward(req, resp);
                    return;
                }

                String sessionOtp = (String) session.getAttribute("resetOtp");
                String sessionEmail = (String) session.getAttribute("resetEmail");

                if (inputOtp == null || !inputOtp.equals(sessionOtp)) {
                    req.setAttribute("error", "Mã OTP không chính xác hoặc đã hết hạn!");
                    req.getRequestDispatcher("/verify-and-reset.jsp").forward(req, resp);
                    return;
                }

                if (newPassword == null || !newPassword.equals(confirmPassword)) {
                    req.setAttribute("error", "Mật khẩu nhập lại không trùng khớp!");
                    req.getRequestDispatcher("/verify-and-reset.jsp").forward(req, resp);
                    return;
                }

                UserDAO resetDb = new UserDAO();
                String encryptedNewPass = InputValidator.hashPassword(newPassword);
                boolean isSuccess = resetDb.updatePasswordByEmail(sessionEmail, encryptedNewPass);

                if (isSuccess) {
                    session.removeAttribute("resetOtp");
                    session.removeAttribute("resetEmail");

                    req.setAttribute("success", "Đổi mật khẩu thành công! Mời bạn đăng nhập lại.");
                    req.getRequestDispatcher("/login.jsp").forward(req, resp);
                } else {
                    req.setAttribute("error", "Cập nhật mật khẩu thất bại. Hệ thống có lỗi xảy ra!");
                    req.getRequestDispatcher("/verify-and-reset.jsp").forward(req, resp);
                }
                break;

            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }
}
