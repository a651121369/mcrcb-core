<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/resources/js/ext/resources/css/ext-all.css" />
	<link rel="stylesheet" type="text/css" href="${basePath}/resources/css/index.css" />
	<script type="text/javascript" src="${basePath}/resources/js/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="${basePath}/resources/js/util/dateFormat.js"></script>
    
	<style type="text/css">
		th{
			text-align: center;
		}
		thead,tfoot{
			font-size: 15px;
			font-weight: bold;
		}
	</style>
  </head>
  <body>
  	<input type="hidden" id="cnnNo" value="${cnnNo}" />
  	<input type="hidden" id="sqTime" value="${sqTime}" />
  	<div style="font-size: 25px;font-weight: bolder;text-align: center;">
  		卫计委核算中心支付申请书
  	</div>
  	<!-- <input type="button" value="打印" id="printDiv" onclick="javascript:printDetail(this);" style="margin-left:95%;margin-top:-20px;"> -->
  	<div id="div" style="margin-left: 3%;font-size: 13px;margin-top:60px;" >
  		<div>
  			<h3 id="unitName">申请单位名称：${main.unitName}</h3>
	  		<div align="center" id="dateFormat">${main.sqTime }</div>
	  	</div>
	  	<div>
	  		<h3 id="unitNo">申请单位编号：${main.unitNo}</h3>
	  	</div>
	  	<br />
	  	<div>
	  		<h3 id="payWay">支&nbsp;&nbsp;出&nbsp;&nbsp;类&nbsp;&nbsp;型：${main.payType }</h3>
	  		<div align="right" style="margin-right: 3%">单位：元</div>
	  	</div>
  	</div>
  	<table border="1" align="center" width="95%" height="120px" cellspacing='0' cellpadding='0'>
  		<thead>
  			<tr>
  				<th rowspan="2">序号</th>
  				<th colspan="3">预算科目</th>
  				<th rowspan="2">用途</th>
  				<th colspan="3">收款人</th>
  				<th rowspan="2">申请金额</th>
  			</tr>
  			<tr>
  				<th>指标摘要</th>
  				<th>功能分类(类款项)</th>
  				<th>经济分类(类款)</th>
  				<th>全称</th>
  				<th>开户银行</th>
  				<th>银行账号</th>
  			</tr>
  			
  		</thead>
  		<c:forEach items="${mainmxList}" var="pro">
					<tr>
					    <td align="center">${pro.rowid }</td>
						<td align="center">${pro.zbDetail }</td>
						<td align="center">${pro.funcFl }</td>
						<td align="center">${pro.ecnoFl }</td>
						<td align="center">${pro.yt }</td> 
						<td align="center">${pro.inName }</td>
						<td align="center">${pro.inAccno }</td>
						<td align="center">${pro.inBank }</td>
						<td align="right">￥${pro.amount }</td>
					</tr>
				</c:forEach>
  		<tfoot>
  			<tr>
  				<th colspan="3">金额合计(大写)</th>
  				<td colspan="4" id="amountD" align="left">${amountUpper}</td>
  				<th>金额合计(小写)</th>
  				<td id="amountX" align="right">￥${amount}</td>
  			</tr>
  			<tr>
  				<th colspan="3">申请支付单位</th>
  				<th colspan="6">蒙城县卫计委核算中心</th>
  			</tr>
  			<tr>
  				<th rowspan="4">(印章)</th>
  				<th>负责人</th>
  				<th>经办人</th>
  				<th rowspan="4">(印章)</th>
  				<th colspan="2">初审</th>
  				<th colspan="2">复审</th>
  				<th>备注</th>
  			</tr>
  			<tr>
  				<th rowspan="3"></th>
  				<th rowspan="3"></th>
  				<th>负责人</th>
  				<th>经办人</th>
  				<th>负责人</th>
  				<th>经办人</th>
  				<th rowspan="3"></th>
  			</tr>
  			<tr>
  				<th rowspan="2">&nbsp;</th>
  				<th rowspan="2">&nbsp;</th>
  				<th rowspan="2">&nbsp;</th>
  				<th rowspan="2">&nbsp;</th>
  			</tr>
  		</tfoot>
  	</table>
  </body>
</html>
