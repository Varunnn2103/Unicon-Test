package com.unicoconnect.issuetracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IssueRequestDTO {

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must be at most 255 characters")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Project is required")
    @Pattern(regexp = "^(Alpha|Beta|Gamma|Delta)$", message = "Project must be one of: Alpha, Beta, Gamma, Delta")
    private String project;

    @NotNull(message = "Priority is required")
    private String priority;

    @NotNull(message = "Status is required")
    private String status;

    @NotBlank(message = "Assignee is required")
    @Pattern(regexp = "^(Alice|Bob|Carol|David|Eve)$", message = "Assignee must be one of: Alice, Bob, Carol, David, Eve")
    private String assignee;
}
