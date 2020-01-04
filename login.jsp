<%@page contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>동덕여자대학교 수강신청</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel=stylesheet href="<c:url value='/css/user.css' />" type="text/css">
<script>
function login() {
	if (form.userId.value == "") {
		alert("사용자 ID를 입력하십시오.");
		form.userId.focus();
		return false;
	} 
	if (form.password.value == "") {
		alert("비밀번호를 입력하십시오.");
		form.password.focus();
		return false;
	}		
	form.submit();
}


</script>
</head>
<body>
<h2>동덕여자대학교 수강신청 LOGIN</h2>
<form name = "form" method="POST" action="<c:url value='/user/login' />">

<table>
	<tr>
		<td>
			<select name="cate">
			    <option value="student">학생</option>
			    <option value="prof">교수</option>
			</select>
		</td>
	</tr>
	<c:if test="${loginFailed}">
	  	  <br><font color="red"><c:out value="${exception.getMessage()}" /></font><br>
	</c:if>
	<tr>
		<td>ID</td>
		<td><input type = "text" name = "userId" style = "width:150px;"></td>
		<td rowspan = "2"><input type = "submit" value = "로그인" onClick = "login()" ></td>
	</tr>
	<tr>
		<td>PW</td>
		<td><input type = "password" name = "password" style = "width:150px;"></td>
	</tr>
</table>
</form>
</body>

</html>