package rabbitmq.tutorial.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import rabbitmq.tutorial.ConnectionModal;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        String queueName = "test_queue";
        Consumer consumer = new Consumer(queueName);
        consumer.connect();
        String consumerTag = consumer.receiveString();
        System.out.println("consumerTag: " + consumerTag);
        Thread.sleep(10000);
        consumer.close();
    }

    private Connection connection;
    private Channel channel;
    private String queueName = "demo-simplest";

    public Consumer() {
    }

    public Consumer(String queueName) {
        this.queueName = queueName;
    }

    private void connect() throws IOException, TimeoutException {
        ConnectionFactory factory = ConnectionModal.defaultConnectionModal.buildFactory();
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(queueName, false, false, false, null);
    }

    private void close() throws IOException, TimeoutException {
        channel.close();
        connection.close();
    }

    private final DeliverCallback callback = (consumerTag, delivery) -> {
        String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
        System.out.println("received message: '" + message + "' with consumerTag: " + consumerTag);
    };

    private String receiveString() throws IOException {
        return channel.basicConsume(queueName, true, callback, consumerTag -> {
        });
    }
}
