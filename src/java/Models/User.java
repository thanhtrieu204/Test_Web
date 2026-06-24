package Models;

import java.sql.Timestamp;
//

public class User {
    private int id;
    private String username;
    private String passwordHash;
    private String fullName;
    private String role;
    private int active;
    private String phone;
private String email;


    private Timestamp createdAt;
    private Timestamp updatedAt;

    public User() {
    }

    public User(int id, String username, String passwordHash, String fullName, String role, int active, String phone, String email, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.role = role;
        this.active = active;
        this.phone = phone;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    
    
    // Constructor dùng khi lấy dữ liệu đầy đủ từ DB lên
    public User(int id, String username, String passwordHash, String fullName, String role, int active, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.role = role;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Constructor dùng khi đăng ký / thêm mới Admin/Staff
    public User(String username, String passwordHash, String fullName, String role, int active) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.role = role;
        this.active = active;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public int getActive() { return active; }
    public void setActive(int active) { this.active = active; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
}