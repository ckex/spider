<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <title>运营商数据查询</title>
    <link rel="stylesheet" href="../../css/bootstrap.min.css"/>
    <script type="text/javascript" src="../../js/jquery-1.7.2.js"></script>
</head>
<body>
<div class="container" style="width: 1140px">
    <div class="row">

        <form class="form-horizontal" method="post" action="/chinaMobile/getAllInfos">
            <fieldset>
                <div id="legend" class="">
                    <legend class="">运行商数据查询</legend>
                </div>
                <div class="control-group">

                    <!-- Text input-->
                    <label class="control-label" for="input01">手机号</label>
                    <div class="controls">
                        <input id="telno" name="telno" type="text" class="input-xlarge"/>
                        <p class="help-block">请输入11位手机号</p>
                    </div>
                </div>

                <div class="control-group">

                    <!-- Text input-->
                    <label class="control-label" for="input01">服务密码</label>
                    <div class="controls">
                        <input name="password" type="password" class="input-xlarge"/>
                    </div>
                </div>

                <div class="control-group">

                    <!-- Text input-->
                    <label class="control-label" for="input01">姓名</label>
                    <div class="controls">
                        <input name="userName" type="text" class="input-xlarge"/>
                    </div>
                </div>

                <div class="control-group">

                    <!-- Text input-->
                    <label class="control-label" for="input01">身份证号</label>
                    <div class="controls">
                        <input name="idcard" type="text" class="input-xlarge"/>
                    </div>
                </div>

                <div class="control-group">

                    <!-- Text input-->
                    <label class="control-label" for="input01">短信验证码</label>
                    <div class="controls">
                        <input name="dtm" type="text" class="input-xlarge"/>
                        <button type="button" onclick="sendCode()" class="btn btn-primary">发送短信验证码</button>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label"></label>

                    <!-- Button -->
                    <div class="controls">
                        <button type="submit" class="btn btn-success">获取运营商数据</button>
                    </div>
                </div>

            </fieldset>
        </form>

    </div>
</div>

<script>
    function sendCode(){
        var telno=$("#telno").val();
        $.ajax({
            type: "POST",
            url: "/getSmsCode/"+telno,
            cache: false,
            async: true,
            success: function (json) {
                alert(json);
            }

        });
    }
</script>

</body>
</html>
