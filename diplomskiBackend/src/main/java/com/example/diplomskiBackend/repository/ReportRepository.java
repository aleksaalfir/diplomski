package com.example.diplomskiBackend.repository;

import com.example.diplomskiBackend.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report,Long> {

    List<Report> findAllByVideoId(Long id);

}
