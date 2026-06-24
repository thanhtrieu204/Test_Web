package DAO;

import DAL.DBContext;
import Models.Customer;
import Models.User;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends DBContext {

    public UserDAO() {
        super();
    }

    // 1. Lấy danh sách toàn bộ Users hệ thống (Đã bổ sung đọc Phone và Email)
    public List<User> getUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM Users";

        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("Id"));
                u.setUsername(rs.getString("Username"));
                u.setPasswordHash(rs.getString("PasswordHash"));
                u.setFullName(rs.getNString("FullName"));
                u.setRole(rs.getString("Role"));
                u.setActive(rs.getInt("Active"));
                
                // 🛠️ Đọc thêm Phone và Email từ bảng Users lên
                u.setPhone(rs.getString("Phone"));
                u.setEmail(rs.getString("Email"));
                
                u.setCreatedAt(rs.getTimestamp("CreatedAt"));
                u.setUpdatedAt(rs.getTimestamp("UpdatedAt"));

                list.add(u);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi getUsers: " + e.getMessage());
        }
        return list;
    }

    // 2. Hàm chèn tài khoản hệ thống mới (Đã bổ sung thêm Phone và Email khi tạo mới Staff/Admin)
    public boolean insertUser(User u) {
        String sql = "INSERT INTO Users (Username, PasswordHash, FullName, Role, Active, Phone, Email, CreatedAt, UpdatedAt) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, GETDATE(), GETDATE())";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPasswordHash()); 
            ps.setNString(3, u.getFullName());
            ps.setString(4, u.getRole());
            ps.setInt(5, u.getActive());
            ps.setString(6, u.getPhone());
            ps.setString(7, u.getEmail());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi insertUser: " + e.getMessage());
        }
        return false;
    }

    // 3. Hàm Đăng Nhập cho nhân sự 
    public User checkLogin(String username, String password) {
        String sql = "SELECT * FROM Users WHERE Username = ? AND PasswordHash = ? AND Active = 1";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User u = new User();
                    u.setId(rs.getInt("Id"));
                    u.setUsername(rs.getString("Username"));
                    u.setPasswordHash(rs.getString("PasswordHash"));
                    u.setFullName(rs.getNString("FullName"));
                    u.setRole(rs.getString("Role")); 
                    u.setActive(rs.getInt("Active"));
                    
                    // 🛠️ Lấy dữ liệu Phone và Email nạp vào object User khi đăng nhập thành công
                    u.setPhone(rs.getString("Phone"));
                    u.setEmail(rs.getString("Email"));
                    
                    return u;
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi checkLogin: " + e.getMessage());
        }
        return null;
    }

    // 4. Hàm đăng ký dành riêng cho khách hàng mua sách 
    public boolean registerCustomer(Customer c) {
        String sql = "INSERT INTO Customers (FullName, Phone, Email, Username, PasswordHash, Active, CreatedAt, UpdatedAt) "
                + "VALUES (?, ?, ?, ?, ?, ?, GETDATE(), GETDATE())";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setNString(1, c.getFullName());
            ps.setString(2, c.getPhone());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getUsername());
            ps.setString(5, c.getPasswordHash()); 
            ps.setInt(6, c.getActive()); 

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi registerCustomer: " + e.getMessage());
        }
        return false;
    }

    // 5. Hàm đăng nhập dành riêng cho Khách hàng 
    public Customer checkLoginCustomer(String username, String password) {
        String sql = "SELECT * FROM Customers WHERE Username = ? AND PasswordHash = ? AND Active = 1";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Customer c = new Customer();
                    c.setId(rs.getInt("Id"));
                    c.setFullName(rs.getNString("FullName"));
                    c.setPhone(rs.getString("Phone"));
                    c.setEmail(rs.getString("Email"));
                    c.setUsername(rs.getString("Username"));
                    c.setPasswordHash(rs.getString("PasswordHash"));
                    c.setActive(rs.getInt("Active"));
                    return c;
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi checkLoginCustomer: " + e.getMessage());
        }
        return null;
    }

    // 6. Hàm thay đổi trạng thái hoạt động 
    public boolean toggleUserStatus(int id, int newStatus, String tableName) {
        if (!tableName.equals("Users") && !tableName.equals("Customers")) {
            return false;
        }

        String sql = "UPDATE " + tableName + " SET Active = ?, UpdatedAt = GETDATE() WHERE Id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, newStatus); 
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi toggleUserStatus: " + e.getMessage());
        }
        return false;
    }

    // 7. Hàm lấy danh sách tất cả Khách hàng
    public List<Customer> getCustomers() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM Customers";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Customer c = new Customer();
                c.setId(rs.getInt("Id"));
                c.setFullName(rs.getNString("FullName"));
                c.setPhone(rs.getString("Phone"));
                c.setEmail(rs.getString("Email"));
                c.setUsername(rs.getString("Username"));
                c.setActive(rs.getInt("Active"));
                list.add(c); 
            }
        } catch (SQLException e) {
            System.out.println("Lỗi getCustomers: " + e.getMessage());
        }
        return list;
    }

    // 8. Hàm bộ lọc nâng cao dành cho Users hệ thống
    public List<User> getUsersAdvanced(String searchName, String status, String role) {
        List<User> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Users WHERE 1=1");

        if (searchName != null && !searchName.trim().isEmpty()) {
            sql.append(" AND FullName LIKE ?");
        }
        if (status != null && !status.isEmpty()) {
            sql.append(" AND Active = ?");
        }
        if (role != null && !role.isEmpty()) {
            sql.append(" AND Role = ?");
        }

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            if (searchName != null && !searchName.trim().isEmpty()) {
                ps.setNString(paramIndex++, "%" + searchName.trim() + "%");
            }
            if (status != null && !status.isEmpty()) {
                ps.setInt(paramIndex++, Integer.parseInt(status));
            }
            if (role != null && !role.isEmpty()) {
                ps.setString(paramIndex++, role);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    User u = new User();
                    u.setId(rs.getInt("Id"));
                    u.setUsername(rs.getString("Username"));
                    u.setFullName(rs.getNString("FullName"));
                    u.setRole(rs.getString("Role"));
                    u.setActive(rs.getInt("Active"));
                    
                    // Nạp thêm phone/email cho bộ lọc nâng cao nếu cần hiển thị ngoài view quản trị
                    u.setPhone(rs.getString("Phone"));
                    u.setEmail(rs.getString("Email"));
                    
                    list.add(u);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi getUsersAdvanced: " + e.getMessage());
        }
        return list;
    }

    // 9. Hàm bộ lọc nâng cao dành cho Khách hàng
    public List<Customer> getCustomersAdvanced(String searchName, String status) {
        List<Customer> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Customers WHERE 1=1");

        if (searchName != null && !searchName.trim().isEmpty()) {
            sql.append(" AND FullName LIKE ?");
        }
        if (status != null && !status.isEmpty()) {
            sql.append(" AND Active = ?");
        }

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            if (searchName != null && !searchName.trim().isEmpty()) {
                ps.setNString(paramIndex++, "%" + searchName.trim() + "%");
            }
            if (status != null && !status.isEmpty()) {
                ps.setInt(paramIndex++, Integer.parseInt(status));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Customer c = new Customer();
                    c.setId(rs.getInt("Id"));
                    c.setFullName(rs.getNString("FullName"));
                    c.setPhone(rs.getString("Phone"));
                    c.setEmail(rs.getString("Email"));
                    c.setUsername(rs.getString("Username"));
                    c.setActive(rs.getInt("Active"));
                    list.add(c);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi getCustomersAdvanced: " + e.getMessage());
        }
        return list;
    }

    // 10. Check email customer tồn tại
    public Customer checkEmailCustomerExist(String email) {
        String sql = "SELECT * FROM Customers WHERE email = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, email);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    Customer c = new Customer();
                    c.setId(rs.getInt("Id"));
                    c.setFullName(rs.getString("FullName"));
                    c.setEmail(rs.getString("Email"));
                    c.setUsername(rs.getString("Username")); 
                    c.setPhone(rs.getString("Phone"));
                    c.setActive(rs.getInt("Active"));
                    return c;
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi checkEmailCustomerExist: " + e.getMessage());
        }
        return null;
    }

    // 11. Kiểm tra tài khoản khớp cả Username và Email không
    public Customer checkUsernameAndEmailExist(String username, String email) {
        String sql = "SELECT * FROM Customers WHERE Username = ? AND Email = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, username);
            stm.setString(2, email);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    Customer c = new Customer();
                    c.setId(rs.getInt("Id"));
                    c.setFullName(rs.getString("FullName"));
                    c.setEmail(rs.getString("Email"));
                    c.setUsername(rs.getString("Username"));
                    c.setActive(rs.getInt("Active"));
                    return c;
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi checkUsernameAndEmailExist: " + e.getMessage());
        }
        return null;
    }

    // 12. Cập nhật mật khẩu bằng Email
    public boolean updatePasswordByEmail(String email, String newPassword) {
        String sql = "UPDATE Customers SET PasswordHash = ? WHERE Email = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, newPassword); 
            stm.setString(2, email);
            return stm.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi updatePasswordByEmail: " + e.getMessage());
        }
        return false;
    }

    // 13. ?️ HÀM CẬP NHẬT THÔNG TIN HỒ SƠ ĐA NĂNG 
    public boolean updateProfile(int id, String fullName, String phone, String email, String role) {
        // Mặc định sửa ở bảng Customers, nếu role không phải CUSTOMER thì đổi sang bảng Users
        String tableName = "Customers";
        if (role != null && !role.equalsIgnoreCase("CUSTOMER")) {
            tableName = "Users";
        }

        String sql = "UPDATE " + tableName + " SET FullName = ?, Phone = ?, Email = ?, UpdatedAt = GETDATE() WHERE Id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) { 
            ps.setNString(1, fullName); 
            ps.setString(2, phone);
            ps.setString(3, email);
            ps.setInt(4, id);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi updateProfile trên bảng " + tableName + ": " + e.getMessage());
        }
        return false;
    }
}