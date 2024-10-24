package com.example.chatcli.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Service
public class ChatService {

    private final ChatClient chatClient;

    public ChatService(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory())).build();
    }



    public String getResponse(String input) {
        String lowerCaseInput = input.toLowerCase();
        return chatClient.prompt().user(lowerCaseInput).call().content();
    }

    public void startChat() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Start chatting (type 'exit' to stop):");
        
        while (true) {
            System.out.print("You: ");
            String userInput = scanner.nextLine();
            
            if (userInput.equalsIgnoreCase("exit")) {
                System.out.println("Chat ended.");
                break;
            }

            String response = getResponse(userInput);
            System.out.println("Bot: " + response);
        }
        
        scanner.close();
    }
}
