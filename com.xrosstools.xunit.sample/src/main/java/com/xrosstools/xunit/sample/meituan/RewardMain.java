package com.xrosstools.xunit.sample.meituan;

import com.xrosstools.xunit.Processor;
import com.xrosstools.xunit.XunitFactory;

public class RewardMain {
    public void reward(Long userId) throws Exception {
        XunitFactory factory = XunitFactory.load("meituan.xunit");
        
        RewardContxt ctx = new RewardContxt();
        ctx.setUserId(userId);

        Processor rewardProcessor = factory.getProcessor("reward");
        rewardProcessor.process(ctx);
    }
}
