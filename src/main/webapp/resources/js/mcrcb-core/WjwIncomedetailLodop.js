	var detail;
	var LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
//	var did = 0;
// 	$(document).ready(function(){
//	//	var a = <%=request.getParameter("id")%>; 
// 	//	alert(a);
// 	var a = $('#id').val();
// 	//alert(a);
// 		getRoleInfo();
// 	});
 	
	function getRoleInfo(){
		//alert($('#id').val());
		//alert(id);
		$.ajax({
	           	type: "post",
	           	data:{id:$('#id').val(),start:0,limit:50},
	            url: "/WjwIncomedetail/getWjwIncomedetailById",
	            dataType: "json",
	            success: function (data) {
	            		//setRole(data);
	            		//alert(data);
	            		detail = data;
	            		//alert(WjwIncomedetail.unitName);
	            		$('#unitNo').text(detail.unitNo);
	            		$('#unitName').text(detail.unitName);
	            		$('#outAccname').text(detail.outAccname);
	            		$('#inName').text(detail.inName);
	            		$('#outAccno').text(detail.outAccno);
	            		$('#inAccno').text(detail.inAccno);
	            		$('#outBank').text(detail.outBank);
	            		$('#inBank').text(detail.inBank);
	            		//收入项目1设置
	            		$('#item1Code').text(detail.item1Code);
	            		if(detail.item1==1){
	            			detail.item1='医疗';
	            			$('#item1').text('医疗');
	            		}else if(detail.item1==2){
	            			detail.item1='药品';
	            			$('#item1').text('药品');
	            		}else if(detail.item1==3){
	            			detail.item1='其他';
	            			$('#item1').text('其他');
	            		}
//	            		$('#item1').text(detail.item1);
	            		$('#item1Dw').text(detail.item1Dw);
//	            		$('#item1Num').text(detail.item1Num);
	            		if(detail.item1Num == 0){
	            			detail.item1Num='';
	            			$('#item1Num').text('');
	            		}else{
	            			$('#item1Num').text(detail.item1Num);
	            		}
	            		$('#item1St').text(detail.item1St);
	            		$('#item1Amt').text(detail.item1Amt);
	            		//收入项目2设置
	            		$('#item2Code').text(detail.item2Code);
	            		if(detail.item2==1){
	            			detail.item2='医疗';
	            			$('#item2').text('医疗');
	            		}else if(detail.item2==2){
	            			detail.item2='药品';
	            			$('#item2').text('药品');
	            		}else if(detail.item2==3){
	            			detail.item2='其他';
	            			$('#item2').text('其他');
	            		}
//	            		$('#item2').text(detail.item2);
	            		$('#item2Dw').text(detail.item2Dw);
//	            		$('#item2Num').text(detail.item2Num);
	            		if(detail.item2Num == 0){
	            			detail.item2Num='';
	            			$('#item2Num').text('');
	            		}else{
	            			$('#item2Num').text(detail.item2Num);
	            		}
	            		$('#item2St').text(detail.item2St);
//	            		$('#item2Amt').text(detail.item2Amt);
	            		if(detail.item2Amt == 0 || detail.item2Amt == 0.00){
	            			detail.item2Amt='';
	            			$('#item2Amt').text('');
	            		}else{
	            			$('#item2Amt').text(detail.item2Amt);
	            		}
	            		//收入项目3设置
	            		$('#item3Code').text(detail.item3Code);
	            		if(detail.item3==1){
	            			detail.item3='医疗';
	            			$('#item3').text('医疗');
	            		}else if(detail.item3==2){
	            			detail.item3='药品';
	            			$('#item3').text('药品');
	            		}else if(detail.item3==3){
	            			detail.item3='其他';
	            			$('#item3').text('其他');
	            		}
//	            		$('#item3').text(detail.item3);
	            		$('#item3Dw').text(detail.item3Dw);
//	            		$('#item3Num').text(detail.item3Num);
	            		if(detail.item3Num == 0){
	            			detail.item3Num='';
	            			$('#item3Num').text('');
	            		}else{
	            			$('#item3Num').text(detail.item3Num);
	            		}
	            		$('#item3St').text(detail.item3St);
//	            		$('#item3Amt').text(detail.item3Amt);
	            		if(detail.item3Amt == 0 || detail.item3Amt == 0.00){
	            			detail.item3Amt='';
	            			$('#item3Amt').text('');
	            		}else{
	            			$('#item3Amt').text(detail.item3Amt);
	            		}
	            		
	            		$('#amount').text(RMBformat(detail.amount));
	            		$('#dx').text('人民币'+DX(detail.amount));
	            		$('#certNo').text(detail.certNo);
	            		
	            		$('#yyyy').text(detail.incTime.substr(0,4));
	            		$('#MM').text(detail.incTime.substr(4,2));
	            		$('#dd').text(detail.incTime.substr(6,2));
	            		//WjwIncomedetail = $.parseJSON(data);
	            		//alert("成功");
	            		//alert(WjwIncomedetail.unitNo);
	            },
	            error: function (XMLHttpRequest, textStatus, errorThrown) {
	                    alert("操作失败，请稍后重试");
	            }
	    });
	}
	
	function createPrintPage(){
		
		LODOP.PRINT_INITA(0,0,790,427,"缴款信息");
		LODOP.SET_PRINT_PAGESIZE(1,2090,1130,"");
		LODOP.ADD_PRINT_SETUP_BKIMG("<img border='0' src='/resources/images/income.jpg'>");
		LODOP.SET_SHOW_MODE("BKIMG_LEFT",50);
		LODOP.ADD_PRINT_TEXT(64,110,134,20,detail.unitNo);
		LODOP.ADD_PRINT_TEXT(84,112,131,21,detail.unitName);
		LODOP.ADD_PRINT_TEXT(80,245,34,20,detail.incTime.substr(0,4));
		LODOP.ADD_PRINT_TEXT(88,429,189,21,detail.inName);
		LODOP.ADD_PRINT_TEXT(125,437,229,18,detail.inAccno);
		LODOP.ADD_PRINT_TEXT(145,438,232,20,detail.inBank);
		LODOP.ADD_PRINT_TEXT(103,131,210,20,detail.outAccname);
		LODOP.ADD_PRINT_TEXT(124,133,212,20,detail.outAccno);
		LODOP.ADD_PRINT_TEXT(145,134,207,20,detail.outBank);
		LODOP.ADD_PRINT_TEXT(187,24,100,20,detail.item1Code);
		LODOP.ADD_PRINT_TEXT(211,24,100,20,detail.item2Code);
		LODOP.ADD_PRINT_TEXT(231,25,100,20,detail.item3Code);
		LODOP.ADD_PRINT_TEXT(184,136,135,20,detail.item1);
		LODOP.ADD_PRINT_TEXT(205,133,138,20,detail.item2);
		LODOP.ADD_PRINT_TEXT(230,131,140,20,detail.item3);
		LODOP.ADD_PRINT_TEXT(187,278,71,20,detail.item1Dw);
		LODOP.ADD_PRINT_TEXT(212,278,75,20,detail.item2Dw);
		LODOP.ADD_PRINT_TEXT(230,276,75,20,detail.item3Dw);
		LODOP.ADD_PRINT_TEXT(185,350,80,20,detail.item1Num);
		LODOP.ADD_PRINT_TEXT(209,350,79,20,detail.item2Num);
		LODOP.ADD_PRINT_TEXT(231,350,78,20,detail.item3Num);
		LODOP.ADD_PRINT_TEXT(184,437,80,20,detail.item1St);
		LODOP.ADD_PRINT_TEXT(204,433,85,20,detail.item2St);
		LODOP.ADD_PRINT_TEXT(230,431,85,20,detail.item3St);
		LODOP.ADD_PRINT_TEXT(184,518,177,20,detail.item1Amt);
		LODOP.ADD_PRINT_TEXT(205,520,171,20,detail.item2Amt);
		LODOP.ADD_PRINT_TEXT(224,520,169,20,detail.item3Amt);
		LODOP.ADD_PRINT_TEXT(251,279,198,20,'人民币'+DX(detail.amount));
		LODOP.ADD_PRINT_TEXT(247,527,150,20,'￥'+RMBformat(detail.amount));
		LODOP.ADD_PRINT_TEXT(343,68,169,20,detail.certNo);
		LODOP.ADD_PRINT_TEXT(80,300,23,20,detail.incTime.substr(4,2));
		LODOP.ADD_PRINT_TEXT(79,339,21,20,detail.incTime.substr(6,2));

		
		
		
		/*LODOP.PRINT_INIT("缴款信息");//打印初始化
		LODOP.SET_PRINT_PAGESIZE(1,"209mm","113mm","");//设置纸张高度
		LODOP.ADD_PRINT_SETUP_BKIMG("<img border='0' src='/resources/images/income.jpg'>");
		LODOP.SET_SHOW_MODE("BKIMG_LEFT",50);
//		LODOP.SET_SHOW_MODE("BKIMG_IN_PREVIEW",1);//设置显示背景图片
		
		LODOP.ADD_PRINT_TEXT(67,153,134,20,detail.unitNo);
		LODOP.ADD_PRINT_TEXT(87,155,131,21,detail.unitName);
//		LODOP.ADD_PRINT_TEXT(83,400,155,20,detail.fmtTime);//年月日
		LODOP.ADD_PRINT_TEXT(83,400,155,20,detail.incTime.substr(0,4));//年月日
		
		LODOP.ADD_PRINT_TEXT(101,518,215,21,detail.inName);
		LODOP.ADD_PRINT_TEXT(128,521,218,18,detail.inAccno);
		LODOP.ADD_PRINT_TEXT(148,522,220,20,detail.inBank);
		LODOP.ADD_PRINT_TEXT(106,174,210,20,detail.outAccname);//outAccname
		LODOP.ADD_PRINT_TEXT(127,176,212,20,detail.outAccno);//outAccno
		LODOP.ADD_PRINT_TEXT(148,177,207,20,detail.outBank);//outBank
		LODOP.ADD_PRINT_TEXT(190,67,100,20,detail.item1Code);
		LODOP.ADD_PRINT_TEXT(214,67,100,20,detail.item2Code);
		LODOP.ADD_PRINT_TEXT(234,68,100,20,detail.item3Code);
		LODOP.ADD_PRINT_TEXT(192,179,135,20,detail.item1);
		LODOP.ADD_PRINT_TEXT(213,176,138,20,detail.item2);
		LODOP.ADD_PRINT_TEXT(233,174,140,20,detail.item3);
		LODOP.ADD_PRINT_TEXT(190,321,71,20,detail.item1Dw);
		LODOP.ADD_PRINT_TEXT(215,321,75,20,detail.item2Dw);
		LODOP.ADD_PRINT_TEXT(233,319,75,20,detail.item3Dw);
		LODOP.ADD_PRINT_TEXT(188,393,80,20,detail.item1Num);
		LODOP.ADD_PRINT_TEXT(212,393,79,20,detail.item2Num);
		LODOP.ADD_PRINT_TEXT(234,393,78,20,detail.item3Num);
		LODOP.ADD_PRINT_TEXT(191,480,80,20,detail.item1St);
		LODOP.ADD_PRINT_TEXT(210,476,85,20,detail.item2St);
		LODOP.ADD_PRINT_TEXT(233,474,85,20,detail.item3St);
		LODOP.ADD_PRINT_TEXT(191,561,177,20,detail.item1Amt);
		LODOP.ADD_PRINT_TEXT(210,563,171,20,detail.item2Amt);
		LODOP.ADD_PRINT_TEXT(231,563,169,20,detail.item3Amt);
		LODOP.ADD_PRINT_TEXT(254,322,198,20,'人民币'+DX(detail.amount));
		LODOP.ADD_PRINT_TEXT(255,570,150,20,'￥'+RMBformat(detail.amount));
		LODOP.ADD_PRINT_TEXT(374,111,169,20,detail.certNo);
		//LODOP.PREVIEW();
		//
		LODOP.ADD_PRINT_TEXT(83,444,23,20,detail.incTime.substr(4,2));
		LODOP.ADD_PRINT_TEXT(82,483,21,20,detail.incTime.substr(6,2));*/
	}
	
	function myPrint1(){
		createPrintPage();
		LODOP.PREVIEW();
	}
	
	function printSetup(){
		createPrintPage();
		LODOP.PRINT_SETUP();
	}
	
	function printDesign(){
		createPrintPage();
		LODOP.PRINT_DESIGN();
	}
	
	function print(){
		createPrintPage();
		LODOP.PRINT();
	}
	