FROM tomcat:9.0-jdk11-openjdk

RUN rm -rf /usr/local/tomcat/webapps/*

# 1. Đổi cổng chạy chính từ 8080 sang 10000
RUN sed -i 's/port="8080"/port="10000"/g' /usr/local/tomcat/conf/server.xml

# 2. 🔥 SỬA CHÍ CHÍNH: Vô hiệu hóa cổng shutdown (đổi từ 8005 sang -1) để diệt log rác
RUN sed -i 's/port="8005" shutdown="SHUTDOWN"/port="-1" shutdown="SHUTDOWN"/g' /usr/local/tomcat/conf/server.xml

COPY dist/Project_Test.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 10000

CMD ["catalina.sh", "run"]