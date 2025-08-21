package com.example.ministop.embedded;

import java.io.Serializable;
import java.util.Objects;

public class InvoiceDetailId implements Serializable {
    private String invoiceId;
    private String productId;

    public InvoiceDetailId() {}

    public InvoiceDetailId(String invoiceId, String productId) {
        this.invoiceId = invoiceId;
        this.productId = productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceId, productId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof InvoiceDetailId that)) return false;
        return Objects.equals(invoiceId, that.invoiceId)
                && Objects.equals(productId, that.productId);
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
