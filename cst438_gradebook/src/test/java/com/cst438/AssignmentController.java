package com.cst438;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.cst438.domain.Assignment;

@RestController
@RequestMapping("/assignments")
public class AssignmentController {

    private Map<Long, Assignment> assignments = new HashMap<>();

    @GetMapping("/{id}")
    public Assignment getAssignment(@PathVariable Long id) {
        return assignments.get(id);
    }

    @PostMapping("/")
    public Assignment addAssignment(@RequestBody Assignment assignment) {
        long id = generateAssignmentId();
        assignment.setId(id);
        assignments.put(id, assignment);
        return assignment;
    }

    @PutMapping("/{id}")
    public Assignment updateAssignment(@PathVariable Long id, @RequestBody Assignment updatedAssignment) {
        Assignment existingAssignment = assignments.get(id);
        if (existingAssignment == null) {
            throw new IllegalArgumentException("Assignment not found");
        }
        existingAssignment.setName(updatedAssignment.getName());
        // Update other assignment properties here
        return existingAssignment;
    }

    @DeleteMapping("/{id}")
    public void deleteAssignment(@PathVariable Long id) {
        Assignment assignment = assignments.get(id);
        if (assignment == null) {
            throw new IllegalArgumentException("Assignment not found");
        }

        assignments.remove(id);
    }

    @GetMapping("/")
    public List<Assignment> listAssignments() {
        return new ArrayList<>(assignments.values());
    }

    private long generateAssignmentId() {
        // Generate unique assignment IDs here
        return System.currentTimeMillis();
    }
}
