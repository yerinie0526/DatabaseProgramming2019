<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>수강신청 결과 보기</title>
<link rel=stylesheet href="<c:url value='/css/main.css' />" type="text/css">
<link rel=stylesheet href="<c:url value='/css/topbar.css' />" type="text/css">
<style>
table {
   text-align: center;
   width: 100%;
   border-collapse:collapse;
}

th{
	border:2px solid black;
}
td {
   width: 150px;
   border:1px solid black;
   
}
</style>
</head>
<body>
<!-- Header -->
	<header>
		<!-- Header desktop -->
		<div class="container-menu-desktop">
			<div class="topbar top">
				<div class="content-topbar container">
					<div class="left-topbar">

						<a href="<c:url value='/user/pmenu' />" class="left-topbar-item"> 메인 </a> 
						<a href="<c:url value='/user/pwish' />" class="left-topbar-item"> 위시리스트 </a> 
					</div>
					<div class="right-topbar">
						<a href="<c:url value='/user/logout' />"  class="right-topbar-item"> logout </a>
					</div>
				</div>
			</div>
		</div>
	</header>
<h3 class="m-t-20">수강신청 결과</h3>
<table>
	<tr>
		<th>학수번호&nbsp;</th>
		<th>과목명&nbsp;</th>
		<th>분반&nbsp;</th>
		<th>학점&nbsp;</th>
		<th>교실&nbsp;</th>
		<th>시간&nbsp;</th>
		<th>수강인원&nbsp;</th>
	</tr>
	
	<c:forEach var="result" items = "${takesList}">
	<tr>
		<td> ${result.courseId}&nbsp;</td>                                                                                                                                                             
		<td>${result.courseName} &nbsp;</td>
	    <td> ${result.classNum} &nbsp;</td>
	    <td> ${result.credit}&nbsp; </td>
	    <c:if test="${result.classroom1 ne result.classroom2}">
		  	<td> ${result.classroom1 } / ${result.classroom2}&nbsp; </td>
		</c:if>
		<c:if test="${result.classroom1 eq result.classroom2}">
		  	<td> ${result.classroom1 }&nbsp;</td>
		</c:if>
	    <c:if test="${result.classroom1 ne result.classroom2}">
		  	<td> ${result.dayWeek1 }${result.time1} / ${result.dayWeek2}${result.time2}&nbsp;</td>
		</c:if>
		<c:if test="${result.classroom1 eq result.classroom2}">
		  	<td> ${result.dayWeek1 }${result.time1}&nbsp;</td>
		</c:if>
	    <td>${result.total}&nbsp;</td>
		
	    </tr>
	</c:forEach>
	
</table>
</body>
</html>