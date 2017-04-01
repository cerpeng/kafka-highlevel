package app.utils.single;

import org.apache.kafka.clients.consumer.ConsumerRecord;

/**
 * @author cerpengxi
 * @date 17/4/1 下午2:32
 */

public class ConsumerThreadHandler implements Runnable {

    private ConsumerRecord consumerRecord;

    public ConsumerThreadHandler(ConsumerRecord consumerRecord) {
        this.consumerRecord = consumerRecord;
    }

    public void run() {
        System.out.println("Process: " + consumerRecord.value() + ", Offset: " + consumerRecord.offset()
                + ", By ThreadID: " + Thread.currentThread().getId());
    }
}
