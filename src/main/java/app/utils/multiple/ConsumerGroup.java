package app.utils.multiple;

/**
 * @author cerpengxi
 * @date 17/3/31 下午6:45
 */

import java.util.ArrayList;
import java.util.List;

public class ConsumerGroup {

    private final int numberOfConsumers;
    private List<ConsumerThread> consumers;

    public ConsumerGroup(
            int numberOfConsumers) {
        this.numberOfConsumers = numberOfConsumers;
        consumers = new ArrayList<ConsumerThread>();
        for (int i = 0; i < this.numberOfConsumers; i++) {
            ConsumerThread ncThread =
                    new ConsumerThread();
            consumers.add(ncThread);
        }
    }

    public void execute() {
        for (ConsumerThread ncThread : consumers) {
            Thread t = new Thread(ncThread);
            t.start();
        }
    }
}
