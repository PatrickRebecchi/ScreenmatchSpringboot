package br.com.alura.screenmatchSpring.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;

import java.util.List;

public class ConsultaChatGPT {
    public static String obterTraducao(String texto) {
        OpenAiService service = new OpenAiService("teste teste");

        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model("gpt-4o-mini")
                .messages(List.of(
                        new ChatMessage("system", "Você é um tradutor profissional."),
                        new ChatMessage("user", "Traduza para o português o texto a seguir:\n" + texto)
                ))
                .temperature(0.3)
                .build();

        var response = service.createChatCompletion(request);

        return response.getChoices()
                .get(0)
                .getMessage()
                .getContent();
    }
}
