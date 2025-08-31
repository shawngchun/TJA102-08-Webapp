package com.task.model;
import java.sql.Date;

public class TaskVO implements java.io.Serializable{
    private Integer taskId;       // 任務流水號 (PK)
    private Integer taskTypeId;   // 對應任務類型 (FK)
    private String taskName;      // 任務名稱
    private Integer targetValue;  // 任務目標值
    private String unit;          // 任務單位
    private Date startTime;       // 任務開始日
    private Date endTime;         // 任務結束日
    private Byte points;          // 點數 (TINYINT → Byte)
    private String taskIcon;      // 任務圖檔
    private Integer adminId;      // 任務建立人員 ID (FK)

    // Getter & Setter
    public Integer getTaskId() {
        return taskId;
    }
    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getTaskTypeId() {
        return taskTypeId;
    }
    public void setTaskTypeId(Integer taskTypeId) {
        this.taskTypeId = taskTypeId;
    }

    public String getTaskName() {
        return taskName;
    }
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Integer getTargetValue() {
        return targetValue;
    }
    public void setTargetValue(Integer targetValue) {
        this.targetValue = targetValue;
    }

    public String getUnit() {
        return unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Date getStartTime() {
        return startTime;
    }
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Byte getPoints() {
        return points;
    }
    public void setPoints(Byte points) {
        this.points = points;
    }

    public String getTaskIcon() {
        return taskIcon;
    }
    public void setTaskIcon(String taskIcon) {
        this.taskIcon = taskIcon;
    }

    public Integer getAdminId() {
        return adminId;
    }
    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    @Override
    public String toString() {
        return "TaskVO [taskId=" + taskId 
                + ", taskTypeId=" + taskTypeId 
                + ", taskName=" + taskName 
                + ", targetValue=" + targetValue 
                + ", unit=" + unit 
                + ", startTime=" + startTime 
                + ", endTime=" + endTime 
                + ", points=" + points 
                + ", taskIcon=" + taskIcon 
                + ", adminId=" + adminId + "]";
    }
}
