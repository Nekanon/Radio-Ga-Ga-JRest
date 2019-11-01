package com.starlabs.RadioGaGa.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.starlabs.RadioGaGa.domain.Message;
//import com.starlabs.RadioGaGa.domain.User;
import com.starlabs.RadioGaGa.domain.Views;
import com.starlabs.RadioGaGa.exceptions.NotFoundException;
//import com.starlabs.RadioGaGa.repo.MessageRepo;
import com.starlabs.RadioGaGa.repo.MessageRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.StringReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping ("message")
public class MessageController {
    private final MessageRepo messageRepo;

    @Autowired
    public MessageController(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }


//    private int counter = 5;
//
//    public List<Map<String, String>> messages = new ArrayList <Map<String, String>>() {{
//        add(new HashMap<String, String>() {{ put("id", "1"); put("text", "First message");}});
//        add(new HashMap<String, String>() {{ put("id", "2"); put("text", "Second message");}});
//        add(new HashMap<String, String>() {{ put("id", "3"); put("text", "Third message");}});
//        add(new HashMap<String, String>() {{ put("id", "4"); put("text", "Fourth message");}});
//    }};

    @GetMapping
    @JsonView(Views.IdName.class)
    public List<Message> list() {
        return messageRepo.findAll();
    }

    @GetMapping("{id}")
    @JsonView(Views.FullMessage.class)
    public Message getOne(@PathVariable("id") Message message) {
//        return getMessage(id);
        return message;
    }


//    private Message getMessage(@PathVariable String id) {
//        return messages.stream()
//                .filter(message -> message.get("id").equals(id))
//                .findFirst()
//                .orElseThrow(NotFoundException::new);
//    }

    @PostMapping
    public Message create(@RequestBody Message message) {
//        message.put("id", String.valueOf(counter++));
//        messages.add(message);
//        return message;
        message.setCreationDate(LocalDateTime.now());
        return messageRepo.save(message);
    }

    @PutMapping("{id}")
    public Message update(@PathVariable("id") Message messageFromDb, @RequestBody Message message) {
//        Message messageFromDb = getMessage(id);
//        messageFromDb.putAll(message);
//        messageFromDb.put("id", id);
//        return messageFromDb;
        BeanUtils.copyProperties(message, messageFromDb, "id");
        return messageRepo.save(messageFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Message message) {
//        Message message = getMessage(id);
//        messages.remove(message);
        messageRepo.delete(message);
    }
}