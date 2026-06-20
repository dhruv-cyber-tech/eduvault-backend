package com.eduvault.backend.service;

import com.eduvault.backend.model.Standard;
import com.eduvault.backend.model.Subject;
import com.eduvault.backend.repository.StandardRepository;
import com.eduvault.backend.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final StandardRepository standardRepository;

    public List<Subject> getSubjectsByStandard(Long standardId) {
        return subjectRepository.findByStandardId(standardId);
    }

    public Subject createSubject(String name, Long standardId) {
        // first check if standard exists
        Standard standard = standardRepository.findById(standardId)
                .orElseThrow(() -> new RuntimeException("Standard not found"));

        // check duplicate
        if (subjectRepository.existsByNameAndStandardId(name, standardId)) {
            throw new RuntimeException("Subject already exists in this standard");
        }

        Subject subject = new Subject();
        subject.setName(name);
        subject.setStandard(standard);
        return subjectRepository.save(subject);
    }

    public void deleteSubject(Long id) {
        if (!subjectRepository.existsById(id)) {
            throw new RuntimeException("Subject not found");
        }
        subjectRepository.deleteById(id);
    }
}