package com.jeleniasty.passwordgenerator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PasswordRepository extends JpaRepository<Password, Long> {

    Optional<Password> findByPassword(String password);

    void deleteByPassword(String password);

}
