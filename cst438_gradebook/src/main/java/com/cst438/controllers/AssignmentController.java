package com.cst438.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
<<<<<<< HEAD

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
=======
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
>>>>>>> branch 'master' of git@github.com:cvlmd/CST438-Register-backend--Camila-.git
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.Assignment;
import com.cst438.domain.AssignmentDTO;
import com.cst438.domain.AssignmentRepository;
import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;

@RestController
@CrossOrigin
@RequestMapping("/api/assignments")
public class AssignmentController {
<<<<<<< HEAD
    
    @Autowired
    AssignmentRepository assignmentRepository;
    
    @Autowired
    CourseRepository courseRepository;
    
    @GetMapping("/assignment")
    public AssignmentDTO[] getAllAssignmentsForInstructor() {
        // get all assignments for this instructor
        String instructorEmail = "dwisneski@csumb.edu";  // user name (should be instructor's email) 
        List<Assignment> assignments = assignmentRepository.findByEmail(instructorEmail);
        AssignmentDTO[] result = new AssignmentDTO[assignments.size()];
        for (int i=0; i<assignments.size(); i++) {
            Assignment as = assignments.get(i);
            AssignmentDTO dto = new AssignmentDTO(
                    as.getId(), 
                    as.getName(), 
                    as.getDueDate().toString(), 
                    as.getCourse().getTitle(), 
                    as.getCourse().getCourse_id());
            result[i]=dto;
        }
        return result;
    }
    
    @GetMapping("/assignment/{id}")
    public AssignmentDTO getAssignment(@PathVariable("id") int id)  {
        String instructorEmail = "dwisneski@csumb.edu";  // user name (should be instructor's email)
        Assignment a = assignmentRepository.findById(id).orElse(null);
        if (a==null) {
            throw  new ResponseStatusException( HttpStatus.NOT_FOUND, "assignment not found "+id);
        }
        // check that assignment is for a course of this instructor
        if (! a.getCourse().getInstructor().equals(instructorEmail)) {
            throw  new ResponseStatusException( HttpStatus.FORBIDDEN, "not authorized "+id);
        }
        AssignmentDTO adto = new AssignmentDTO(a.getId(), a.getName(), a.getDueDate().toString(), a.getCourse().getTitle(), a.getCourse().getCourse_id());
        return adto;

    }
    
    @PostMapping("/assignment")
    public int createAssignment(@RequestBody AssignmentDTO adto) {
        // check that course exists and belongs to this instructor
        String instructorEmail = "dwisneski@csumb.edu";  // user name (should be instructor's email)
        Course c = courseRepository.findById(adto.courseId()).orElse(null);
        if (c==null || ! c.getInstructor().equals(instructorEmail)) {
            throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "course id not found or not authorized "+adto.courseId());
        }
        // create and save assignment.  Return generated id to client.
        Assignment a = new Assignment();
        a.setCourse(c);
        a.setDueDate( java.sql.Date.valueOf(adto.dueDate()));
        a.setName(adto.assignmentName());
        assignmentRepository.save(a);
        return a.getId();
    }
    
    @PutMapping("/assignment/{id}")
    public void updateAssignment(@PathVariable("id") int id, @RequestBody AssignmentDTO adto) {
        // check assignment belongs to a course for this instructor
        String instructorEmail = "dwisneski@csumb.edu";  // user name (should be instructor's email)
        Assignment a = assignmentRepository.findById(id).orElse(null);
        if (a==null || ! a.getCourse().getInstructor().equals(instructorEmail)) {
            throw  new ResponseStatusException( HttpStatus.NOT_FOUND, "assignment not found or not authorized "+id);
        }
        a.setDueDate( java.sql.Date.valueOf(adto.dueDate()));
        a.setName(adto.assignmentName());
        assignmentRepository.save(a);
    }
    
    @DeleteMapping("/assignment/{id}")
    public void deleteAssignment(@PathVariable("id") int id, @RequestParam("force") Optional<String> force) {
        // check assignment belongs to a course for this instructor
        String instructorEmail = "dwisneski@csumb.edu";  // user name (should be instructor's email)
        Assignment a = assignmentRepository.findById(id).orElse(null);
        if (a==null) {
            return;
        }
        if (! a.getCourse().getInstructor().equals(instructorEmail)) {
            throw  new ResponseStatusException( HttpStatus.FORBIDDEN, "not authorized "+id);
        }
        // does assignment have grades?  if yes, don't delete unless force is specified 
        if (a.getAssignmentGrades().size()==0 || force.isPresent()) {
            assignmentRepository.deleteById(id);
        } else {
            throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "assignment has grades ");
        }
    }
}
=======

    @Autowired
    AssignmentRepository assignmentRepository;

    @Autowired
    CourseRepository courseRepository;

 // Get all assignments
    @GetMapping
    public ResponseEntity<List<AssignmentDTO>> getAllAssignments() {
        List<Assignment> assignments = (List<Assignment>) assignmentRepository.findAll();
        if (assignments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            // Convert assignments to AssignmentDTOs
            List<AssignmentDTO> assignmentDTOs = AssignmentDTO.convertToDTOs(assignments);
            return new ResponseEntity<>(assignmentDTOs, HttpStatus.OK);
        }
    }

    // Get a specific assignment by ID
    @GetMapping("/{assignmentId}")
    public ResponseEntity<AssignmentDTO> getAssignmentById(@PathVariable int assignmentId) {
        Optional<Assignment> optionalAssignment = assignmentRepository.findById(assignmentId);
        if (!optionalAssignment.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Assignment not found");
        }

        Assignment assignment = optionalAssignment.get();
        AssignmentDTO assignmentDTO = AssignmentDTO.convertToDTO(assignment);
        return new ResponseEntity<>(assignmentDTO, HttpStatus.OK);
    }

    // Create a new assignment
    @PostMapping
    public ResponseEntity<AssignmentDTO> createAssignment(@RequestBody AssignmentDTO assignmentDTO) {
        try {
            Assignment assignment = AssignmentDTO.convertToEntity(assignmentDTO);

            // Retrieve the course by its ID
            Optional<Course> course = courseRepository.findById(assignmentDTO.getCourseId());
            if (!course.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found");
            }
            assignment.setCourse(course.get());

            Assignment savedAssignment = assignmentRepository.save(assignment);
            AssignmentDTO responseDTO = AssignmentDTO.convertToDTO(savedAssignment);

            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error creating assignment", e);
        }
    }

    // Update an assignment by ID
    @PutMapping("/{assignmentId}")
    public ResponseEntity<AssignmentDTO> updateAssignment(@PathVariable int assignmentId, @RequestBody AssignmentDTO assignmentDTO) {
        try {
            Optional<Assignment> optionalAssignment = assignmentRepository.findById(assignmentId);
            if (!optionalAssignment.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Assignment not found");
            }

            Assignment assignment = optionalAssignment.get();
            assignment.updateFromDTO(assignmentDTO);

            // Retrieve the course by its ID
            Optional<Course> course = courseRepository.findById(assignmentDTO.getCourseId());
            if (!course.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found");
            }
            assignment.setCourse(course.get());

            Assignment updatedAssignment = assignmentRepository.save(assignment);
            AssignmentDTO responseDTO = AssignmentDTO.convertToDTO(updatedAssignment);

            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error updating assignment", e);
        }
    }

    // Delete an assignment by ID
    @DeleteMapping("/{assignmentId}")
    public ResponseEntity<String> deleteAssignment(@PathVariable int assignmentId) {
        try {
            Optional<Assignment> optionalAssignment = assignmentRepository.findById(assignmentId);
            if (!optionalAssignment.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Assignment not found");
            }

            Assignment assignment = optionalAssignment.get();
            // You can implement logic to check if the assignment has associated grades here
            // if (assignmentHasGrades(assignment)) {
            //     throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Assignment has grades and cannot be deleted");
            // }

            assignmentRepository.delete(assignment);

            return new ResponseEntity<>("Assignment deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error deleting assignment", e);
        }
    }
}
>>>>>>> branch 'master' of git@github.com:cvlmd/CST438-Register-backend--Camila-.git
