package app.utils.multiple;

/**
 * @author cerpengxi
 * @date 17/3/31 下午6:46
 */

public final class ConsumersMain {

    public static void main(String[] args) {

        ConsumerGroup consumerGroup = new ConsumerGroup(3);
        consumerGroup.execute();

        try {
            Thread.sleep(100000);
        } catch (InterruptedException ie) {

        }
    }
}
