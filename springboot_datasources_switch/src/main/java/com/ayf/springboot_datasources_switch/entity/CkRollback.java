package com.ayf.springboot_datasources_switch.entity;

import java.util.Date;

public class CkRollback {
    private Integer id;

    private Integer shipid;

    private String porderid;

    private Integer productid;

    private Byte estate;

    private Integer operator;

    private Date operatingTime;

    private String reason;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShipid() {
        return shipid;
    }

    public void setShipid(Integer shipid) {
        this.shipid = shipid;
    }

    public String getPorderid() {
        return porderid;
    }

    public void setPorderid(String porderid) {
        this.porderid = porderid == null ? null : porderid.trim();
    }

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    public Byte getEstate() {
        return estate;
    }

    public void setEstate(Byte estate) {
        this.estate = estate;
    }

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    public Date getOperatingTime() {
        return operatingTime;
    }

    public void setOperatingTime(Date operatingTime) {
        this.operatingTime = operatingTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}