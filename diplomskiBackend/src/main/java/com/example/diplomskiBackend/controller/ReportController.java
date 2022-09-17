package com.example.diplomskiBackend.controller;

import com.example.diplomskiBackend.entity.Report;
import com.example.diplomskiBackend.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping
    public ResponseEntity<List<Report>> getAll(){
        return new ResponseEntity<>(reportService.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @DeleteMapping("/accept/{id}")
    public ResponseEntity<Void> acceptReport(@PathVariable Long id){
        reportService.acceptReport(id);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @DeleteMapping("/reject/{id}")
    public ResponseEntity<Void> rejectReport(@PathVariable Long id){
        reportService.rejectReport(id);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<Report> save(@RequestBody Report report){
        return new ResponseEntity<>(reportService.save(report), HttpStatus.CREATED);
    }
}
