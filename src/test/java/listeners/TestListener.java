package listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    private static final Logger log = LogManager.getLogger(TestListener.class);
    @Override public void onTestStart(ITestResult result) { log.info("START: {}", result.getName()); }
    @Override public void onTestSuccess(ITestResult result) { log.info("PASS: {}", result.getName()); }
    @Override public void onTestFailure(ITestResult result) { log.error("FAIL: {}", result.getName(), result.getThrowable()); }
    @Override public void onTestSkipped(ITestResult result) { log.warn("SKIP: {}", result.getName()); }
}
