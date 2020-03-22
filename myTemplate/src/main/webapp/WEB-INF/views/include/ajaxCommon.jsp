<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script>
var ajax = {
	    stackLoading : 0,
	    post : function(url, param, fnCallback) {
	         return $.post(url, param, fnCallback);
	    }
	    , get : function(url, param, fnCallback, doAsync) {

	            let setAsync = true;
	
	            if(doAsync==false) {
	                setAsync = doAsync;
	            }
	            return $.ajax({
	                url: url,
	                data: param,
	                type: "get",
	                async : setAsync, // false면 순차적 실행
	                contentType: "application/html; charset=UTF-8",
	                success: function(data) {
	                    fnCallback(data);
	                }
	            });
	    }
	    , json : function(url, param, fnCallback, doAsync) {

                let setAsync = true;

	            if(doAsync==false) {
	                setAsync = doAsync;
	            }

	            if( $.type(param) == 'object' ) {
	                param = JSON.stringify(param);
	            }

	            return $.ajax({
	                url : url,
	                data : param,
	                type : 'post',
	                dataType : 'json',
	                async : setAsync, // false면 순차적 실행
	                contentType : "application/json; charset=UTF-8",
	                success : function(data) {
	                    if( data && data.exceptionMsg ){
	                        simpleAlert(data.exceptionMsg, 'error');
	                    } else {
	                        try {
	                            fnCallback(data);
	                        } catch(e) {
	                        	simpleAlert('<spring:message code="common.message.unknownErrorOccurred"/>', 'error');
	                        }
	                    }
	                }
	            });
	        }
	    , updateDiv : function(url, param, divId, fnCallback, doAsync) {

            let setAsync = true;

            if(doAsync==false) {
                setAsync = doAsync;
            }

	        return $.ajax({
	            url : url,
	            data : param,
	            type : 'post',
	            async : setAsync,
	            //contentType : "application/html; charset=UTF-8",
	            success : function(strHtml) {
	                $('#' + divId).html(strHtml);
	                try {
	                    if( fnCallback ) {
	                        fnCallback();
	                    }
	                } catch(e) {
	                	simpleAlert('<spring:message code="common.message.unknownErrorOccurred"/>', 'error');
	                }
	            }
	        });
	    }
	}
</script>