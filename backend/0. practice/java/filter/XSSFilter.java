/**
 * Servlet Filter implementation class XSSFilter
 */
@WebFilter("/*")
public class XSSFilter extends HttpFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        chain.doFilter(new XssRequestWrapper(httpRequest), response);
    }

    class XssRequestWrapper extends HttpServletRequestWrapper {

        public XssRequestWrapper(HttpServletRequest request) {
            super(request);
        }

        @Override
        public String getParameter(String name) {
            return cleanXss(super.getParameter(name));
        }

        private String cleanXss(String value) {
            if (value == null) {
                return null;
            }
            return value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        }
    }
}
