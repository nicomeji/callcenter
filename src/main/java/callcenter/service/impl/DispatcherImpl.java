package callcenter.service.impl;

import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import callcenter.model.Employee;
import callcenter.model.PhoneCall;
import callcenter.service.CallRouting;
import callcenter.service.Dispatcher;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

@Data
public class DispatcherImpl implements Dispatcher {
    @Getter(value = AccessLevel.NONE)
    private final ConcurrentSkipListSet<Employee> availableEmployees = new ConcurrentSkipListSet<>((Employee emp1, Employee emp2) -> {
        return emp1.getLastCall().getEnd().compareTo(emp2.getLastCall().getEnd());
    });

    private ExecutorService executor = new ThreadPoolExecutor(10, 10, 10, TimeUnit.SECONDS, workQueue, threadFactory, handler);

    private CallRouting callRouting;

    public void dispatchCall(PhoneCall phoneCall) {
        executor.submit(() -> {
            processPhoneCall(phoneCall);
        });
    }

    private void processPhoneCall(PhoneCall phoneCall) {
        callRouting.sendCall(extractFirstAvailable(), phoneCall);
    }

    private synchronized Employee extractFirstAvailable() {
        Employee emp = availableEmployees.first();
        availableEmployees.remove(emp);
        return emp;
    }
}
