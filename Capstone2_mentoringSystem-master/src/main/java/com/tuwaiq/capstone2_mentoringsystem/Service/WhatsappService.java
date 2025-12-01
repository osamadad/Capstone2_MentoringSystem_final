package com.tuwaiq.capstone2_mentoringsystem.Service;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestInstance;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class WhatsappService {

    public void sendWhatsAppMessage(String to, String message) {
        UnirestInstance unirest = Unirest.spawnInstance();
        unirest.config()
                .socketTimeout(0)
                .connectTimeout(0);

        HttpResponse<String> response = Unirest.post("https://api.ultramsg.com/instance153447/messages/chat")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .field("token", "bpkp38d9f0qqn8wc")
                .field("to", to)
                .field("body", message)

                .asString();
        System.out.println(response.getBody());


    }
}


