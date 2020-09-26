<!DOCTYPE html>
<html lang="zh-hans">
    <head>
        <meta charset="UTF-8">
        <title>美丽世界</title>
        <link rel="stylesheet" href="/css/bootstrap.min.css">
        <link rel="stylesheet" href="/css/font-awesome.min.css">
        <script src="https://lib.sinaapp.com/js/jquery/2.0.2/jquery-2.0.2.min.js">
        </script>
        <style>
            body {
                background-image: url(/images/email-pattern.png);
            }

            body {
                height: 100%;
            }

            body {
                display: -ms-flexbox;
                display: flex;
                -ms-flex-align: center;
                align-items: center;
                padding-top: 120px;
                padding-bottom: 40px;
            }

            .form-signin {
                width: 100%;
                max-width: 330px;
                padding: 15px;
                margin: auto;
            }
            .form-signin .checkbox {
                font-weight: 400;
            }
            .form-signin .form-control {
                position: relative;
                box-sizing: border-box;
                height: auto;
                padding: 10px;
                font-size: 16px;
            }
            .form-signin .form-control:focus {
                z-index: 2;
            }
            .form-signin input[type="email"] {
                margin-bottom: -1px;
                border-bottom-right-radius: 0;
                border-bottom-left-radius: 0;
            }
            .form-signin input[type="password"] {
                margin-bottom: 0;
                border-top-left-radius: 0;
                border-top-right-radius: 0;
            }
            .form-signin button[type="submit"] {
                margin-top: 20px;
                border-top-left-radius: 0;
                border-top-right-radius: 0;
            }
        </style>

    </head>
    <body class="text-center">
        <form class="form-signin" method="post" action="/login">
            <h1 class="h3 mb-3 font-weight-normal">请登录</h1>
            <label for="username" class="sr-only">用户名</label>
            <input type="text" id="username" name="username" class="form-control"
                   placeholder="用户名" autofocus="" autocomplete="off">
            <label for="password" class="sr-only">密码</label>
            <input type="password" id="password" name="password" class="form-control" placeholder="密码">
            <div class="input-group">
                <label for="verifyInput" class="sr-only">验证码</label>
                <input id="verifyInput" name="verifyInput" class="form-control" placeholder="验证码" required="">
                <img id="verifyImg" class="input-group-append" onclick="changeVerifyCode()" src="getVerify">
            </div>
            <button id="submit" class="btn btn-lg btn-outline-secondary btn-block" type="submit">登录</button>
            <a href="/register">立即注册</a>
    </body>
    <script async type="text/javascript">
        <#--$(document).ready(function(){-->
            <#--if ('${mes}'){-->
                <#--var mes = '${mes}';-->
                <#--alert(mes);-->
            <#--}-->
        <#--})-->

        function changeVerifyCode(){
            // 加时间戳，防止浏览器缓存
            var src = "getVerify?date=" + new Date();
            $("#verifyImg").attr("src", src);
        }
    </script>
</html>