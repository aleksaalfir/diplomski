package com.example.diplomskiBackend.repository;

import com.example.diplomskiBackend.entity.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministratorRepository extends JpaRepository<Administrator, Long> {
}
