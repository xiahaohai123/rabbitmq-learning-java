package com.example.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import rabbitmq.tutorial.ConnectionModal;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class RabbitmqSender {

    public static void main(String[] args) throws IOException, TimeoutException {
        RabbitmqSender rabbitmqSender = new RabbitmqSender();
        rabbitmqSender.connect();
        rabbitmqSender.sendMessage("hello world");
        rabbitmqSender.close();
    }

    private Connection connection;
    private Channel channel;
    private String exchangeName = "myExchange";
    private String routingKey;

    private void connect() throws IOException, TimeoutException {
        ConnectionFactory factory = ConnectionModal.defaultConnectionModal.buildFactory();
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.exchangeDeclare(exchangeName, "direct", true);
        String queueName = channel.queueDeclare().getQueue();
        routingKey = queueName;
        channel.queueBind(queueName, exchangeName, routingKey);
    }

    private void close() throws IOException, TimeoutException {
        channel.close();
        connection.close();
    }

    private void sendMessage(String message) throws IOException {
        if (message == null) {
            return;
        }
        byte[] messageBodyBytes = message.getBytes(StandardCharsets.UTF_8);
        channel.basicPublish(exchangeName, routingKey, false, MessageProperties.PERSISTENT_TEXT_PLAIN,
                messageBodyBytes);
    }
}
