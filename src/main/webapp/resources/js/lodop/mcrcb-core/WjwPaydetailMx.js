/****************************jQuery入口*******************************/
$(function() {
//	console.log($('#cnnNo').val());
	var time = $('#sqTime').val();
	$.ajax({
		type: "post", 
		traditional: true,
		url: "/select/Wjw_Paydetail_Lodop", 
		dataType: "json", 
        data:{'connNo':$('#cnnNo').val()},
		success: function (data){
			var result = data.rows;
			var amount = new Number();
			$('.rm').remove();
			for(var i=0;i<result.length;i++){
//				$('tbody').append('<tr><td>'+(i+1)+'</td><td>'+result[i].zbDetail+'</td><td>'+result[i].funcFl+'</td>'
//						+'<td>'+result[i].ecnoFl+'</td><td>'+result[i].yt+'</td><td>'+result[i].inName+'</td>'
//						+'<td>'+result[i].inBank+'</td><td>'+result[i].inAccno+'</td><td>￥'+result[i].amount+'</td></tr>');
				$('#here').append('<tr class="rm"><td>'+(i+1)+'</td><td>'+result[i].zbDetail+'</td><td>21003-基层医疗卫生机构</td>'
						+'<td>'+result[i].ecnoFl+'</td><td>'+result[i].yt+'</td><td>'+result[i].inName+'</td>'
						+'<td>'+result[i].inBank+'</td><td>'+result[i].inAccno+'</td><td align="right">￥'+result[i].amount+'</td></tr>');
				amount += parseFloat(result[i].amount);

//				if((i+1)%7==0){
//					$('#amountX').text('￥'+amount);
//					$('#amountD').text(DX(amount));
//					amount = 0;
////					$('#here').append('<tr class="nextPage"><td>'+(i+1)+'</td><td>'+result[i].zbDetail+'</td><td>21003-基层医疗卫生机构</td>'
////							+'<td>'+result[i].ecnoFl+'</td><td>'+result[i].yt+'</td><td>'+result[i].inName+'</td>'
////							+'<td>'+result[i].inBank+'</td><td>'+result[i].inAccno+'</td><td align="right">￥'+result[i].amount+'</td></tr>');
//					$('#here').append('<tr class="nextPage"></tr>')
//				}
				
			}

			if(result.length<7){
				for(var j=0;j<(6-result.length);j++){
					$('#here').append('<tr class="rm"><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;'+
					'</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>');
				}
			}
			$('#unitName').append(result[0].unitName);
			$('#unitNo').append(result[0].unitNo);
			if(result[0].payWay=='1'){
//				$('#payWay').append('直接支付');
				$('#payWay').text('支出类型：直接支付');
			}else{
//				$('#payWay').append('转账');
				$('#payWay').text('支出类型：转账');
			}
//			$('#amountX').text('￥'+Math.round(amount*100)/100);
			$('#amountX').text('￥'+amount.toFixed(2));
			$('#amountD').text(DX(amount));
			$('#dateFormat').text(time.substr(0,4)+"年"+time.substr(4,2)+"月"+time.substr(6,2)+"日");
//			console.log(time.substr(0,4));
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
		$(value).hide();
		pagesetup_null();
		window.print();
//		$('#div').printArea();
		$(value).show();
	};
/****************************************************************/
	//设置网页打印的页眉页脚为空
    function pagesetup_null(){
        try{
            var RegWsh = new ActiveXObject("WScript.Shell");
            hkey_key="header";
            RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,"");
            hkey_key="footer";
            RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,"");
        }catch(e){}
    }
});