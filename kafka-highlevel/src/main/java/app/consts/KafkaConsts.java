package app.consts;

/**
 * @author cerpengxi
 * @date 17/3/30 上午11:24
 */

public class KafkaConsts {
    public static final String CONF_FILE = "conf/kafka.properties";
    public static final String TOPIC_JOB = "kafka.topic.job";
    public static final String BROKER_LIST = "metadata.broker.list";
    public static final String GROUP_ID = "kafka.groupId";
    public static final String ACKS = "kafka.producer.acks";
    public static final String RETRIES = "kafka.producer.retries";
    public static final String BATCH_SIZE = "kafka.producer.batch.size";
    public static final String LINGER_MS = "kafka.producer.linger.ms";
    public static final String BUFFER_MEMORY = "kafka.producer.buffer.memory";
    public static final String AUTO_COMMIT = "kafka.consumer.enable.auto.commit";
    public static final String INTERVAL_MS = "kafka.consumer.auto.commit.interval.ms";
    public static final String TIMEOUT_MS = "kafka.consumer.session.timeout.ms";
    public static final String OFFSET_RESET = "kafka.consumer.auto.offset.reset";
}
