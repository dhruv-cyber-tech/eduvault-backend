package com.eduvault.backend.service;

import com.eduvault.backend.model.Chapter;
import com.eduvault.backend.model.Subject;
import com.eduvault.backend.repository.ChapterRepository;
import com.eduvault.backend.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChapterService {

    private final ChapterRepository chapterRepository;
    private final SubjectRepository subjectRepository;

    public List<Chapter> getChaptersBySubject(Long subjectId) {
        return chapterRepository.findBySubjectId(subjectId);
    }

    public Chapter createChapter(String name, Integer chapterNumber, Long subjectId) {
        // check if subject exists first
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        Chapter chapter = new Chapter();
        chapter.setName(name);
        chapter.setChapterNumber(chapterNumber);
        chapter.setSubject(subject);
        return chapterRepository.save(chapter);
    }

    public void deleteChapter(Long id) {
        if (!chapterRepository.existsById(id)) {
            throw new RuntimeException("Chapter not found");
        }
        chapterRepository.deleteById(id);
    }
}