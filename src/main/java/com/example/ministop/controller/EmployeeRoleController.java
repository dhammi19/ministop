package com.example.ministop.controller;

import com.example.ministop.entity.EmployeeRole;
import com.example.ministop.service.EmployeeRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/show")
    public ResponseEntity<?> showEmployeeRoles() {
        List<EmployeeRole> employeeRoles = employeeRoleService.getEmployeeRoles();
        return new ResponseEntity<>(employeeRoles, HttpStatus.OK);
    }
}
