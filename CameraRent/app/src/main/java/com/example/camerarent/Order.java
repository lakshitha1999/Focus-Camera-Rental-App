package com.example.camerarent;

public class Order {
    private String orderId;
    private String name;
    private String address;
    private String category;
    private String product;
    private int quantity;
    private String status;

    public Order() {
        // Default constructor required for Firebase
    }

    public Order(String orderId, String name, String address, String category, String product, int quantity) {
        this.orderId = orderId;
        this.name = name;
        this.address = address;
        this.category = category;
        this.product = product;
        this.quantity = quantity;
        this.status = "Pending"; // Default status is Pending
    }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getProduct() { return product; }
    public void setProduct(String product) { this.product = product; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}