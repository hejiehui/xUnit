package com.xrosstools.xunit.sample.meituan;

import com.xrosstools.xunit.Context;
import com.xrosstools.xunit.Validator;

public class RewardValidator implements Validator {

    @Override
    public boolean validate(Context ctx) {
        return ((RewardContxt)ctx).isRewardQualified();
    }
}
