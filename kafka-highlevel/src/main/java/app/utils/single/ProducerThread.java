package app.utils.single;

import app.utils.PropertiesParser;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;

import static app.consts.KafkaConsts.*;

/**
 * @author cerpengxi
 * @date 17/4/1 下午2:30
 */

public class ProducerThread implements Runnable {

    private final KafkaProducer<String, String> producer;
    private static PropertiesParser prop = new PropertiesParser(CONF_FILE);
    private static String topic = prop.getStringProperty(TOPIC_JOB);

    public ProducerThread() {
        Properties prop = app.utils.multiple.ProducerThread.createProducerConfig();
        this.producer = new KafkaProducer<String, String>(prop);
    }

    @Override
    public void run() {
        System.out.println("Produces 5 messages");
        for (int i = 0; i < 5; i++) {
            final String msg = "Message " + i;
            producer.send(new ProducerRecord<String, String>(topic, msg), new Callback() {
                public void onCompletion(RecordMetadata metadata, Exception e) {
                    if (e != null) {
                        e.printStackTrace();
                    }
                    System.out.println("Sent:" + msg + ", Offset: " + metadata.offset());
                }
            });
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // closes producer
        producer.close();

    }
}