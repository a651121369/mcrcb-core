function login() {
	 $("#msg").html("登录中...");
	 $("#loginForm").ajaxForm({  
		   beforeSubmit: function(formData, jqForm, options) {
			   var userName =  $("#j_username").val();
			   var password =  $("#j_password").val();
			   if(userName == "") {
				   //alert("请输入用户名");
				   $("#msg").html("请输入用户名");
				   return false;
			   }else if(password == "") {
				   $("#msg").html("请输入密码");
				   return false;
			   }
			   return true;
		   },
		   success: function(responseText, statusText) {
				 if(responseText.success == true) {
					 window.location = "./";
				 }else {
					 $("#msg").html(responseText.message);
				 }				   			   	
		   },
		   url: 'j_spring_security_check',                 //默认是form的action， 如果申明，则会覆盖  
		  // dataType: 'json',           //html(默认), xml, script, json...接受服务端返回的类型  
		   timeout: 10000              //限制请求的时间，当请求大于10秒后，跳出请求  
	}); 
	 
}

//JavaScript Document
$(function() {

	
		
	 /**
	  * 绑定重置事件
	  */
	 $("#resetBtn").click(function() {
		 $("#j_username").attr("value",'');
		 $("#j_password").attr("value",'');
	 });
	 
});