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
	<script type="text/javascript" src="${basePath}/resources/js/mcrcb-core/LodopPage.js"></script>
    
	<style type="text/css">
		td{
			text-align: center;
		}
		tr{
			height: 18px;
		}
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
  	<div style="font-size: 20px;font-weight: bolder;text-align: center;">
  		蒙&nbsp;&nbsp;城&nbsp;&nbsp;县&nbsp;&nbsp;卫&nbsp;&nbsp;生&nbsp;&nbsp;核&nbsp;&nbsp;算&nbsp;&nbsp;中&nbsp;&nbsp;心&nbsp;&nbsp;支&nbsp;&nbsp;付&nbsp;&nbsp;凭&nbsp;&nbsp;证
  	</div>
<!--   	<input type="button" style="margin-left:75%;margin-top:-10px;" onclick="javascript:printDetail(this);" id="printDiv" value="打印"> -->
  	<div style="margin-top:20px;margin-bottom:6px;">
	  	  <span style="padding-left:23%;font-size: 15px;font-weight: bolder;">付款日期：</span>
	  	  <span style="padding-left:5%;" id="year"></span>
		  <span style="padding-left:1%;font-size: 15px;font-weight: bolder;">年</span>
		  <span style="padding-left:1%;" id="month"></span>
		  <span style="padding-left:1%;font-size: 15px;font-weight: bolder;">月</span>
		  <span style="padding-left:1%;" id="day"></span>
		  <span style="padding-left:1%;font-size: 15px;font-weight: bolder;">日</span>
  		  <span style="padding-left:10%;font-size: 15px;font-weight: bolder;">凭证号：</span>
  		  <span style="padding-left:1%;" id="operNo"></span>
  	</div>
  	<table border="1" align="center" width="55%" height="60%" cellspacing='0' cellpadding='0'>
  		<tbody>
  			<tr>
  				<td rowspan="3" style="width:24px;">付<br>款<br>人</td>
  				<td style="width:75px;">全称</td>
  				<td id="outAccname" style="width:280px;"></td>
  				<td rowspan="3" style="width:24px;">收<br>款<br>人</td>
  				<td style="width:70px;">全称</td>
  				<td colspan="11" id="inName"></td>
  			</tr>
  			<tr>
  				<td>帐号</td>
  				<td id="outAccno"></td>
  				<td>帐号</td>
  				<td colspan="11" id="inAccno"></td>
  			</tr>
  			<tr>
  				<td>开户行</td>
  				<td id="outBank"></td>
  				<td>开户行</td>
  				<td colspan="11" id="inBank"></td>
  			</tr>
  			<tr>
  				<td colspan="2">资金性质</td>
  				<td id="zjFld"></td>
  				<td colspan="2">结算方式</td>
  				<td colspan="11" id="payWay"></td>
  			</tr>
  			<tr>
  				<td colspan="2">一级预算单位</td>
  				<td id="topYsdw"></td>
  				<td colspan="2">基层预算单位</td>
  				<td colspan="11" id="footYsdw"></td>
  			</tr>
  			<tr>
  				<td colspan="2">预算项目</td>
  				<td id="zbDetail"></td>
  				<td rowspan="2">科<br>目</td>
  				<td>功能分类</td>
  				<td colspan="11" id="funcFl"></td>
  			</tr>
  			<tr>
  				<td colspan="2">用途</td>
  				<td id="yt"></td>
  				<td>经济分类</td>
  				<td colspan="11" id="ecnoFl"></td>
  			</tr>
  			<tr>
  				<td rowspan="2" colspan="2">支付人名币（大写）</td>
  				<td id="amountD" rowspan="2" colspan="3"></td>
  				<td>亿</td>
  				<td>千</td>
  				<td>百</td>
  				<td>十</td>
  				<td>万</td>
  				<td>千</td>
  				<td>百</td>
  				<td>十</td>
  				<td>元</td>
  				<td>角</td>
  				<td>分</td>
  			</tr>
  			<tr id="amount">
  				<td style="height:40px;">&nbsp;</td>
  				<td>&nbsp;</td>
  				<td>&nbsp;</td>
  				<td>&nbsp;</td>
  				<td>&nbsp;</td>
  				<td>&nbsp;</td>
  				<td>&nbsp;</td>
  				<td>&nbsp;</td>
  				<td>&nbsp;</td>
  				<td>&nbsp;</td>
  				<td>&nbsp;</td>
  			</tr>
  			<tr>
  				<td colspan="6"></td>
  				<td>银<br>行<br>会<br>计<br>分<br>录</td>
  				<td colspan="9">
  					<table>
  						<tbody>
  							<tr>
  								<td style="padding-bottom:30px">(借)</td>
  								<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
  							</tr>
  							<tr>
  								<td style="padding-bottom:20px">对方科目</td>
  								<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
  							</tr>
  							<tr>
  								<td style="padding-right:20px">复核员</td>
  								<td style="padding-left:50px;">记帐员&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
  							</tr>
  						</tbody>
  					</table>
  				</td>
  			</tr>
  		</tbody>
  	</table>
  </body>
</html>
