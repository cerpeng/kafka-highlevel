package app.utils.single;

import app.utils.PropertiesParser;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static app.consts.KafkaConsts.CONF_FILE;
import static app.consts.KafkaConsts.TOPIC_JOB;

/**
 * @author cerpengxi
 * @date 17/4/1 下午2:32
 */

public class ConsumerThread {

    private final KafkaConsumer<String, String> consumer;
    private static PropertiesParser prop = new PropertiesParser(CONF_FILE);
    private static String topic = prop.getStringProperty(TOPIC_JOB);
    private ExecutorService executor;

    public ConsumerThread() {
        Properties prop = app.utils.multiple.ConsumerThread.createConsumerConfig();
        this.consumer = new KafkaConsumer<String, String>(prop);
        this.consumer.subscribe(Arrays.asList(this.topic));
    }


    public void execute(int numberOfThreads) {

        executor = new ThreadPoolExecutor(numberOfThreads, numberOfThreads, 0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(1000), new ThreadPoolExecutor.CallerRunsPolicy());

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (final ConsumerRecord record : records) {
                executor.submit(new ConsumerThreadHandler(record));
            }
        }
    }

    public void shutdown() {
        if (consumer != null) {
            consumer.close();
        }
        if (executor != null) {
            executor.shutdown();
        }
        try {
            if (!executor.awaitTermination(5000, TimeUnit.MILLISECONDS)) {
                System.out
                        .println("Timed out waiting for consumer threads to shut down, exiting uncleanly");
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted during shutdown, exiting uncleanly");
        }
    }

}
