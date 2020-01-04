<%@page contentType="text/html; charset=utf-8" %>
<%-- <%@page import="java.util.*, model.*" %> --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="utf-8">
<title>위시리스트</title>
</head>
<style media="screen">
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
<link rel=stylesheet href="<c:url value='/css/main.css' />" type="text/css">
<link rel=stylesheet href="<c:url value='/css/wish.css' />" type="text/css">
<link rel=stylesheet href="<c:url value='/css/topbar.css' />" type="text/css">
<body>
	<!-- Header -->
	<header>
		<!-- Header desktop -->
		<div class="container-menu-desktop">
			<div class="topbar top">
				<div class="content-topbar container">
					<div class="left-topbar">

						<a href="<c:url value='/user/pmenu' />" class="left-topbar-item"> 메인 </a> 
						<a href="<c:url value='/user/ptakes' />" class="left-topbar-item"> 수강신청 </a> 
					</div>
					<div class="right-topbar">
						<a href="<c:url value='/user/logout' />"  class="right-topbar-item"> logout </a>
					</div>
				</div>
			</div>
		</div>
	</header>

	<section class="m-t-20">
		<div class="">
			 <div class="col-12">
            	<div class="">
            		<form name = "form" method="POST" action="<c:url value='/wish/table' />">
	                	<h2 class="tm-block-title">위시리스트 담은 인원 보기
						</h2>
					</form>
					<table class="table">
                    	<thead>
                    		<tr>
                            	<th scope="col">학수번호</th>
                            	<th scope="col">강의명</th>
                                <th scope="col">담은인원</th>
                                <th scope="col">교수</th>
                                <th scope="col">학점</th>
                                <th scope="col">강의실</th>
                                <th scope="col">분반</th>
                                <th scope="col">시간</th>
                            </tr>
                        </thead>
                        <tbody>
                        	<c:forEach var="wl" items="${pwishList}">
                              <tr>
                                 <td>${wl.courseId}</td>
                                 <td>${wl.courseName}</td>
                                 <td>${wl.total}/${wl.enroll}</td>
                                 <td>${wl.pName}</td>
                                 <td>${wl.credit}</td>
                                 <td>${wl.classNum}</td>
                                 
                                 <c:choose>
                                    <c:when test="${(wl.classroom1 == wl.classroom2) and (wl.dayWeek1 == wl.dayWeek2) and (wl.time1 == wl.time2)}">
                                      <td>${wl.classroom1}</td>
                                      <td> ${wl.dayWeek1}${wl.time1}</td>
                                </c:when>
                                <c:otherwise>
                                   <td>${wl.classroom1}/${wl.classroom2}</td>
                                   <td> ${wl.dayWeek1}${wl.time1}/${wl.dayWeek2}${wl.time2}</td>
                                </c:otherwise>
                           </c:choose>
	                        	</tr>
                        	</c:forEach> 
                        </tbody>
					</table>
                </div>
            </div>
        </div>
	</section>
</body>
</html>