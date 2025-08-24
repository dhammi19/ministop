package com.example.ministop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

// @Entity(name = "invoice")
// Đánh dấu class Invoice là một JPA entity → Hibernate quản lý và ánh xạ với một bảng trong DB.
// name = "invoice": tên entity để dùng trong JPQL/HQL query (ví dụ: SELECT i FROM invoice i).
// Nếu bạn muốn ánh xạ trực tiếp với bảng invoice trong DB, nên thêm: @Table(name = "invoice")
@Entity(name = "invoice")
public class Invoice {
    // @Id: xác định khóa chính (primary key).
    // invoiceId: kiểu String → mã hóa đơn (vd: "INV001").
    // Trong DB → ánh xạ với cột invoice_id (nếu không có @Column, JPA sẽ dùng tên field).
    @Id
    private String invoiceId;

    // @ManyToOne: nhiều hóa đơn có thể do 1 nhân viên lập.
    // @JoinColumn(name = "employee_id"): trong bảng invoice, sẽ có cột employee_id làm
    // foreign key tham chiếu đến Employee.employeeId.
    // Quan hệ: Invoice → thuộc về 1 Employee.
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    // @ManyToOne: nhiều hóa đơn có thể thuộc về 1 khách hàng.
    // @JoinColumn(name = "customer_id"): trong bảng invoice, có cột customer_id làm
    // foreign key tham chiếu đến Customer.customerId.
    // Quan hệ: Invoice → thuộc về 1 Customer.
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // @Column(name = "created_date"): ánh xạ với cột created_date trong DB.
    // Lưu thời điểm tạo hóa đơn.
    // Kiểu Date: bạn có thể thay bằng LocalDateTime (Java 8+) để rõ ràng hơn.
    @Column(name = "created_date")
    private Date createdDate;

    // Tổng số tiền của hóa đơn.
    // Kiểu BigDecimal thay vì double/float để đảm bảo chính xác khi tính toán tiền tệ.
    // Ánh xạ với cột total_amount trong DB.
    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    // @JsonIgnore: bỏ qua khi serialize sang JSON để
    // tránh vòng lặp vô hạn (Invoice → InvoiceDetail → lại chứa Invoice).
    @JsonIgnore
    // @OneToMany(mappedBy = "invoice"): một hóa đơn có nhiều dòng chi tiết (InvoiceDetail).
    // Trong class InvoiceDetail, chắc chắn có:
    // @ManyToOne
    // @JoinColumn(name = "invoice_id")
    // private Invoice invoice;
    // mappedBy = "invoice": chỉ rõ mối quan hệ được ánh xạ từ field invoice bên InvoiceDetail.
    @OneToMany(mappedBy = "invoice")
    // Set<InvoiceDetail>: chứa toàn bộ các sản phẩm và số lượng trong hóa đơn.
    private Set<InvoiceDetail> invoiceDetails;

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Set<InvoiceDetail> getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(Set<InvoiceDetail> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }
}
