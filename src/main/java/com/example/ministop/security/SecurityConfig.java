package com.example.ministop.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
/*
    1. SecurityFilterChain là gì?
        - Trong Spring Security, tất cả request HTTP đi vào ứng dụng đều phải đi qua một chuỗi các filter (bộ lọc).
        - SecurityFilterChain chính là đối tượng định nghĩa tập hợp các filter nào sẽ được áp dụng cho các request nào.
        - Bạn có thể hiểu:
            + "Filter" giống như chốt kiểm soát ở cổng.
            + "SecurityFilterChain" là danh sách nhiều chốt kiểm soát nối tiếp nhau.
            + Request muốn vào ứng dụng thì phải đi qua toàn bộ chuỗi filter này.

    2. Tại sao cần SecurityFilterChain?
        - Nó cho phép bạn tùy biến quy tắc bảo mật cho các endpoint khác nhau.
        - Ví dụ:
            + /api/admin/** chỉ cho phép ROLE_ADMIN.
            + /api/public/** thì ai cũng vào được.
            + /api/user/** thì chỉ ROLE_USER mới được.
        - Tất cả các quy tắc này đều được định nghĩa trong SecurityFilterChain.

    3. Cách hoạt động của SecurityFilterChain
        - Khi có một request HTTP đi vào, Spring Security sẽ duyệt qua danh sách SecurityFilterChain.
        - Với mỗi chain, nó sẽ kiểm tra request có khớp với URL pattern đã định nghĩa không (requestMatchers).
        - Nếu khớp, nó áp dụng các filter tương ứng trong chain đó.
        - Nếu không khớp, nó bỏ qua và tiếp tục kiểm tra chain tiếp theo.
        - Bạn có thể có nhiều SecurityFilterChain trong cùng một ứng dụng, mỗi chain áp dụng cho một nhóm URL khác nhau.

    4. Một số filter quan trọng trong SecurityFilterChain
        - UsernamePasswordAuthenticationFilter → Xử lý đăng nhập bằng form login.
        - BasicAuthenticationFilter → Xử lý xác thực bằng HTTP Basic Auth.
        - BearerTokenAuthenticationFilter → Xử lý xác thực JWT (Bearer Token).
        - ExceptionTranslationFilter → Chuyển các exception thành HTTP response.
        - FilterSecurityInterceptor → Thực hiện kiểm tra cuối cùng quyền truy cập (authorization).

    5. Tóm gọn
        - SecurityFilterChain = danh sách filter bảo mật áp dụng cho request.
        - Bạn có thể có nhiều chain, mỗi chain cho một nhóm URL.
        - Nó thay thế cho WebSecurityConfigurerAdapter trong Spring Boot mới.
        - Dùng để cấu hình xác thực (authentication) và phân quyền (authorization).
 */

@Configuration // Báo cho Spring biết đây là class cấu hình, chứa bean.
// Bật module Spring Security cho ứng dụng web và cho phép bạn override cấu hình bảo mật mặc định
// bằng SecurityFilterChain. Trong Spring Boot 3.x trở lên, nếu bạn đã dùng starter
// spring-boot-starter-security, thì đôi khi không cần @EnableWebSecurity, vì nó đã auto-config rồi.
// Nhưng thêm vào vẫn tốt khi bạn muốn cấu hình rõ ràng.
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                /*
                    - CSRF = Cross-Site Request Forgery (tấn công giả mạo yêu cầu liên trang).
                    - CSRF = tấn công giả mạo request bằng cookie/session của bạn.
                    - Spring Security bật CSRF mặc định để chống tấn công.
                    - Tắt CSRF thường áp dụng cho REST API + JWT (không dùng session).
                    - Bật CSRF nếu app dùng session + form login truyền thống.
                */
                .csrf(csrf -> csrf.disable()) // tắt CSRF
                // Không dùng login form mặc định (phù hợp REST API).
                .formLogin(login -> login.disable()) // tắt form login
                // Không dùng Basic Auth (phù hợp khi bạn muốn JWT/custom).
                .httpBasic(basic -> basic.disable()); // tắt HTTP Basic Auth
        return http.build();
    }

    // Override để Spring không sinh user mặc định
    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager();
    }
}
