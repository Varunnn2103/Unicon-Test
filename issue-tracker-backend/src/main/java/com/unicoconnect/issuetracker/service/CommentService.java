package com.unicoconnect.issuetracker.service;

import com.unicoconnect.issuetracker.dto.CommentRequestDTO;
import com.unicoconnect.issuetracker.dto.CommentResponseDTO;
import com.unicoconnect.issuetracker.exception.ResourceNotFoundException;
import com.unicoconnect.issuetracker.model.Comment;
import com.unicoconnect.issuetracker.model.Issue;
import com.unicoconnect.issuetracker.repository.CommentRepository;
import com.unicoconnect.issuetracker.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final IssueRepository issueRepository;

    public List<CommentResponseDTO> getCommentsByIssueId(Long issueId) {
        if (!issueRepository.existsById(issueId)) {
            throw new ResourceNotFoundException("Issue not found with id: " + issueId);
        }
        List<Comment> comments = commentRepository.findByIssueIdOrderByCreatedAtDesc(issueId);
        return comments.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public CommentResponseDTO addComment(Long issueId, CommentRequestDTO dto) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new ResourceNotFoundException("Issue not found with id: " + issueId));

        Comment comment = Comment.builder()
                .text(dto.getText())
                .issue(issue)
                .build();

        Comment saved = commentRepository.save(comment);
        return toResponseDTO(saved);
    }

    private CommentResponseDTO toResponseDTO(Comment comment) {
        return CommentResponseDTO.builder()
                .id(comment.getId())
                .text(comment.getText())
                .issueId(comment.getIssue().getId())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
