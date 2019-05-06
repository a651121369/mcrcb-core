<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
    <title>蒙城卫健委财政专户资金管理系统</title>
    <script type="text/javascript" src="${basePath}/resources/login/js/jquery.min.js"></script>
    <script type="text/javascript" src="${basePath}/resources/login/js/jquery.form.js"></script>
    <!--[if IE 6]>
    <script src="/resources/js/EvPNG.js" type="text/javascript"></script>
    <script type="text/javascript">
        EvPNG.fix('.login_2,.textbox_yhm,.textbox_mm,img,background');
    </script>
    <![endif]-->
    <script type="text/javascript" src="${basePath}/resources/login/js/login.js"></script>
    <link rel="stylesheet" type="text/css" href="${basePath}/resources/css/login.css"/>
    <link rel="icon" href="${basePath}/resources/images/core/login/favicon.ico" type="image/x-icon"/>
    <link rel="shortcut icon" href="${basePath}/resources/images/core/login/favicon.ico" type="image/x-icon"/>
</head>
<body>
<div class="dltitle">
    <img src="${basePath}/resources/images/core/login/title.png" border="0"/>
</div>

<div class="login_box">
    <div class="login_2">
        <form id="loginForm" method="post">
            <div class="login_title">
                <img src="${basePath}/resources/images/core/login/u1.png" border="0"/><span>用户登录</span><span id="msg"></span>
            </div>
            <p>用户名</p>
            <p>
                <input id="j_username" name="j_username" class="textbox_yhm"/>
            </p>

            <p>密&nbsp;&nbsp;码</p>
            <p>
                <input type="password" id="j_password" name="j_password" class="textbox_mm"/>
            </p>


            <p>
                <input type="submit" onclick="login();" class="login_btn"
                      value=""/><input id="resetBtn" type="button" class="login_btn_cz" value=""/>
            </p>


        </form>
    </div>
    <div class="copy">
        <p> Copyright @ 2015 中裕合普 </p>
        <%--<p>地址：高新区黄山路601号安徽省科技创新公共服务中心712/717 电话：0551-65307373 </p>--%>
    </div>
</div>

</body>
</html>