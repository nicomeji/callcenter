package callcenter.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import callcenter.model.Employee;
import callcenter.model.PhoneCall;
import callcenter.util.EmployeeQueue;
import lombok.Data;
import lombok.NonNull;

/**
 * A {@link Dispatcher} use the policies defined in the
 * {@link callcenter.util.EmployeeQueue} to balance the phone calls
 * between call center employees, including supervisors and directors.
 */
@Data
public class Dispatcher implements AutoCloseable {
    private static Logger logger = Logger.getLogger(Dispatcher.class);

    private ExecutorService executor = new ThreadPoolExecutor(10, 10, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>(30), new AbortPolicy());

    @NonNull
    private EmployeeQueue availableEmployees;

    @NonNull
    private CallRouting callRouting;

    /**
     * Using {@link callcenter.util.EmployeeQueue} dispatch each call to
     * call center employees.
     *
     * @param phoneCall
     *            New call to be handle.
     */
    public void dispatchCall(PhoneCall phoneCall) {
        executor.submit(() -> {
            try {
                processPhoneCall(phoneCall);
            } catch (InterruptedException e) {
                logger.error("Unexpected interruption", e);
            }
        });
    }

    private void processPhoneCall(PhoneCall phoneCall) throws InterruptedException {
        Employee employee = availableEmployees.poll();
        callRouting.sendCall(employee, phoneCall);
        availableEmployees.add(employee);
    }

    /**
     * Initiates an orderly shutdown in which previously submitted tasks are
     * executed, but no new tasks will be accepted. It gives a timeout of 20
     * seconds to terminate any reminding thread. Blocks until all tasks have
     * completed execution, or the timeout occurs, or the current thread is
     * interrupted, whichever happens first.
     */
    @Override
    public void close() throws Exception {
        executor.shutdown();
        if (!executor.awaitTermination(20, TimeUnit.SECONDS)) {
            executor.shutdownNow();
        }
    }
}
