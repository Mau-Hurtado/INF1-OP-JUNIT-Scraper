package threads;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

public class CPUProcessTest {

    @Test
    public void testRun() {
        CPUProcess.resetProcessCounts();
        assertEquals(0, CPUProcess.getStartedProcessCount());
        assertEquals(0, CPUProcess.getCompletedProcessCount());
        
        // Run one process to make sure runtime is not excessive
        long startTime = Calendar.getInstance().getTimeInMillis();
        CPUProcess.run();
        long endTime = Calendar.getInstance().getTimeInMillis();
        long runtime = endTime - startTime;
        assertTrue("Runtime is too long. Reduce value of CPUProcess.DIFFICULTY", runtime < 5000);

        // Run another few processes to make sure average runtime is not too short
        for (int i=0; i<4; i++) {
            CPUProcess.run();
        }
        endTime = Calendar.getInstance().getTimeInMillis();
        runtime = endTime - startTime;
        assertTrue("Runtime is too short. Increase value of CPUProcess.DIFFICULTY", runtime > 3000 * 5);
        
        assertEquals(5, CPUProcess.getStartedProcessCount());
        assertEquals(5, CPUProcess.getCompletedProcessCount());
    }

}
