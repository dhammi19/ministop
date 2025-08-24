package com.example.ministop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.Set;
// @Entity(name = "customer") Đánh dấu class này là một JPA entity → ánh xạ với một bảng trong database.
// name = "customer": đặt tên entity là "customer".
// Đây không phải là tên bảng trong DB (tên bảng phải chỉnh bằng @Table).
@Entity(name = "customer")
public class Customer {
    @Id // Đánh dấu trường này là khóa chính (primary key).
    private String customerId;

    // @Column: ánh xạ trường fullName của Java với cột full_name trong DB.
    // Nếu không viết @Column, JPA mặc định sẽ map fullName → fullName.
    // Bạn dùng name = "full_name" để match với cột snake_case trong DB.
    @Column(name = "full_name")
    private String fullName;

    // Map phoneNumber trong Java với cột phone_number trong DB.
    // Dùng kiểu String vì số điện thoại thường có 0, +84… (không phải số toán học).
    @Column(name = "phone_number")
    private String phoneNumber;

    // Map loyaltyPoints với cột loyalty_points trong DB.
    // Kiểu int → số điểm tích lũy (chương trình khách hàng thân thiết).
    // Giá trị này có thể tăng/giảm khi khách mua hàng.
    @Column(name = "loyalty_points")
    private int loyaltyPoints;

    // Annotation của Jackson (dùng khi serialize object thành JSON).
    // Nếu không có @JsonIgnore, khi convert Customer sang JSON sẽ xảy ra vòng lặp vô hạn:
    // Customer chứa invoices → mỗi Invoice lại chứa customer → lặp lại mãi.
    // @JsonIgnore giúp bỏ qua invoices khi trả JSON (ví dụ khi viết REST API).
    @JsonIgnore
    // Thiết lập mối quan hệ 1-N: Một khách hàng (Customer) có thể có nhiều hóa đơn (Invoice).
    // mappedBy = "customer": chỉ ra rằng:
    // Mối quan hệ được ánh xạ bởi field customer trong class Invoice.
    // Nghĩa là bên class Invoice có:
    // @ManyToOne
    // @JoinColumn(name = "customer_id")
    // private Customer customer;
    @OneToMany(mappedBy = "customer")
    // Tập hợp các hóa đơn (Invoice) mà khách hàng đã phát sinh.
    // Dùng Set thay vì List để tránh trùng lặp hóa đơn.
    private Set<Invoice> invoices;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }
}
