package com.sipl.tcp.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.core.serializer.Deserializer;
import org.springframework.core.serializer.Serializer;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class CustomSerializerDeserializer implements Serializer<byte[]>, Deserializer<byte[]> {

	@Override
	public byte[] deserialize(InputStream inputStream) throws IOException {
		byte[] message = new byte[0];
		if (inputStream.available() > 0) {
			message = inputStream.readAllBytes();
		}
		log.debug("Deserialized message {}", new String(message, StandardCharsets.UTF_8));
		return inputStream.readAllBytes();

	}

	@Override
	public void serialize(byte[] message, OutputStream outputStream) throws IOException {
		log.info("Serializing {}", new String(message, StandardCharsets.UTF_8));
		outputStream.write(message);
		outputStream.flush();
	}
}
