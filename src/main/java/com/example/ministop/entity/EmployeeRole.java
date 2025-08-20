package com.example.ministop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

// @Entity đánh dấu đây là thực thể JPA để Hibernate quản lý.
// Thuộc tính name = "employee_role" đặt tên entity dùng trong JPQL/HQL (ví dụ: SELECT e FROM employee_role e).
// Tên bảng trong DB không được chỉ định trực tiếp bởi @Entity.
// Nếu bạn muốn chắc chắn map tới bảng employee_role trong DB, nên thêm @Table(name = "employee_role").
// Nếu không khai báo @Table, Hibernate sẽ dùng implicit naming strategy để suy ra tên bảng từ tên entity/class
// (thường ra employee_role, nhưng tốt nhất là khai báo rõ).
@Entity(name = "employee_role")
public class EmployeeRole {
    // @Id đánh dấu khóa chính của entity.
    // Không có @Column ⇒ tên cột sẽ được suy ra từ naming strategy.
    // Với Spring Boot mặc định, roleId sẽ thành role_id.
    // Nếu cột DB đúng là role_id, bạn có thể để nguyên
    // Để chắc chắn và tự tài liệu hóa, nên viết rõ:
    // Id
    // @Column(name = "role_id", length = 50)
    // private String roleId;
    @Id
    private String roleId;

    @Column(name = "role_name") // Map field roleName tới cột role_name trong bảng.
    private String roleName;

    // Đây là quan hệ 1-n (OneToMany): một vai trò có nhiều nhân viên.
    //mappedBy = "employeeRole": chỉ ra field ở phía “sở hữu quan hệ” (owning side) trong entity Employee.
    // Tức là trong Employee phải có:
    // @ManyToOne
    // @JoinColumn(name = "role_id")
    // private EmployeeRole employeeRole;
    // Nếu tên field bên Employee khác (không phải employeeRole), bạn phải đổi mappedBy cho trùng khớp.
    // Set<Employee>: dùng Set để tránh trùng lặp
    // Mặc định @OneToMany có fetch = LAZY (Hibernate chỉ tải employees khi cần).
    // @JsonIgnore: khi serialize sang JSON (ví dụ trả API), thuộc tính employees sẽ bị bỏ qua →
    // Tránh vòng lặp vô hạn (role → employees → role → …) và giảm payload.
    @JsonIgnore
    @OneToMany(mappedBy = "employeeRole")
    private Set<Employee> employees;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
}