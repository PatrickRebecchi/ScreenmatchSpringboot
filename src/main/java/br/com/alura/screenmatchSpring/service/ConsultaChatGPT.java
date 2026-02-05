package br.com.alura.screenmatchSpring.service;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

public class ConsultaChatGPT {

    public static String obterTraducao(String texto) {

        ChatLanguageModel chatGPT = OpenAiChatModel.builder()
                .apiKey(System.getenv("OPENAI_API_KEY"))
                //.modelName("gpt-4o-mini")
                .modelName("gpt-5.2")
                .temperature(0.0)
                .build();

        String response = chatGPT.generate(
                "Traduza o seguinte texto para o português. " +
                        "Retorne apenas a tradução direta, sem comentários, sem explicações e sem variações: " + texto
        );

        return response;
    }
}