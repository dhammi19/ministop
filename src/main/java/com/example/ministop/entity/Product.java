package com.example.ministop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Set;
// @Entity(name = "product")
// Đánh dấu class Product là một JPA entity (được Hibernate quản lý, ánh xạ với một bảng trong DB).
// name = "product": đặt tên entity để sử dụng trong JPQL/HQL query (ví dụ: SELECT p FROM product p).
// Nếu muốn ánh xạ với bảng product trong DB, bạn nên thêm: @Table(name = "product")
@Entity(name = "product")
public class Product {
    @Id // xác định khóa chính (primary key) của bảng.
    private String  productId;

    // Ánh xạ field productName với cột product_name trong DB.
    // Lưu tên sản phẩm (vd: "Coca Cola", "Bánh Oreo").
    @Column(name = "product_name")
    private String productName;

    // Giá sản phẩm, dùng kiểu BigDecimal thay vì double/float để tránh lỗi làm tròn số
    // khi tính toán tiền tệ.
    // Ánh xạ với cột price trong DB.
    @Column(name = "price")
    private BigDecimal price;

    // @ManyToOne: một sản phẩm thuộc về một đơn vị tính (ví dụ "Coca Cola" → "Lon").
    // @JoinColumn(name = "unit"):
    // Trong bảng product, sẽ có cột unit làm foreign key.
    // FK này tham chiếu đến unitId trong bảng product_unit.
    // Như vậy: product.unit → product_unit.unit_id.
    @ManyToOne
    @JoinColumn(name = "unit")
    private ProductUnit productUnit;

    // @ManyToOne: nhiều sản phẩm có thể cùng thuộc một loại hàng
    // (ví dụ "Coca Cola", "Pepsi" đều thuộc "Nước giải khát").
    // @JoinColumn(name = "category_id"):
    // Trong bảng product, có cột category_id làm foreign key.
    // FK này tham chiếu đến categoryId trong bảng product_category.
    // Như vậy: product.category_id → product_category.category_id.
    @ManyToOne
    @JoinColumn(name = "category_id")
    private ProductCategory productCategory;

    // Số lượng tồn kho hiện có của sản phẩm.
    // Kiểu int → ánh xạ cột stock_quantity trong DB.
    @Column(name = "stock_quantity")
    private int stockQuantity;

    // Đường dẫn (URL hoặc file path) tới hình ảnh sản phẩm.
    // Ánh xạ với cột product_image trong DB.
    @Column(name = "product_image")
    private String productImage;

    // @JsonIgnore: bỏ qua khi convert sang JSON để tránh vòng lặp vô hạn
    // (Product chứa InvoiceDetail, mỗi InvoiceDetail lại chứa Product).
    @JsonIgnore
    // @OneToMany(mappedBy = "product"): một sản phẩm có thể xuất hiện trong
    // nhiều dòng hóa đơn (InvoiceDetail).
    // mappedBy = "product" → ánh xạ ngược với trường product trong class InvoiceDetail.
    // Trong InvoiceDetail bạn sẽ có:
    // @ManyToOne
    // @JoinColumn(name = "product_id")
    // private Product product;
    @OneToMany(mappedBy = "product")
    private Set<InvoiceDetail> invoiceDetails;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ProductUnit getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(ProductUnit productUnit) {
        this.productUnit = productUnit;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public Set<InvoiceDetail> getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(Set<InvoiceDetail> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }
}
