package app.utils.multiple;

/**
 * @author cerpengxi
 * @date 17/3/31 下午6:46
 */

public final class ProducerMain {

    public static void main(String[] args) {
        ProducerThread producerThread = new ProducerThread();
        Thread t1 = new Thread(producerThread);
        t1.start();
    }
}
