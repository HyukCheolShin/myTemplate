<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>
	<title>MyTemplate Main</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<jsp:include page="/common/header.do" />

</head>
<body>
    <a href="#" id="logout">logout</a>
</body>

<jsp:include page="/common/jsCommon.do" />
<jsp:include page="/common/ajaxCommon.do" />

<script>
$(document).ready(function() {
    $("#logout").click(function(e){
        e.preventDefault();

        top.location.href = '<c:url value="/logout.do"/>';
    });
});
</script>
</html>