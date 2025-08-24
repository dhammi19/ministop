package com.example.ministop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.Set;
// @Entity(name = "product_category") Đánh dấu class ProductCategory
// là một JPA entity → ánh xạ với bảng trong DB.
// name = "product_category": đặt tên entity trong JPQL/HQL query.
// Ví dụ: SELECT c FROM product_category c
// Lưu ý: name trong @Entity không phải tên bảng.
// Nếu muốn đổi tên bảng trong DB → dùng @Table(name = "product_category").
@Entity(name = "product_category")
public class ProductCategory {

    @Id // @Id: trường này là khóa chính (primary key) trong bảng. Trong DB → cột này là primary key.
    private String categoryId;

    // @Column: ánh xạ giữa thuộc tính categoryName trong Java ↔ cột category_name trong DB.
    // Dùng khi tên thuộc tính trong code không trùng với tên cột trong DB (camelCase ↔ snake_case).
    // Đây là tên loại sản phẩm, ví dụ: "Đồ uống", "Bánh kẹo", "Gia dụng".
    @Column(name = "category_name")
    private String categoryName;

    // Tránh lỗi vòng lặp vô hạn khi serialize JSON.
    // Nếu không có @JsonIgnore, khi trả JSON từ REST API:
    // ProductCategory chứa products,
    // mỗi Product lại chứa productCategory,
    // dẫn đến vòng lặp vô hạn → StackOverflowError.
    @JsonIgnore
    // @OneToMany(mappedBy = "productCategory") là quan hệ 1-N:
    // Một ProductCategory có thể chứa nhiều Product.
    // mappedBy = "productCategory": nghĩa là quan hệ này được ánh xạ từ field productCategory
    // trong entity Product.
    // Bên class Product chắc chắn có:
    // @ManyToOne
    // @JoinColumn(name = "category_id")
    // private ProductCategory productCategory;
    @OneToMany(mappedBy = "productCategory")
    // Tập hợp (Set) chứa các sản phẩm thuộc loại này.
    // Dùng Set để đảm bảo không có sản phẩm trùng lặp.
    private Set<Product> products;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
