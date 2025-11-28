package com.abhisheksharma.springaipracticew.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/openai")
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }


    @GetMapping("/chat")
    public String chat(){
        return chatClient.prompt()
                .system("You are a head chef and your job is to provide good recipes. If any other task that is not related to cooking is asked, you simply deny.")
                .user("How to build a car?")
                .call()
                .content();
    }

    @GetMapping("/chat-stream")
    public Flux<String> stream(){
        return chatClient.prompt()
                .system("You are tours and travel guide. Your job is to provide information regarding cities in a country.")
                .user("I am visiting Hilton Head soon, can you give me 10 places that I must visit ?")
                .stream()
                .content();
    }

    @GetMapping("/chat-joke")
    public ChatResponse joke(){
        return chatClient.prompt()
                .user("Tell me a dad joke about dog.")
                .call()
                .chatResponse();
    }

}
