<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Trang cá nhân nhân sự</title>
        <link href="${pageContext.request.contextPath}/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/sb-admin-2.min.css" rel="stylesheet">
    </head>
    <body>
        <c:if test="${empty sessionScope.currentUser}">
            <c:redirect url="/user/login" />
        </c:if>

        <nav style="text-align: center; margin-top: 20px;">
            <a href="${pageContext.request.contextPath}/user/profile">Hồ sơ cá nhân</a>

            <c:if test="${sessionScope.userRole ne 'CUSTOMER'}">
                | <a href="${pageContext.request.contextPath}/user/list">Quản lý Nhân sự</a>
                | <a href="${pageContext.request.contextPath}/user/listCustomer">Quản lý Khách hàng</a>
            </c:if>

            | <a href="${pageContext.request.contextPath}/user/logout" style="color: red;">Đăng xuất</a>
        </nav>

        <div class="container" style="max-width: 500px; margin: 30px auto; padding: 20px; background: #fff; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.05);">
            <h2>Thông Tin Tài Khoản Hệ Thống</h2>

            <ul style="list-style: none; padding-left: 0; line-height: 2;">
                <li><strong>Tên đăng nhập (Username):</strong> ${sessionScope.currentUser.username}</li>
                <li><strong>Họ và tên:</strong> ${sessionScope.currentUser.fullName}</li>

                <li><strong>Số điện thoại:</strong> ${sessionScope.currentUser.phone}</li>
                <li><strong>Địa chỉ Email:</strong> ${sessionScope.currentUser.email}</li>

                <li><strong>Vai trò hệ thống:</strong> <span style="color: blue; font-weight: bold; text-transform: uppercase;">${sessionScope.currentUser.role}</span></li>
            </ul>

            <div style="text-align: right; margin-top: 20px;">
                <a href="${pageContext.request.contextPath}/user/profile-edit" 
                   style="background-color: #28a745; color: white; padding: 8px 15px; text-decoration: none; border-radius: 4px; font-weight: bold; font-size: 14px; display: inline-block;">
                    ✏️ Chỉnh sửa thông tin
                </a>
            </div>
        </div>
        <script src="${pageContext.request.contextPath}/vendor/jquery/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/sb-admin-2.min.js"></script>
    </body>
</html>