package com.eduvault.backend.service;

import com.eduvault.backend.dto.response.DashboardStatsDto;
import com.eduvault.backend.model.Resource;
import com.eduvault.backend.repository.ResourceRepository;
import com.eduvault.backend.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ResourceRepository resourceRepository;
    private final SubjectRepository subjectRepository;

    public DashboardStatsDto getStats() {

        // total resources
        long totalResources = resourceRepository.count();

        // total subjects
        long totalSubjects = subjectRepository.count();

        // resources by subject
        List<DashboardStatsDto.SubjectStats> bySubject = List.of(
                new DashboardStatsDto.SubjectStats("Maths",
                        resourceRepository.countBySubjectId(4L)),
                new DashboardStatsDto.SubjectStats("English",
                        resourceRepository.countBySubjectId(3L)),
                new DashboardStatsDto.SubjectStats("Gujarati",
                        resourceRepository.countBySubjectId(1L))
        );

        // resources by type
        List<DashboardStatsDto.TypeStats> byType = Arrays.stream(
                        Resource.ResourceType.values())
                .map(type -> new DashboardStatsDto.TypeStats(
                        type.name(),
                        resourceRepository.countByResourceType(type)))
                .toList();

        return new DashboardStatsDto(
                totalResources,
                totalResources, // simplified for now
                totalSubjects,
                bySubject,
                byType
        );
    }
}