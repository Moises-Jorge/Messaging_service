package com.example.messaging_service.controller;

// NESTE UNICO ARQUIVO DE ENDPOINTS, COLOCOU-SE TODOS OS SERVICOS, O QUE RESULTA EM ALTO ACOPLAMENTO. FAZER A SEPARACAO DOS SERVICOS MAIS TARDE

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;
import java.util.stream.Collectors;

import com.example.messaging_service.model.Message;
import com.example.messaging_service.model.User;
import com.example.messaging_service.repository.MessageRepository;
import com.example.messaging_service.repository.UserRepository;

import generated.classes.LoginUserRequest;
import generated.classes.LoginUserResponse;
import generated.classes.MessageList;
import generated.classes.ReceiveMessagesRequest;
import generated.classes.ReceiveMessagesResponse;
import generated.classes.RegisterUserRequest;
import generated.classes.RegisterUserResponse;
import generated.classes.SendMessageRequest;
import generated.classes.SendMessageResponse;

@Endpoint
public class MessagingEndpoint {
    private static final String NAMESPACE_URI = "http://www.classes.generated";

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    // DECLARACAO DO CONSTRUTOR PARA APLICAR A INVERSAO DE DEPENCDENCIA VIA CONSTRUTOR. MODIFICAR ISSO MAIS TARDE COM A ANNOTATION @Autowired
    public MessagingEndpoint(UserRepository userRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    // SEVICO para "CADASTRO" de "Usuarios"
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "registerUserRequest")
    @ResponsePayload
    public RegisterUserResponse registerUser(@RequestPayload RegisterUserRequest request) {
        RegisterUserResponse response = new RegisterUserResponse();
        if (userRepository.findByUsername(request.getUsername()) == null) {
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(request.getPassword());
            userRepository.save(user);
            response.setStatus("SUCCESS");
        } else {
            response.setStatus("USER_ALREADY_EXISTS");
        }
        return response;
    }

    // SERVICO de "LOGIN" de Usuarios
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "loginUserRequest")
    @ResponsePayload
    public LoginUserResponse loginUser(@RequestPayload LoginUserRequest request) {
        LoginUserResponse response = new LoginUserResponse();
        User user = userRepository.findByUsername(request.getUsername());
        if (user != null && user.getPassword().equals(request.getPassword())) { // VERIFICAR DEPOIS SE NO "LOGIN" JA NAO SE PEDE O "USERNAME"
            response.setStatus("SUCCESS");
        } else {
            response.setStatus("FAILURE");
        }
        return response;
    }

    // SERVICO de "ENVIO_DE_MENSAGENS"
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "sendMessageRequest")
    @ResponsePayload
    public SendMessageResponse sendMessage(@RequestPayload SendMessageRequest request) {
        SendMessageResponse response = new SendMessageResponse();
        Message message = new Message();
        message.setFromUser(request.getFrom());
        message.setToUser(request.getTo());
        message.setMessage(request.getMessage());
        messageRepository.save(message);
        response.setStatus("SUCCESS");
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "receiveMessagesRequest")
    @ResponsePayload
    public ReceiveMessagesResponse receiveMessages(@RequestPayload ReceiveMessagesRequest request) {
        ReceiveMessagesResponse response = new ReceiveMessagesResponse();
        List<Message> messages = messageRepository.findByToUser(request.getUsername());

        response.setMessages(messages.stream() // EM CASO DE BUG, ANALISAR AQUI(JUNTAMENTE COM O BUG EM "ReceiveMessagesResponse.java" e em "MessageList.java")...
            .map(msg -> {
                MessageList message = new MessageList();
                message.setFrom(msg.getFromUser());
                message.setTo(msg.getToUser());
                message.setMessage(msg.getMessage());
                return message;
            })
            .collect(Collectors.toList()));
        return response;
    }
}
