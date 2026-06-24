<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Xác minh & Đặt lại mật khẩu</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f6f9; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }
        .container { background: #fff; padding: 30px; border-radius: 8px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); width: 100%; max-width: 400px; }
        h2 { text-align: center; color: #333; margin-bottom: 10px; }
        p.info { text-align: center; color: #28a745; font-size: 13px; margin-bottom: 20px; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; color: #666; font-size: 14px; }
        input[type="text"], input[type="password"] { width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
        .btn { width: 100%; padding: 10px; background-color: #007bff; border: none; color: white; font-size: 16px; border-radius: 4px; cursor: pointer; }
        .btn:hover { background-color: #0069d9; }
        .error { color: red; font-size: 13px; margin-bottom: 15px; text-align: center; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Xác Minh OTP</h2>
        <p class="info">Mã OTP đã được gửi tới Email của bạn. Vui lòng kiểm tra hộp thư!</p>
        
        <% if (request.getAttribute("error") != null) { %>
            <div class="error"><%= request.getAttribute("error") %></div>
        <% } %>

        <form action="${pageContext.request.contextPath}/user/verify-and-reset" method="POST">
            <div class="form-group">
                <label>Nhập mã xác thực OTP (6 chữ số):</label>
                <input type="text" name="otp" placeholder="Ví dụ: 123456" maxlength="6" required style="letter-spacing: 2px; text-align: center; font-size: 18px; font-weight: bold;">
            </div>
            <hr style="border: 0; border-top: 1px solid #eee; margin: 20px 0;">
            <div class="form-group">
                <label>Mật khẩu mới:</label>
                <input type="password" name="newPassword" placeholder="Tối thiểu 6 ký tự" required>
            </div>
            <div class="form-group">
                <label>Xác nhận lại mật khẩu mới:</label>
                <input type="password" name="confirmPassword" placeholder="Nhập lại mật khẩu mới" required>
            </div>
            <button type="submit" class="btn">Đổi mật khẩu & Đăng nhập</button>
        </form>
    </div>
</body>
</html>