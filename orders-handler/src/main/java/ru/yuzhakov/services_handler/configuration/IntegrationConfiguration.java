package ru.yuzhakov.services_handler.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.GenericTransformer;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.messaging.MessageChannel;

import java.io.File;

@Configuration
public class IntegrationConfiguration {

    @Bean
    public MessageChannel textInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel fileWriterChannel() {
        return new DirectChannel();
    }

    @Bean
    @Transformer(inputChannel = "textInputChannel", outputChannel = "fileWriterChannel")
    public GenericTransformer<String, String> mainTransformer() {
        return text -> {
            //логика
            return text;
        };
    }

    @Bean
    @ServiceActivator(inputChannel = "fileWriterChannel")
    public FileWritingMessageHandler messageHandler() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(
                new File("/media/yuzhakovmihail/DATA/04_GeekBrains/08_backend_java_spring/printspace/order-storage/integration_files"));
        handler.setExpectReply(false);
        handler.setFileExistsMode(FileExistsMode.APPEND);
        handler.setAppendNewLine(true);
        return handler;
    }
}
