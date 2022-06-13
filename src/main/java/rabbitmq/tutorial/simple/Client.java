package rabbitmq.tutorial.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import rabbitmq.tutorial.ConnectionModal;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Client {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Client client = new Client();
        client.connect();
        for (int i = 0; i < 1000; i++) {
            //Thread.sleep(10);
            String message = "hello: " + i;
            client.sendString(message);
            System.out.println("sent message: '" + message + "'");
        }
        client.close();
    }

    private Connection connection;
    private Channel channel;
    private final String queueName = "demo-simplest";

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

    private void sendString(String message) throws IOException {
        if (message == null) {
            return;
        }
        channel.basicPublish("", queueName, null, message.getBytes(StandardCharsets.UTF_8));
    }
}
