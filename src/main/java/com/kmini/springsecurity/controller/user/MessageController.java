package com.kmini.springsecurity.controller.user;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MessageController {
	
	@GetMapping(value="/messages")
	public String mypage() throws Exception {

		return "user/messages";
	}

	@ResponseBody
	@PostMapping("/api/messages")
	public String apiMessage() {
		return " \"{ 'messages' : 'ok' }\" ";
//  		return "{ 'messages' : 'ok' }";
	}
}
