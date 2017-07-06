package org.mordraug.wssecurity.controller;

import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;


@Controller
public class WebSocketController {
	String someText = "This is initial text";
	
	@MessageMapping("/settext")
	@SendTo("/app/text")
	public String setText(Message<String> msg){
		someText = msg.getPayload();
		System.out.println(someText);
		return someText;
	}
	
	@SubscribeMapping("/text")
	public String subText(){
		return someText;
	}
}
