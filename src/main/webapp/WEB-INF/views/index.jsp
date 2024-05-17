<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Home</title>
</head>
<body>
<c:choose>
    <c:when test="${empty sessionScope.member}">
        <a href="/login">로그인</a>
    </c:when>
    <c:otherwise>
        <a href="/logout">로그아웃</a>
    </c:otherwise>
</c:choose>
<a href="<c:url value="/board/list"/>"> 게시판 </a>
</body>
</html>