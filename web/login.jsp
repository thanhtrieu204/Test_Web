<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Đăng nhập hệ thống</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    </head>
    <body>
        <div class="container">
            <h2>HỆ THỐNG BOOKSTORE</h2>

            <c:if test="${not empty error}">
                <div class="error-msg">${error}</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/user/login" method="POST">
                <div class="form-group">
                    <label>Tên đăng nhập (Username):</label>
                    <input type="text" name="username" placeholder="Nhập username của bạn..." required>
                </div>
                <div class="form-group">
                    <label>Mật khẩu:</label>
                    <input type="password" name="password" placeholder="Nhập mật khẩu..." required>
                </div>
                <button type="submit">Đăng nhập</button>
            </form>
            <hr/>
            <div style="margin-top: 10px; text-align: right;">
                <a href="${pageContext.request.contextPath}/user/forgot-password" style="text-decoration: none; color: #007bff; font-size: 14px;">
                    Quên mật khẩu?
                </a>
            </div>
            <div style="text-align: center; margin-top: 15px;">
                <a href="https://accounts.google.com/o/oauth2/auth?scope=email%20profile&redirect_uri=http://localhost:9999/Project/user/login-google&response_type=code&client_id=153135038021-v2i38brv0f598ark9j6hd3khq0it79dr.apps.googleusercontent.com">
                    Đăng nhập bằng Google
                </a>
            </div>
            <div style="margin-top: 20px; text-align: center; border-top: 1px solid #eee; padding-top: 15px;">
                <span style="font-size: 14px; color: #666;">Chưa có tài khoản khách hàng?</span>
                <br>
                <a href="${pageContext.request.contextPath}/user/register" style="color: #007bff; text-decoration: none; font-weight: bold; display: inline-block; margin-top: 5px;">
                    Đăng ký tài khoản mới tại đây
                </a>
            </div>
        </div>
    </body>
</html>