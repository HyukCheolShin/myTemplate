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
       <input type="text" id="userId" name="userId" placeholder="<spring:message code='common.text.id'/>"  maxlength="20" tabindex ="1">
       <input type="password" id="userPassword" name="userPassword" placeholder="<spring:message code='common.text.password'/>"  maxlength="20" tabindex ="2" AUTOCOMPLETE="OFF">
       <button onclick="doLogin()"><spring:message code='common.text.login'/></button>
   </form>

<script>

    var inputText = "<spring:message code='common.message.inputText'/>";    // {0}을(를) 입력하여 주십시오.

    // sessionUserId가 존재하는 경우 로그인이 된 경우이므로 메인페이지로 이동
    if("${sessionUserId}"!=="") {
        top.location.href = '<c:url value="/loginConfirm.do" />';
    }

    function check_validation() {
        form = document.loginForm;

        if (form.userId.value == "") {
            alert(inputText.replace("{0}", '<spring:message code="common.text.id"/>'));
            return false;
        }

        if (form.userPassword.value == "") {
            alert(inputText.replace("{0}", '<spring:message code="common.text.password"/>'));
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
