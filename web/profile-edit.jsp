<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Chỉnh sửa hồ sơ cá nhân</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card shadow">
                <div class="card-header bg-primary text-white">
                    <h4 class="mb-0">Chỉnh Sửa Hồ Sơ</h4>
                </div>
                <div class="card-body">
                    
                    <%-- Hiển thị thông báo lỗi nếu có --%>
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/user/profile" method="POST">
                        <%-- Thẻ input ẩn để truyền ID của User lên Servlet --%>
                        <input type="hidden" name="id" value="${currentUser.id}">

                        <div class="mb-3">
                            <label class="form-label">Tài khoản (Username)</label>
                            <input type="text" class="form-control" value="${currentUser.username}" disabled>
                            <small class="text-muted">Không thể thay đổi tên tài khoản</small>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Họ và tên</label>
                            <input type="text" class="form-control" name="fullName" value="${currentUser.fullName}" required>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Số điện thoại</label>
                            <input type="text" class="form-control" name="phone" value="${currentUser.phone}">
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Email</label>
                            <input type="email" class="form-control" name="email" value="${currentUser.email}" required>
                        </div>

                        <div class="d-flex justify-content-between">
                            <a href="${pageContext.request.contextPath}/user/profile" class="btn btn-secondary">Hủy bỏ</a>
                            <button type="submit" class="btn btn-success">Lưu thay đổi</button>
                        </div>
                    </form>

                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>