package com.example.ministop.embedded;

import java.io.Serializable;
import java.util.Objects;

// 1. Serialization = quá trình biến một đối tượng Java thành dãy byte để:
// Lưu xuống file.
// Gửi qua mạng.
// Lưu vào database.
// Cache/ghi nhớ trong session.
// Trong Java, để một class có thể tuần tự hóa được, nó phải implements Serializable
// (đây là một marker interface, không có phương thức nào).
// 2. Vì sao JPA cần Serializable?
// Trong JPA/Hibernate:
// Khi bạn dùng khóa chính tổng hợp (composite key) (như InvoiceDetailId), framework có thể phải:
// Lưu trữ khóa này vào cache (ví dụ: Level 1 cache trong Session hoặc Level 2 cache).
// Gửi nó qua mạng trong trường hợp phân tán (distributed systems).
// Vì vậy JPA bắt buộc lớp ID phải Serializable để đảm bảo có thể tuần tự hóa/giải tuần tự khi cần.
// 3. Quy định trong JPA
// Theo JPA Specification, một class composite key (dùng với @IdClass hoặc @EmbeddedId) bắt buộc phải:
// Có constructor không tham số.
// Override equals() và hashCode().
// phải implements Serializable.
// Nghĩa là nếu thiếu Serializable, bạn đã vi phạm chuẩn JPA.
// Kết luận:
// Nếu bạn không tuần tự hóa class dùng làm khóa chính tổng hợp → Hibernate/JPA
// sẽ không hoạt động đúng và có thể báo lỗi runtime (đặc biệt khi dùng cache hoặc cluster).
// Vì vậy: luôn implements Serializable cho composite key class là bắt buộc.
// 4. Mã băm là gì?
// Mã băm (hash code) là một số nguyên (int trong Java) được sinh ra từ dữ liệu (object).
// Nó được tính bởi hàm băm (hash function) – một thuật toán chuyển đổi dữ liệu bất kỳ
// thành một giá trị số có độ dài cố định.
// Ví dụ:
// Bạn có chuỗi "ABC" → hàm băm có thể trả về 64578.
// Bạn có số 123 → có thể ra 123 hoặc một số khác tùy thuật toán.
// Nói ngắn gọn: mã băm là một "dấu vân tay số" của dữ liệu.

public class InvoiceDetailId implements Serializable {
    // Hai trường dữ liệu riêng tư (đóng gói/encapsulation),
    // đại diện cho 2 cột khóa cấu thành khóa chính tổng hợp.
    private String invoiceId;
    private String productId;

    // Constructor không tham số (no-args): bắt buộc cho JPA/Hibernate
    // để chúng có thể khởi tạo đối tượng bằng phản chiếu (reflection).
    public InvoiceDetailId() {}

    // Constructor đầy đủ tham số: tạo nhanh một khóa tổng hợp hợp lệ.
    public InvoiceDetailId(String invoiceId, String productId) {
        this.invoiceId = invoiceId;
        this.productId = productId;
    }

    // Chỉ thị cho trình biên dịch biết rằng bạn đang ghi đè (override) phương thức
    // từ lớp cha (java.lang.Object).
    // Giúp bắt lỗi sớm nếu ký hiệu phương thức sai (ví dụ viết nhầm hashcode thay vì hashCode).
    @Override
    public int hashCode() { // hashCode() cung cấp mã băm nhất quán với equals.
        // Objects.hash(...) sinh mã băm từ nhiều trường một cách an toàn với null và đúng quy ước.
        // Tại sao quan trọng?
        // Các cấu trúc như HashSet, HashMap dựa vào hashCode để so sánh/tra cứu.
        // Hibernate/JPA cũng có thể đặt các entity/ID vào bộ nhớ đệm dựa trên hash.
        // Quy tắc: nếu a.equals(b) là true thì a.hashCode() == b.hashCode() phải đúng.
        return Objects.hash(invoiceId, productId);
    }

    @Override
    public boolean equals(Object obj) {
        // So sánh tham chiếu: nếu là cùng một đối tượng thì bằng nhau ngay, tối ưu hiệu năng.
        if (this == obj) return true;
        // Kiểm tra kiểu: nếu obj không phải InvoiceDetailId → không bằng.
        // Cú pháp pattern matching cho instanceof (Java 16 trở lên): nếu đúng kiểu, tự cast vào biến
        if (!(obj instanceof InvoiceDetailId that)) return false;
        // So sánh từng trường của khóa, dùng Objects.equals để an toàn với null.
        // Hai đối tượng bằng nhau khi cùng invoiceId và productId.
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
