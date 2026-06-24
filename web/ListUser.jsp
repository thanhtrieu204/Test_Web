<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Quản lý Nhân sự</title>
    <style>
        table {
            width: 85%;
            margin: 20px auto;
            border-collapse: collapse;
            font-family: Arial, sans-serif;
        }
        th, td {
            border: 1px solid #ccc;
            padding: 12px 10px;
            text-align: center;
        }
        th {
            background-color: #f4f4f4;
        }
        .btn-action {
            text-decoration: none;
            padding: 5px 10px;
            border-radius: 4px;
            font-size: 13px;
            font-weight: bold;
        }
        .badge {
            padding: 3px 8px;
            border-radius: 4px;
            font-size: 12px;
            font-weight: bold;
        }
        .badge-admin { background-color: #ffcccc; color: #cc0000; }
        .badge-manager { background-color: #ffe6cc; color: #cc6600; }
        .badge-staff { background-color: #e1f5fe; color: #0288d1; }
        nav { text-align: center; margin-top: 20px; font-family: Arial, sans-serif; }
    </style>
</head>
<body>

    <nav>
        <a href="${pageContext.request.contextPath}/user/profile">Hồ sơ cá nhân</a>
        <c:if test="${sessionScope.userRole ne 'CUSTOMER'}">
            | <a href="${pageContext.request.contextPath}/user/list" style="font-weight: bold;">Quản lý Nhân sự</a>
            | <a href="${pageContext.request.contextPath}/user/listCustomer">Quản lý Khách hàng</a>
        </c:if>
        | <a href="${pageContext.request.contextPath}/user/logout" style="color: red;">Đăng xuất</a>
    </nav>

    <c:choose>
        <c:when test="${sessionScope.userRole eq 'ADMIN' || sessionScope.userRole eq 'MANAGER'}">
            
            <h2 style="text-align: center; margin-top: 30px;">DANH SÁCH TÀI KHOẢN HỆ THỐNG</h2>

            <div style="width: 85%; margin: 0 auto 15px auto; text-align: left;">
                <form action="${pageContext.request.contextPath}/user/list" method="POST">
                    <input type="text" name="txtSearch" value="${oldSearch}" placeholder="Nhập tên nhân viên..." style="padding: 6px; width: 200px;" />
                    
                    <select name="selStatus" style="padding: 6px;">
                        <option value="">-- Tất cả trạng thái --</option>
                        <option value="1" ${oldStatus eq '1' ? 'selected' : ''}>Đang hoạt động</option>
                        <option value="0" ${oldStatus eq '0' ? 'selected' : ''}>Đã khóa</option>
                    </select>

                    <select name="selRole" style="padding: 6px;">
                        <option value="">-- Tất cả vai trò --</option>
                        <option value="ADMIN" ${oldRole eq 'ADMIN' ? 'selected' : ''}>ADMIN</option>
                        <option value="MANAGER" ${oldRole eq 'MANAGER' ? 'selected' : ''}>MANAGER</option>
                        <option value="STAFF" ${oldRole eq 'STAFF' ? 'selected' : ''}>STAFF</option>
                    </select>

                    <button type="submit" style="padding: 6px 15px; cursor: pointer; font-weight: bold; background-color: #0288d1; color: white; border: none; border-radius: 4px;">Tìm kiếm</button>
                    <a href="${pageContext.request.contextPath}/user/list" style="margin-left: 10px; text-decoration: none; color: gray;">Xóa bộ lọc</a>
                </form>
            </div>

            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Username</th>
                        <th>Họ Tên</th>
                        <th>Vai Trò</th>
                        <th>Trạng Thái</th>
                        <th>Hành Động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${empty list}">
                        <tr>
                            <td colspan="6" style="color: gray; font-style: italic;">Không tìm thấy nhân viên nào phù hợp.</td>
                        </tr>
                    </c:if>

                    <c:forEach items="${list}" var="u">
                        <tr>
                            <td>${u.id}</td>
                            <td><strong>${u.username}</strong></td>
                            <td>${u.fullName}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${u.role eq 'ADMIN'}"><span class="badge badge-admin">ADMIN</span></c:when>
                                    <c:when test="${u.role eq 'MANAGER'}"><span class="badge badge-manager">MANAGER</span></c:when>
                                    <c:otherwise><span class="badge badge-staff">${u.role}</span></c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${u.active == 1}">
                                        <span style="color: green; font-weight: bold;">Đang hoạt động</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span style="color: red; font-weight: bold;">Đã khóa</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${u.active == 1}">
                                        <a href="${pageContext.request.contextPath}/user/toggle?id=${u.id}&status=0" 
                                           onclick="return confirm('Bạn có chắc muốn KHÓA tài khoản [${u.username}]?')" 
                                           style="color: #dc3545;" class="btn-action">Khóa</a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="${pageContext.request.contextPath}/user/toggle?id=${u.id}&status=1" 
                                           style="color: #28a745;" class="btn-action">Mở khóa</a>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

        </c:when>
        <c:otherwise>
            <div style="text-align: center; margin-top: 50px; color: red; font-family: Arial, sans-serif;">
                <h3>⛔ CẢNH BÁO: Bạn không có quyền truy cập vào khu vực quản trị Nhân sự!</h3>
                <a href="${pageContext.request.contextPath}/user/profile">Quay lại Hồ sơ cá nhân</a>
            </div>
        </c:otherwise>
    </c:choose>

</body>
</html>