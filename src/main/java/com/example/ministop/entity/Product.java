package com.example.ministop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity(name = "product")
public class Product {
    @Id
    private String  productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "price")
    private BigDecimal price;

    // @JoinColumn(name = "unit") → nói rõ rằng cột unit trong bảng product là khóa ngoại
    // Trỏ tới ProductUnit.unitId.
    @ManyToOne
    @JoinColumn(name = "unit")
    private ProductUnit productUnit;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ProductCategory productCategory;

    @Column(name = "stock_quantity")
    private int stockQuantity;

    @Column(name = "product_image")
    private String productImage;

    @JsonIgnore
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
