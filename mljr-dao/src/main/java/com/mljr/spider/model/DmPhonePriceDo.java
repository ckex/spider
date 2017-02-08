package com.mljr.spider.model;

import java.io.Serializable;
import java.util.Date;

public class DmPhonePriceDo implements Serializable {
    private static final long serialVersionUID = 1486448279887L;

    private Long id;
    private String brand;
    private String title;
    private Date createDate;
    private String website;
    private Float price;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getWebsite() {
        return website;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getPrice() {
        return price;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((brand == null) ? 0 : brand.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((createDate == null) ? 0 : createDate.hashCode());
        result = prime * result + ((website == null) ? 0 : website.hashCode());
        result = prime * result + ((price == null) ? 0 : price.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        DmPhonePriceDo other = (DmPhonePriceDo) obj;
        if (id == null) {
            if(other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (brand == null) {
            if(other.brand != null)
                return false;
        } else if (!brand.equals(other.brand))
            return false;
        if (title == null) {
            if(other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (createDate == null) {
            if(other.createDate != null)
                return false;
        } else if (!createDate.equals(other.createDate))
            return false;
        if (website == null) {
            if(other.website != null)
                return false;
        } else if (!website.equals(other.website))
            return false;
        if (price == null) {
            if(other.price != null)
                return false;
        } else if (!price.equals(other.price))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "DmPhonePriceDo[id=" + id + ", brand=" + brand + ", title=" + title + ", createDate=" + createDate + ", website=" + website + ", price=" + price+ "]";
    }
}
