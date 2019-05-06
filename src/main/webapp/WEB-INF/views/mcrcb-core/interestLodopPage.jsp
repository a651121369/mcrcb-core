<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/resources/js/ext/resources/css/ext-all.css" />
    
	<link rel="stylesheet" type="text/css" href="${basePath}/resources/css/index.css" />
	
	<script type="text/javascript" src="${basePath}/resources/js/ext/ext-base.js"></script>
    <script type="text/javascript" src="${basePath}/resources/js/ext/ext-all.js"></script>
    <script type="text/javascript" src="${basePath}/resources/js/ext/ext-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${basePath}/resources/js/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="${basePath}/resources/js/util/dateFormat.js"></script>
	<script type="text/javascript" src="${basePath}/resources/js/util/LodopFuncs.js"></script>
	<script type="text/javascript" src="${basePath}/resources/js/mcrcb-core/interestLodopPage.js"></script>
    
	<style type="text/css">
	{font-size:12pt;border:0;margin:0;padding:0;}
			body{font-family:'微软雅黑'; margin:0 auto;min-width:980px;}
			ul{display:block;margin:0;padding:0;list-style:none;}
			li{display:block;margin:0;padding:0;list-style: none;}
			img{border:0;}
			dl,dt,dd,span{margin:0;padding:0;display:block;}
			a,a:focus{text-decoration:none;color:#000;outline:none;blr:expression(this.onFocus=this.blur());}


	table td{word-break: keep-all;white-space:nowrap;}
	.header{width: 1024px;
	        
	}
	span{float: left;}
	 .container{width: 1024px;
	            margin: 0 auto;
	            overflow: hidden;
	            margin-top: 10px;}
	.text{text-align:center;margin-bottom: 5px;}
	</style>
	<object id="LODOP_OB"
		classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0>
		<embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
	</object>
  </head>
  <body>
 	 	<div align="right">	 	
 	 		<a href="javascript:print();">打印</a>
 			 <a href="javascript:myPrint1();">打印预览</a>
<!-- 			<a href="javascript:printSetup();">打印维护</a> -->
<!-- 			<a href="javascript:printDesign();">打印设计</a> -->
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</div>
  	<input type="hidden" id="id" value="${id}" />
<div class="container">
  
	<div class="header">
		     <div style="width:1024px;">
		     <p style="display: block;
			        	float: right;font-size:20px;font-weight:bold;margin-bottom:-100px;width:px;">进账单（回&nbsp;&nbsp;&nbsp;&nbsp;单）</p>
				 <img src="${basePath}/resources/images/app_logo.png" alt="" id="logo" style="margin-bottom:0px;display: block;" >
			
		     </div>

	 
			 <HR WIDTH=1024px; SIZE=3 ALIGN=LEFT NOSHADE style="margin-left:50px;">
			 <p style="margin-left:60px;margin-top:-5px;margin-bottom:20px;">ANHI RURAL CREDIT UNION</p>
	 </div>
<!-- 			<div class="text">年 &nbsp;&nbsp;&nbsp;&nbsp; 月  &nbsp;&nbsp;&nbsp;&nbsp;日</div> -->
			<div style="margin-top:10px;margin-right:20px;margin-bottom: 5px;margin-left:300px;">
			  <span style="padding-left:40%;" id="year"></span><span style="padding-left:1%;">年</span>	  
			  <span style="padding-left:1%;" id="month"></span>
			  <span style="padding-left:1%;">月</span>
			  <span style="padding-left:1%;" id="day"></span>
			  <span style="padding-left:1%;">日</span>
	  		  <span style="padding-left:10%;">凭证号：</span>
	  		  <span style="padding-left:1%;" id="voucher"></span>
			</div>

	   <table width="1024" height="350" border="2" cellpadding="0" cellspacing="0" class="tb" style="word-break:break-all;table-layout:fixed;word-break:break-all; "  >  
	   
	   <tr>
	   	<td  rowspan="3"colspan="4" align="center" valign="middle">出<br>票<br>人</td>
	   	<td colspan="10" align="center" valign="middle">全称</td>
	   	<td id="outAccname" colspan="38"></td>
	   	<td rowspan="3"colspan="4" align="center" valign="middle">收<br>款<br>人</td>
	   	<td colspan=" 10" align="center" valign="middle">全称</td>
	   	<td id="inAccname" colspan="38"></td>
	   	
	   
	   
	   </tr>
	   <tr>
	   	<td colspan="10" align="center" valign="middle">帐号</td>
	   	<td id="outAccno" colspan="38"></td>
	   	<td colspan="10" align="center" valign="middle">帐号</td>
	   	<td id="inAccno" colspan="38"></td>
	   
	   	
	   	
	   	
	   </tr>
	   <tr>
	   	<td colspan="10" align="center" valign="middle">开户银行</td>
	   	<td id="outBank" colspan="38"></td>
	   	<td colspan="10" align="center" valign="middle">开户银行</td>
	   	<td id="inBank" colspan="38"></td>
	   	
	   
	   	
	   </tr>
	   <tr>
	   	<td rowspan="2"colspan="4" align="center" valign="middle">金<br>额</td>
	   	<td colspan="10" rowspan="2" align="center" valign="middle">人民币<br> (大写)</td>
	   	<td id="amountD" colspan="57" rowspan="2"></td>
	   	

	   	<td align="center" valign="middle"colspan="3">亿</td>
	   	<td align="center" valign="middle"colspan="3">千</td>
	   	<td align="center" valign="middle"colspan="3">百</td>
	   	<td align="center" valign="middle" colspan="3">十</td>
	   	<td align="center" valign="middle"colspan="3">万</td>
	   	<td align="center" valign="middle"colspan="3">千</td>
	   	<td align="center" valign="middle"colspan="3">百</td>
	   	<td align="center" valign="middle"colspan="3">十</td>
	   	<td align="center" valign="middle"colspan="3">元</td>
	   	<td align="center" valign="middle"colspan="3">角</td>
	   	<td align="center" valign="middle"colspan="3">分</td>
	   	
	   	
	   
	   </tr>
	   <tr id="amount">
	   	
	   	<td colspan="3"> &nbsp;</td>
	   	<td colspan="3">&nbsp; </td>
	   	<td colspan="3"></td>
	   	<td colspan="3"></td>
	   	<td colspan="3"></td>
	   	<td colspan="3"></td>
	   	<td colspan="3"></td>
	   	<td colspan="3"></td>
	   	<td colspan="3"></td>
	   	<td colspan="3"></td>
	   	<td colspan="3"></td>
	   	
	   	
	   	
	   </tr>
	   <tr>
	   	<td colspan="14" align="center" valign="middle">票据种类</td>
	   	<td id="pjType" colspan="22"></td>
	   	<td colspan="8" align="center" valign="middle">票据张数</td>
	   	<td colspan="8"></td>
	   	<td colspan="52" rowspan="5"valign="bottom">&nbsp  &nbsp  &nbsp &nbsp  &nbsp  &nbsp &nbsp  &nbsp  &nbsp &nbsp  &nbsp  &nbsp &nbsp  &nbsp  &nbsp &nbsp  &nbsp  &nbsp &nbsp  &nbsp  &nbsp &nbsp  &nbsp  &nbsp &nbsp  &nbsp  &nbsp &nbsp  &nbsp  &nbsp 收款人开户银行盖章<br>
	   	<br></td>
	   
	   
	   </tr>
	   <tr>
	   	<td colspan="14" align="center" valign="middle">票据号码</td>
	   	<td colspan="38"></td>
	   
	   
	   </tr>
	   <tr>
	   	<td colspan="52" rowspan="3"valign="bottom"> &nbsp &nbsp &nbsp &nbsp 备注：<br><br><br>&nbsp  &nbsp  &nbsp  &nbsp  &nbsp  &nbsp  &nbsp  &nbsp  &nbsp  &nbsp  &nbsp  &nbsp  &nbsp   &nbsp    &nbsp  &nbsp   &nbsp  &nbsp &nbsp  &nbsp   &nbsp  &nbsp 复核： &nbsp  &nbsp  &nbsp  &nbsp  &nbsp  &nbsp  记账：<br><br></td>
	   	

	    </tr>


	   </table>

	</div>
  </body>
</html>
