package com.starlabs.RadioGaGa.repo;

import com.starlabs.RadioGaGa.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;

public interface MessageRepo extends JpaRepository<Message, Long> {

//    List<Map<String, String>> findAll();
}