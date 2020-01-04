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

.scroll {
   overflow-y: scroll;
}
.order{
	width: 80%;
}
</style>
<script type="text/javascript">
    $("#more").click(function() {
        $("#moreRegion").hide(); // 천천히 보이기

    });
</script>
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

                  <a href="<c:url value='/user/menu' />" class="left-topbar-item"> 메인 </a> 
                  <a href="<c:url value='/user/table' />"class="left-topbar-item"> 내 시간표 </a>
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
                  <form name = "form" method="POST" action="<c:url value='/wish/search' />">
                      <h2 class="tm-block-title">강의 검색 
                         <select class="cate" name="cate">
	                        <option value="학과">학과</option>
	                        <option value="학수번호">학수번호</option>
	                        <option value="교수">교수</option>
	                        <option value="과목명">과목명</option>
                     </select> 
                     <input type="search" name="key" value=""> 
                     <input type="submit" value = "검색" onClick = "" > 
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
                                <th scope="col">분반</th>
                                <th scope="col">강의실</th>
                                <th scope="col">시간</th>
                                <th scope="col">담기</th>
                            </tr>
                        </thead>
                        <tbody>
                           <c:forEach var="sl" items="${searchList}">
                              <tr>
                                 <td>${sl.courseId}</td>
                                 <td>${sl.courseName}</td>
                                 <td>${sl.total}/${sl.enroll}</td>
                                 <td>${sl.pName}</td>
                                 <td>${sl.credit}</td>
                                 <td>${sl.classNum}</td>
                                 
                                 <c:choose>
                                    <c:when test="${(sl.classroom1 == sl.classroom2) and (sl.dayWeek1 == sl.dayWeek2) and (sl.time1 == sl.time2)}">
                                      <td>${sl.classroom1}</td>
                                      <td> ${sl.dayWeek1}${sl.time1}</td>
                                </c:when>
                                <c:otherwise>
                                   <td>${sl.classroom1}/${sl.classroom2}</td>
                                   <td> ${sl.dayWeek1}${sl.time1}/${sl.dayWeek2}${sl.time2}</td>
                                </c:otherwise>
                           </c:choose>
                                 <td><a href="<c:url value='/wish/search/insert'>
                                       <c:param name='c_id' value='${sl.courseId}'/>
                                       <c:param name='class_num' value='${sl.classNum}'/>
                                       <c:param name='wish_id' value='1'/>
                                    </c:url>"  class="btn">담기</a>
                                 </td>
                                 
                           
                              </tr>
                           </c:forEach> 
                        </tbody>
               </table>
                </div>
            </div>
            
            
            <div>
               <div class="">
                  <div class="row">
                     <form name = "form" method="POST" action="<c:url value='/user/wish' />">
                         <h2 class="tm-block-title">위시리스트 
                            <select class="wish_id" name="w_id">
                               <c:forEach var="wid" items="${wishIdList}">
                              		<option value="${wid}" <c:if test="${selectWishId == wid}">selected</c:if>>${wid}</option>
                           		</c:forEach>
                        </select>
                        <input type="submit" value = "검색">
                        <a href="<c:url value='/wish/insert'>
                        </c:url>" class="btn1">위시리스트 추가하기</a>
                        <a href="<c:url value='/wish/delete'>
                        </c:url>" class="btn1">위시리스트 삭제하기</a>
                     </h2>
                  </form>
                  <form class="m-t-20" name = "form" method="POST" action="<c:url value='/wish/finalwish' />">
                     최종위시설정 :
                     <select class="wish_id" name="fwish">
                            <c:forEach var="wid" items="${wishIdList}">
                           		<option value="${wid}" <c:if test="${selectFWishId == wid}">selected</c:if>>${wid}</option>
                        	</c:forEach>
                     </select>
                     <input type="submit" value = "설정" onClick = "" >
                  </form>
               </div>
               <form name = "form" method="POST" action="<c:url value='/wish/set_orders' />">
               <table class="table">
                       <thead>
                          <tr>
							<th scope="col">
                               	우선순위 
                               	<input type="submit" value = "확인" > 
          					</th> 
                               <th scope="col">학수번호</th>
                               <th scope="col">강의명</th>
                                <th scope="col">담은인원</th>
                                <th scope="col">교수</th>
                                <th scope="col">학점</th>
                                <th scope="col">분반</th>
                                <th scope="col">강의실</th>
                                <th scope="col">시간</th>
                                <th scope="col">삭제</th>
                            </tr>
                        </thead>
                        <tbody>
                           <c:forEach var="wl" items="${wishList}">
                              <tr id = "wishCount">
                              	 <td>
                              	 <c:if test="${wl.order ne 100}">
	                              	 <input class="order" type="number" name="orders" value="${wl.order}">
	                             </c:if>
	                             <c:if test="${wl.order eq 100}">
	                             	 <input class="order" type="number" name="orders" value="">
	                             </c:if>
	                             </td> 
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
                                 <td><a href="<c:url value='/wish/list/delete'>
                                       <c:param name='c_id' value='${wl.courseId}'/>
                                       <c:param name='class_num' value='${wl.classNum}'/>
                                       <c:param name='wish_id' value='1'/>
                                    </c:url>" class="btn1">삭제</a>
                                 </td>
                              </tr>
                           </c:forEach> 
                        </tbody>
               </table>
               </form>
                </div>
            </div>
              <c:if test="${insertFailed}">
            <script>alert("${exception}");</script>      
         </c:if> 
         	<div>
         	
               <div class="region3">
                      <h2 class="tm-block-title">위시시간표
                      </h2>
               </div>
               <div id="moreRegion">
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
                                    <td id ="tabletd1">
	                                    <c:forEach var="rl" items="${wishList}">
	                                   		<c:if test="${bool eq '1' }">
	                                   			<c:set var="bool" value="2"/>
	                                   			<br>
	                                   		</c:if>
	                                       <c:if test="${item eq rl.dayWeek1 and i eq rl.time1}">
	                                          ${rl.courseName}
	                                          <c:set var="bool" value="1"/>
	                                       </c:if>
	                                       <c:if test="${item eq rl.dayWeek2 and i eq rl.time2 and !((rl.dayWeek1 eq rl.dayWeek2) and (rl.time1 eq rl.time2))}">
	                                          ${rl.courseName}
	                                          <c:set var="bool" value="1"/>
	                                       </c:if>
	                                    </c:forEach>
	                                    <c:if test="${bool eq '0'}">
	                                    	&nbsp;
                                    	</c:if>
                                    </td>
                                 </c:forEach>
                              </tr>
                           </c:forEach> 
                        </tbody>
               </table>
               	</div>
            </div>
         	
         	
         	
         	
            <div>
               <div class="">
                  <form name = "form" method="POST" action="<c:url value='/wish/recommend' />">
                      <h2 class="tm-block-title">빈시간 강의추천 
                      <input type="submit" value = "검색">
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
                                <th scope="col">분반</th>
                                <th scope="col">강의실</th>
                                <th scope="col">시간</th>
                                <th scope="col">신청</th>
                            </tr>
                        </thead>
                        <tbody>
                           <c:forEach var="tl" items="${recList}">
                              <tr>
                                 <td>${tl.courseId}</td>
                                 <td>${tl.courseName}</td>
                                 <td>${tl.total}</td>
                                 <td>${tl.pName}</td>
                                 <td>${tl.credit}</td>
                                 <td>${tl.classNum}</td>
                                 <c:choose>
                                    <c:when test="${(tl.classroom1 == tl.classroom2) and (tl.dayWeek1 == tl.dayWeek2) and (tl.time1 == tl.time2)}">
                                      <td>${tl.classroom1}</td>
                                      <td> ${tl.dayWeek1}${tl.time1}</td>
                                </c:when>
                                <c:otherwise>
                                   <td>${tl.classroom1}/${tl.classroom2}</td>
                                   <td> ${tl.dayWeek1}${tl.time1}/${tl.dayWeek2}${tl.time2}</td>
                                </c:otherwise>
                           </c:choose>
                                 <td><a href="<c:url value='/wish/recommend/insert'>
                                       <c:param name='c_id' value='${tl.courseId}'/>
                                       <c:param name='class_num' value='${tl.classNum}'/>
                                       <c:param name='wish_id' value='1'/>
                                    </c:url>" class="btn">담기</a>
                                 </td>
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