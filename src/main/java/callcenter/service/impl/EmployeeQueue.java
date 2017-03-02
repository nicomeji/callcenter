package callcenter.service.impl;

import java.util.concurrent.PriorityBlockingQueue;

import callcenter.model.Employee;
import callcenter.util.EmployeeComparator;

public class EmployeeQueue {
    private PriorityBlockingQueue<Employee> queue;

    public EmployeeQueue() {
        queue = new PriorityBlockingQueue<Employee>(20, new EmployeeComparator());
    }

    public boolean add(Employee emp) {
        if(queue.contains(emp)){
            throw new IllegalArgumentException();
        }
        return queue.add(emp);
    }

    public Employee poll() {
        return queue.poll();
    }

    public void clear() {
        queue.clear();
    }
}
