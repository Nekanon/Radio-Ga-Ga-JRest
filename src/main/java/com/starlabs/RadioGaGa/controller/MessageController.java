package com.starlabs.RadioGaGa.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.starlabs.RadioGaGa.domain.User;
import com.starlabs.RadioGaGa.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping ("message")
public class MessageController {
    private int counter = 4;
    private final AppRunner appRunner;

    private List<Map<String, String>> messages = new ArrayList<Map<String, String>>() {{
        add(new HashMap<String, String>() {{ put("id", "1"); put("text", "First message"); }});
        add(new HashMap<String, String>() {{ put("id", "2"); put("text", "Second message"); }});
        add(new HashMap<String, String>() {{ put("id", "3"); put("text", "Third message"); }});
    }};

    @Autowired
    public MessageController(AppRunner appRunner) {
        this.appRunner = appRunner;
    }

    @GetMapping
    public List<Map<String, String>> list() {
        //appRunner.setBool(true);
        User object = new User().name("name").blog("blog");
        String json_1;
        String json0;
        String json;
        String json1;
        try{
            ObjectMapper mapper_2 = new ObjectMapper();
            String json_2 = "{ \"name\" : \"Black\", \"blog\" : \"BMW\" }";
            User car = mapper_2.readValue(json_2, User.class);



            json_1 = "{\\\"name\\\":\\\"name\\\", \\\"blog\\\":\\\"blog\\\"}";

            ObjectWriter ow0 = new ObjectMapper().writer().withDefaultPrettyPrinter();
            json0 = ow0.writeValueAsString(object);

            ObjectWriter ow = new ObjectMapper().writer();//.withDefaultPrettyPrinter();
            json = ow.writeValueAsString(object);
            int re = 5;

            ObjectMapper mapper1 = new ObjectMapper();
            json1 = mapper1.writeValueAsString(object);

            ObjectMapper mapper2 = new ObjectMapper();
            StringReader reader = new StringReader(json);
            User headClass1 = (User) mapper2.readValue(reader, User.class);
            re = 5;

            ObjectMapper objectMapper_1 = new ObjectMapper();
            User cars_1 = objectMapper_1.readValue(json, User.class);
            re = 5 + 5;

            ObjectMapper objectMapper = new ObjectMapper();
            User cars = objectMapper.readValue(json0, User.class);
            re = 5;
        } catch(Exception ex) {
            int re = 5;
        }

        return messages;
    }

    @GetMapping("{id}")
    public Map<String, String> getOne(@PathVariable String id) {
        return getMessage(id);
    }

    private Map<String, String> getMessage(@PathVariable String id) {
        return messages.stream()
                .filter(message -> message.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Map<String, String> create(@RequestBody Map<String, String> message) {
        message.put("id", String.valueOf(counter++));

        messages.add(message);

        return message;
    }

    @PutMapping("{id}")
    public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> message) {
        Map<String, String> messageFromDb = getMessage(id);

        messageFromDb.putAll(message);
        messageFromDb.put("id", id);

        return messageFromDb;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        Map<String, String> message = getMessage(id);

        messages.remove(message);
    }
//    private int counter = 5;
//
//    public List<Map<String, String>> messages = new ArrayList <Map<String, String>>() {{
//        add(new HashMap<String, String>() {{ put("id", "1"); put("text", "First message");}});
//        add(new HashMap<String, String>() {{ put("id", "2"); put("text", "Second message");}});
//        add(new HashMap<String, String>() {{ put("id", "3"); put("text", "Third message");}});
//        add(new HashMap<String, String>() {{ put("id", "4"); put("text", "Fourth message");}});
//    }};
//
//    @GetMapping
//    public List<Map<String, String>> list() {
//        return messages;
//    }
//
//    @GetMapping("{id}")
//    public Map<String, String> getOne(@PathVariable String id) {
//        return getMessage(id);
//    }
//
//    private Map<String, String> getMessage(@PathVariable String id) {
//        return messages.stream()
//                .filter(message -> message.get("id").equals(id))
//                .findFirst()
//                .orElseThrow(NotFoundException::new);
//    }
//
//    @PostMapping
//    public Map<String, String> create(@RequestBody Map<String, String> message) {
//        message.put("id", String.valueOf(counter++));
//        messages.add(message);
//        return message;
//    }
//
//    @PutMapping("{id}")
//    public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> message) {
//        Map<String, String> messageFromDb = getMessage(id);
//
//        messageFromDb.putAll(message);
//
//        messageFromDb.put("id", id);
//
//        return messageFromDb;
//    }
//
//    @DeleteMapping("{id}")
//    public void delete(@PathVariable String id) {
//        Map<String, String> message = getMessage(id);
//
//        messages.remove(message);
//    }
}