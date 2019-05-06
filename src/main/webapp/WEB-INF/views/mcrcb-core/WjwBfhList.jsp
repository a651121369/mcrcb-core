<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
    <title>蒙城卫健委拨付清单</title>
<style type="text/css">
<!--
.STYLE2 {
	font-size: x-large;
	font-family: "宋体";
	font-weight: bold;
}
-->
</style>
</head>

<body>
<div align="center">
	
	<div>
		<h2>蒙城卫健委拨付清单</h2>
	</div>
  <table width="850" border="1">
  	<tr>
  		<th scope="col" colspan="6">
  			<div align=left>&nbsp;&nbsp;&nbsp;&nbsp;
  					凭证编号：<label id="certNo">${certNo}</label>
  			</div>
  		</th>
  	</tr>
    <tr>
	<!--
      <th width="63" scope="col"><div align="center">机构号</div></th>
      <th width="99" scope="col"><div align="center">机构名称</div></th>
      <th width="94" scope="col"><div align="center">拨付总金额</div></th>
      <th width="106" scope="col"><div align="center">拨付药品金额</div></th>
      <th width="104" scope="col"><div align="center">拨付医疗金额</div></th>
      <th width="113" scope="col"><div align="center">拨付时间</div></th>
	-->
	   <th  scope="col"><div align="center">机构号</div></th>
      <th  scope="col"><div align="center">机构名称</div></th>
      <th  scope="col"><div align="center">拨付总金额</div></th>
      <th scope="col"><div align="center">拨付药品金额</div></th>
      <th  scope="col"><div align="center">拨付医疗金额</div></th>
      <th scope="col"><div align="center">拨付时间</div></th>
    </tr>
  	
	<c:forEach var="data" items="${lists}">
    <tr>
      <td><div align="center">${data.unitNo}</div></td>
      <td><div align="center">${data.unitName}</div></td>
      <td><div align="center">${data.amount}</div></td>
      <td><div align="center">${data.drugAmt}</div></td>
      <td><div align="center">${data.medcAmt}</div></td>
      <td><div align="center">${data.bfTime.substring(0,8)}</div></td>
    </tr>
  	</c:forEach><p> 
  	
  </table>
</div>
</body>
</html>
