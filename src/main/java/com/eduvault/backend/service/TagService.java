package com.eduvault.backend.service;

import com.eduvault.backend.model.Tag;
import com.eduvault.backend.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public Tag createTag(String name) {
        // if tag already exists just return it
        // no need to create duplicate
        if (tagRepository.existsByName(name)) {
            return tagRepository.findByName(name).get();
        }

        Tag tag = new Tag();
        tag.setName(name);
        return tagRepository.save(tag);
    }
}