package demo;

public class SendToKafka {

    public static void main(String[] args) {

        String message = "test";
        try {
            Producer producer = new Producer();
            producer.send("TOPIC_LEO", message);
        } catch (Exception e) {
            System.out.println("发送kafka数据失败，meaasge:" + message);
            e.printStackTrace();
        }

    }
}
