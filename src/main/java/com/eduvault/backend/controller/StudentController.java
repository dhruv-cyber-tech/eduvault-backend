package com.eduvault.backend.controller;

import com.eduvault.backend.dto.response.ApiResponse;
import com.eduvault.backend.dto.response.StudentDto;
import com.eduvault.backend.model.Student;
import com.eduvault.backend.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    // GET /api/students
    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentDto>>> getAllStudents() {

        List<StudentDto> students = studentService.getAllStudents();

        return ResponseEntity.ok(
                ApiResponse.success(students, "Students fetched successfully"));
    }

    // GET /api/students/by-standard/1
    @GetMapping("/by-standard/{standardId}")
    public ResponseEntity<ApiResponse<List<StudentDto>>> getStudentsByStandard(
            @PathVariable Long standardId) {

        List<StudentDto> students =
                studentService.getStudentsByStandard(standardId);

        return ResponseEntity.ok(
                ApiResponse.success(students, "Students fetched successfully"));
    }

    // GET /api/students/1
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDto>> getStudentById(
            @PathVariable Long id) {

        StudentDto student = studentService.getStudentById(id);

        return ResponseEntity.ok(
                ApiResponse.success(student, "Student fetched successfully"));
    }

    // POST /api/students
    @PostMapping
    public ResponseEntity<ApiResponse<StudentDto>> createStudent(
            @RequestBody Map<String, Object> request) {

        Student student = new Student();

        student.setFirstName((String) request.get("firstName"));
        student.setLastName((String) request.get("lastName"));
        student.setParentName((String) request.get("parentName"));
        student.setParentContact((String) request.get("parentContact"));
        student.setAddress((String) request.get("address"));

        Long standardId =
                Long.valueOf(request.get("standardId").toString());

        StudentDto created = studentService.createStudent(student, standardId);

        return ResponseEntity.ok(
                ApiResponse.success(created, "Student created successfully"));
    }

    // PUT /api/students/1
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDto>> updateStudent(
            @PathVariable Long id,
            @RequestBody Map<String, Object> request) {

        Student student = new Student();

        student.setFirstName((String) request.get("firstName"));
        student.setLastName((String) request.get("lastName"));
        student.setParentName((String) request.get("parentName"));
        student.setParentContact((String) request.get("parentContact"));
        student.setAddress((String) request.get("address"));

        Long standardId = request.get("standardId") != null
                ? Long.valueOf(request.get("standardId").toString())
                : null;

        StudentDto updated =
                studentService.updateStudent(id, student, standardId);

        return ResponseEntity.ok(
                ApiResponse.success(updated, "Student updated successfully"));
    }

    // DELETE /api/students/1
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deactivateStudent(
            @PathVariable Long id) {

        studentService.deactivateStudent(id);

        return ResponseEntity.ok(
                ApiResponse.success(null, "Student deactivated successfully"));
    }

    // GET /api/students/count
    @GetMapping("/count")
    public ResponseEntity<ApiResponse<Long>> getTotalStudents() {

        long count = studentService.getTotalActiveStudents();

        return ResponseEntity.ok(
                ApiResponse.success(count, "Student count fetched successfully"));
    }
}