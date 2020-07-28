package com.xrosstools.xunit.sample.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeoutException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.xrosstools.xunit.XunitFactory;
import com.xrosstools.xunit.sample.parallel.ParallelContext;
import com.xrosstools.xunit.sample.parallel.TaskContext;

public class ParallelTest {
    private static XunitFactory processors;
    private static XunitFactory converters;
    private static XunitFactory counters;
    private static final String T1 = "Task 1";
    private static final String T2 = "Task 2";
    private static final String T3 = "Task 3";
    
    private static final String ALL_MODE = "Parallel All Mode";
    private static final String ANY_MODE = "Parallel Any Mode";
    private static final String CRITICAL_MODE = "Parallel Critical Mode";
    private static final String NONE_MODE = "Parallel None Mode";

    private static final boolean PASS = true;
    private static final boolean FAIL = !PASS;
    
    @BeforeClass
    public static void setup() throws Exception {
        processors = XunitFactory.load("parallel_processor.xunit");
        converters = XunitFactory.load("parallel_converter.xunit");
        counters = XunitFactory.load("parallel_counter.xunit");
    }
    
    private static TaskContext normal() {
        return new TaskContext();
    }

    private static TaskContext timeout() {
        TaskContext ctx = normal();
        ctx.timeout = 15;
        return ctx;
    }

    public static TaskContext failed() {
        TaskContext ctx = normal();
        ctx.fail = true;
        return ctx;
    }
    
    private ParallelContext ctx(TaskContext t1Ctx, TaskContext t2Ctx, TaskContext t3Ctx) {
        ParallelContext ctx = new ParallelContext();
        t1Ctx.taskId = T1;
        t2Ctx.taskId = T2;
        t3Ctx.taskId = T3;
        ctx.add(t1Ctx);
        ctx.add(t2Ctx);
        ctx.add(t3Ctx);
        
        TaskContext s1Ctx = normal();
        s1Ctx.taskId = "S 1";
        
        TaskContext s2Ctx = normal();
        s2Ctx.taskId = "S 2";
        
        ctx.add(s1Ctx);
        ctx.add(s2Ctx);
        return ctx;
    }
    
    private ParallelContext createNormalCtx() {
        return ctx(normal(), normal(), normal());
    }
    
    private ParallelContext createT1TimeoutCtx() {
        return ctx(timeout(), normal(), normal());
    }
    
    private ParallelContext createT1T2TimeoutCtx() {
        return ctx(timeout(), timeout(), normal());
    }
    
    private ParallelContext createAllTimeoutCtx() {
        return ctx(timeout(), timeout(), timeout());
    }
    
    private ParallelContext createT1FailedCtx() {
        return ctx(failed(), normal(), normal());
    }
    
    private ParallelContext createT1T2FailedCtx() {
        return ctx(failed(), failed(), normal());
    }
    
    private ParallelContext createAllFailedCtx() {
        return ctx(failed(), failed(), failed());
    }
    
    private static ParallelContext convert(String id, ParallelContext ctx) throws Exception {
        return (ParallelContext)converters.getConverter(id).convert(ctx);
    }

    private static ParallelContext process(String id, ParallelContext ctx) throws Exception {
        processors.getProcessor(id).process(ctx);
        return ctx;
    }
    
    public void testProcessor(Object[][] caseList) throws Exception {
        for(Object[] caseData: allModeCases()) {
            String mode = (String)caseData[0];
            ParallelContext ctx = (ParallelContext)caseData[1];
            int successCount = (Integer)caseData[2];
            int failedCount = (Integer)caseData[3];

            ctx = process(mode, ctx);
            assertEquals(successCount, ctx.sucessTasks.size());
            assertEquals(failedCount, ctx.failedTasks.size());
        }
    }

    public void testConverter(Object[][] caseList) throws Exception {
        for(Object[] caseData: allModeCases()) {
            String mode = (String)caseData[0];
            ParallelContext ctx = (ParallelContext)caseData[1];
            int successCount = (Integer)caseData[2];
            int failedCount = (Integer)caseData[3];
            boolean pass = (Boolean)caseData[4];

            ctx = convert(mode, ctx);
            assertEquals(successCount, ctx.sucessTasks.size());
            assertEquals(failedCount, ctx.failedTasks.size());
            assertTrue(ctx.fail == !pass);
            if(!pass)
                assertEquals(caseData[5], ctx.exception.getClass());
        }
    }
    
    private Object[][] allModeCases() {
        return new Object[][] {
                {ALL_MODE, createNormalCtx(), 3, 0, PASS},
                {ALL_MODE, createT1TimeoutCtx(), 2, 1, PASS},
                {ALL_MODE, createT1T2TimeoutCtx(), 1, 2, PASS},
                {ALL_MODE, createAllTimeoutCtx(), 0, 3, PASS},
                {ALL_MODE, createT1FailedCtx(), 2, 1, PASS},
                {ALL_MODE, createT1T2FailedCtx(), 1, 2, PASS},
                {ALL_MODE, createAllFailedCtx(), 0, 3, PASS},
        };
    }
    
    private Object[][] anyModeCases() {
        return new Object[][] {
                {ANY_MODE, createNormalCtx(), 1, 0, PASS},
                {ANY_MODE, createT1TimeoutCtx(), 1, 0, PASS},
                {ANY_MODE, createT1T2TimeoutCtx(), 1, 0, PASS},
                {ANY_MODE, createAllTimeoutCtx(), 0, 0, FAIL, TimeoutException.class},
                {ANY_MODE, createT1FailedCtx(), 1, 0, PASS},
                {ANY_MODE, createT1T2FailedCtx(), 1, 0, PASS},
                {ANY_MODE, createAllFailedCtx(), 0, 0, FAIL, RuntimeException.class},
        };
    }

    private Object[][] criticalModeCases() {
        return new Object[][] {
                {CRITICAL_MODE, createNormalCtx(), task(T1, T2, T3), PASS},
                {CRITICAL_MODE, createT1TimeoutCtx(), task(T2, T3), PASS},
                {CRITICAL_MODE, createT1T2TimeoutCtx(), task(T3), PASS},
                {CRITICAL_MODE, createAllTimeoutCtx(), task(), PASS},
                {CRITICAL_MODE, createT1FailedCtx(), task(T2, T3), PASS},
                {CRITICAL_MODE, createT1T2FailedCtx(), task(T3), PASS},
                {CRITICAL_MODE, createAllFailedCtx(), task(), PASS},
        };
    }
    
    private Object[][] noneModeCases() {
        return new Object[][] {
                {CRITICAL_MODE, createNormalCtx(), PASS},
                {CRITICAL_MODE, createT1TimeoutCtx(), PASS},
                {CRITICAL_MODE, createT1T2TimeoutCtx(), PASS},
                {CRITICAL_MODE, createAllTimeoutCtx(), PASS},
                {CRITICAL_MODE, createT1FailedCtx(), PASS},
                {CRITICAL_MODE, createT1T2FailedCtx(), PASS},
                {CRITICAL_MODE, createAllFailedCtx(), PASS},
        };
    }
    private String[] task(String...tasks) {
        return tasks;
    }
    
    @Test
    public void testAllMode() throws Exception {
        testProcessor(allModeCases());
        testConverter(allModeCases());
    }

    @Test
    public void testAnyMode() throws Exception {
        testProcessor(anyModeCases());
        testConverter(anyModeCases());
    }

    @Test
    public void testCriticalModeProcessor() throws Exception {
        for(Object[] caseData: criticalModeCases()) {
            String mode = (String)caseData[0];
            ParallelContext ctx = (ParallelContext)caseData[1];
            String[] tasks = (String[])caseData[2];

            ctx = process(CRITICAL_MODE, ctx);
            
            // For critical mode, not every time all the tasks will be executed
            if(tasks.length > ctx.sucessTasks.size())
                System.out.println("Tasks: " + tasks + " Count:" +ctx.sucessTasks.size());

            if(tasks.length != 3) {
                assertTrue(tasks.length <= ctx.sucessTasks.size());

                for (String task : tasks)
                    assertTrue(ctx.sucessTasks.contains(task));
            }
        }
    }
    
    @Test
    public void testCriticalModeConverter() throws Exception {
        for(Object[] caseData: criticalModeCases()) {
            String mode = (String)caseData[0];
            ParallelContext ctx = (ParallelContext)caseData[1];
            String[] tasks = (String[])caseData[2];

            ctx = convert(CRITICAL_MODE, ctx);
            if(tasks.length > ctx.sucessTasks.size())
                System.out.println("Tasks: " + tasks + " Count:" +ctx.sucessTasks.size());

            if(tasks.length != 3) {
                assertTrue(tasks.length <= ctx.sucessTasks.size());

                for (String task : tasks)
                    assertTrue(ctx.sucessTasks.contains(task));
            }
        }
    }

    @Test
    public void testParallelNoneModeNormal() throws Exception {
        for(Object[] caseData: noneModeCases()) {
            String mode = (String)caseData[0];
            ParallelContext ctx = (ParallelContext)caseData[1];
            boolean pass = (Boolean)caseData[2];
    
            ctx = process(NONE_MODE, ctx);
            assertTrue(ctx.sucessTasks.isEmpty());
        }
    }
    
    @Test
    public void testParallelNoneModeNormalConverter() throws Exception {
        for(Object[] caseData: noneModeCases()) {
            String mode = (String)caseData[0];
            ParallelContext ctx = (ParallelContext)caseData[1];
            boolean pass = (Boolean)caseData[2];
            
            ctx = convert(NONE_MODE, ctx);
            assertTrue(ctx.sucessTasks.isEmpty());
        }
    }
    
    @Test
    public void testParallelAddAllMode() throws Exception {
        IntegerContext ic=  new IntegerContext(0);
        counters.getProcessor("Parallel Adder All Mode").process(ic);
        
        assertEquals(8, ic.getValue().intValue());
        
    }

    @Test
    public void testParallelAddAnyMode() throws Exception {
        IntegerContext ic=  new IntegerContext(0);
        counters.getProcessor("Parallel Adder Any Mode").process(ic);
        
        // Only the shortest path will be executed
        assertEquals(3, ic.getValue().intValue());
        
    }

}
