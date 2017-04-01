package app.utils.multiple;

/**
 * @author cerpengxi
 * @date 17/3/31 下午6:44
 */

import app.utils.PropertiesParser;
import org.apache.kafka.clients.producer.*;

import java.util.Properties;

import static app.consts.KafkaConsts.*;

public class ProducerThread implements Runnable {
    private static PropertiesParser prop = new PropertiesParser(CONF_FILE);
    private static String topic = prop.getStringProperty(TOPIC_JOB);
    private static String brokerList = prop.getStringProperty(BROKER_LIST);
    private static String acks = prop.getStringProperty(ACKS);
    private static String retries = prop.getStringProperty(RETRIES);
    private static String batchSize = prop.getStringProperty(BATCH_SIZE);
    private static String lingerMs = prop.getStringProperty(LINGER_MS);
    private static String bufferMemory = prop.getStringProperty(BUFFER_MEMORY);


    private static Producer<String, String> producer;

    public ProducerThread() {
        super();
    }
    public static void init() {
        Properties prop = createProducerConfig();
        producer = new KafkaProducer<String, String>(prop);
    }

    public static Properties createProducerConfig() {
        Properties props = new Properties();
        props.put("bootstrap.servers", brokerList);
        props.put("acks", acks);//所有follower都响应了才认为消息提交成功，即"committed"
        props.put("retries", retries);//消息发送失败的情况下，重试发送的次数;如果不为0，则存在消息发送是成功的，只是由于网络导致ACK没收到的重试，会出现消息被重复发送的情况;
        props.put("batch.size", batchSize);//Producer会尝试去把发往同一个Partition的多个Requests进行合并，batch.size指明了一次Batch合并后Requests总大小的上限。如果这个值设置的太小，可能会导致所有的Request都不进行Batch（16KB）。
        props.put("linger.ms", lingerMs);//Producer默认会把两次发送时间间隔内收集到的所有Requests进行一次聚合然后再发送，以此提高吞吐量，而linger.ms则更进一步，这个参数为每次发送增加一些delay，以此来聚合更多的Message。
        props.put("buffer.memory", bufferMemory);//在Producer端用来存放尚未发送出去的Message的缓冲区大小。缓冲区满了之后可以选择阻塞发送或抛出异常，由block.on.buffer.full的配置来决定。（32MB）block.on.buffer.full这个配置是默认值是flase，也就是当bufferpool满时，不会抛出BufferExhaustException，而是根据max.block.ms进行阻塞，如果超时抛出TimeoutExcpetion。
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return props;
    }
    public static Producer<String, String> createProducer() {
        if (producer == null) {
            init();
        }
        return producer;
    }
    @Override
    public void run() {
        System.out.println("Produces 3 messages");
        for (int i = 0; i < 5; i++) {
            final String msg = "Message " + i;
            Producer<String, String> producer = createProducer();
            producer.send(new ProducerRecord<String, String>(topic, msg), new Callback() {
                public void onCompletion(RecordMetadata metadata, Exception e) {
                    if (e != null) {
                        e.printStackTrace();
                    }
                    System.out.println("Sent:" + msg + ", Partition: " + metadata.partition() + ", Offset: "
                            + metadata.offset());
                }
            });

        }
        // closes producer
        producer.close();

    }
}