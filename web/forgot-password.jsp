<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quên mật khẩu</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f6f9; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }
        .container { background: #fff; padding: 30px; border-radius: 8px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); width: 100%; max-width: 400px; }
        h2 { text-align: center; color: #333; margin-bottom: 20px; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; color: #666; font-size: 14px; }
        input[type="text"], input[type="email"] { width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
        .btn { width: 100%; padding: 10px; background-color: #28a745; border: none; color: white; font-size: 16px; border-radius: 4px; cursor: pointer; }
        .btn:hover { background-color: #218838; }
        .error { color: red; font-size: 13px; margin-bottom: 15px; text-align: center; }
        .back-link { display: block; text-align: center; margin-top: 15px; font-size: 14px; color: #007bff; text-decoration: none; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Khôi Phục Mật Khẩu</h2>
        
        <% if (request.getAttribute("error") != null) { %>
            <div class="error"><%= request.getAttribute("error") %></div>
        <% } %>

        <form action="${pageContext.request.contextPath}/user/forgot-password" method="POST">
            <div class="form-group">
                <label>Tên đăng nhập (Username):</label>
                <input type="text" name="username" placeholder="Nhập tên đăng nhập của bạn" required>
            </div>
            <div class="form-group">
                <label>Địa chỉ Email đăng ký tài khoản:</label>
                <input type="email" name="email" placeholder="Nhập email đăng ký" required>
            </div>
            <button type="submit" class="btn">Gửi mã OTP xác thực</button>
        </form>
        
        <a class="back-link" href="${pageContext.request.contextPath}/user/login">← Quay lại Đăng nhập</a>
    </div>
</body>
</html>