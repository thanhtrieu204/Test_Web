<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Đăng ký tài khoản mới</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <h2>ĐĂNG KÝ TÀI KHOẢN KHÁCH HÀNG</h2>
        <c:if test="${not empty error}">
            <div class="error-msg">${error}</div>
        </c:if>
        
        <form action="${pageContext.request.contextPath}/user/register" method="POST">
            <div class="form-group">
                <label>Họ và tên:</label>
                <input type="text" name="fullName" placeholder="Nhập họ và tên..." required>
            </div>
            <div class="form-group">
                <label>Số điện thoại:</label>
                <input type="text" name="phone" placeholder="Nhập số điện thoại...">
            </div>
            <div class="form-group">
                <label>Email liên hệ:</label>
                <input type="email" name="email" placeholder="Nhập email..." required>
            </div>
            <div class="form-group">
                <label>Tên đăng nhập (Username):</label>
                <input type="text" name="username" placeholder="Tên dùng để đăng nhập..." required>
            </div>
            <div class="form-group">
                <label>Mật khẩu:</label>
                <input type="password" name="password" placeholder="Nhập mật khẩu..." required>
            </div>
            <button type="submit">Đăng ký tài khoản</button>
        </form>
        <p style="margin-top: 15px; text-align: center;">
            <a href="${pageContext.request.contextPath}/user/login">Đã có tài khoản? Đăng nhập</a>
        </p>
    </div>
</body>
</html>