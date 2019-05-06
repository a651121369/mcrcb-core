dateFullFormat = function(time){
	var datetime = new Date();
    datetime.setTime(time);
    var year = datetime.getFullYear();
    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();
    var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
    var second = datetime.getSeconds()< 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
    return year + "-" + month + "-" + date+" "+hour+":"+minute+":"+second;
};
dateFullFormatZH = function(time){
	var datetime = new Date();
    datetime.setTime(time);
    var year = datetime.getFullYear();
    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    return year + "年" + month + "月" + date+"日";
};
dateFormat = function(time){
	var datetime = new Date();
    datetime.setTime(time);
    var year = datetime.getFullYear();
    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    return year + "-" + month + "-" + date;
};
dateFormat2 = function(time){
	var datetime = new Date();
    datetime.setTime(time);
    var year = datetime.getFullYear();
    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    return year + "" + month + "" + date;
};

delTimeFormat = function(time){
	var mse = 1000;//1秒
	var mi = mse*60;//1分钟
	var hor = mi*60;//1小时
	var day = hor*24;//1天
//	console.info(mse+"=="+mi+"=="+hor+"=="+day);
	if(time<mse){
		return time+"毫秒";
	}else if(time>=mse && time<mi){
		return Math.floor(time/mse)+"秒";
	}else if(mi<=time && time<hor){
		return Math.floor(time/mi)+"分"+Math.floor((time%mi)/mse)+"秒";
	}else if(hor<=time && time<day){
		return Math.floor(time/hor)+"小时"+Math.floor((time%hor)/mi)+"分"+Math.floor(((time%hor)%mi)/mse)+"秒";
	}
};

 //日期加减法
   addDate= function(date,days){
       var d=new Date(date);
       d.setDate(d.getDate()+days);
       var m=d.getMonth()+1;
       var dd = d.getDate() ;
       return d.getFullYear()+'-'+(m+1>10?m:"0"+m)+'-'+(dd+1>10?dd:"0"+dd) ;
     };
/*****格式化货币*******/
     RMBformat = function(num){
    	 num = num.toString().replace(/\$|\,/g,'');  
    	    if(isNaN(num))  
    	        num = "0";  
    	    sign = (num == (num = Math.abs(num)));  
    	    num = Math.floor(num*100+0.50000000001);  
    	    cents = num%100;  
    	    num = Math.floor(num/100).toString();  
    	    if(cents<10)  
    	    cents = "0" + cents;  
    	    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)  
    	    num = num.substring(0,num.length-(4*i+3))+','+  
    	    num.substring(num.length-(4*i+3));  
    	    return (((sign)?'':'-') + num + '.' + cents);
     };
/***************************人民币小写转换大写*****************************************/
//     function DX(n) {
// 		if (!/^(0|[1-9]\d*)(\.\d+)?$/.test(n))
// 			return "数据非法";
// 		var unit = "仟佰拾亿仟佰拾万仟佰拾元角分", str = "";
// 		n += "00";
// 		var p = n.indexOf('.');
// 		if (p >= 0)
// 			n = n.substring(0, p) + n.substr(p+1, 2);
// 		unit = unit.substr(unit.length - n.length);
// 		for (var i=0; i < n.length; i++)
// 			str += '零壹贰叁肆伍陆柒捌玖'.charAt(n.charAt(i)) + unit.charAt(i);
// 			return str.replace(/零(仟|佰|拾|角)/g, "零").replace(/(零)+/g, "零").replace(/零(万|亿|元)/g, "$1").replace(/(亿)万|壹(拾)/g, "$1$2").replace(/^元零?|零分/g, "").replace(/元$/g, "元整");
// 	}
     
     
     
     function  DX(numberValue){
    	 var numberValue=new String(Math.round(numberValue*100)); // 数字金额  
    	 var chineseValue=""; // 转换后的汉字金额  
    	 var String1 = "零壹贰叁肆伍陆柒捌玖"; // 汉字数字  
    	 var String2 = "万仟佰拾亿仟佰拾万仟佰拾元角分"; // 对应单位  
    	 var len=numberValue.length; // numberValue 的字符串长度  
    	 var Ch1; // 数字的汉语读法  
    	 var Ch2; // 数字位的汉字读法  
    	 var nZero=0; // 用来计算连续的零值的个数  
    	 var String3; // 指定位置的数值  
    	 if(len>15){  
    	 alert("超出计算范围");  
    	 return "";  
    	 }  
    	 if (numberValue==0){  
    	 chineseValue = "零元整";  
    	 return chineseValue;  
    	 }  
    	 String2 = String2.substr(String2.length-len, len); // 取出对应位数的STRING2的值  
    	 for(var i=0; i<len; i++){  
    	 String3 = parseInt(numberValue.substr(i, 1),10); // 取出需转换的某一位的值  
    	 if ( i != (len - 3) && i != (len - 7) && i != (len - 11) && i !=(len - 15) ){  
    	 if ( String3 == 0 ){  
    	 Ch1 = "";  
    	 Ch2 = "";  
    	 nZero = nZero + 1;  
    	 }  
    	 else if ( String3 != 0 && nZero != 0 ){  
    	 Ch1 = "零" + String1.substr(String3, 1);  
    	 Ch2 = String2.substr(i, 1);  
    	 nZero = 0;  
    	 }  
    	 else{  
    	 Ch1 = String1.substr(String3, 1);  
    	 Ch2 = String2.substr(i, 1);  
    	 nZero = 0;  
    	 }  
    	 }  
    	 else{ // 该位是万亿，亿，万，元位等关键位  
    	 if( String3 != 0 && nZero != 0 ){  
    	 Ch1 = "零" + String1.substr(String3, 1);  
    	 Ch2 = String2.substr(i, 1);  
    	 nZero = 0;  
    	 }  
    	 else if ( String3 != 0 && nZero == 0 ){  
    	 Ch1 = String1.substr(String3, 1);  
    	 Ch2 = String2.substr(i, 1);  
    	 nZero = 0;  
    	 }  
    	 else if( String3 == 0 && nZero >= 3 ){  
    	 Ch1 = "";  
    	 Ch2 = "";  
    	 nZero = nZero + 1;  
    	 }  
    	 else{  
    	 Ch1 = "";  
    	 Ch2 = String2.substr(i, 1);  
    	 nZero = nZero + 1;  
    	 }  
    	 if( i == (len - 11) || i == (len - 3)){ // 如果该位是亿位或元位，则必须写上  
    	 Ch2 = String2.substr(i, 1);  
    	 }  
    	 }  
    	 chineseValue = chineseValue + Ch1 + Ch2;  
    	 }  
    	 if ( String3 == 0 ){ // 最后一位（分）为0时，加上“整”  
    	 chineseValue = chineseValue + "整";  
    	 }  
    	 return chineseValue; 
     }
/***********************************input输入控制，只允许输入数字和小数点**********************************************/
 	clearNoNum = function(obj)
     {
         //先把非数字的都替换掉，除了数字和.
         obj.value = obj.value.replace(/[^\d.]/g,"");
         //必须保证第一个为数字而不是.
         obj.value = obj.value.replace(/^\./g,"");
         //保证只有出现一个.而没有多个.
         obj.value = obj.value.replace(/\.{2,}/g,".");
         //保证.只出现一次，而不能出现两次以上
         obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
         obj.value = RMBformat(obj.value);
     };