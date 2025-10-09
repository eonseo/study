import java.io.IOException;
import java.util.Arrays;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class LoggingFilter
 */
@WebFilter("/*")
public class LoggingFilter extends HttpFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // 요청 정보 로깅
        System.out.println("필터");
        System.out.printf("##요청 경로: %s, 요청 방식: %s##\n", httpRequest.getRequestURI(), httpRequest.getMethod());
        System.out.println("요청 파라미터 분석");
        request.getParameterMap().forEach((k, v) -> {
            System.out.printf("name: %s, value: %s\n", k, Arrays.toString(v));
        });
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        chain.doFilter(request, response);
    }

}
