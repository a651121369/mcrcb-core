/****************************jQuery入口*******************************/
$(function() {
	var now = new Date();
	var years = now.getFullYear();
	var months = now.getMonth() + 1;
	if(months>0 && months<10){
		months = "0"+months;
	}else{
		months = months;
	}
	var days = now.getDate();		
	if(days>0 && days<10){
		days = "0"+days;
	}else{
		days = days;
	}
	$('#year').text(years);
	$('#month').text(months);
	$('#day').text(days);
	
	//test();
	$.ajax({
		type: "post", 
		traditional: true,
		url: "/select/Wjw_Paydetail_Lodop", 
		dataType: "json", 
        data:{detailId: $('#id').val()},
		success: function (data){
			var result = (data.rows)[0];
			console.log(result);
			var payWay="";
			if(result.payWay==1){
				payWay="现金";
			}else if(result.payWay==2){
				payWay="转账";
			}else{
				payWay="其他";
			}
			$('#outAccname').text(result.outAccname);
			$('#inName').text(result.inName);
			$('#outAccno').text(result.outAccno);
			$('#inAccno').text(result.inAccno);
			$('#outBank').text(result.outBank);
			$('#inBank').text(result.inBank);
			$('#zjFld').text(result.zjFld);
			$('#payWay').text(payWay);
			$('#topYsdw').text(result.topYsdw);
//			$('#footYsdw').text(result.footYsdw);
			$('#footYsdw').text(result.unitName);
			$('#zbDetail').text(result.zbDetail);
			$('#funcFl').text(result.funcFl);
			$('#yt').text(result.yt);
			$('#ecnoFl').text(result.ecnoFl);
			$('#operNo').text(result.operNo);
//			var payTime = result.payTime;
//			$('#year').text(payTime.substr(0,4));
//			$('#month').text(payTime.substr(4,2));
//			$('#day').text(payTime.substr(6,2));
//			var amt = result.amount;
			result.amount = result.amount+"";
			if(result.amount.indexOf('.')>0){
				var p = result.amount.indexOf('.');
				var q = result.amount.substring(p+1);
				if(q.length==1){
					result.amount = result.amount+"0";
				}
				var amount = (result.amount).replace('.','');
				for(var i=0;i<amount.length;i++){
					$($('#amount').children().get((11-(amount.length))+i)).text(amount[i]);
					
				}
				$($('#amount').children().get((11-(amount.length+1)))).text("￥");
			}else{
				var amount = (result.amount)+'00';
				for(var i=0;i<amount.length;i++){
					$($('#amount').children().get((11-(amount.length))+i)).text(amount[i]);
				}
				$($('#amount').children().get((11-(amount.length+1)))).text("￥");
			}
			$('#amountD').text(DX(result.amount));
//			console.log((result.amount).split('.')[0]+"=="+(result.amount).split('.')[1]);
		},
		error:function(){
//			$.confirm({
//    	    	content: '数据加载失败！',
//    	        confirmButtonClass: 'btn-info',
//    	        cancelButtonClass: 'btn-danger'
//    	    });
		}
    });
/****************************打印**************************************/
	printDetail = function(value){
		var LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
		//参数(距离页面头部,距离页面左边距离,文本宽度,文本高度,文本内容)
//		LODOP.PRINT_INIT("财政直接支付凭证");
//		LODOP.ADD_PRINT_SETUP_BKIMG("<img border='0' src='/resources/images/lodop.jpg'>");//放入背景图片
//		LODOP.SET_SHOW_MODE("BKIMG_IN_PREVIEW",1);//设置显示背景图片
//		LODOP.PRINT_DESIGN();//开启打印设置
		LODOP.ADD_PRINT_TEXT(123,212,270,22,$('#outAccname').text());
		LODOP.ADD_PRINT_TEXT(123,590,257,22,$('#inName').text());
		LODOP.ADD_PRINT_TEXT(148,212,270,22,$('#outAccno').text());
		LODOP.ADD_PRINT_TEXT(148,590,257,22,$('#inAccno').text());
		LODOP.ADD_PRINT_TEXT(172,212,270,22,$('#outBank').text());
		LODOP.ADD_PRINT_TEXT(172,590,257,22,$('#inBank').text());
		LODOP.ADD_PRINT_TEXT(197,212,270,22,$('#zjFld').text());//资金性质
		LODOP.ADD_PRINT_TEXT(197,590,257,22,$('#payWay').text());//结算方式
		LODOP.ADD_PRINT_TEXT(221,212,270,22,$('#topYsdw').text());//一级预算单位
		LODOP.ADD_PRINT_TEXT(221,590,257,22,$('#footYsdw').text());//基层预算单位
		LODOP.ADD_PRINT_TEXT(246,212,270,22,$('#zbDetail').text());//预算项目
		LODOP.ADD_PRINT_TEXT(246,590,257,22,$('#funcFl').text());//功能分类
		LODOP.ADD_PRINT_TEXT(271,212,270,22,$('#yt').text());//用途
		LODOP.ADD_PRINT_TEXT(271,590,257,22,$('#ecnoFl').text());//经济分类
		LODOP.ADD_PRINT_TEXT(92,384,35,22,$('#year').text());//付款日期，年
		LODOP.ADD_PRINT_TEXT(92,445,22,22,$('#month').text());//付款日期，月
		LODOP.ADD_PRINT_TEXT(92,491,24,22,$('#day').text());//付款日期，日
		LODOP.ADD_PRINT_TEXT(92,727,125,22,$('#operNo').text());//凭证号
		LODOP.ADD_PRINT_TEXT(328,587,17,22,$($('#amount').children().get(0)).text());//亿
		LODOP.ADD_PRINT_TEXT(328,606,17,22,$($('#amount').children().get(1)).text());//千万
		LODOP.ADD_PRINT_TEXT(328,628,17,22,$($('#amount').children().get(2)).text());//百万
		LODOP.ADD_PRINT_TEXT(328,653,17,22,$($('#amount').children().get(3)).text());//十万
		LODOP.ADD_PRINT_TEXT(328,678,17,22,$($('#amount').children().get(4)).text());//万
		LODOP.ADD_PRINT_TEXT(328,704,17,22,$($('#amount').children().get(5)).text());//千
		LODOP.ADD_PRINT_TEXT(328,730,17,22,$($('#amount').children().get(6)).text());//百
		LODOP.ADD_PRINT_TEXT(328,756,17,22,$($('#amount').children().get(7)).text());//十
		LODOP.ADD_PRINT_TEXT(328,781,17,22,$($('#amount').children().get(8)).text());//元
		LODOP.ADD_PRINT_TEXT(328,807,17,22,$($('#amount').children().get(9)).text());//角
		LODOP.ADD_PRINT_TEXT(328,832,17,22,$($('#amount').children().get(10)).text());//分
		LODOP.PREVIEW();
	};
});

function test(){
	var result = "123456789.00";//test
	if(result.contains('.')){
		var amount = result.replace('.','');
		for(var i=0;i<amount.length;i++){
			$($('#amount').children().get((11-(amount.length))+i)).text(amount[i]);
		}
	}else{
		var amount = result+'00';
		for(var i=0;i<amount.length;i++){
			$($('#amount').children().get((11-(amount.length))+i)).text(amount[i]);
		}
	}
}
//var LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
//var LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
function myPrint1(){
	var LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
	createPrintPage();
	LODOP.PREVIEW();
}

function printSetup(){
	var LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
	createPrintPage();
	LODOP.PRINT_SETUP();
}

function printDesign(){
	var LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
	createPrintPage();
	LODOP.PRINT_DESIGN();
}

function print(){
	var CurrentDate = "";
	var now = new Date();
	var years = now.getFullYear();
	 CurrentDate += years;
	var months = now.getMonth() + 1;
	if(months>0 && months<10){
		months = "0"+months;
	}else{
		months = months;
	}
	CurrentDate += months;
	var days = now.getDate();		
	if(days>0 && days<10){
		days = "0"+days;
	}else{
		days = days;
	}
	CurrentDate += days;
	var id = $("#id").val();
	$.ajax({
		type: "post", 
		traditional: true,
		url: "../WjwPaydetail/updateIsPrintPaypz.do", 
		data: {"id":id},
		dataType: "json", 
		success: function (data){
		},
		error:function(){
		}
	});
	
	var LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
	createPrintPage();
	LODOP.PRINT();
}

function createPrintPage(){
	var LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
	//参数(距离页面头部,距离页面左边距离,文本宽度,文本高度,文本内容)
	LODOP.PRINT_INIT("蒙城县卫生核算中心支付凭证");
	LODOP.ADD_PRINT_SETUP_BKIMG("<img border='0' src='/resources/images/lodop.jpg'>");//放入背景图片
//	LODOP.SET_SHOW_MODE("BKIMG_IN_PREVIEW",1);//设置显示背景图片
//	LODOP.PRINT_DESIGN();//开启打印设置
	LODOP.ADD_PRINT_TEXT(97,121,270,22,$('#outAccname').text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(99,447,257,22,$('#inName').text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(122,121,270,22,$('#outAccno').text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(120,447,257,22,$('#inAccno').text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(145,121,270,22,$('#outBank').text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(143,447,257,22,$('#inBank').text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(164,121,270,22,$('#zjFld').text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(164,447,257,22,$('#payWay').text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(183,121,270,22,$('#topYsdw').text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(184,447,257,22,$('#footYsdw').text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(203,121,270,22,$('#zbDetail').text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(203,447,257,22,$('#funcFl').text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(224,121,270,22,$('#yt').text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(221,447,257,22,$('#ecnoFl').text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(77,273,35,22,$('#year').text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(77,334,22,22,$('#month').text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(77,380,24,22,$('#day').text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(73,561,125,22,$('#operNo').text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(277,448,17,22,$($('#amount').children().get(0)).text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(277,469,17,22,$($('#amount').children().get(1)).text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(277,490,17,22,$($('#amount').children().get(2)).text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(277,512,17,22,$($('#amount').children().get(3)).text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(277,533,17,22,$($('#amount').children().get(4)).text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(277,554,17,22,$($('#amount').children().get(5)).text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(277,577,17,22,$($('#amount').children().get(6)).text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(277,597,17,22,$($('#amount').children().get(7)).text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(277,617,17,22,$($('#amount').children().get(8)).text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(277,637,17,22,$($('#amount').children().get(9)).text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(277,657,17,22,$($('#amount').children().get(10)).text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(274,136,206,41,$('#amountD').text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	 
	
	/*LODOP.ADD_PRINT_TEXT(123,212,270,22,$('#outAccname').text());
	LODOP.ADD_PRINT_TEXT(123,590,257,22,$('#inName').text());
	LODOP.ADD_PRINT_TEXT(148,212,270,22,$('#outAccno').text());
	LODOP.ADD_PRINT_TEXT(148,590,257,22,$('#inAccno').text());
	LODOP.ADD_PRINT_TEXT(172,212,270,22,$('#outBank').text());
	LODOP.ADD_PRINT_TEXT(172,590,257,22,$('#inBank').text());
	LODOP.ADD_PRINT_TEXT(197,212,270,22,$('#zjFld').text());//资金性质
	LODOP.ADD_PRINT_TEXT(197,590,257,22,$('#payWay').text());//结算方式
	LODOP.ADD_PRINT_TEXT(221,212,270,22,$('#topYsdw').text());//一级预算单位
	LODOP.ADD_PRINT_TEXT(221,590,257,22,$('#footYsdw').text());//基层预算单位
	LODOP.ADD_PRINT_TEXT(246,212,270,22,$('#zbDetail').text());//预算项目
	LODOP.ADD_PRINT_TEXT(246,590,257,22,$('#funcFl').text());//功能分类
	LODOP.ADD_PRINT_TEXT(271,212,270,22,$('#yt').text());//用途
	LODOP.ADD_PRINT_TEXT(271,590,257,22,$('#ecnoFl').text());//经济分类
	LODOP.ADD_PRINT_TEXT(92,384,35,22,$('#year').text());//付款日期，年
	LODOP.ADD_PRINT_TEXT(92,445,22,22,$('#month').text());//付款日期，月
	LODOP.ADD_PRINT_TEXT(92,491,24,22,$('#day').text());//付款日期，日
	LODOP.ADD_PRINT_TEXT(92,727,125,22,$('#operNo').text());//凭证号
	LODOP.ADD_PRINT_TEXT(328,587,17,22,$($('#amount').children().get(0)).text());//亿
	LODOP.ADD_PRINT_TEXT(328,606,17,22,$($('#amount').children().get(1)).text());//千万
	LODOP.ADD_PRINT_TEXT(328,628,17,22,$($('#amount').children().get(2)).text());//百万
	LODOP.ADD_PRINT_TEXT(328,653,17,22,$($('#amount').children().get(3)).text());//十万
	LODOP.ADD_PRINT_TEXT(328,678,17,22,$($('#amount').children().get(4)).text());//万
	LODOP.ADD_PRINT_TEXT(328,704,17,22,$($('#amount').children().get(5)).text());//千
	LODOP.ADD_PRINT_TEXT(328,730,17,22,$($('#amount').children().get(6)).text());//百
	LODOP.ADD_PRINT_TEXT(328,756,17,22,$($('#amount').children().get(7)).text());//十
	LODOP.ADD_PRINT_TEXT(328,781,17,22,$($('#amount').children().get(8)).text());//元
	LODOP.ADD_PRINT_TEXT(328,807,17,22,$($('#amount').children().get(9)).text());//角
	LODOP.ADD_PRINT_TEXT(328,832,17,22,$($('#amount').children().get(10)).text());//分
*/
	};