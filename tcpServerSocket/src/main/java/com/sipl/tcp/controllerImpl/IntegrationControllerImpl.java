package com.sipl.tcp.controllerImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sipl.tcp.controller.IntegrationController;
import com.sipl.tcp.service.MessageService;

@Component
public class IntegrationControllerImpl implements IntegrationController {
	
	@Autowired
	private MessageService messageService;

	@Override
	public String getMessageFromIntgerationService(String message) {
		return messageService.processMessage(message);
	}
	
}
