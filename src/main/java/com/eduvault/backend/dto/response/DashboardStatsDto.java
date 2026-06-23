package com.eduvault.backend.dto.response;

import com.eduvault.backend.model.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsDto {
    private long totalResources;
    private long thisMonthUploads;
    private long totalSubjects;
    private List<SubjectStats> resourcesBySubject;
    private List<TypeStats> resourcesByType;
    private List<Resource> recentUploads;

    @Data
    @AllArgsConstructor
    public static class SubjectStats {
        private String subjectName;
        private long count;
    }

    @Data
    @AllArgsConstructor
    public static class TypeStats {
        private String type;
        private long count;
    }
}