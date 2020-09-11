<!DOCTYPE html>
<html lang="zh-hans">
<head>
    <meta charset="UTF-8">
    <title>美丽世界</title>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/font-awesome.min.css">
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

        .form-register {
            width: 100%;
            max-width: 330px;
            padding: 15px;
            margin: auto;
        }
        .form-register .checkbox {
            font-weight: 400;
        }
        .form-register .form-control {
            position: relative;
            box-sizing: border-box;
            height: auto;
            padding: 10px;
            font-size: 16px;
        }
        .form-register .form-control:focus {
            z-index: 2;
        }
        .form-register input[type="email"] {
            margin-bottom: -1px;
            border-bottom-right-radius: 0;
            border-bottom-left-radius: 0;
        }
        .form-register input[name="nickname"] {
            margin-bottom: 10px;
            border-top-left-radius: 0;
            border-top-right-radius: 0;
        }
    </style>
</head>
<body class="text-center">
<form class="form-register" method="post" action="/register/helper">
    <h1 class="h3 mb-3 font-weight-normal">欢迎注册</h1>
    <label for="username" class="sr-only">用户名</label>
    <input type="text" id="username" name="username" class="form-control" placeholder="用户名" required="" autofocus="">
    <label for="password" class="sr-only">密码</label>
    <input type="password" id="password" name="password" class="form-control" placeholder="密码" required="">
    <label for="nickname" class="sr-only">昵称</label>
    <input type="text" id="nickname" name="nickname" class="form-control" placeholder="昵称" required="">
    <button class="btn btn-lg btn-outline-secondary btn-block" type="submit">注册</button>
</body>
</html>