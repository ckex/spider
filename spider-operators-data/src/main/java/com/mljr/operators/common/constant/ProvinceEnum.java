package com.mljr.operators.common.constant;

/**
 * @author gaoxi
 * @time 2017/3/1
 */
public enum ProvinceEnum {

    BJ("北京"),
    AH("安徽"),
    CQ("重庆"),
    FJ("福建"),
    GD("广东"),
    GS("甘肃"),
    GX("广西"),
    GZ("贵州"),
    HB("湖北"),
    HN("湖南"),
    HE("河北"),
    HA("河南"),
    HI("海南"),
    HL("黑龙江"),
    JS("江苏"),
    JL("吉林"),
    JX("江西"),
    LN("辽宁"),
    NM("内蒙古"),
    NX("宁夏"),
    QH("青海"),
    SD("山东"),
    SH("上海"),
    SX("山西"),
    SN("陕西"),
    SC("四川"),
    TJ("天津"),
    XJ("新疆"),
    XZ("西藏"),
    YN("云南"),
    ZJ("浙江"),
    ;

    private String name;

    ProvinceEnum(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }
}
