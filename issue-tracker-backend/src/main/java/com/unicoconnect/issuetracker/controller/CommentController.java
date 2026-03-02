package com.unicoconnect.issuetracker.controller;

import com.unicoconnect.issuetracker.dto.CommentRequestDTO;
import com.unicoconnect.issuetracker.dto.CommentResponseDTO;
import com.unicoconnect.issuetracker.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues/{issueId}/comments")
@CrossOrigin(origins = { "http://localhost:5173", "http://localhost:3000" })
@RequiredArgsConstructor
@Tag(name = "Comments", description = "Manage comments on issues")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "Get all comments for an issue", description = "Returns all comments associated with the given issue ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of comments"),
            @ApiResponse(responseCode = "404", description = "Issue not found")
    })
    @GetMapping
    public ResponseEntity<List<CommentResponseDTO>> getComments(@PathVariable Long issueId) {
        return ResponseEntity.ok(commentService.getComments(issueId));
    }

    @Operation(summary = "Add a comment to an issue", description = "Creates a new comment on the specified issue")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Comment created"),
            @ApiResponse(responseCode = "404", description = "Issue not found"),
            @ApiResponse(responseCode = "422", description = "Validation error — 'text' field required")
    })
    @PostMapping
    public ResponseEntity<CommentResponseDTO> addComment(
            @PathVariable Long issueId,
            @Valid @RequestBody CommentRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.addComment(issueId, dto));
    }
}
