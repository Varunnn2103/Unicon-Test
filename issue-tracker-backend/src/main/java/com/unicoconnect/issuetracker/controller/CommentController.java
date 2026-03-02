package com.unicoconnect.issuetracker.controller;

import com.unicoconnect.issuetracker.dto.CommentRequestDTO;
import com.unicoconnect.issuetracker.dto.CommentResponseDTO;
import com.unicoconnect.issuetracker.service.CommentService;
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
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<CommentResponseDTO>> getComments(@PathVariable Long issueId) {
        List<CommentResponseDTO> comments = commentService.getCommentsByIssueId(issueId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping
    public ResponseEntity<CommentResponseDTO> addComment(
            @PathVariable Long issueId,
            @Valid @RequestBody CommentRequestDTO dto) {
        CommentResponseDTO created = commentService.addComment(issueId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
