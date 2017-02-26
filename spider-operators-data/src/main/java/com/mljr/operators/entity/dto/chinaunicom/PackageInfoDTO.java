package com.mljr.operators.entity.dto.chinaunicom;

import com.mljr.operators.entity.model.operators.PackageInfo;
import com.mljr.operators.entity.model.operators.PackageInfoDetail;

import java.io.Serializable;
import java.util.List;

/**
 * @author gaoxi
 * @time 2017/2/25
 */
public class PackageInfoDTO implements Serializable {

    private static final long serialVersionUID = -7064868703943364475L;

    private PackageInfo packageInfo;

    private List<PackageInfoDetail> detailList;

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }

    public void setPackageInfo(PackageInfo packageInfo) {
        this.packageInfo = packageInfo;
    }

    public List<PackageInfoDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<PackageInfoDetail> detailList) {
        this.detailList = detailList;
    }
}
