# Sử dụng môi trường Tomcat 9 chạy trên Java 11
FROM tomcat:9.0-jdk11-openjdk

# Xóa các file mặc định của Tomcat để giải phóng bộ nhớ
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy file .war từ thư mục dist của NetBeans vào Tomcat và đổi tên thành ROOT.war
# (Giúp bạn truy cập thẳng qua link web chính mà không cần gõ tên dự án phía sau)
COPY dist/Project.war /usr/local/tomcat/webapps/ROOT.war

# Mở cổng 8080 để kết nối mạng với Render
EXPOSE 8080

# Lệnh khởi chạy Server Tomcat khi Render kích hoạt
CMD ["catalina.sh", "run"]