package com.example.ministop.controller;

import com.example.ministop.entity.EmployeeRole;
import com.example.ministop.payload.request.EmployeeRoleRequest;
import com.example.ministop.payload.response.DataResponse;
import com.example.ministop.service.EmployeeRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
    - HTTP Response gồm 2 phần chính:
        + Header (thông tin meta: status code, content-type, length, cookies, v.v.)
        + Body (phần dữ liệu mà client thực sự nhận về: HTML, JSON, file, text, …)
        + Ví dụ một HTTP Response chuẩn:
            HTTP/1.1 200 OK
            Content-Type: application/json
            Content-Length: 65

            {
                "id": "u001",
                "name": "Anh Dao",
                "role": "IT Developer"
            }
        + Phần trên cùng (HTTP/1.1 200 OK, Content-Type, Content-Length) là header.
        + Phần dưới (JSON) chính là response body.

    - @Controller là một annotation trong Spring MVC. Dùng để đánh dấu class là Controller
    trong mô hình MVC (Model–View–Controller). Mục tiêu chính: nhận request từ client → xử lý →
    trả về tên View (HTML, JSP, Thymeleaf, …).
        + Dùng để trả về tên View (không phải dữ liệu thô), Spring sẽ tìm đến file template
        (JSP, Thymeleaf, Freemarker) tương ứng để render HTML.
        + Có thể kết hợp với @ResponseBody nếu bạn muốn trả JSON thay vì View, nhưng lúc này
        thì gần giống @RestController.
        + Làm việc chặt chẽ với Model, thường dùng Model hoặc ModelAndView để đẩy dữ liệu ra View.

    - @ResponseBody là Annotation trong Spring MVC/Spring Boot. Báo cho Spring biết
    rằng giá trị trả về của method không phải là tên view, mà là dữ liệu trực tiếp
    (được ghi vào HTTP response body). Spring sẽ tự động convert object → JSON (hoặc XML)
    thông qua HttpMessageConverter (thường dùng Jackson cho JSON).

    - @RestController là một specialized controller trong Spring Boot,
    Được sử dụng để xây dựng RESTful API.
        + Thay vì trả về view (HTML), nó trả về dữ liệu (JSON/XML/Plain Text).
        + Bản chất = @Controller + @ResponseBody.
        + Thường dùng với API Client (Postman, Frontend, Mobile App), không render ra HTML, chỉ trả dữ liệu thô.

    - So sánh giữa @ResponseBody và @RestController:
        | Đặc điểm        | `@ResponseBody`                                               | `@RestController`                                                      |
        | --------------- | ------------------------------------------------------------- | ---------------------------------------------------------------------- |
        | Loại annotation | Gắn trên **method** hoặc **class**                            | Gắn trên **class**                                                     |
        | Chức năng       | Chỉ áp dụng cho method có annotation này.                     | Tự động áp dụng `@ResponseBody` cho **tất cả các method** trong class. |
        | Kết hợp với     | Thường kết hợp với `@Controller`                              | Bản chất là `@Controller` + `@ResponseBody`                            |
        | Dùng khi        | Muốn 1 vài method trong Controller trả JSON, còn lại trả View | Xây REST API (toàn bộ method trả JSON/XML)                             |

*/
@RestController
@RequestMapping("/employee-role")
public class EmployeeRoleController {
    @Autowired
    EmployeeRoleService employeeRoleService;

    // Đây là annotation của Spring MVC.
    // Nghĩa là: Khi client gửi request GET /api/employee-role/show, thì method này sẽ được gọi.
    @GetMapping("")
    // ResponseEntity<T>: lớp của Spring dùng để trả về HTTP response đầy đủ
    // (bao gồm body + status code + header).
    // DataResponse: là class wrapper bạn đã định nghĩa (statusCode, success, description, data).
    public ResponseEntity<DataResponse> showEmployeeRoles() {
        //Gọi tới service layer (employeeRoleService) để lấy dữ liệu từ database.
        //getEmployeeRoles() thường sẽ gọi xuống repository (DAO) để fetch danh sách EmployeeRole.
        //Kết quả lưu vào employeeRoles.
        List<EmployeeRole> employeeRoles = employeeRoleService.getEmployeeRoles();

        // Tạo một object DataResponse mới. Đây sẽ là response chuẩn hóa mà bạn trả về cho client.
        DataResponse response = new DataResponse();
        // Gán statusCode = 200.
        // HttpStatus.OK.value() chính là số 200 (HTTP status code cho “OK”).
        response.setStatusCode(HttpStatus.OK.value());
        // Đánh dấu response là thành công (true).
        // Client chỉ cần check success=true/false để biết kết quả mà không cần phân tích sâu status code.
        response.setSuccess(true);
        // Ghi chú thêm một mô tả qngắn gọn để client dễ hiểu kết quả.
        // Ví dụ: "Get employee roles successfully".
        // Nếu lỗi, bạn có thể set message khác, ví dụ "No roles found" hoặc "Database error".
        response.setDescription("Get employee roles successfully");
        // data chính là payload thực sự: danh sách các EmployeeRole.
        // Đây mới là dữ liệu mà client cần.
        response.setData(employeeRoles);

        // Trả về HTTP Response với status code 200 OK và body là response (DataResponse).
        // Thay vì return new ResponseEntity<>(response, HttpStatus.OK);, bạn dùng helper
        // ResponseEntity.ok(...) cho gọn.
        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<DataResponse> addAnEmployeeRole(@RequestBody EmployeeRoleRequest request) {
        boolean isCreated = employeeRoleService.addEmployeeRole(request);

        DataResponse response = new DataResponse();

        if (isCreated) {
            response.setStatusCode(HttpStatus.CREATED.value());
            response.setSuccess(true);
            response.setDescription("Employee role created successfully");
            response.setData(request);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            response.setStatusCode(HttpStatus.CONFLICT.value()); // 409 Conflict
            response.setSuccess(false);
            response.setDescription("RoleId already exists: " + request.getRoleId());
            response.setData(null);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<DataResponse> updateEmployeeRole(
            @PathVariable String roleId,
            @RequestBody EmployeeRoleRequest request) {
        boolean isUpdated = employeeRoleService.updateEmployeeRole(roleId, request);

        DataResponse response = new DataResponse();

        if (isUpdated) {
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setDescription("Employee role updated successfully");
            response.setData(request);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setSuccess(false);
            response.setDescription("Couldn't update the role");
            response.setData(null);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<DataResponse> deleteEmployee(
            @PathVariable String roleId) {
        boolean isDeleted = employeeRoleService.deleteEmployeeRole(roleId);

        DataResponse response = new DataResponse();

        if (isDeleted) {
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setDescription("Employee role deleted successfully");
            response.setData("Employee role id just deleted: "+roleId);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setSuccess(false);
            response.setDescription("Couldn't delete the role");
            response.setData(null);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<DataResponse> getEmployeeRoleById(
            @PathVariable String roleId) {
        List<EmployeeRole> employeeRoles = employeeRoleService.getEmployeeRoleById(roleId);

        DataResponse response = new DataResponse();

        if (employeeRoles.isEmpty()) {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setSuccess(false);
            response.setDescription("Couldn't find employee role id");
            response.setData(null);

            return ResponseEntity.ok(response);
        } else {
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setDescription("Get employee roles successfully");
            response.setData(employeeRoles);

            return ResponseEntity.ok(response);
        }
    }

    /*
        ---------- Tại sao phải trả về 2 chỗ chứa status code: ----------

        1. ResponseEntity → đây là HTTP status code (ví dụ: 200, 400, 404, 500).
            - Đây là chuẩn HTTP, được browser, Postman, client-side framework (React, Angular,
            mobile app) dùng để biết kết quả request có thành công hay không.
            - Ví dụ:
                + 200 OK → thành công
                + 404 Not Found → không tìm thấy
                + 500 Internal Server Error → lỗi server

        2. DataResponse.statusCode → đây là application-level status code
        (do bạn tự định nghĩa trong JSON trả về).
            - Nó nằm trong body response, chứ không phải HTTP header.
            - Mục đích: client có thể xử lý chi tiết hơn dựa trên business logic.
            - Ví dụ:
                + 1000 → Lấy dữ liệu thành công
                + 1001 → Không tìm thấy nhân viên
                + 1002 → Role không tồn tại
                + 2000 → Lỗi xác thực

        3. Lý do cần cả 2 là vì:
            - HTTP status code: dùng để phân biệt lỗi ở mức HTTP (thành công hay thất bại chung).
            - Application status code: dùng để mô tả chi tiết hơn về ngữ cảnh trong ứng dụng.
    */
}
