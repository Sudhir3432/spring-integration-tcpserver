package com.sipl.tcp.serviceImpl;

import org.springframework.stereotype.Service;

import com.sipl.tcp.service.MessageService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService {

	@Override
	public String processMessage(String message) {
		String messageContent = new String(message);
		log.info("Receive message: {}", messageContent);
		String responseContent = String.format("Message \"%s\" is processed", messageContent);
		return responseContent;
	}
}
