package callcenter.util;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

import callcenter.model.Employee;

/**
 * This queue has sorting criteria so employees are more preferable than
 * supervisor, which are more preferable than directors. If queue has two or
 * more employees of same range available, employee with oldest phone call goes
 * first.
 */
public class EmployeeQueue {
    private PriorityBlockingQueue<Employee> queue;

    public EmployeeQueue() {
        queue = new PriorityBlockingQueue<Employee>(20, new EmployeeComparator());
    }

    /**
     * It attempts to add an idle employee to this queue, which means it's
     * available to pick up a new phone call.
     *
     * @param emp
     *            Employee available to pick up a new call.
     * @return True if employee was added to queue successfully, otherwise
     *         returns false.
     */
    public synchronized Boolean add(Employee emp) {
        if (queue.contains(emp)) {
            throw new IllegalArgumentException();
        }
        return queue.add(emp);
    }

    /**
     * Extract from the queue the most preferable employee to pick a new phone
     * call. Selected employee is removed from the queue.
     *
     * @return The most preferable employee to pick up a phone call.
     * @throws InterruptedException
     */
    public Employee poll() throws InterruptedException {
        return queue.poll(30, TimeUnit.MINUTES);
    }
}
