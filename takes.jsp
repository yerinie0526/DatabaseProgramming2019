<%@page contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<%request.setCharacterEncoding("UTF-8");%>

<html>
<head>
<meta charset="utf-8">

<title>동덕여자대학교 수강신청</title>
<script>
function search(){
   if (form_search.key.value == "") {
      alert("검색어를 입력하세요.");
      form_search.key.focus();
      return false;
   }      
   form_search.submit();
}

</script>
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
.order{
	width: 80%;
}
#s_table, #fw_table, #r_table {
   text-align: center;
   width: 100%;
}

#style {
   border-style: solid;
   border-collapse:collapse;
   width: 150px;
}
#btn1 {
   display: inline-block;
   font-weight: 400;
   color: #212529;
   text-align: center;
   vertical-align: middle;
   -webkit-user-select: none;
   -moz-user-select: none;
   -ms-user-select: none;
   user-select: none;
   background-color: transparent;
   border: 1px solid transparent;
   padding: .375rem .75rem;
   font-size: 1rem;
   line-height: 1.5;
   transition: color .15s ease-in-out, background-color .15s ease-in-out,
      border-color .15s ease-in-out, box-shadow .15s ease-in-out;
   text-decoration: none;
}
}
</style>
<link rel=stylesheet href="<c:url value='/css/topbar.css' />" type="text/css">

<body>
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
<section style = "padding-top: 30px">

<form name = "form_search" method="POST" accept-charset="utf-8" action="<c:url value='/takes/search' />">
<h2 class="tm-block-title">강의 검색 
                         <select class="cate" name="cate">
	                        <option value="학과">학과</option>
	                        <option value="학수번호">학수번호</option>
	                        <option value="교수">교수</option>
	                        <option value="과목명">과목명</option>
                     </select> 
                    <input type = "text" name = "key">
      <input type = "submit" value = "검색" onclick = "search()">
                  </h2>
</form>



<table style="text-align:center" id = "s_table">
   <tr>
      <th>학수번호&nbsp;</th>
      <th>과목명&nbsp;</th>
      <th>분반&nbsp;</th>
      <th>학점&nbsp;</th>
      <th>교실&nbsp;</th>
      <th>시간&nbsp;</th>
      <th>교수&nbsp;</th>
      <th>현재수강인원&nbsp;</th>
      <th>최대수강인원&nbsp;</th>
      <th>신청&nbsp;</th>
      
   </tr>

   <tr>
    <c:forEach var="search" items="${searchList}">                
        <tr>
        <td id = "style"> ${search.courseId}&nbsp; </td>
        <td id = "style">&nbsp; ${search.courseName}&nbsp; </td>
        <td id = "style"> &nbsp;${search.classNum}&nbsp; </td>
        <td id = "style">&nbsp; ${search.credit}&nbsp; </td>
        <c:choose>
            <c:when test="${(search.classroom1 == search.classroom2) and (search.dayWeek1 == search.dayWeek2) and (search.time1 == search.time2)}">
            <td>${search.classroom1}</td>
            <td> ${search.dayWeek1}${search.time1}</td>
         </c:when>
         <c:otherwise>
            <td id = "style">&nbsp;${search.classroom1}/${search.classroom2}&nbsp;</td>
            <td id = "style"> &nbsp;${search.dayWeek1}${search.time1}/${search.dayWeek2}${search.time2}&nbsp;</td>
         </c:otherwise>
      </c:choose>
        <td id = "style"> &nbsp;${search.pName}&nbsp;</td>
        <td id = "style">&nbsp;${search.total}&nbsp;</td>
        <td id = "style">&nbsp;${search.enroll }&nbsp;</td>
        <td id = "style">
          <a href = "<c:url value='/takes/search/insert'>
             <c:param name= 'courseId' value = '${search.courseId}'/>
             <c:param name = 'classNum' value = '${search.classNum}'/>
             </c:url>"  id ="btn1" onclick="alert('수강 신청 하겠습니까?');return true;">신청
          </a>

       </td>
       
      </tr>
     </c:forEach> 

     </tr>
</table>


<h3>최종 위시리스트</h3>
<table style="text-align:center" id = "fw_table">
   <tr>
      <th>NO&nbsp;</th>
      <th>학수번호&nbsp;</th>
      <th>과목명&nbsp;</th>
      <th>분반&nbsp;</th>
      <th>학점&nbsp;</th>
      <th>교실&nbsp;</th>
      <th>시간&nbsp;</th>
      <th>교수&nbsp;</th>
      <th>최대수강인원&nbsp;</th>
      <th>신청&nbsp;</th>
      
      
   </tr>
   <c:set var="c" value = "0"/>
   <c:forEach var="fwish" items = "${wishList}">
   <tr>
      <c:set var = "c" value="${c+1}"/>
      <td id = "style">${c}&nbsp;</td>
      <td id = "style"> &nbsp;${fwish.courseId}&nbsp;</td>                                                                                                                                                             
      <td id = "style">&nbsp; ${fwish.courseName}&nbsp; </td>
       <td id = "style"> &nbsp;${fwish.classNum}&nbsp; </td>
       <td id = "style">&nbsp; ${fwish.credit}&nbsp; </td>
       <c:choose>
            <c:when test="${(fwish.classroom1 == fwish.classroom2) and (fwish.dayWeek1 == fwish.dayWeek2) and (fwish.time1 == fwish.time2)}">
            <td id = "style">&nbsp;${fwish.classroom1}&nbsp;</td>
            <td id = "style"> &nbsp;${fwish.dayWeek1}${fwish.time1}&nbsp;</td>
         </c:when>
         <c:otherwise>
            <td id = "style">&nbsp;${fwish.classroom1}/${fwish.classroom2}&nbsp;</td>
            <td id = "style"> &nbsp;${fwish.dayWeek1}${fwish.time1}/${fwish.dayWeek2}${fwish.time2}&nbsp;</td>
         </c:otherwise>
      </c:choose>
   
       <td id = "style"> ${fwish.pName }&nbsp;</td>
       <td id = "style">${fwish.enroll }</td>
       <td id = "style">
          <a href = "<c:url value='/takes/wish/insert'>
             <c:param name= 'courseId' value = '${fwish.courseId}'/>
             <c:param name = 'classNum' value = '${fwish.classNum}'/>
             </c:url>"  id ="btn1" onclick="alert('수강 신청 하겠습니까?');return true;">신청
          </a>
       </td>
      </tr>
   </c:forEach>
             
</table>
  <c:if test="${insertFailed}">
<script>alert("${exception}");</script>      
</c:if> 
<h3>수강신청 결과</h3>
<table style="text-align:center" id = "r_table">
   <tr>
      <th>학수번호&nbsp;</th>
      <th>과목명&nbsp;</th>
      <th>분반&nbsp;</th>
      <th>학점&nbsp;</th>
      <th>교수&nbsp;</th>
      <th>교실&nbsp;</th>
      <th>시간&nbsp;</th>
      <th>현재수강인원&nbsp;</th>
      <th>최대수강인원&nbsp;</th>
      <th>삭제&nbsp;</th>
      
   </tr>
   
   <c:forEach var="result" items = "${takesList}">
   <tr>
      <td id = "style"> ${result.courseId}&nbsp;</td>                                                                                                                                                             
      <td id = "style">&nbsp;${result.courseName} &nbsp;</td>
       <td id = "style">&nbsp; ${result.classNum} &nbsp;</td>
       <td id = "style"> &nbsp;${result.credit}&nbsp; </td>
       <td id = "style"> &nbsp;${result.pName }&nbsp;</td>
        <c:choose>
            <c:when test="${(result.classroom1 == result.classroom2) and (result.dayWeek1 == result.dayWeek2) and (result.time1 == result.time2)}">
            <td id = "style">&nbsp;${result.classroom1}&nbsp;</td>
            <td id = "style"> &nbsp;${result.dayWeek1}${result.time1}&nbsp;</td>
         </c:when>
         <c:otherwise>
            <td id = "style">&nbsp;${result.classroom1}/${result.classroom2}&nbsp;</td>
            <td id = "style"> &nbsp;${result.dayWeek1}${result.time1}/${result.dayWeek2}${result.time2}&nbsp;</td>
         </c:otherwise>
      </c:choose>
       <td id = "style">${result.total}&nbsp;</td>
       <td id = "style">${result.enroll }
       <td id = "style">
          <a href = "<c:url value='/takes/list/delete'>
             <c:param name= 'courseId' value = '${result.courseId}'/>
             <c:param name = 'classNum' value = '${result.classNum}'/>
             </c:url>"  id ="btn1" onclick="alert('수강 삭제 하겠습니까?');return true;">삭제
          </a>
       </td>
       </tr>
   </c:forEach>
   
</table>


</section>
</body>
</html>