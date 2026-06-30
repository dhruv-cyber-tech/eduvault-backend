package com.eduvault.backend.service;

import com.eduvault.backend.dto.response.StudentDto;
import com.eduvault.backend.model.Standard;
import com.eduvault.backend.model.Student;
import com.eduvault.backend.repository.StandardRepository;
import com.eduvault.backend.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StandardRepository standardRepository;

    // Convert Entity -> DTO
    private StudentDto toDto(Student student) {
        return new StudentDto(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getParentName(),
                student.getParentContact(),
                student.getAddress(),
                student.getIsActive(),
                student.getCreatedAt(),
                student.getStandard().getId(),
                student.getStandard().getName()
        );
    }

    // Returns the actual entity (used internally)
    private Student getStudentEntity(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Student not found with id: " + id));
    }

    // Get all active students
    public List<StudentDto> getAllStudents() {
        return studentRepository.findByIsActive(true)
                .stream()
                .map(this::toDto)
                .toList();
    }

    // Get students by standard
    public List<StudentDto> getStudentsByStandard(Long standardId) {
        return studentRepository.findByStandardIdAndIsActive(standardId, true)
                .stream()
                .map(this::toDto)
                .toList();
    }

    // Get single student
    public StudentDto getStudentById(Long id) {
        return toDto(getStudentEntity(id));
    }

    // Create student
    public StudentDto createStudent(Student student, Long standardId) {

        Standard standard = standardRepository.findById(standardId)
                .orElseThrow(() -> new RuntimeException("Standard not found"));

        student.setStandard(standard);
        student.setIsActive(true);

        Student saved = studentRepository.save(student);
        return toDto(saved);
    }

    // Update student
    public StudentDto updateStudent(Long id, Student updatedStudent, Long standardId) {

        Student student = getStudentEntity(id);

        student.setFirstName(updatedStudent.getFirstName());
        student.setLastName(updatedStudent.getLastName());
        student.setParentName(updatedStudent.getParentName());
        student.setParentContact(updatedStudent.getParentContact());
        student.setAddress(updatedStudent.getAddress());

        if (standardId != null) {
            Standard standard = standardRepository.findById(standardId)
                    .orElseThrow(() -> new RuntimeException("Standard not found"));

            student.setStandard(standard);
        }

        Student saved = studentRepository.save(student);
        return toDto(saved);
    }

    // Soft delete student
    public void deactivateStudent(Long id) {

        Student student = getStudentEntity(id);

        student.setIsActive(false);

        studentRepository.save(student);
    }

    // Count active students
    public long getTotalActiveStudents() {
        return studentRepository.countByIsActive(true);
    }
}