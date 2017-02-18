<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <title>运营商数据查询</title>
    <link rel="stylesheet" href="../../css/bootstrap.min.css"/>
    <style>
        p{
            color: red;
            font-size: 20px;
        }
    </style>
</head>
<body>
<div class="container" style="width: 1140px">
    <div class="row">
        <p>01 用户在运营商信息</p>
        <div>${accountInfo?html}</div>

        <p>02 套餐信息</p>
        <div>${planInfo?html}</div>
        
        <p>03 用户话费信息</p>
        <div>${costInfo?html}</div>

        <p>04 网络流量信息</p>
        <div>${flowBill?html}</div>

        <p>05 短信使用信息</p>
        <div>${smsBill?html}</div>

        <p>06 通话详单</p>
        <div>${callBill?html}</div>

    </div>
</div>
</body>
</html>
