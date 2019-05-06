<%@page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
  	<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
	<title>明细账打印</title>
	<link type="text/css" rel="stylesheet" href="${basePath}/resources/css/table.css" />
	<link type="text/css" rel="stylesheet" href="${basePath}/resources/css/buttons.css" />
	<link type="text/css" rel="stylesheet" href="${basePath}/resources/css/print.css" />
	<style>
		
		table {
			background:#000000;
		}
		
	</style>
	</head>
	<body>
	<div class="container">
	
		<div class="detail">
			<div class="fl det-l">
	      <p>账号：${acctNo }</p>
	      <p>户名：${acctName }</p>
	      <p>币种：人民币</p>
	   </div>
	   <div class="fr det-r">
	   
	     <p><span class="s01"></span>&nbsp;&nbsp;&nbsp;&nbsp;
	       <span class="s01">起始日期：${beginTime }</span>
	     </p>
	     <p><span class="s01"></span>&nbsp;&nbsp;&nbsp;&nbsp;
	       <span class="s01">截止日期：${endTime }</span>
	     </p>
	     <p>
	     	<span class="s01"></span>&nbsp;&nbsp;&nbsp;&nbsp;
	      <!-- <span class="s01">账户余额：<fmt:formatNumber value="${item1.acctBalance }" pattern="#,#00.00#"/></span>-->
	     </p>
	   </div>
	   <div class="clr"></div>
 		</div>
 		
 		<table border="1px solid #000000" cellspacing="0" cellpadding="0" class="table">
				 <colgroup>
				   <col width="80px"/>
				   <col width="100px"/>
				   <col width="80px"/>
				   <col width="80px"/>
				   <col width="80px"/>
				   <col width="100px"/>
				   <col width="100px"/>
				   <col width="80px"/>
				   <col width="180px"/>
				   <!-- <col width="80px"/>
				   
				   <col width="80px"/> -->
				 </colgroup>
				 <tr>
				  <th>交易时间</th>
				  <th>银行凭证号码</th>
				  <th>收入支出标志</th>
				  <th>交易金额</th>
				  <th>余额</th>
				  <th>药品金额</th>
				  <th>医疗金额</th>
				  
				  <th>对方账号</th>
				  <th>对方户名</th>
				  <!-- 
				  <th>是否冲正</th> -->
				 </tr>
				 
		<c:forEach items="${list }" var="item2">
			 
				 <tr>
				  <td align="center">${item2.tranTime}</td>
				  <td align="center">${item2.note1 }&nbsp;</td>
				  <td align="center">
				  	<c:if test="${item2.inOrOut eq 1 }">收入</c:if>
				  	<c:if test="${item2.inOrOut eq 2 }">支出</c:if>&nbsp;
				  </td>
				  <td align="right"><fmt:formatNumber value="${item2.tranAmt }" pattern="#,##0.00#"/>&nbsp;</td>
				   <td align="right"><fmt:formatNumber value="${item2.amount }" pattern="#,##0.00#"/>&nbsp;</td>
				  <td align="right"><fmt:formatNumber value="${item2.drugAmt }" pattern="#,##0.00#"/>&nbsp;</td>
				  <td align="right"><fmt:formatNumber value="${item2.medcAmt }" pattern="#,##0.00#"/>&nbsp;</td>
				  
				  <td align="center">${item2.dfAccno }&nbsp;</td>
				  <td align="center">${item2.dfAccname }&nbsp;</td>
				 </tr>
		</c:forEach>
				</table>
				<p>&nbsp;</p>
 
<div style="text-align: center">
	<button type="button" id ="pp" class="button glow button-rounded button-flat-primary noprint" style="width:100px;" onclick="window.print();" >打印</button>
</div>


</div>


	</body>

</html>
