package com.eduvault.backend.service;                               /**Controller  →  "What" (receives request)
                                                                     Service     →  "How"  (does the work)
                                                                     Repository  →  "Where" (talks to database)**/
import com.eduvault.backend.model.Standard;
import com.eduvault.backend.repository.StandardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StandardService {

    private final StandardRepository standardRepository;

    public List<Standard> getAllStandards() {
        return standardRepository.findAll();
    }

    public Standard createStandard(String name, Integer sortOrder) {
        if (standardRepository.existsByName(name)) {
            throw new RuntimeException("Standard with name '" + name + "' already exists");
        }
        Standard standard = new Standard();
        standard.setName(name);
        standard.setSortOrder(sortOrder);
        return standardRepository.save(standard);
    }

    public void deleteStandard(Long id) {
        if (!standardRepository.existsById(id)) {
            throw new RuntimeException("Standard not found with id: " + id);
        }
        standardRepository.deleteById(id);
    }
}
/** 1. Is this a valid PDF file?
 2. Does this Standard exist in DB?
 3. Does this Subject exist in DB?
 4. Save the PDF file to ~/eduvault-files/
 5. Create the tags if they don't exist
 6. Save everything to database
 7. Return the saved resource **/