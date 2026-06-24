FROM tomcat:9.0-jdk11-openjdk

RUN rm -rf /usr/local/tomcat/webapps/*

# Sửa lại đúng đường dẫn file server.xml (thêm thư mục /conf/)
RUN sed -i 's/port="8080"/port="10000"/g' /usr/local/tomcat/conf/server.xml

COPY dist/Project_Test.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 10000

CMD ["catalina.sh", "run"]