package com.jeleniasty.passwordgenerator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordRepository extends JpaRepository<Password, Long> {

    Password findByPassword(String name);

    void deleteByPassword(String password);

}
