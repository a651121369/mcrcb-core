/****************************jQuery入口*******************************/
$(function() {
//	var LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
	$.ajax({
		type: "post", 
		traditional: true,
		url: "/WjwPaydetail/getLodopPageList", 
//		dataType: "json", 
        data:{ids: $('#ids').val()},
		success: function (data){
			batchPrint(data);
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
function printPreview(){
	var LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
	createPrintPage();
	LODOP.PREVIEW();
}
function batchPrint(list){
	var LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));	
	//参数(距离页面头部,距离页面左边距离,文本宽度,文本高度,文本内容)
	LODOP.PRINT_INIT("蒙城县卫生核算中心支付凭证");
	LODOP.ADD_PRINT_SETUP_BKIMG("<img border='0' src='/resources/images/lodop.jpg'>");//放入背景图片
	for(i=0;i<list.length;i++){
		createPrintPage(list[i]);
	}
	LODOP.PRINT();
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



function createPrintPage(list){
	var LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
	LODOP.NewPage();
//	LODOP.SET_SHOW_MODE("BKIMG_IN_PREVIEW",1);//设置显示背景图片
//	LODOP.PRINT_DESIGN();//开启打印设置
	LODOP.ADD_PRINT_TEXT(97,121,270,22,list.outAccname);
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(99,447,257,22,list.inName);
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(122,121,270,22,list.outAccno);
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(120,447,257,22,list.inAccno);
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(145,121,270,22,list.outBank);
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(143,447,257,22,list.inBank);
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(164,121,270,22,list.zjFld);
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(164,447,257,22,list.payWay==1?'现金':'转账');
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(183,121,270,22,list.topYsdw);
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
//	LODOP.ADD_PRINT_TEXT(184,447,257,22,list.footYsdw);
	LODOP.ADD_PRINT_TEXT(184,447,257,22,list.unitName);
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(203,121,270,22,list.zbDetail);
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(203,447,257,22,list.funcFl);
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(224,121,270,22,list.yt);
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(221,447,257,22,list.ecnoFl);
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(77,273,35,22,list.payTime.substr(0,4));
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(77,334,22,22,list.payTime.substr(4,2));
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(77,380,24,22,list.payTime.substr(6,2));
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	LODOP.ADD_PRINT_TEXT(73,561,125,22,list.operNo);
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	
	list.amount = list.amount+"";
	if(list.amount.contains('.')){
		var p = list.amount.indexOf('.');
		var q = list.amount.substring(p+1);
		if(q.length==1){
			list.amount = list.amount+"0";
		}
		var amount = (list.amount).replace('.','');
		for(var i=0;i<amount.length;i++){
			$($('#amount').children().get((11-(amount.length))+i)).text(amount[i]);			
		}
		$($('#amount').children().get((11-(amount.length+1)))).text("￥");
	}else{
		var amount = (list.amount)+'00';
		for(var i=0;i<amount.length;i++){
			$($('#amount').children().get((11-(amount.length))+i)).text(amount[i]);
		}
		$($('#amount').children().get((11-(amount.length+1)))).text("￥");
	}
	
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
	LODOP.ADD_PRINT_TEXT(274,136,206,41,DX(list.amount));
	LODOP.SET_PRINT_STYLEA(0,"Angle",1);
	//清空页面金额数字
	 for(i=0;i<$('#amount').children().length;i++){
		 $($('#amount').children().get(i)).text('');
	 }
	};