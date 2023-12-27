<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Web Study</title>

    <%@ include file="include/static-head.jsp" %>

    <style>
        h1 {
            margin: 200px auto;
            width: 40%;
            font-size: 40px;
            font-weight: 700;
            color: orange;
            text-align: center;
        }
    </style>
</head>
<body>

<%
    String userName = "방문자";
    // 클라이언트에게 쿠키를 검사
    Cookie[] cookies = request.getCookies();
    for (Cookie c : cookies) {
        if (c.getName().equals("login")) {
            userName = c.getValue();
        }
    }
%>

<%@ include file="include/header.jsp" %>


<%--
<h1> <%= userName %> 님 안녕하세요~~</h1>
--%>

<%--
    서버에서 Model이나 RedirectAttributes에 담아 놓은 데이터는
    ${requestScope.aaaa} 로 참조가능하고 requestScope는 생략가능

    ${}
--%>
<c:if test="${login == null}">
    <h1>방문자님 안농하세욤~!~!~!~!~!</h1>
</c:if>

<c:if test="${login != null}">
    <h1>${login.nickName}(${login.account})님 ㅎㅇ</h1>
</c:if>

<a href="/members/sign-out">로그아웃</a>
</body>
</html>