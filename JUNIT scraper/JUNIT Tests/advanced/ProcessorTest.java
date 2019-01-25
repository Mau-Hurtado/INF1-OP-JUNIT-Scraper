package threads;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

public class ProcessorTest {

    @Test
    public void testMain() {
        CPUProcess.resetProcessCounts();
        assertEquals(0, CPUProcess.getStartedProcessCount());
        assertEquals(0, CPUProcess.getCompletedProcessCount());

        // Run slow processor
        long startTime = Calendar.getInstance().getTimeInMillis();
        SlowProcessor.main(null);
        long endTime = Calendar.getInstance().getTimeInMillis();
        long slowProcessorRuntime = endTime - startTime;
        assertEquals(10, CPUProcess.getStartedProcessCount());
        assertEquals(10, CPUProcess.getCompletedProcessCount());
        assertTrue(slowProcessorRuntime > 3000 * 10);
        
        CPUProcess.resetProcessCounts();
        assertEquals(0, CPUProcess.getStartedProcessCount());
        assertEquals(0, CPUProcess.getCompletedProcessCount());

        // Run threaded processor
        startTime = Calendar.getInstance().getTimeInMillis();
        ThreadedProcessor.main(null);
        endTime = Calendar.getInstance().getTimeInMillis();
        long threadedProcessorRuntime = endTime - startTime;
        assertEquals(10, CPUProcess.getStartedProcessCount());
        assertEquals(10, CPUProcess.getCompletedProcessCount());
        // Even a dual core machine should show significant speedup
        assertTrue(threadedProcessorRuntime < slowProcessorRuntime * 0.6);
    }

}
