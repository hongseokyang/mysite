<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link
	href="${pageContext.servletContext.contextPath }/assets/css/board.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<form 
					id="search_form"
					action="${pageContext.servletContext.contextPath }/board"
					method="post">
					<input type="text" id="kwd" name="kwd" value="${kwd }"> 
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:set var="size" value="${fn:length(list) }" />
					<c:forEach items="${list }" var="vo" varStatus="status">
						<tr>
							
							<td>${pagination.listCnt-((pagination.curPage-1)*pagination.pageSize)-status.index }</td>
							
							<td style="padding-left:${15*vo.depth}px; text-align:left">
								<c:if test="${vo.depth ne 0 }">
									<img src="${pageContext.servletContext.contextPath }/assets/images/reply.png" />
								</c:if>
								<c:if test="${vo.stateNo ne 0 }">
									[${vo.state }]
								</c:if>
								<c:if test="${vo.stateNo ne 2 }">
									<a href="${pageContext.servletContext.contextPath }/board/view/${vo.no }/${pagination.curPage }?kwd=${kwd }">${vo.title }</a>
								</c:if>
							</td>
							<td>${vo.userName }</td>
							<td>${vo.hit }</td>
							<td>${vo.regDate }</td>
							<td>
							<c:if test="${authUser.no ne null && authUser.no eq vo.userNo && vo.stateNo ne 2}">
								<a href="${pageContext.servletContext.contextPath }/board/delete/${vo.no }/${pagination.curPage }" class="del">삭제</a>
							</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>

				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<c:if test="${pagination.curRange ne 1 }">
							<li><a href="${pageContext.servletContext.contextPath }/board?p=1&kwd=${kwd }" >[◀◀]</a></li>
						</c:if>
						 
						<c:if test="${pagination.curRange ne 1}">
							<li><a href="${pageContext.servletContext.contextPath }/board?p=${pagination.prevRange }&kwd=${kwd }">[◀]</a></li>
						</c:if>
						
						<c:forEach var="pageNum" begin="${pagination.startPage }" end="${pagination.endPage }">
							<c:choose>
								<c:when test="${pageNum eq  pagination.curPage}">
									<li class="selected">${pageNum }</li>
								</c:when>
								<c:otherwise>
									<li><a href="${pageContext.servletContext.contextPath }/board?p=${pageNum }&kwd=${kwd }">${pageNum }</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						 
						<c:if test="${pagination.curRange ne pagination.rangeCnt && pagination.rangeCnt > 1}">
							<li><a href="${pageContext.servletContext.contextPath }/board?p=${pagination.nextRange }&kwd=${kwd }">[▶]</a></li>
						</c:if>
						 
						<c:if test="${pagination.curRange ne pagination.rangeCnt && pagination.rangeCnt > 0}">
							<li><a href="${pageContext.servletContext.contextPath }/board?p=${pagination.pageCnt }&kwd=${kwd }" >[▶▶]</a></li>
						</c:if>
					</ul>
				</div>

				<div class="bottom">
					<a href="${pageContext.servletContext.contextPath }/board/write?p=${pagination.curPage }"	id="new-book">글쓰기</a>
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board" />
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>