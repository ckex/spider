package com.mljr.operators.common.constant;

public enum ErrorCodeEnum {

    C65537(65537, "机构账号不存在"),

    C65538(65538, "机构账号已冻结"),

    C65539(65539, "机构账号已过期"),

    C65540(65540, "基本信息不能为空"),

    IDCARD_ERR(65541, "身份证号码为空或身份证号码不合法"),

    CELL_ERR(65542, "手机号码为空或手机号码不合法"),

    C65543(65543, "姓名为空或姓名不合法"),

    C65544(65544, "联系人姓名不合法"),

    C65545(65545, "联系人手机号码不合法"),

    C65552(65552, "联系人类型不合法"),

    C65553(65553, "数据源名称不合法"),

    C65554(65554, "手机号码不支持认证"),

    OPERATOR_ERR(65555, "手机号码所在运营商正在维护"),

    TOKEN_FAIL(65556, "生成token失败"),

    TOKEN_SUCC(65557, "生成token成功");

    private int value;

    private String name;

    ErrorCodeEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static ErrorCodeEnum indexOf(int value) {
        for (ErrorCodeEnum enums : ErrorCodeEnum.values()) {
            if (value == enums.value) {
                return enums;
            }
        }
        return null;
    }
}
