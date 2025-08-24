package com.example.ministop.entity;

import com.example.ministop.embedded.InvoiceDetailId;
import jakarta.persistence.*;

import java.math.BigDecimal;

// Đây chính là entity bảng trung gian InvoiceDetail – giúp kết nối Invoice và Product
// theo quan hệ nhiều-nhiều, đồng thời lưu thêm thông tin như số lượng, thành tiền.
// @Entity(name = "invoice_detail")
// Đánh dấu InvoiceDetail là JPA entity.
// name = "invoice_detail": tên entity để dùng trong JPQL/HQL query.
// Nếu muốn ánh xạ đúng bảng DB, bạn có thể thêm: @Table(name = "invoice_detail")
@Entity(name = "invoice_detail")
public class InvoiceDetail {
    @EmbeddedId // cho biết entity này có khóa chính phức hợp (composite key).
    // InvoiceDetailId là class nhúng (embeddable class), trong đó chứa:
    // private String invoiceId;
    // private String productId;
    // Đây chính là primary key của bảng invoice_detail, được tạo từ cặp (invoiceId, productId).
    private InvoiceDetailId invoiceDetailId;

    // @ManyToOne: nhiều dòng chi tiết (InvoiceDetail) có thể thuộc về 1 hóa đơn (Invoice).
    @ManyToOne
    // @MapsId("invoiceId"): ánh xạ trực tiếp field invoiceId trong InvoiceDetailId với quan hệ này.
    // Nghĩa là invoiceDetailId.invoiceId sẽ tự động lấy từ invoice.getInvoiceId().
    @MapsId("invoiceId")
    // @JoinColumn(name = "invoice_id"): trong bảng invoice_detail có cột invoice_id làm foreign
    // key tham chiếu đến Invoice.invoiceId.
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    // @ManyToOne: nhiều dòng chi tiết (InvoiceDetail) có thể tham chiếu cùng một sản phẩm.
    @ManyToOne
    // @MapsId("productId"): ánh xạ field productId trong InvoiceDetailId với quan hệ này.
    // Nghĩa là invoiceDetailId.productId sẽ tự động lấy từ product.getProductId().
    @MapsId("productId")
    // @JoinColumn(name = "product_id"): trong bảng invoice_detail có cột product_id
    // làm foreign key tham chiếu đến Product.productId.
    @JoinColumn(name = "product_id")
    private Product product;

    // Số lượng sản phẩm trong hóa đơn.
    // Ánh xạ với cột quantity trong DB.
    @Column(name = "quantity")
    private int quantity;

    // Thành tiền của dòng chi tiết = quantity × product.price.
    // Kiểu BigDecimal để tính toán tiền tệ chính xác.
    // Ánh xạ với cột subtotal trong DB.
    @Column(name = "subtotal")
    private BigDecimal subtotal;

    public InvoiceDetailId getInvoiceDetailId() {
        return invoiceDetailId;
    }

    public void setInvoiceDetailId(InvoiceDetailId invoiceDetailId) {
        this.invoiceDetailId = invoiceDetailId;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
