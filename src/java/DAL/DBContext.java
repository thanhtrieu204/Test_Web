package DAL;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author FPT University - PRJ30X
 */
public class DBContext {

    protected Connection connection;

    public DBContext() {
        //@Students: You are not allowed to edit this method  
        try {
            Properties properties = new Properties();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("../ConnectDB.properties");
            try {
                properties.load(inputStream);
            } catch (IOException ex) {
                Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            String user = properties.getProperty("userID");
            String pass = properties.getProperty("password");
            String url = properties.getProperty("url");

//            String user = "sa";
//            String pass = "123";
//            String url = "jdbc:sqlserver://localhost:1433;databaseName=PRJ301DB;trustServerCertificate=true";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        // 1. Khởi tạo đối tượng DBContext (hàm constructor sẽ tự động chạy)
        DBContext db = new DBContext();

        // 2. Kiểm tra xem connection đã được thiết lập thành công hay chưa
        try {
            if (db.connection != null && !db.connection.isClosed()) {
                System.out.println("🎉 KẾT NỐI ĐẾN CƠ SỞ DỮ LIỆU THÀNH CÔNG! 🎉");
            } else {
                System.err.println("❌ Kết nối thất bại! Biến connection đang bị null hoặc đã đóng.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, "Lỗi khi kiểm tra trạng thái kết nối!", ex);
        }
    }
}
