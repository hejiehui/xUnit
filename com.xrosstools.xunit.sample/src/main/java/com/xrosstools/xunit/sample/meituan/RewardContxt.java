package com.xrosstools.xunit.sample.meituan;

import com.xrosstools.xunit.Context;

public class RewardContxt implements Context {
    private Long userId;
    private boolean rewardQualified;
    private UserTypeEnum type;
    private double reward;
    
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public boolean isRewardQualified() {
        return rewardQualified;
    }
    public void setRewardQualified(boolean rewardQualified) {
        this.rewardQualified = rewardQualified;
    }
    public UserTypeEnum getType() {
        return type;
    }
    public void setType(UserTypeEnum type) {
        this.type = type;
    }
    public double getReward() {
        return reward;
    }
    public void setReward(double reward) {
        this.reward = reward;
    }
}
