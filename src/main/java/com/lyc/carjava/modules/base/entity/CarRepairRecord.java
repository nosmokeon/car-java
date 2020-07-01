package com.lyc.carjava.modules.base.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 车辆维修
 * </p>
 *
 * @author lyc
 * @since 2020-06-20
 */
public class CarRepairRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 车辆id
     */
    private String carId;

    /**
     * 维修费用
     */
    private String repairFair;

    /**
     * 维修状态
     */
    private String repairState;

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
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }
    public String getRepairFair() {
        return repairFair;
    }

    public void setRepairFair(String repairFair) {
        this.repairFair = repairFair;
    }
    public String getRepairState() {
        return repairState;
    }

    public void setRepairState(String repairState) {
        this.repairState = repairState;
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
        return "CarRepairRecord{" +
            "id=" + id +
            ", userId=" + userId +
            ", carId=" + carId +
            ", repairFair=" + repairFair +
            ", repairState=" + repairState +
            ", createTime=" + createTime +
            ", createBy=" + createBy +
            ", updateTime=" + updateTime +
            ", updateBy=" + updateBy +
            ", delFlag=" + delFlag +
        "}";
    }
}
