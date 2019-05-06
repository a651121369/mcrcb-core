<%@page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
	<title>错误信息</title>
	<!--[if IE 6]>
		<script src="/resources/js/EvPNG.js" type="text/javascript"></script>
		<script type="text/javascript">
		   EvPNG.fix('.login_2,.textbox_yhm,.textbox_mm,img,background');
		</script>
	<![endif]-->
</head>
<body>
	<script type="text/javascript">
		var errorCode = '${ERROR_CODE}';
		var errorMessage = '${ERROR_MESSAGE}';
		var errorRemark = '${ERROR_REMARK}';
		if(errorCode == 'PARAM_FORMAT_ERROR_DATE') {
			if(!errorMessage) {
				errorMessage = '日期格式错误';
			}
		} else if (errorCode == 'PARAM_FORMAT_ERROR_DATE_SD'){
			if(!errorMessage) {
				errorMessage = '开始日期不能大于结束日期';
			}
		} else if (errorCode == 'PARAM_FORMAT_ERROR_DATE_MAX'){
			if(!errorMessage) {
				errorMessage = '只能查询昨天及以前的数据';
			}
		} else {
			errorMessage = '未知错误, 请联系系统管理员!';
		}
		errorMessage += '\n' + errorRemark;
		alert(errorMessage);
		if(window.top != window.self) {
			window = window.top;
		}
		window.close();
	</script>
</body>
</html>