package com.jeleniasty.passwordgenerator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PasswordRepository extends JpaRepository<Password, Long> {

    List<Password> findByPassword(String name);

    void deleteByPassword(String password);

}
