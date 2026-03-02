package com.unicoconnect.issuetracker.controller;

import com.unicoconnect.issuetracker.dto.IssueRequestDTO;
import com.unicoconnect.issuetracker.dto.IssueResponseDTO;
import com.unicoconnect.issuetracker.dto.StatusUpdateDTO;
import com.unicoconnect.issuetracker.service.IssueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
@CrossOrigin(origins = { "http://localhost:5173", "http://localhost:3000" })
@RequiredArgsConstructor
public class IssueController {

    private final IssueService issueService;

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportCsv() {
        String csv = issueService.exportCsv();
        byte[] csvBytes = csv.getBytes();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=issues_export.csv");
        return ResponseEntity.ok().headers(headers).body(csvBytes);
    }

    @GetMapping
    public ResponseEntity<List<IssueResponseDTO>> getAllIssues(
            @RequestParam(required = false) String project,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String assignee,
            @RequestParam(required = false) String search) {
        List<IssueResponseDTO> issues = issueService.getAllIssues(project, priority, status, assignee, search);
        return ResponseEntity.ok(issues);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IssueResponseDTO> getIssueById(@PathVariable Long id) {
        IssueResponseDTO issue = issueService.getIssueById(id);
        return ResponseEntity.ok(issue);
    }

    @PostMapping
    public ResponseEntity<IssueResponseDTO> createIssue(@Valid @RequestBody IssueRequestDTO dto) {
        IssueResponseDTO created = issueService.createIssue(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<IssueResponseDTO> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody StatusUpdateDTO dto) {
        IssueResponseDTO updated = issueService.updateStatus(id, dto.getStatus());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIssue(@PathVariable Long id) {
        issueService.deleteIssue(id);
        return ResponseEntity.noContent().build();
    }
}
