package com.example.gymrat_backend.controller;

import com.example.gymrat_backend.dto.PerformedSetDTO;
import com.example.gymrat_backend.service.PerformedSetService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/performed-sets")
public class PerformedSetController {
    
    private final PerformedSetService performedSetService;
    
    public PerformedSetController(PerformedSetService performedSetService) {
        this.performedSetService = performedSetService;
    }
    
    /**
     * GET /api/performed-sets - Hent alle udførte sæt
     */
    @GetMapping
    public ResponseEntity<List<PerformedSetDTO>> getAllPerformedSets() {
        List<PerformedSetDTO> performedSets = performedSetService.getAllPerformedSets();
        return ResponseEntity.ok(performedSets);
    }
    
    /**
     * GET /api/performed-sets/{id} - Hent et specifikt udført sæt
     */
    @GetMapping("/{id}")
    public ResponseEntity<PerformedSetDTO> getPerformedSetById(@PathVariable Long id) {
        PerformedSetDTO performedSet = performedSetService.getPerformedSetById(id);
        return ResponseEntity.ok(performedSet);
    }
    
    /**
     * GET /api/performed-sets/exercise/{performedExerciseId} - Hent alle udførte sæt for en udført øvelse
     */
    @GetMapping("/exercise/{performedExerciseId}")
    public ResponseEntity<List<PerformedSetDTO>> getPerformedSetsByPerformedExerciseId(
            @PathVariable Long performedExerciseId) {
        List<PerformedSetDTO> performedSets = 
            performedSetService.getPerformedSetsByPerformedExerciseId(performedExerciseId);
        return ResponseEntity.ok(performedSets);
    }
    
    /**
     * POST /api/performed-sets - Opret et nyt udført sæt
     */
    @PostMapping
    public ResponseEntity<PerformedSetDTO> createPerformedSet(
            @Valid @RequestBody PerformedSetDTO performedSetDTO) {
        PerformedSetDTO newPerformedSet = performedSetService.createPerformedSet(performedSetDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPerformedSet);
    }
    
    /**
     * PUT /api/performed-sets/{id} - Opdater et eksisterende udført sæt
     */
    @PutMapping("/{id}")
    public ResponseEntity<PerformedSetDTO> updatePerformedSet(
            @PathVariable Long id,
            @Valid @RequestBody PerformedSetDTO performedSetDTO) {
        PerformedSetDTO updatedPerformedSet = performedSetService.updatePerformedSet(id, performedSetDTO);
        return ResponseEntity.ok(updatedPerformedSet);
    }
    
    /**
     * DELETE /api/performed-sets/{id} - Slet et udført sæt
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerformedSet(@PathVariable Long id) {
        performedSetService.deletePerformedSet(id);
        return ResponseEntity.noContent().build();
    }
}
