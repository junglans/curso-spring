package hello;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

@RestController
public class GreetingController {
	@Autowired
	private SimpMessagingTemplate template;
	

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting(message.getId(), "Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }
    
    @RequestMapping(value="/hello2", method=RequestMethod.POST)
    public Greeting greeting2(@RequestBody HelloMessage message, HttpServletRequest request) throws Exception {
    	 Greeting greeting =  new Greeting(message.getId(), "¡¡Hola, " + HtmlUtils.htmlEscape(message.getName()) + "!!");
    	 this.template.convertAndSend("/topic/greetings", greeting);
    	 return greeting;
    }

}
