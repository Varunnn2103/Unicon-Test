package com.unicoconnect.issuetracker.controller;

import com.unicoconnect.issuetracker.dto.IssueRequestDTO;
import com.unicoconnect.issuetracker.dto.IssueResponseDTO;
import com.unicoconnect.issuetracker.dto.StatusUpdateDTO;
import com.unicoconnect.issuetracker.service.IssueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Issues", description = "CRUD operations for managing issues")
public class IssueController {

    private final IssueService issueService;

    @Operation(summary = "Export issues as CSV", description = "Downloads all issues in CSV format")
    @ApiResponse(responseCode = "200", description = "CSV file downloaded")
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportCsv() {
        String csv = issueService.exportCsv();
        byte[] csvBytes = csv.getBytes();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=issues_export.csv");
        return ResponseEntity.ok().headers(headers).body(csvBytes);
    }

    @Operation(summary = "List all issues", description = "Returns all issues with optional filtering by project, priority, status, assignee, or search term")
    @ApiResponse(responseCode = "200", description = "List of issues")
    @GetMapping
    public ResponseEntity<List<IssueResponseDTO>> getAllIssues(
            @Parameter(description = "Filter by project name") @RequestParam(required = false) String project,
            @Parameter(description = "Filter by priority (LOW, MEDIUM, HIGH, CRITICAL)") @RequestParam(required = false) String priority,
            @Parameter(description = "Filter by status (OPEN, IN_PROGRESS, RESOLVED, CLOSED)") @RequestParam(required = false) String status,
            @Parameter(description = "Filter by assignee name") @RequestParam(required = false) String assignee,
            @Parameter(description = "Search in title and description") @RequestParam(required = false) String search) {
        List<IssueResponseDTO> issues = issueService.getAllIssues(project, priority, status, assignee, search);
        return ResponseEntity.ok(issues);
    }

    @Operation(summary = "Get issue by ID", description = "Returns a single issue by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Issue found"),
            @ApiResponse(responseCode = "404", description = "Issue not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<IssueResponseDTO> getIssueById(@PathVariable Long id) {
        IssueResponseDTO issue = issueService.getIssueById(id);
        return ResponseEntity.ok(issue);
    }

    @Operation(summary = "Create a new issue", description = "Creates a new issue and broadcasts via WebSocket")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Issue created successfully"),
            @ApiResponse(responseCode = "422", description = "Validation error")
    })
    @PostMapping
    public ResponseEntity<IssueResponseDTO> createIssue(@Valid @RequestBody IssueRequestDTO dto) {
        IssueResponseDTO created = issueService.createIssue(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Update issue status", description = "Updates the status of an existing issue")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status updated"),
            @ApiResponse(responseCode = "404", description = "Issue not found"),
            @ApiResponse(responseCode = "422", description = "Invalid status value")
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<IssueResponseDTO> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody StatusUpdateDTO dto) {
        IssueResponseDTO updated = issueService.updateStatus(id, dto.getStatus());
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Delete an issue", description = "Permanently deletes an issue and its comments")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Issue deleted"),
            @ApiResponse(responseCode = "404", description = "Issue not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIssue(@PathVariable Long id) {
        issueService.deleteIssue(id);
        return ResponseEntity.noContent().build();
    }
}
