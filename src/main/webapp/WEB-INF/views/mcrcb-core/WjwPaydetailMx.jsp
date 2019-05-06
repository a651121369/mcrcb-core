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
	<script type="text/javascript" src="${basePath}/resources/js/mcrcb-core/WjwPaydetailMx.js"></script>
    

  <style type="text/css">
  .Noprint{display:none;}
  .nextPage  
 {  
     page-break-after:always;  

 }  
          table {

table-layout: fixed;

word-wrap:break-word;

}

div {

word-wrap:break-word;

}

*{padding:0px;margin:0px;word-wrap:break-word;word-break:normal;} 

ul,li{list-style-type:none;}


.header{margin:10px auto;width:960px;}
.header h3{font-family:"微软雅黑";color:#222;line-height:28px;font-size:18px;font-weight:bold;text-align:center;margin:20px 0 10px 0;}
.header-bottom{font-family:"宋体";margin-bottom:10px;overflow:hidden;}
.header-bottom ul li{float:left;line-height:20px;height:20px;font-size:14px;color:#333;}
.header-bottom ul li.list-A{width:45%;}
.header-bottom ul li.list-B{width:10%;text-align:right;}
.header-bottom ul li.list-C{width:100%;}

         tr,td{font-size:14px;color:#000000;background:#ffffff;}

        .tb
        {
         border-width:1px;
         border-collapse:separate;
         border-color:#000000;
         border-style:solid solid hidden solid;
        }
        .tb2
        {
         
         border-width:1px;
         border-collapse:separate;
         border-color:#000000;
         border-style:hidden solid solid solid;
         border-top:0;
        }
        td
        {
         border-width:1px;
         border-collapse:collapse;
         border-color:#000000;
         
        }
    </style>
  </head>
<body style="overflow:auto;">
<!--startprint--><div id="printTop" class="header">
	  	<input type="hidden" id="cnnNo" value="${cnnNo}" />
  		<input type="hidden" id="sqTime" value="${sqTime}" />
		<h3>卫计委核算中心支付申请书</h3>
        <input type="button" value="打印" id="printDiv" onclick="javascript:printDetail(this);" style="margin-left:95%;margin-top:-20px;">
		<div class="header-bottom">
        	<ul>	
            	<li id="unitName" class="list-A">
                	申请单位名称：
                </li>
                <li id="dateFormat" class="list-A">
                	2015年11月06日
                </li>
                
                <li id="unitNo" class="list-C">
                	申请单位编码：
                </li>
                
<!--                 <div class="clear"></div> -->
                
                <li class="list-A">
                	<span id="payWay" style="letter-spacing:7px;">支出类型</span>
                </li>
                <li class="list-A">
                	
                </li>
                <li class="list-B">
                	单位：元
                </li>
            </ul>
<!--             <div class="clear"></div> -->
        </div>	
	</div>

 
    <table  width="960" height="140" border="1"  cellpadding="0" cellspacing="0" class="tb" style="word-break:break-all;margin:0  auto;">
     <thead style="display:table-header-group">
     <tr>
       <td rowspan="2"  align="center" valign="middle" > 序号</td>
       <td colspan="3" align="center" valign="middle">预算科目 </td>
       <td rowspan="2" align="center" valign="middle">用途 </td>
       <td colspan="3" align="center" valign="middle">收款人 </td>
       <td rowspan="2" align="center" valign="middle">申请金额 </td>
     </tr>
      <tr >
       <td align="center" valign="middle"> 指标摘要</td>
       <td align="center" valign="middle"> 功能分类（类款项）</td>
       <td align="center" valign="middle"> 经济分类（类款）</td>
       <td align="center" valign="middle"> 全称</td>
       <td align="center" valign="middle"> 开户银行</td>
       <td align="center" valign="middle"> 银行帐号</td>
       
     </tr>
     </thead>
     <tbody id="here"></tbody>
<!--  	 <tfoot style="display:table-footer-group"> -->
     <tr>
       <td colspan="2" width="400"height="30" align="center" valign="middle"> 金额合计（大写）</td>
       <td id="amountD" align="left" colspan="5"> </td>
       <td align="center"  valign="middle">金额合计（小写） </td>
       <td id="amountX" align="right"> </td>
     </tr>
<!--      </tfoot> -->
    </table>
    
    <table id="printBtm"  width="960" height="150" border="1" cellpadding="0" cellspacing="0" class="tb2" style="word-break:break-all;margin:0  auto;">
         <tr>
       <td colspan="4" align="center" valign="middle">申 请 支 付 单 位 </td>
       <td colspan="5" align="center" valign="middle">蒙城县卫计委核算中心 </td>
       <td align="center" valign="middle">备注 </td>
      </tr>
      <tr >
       <td colspan="2" rowspan="4"> </td>
       
       <td  align="center" valign="middle">负责人 </td>
       <td  align="center" valign="middle"> 经办人</td>
       
       <td rowspan="4"> </td>
       <td colspan="2" align="center" valign="middle">初审 </td>
       <td colspan="2" align="center" valign="middle">复审 </td>
       <td rowspan="4"align="center" valign="middle"> </td>

     </tr>
     <tr >
       <td rowspan="3">&nbsp;</td>
       <td rowspan="3"> &nbsp;</td>
       <td rowspan="3"> &nbsp;</td>
       
       
       <td rowspan="3">&nbsp; </td>
       <td rowspan="3"> &nbsp;</td>
       <td rowspan="3">&nbsp; </td>
     
   </tr>
  
    </table><!--endprint-->
</body>
</html>
