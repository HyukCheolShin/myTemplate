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

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.4/jquery-confirm.min.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.12.1/css/all.css" crossorigin="anonymous">

</head>
<body>
<form id="loginForm" name="loginForm">
    <input type="text" id="userId" name="userId" placeholder="<spring:message code='common.text.id'/>"  maxlength="20" tabindex ="1">
    <input type="password" id="userPassword" name="userPassword" placeholder="<spring:message code='common.text.password'/>"  maxlength="20" tabindex ="2" AUTOCOMPLETE="OFF">
    <button id="login"><spring:message code='common.text.login'/></button>
</form>

<script src="https://code.jquery.com/jquery-3.4.1.min.js" integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.4/jquery-confirm.min.js"></script>
<script type="text/javascript" src="/resources/js/common/common.js"></script>
<script type="text/javascript" src="/resources/js/common/util.js"></script>

<script>

    var inputText = "<spring:message code='common.message.inputText'/>";    // {0}을(를) 입력하여 주십시오.

    // sessionUserId가 존재하는 경우 로그인이 된 경우이므로 메인페이지로 이동
    if("${sessionUserId}"!=="") {
        top.location.href = '<c:url value="/loginConfirm.do" />';
    }

    $(document).ready(function() {

        function check_validation() {
            form = document.loginForm;

            if (form.userId.value == "") {
            	simpleAlert(inputText.replace("{0}", '<spring:message code="common.text.id"/>'));
                return false;
            }

            if (form.userPassword.value == "") {
            	simpleAlert(inputText.replace("{0}", '<spring:message code="common.text.password"/>'));
                return false;
            }
            return true;
        }

    	$("#login").click(function(e){
    		e.preventDefault();

            if(check_validation() == false) {
                return false;
            }

            $.ajax({
                url : '<c:url value="/" />loginUserValidation.json',
                data : JSON.stringify($('#loginForm').serializeForm()),
                type : 'post',
                dataType : 'json',
                contentType : "application/json; charset=UTF-8",
                success : function(data) {
                	console.log(JSON.stringify(data,null,4));
                    if(data.returnCd == "Y") {
                        form = document.loginForm;
                        form.method = "post";
                        form.action = "<c:url value='/login.do'/>";
                        form.submit();
                    } else if(data.returnCd == "NOT_FOUND_USER") {
                    	simpleAlert('<spring:message code="login.message.userIdNotFound"/>', 'error');
                    	return false;
                    } else {
                    	simpleAlert('<spring:message code="login.message.loginFailed"/>', 'error');
                    	return false;
                    }
                }
            });
    	})
    });
</script>
</body>
</html>
