package com.sipl.tcp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/integrate")
public interface IntegrationController {

	@GetMapping
	public String getMessageFromIntgerationService(@PathVariable("message") String message);

}
