package com.abhisheksharma.springaipracticew.multimodel;

import jakarta.annotation.Resources;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/imagedetection")
public class ImageDetection {

    private final ChatClient chatClient;

    @Value("classpath:/images/ruben-mavarez-iLDoHJrdf5s-unsplash.jpg")
    Resource sampleImage;

    public ImageDetection(ChatClient.Builder builder){
        this.chatClient = builder.build();
    }

    @GetMapping("/detect")
    public String imageToText(){
        return chatClient.prompt()
                .user(u -> {
                    u.text("Can you please tell me what you see in the following image ?");
                    u.media(MimeTypeUtils.IMAGE_JPEG, sampleImage);
                })
                .call()
                .content();
    }
}
