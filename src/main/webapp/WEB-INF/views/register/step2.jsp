<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2024-05-10
  Time: 오후 4:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>회원 가입</title>
</head>
<body>
<h2>회원 정보 입력</h2>
<form action="step3" method="post">
    <p>
        <label>아이디:<br>
            <input type="text" name="id" id="id" value="${registerRequest.id}">
        </label>
    </p>
    <p>
        <label>이메일:<br>
            <input type="email" name="email" id="email" value="${registerRequest.email}">
        </label>
    </p>
    <p>
        <label>이름:<br>
            <input type="text" name="name" id="name" value="${registerRequest.email}">
        </label>
    </p>
    <p>
        <label>비밀번호:<br>
            <input type="password" name="password" id="password" value="${registerRequest.password}">
        </label>
    </p>
    <p>
        <label>비밀번호 확인:<br>
            <input type="password" name="confirmPassword" id="confirmPassword" value="${registerRequest.confirmPassword}">
        </label>
    </p>
    <button type="submit">가입 완료</button>
</form>
</body>
</html>
