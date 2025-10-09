<%@ page import="java.time.LocalDateTime" %><%--
  Created by IntelliJ IDEA.
  User: o-eonseo
  Date: 2025. 10. 10.
  Time: 오전 2:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <%-- JSP 주석: 클라이언트에게는 가지 않는다.--%>
    <!-- HTML 주석: 클라이언트에게도 보임 -->
    <%
        //script let: 실행문 작성
        LocalDateTime now = LocalDateTime.now();
        out.println(now);
        out.println(sayHello());

    %>>

    <%!
        private String sayHello() {
            return "Hello";
        }
    %>
    <%=sayHello()%>
</body>
</html>
