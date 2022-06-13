package rabbitmq.tutorial;

import com.rabbitmq.client.ConnectionFactory;

public class ConnectionModal {
    public static final ConnectionModal defaultConnectionModal = new ConnectionModal("guest", "guest", "/",
            "localhost", 5672);

    String username;
    String password;
    String virtualHost;
    String hostname;
    Integer port;

    public ConnectionModal(String username, String password, String virtualHost, String hostname, Integer port) {
        this.username = username;
        this.password = password;
        this.virtualHost = virtualHost;
        this.hostname = hostname;
        this.port = port;
    }

    public ConnectionFactory buildFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername(getUsername());
        factory.setPassword(getPassword());
        factory.setVirtualHost(getVirtualHost());
        factory.setHost(getHostname());
        factory.setPort(getPort());
        return factory;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVirtualHost() {
        return virtualHost;
    }

    public void setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
