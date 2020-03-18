<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
	<title>MyTemplate Login</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
   <form id="loginForm" name="loginForm">
       <input type="text" placeholder='아이디' name="userId" maxlength="20" tabindex ="1">
       <input type="password" placeholder='비밀번호' name="userPassword" maxlength="20" tabindex ="2" AUTOCOMPLETE="OFF">
       <button onclick="doLogin()">로그인</button>
   </form>

<script>

    function check_validation() {
        form = document.loginForm;

        if (form.userId.value == "") {
               alert("userId를 입력하세요.");	
            return false;
        }

        if (form.userPassword.value == "") {
        	alert("비밀번호를 입력하세요.");
            return false;
        }
        return true;
    }

    function doLogin() {

        if(check_validation() == false) {
            return false;
        }

        form = document.loginForm;
        form.method = "post";
        form.action = "<c:url value='/login.do'/>";
        form.submit();
    }

</script>
</body>
</html>
