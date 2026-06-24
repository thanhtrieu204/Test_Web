FROM tomcat:9.0-jdk11-openjdk

RUN rm -rf /usr/local/tomcat/webapps/*

# Thay đổi cổng mặc định của Tomcat từ 8080 sang 10000 để khớp với Render
RUN sed -i 's/port="8080"/port="10000"/g' /usr/local/tomcat/server.xml

COPY dist/Project_Test.war /usr/local/tomcat/webapps/ROOT.war

# Mở cổng 10000 ra môi trường ngoài
EXPOSE 10000

CMD ["catalina.sh", "run"]