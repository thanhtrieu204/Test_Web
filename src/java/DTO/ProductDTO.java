package DTO;

public class ProductDTO {
    private int id;
    private String name;
    private int price;
    private int unitsInStock;
    private int categoryId;
    private String categoryName; // Thuộc tính bổ sung để chứa tên danh mục

    // Constructor không tham số
    public ProductDTO() {
    }

    // Constructor đầy đủ tham số
    public ProductDTO(int id, String name, int price, int unitsInStock, int categoryId, String categoryName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.unitsInStock = unitsInStock;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getUnitsInStock() {
        return unitsInStock;
    }

    public void setUnitsInStock(int unitsInStock) {
        this.unitsInStock = unitsInStock;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}