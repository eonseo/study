import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface ControllerHelper {

    public default String preProcessing(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 로깅
        System.out.println(req.getRequestURL() + " : " + req.getMethod());
        System.out.println("파라미터 로깅");
        Map<String, String[]> params = req.getParameterMap();
        params.forEach((k, v) -> {
            System.out.println("key name: %s, values: %s".formatted(k, Arrays.toString(v)));
        });
        // 2. action parameter 반환
        String action = req.getParameter("action");
        if (action == null || action.isBlank()) {
            action = "index";
        }
        return action;
    }

    public default void responseHtml(HttpServletResponse resp, String title, String content) throws IOException {
        resp.setContentType("text/html;charset=utf-8");
        //  서블릿의 단점: html 을 한 땀 한 땀 !?
        String html = "<html><body><h1><%s/h1><div>%s</div></body></hjtml>".formatted(title, content);
        resp.getWriter().write(html);
    }

    //TODO: 01. redirect와 forward를 처리할 수 있는 utility method로 redirect, forward를 작성하세요.
    public default void redirect(HttpServletRequest request, HttpServletResponse response, String path) throws IOException {}
    public default void forward(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {}

    //END
}
