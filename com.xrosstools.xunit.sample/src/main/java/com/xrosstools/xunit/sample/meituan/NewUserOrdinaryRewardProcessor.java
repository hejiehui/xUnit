package com.xrosstools.xunit.sample.meituan;

import com.xrosstools.xunit.Context;
import com.xrosstools.xunit.Processor;

public class NewUserOrdinaryRewardProcessor implements Processor {

    @Override
    public void process(Context ctx) {
        RewardContxt rwdCtx = (RewardContxt)ctx;
        rwdCtx.setReward(100);
    }
}
