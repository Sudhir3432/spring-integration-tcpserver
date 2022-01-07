package com.sipl.tcp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.integration.ip.tcp.TcpInboundGateway;
import org.springframework.integration.ip.tcp.TcpOutboundGateway;
import org.springframework.integration.ip.tcp.connection.AbstractClientConnectionFactory;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNetClientConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNetServerConnectionFactory;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Header;

@Configuration
@EnableIntegration
@IntegrationComponentScan
public class TcpServerConfig {
	public static final CustomSerializerDeserializer SERIALIZER = new CustomSerializerDeserializer();
    @Value("${socket.port}")
    private int socketPort;

    /**
     * Reply messages are routed to the connection only if the reply contains the ip_connectionId header
     * that was inserted into the original message by the connection factory.
     */
    @MessagingGateway(defaultRequestChannel = "toTcp")
    public interface Gateway {
        void send(String message, @Header(IpHeaders.CONNECTION_ID) String connectionId);
    }

    @Bean
    public MessageChannel fromTcp() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel toTcp() {
        return new DirectChannel();
    }

    @Bean
    public AbstractServerConnectionFactory serverCF() {
        TcpNetServerConnectionFactory serverCf = new TcpNetServerConnectionFactory(socketPort);
        serverCf.setSerializer(SERIALIZER);
        serverCf.setDeserializer(SERIALIZER);
        serverCf.setSoTcpNoDelay(true);
        serverCf.setSoKeepAlive(true);
        // serverCf.setSingleUse(true);
        // final int soTimeout = 5000;
        // serverCf.setSoTimeout(soTimeout);
        return serverCf;
    }

   /* @Bean
    public AbstractClientConnectionFactory clientCF() {

        TcpNetClientConnectionFactory clientCf = new TcpNetClientConnectionFactory("localhost", socketPort);
        clientCf.setSerializer(SERIALIZER);
        clientCf.setDeserializer(SERIALIZER);
        clientCf.setSoTcpNoDelay(true);
        clientCf.setSoKeepAlive(true);
        // clientCf.setSingleUse(true);
        // final int soTimeout = 5000;
        // clientCf.setSoTimeout(soTimeout);
        return clientCf;
    } */

    @Bean
    public TcpInboundGateway tcpInGate() {
        TcpInboundGateway inGate = new TcpInboundGateway();
        inGate.setConnectionFactory(serverCF());
        inGate.setRequestChannel(fromTcp());
        inGate.setReplyChannel(toTcp());
        return inGate;
    }

  /*  @Bean
    public TcpOutboundGateway tcpOutGate() {
        TcpOutboundGateway outGate = new TcpOutboundGateway();
        outGate.setConnectionFactory(clientCF());
        outGate.setReplyChannel(toTcp());
        return outGate;
    }  */
}
