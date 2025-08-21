package com.example.ministop.entity;

import com.example.ministop.embedded.InvoiceDetailId;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity(name = "invoice_detail")
public class InvoiceDetail {
    @EmbeddedId
    private InvoiceDetailId invoiceDetailId;

    @ManyToOne
    @MapsId("invoiceId")
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private int quantity;

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
