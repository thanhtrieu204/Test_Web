package Models;

import java.sql.Timestamp;

public class Customer {
    private int id;
    private String fullName;
    private String phone;
    private String email;
    private String username;
    private String passwordHash;
    private int active;
    private String note;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Customer() {}

    // Constructor dùng để Đăng ký (Register)
    public Customer(String fullName, String phone, String email, String username, String passwordHash, int active) {
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.username = username;
        this.passwordHash = passwordHash;
        this.active = active;
    }

    // Getters và Setters tương ứng...
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public int getActive() { return active; }
    public void setActive(int active) { this.active = active; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}