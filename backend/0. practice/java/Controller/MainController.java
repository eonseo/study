import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import com.ssafy.live.model.service.BasicSimpleService;
import com.ssafy.live.model.service.SimpleService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MainController
 */
@WebServlet("/main")
public class MainController extends HttpServlet implements ControllerHelper {

    private final SimpleService sService = null;
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = preProcessing(req, resp);
        switch (action) {
        case "gugu" -> gugu(req, resp);
        default -> resp.sendError(HttpServletResponse.SC_NOT_FOUND); // 404 처리
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = preProcessing(req, resp);
        switch (action) {
            case "login" -> login(req, resp);
        }
    }

    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 1. 파라미터 추출
        String id = req.getParameter("id");
        String password = req.getParameter("pass");

        // 2. 비즈니스 로직 호출
        String result = null;
        if ("ssafy".equals(id) && "1234".equals(password)) {
            result = "로그인 성공!";
        } else {
            result = "id/pass 확인";
        }

        // 3. 화면 처리
        responseHtml(resp, "로그인 결과", result);
    }

    private void gugu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String content = null;

        try {
            int dan = Integer.parseInt(request.getParameter("dan"));
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < 10; i++) {
                sb.append("<li>%d * %d = %d".formatted(dan, i, dan * i));
            }
            content = "<ul>" + sb + "<ul>";

        } catch (NumberFormatException e) {
            content = e.getMessage();
        } catch (RuntimeException e) {
        }
        responseHtml(response, "구구단", content);

    }
}
