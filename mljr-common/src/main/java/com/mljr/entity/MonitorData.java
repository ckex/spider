package com.mljr.entity;

import org.apache.commons.lang3.time.DateUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

public class MonitorData implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String time;
    private String domain;
    private String serverIp;
    private int totalRequests;
    private String statusCodes;
    private int freq200;
    private int freq301;
    private int freq302;
    private int freq304;
    private int freq307;
    private int freq401;
    private int freq403;
    private int freq404;
    private int freq500;
    private int freq501;
    private int freq504;
    private int freqParseFail;
    private int onErrorCount;
    private int onSuccessCount;

    public int getFreqParseFail() {
        return freqParseFail;
    }

    public void setFreqParseFail(int freqParseFail) {
        this.freqParseFail = freqParseFail;
    }

    public String getStatusCodes() {
        return statusCodes;
    }

    public void setStatusCodes(String statusCodes) {
        this.statusCodes = statusCodes;
    }

    // 成功率
    private double successRate;

    // 解析成功率
    private double parseSuccessRate;

    public double getParseSuccessRate() {
        if(freq200==0){
            return 0;
        }
        BigDecimal result = new BigDecimal((freq200-freqParseFail) * 100)
                .divide(new BigDecimal(freq200), 2, BigDecimal.ROUND_HALF_EVEN);
        return result.doubleValue();
    }

    public void setParseSuccessRate(double parseSuccessRate) {
        this.parseSuccessRate = parseSuccessRate;
    }

    private int diffTime;

    public int getFreq301() {
        return freq301;
    }

    public void setFreq301(int freq301) {
        this.freq301 = freq301;
    }

    public int getFreq302() {
        return freq302;
    }

    public void setFreq302(int freq302) {
        this.freq302 = freq302;
    }

    public int getFreq304() {
        return freq304;
    }

    public void setFreq304(int freq304) {
        this.freq304 = freq304;
    }

    public int getFreq307() {
        return freq307;
    }

    public void setFreq307(int freq307) {
        this.freq307 = freq307;
    }

    public int getDiffTime() {
        try {
            Date dbDate = DateUtils.parseDate(time, "yy-MM-dd-HH-mm");
            long diff = System.currentTimeMillis() - dbDate.getTime();
            // 时间单位:分钟
            return (int) (diff / 1000 / 60);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void setDiffTime(int diffTime) {
        this.diffTime = diffTime;
    }




    public MonitorData() {
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public double getSuccessRate() {
        if (totalRequests == 0) {
            return 0;
        }
        BigDecimal result = new BigDecimal(freq200 * 100).divide(new BigDecimal(totalRequests), 2, BigDecimal.ROUND_HALF_EVEN);

        return result.doubleValue();
    }

    public void setSuccessRate(double successRate) {
        this.successRate = successRate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public int getFreq200() {
        return freq200;
    }

    public void setFreq200(int freq200) {
        this.freq200 = freq200;
    }

    public int getFreq401() {
        return freq401;
    }

    public void setFreq401(int freq401) {
        this.freq401 = freq401;
    }

    public int getFreq403() {
        return freq403;
    }

    public void setFreq403(int freq403) {
        this.freq403 = freq403;
    }

    public int getFreq404() {
        return freq404;
    }

    public void setFreq404(int freq404) {
        this.freq404 = freq404;
    }

    public int getFreq500() {
        return freq500;
    }

    public void setFreq500(int freq500) {
        this.freq500 = freq500;
    }

    public int getFreq501() {
        return freq501;
    }

    public void setFreq501(int freq501) {
        this.freq501 = freq501;
    }

    public int getFreq504() {
        return freq504;
    }

    public void setFreq504(int freq504) {
        this.freq504 = freq504;
    }

    public int getTotalRequests() {
        return totalRequests;
    }

    public void setTotalRequests(int totalRequests) {
        this.totalRequests = totalRequests;
    }
    
    public int getOnErrorCount() {
		return onErrorCount;
	}

	public void setOnErrorCount(int onErrorCount) {
		this.onErrorCount = onErrorCount;
	}

	public int getOnSuccessCount() {
		return onSuccessCount;
	}

	public void setOnSuccessCount(int onSuccessCount) {
		this.onSuccessCount = onSuccessCount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + diffTime;
		result = prime * result + ((domain == null) ? 0 : domain.hashCode());
		result = prime * result + freq200;
		result = prime * result + freq301;
		result = prime * result + freq302;
		result = prime * result + freq304;
		result = prime * result + freq307;
		result = prime * result + freq401;
		result = prime * result + freq403;
		result = prime * result + freq404;
		result = prime * result + freq500;
		result = prime * result + freq501;
		result = prime * result + freq504;
		result = prime * result + freqParseFail;
		result = prime * result + onErrorCount;
		result = prime * result + onSuccessCount;
		long temp;
		temp = Double.doubleToLongBits(parseSuccessRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((serverIp == null) ? 0 : serverIp.hashCode());
		result = prime * result + ((statusCodes == null) ? 0 : statusCodes.hashCode());
		temp = Double.doubleToLongBits(successRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		result = prime * result + totalRequests;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MonitorData other = (MonitorData) obj;
		if (diffTime != other.diffTime)
			return false;
		if (domain == null) {
			if (other.domain != null)
				return false;
		} else if (!domain.equals(other.domain))
			return false;
		if (freq200 != other.freq200)
			return false;
		if (freq301 != other.freq301)
			return false;
		if (freq302 != other.freq302)
			return false;
		if (freq304 != other.freq304)
			return false;
		if (freq307 != other.freq307)
			return false;
		if (freq401 != other.freq401)
			return false;
		if (freq403 != other.freq403)
			return false;
		if (freq404 != other.freq404)
			return false;
		if (freq500 != other.freq500)
			return false;
		if (freq501 != other.freq501)
			return false;
		if (freq504 != other.freq504)
			return false;
		if (freqParseFail != other.freqParseFail)
			return false;
		if (onErrorCount != other.onErrorCount)
			return false;
		if (onSuccessCount != other.onSuccessCount)
			return false;
		if (Double.doubleToLongBits(parseSuccessRate) != Double.doubleToLongBits(other.parseSuccessRate))
			return false;
		if (serverIp == null) {
			if (other.serverIp != null)
				return false;
		} else if (!serverIp.equals(other.serverIp))
			return false;
		if (statusCodes == null) {
			if (other.statusCodes != null)
				return false;
		} else if (!statusCodes.equals(other.statusCodes))
			return false;
		if (Double.doubleToLongBits(successRate) != Double.doubleToLongBits(other.successRate))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		if (totalRequests != other.totalRequests)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MonitorData [time=" + time + ", domain=" + domain + ", serverIp=" + serverIp + ", totalRequests="
				+ totalRequests + ", statusCodes=" + statusCodes + ", freq200=" + freq200 + ", freq301=" + freq301
				+ ", freq302=" + freq302 + ", freq304=" + freq304 + ", freq307=" + freq307 + ", freq401=" + freq401
				+ ", freq403=" + freq403 + ", freq404=" + freq404 + ", freq500=" + freq500 + ", freq501=" + freq501
				+ ", freq504=" + freq504 + ", freqParseFail=" + freqParseFail + ", onErrorCount=" + onErrorCount
				+ ", onSuccessCount=" + onSuccessCount + ", successRate=" + successRate + ", parseSuccessRate="
				+ parseSuccessRate + ", diffTime=" + diffTime + "]";
	}

}