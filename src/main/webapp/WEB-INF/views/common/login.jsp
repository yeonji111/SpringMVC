<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 로그인</title>
<style>
	label {
		display: block;
	}
	#log {
		color: red;
	}
</style>
</head>
<body>
<h2>로그인</h2>
<form action="/login" method="post" id="loginForm">
	<label>아이디:
		<input type="text" name="id" value="${cookie.savedId.value }" placeholder="ID를 입력하세요.">
	</label>
	<label>패스워드:
		<input type="password" name="password" placeholder="패스워드를 입력하세요.">
	</label>
	<label>
		<input type="checkbox" value="true"> 아이디 저장
	</label>
	<input type="hidden" name="location" value="${location}">
	<div id="log">${msg}</div>
	<button type="button" id="loginBtn">로그인</button>
	<button type="button">취소</button>
</form>
<script>
	document.querySelector("#loginBtn").addEventListener("click", evt => {
		const loginForm = document.querySelector("#loginForm");
		// javascript에서 지원하는 FormData는 multipart/form-data 형식으로 전송
		// payload를 통해 전달함
		let formData = new FormData(loginForm)
		fetch("/ajaxLogin", {
			method: "POST",
			body: formData
		})
		.then(response => response.json())
		.then(data => {
			if (data.msg === "failure") {
				document.querySelector("#log").textContent = "로그인 실패!"
			} else {
				location.href = data.msg;
			}
		})
	})
</script>
</body>
</html>