package com.example.ministop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.Set;
// @Entity(name = "product_unit")
// Biến class ProductUnit thành một JPA entity được quản lý bởi Hibernate.
// name = "product_unit": đây là tên entity để dùng trong JPQL/HQL query (ví dụ: SELECT u FROM product_unit u).
@Entity(name = "product_unit")
public class ProductUnit {
    @Id // xác định khóa chính (primary key) của bảng. Trong DB, đây sẽ là cột khóa chính (PRIMARY KEY).
    private String unitId;

    // @Column: ánh xạ giữa thuộc tính unitName trong Java ↔ cột unit_name trong DB.
    // Dùng để lưu tên của đơn vị tính sản phẩm, ví dụ: "Hộp", "Cái", "Kg", "Lít".
    @Column(name = "unit_name")
    private String unitName;

    // Khi serialize sang JSON (ví dụ trả về từ REST API), bỏ qua trường products.
    // Nếu không có @JsonIgnore, sẽ xảy ra vòng lặp vô hạn:
    // ProductUnit chứa products → mỗi Product lại chứa productUnit → lặp vô tận.
    @JsonIgnore
    // @OneToMany(mappedBy = "productUnit")
    // Quan hệ 1-N:
    // Một đơn vị tính (ProductUnit) có thể áp dụng cho nhiều sản phẩm (Product).
    // mappedBy = "productUnit" nghĩa là:
    // Quan hệ này được ánh xạ từ trường productUnit trong class Product.
    // Trong entity Product, bạn sẽ có:
    // @ManyToOne
    // @JoinColumn(name = "unit_id")
    // private ProductUnit productUnit;
    @OneToMany(mappedBy = "productUnit")
    // Tập hợp các sản phẩm thuộc đơn vị tính này.
    // Dùng Set để tránh trùng lặp.
    private Set<Product> products;

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
