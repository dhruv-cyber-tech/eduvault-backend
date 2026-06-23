package com.eduvault.backend.service;

import com.eduvault.backend.dto.response.DashboardStatsDto;
import com.eduvault.backend.model.Resource;
import com.eduvault.backend.repository.ResourceRepository;
import com.eduvault.backend.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ResourceRepository resourceRepository;
    private final SubjectRepository subjectRepository;

    public DashboardStatsDto getStats() {

        long totalResources = resourceRepository.count();
        long totalSubjects = subjectRepository.count();

        List<DashboardStatsDto.SubjectStats> bySubject = subjectRepository
                .findAll()
                .stream()
                .map(subject -> new DashboardStatsDto.SubjectStats(
                        subject.getName(),
                        resourceRepository.countBySubjectId(subject.getId())))
                .filter(s -> s.getCount() > 0)
                .toList();

        List<DashboardStatsDto.TypeStats> byType = Arrays.stream(
                        Resource.ResourceType.values())
                .map(type -> new DashboardStatsDto.TypeStats(
                        type.name(),
                        resourceRepository.countByResourceType(type)))
                .filter(t -> t.getCount() > 0)
                .toList();

        List<Resource> recentUploads = resourceRepository
                .findRecentUploads(PageRequest.of(0, 5));

        return new DashboardStatsDto(
                totalResources,
                totalResources,
                totalSubjects,
                bySubject,
                byType,
                recentUploads
        );
    }
}