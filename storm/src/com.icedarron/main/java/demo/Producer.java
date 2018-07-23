package demo;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

public class Producer {

    public Map<String, Object> config = new HashMap<String, Object>();

    private KafkaProducer<String, String> producer;

//	private static String kafkaServer;

//	private static String kafkaTopic;

    static {
        try {
            //kafkaServer = ContextConfig.ContextConfigMap.kafkaServer;
            //kafkaTopic = ContextConfig.ContextConfigMap.kafkaTopic;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Producer() {
        //producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.100.90:9092,192.168.100.91:9092");
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.4.56.209:9092,10.4.59.141:9092,10.4.59.142:9092"); //kafka服务器地址
        //kafka消息序列化类 即将传入对象序列化为字节数组
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        //kafka消息key序列化类 若传入key的值，则根据该key的值进行hash散列计算出在哪个partition上
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        config.put(ProducerConfig.BATCH_SIZE_CONFIG, 1024 * 1024 * 5);
        //往kafka服务器提交消息间隔时间，0则立即提交不等待
        config.put(ProducerConfig.LINGER_MS_CONFIG, 0);

        producer = new KafkaProducer<String, String>(config);

    }

    public Future<RecordMetadata> send(String value) {
        return send("TOPIC_LEO", value);
    }

    /**
     * 发送消息，发送的对象必须是可序列化的
     */
    public Future<RecordMetadata> send(String topic, String value) {
        //将对象序列化称字节码
        //byte[] bytes = SerializationUtils.serialize(value);
        //Object future = producer.send("", "");
        ProducerRecord<String, String> sss = new ProducerRecord<String, String>(topic, value);
        Future<RecordMetadata> future = producer.send(sss);
        return future;
    }

}
