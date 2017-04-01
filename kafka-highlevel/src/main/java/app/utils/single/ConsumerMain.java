package app.utils.single;

/**
 * @author cerpengxi
 * @date 17/4/1 下午2:33
 */

public class ConsumerMain {
    public static void main(String[] args) {
        ProducerThread producerThread = new ProducerThread();
        Thread t1 = new Thread(producerThread);
        t1.start();
        ConsumerThread consumers = new ConsumerThread();
        consumers.execute(3);
        try {
            Thread.sleep(100000);
        } catch (InterruptedException ie) {

        }
        consumers.shutdown();
    }
}
