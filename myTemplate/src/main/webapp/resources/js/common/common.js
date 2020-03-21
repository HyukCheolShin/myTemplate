function simpleAlert(strMsg, strType, strAlign, intWidth){

	let setTitle = ' ';
    let setIcon = 'fa fa-info-circle';
    let setColor = 'orange';

    if(convertUndefinedToString(strMsg)==""){
        $.alert({
            title : setTitle,
            content : '<center>Please Enter a Message.</center>',
            type : 'red',
            icon: 'fa fa-exclamation-triangle',
            useBootstrap : false,
            boxWidth : '400px'
        });

        return false;
    }

    if(convertUndefinedToString(strAlign)!="") {
        if(strAlign=="left") {
            strMsg = '<left>' + strMsg + '</left>'
        } else {
            strMsg = '<center>' + strMsg + '</center>'
        }
    } else {
        strMsg = '<center>' + strMsg + '</center>'
    }

    if(convertUndefinedToString(intWidth)=="") {
        intWidth = 400;
    }

    if(convertUndefinedToString(strType).toUpperCase()=="ERROR") {
        setColor = 'red';
        setIcon = 'fa fa-exclamation-triangle';
    } else if(convertUndefinedToString(strType).toUpperCase()=="SUCCESS") {
        setIcon = 'fa fa-smile';
        setColor = 'green';
    }

    $.alert({
        title : setTitle,
        content : strMsg,
        type : setColor,
        icon : setIcon,
        useBootstrap : false,
        boxWidth : intWidth + 'px'
    });
}