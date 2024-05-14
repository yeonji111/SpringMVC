<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/views/common/header.jsp">
    <jsp:param name="title" value="게시글 수정"/>
</jsp:include>
<h1>게시글 수정</h1>
<form action="/board/update" method="post">
    <label>글번호:
        <input type="text" name="no" readonly value="${board.no }">
    </label>
    <label>제목:
        <input type="text" name="title" value="${board.title }">
    </label>
    <label>내용:
        <textarea name="content" cols="40" rows="10">${board.content }</textarea>
    </label>
    <div style="color:red;">${msg}</div>
    <button type="submit">저장</button>
    <button type="button">취소</button>
</form>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />