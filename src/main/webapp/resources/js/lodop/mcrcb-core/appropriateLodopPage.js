/****************************jQuery入口*******************************/

$(function() {
	//test();
	$.ajax({
		type: "post", 
		traditional: true,
		url: "/WjwBfhzb/appropriatePrint", 
//		dataType: "json", 
        data:{id: $('#id').val()},
		success: function (data){
			var result = data[0];
			$('#outAccname').text(result.outAccname);
			$('#inAccname').text(result.inName);
			$('#outAccno').text(result.outAccno);
			$('#inAccno').text(result.inAccno);
			$('#outBank').text(result.outBank);
			$('#inBank').text(result.inBank);
			$('#certNo').text(result.certNo);
			var operTime = result.operTime;
			$('#year').text(operTime.substr(0,4));
			$('#month').text(operTime.substr(4,2));
			$('#day').text(operTime.substr(6,2));
			$('#pjType').text('转账支票');
//			var amt = result.amount;
			result.amount = result.amount+"";
			if(result.amount.contains('.')){
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
	var LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
	createPrintPage();
	LODOP.PRINT();
}

function createPrintPage(){
	var LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
	//参数(距离页面头部,距离页面左边距离,文本宽度,文本高度,文本内容)

	LODOP.PRINT_INIT("蒙城县卫生核算中心拨付凭证");
	LODOP.ADD_PRINT_SETUP_BKIMG("<img border='0' src='/resources/images/appropriatelodop.png'>");
	LODOP.ADD_PRINT_TEXT(129,61,270,22,$('#outAccname').text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(128,366,257,22,$('#inAccname').text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(154,61,270,22,$('#outAccno').text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(153,366,257,22,$('#inAccno').text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(179,61,270,22,$('#outBank').text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(178,366,257,22,$('#inBank').text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(97,204,35,22,$('#year').text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(96,270,22,22,$('#month').text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(96,316,24,22,$('#day').text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(74,488,141,22,$('#certNo').text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(220,412,17,22,$($('#amount').children().get(0)).text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(220,429,17,22,$($('#amount').children().get(1)).text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(220,446,17,22,$($('#amount').children().get(2)).text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(220,463,17,22,$($('#amount').children().get(3)).text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(220,480,17,22,$($('#amount').children().get(4)).text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(220,498,17,22,$($('#amount').children().get(5)).text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(220,515,17,22,$($('#amount').children().get(6)).text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(220,532,17,22,$($('#amount').children().get(7)).text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(220,549,17,22,$($('#amount').children().get(8)).text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(220,566,17,22,$($('#amount').children().get(9)).text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(220,583,17,22,$($('#amount').children().get(10)).text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(221,86,240,26,$('#amountD').text());
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(253,86,115,22,$('#pjType').text());
	
//	LODOP.PRINT_INIT("蒙城县卫生核算中心拨付凭证");
//	LODOP.ADD_PRINT_SETUP_BKIMG("<img border='0' src='/resources/images/appropriatelodop.png'>");
//	LODOP.ADD_PRINT_TEXT(130,105,270,22,$('#outAccname').text());//出票人全称 
//	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
//	LODOP.ADD_PRINT_TEXT(129,449,257,22,$('#inAccname').text());//收款人全称 
//	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
//	LODOP.ADD_PRINT_TEXT(154,105,270,22,$('#outAccno').text());//出票人账号 
//	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
//	LODOP.ADD_PRINT_TEXT(156,489,257,22,$('#inAccno').text());//收款人账户 
//	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
//	LODOP.ADD_PRINT_TEXT(181,143,270,22,$('#outBank').text());//出票人开户银行
//	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
//	LODOP.ADD_PRINT_TEXT(179,489,257,22,$('#inBank').text());//收款人开户银行
//	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
//	LODOP.ADD_PRINT_TEXT(106,338,35,22,$('#year').text());//年
//	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
//	LODOP.ADD_PRINT_TEXT(106,399,22,22,$('#month').text());//月
//	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
//	LODOP.ADD_PRINT_TEXT(106,445,24,22,$('#day').text());//日
//	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
//	LODOP.ADD_PRINT_TEXT(82,603,141,22,$('#certNo').text());//拨付凭证号 
//	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
//	LODOP.ADD_PRINT_TEXT(240,516,17,22,$($('#amount').children().get(0)).text());//亿
//	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
//	LODOP.ADD_PRINT_TEXT(240,537,17,22,$($('#amount').children().get(1)).text());//千
//	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
//	LODOP.ADD_PRINT_TEXT(240,558,17,22,$($('#amount').children().get(2)).text());//百
//	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
//	LODOP.ADD_PRINT_TEXT(240,580,17,22,$($('#amount').children().get(3)).text());//十
//	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
//	LODOP.ADD_PRINT_TEXT(240,601,17,22,$($('#amount').children().get(4)).text());//万
//	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
//	LODOP.ADD_PRINT_TEXT(240,622,17,22,$($('#amount').children().get(5)).text());//千
//	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
//	LODOP.ADD_PRINT_TEXT(240,645,17,22,$($('#amount').children().get(6)).text());//百
//	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
//	LODOP.ADD_PRINT_TEXT(240,665,17,22,$($('#amount').children().get(7)).text());//十
//	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
//	LODOP.ADD_PRINT_TEXT(240,685,17,22,$($('#amount').children().get(8)).text());//元
//	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
//	LODOP.ADD_PRINT_TEXT(240,705,17,22,$($('#amount').children().get(9)).text());//角
//	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
//	LODOP.ADD_PRINT_TEXT(240,725,17,22,$($('#amount').children().get(10)).text());//分
//	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
//	LODOP.ADD_PRINT_TEXT(227,147,240,41,$('#amountD').text());//大写 金额
//	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
//	LODOP.ADD_PRINT_TEXT(273,148,115,22,$('#pjType').text());//票据种类
	


	};
	
	function testPrint(){
		var LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
		LODOP.PRINT_INIT("蒙城县卫生核算中心拨付凭证");
		LODOP.ADD_PRINT_SETUP_BKIMG("<img border='0' src='/resources/images/appropriatelodop.png'>");
		LODOP.ADD_PRINT_TEXT(129,61,270,22,$('#outAccname').text());
		LODOP.SET_PRINT_STYLEA(0,"Angle",1);
		LODOP.ADD_PRINT_TEXT(128,366,257,22,$('#inAccname').text());
		LODOP.SET_PRINT_STYLEA(0,"Angle",1);
		LODOP.ADD_PRINT_TEXT(154,61,270,22,$('#outAccno').text());
		LODOP.SET_PRINT_STYLEA(0,"Angle",1);
		LODOP.ADD_PRINT_TEXT(153,366,257,22,$('#inAccno').text());
		LODOP.SET_PRINT_STYLEA(0,"Angle",1);
		LODOP.ADD_PRINT_TEXT(179,61,270,22,$('#outBank').text());
		LODOP.SET_PRINT_STYLEA(0,"Angle",1);
		LODOP.ADD_PRINT_TEXT(178,366,257,22,$('#inBank').text());
		LODOP.SET_PRINT_STYLEA(0,"Angle",1);
		LODOP.ADD_PRINT_TEXT(97,204,35,22,$('#year').text());
		LODOP.SET_PRINT_STYLEA(0,"Angle",1);
		LODOP.ADD_PRINT_TEXT(96,270,22,22,$('#month').text());
		LODOP.SET_PRINT_STYLEA(0,"Angle",1);
		LODOP.ADD_PRINT_TEXT(96,316,24,22,$('#day').text());
		LODOP.SET_PRINT_STYLEA(0,"Angle",1);
		LODOP.ADD_PRINT_TEXT(74,488,141,22,$('#certNo').text());
		LODOP.SET_PRINT_STYLEA(0,"Angle",1);
		LODOP.ADD_PRINT_TEXT(220,412,17,22,$($('#amount').children().get(0)).text());
		LODOP.SET_PRINT_STYLEA(0,"Angle",1);
		LODOP.ADD_PRINT_TEXT(220,429,17,22,$($('#amount').children().get(1)).text());
		LODOP.SET_PRINT_STYLEA(0,"Angle",1);
		LODOP.ADD_PRINT_TEXT(220,446,17,22,$($('#amount').children().get(2)).text());
		LODOP.SET_PRINT_STYLEA(0,"Angle",1);
		LODOP.ADD_PRINT_TEXT(220,463,17,22,$($('#amount').children().get(3)).text());
		LODOP.SET_PRINT_STYLEA(0,"Angle",1);
		LODOP.ADD_PRINT_TEXT(220,480,17,22,$($('#amount').children().get(4)).text());
		LODOP.SET_PRINT_STYLEA(0,"Angle",1);
		LODOP.ADD_PRINT_TEXT(220,498,17,22,$($('#amount').children().get(5)).text());
		LODOP.SET_PRINT_STYLEA(0,"Angle",1);
		LODOP.ADD_PRINT_TEXT(220,515,17,22,$($('#amount').children().get(6)).text());
		LODOP.SET_PRINT_STYLEA(0,"Angle",1);
		LODOP.ADD_PRINT_TEXT(220,532,17,22,$($('#amount').children().get(7)).text());
		LODOP.SET_PRINT_STYLEA(0,"Angle",1);
		LODOP.ADD_PRINT_TEXT(220,549,17,22,$($('#amount').children().get(8)).text());
		LODOP.SET_PRINT_STYLEA(0,"Angle",1);
		LODOP.ADD_PRINT_TEXT(220,566,17,22,$($('#amount').children().get(9)).text());
		LODOP.SET_PRINT_STYLEA(0,"Angle",1);
		LODOP.ADD_PRINT_TEXT(220,583,17,22,$($('#amount').children().get(10)).text());
		LODOP.SET_PRINT_STYLEA(0,"Angle",1);
		LODOP.ADD_PRINT_TEXT(221,86,240,26,$('#amountD').text());
		LODOP.SET_PRINT_STYLEA(0,"Angle",1);
		LODOP.ADD_PRINT_TEXT(253,86,115,22,$('#pjType').text());


	}