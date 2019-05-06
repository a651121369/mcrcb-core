<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>蒙城基层医疗卫生机构收入缴款书</title>
<object  id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0> 
       <embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
</object>
<script type="text/javascript" src="${basePath}/resources/js/util/dateFormat.js"></script>
<script type="text/javascript" src="${basePath}/resources/js/util/LodopFuncs.js"></script>
<script type="text/javascript" src="${basePath}/resources/js/jquery/jquery-1.4.3.js"></script>
<script type="text/javascript" src="${basePath}/resources/js/mcrcb-core/WjwIncomedetailLodop.js"></script>
<script type="text/javascript">
 	$(document).ready(function(){
 		getRoleInfo();
 	});
</script>
</head>
<body>
<div align="center"> 
<%-- 	${id} --%>
	<input type="hidden" id="id" value="${id}"/>
<!-- 		<input type="button" style="margin-left:75%;margin-top:-10px;" onclick="javascript:myPrint1();" id="printDiv" value="打印"> -->
		<div align="right">
		<a href="javascript:print();">打印</a>
	      	<a href="javascript:myPrint1();">打印预览</a>
<!-- 			<a href="javascript:printSetup();">打印维护</a> -->
<!-- 			<a href="javascript:printDesign();">打印设计</a> -->
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;			
		</div>
	<div>
		<h3>蒙城基层医疗卫生机构收入缴款书</h3>
	</div>
    <div>
      <table width="880" height="394" border="1">
		<tr>
			<td colspan="9">
				<div align="left">执收单位编码：
					<label id="unitNo"></label>
				</div>
				<div align="left">执收单位名称：
					<label id="unitName"></label>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;<label id="yyyy"></label>&nbsp;年
				 &nbsp;&nbsp;&nbsp;<label id="MM"></label>&nbsp; 月
				  &nbsp;&nbsp;&nbsp;<label id="dd"></label>&nbsp;日
				</div>
			</td>
		</tr>

        <tr>
          <td width="50" rowspan="3">付款人</td>
        <td width="84"><div align="center">全 称</div></td>
        <td colspan="2">
        	<label id="outAccname"></label>
        </td>
        <td width="49" rowspan="3">收款人</td>
        <td colspan="2"><div align="center">全 称</div></td>
        <td colspan="2">
        	<label id="inName"></label>
        </td>
      </tr>
        <tr>
          <td width="84"><div align="center">账 号</div></td>
        <td colspan="2">
        	<label id="outAccno"></label>
        </td>
        <td colspan="2"><div align="center">账 号</div></td>
        <td colspan="2">
        	<label id="inAccno"></label>
        </td>
      </tr>
        <tr>
          <td width="84"><div align="center">开户银行</div></td>
        <td colspan="2">
        	<label id="outBank"></label>
        </td>
        <td colspan="2"><div align="center">开户银行</div></td>
        <td colspan="2">
        	<label id="inBank"></label>
        </td>
      </tr>
        <tr >
          <td colspan="2"><div align="center">项目编码</div></td>
        <td width="182"><div align="center">收入项目名称</div></td>
        <td width="88"><div align="center">单位</div></td>
        <td colspan="2"><div align="center">数量</div></td>
        <td colspan="2"><div align="center">收缴标准</div></td>
        <td width="233"><div align="center">金额</div></td>
      </tr>
        <tr align="center">
          <td colspan="2">
          	<label id="item1Code"></label>
          </td>
        <td>
        	<label id="item1"></label>
        </td>
        <td>
        	<label id="item1Dw"></label>
        </td>
        <td colspan="2">
        	<label id="item1Num"></label>
        </td>
        <td colspan="2">
        	<label id="item1St"></label>
        </td>
        <td>
        	<label id="item1Amt">&nbsp;</label>
        </td>
        </tr>
        
 		<tr align="center">
          <td colspan="2">
          	<label id="item2Code"></label>
          </td>
        <td>
        	<label id="item2">&nbsp;</label>
        </td>
        <td>
        	<label id="item2Dw">&nbsp;</label>
        </td>
        <td colspan="2">
        	<label id="item2Num">&nbsp;</label>
        </td>
        <td colspan="2">
        	<label id="item2St">&nbsp;</label>
        </td>
        <td>
        	<label id="item2Amt">&nbsp;</label>
        </td>
      </tr>
      
        <tr align="center">
          <td colspan="2">
          	<label id="item3Code"></label>
          </td>
        <td>
        	<label id="item3">&nbsp;</label>
        </td>
        <td>
        	<label id="item3Dw">&nbsp;</label>
        </td>
        <td colspan="2">
        	<label id="item3Num">&nbsp;</label>
        </td>
        <td colspan="2">
        	<label id="item3St">&nbsp;</label>
        </td>
        <td>
        	<label id="item3Amt">&nbsp;</label>
        </td>
      </tr>
        <tr>
          <td colspan="9">
		  币种： &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp&nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

		  金额（大写）
		  &nbsp;&nbsp;&nbsp;<label id="dx"></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		  （小写）&nbsp;&nbsp;&nbsp; &#65509;<label id="amount"></label></td>
        </tr>
        <tr>
          <td height="120" colspan="3">&nbsp;
		  <div>此款支付给收款人</div>
		  <div align="center">付款人盖章</div>
		  <div align="center">预留银行印鉴</div>
		  </td>
        <td colspan="3">&nbsp;
		 <div>上述款项已从付款人账户</div>
		  <div>支付并划转收款人账户</div>
		  <div align="center">银行盖章</div>
		</td>
        <td colspan="3">&nbsp;
		 <div>科目（借）：
		 <span style="text-decoration:underline">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		 
		 </span></div>
		  <div>对方科目（贷）：
		  <span style="text-decoration:underline">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		  
		  </span></div>
		  <div>复核： &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;记账：</div>
		</td>
      </tr>
	  <tr>
          <td colspan="9">
		  效验码：<label id="certNo"></label>
		  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		  本缴款书付款期为5天（到期日遇节假日顺延），过期无效
		  </td>
	 </tr>
      </table>	
    </div>
</div>
</body>
<script type="text/javascript">


</script>
</html>