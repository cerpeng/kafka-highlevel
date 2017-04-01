package app.utils.multiple;

/**
 * @author cerpengxi
 * @date 17/3/31 下午6:45
 */

import app.utils.PropertiesParser;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

import static app.consts.KafkaConsts.*;

public class ConsumerThread implements Runnable {

    private final KafkaConsumer<String, String> consumer;
    private static PropertiesParser prop = new PropertiesParser(CONF_FILE);
    private static String topic = prop.getStringProperty(TOPIC_JOB);
    private static String groupId = prop.getStringProperty(GROUP_ID);
    private static String brokerList = prop.getStringProperty(BROKER_LIST);
    private static String autoCommit = prop.getStringProperty(AUTO_COMMIT);
    private static String intervalMs = prop.getStringProperty(INTERVAL_MS);
    private static String timeoutMs = prop.getStringProperty(TIMEOUT_MS);
    private static String reset = prop.getStringProperty(OFFSET_RESET);


    public ConsumerThread() {
        Properties prop = createConsumerConfig();
        this.consumer = new KafkaConsumer<String, String>(prop);
        this.consumer.subscribe(Arrays.asList(this.topic));
    }

    public static Properties createConsumerConfig() {
        Properties props = new Properties();
        props.put("bootstrap.servers", brokerList);
        props.put("group.id", groupId);
        props.put("enable.auto.commit", autoCommit);//true时，Consumer会在消费消息后将offset同步到zookeeper，这样当Consumer失败后，新的consumer就能从zookeeper获取最新的offset
        props.put("auto.commit.interval.ms", intervalMs);//自动提交的时间间隔
        props.put("session.timeout.ms", timeoutMs);//超时时间，超过时间会认为是无效的消费者
        props.put("auto.offset.reset", reset);//当zookeeper中没有初始的offset时，或者超出offset上限时的处理方式 ;默认值为latest，表示从topic的的尾开始处理，earliest表示从topic的头开始处理
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    }

    @Override
    public void run() {
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("Receive message: " + record.value() + ", Offset: " + record.offset());
            }
        }
    }
}
