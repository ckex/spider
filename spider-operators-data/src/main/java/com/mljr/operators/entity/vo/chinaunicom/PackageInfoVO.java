package com.mljr.operators.entity.vo.chinaunicom;

import java.io.Serializable;
import java.util.List;

/**
 * @author gaoxi
 * @Time 2017/2/17
 */
public class PackageInfoVO implements Serializable {

    private static final long serialVersionUID = 1900827907230103166L;

    private String packageName;

    private List<DiscntInfoChinaUnicomVO> discntInfos;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public List<DiscntInfoChinaUnicomVO> getDiscntInfos() {
        return discntInfos;
    }

    public void setDiscntInfos(List<DiscntInfoChinaUnicomVO> discntInfos) {
        this.discntInfos = discntInfos;
    }

    public static class DiscntInfoChinaUnicomVO {

        private String discntName;

        private String discntFee;

        public String getDiscntName() {
            return discntName;
        }

        public void setDiscntName(String discntName) {
            this.discntName = discntName;
        }

        public String getDiscntFee() {
            return discntFee;
        }

        public void setDiscntFee(String discntFee) {
            this.discntFee = discntFee;
        }
    }
}

