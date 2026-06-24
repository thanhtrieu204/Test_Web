<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Tự động chuyển hướng từ trang gốc sang đường dẫn /user/login của UserServlet
    response.sendRedirect(request.getContextPath() + "/user/login");
%>