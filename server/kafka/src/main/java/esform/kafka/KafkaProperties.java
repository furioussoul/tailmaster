package esform.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KafkaProperties {

    @Value("${kafka-master1.ip}")
    public static String KAFKA_MASTER1_IP = "47.97.220.227";
    @Value("${kafka-master1.port}")
    public static String KAFKA_MASTER1_PORT = "9092";

    public static final String GROUP1 = "group1";

    public static final String TOPIC1 = "test";

    public static final int KAFKA_PRODUCER_BUFFER_SIZE = 64 * 1024;
    public static final int CONNECTION_TIMEOUT = 100000;
    public static final String TOPIC2 = "topic2";
    public static final String TOPIC3 = "topic3";
    public static final String CLIENT_ID = "SimpleConsumerDemoClient";

    private KafkaProperties() {}
}
