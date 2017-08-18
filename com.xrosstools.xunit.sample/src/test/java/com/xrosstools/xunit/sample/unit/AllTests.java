package com.xrosstools.xunit.sample.unit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    DefaultUnitImplTest.class,
    XunitBasicTest.class,
    MapContextTest.class,
})
public class AllTests {

}
