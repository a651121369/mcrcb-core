<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/resources/js/ext/resources/css/ext-all.css" />
    <script type="text/javascript" src="${basePath}/resources/js/ext/ext-base.js"></script>
    <script type="text/javascript" src="${basePath}/resources/js/ext/ext-all.js"></script>
    <script type="text/javascript" src="${basePath}/resources/js/ext/ext-lang-zh_CN.js"></script>
    
	<link rel="stylesheet" type="text/css" href="${basePath}/resources/css/icons.css" />
	<link rel="stylesheet" type="text/css" href="${basePath}/resources/css/index.css" />
	<script type="text/javascript" src="${basePath}/resources/js/ux/Ext.ux.TreeCombo.js"></script>
	<script type="text/javascript" src="${basePath}/resources/js/ux/ST.ux.util.js"></script>
	<script type="text/javascript" src="${basePath}/resources/js/ux/ST.ux.ExtField.js"></script>
	<script type="text/javascript" src="${basePath}/resources/js/ux/Ext.ux.PagePlugins.js"></script>
	<script type="text/javascript" src="${basePath}/resources/js/ux/ST.ux.PlainGrid.js"></script>
	<script type="text/javascript" src="${basePath}/resources/js/ux/ST.ux.ViewGrid.js"></script>
	
	<script type="text/javascript" src="${basePath}/resources/js/admin/ST.Base.User.js"></script>
	<script type="text/javascript">
		Ext.onReady(function(){
		    new ST.base.userView();
		});
	</script>
  </head>
  <body>
  </body>
</html>