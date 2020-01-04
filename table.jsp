<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<%request.setCharacterEncoding("UTF-8");%>

<html>
<head>
<meta charset="utf-8">

<title>동덕여자대학교 수강신청</title>
<link rel=stylesheet href="<c:url value='/css/main.css' />" type="text/css">
<link rel=stylesheet href="<c:url value='/css/wish.css' />" type="text/css">
<link rel=stylesheet href="<c:url value='/css/topbar.css' />" type="text/css">
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
<body>
   <!-- Header -->
   <header>
      <!-- Header desktop -->
      <div class="container-menu-desktop">
         <div class="topbar top">
            <div class="content-topbar container">
               <div class="left-topbar">
					<a href="<c:url value='/user/menu' />" class="left-topbar-item"> 메인 </a> 
                  <a href="<c:url value='/user/takes' />" class="left-topbar-item"> 수강신청 </a> 
                  <a href="<c:url value='/user/wish' />" class="left-topbar-item"> 위시리스트 </a> 
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
                   <h2 class="tm-block-title">내 시간표 </h2>
                    <table class="table">
                       <thead>
                          <tr>
                               <th scope="col">교시\요일</th>
                               <c:forEach var="item" items="${nameList}">
                               <th scope="col">${item}</th>
                                </c:forEach>
                            </tr>
                        </thead>
                        <tbody>
                           <c:forEach var="i" begin="1" end="6">
                              <tr>
                        <td>${i}교시</td>
                                 <c:forEach var="item" items="${nameList}">
                                    <c:set var="bool" value="0"/>
                                    <c:forEach var="rl" items="${resultList}">
                                       <c:if test="${item eq rl.dayWeek1 and i eq rl.time1}">
                                          <td>${rl.courseName}</td>
                                          <c:set var="bool" value="1"/>
                                       </c:if>
                                       <c:if test="${item eq rl.dayWeek2 and i eq rl.time2 and !((rl.dayWeek1 eq rl.dayWeek2) and (rl.time1 eq rl.time2))}">
                                          <td>${rl.courseName}</td>
                                          <c:set var="bool" value="1"/>
                                       </c:if>
                                    </c:forEach>
                                    <c:if test="${bool eq '0'}">
                                       <td>&nbsp;</td>
                                    </c:if>
                                 </c:forEach>
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