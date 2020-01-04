<%@page contentType="text/html; charset=utf-8" %>
<%-- <%@page import="java.util.*, model.*" %> --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="utf-8">
<title>동덕여자대학교 수강신청</title>
<style>
  table {
    width: 100%;
    border: 1px solid #444444;
  }

#takes {
  
  pointer-events: none;
  text-decoration: none;
}
#top_takes {
  
  pointer-events: none;
  text-decoration: none;
}
</style>
<script>
var xmlHttp;
function srvTime(){
   var xmlHttp;
    try {
        xmlHttp = new XMLHttpRequest();
    }
    catch (err1) {
        try {
            xmlHttp = new ActiveXObject('Msxml2.XMLHTTP');
        }
        catch (err2) {
            try {
                xmlHttp = new ActiveXObject('Microsoft.XMLHTTP');
            }
            catch (eerr3) {
                 alert("AJAX not supported");
            }
        }
    }

    xmlHttp.open('HEAD',window.location.href.toString(),false);
    xmlHttp.setRequestHeader("Content-Type", "text/html");
    xmlHttp.send('');
    var st =  xmlHttp.getResponseHeader("Date");
    var date = new Date(st);
    
    var h = date.getHours();
    var m = date.getMinutes();
    var s = date.getSeconds();
    var timecheck = false;
    
    var check_h = 15;
    var check_m = 17;
    var check_s = 0
    //시 가 클때
    if (h > check_h){ //시
    	timecheck=true;
    }
    //같은 시 다른 분일때
    else if(h == check_h) {//시
    	if (m >= check_m){//분
    		if(s >= check_s){//초
    			timecheck=true;
    		}
    	}
    }
    if (timecheck == true){
    	document.getElementById('takes').style.pointerEvents  = "auto";
	    document.getElementById('top_takes').style.pointerEvents  = "auto";
	       
	    document.getElementById('timecheck').innerHTML = "　　　　";
    }
    else{
    	
       //var btn2 = document.getElementById('takes');
       //btn2.style.pointer-events = auto;
       
       //document.getElementById('top_takes').style.pointer-events = auto;
       
       document.getElementById('timecheck').innerHTML = "수강 신청 기간이 아닙니다.";

    }

    m = checkTime(m);
    s = checkTime(s);
    
    document.getElementById('clock').innerHTML =
       h + ":" + m + ":" + s;
    setTimeout(srvTime, 1000);
 }

function checkTime(i) {
    if (i < 10) {i = "0" + i;} 
    return i;
}




</script>

</head>
<link rel=stylesheet href="<c:url value='/css/main.css' />" type="text/css">
<link rel=stylesheet href="<c:url value='/css/topbar.css' />" type="text/css">
<body onload="srvTime();">
   <!-- Header -->
  <header>
      <!-- Header desktop -->
      <div class="container-menu-desktop">
         <div class="topbar top">
            <div class="content-topbar container">
               <div class="left-topbar">

                  <a href="<c:url value='/user/menu' />" class="left-topbar-item"> 메인 </a> 
                  <a href="<c:url value='/user/takes' />" class="left-topbar-item" id = "top_takes"> 수강신청 </a> 
                  <a href="<c:url value='/user/wish' />" class="left-topbar-item"> 위시리스트 </a> 
                  <a href="<c:url value='/user/table' />"class="left-topbar-item"> 내 시간표 </a>
               </div>
               <div class="right-topbar">
                  <a href="<c:url value='/user/logout' />"  class="right-topbar-item"> logout </a>
               </div>
            </div>
         </div>
      </div>
   </header>
   
   
   <section class="p-rl-33">
      <div class="right p-t-33">
      
         <h2 class="f1-l-3 cl2 p-b-16 respon2 p-rl-33">동덕여자대학교 수강신청</h2>
         <div id = "timecheck" style="color:red"  align="center"></div>
         <div class="custom-btn-group p-rl-33">
            <a href="<c:url value='/user/takes' />" class="btn custom-btn custom-btn-bg" id = "takes" >수강신청</a> <a
               href="<c:url value='/user/wish' />" class="btn custom-btn custom-btn-bg">위시리스트</a> <a
               href="<c:url value='/user/table' />" class="btn custom-btn custom-btn-bg">내 시간표</a>
         </div>
      </div>
      <div class="left p-t-33">
      <table>
         <h1><div id="clock" ></div></h1>
      </table>
         <h3 class="f1-m-2 cl3 tab01-title">내 정보</h3>
         <p>이름: ${studentInfo.name}</p>
         <p>학번: ${studentInfo.stu_id}</p>
         <p>학점: ${studentInfo.availCredit}</p>
         <p>학기: ${studentInfo.regiSeme}</p>
         <p>학과: ${studentInfo.dept}</p>
         
      </div>
      
   </section>

</body>
</html>