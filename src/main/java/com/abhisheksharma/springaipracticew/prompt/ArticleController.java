package com.abhisheksharma.springaipracticew.prompt;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    private final ChatClient chatClient;

    public ArticleController(ChatClient.Builder builder){
        this.chatClient = builder.build();
    }

    @GetMapping("/posts/new")
    public String newPost(@RequestParam(value = "topic", defaultValue = "JDK virtual threads") String topic,
                          @RequestParam(value= "wordLimit", defaultValue = "20") Integer wordLimit
    ){

        String systemMessage = """
                Blog Post Generator Guidelines:

                1. Purpose: Generate a blog post that inform and engage general audience.

                2. Structure:
                    - Introduction
                    - Body
                    - Conclusion
                """;


        return chatClient.prompt()
                .user(u -> {
                    u.text("Write me a blog post about {topic} and the word limit must be {wordLimit}");
                    u.param("topic", topic);
                    u.param("wordLimit", wordLimit);
                })
                .system(systemMessage)
                .call()
                .content();

    }

}
