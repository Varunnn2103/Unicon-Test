package com.unicoconnect.issuetracker.repository;

import com.unicoconnect.issuetracker.model.Issue;
import com.unicoconnect.issuetracker.model.Priority;
import com.unicoconnect.issuetracker.model.Status;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class IssueSpecification {

    public static Specification<Issue> withFilters(String project, Priority priority, Status status, String assignee,
            String search) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (project != null && !project.isEmpty()) {
                predicates.add(cb.equal(root.get("project"), project));
            }
            if (priority != null) {
                predicates.add(cb.equal(root.get("priority"), priority));
            }
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }
            if (assignee != null && !assignee.isEmpty()) {
                predicates.add(cb.equal(root.get("assignee"), assignee));
            }
            if (search != null && !search.isEmpty()) {
                String searchLower = "%" + search.toLowerCase() + "%";
                Predicate titleMatch = cb.like(cb.lower(root.get("title")), searchLower);
                Predicate descMatch = cb.like(cb.lower(root.get("description")), searchLower);
                predicates.add(cb.or(titleMatch, descMatch));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
