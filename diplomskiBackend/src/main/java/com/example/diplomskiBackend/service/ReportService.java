package com.example.diplomskiBackend.service;

import com.example.diplomskiBackend.entity.Report;

import java.util.List;

public interface ReportService {

    List<Report> findAll();

    void acceptReport(Long id);

    void rejectReport(Long id);

    Report save(Report report);


}
