package com.zzitbar.quartzcluster.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class JobAndTrigger {
    @TableField("JOB_NAME")
    private String jobName;
    @TableField("JOB_GROUP")
    private String jobGroup;
    @TableField("JOB_CLASS_NAME")
    private String jobClassName;
    @TableField("TRIGGER_NAME")
    private String triggerName;
    @TableField("TRIGGER_GROUP")
    private String triggerGroup;
    @TableField("REPEAT_INTERVAL")
    private BigInteger repeatInterval;// QRTZ_SIMPLE_TRIGGERS 触发间隔（毫秒）
    @TableField("TIMES_TRIGGERED")
    private BigInteger timesTriggered;// QRTZ_SIMPLE_TRIGGERS 已触发次数
    @TableField("CRON_EXPRESSION")
    private String cronExpression;
    @TableField("TIME_ZONE_ID")
    private String timeZoneId;
    @TableField("PREV_FIRE_TIME")
    private String prevFireTime;//上一次触发时间
    @TableField("NEXT_FIRE_TIME")
    private String nextFireTime;//下一次触发时间
    /**
     * @see org.quartz.Trigger.TriggerState
     */
    @TableField("TRIGGER_STATE")
    private String triggerState;//触发器状态 WAITING:等待 PAUSED:暂停 ACQUIRED:正常执行 BLOCKED：阻塞 ERROR：错误
    @TableField("TRIGGER_TYPE")
    private String triggerType;//触发器类型
    @TableField("START_TIME")
    private String startTime;//开始时间
    @TableField("START_TIME")
    private String jobDescription;//开始时间

    @TableField(exist = false)
    private LocalDateTime prevFireDateTime;
    @TableField(exist = false)
    private LocalDateTime nextFireDateTime;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobClassName() {
        return jobClassName;
    }

    public void setJobClassName(String jobClassName) {
        this.jobClassName = jobClassName;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getTriggerGroup() {
        return triggerGroup;
    }

    public void setTriggerGroup(String triggerGroup) {
        this.triggerGroup = triggerGroup;
    }

    public BigInteger getRepeatInterval() {
        return repeatInterval;
    }

    public void setRepeatInterval(BigInteger repeatInterval) {
        this.repeatInterval = repeatInterval;
    }

    public BigInteger getTimesTriggered() {
        return timesTriggered;
    }

    public void setTimesTriggered(BigInteger timesTriggered) {
        this.timesTriggered = timesTriggered;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getTimeZoneId() {
        return timeZoneId;
    }

    public void setTimeZoneId(String timeZoneId) {
        this.timeZoneId = timeZoneId;
    }

    public String getPrevFireTime() {
        return prevFireTime;
    }

    public void setPrevFireTime(String prevFireTime) {
        this.prevFireTime = prevFireTime;
    }

    public String getNextFireTime() {
        return nextFireTime;
    }

    public void setNextFireTime(String nextFireTime) {
        this.nextFireTime = nextFireTime;
    }

    public String getTriggerState() {
        return triggerState;
    }

    public void setTriggerState(String triggerState) {
        this.triggerState = triggerState;
    }

    public String getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getPrevFireDateTime() {
        if (StringUtils.hasText(this.prevFireTime)) {
            return Instant.ofEpochMilli(Long.valueOf(this.prevFireTime)).atZone(ZoneId.of("Asia/Shanghai")).toLocalDateTime();
        } else {
            return prevFireDateTime;
        }
    }

    public void setPrevFireDateTime(LocalDateTime prevFireDateTime) {
        this.prevFireDateTime = prevFireDateTime;
    }

    public LocalDateTime getNextFireDateTime() {
        if (StringUtils.hasText(this.nextFireTime)) {
            return Instant.ofEpochMilli(Long.valueOf(this.nextFireTime)).atZone(ZoneId.of("Asia/Shanghai")).toLocalDateTime();
        } else {
            return nextFireDateTime;
        }
    }

    public void setNextFireDateTime(LocalDateTime nextFireDateTime) {
        this.nextFireDateTime = nextFireDateTime;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public static void main(String[] args) {
        LocalDateTime ld = LocalDateTime.ofEpochSecond(1530776610000l, 0, ZoneOffset.of("+08"));
        System.out.println(ld.toString());
        //获取秒数
        Long second = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        //获取毫秒数
        Long milliSecond = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        LocalDateTime ld1 = Instant.ofEpochMilli(second).atZone(ZoneId.of("Asia/Shanghai")).toLocalDateTime();
        LocalDateTime ld2 = Instant.ofEpochMilli(milliSecond).atZone(ZoneId.of("Asia/Shanghai")).toLocalDateTime();

        System.out.println(second);
        System.out.println(milliSecond);
        System.out.println(ld1);
        System.out.println(ld2);
    }
}