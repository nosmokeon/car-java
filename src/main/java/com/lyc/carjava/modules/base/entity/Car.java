package com.lyc.carjava.modules.base.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 电动车
 * </p>
 *
 * @author lyc
 * @since 2020-06-20
 */
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 车牌号
     */
    private String carNumber;

    /**
     * 车型
     */
    private String carType;

    /**
     * 车辆识别代号
     */
    private String vin;

    /**
     * 是否报废
     */
    private Integer isDestroied;

    /**
     * 现在所在地
     */
    private String nowPlace;

    /**
     * 剩余电量
     */
    private Integer remianPower;

    private LocalDateTime createTime;

    private String createBy;

    private LocalDateTime updateTime;

    private String updateBy;

    private String delFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }
    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }
    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }
    public Integer getIsDestroied() {
        return isDestroied;
    }

    public void setIsDestroied(Integer isDestroied) {
        this.isDestroied = isDestroied;
    }
    public String getNowPlace() {
        return nowPlace;
    }

    public void setNowPlace(String nowPlace) {
        this.nowPlace = nowPlace;
    }
    public Integer getRemianPower() {
        return remianPower;
    }

    public void setRemianPower(Integer remianPower) {
        this.remianPower = remianPower;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "Car{" +
            "id=" + id +
            ", carNumber=" + carNumber +
            ", carType=" + carType +
            ", vin=" + vin +
            ", isDestroied=" + isDestroied +
            ", nowPlace=" + nowPlace +
            ", remianPower=" + remianPower +
            ", createTime=" + createTime +
            ", createBy=" + createBy +
            ", updateTime=" + updateTime +
            ", updateBy=" + updateBy +
            ", delFlag=" + delFlag +
        "}";
    }
}
