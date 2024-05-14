<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/common/header.jsp">
    <jsp:param name="title" value="게시글 상세"/>
</jsp:include>


<a href="/board/update?no=${board.no}">수정</a>
<a href="javascript:deleteItem('/board/delete?no=${board.no}');">삭제</a>


<div>
    글번호: ${board.no }
</div>
<div>
    작성자: ${board.writer }
</div>
<div>
    등록일자: ${board.createDate}
</div>
<div>
    수정일자: ${board.modifyDate}
</div>
<div>
    제목: ${board.title }
</div>
<div>
    내용: ${board.content }
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />