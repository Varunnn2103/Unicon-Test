package com.unicoconnect.issuetracker.service;

import com.unicoconnect.issuetracker.dto.IssueRequestDTO;
import com.unicoconnect.issuetracker.dto.IssueResponseDTO;
import com.unicoconnect.issuetracker.exception.ResourceNotFoundException;
import com.unicoconnect.issuetracker.model.Issue;
import com.unicoconnect.issuetracker.model.Priority;
import com.unicoconnect.issuetracker.model.Status;
import com.unicoconnect.issuetracker.repository.IssueRepository;
import com.unicoconnect.issuetracker.repository.IssueSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IssueService {

    private final IssueRepository issueRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public List<IssueResponseDTO> getAllIssues(String project, String priority, String status, String assignee,
            String search) {
        Priority priorityEnum = (priority != null && !priority.isEmpty()) ? Priority.valueOf(priority.toUpperCase())
                : null;
        Status statusEnum = (status != null && !status.isEmpty()) ? Status.valueOf(status.toUpperCase()) : null;

        Specification<Issue> spec = IssueSpecification.withFilters(project, priorityEnum, statusEnum, assignee, search);
        List<Issue> issues = issueRepository.findAll(spec);
        return issues.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public IssueResponseDTO getIssueById(Long id) {
        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Issue not found with id: " + id));
        return toResponseDTO(issue);
    }

    public IssueResponseDTO createIssue(IssueRequestDTO dto) {
        Issue issue = Issue.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .project(dto.getProject())
                .priority(Priority.valueOf(dto.getPriority().toUpperCase()))
                .status(Status.valueOf(dto.getStatus().toUpperCase()))
                .assignee(dto.getAssignee())
                .build();

        Issue saved = issueRepository.save(issue);
        IssueResponseDTO responseDTO = toResponseDTO(saved);
        messagingTemplate.convertAndSend("/topic/issues", responseDTO);
        return responseDTO;
    }

    public IssueResponseDTO updateStatus(Long id, String statusStr) {
        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Issue not found with id: " + id));

        Status newStatus = Status.valueOf(statusStr.toUpperCase());
        issue.setStatus(newStatus);
        Issue updated = issueRepository.save(issue);
        return toResponseDTO(updated);
    }

    public void deleteIssue(Long id) {
        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Issue not found with id: " + id));
        issueRepository.delete(issue);
    }

    public String exportCsv() {
        List<Issue> issues = issueRepository.findAll();
        StringBuilder sb = new StringBuilder();
        sb.append("ID,Title,Description,Project,Priority,Status,Assignee,Created\n");
        for (Issue issue : issues) {
            sb.append(issue.getId()).append(",");
            sb.append("\"").append(issue.getTitle().replace("\"", "\"\"")).append("\",");
            sb.append("\"").append(issue.getDescription().replace("\"", "\"\"")).append("\",");
            sb.append(issue.getProject()).append(",");
            sb.append(issue.getPriority()).append(",");
            sb.append(issue.getStatus()).append(",");
            sb.append(issue.getAssignee()).append(",");
            sb.append(issue.getCreatedAt()).append("\n");
        }
        return sb.toString();
    }

    private IssueResponseDTO toResponseDTO(Issue issue) {
        return IssueResponseDTO.builder()
                .id(issue.getId())
                .title(issue.getTitle())
                .description(issue.getDescription())
                .project(issue.getProject())
                .priority(issue.getPriority().name())
                .status(issue.getStatus().name())
                .assignee(issue.getAssignee())
                .createdAt(issue.getCreatedAt())
                .updatedAt(issue.getUpdatedAt())
                .build();
    }
}
