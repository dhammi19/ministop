package com.example.ministop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

// @Entity đánh dấu đây là một thực thể JPA (sẽ được map với một bảng trong CSDL).
// Thuộc tính name = "employee" đặt tên thực thể trong JPQL/HQL.
// Nếu bạn không dùng @Table, đa số provider sẽ dùng tên thực thể làm tên bảng mặc định.
// Tuy nhiên, để rõ ràng, thực tế nên thêm @Table(name = "employee").
// public class Employee là lớp mô tả bản ghi trong bảng nhân viên.
@Entity(name = "employee")
public class Employee {
    // @Id đánh dấu khóa chính của thực thể.
    // Kiểu String cho phép bạn tự cấp mã nhân viên (ví dụ: EMP001).
    // Nếu muốn DB tự sinh, bạn cần thêm @GeneratedValue(...).
    //Lưu ý: bạn không có @Column ở đây, nên tên cột mặc định sẽ là employeeId
    // (trừ khi bạn cấu hình naming strategy chuyển thành employee_id).
    @Id
    private String employeeId;

    @Column(name = "full_name") // Map thuộc tính fullName với cột full_name trong bảng.
    private String fullName;

    // @Temporal(TemporalType.DATE) nói với JPA rằng trường Date này chỉ lưu phần ngày (không có giờ/phút/giây)
    // Tương ứng kiểu DATE ở DB.
    // Nếu bạn dùng java.time.LocalDate, không cần @Temporal.
    // Map với cột birth_date.
    @Temporal(TemporalType.DATE)
    @Column(name = "birth_date")
    private Date birthDate;

    // Cột gender. Đang dùng String (ví dụ "MALE", "FEMALE", "OTHER"…).
    // Nếu muốn chuẩn hóa, có thể dùng enum và chú thích @Enumerated(EnumType.STRING).
    @Column(name = "gender")
    private String gender;

    // Cột phone_number. Dùng String là hợp lý cho số điện thoại
    // (vì có thể bắt đầu bằng số 0, có dấu +, khoảng trắng…).
    // Có thể bổ sung validate (Bean Validation) như @Pattern hoặc @Size.
    @Column(name = "phone_number")
    private String phoneNumber;

    // Quan hệ Many-to-One: nhiều nhân viên có thể thuộc một vai trò (EmployeeRole).
    // @JoinColumn(name = "role_id") chỉ định tên cột khóa ngoại trong bảng employee
    // Trỏ tới khóa chính của bảng vai trò (thường là employee_role).
    // Mặc định của @ManyToOne là FetchType.EAGER (khi tải Employee sẽ kéo theo EmployeeRole).
    // Điều này tiện nhưng có rủi ro N+1 query; nếu không cần luôn, cân nhắc đặt fetch = FetchType.LAZY.
    // Không khai báo cascade, nên thao tác lưu/xóa Employee không tự động cascade sang EmployeeRole
    // (thường là đúng mong muốn vì role là dữ liệu dùng chung).
    // Ở phía nghịch (EmployeeRole), bạn thường sẽ có:
    // @OneToMany(mappedBy = "employeeRole")
    // private List<Employee> employees;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private EmployeeRole employeeRole;

    @JsonIgnore
    @OneToMany(mappedBy = "employee")
    private Set<Invoice> invoices;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public EmployeeRole getEmployeeRole() {
        return employeeRole;
    }

    public void setEmployeeRole(EmployeeRole employeeRole) {
        this.employeeRole = employeeRole;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }
}