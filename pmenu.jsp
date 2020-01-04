<%@page contentType="text/html; charset=utf-8" %>
<%-- <%@page import="java.util.*, model.*" %> --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="utf-8">
<title>동덕여자대학교 수강신청</title>
</head>
<link rel=stylesheet href="<c:url value='/css/main.css' />" type="text/css">
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
						<a href="<c:url value='/user/pwish' />" class="left-topbar-item"> 위시리스트 </a> 
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

			<div class="custom-btn-group p-rl-33">
				<a href="<c:url value='/user/ptakes' />" class="btn custom-btn custom-btn-bg">수강신청</a> <a
					href="<c:url value='/user/pwish' />" class="btn custom-btn custom-btn-bg">위시리스트</a>
			</div>
		</div>
		<div class="left p-t-33">
			<h3 class="f1-m-2 cl3 tab01-title">내 정보</h3>
			<p>아이디: ${profInfo.prof_id}</p>
			<p>이름: ${profInfo.name}</p>
			<p>학과: ${profInfo.dept}</p>
			
		</div>
	</section>
</body>
</html>