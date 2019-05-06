<%@page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>蒙城县卫生核算中心支付申请书</title>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
	<link type="text/css" rel="stylesheet" href="${basePath}/resources/css/table.css">
</head>

<body>
	<div align="center" class="content" style="font-size: 20px;font-weight: bold;">
	          蒙城县卫生核算中心支付申请书
	</div>
	<div  class="content" >
		<table cellpadding="0" cellspacing="1" border="0" class="tab-1" >
			<tr align="left">
				<td>申请单位名称：${main.unitName}</td>
				<td colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                <td>${main.sqTime } </td>
			</tr>
			<tr align="left">
				<td>申请单位编码：${main.unitNo }</td>
			</tr>
			<tr align="left">
				<td>支出类型：${main.payType }</td>
				<td>单位：元</td>
			</tr>
		</table>
		<table cellpadding="0" cellspacing="1" class="tab-2" border="1">
				<tr class="tab_title">
					<td align="center" colspan="4" rowspan="2">序号</td>
					<td align="center" colspan="3" >
					                            预算科目
					</td>
					<td align="center" colspan="4" rowspan="2">用途</td>
					
					<td align="center" colspan="3" >
					                           收款人
					</td>
					<td align="center" colspan="4" rowspan="2">申请金额</td>
				</tr>
				<tr class="tab_title">
					<td align="center" >
					         指标摘要
					</td>
					<td align="center" >
					    功能分类（类款项）
					</td>
					<td align="center" >
					  经济分类（类款）
					</td>
					
					<td align="center" >
					     全称
					</td>
					<td align="center" >
					    开户银行
					</td>
					<td align="center" >
					  银行账号
					</td>
				</tr>
				<c:forEach items="${mainmxList}" var="pro">
					<tr class="header">
					    <td colspan="4">${pro.id }</td>&nbsp;
						<%-- <td colspan="1">${pro.unitNo}</td> --%>
						<td colspan="1">${pro.zbDetail }</td>
						<td colspan="1">${pro.funcFl }</td>
						<td colspan="1">${pro.ecnoFl }</td>
						<td colspan="4">${pro.yt }</td> 
						<td colspan="1">${pro.inName }</td>
						<td colspan="1">${pro.inAccno }</td>
						<td colspan="1">${pro.inBank }</td>
						<td colspan="1">￥${pro.amount }</td>
					</tr>
				</c:forEach>
				<tr class="header">
				     <td colspan="6"  align="center">金额合计（大写）</td>&nbsp;
				     <td colspan="7"  align="left">${amountUpper}</td>&nbsp;
				     <td colspan="1"  align="center">金额合计（小写）</td>&nbsp;
				     <td colspan="1"  align="let">￥${amount}</td>&nbsp;
				</tr>
				<tr class="header">
				     <td colspan="7"  align="center">申请支付单位</td>&nbsp;
				     <td colspan="8"  align="center">蒙城县财政局国库支付中心</td>&nbsp;
				</tr>
				
				<tr class="header">
				     <td  align="center" colspan="5" rowspan="2">（印章）</td>&nbsp;
				      <td align="center">负责人</td>&nbsp;
				     <td align="center">经办人</td>&nbsp;
				     <td  align="center" colspan="5" rowspan="2">（印章）</td>&nbsp;
				     
				     <td align="center">中心会计初审</td>&nbsp;
				     <td align="center">中心会计终审</td>&nbsp;
				      <td align="center" >备注</td>&nbsp;
				</tr>
				
				 <tr class="header" height="100px">
				     <td align="center"></td>
				      <td align="center"></td>
				       <td align="center"></td>
				      <td align="center">&nbsp;</td>
				</tr> 
		</table>
	</div>
	<script type="text/javascript" src="${basePath}/resources/js/jquery/jquery.min.js"></script>
</body>
</html>