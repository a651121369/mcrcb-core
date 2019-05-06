<%@page import="com.unteck.tpc.framework.core.util.SecurityContextUtil"%>
<%@page import="com.unteck.tpc.framework.core.support.ProfileApplicationContextInitializer"%>
<%@page import="java.awt.Toolkit"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<html>
  <head>
  <title>蒙城卫健委财政专户资金管理系统</title>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/resources/js/ext/resources/css/ext-all.css" />
    <script type="text/javascript" src="${basePath}/resources/js/ext/ext-base.js"></script>
    <script type="text/javascript" src="${basePath}/resources/js/ext/ext-all.js"></script>
    <script type="text/javascript" src="${basePath}/resources/js/ext/ext-lang-zh_CN.js"></script>
    
    <link rel="stylesheet" type="text/css" href="${basePath}/resources/css/ext/tab-scroller-menu.css" />
	<link rel="stylesheet" type="text/css" href="${basePath}/resources/css/icons.css" />
	<link rel="stylesheet" type="text/css" href="${basePath}/resources/css/index.css" />
	<script type="text/javascript" src="${basePath}/resources/js/ux/Ext.ux.TabScrollerMenu.js"></script>
	<script type="text/javascript" src="${basePath}/resources/js/ux/ST.ux.util.js"></script>
	<script type="text/javascript" src="${basePath}/resources/js/ux/Ext.ux.AccordionPanel.js"></script>
	<script type="text/javascript" src="${basePath}/resources/js/ux/Ext.ux.TreeCombo.js"></script>
	<script type="text/javascript" src="${basePath}/resources/js/ux/ST.ux.ExtField.js"></script>
	<script type="text/javascript" src="${basePath}/resources/js/ux/uxVtypes.js"></script>
	<script type="text/javascript" src="${basePath}/resources/js/admin/ST.Base.Home.js"></script>
	<link href="${basePath}/resources/css/jc_css.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${basePath}/resources/login/js/jquery.js"></script>
	<style type="text/css">
	#ext-gen10{margin:0;padding:0;}
	.x-panel-mr{padding:0;margin:0;}
	.x-panel-ml{padding:0;margin:0;}
	</style>
	<style type="text/css">
	.x-tree .x-panel-header .x-panel-header-text{
		font-size:14px;font-weight:bold;color:009843;
	}
	.x-tool-rollup,.x-tool-toggle{
		margin-top:5px;
	}
	</style>
	<!--[if IE 6]>
	<script src="${basePath}/resources/js/EvPNG.js" type="text/javascript"></script>
	<script type="text/javascript">
	   EvPNG.fix('.top_logo, img,.top_ricon_a,.top_ricon_b,.top_ricon_c');
	</script>
	<![endif]-->
  </head>
  <body style="min-width:1000px;min-height:600px;">
   <script type="text/javascript"> 
//     function getCurrentTime(){
//     	return new Date().toLocaleString();
//     }; 
//     setInterval("curTime.innerHTML=getCurrentTime();",1000);
		Date.prototype.format = function(format){ 
	   var o = { 
	   "M+" : this.getMonth()+1, //month 
	   "d+" : this.getDate(), //day 
	   "h+" : this.getHours(), //hour 
	   "m+" : this.getMinutes(), //minute 
	   "s+" : this.getSeconds(), //second 
	   "q+" : Math.floor((this.getMonth()+3)/3), //quarter 
	   "S" : this.getMilliseconds() //millisecond 
	   } 

	   if(/(y+)/.test(format)) { 
	   format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
	   } 

	   for(var k in o) { 
	   if(new RegExp("("+ k +")").test(format)) { 
	   format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
	   } 
	   } 
	   return format; 
	   } 

    function getCurrentTime(){

    	//alert(new Date().format("yyyy-MM-dd hh:mm:ss"));
    	return new Date().format("MM月dd hh:mm:ss")
		
    	//return new Date().toLocaleString();
    }; 
    </script>
    <script type="text/javascript">
$(document).ready(function(){
	$("#aaa").width($(window).width() - 1224);
});
</script>
  	<input type="hidden" id="casPorfile" value="<%=ProfileApplicationContextInitializer.casProfile %>" />
  	<input type="hidden" id="CONTEXT_PATH" value="<%= request.getContextPath() %>" />
 <div style="display: none;">

	   <div id="app-header" >
<!-- 	   		<div> -->
             <!-- <embed id="topFlash" src="/resources/flash/top.swf" width="1224" height="60" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" wmode="transparent"></embed> -->
<!--              <img src="/resources/images/top_logo1.jpg"/> -->
<!--             </div> -->
<!-- 	        <div class="top_b" style="position: absolute;top:15px;right:0px;z-index:999;"> -->
	        <div class="top_b">
	        			<div class="top_logo_1"></div>
			    <div class="top_r">
			     	   <p><span>您好,<b><%= SecurityContextUtil.getCurrentUser().getUserName() %></span> 今天是:<span id="curTime"><script>curTime.innerHTML=getCurrentTime();</script></span></b></p>
			       	   <div class="top_ricon">
				            <ul>
				                <li class="top_ricon_a"><a href="#" onClick=Home.OpenOnLineWin();>在线</a></li>
				                <li class="top_ricon_b"><a href="#" onClick=ST.base.PersonConfig.showWin("personConfig");>个人设置</a></li>
				                <li class="top_ricon_c"><a href="#" onClick=Home.Logout();>退出</a></li>
				            </ul>
				        </div>
				    </div>
			 </div>
	        <!-- top end -->
      
	    </div> 
	    <div id="home-panel">
	        <p>主页内容</p>
	    </div>
    </div>
    <script type="text/javascript">
    function selectorWin(){
    	document.getElementById("contentIframe").contentWindow.selectorWin();
    }
    
    </script>
    <script type="text/javascript" src="${basePath}/resources/js/ux/Ext.ux.TabCloseMenu.js"></script>
	<script type="text/javascript" src="${basePath}/resources/js/admin/ST.Base.PersonConfig.js"></script>
  </body>
</html>