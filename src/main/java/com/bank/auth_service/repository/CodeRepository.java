package com.bank.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.auth_service.model.Code;
import java.util.List;
import java.util.Optional;


public interface CodeRepository extends JpaRepository<Code, Long> {

    Optional<List<Code>> findByKey(String key);
}
