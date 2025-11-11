package com.example.gymrat_backend.service;

import com.example.gymrat_backend.dto.PerformedSetDTO;

import java.util.List;

public interface PerformedSetService {
    
    List<PerformedSetDTO> getAllPerformedSets();
    
    PerformedSetDTO getPerformedSetById(Long id);
    
    List<PerformedSetDTO> getPerformedSetsByPerformedExerciseId(Long performedExerciseId);
    
    PerformedSetDTO createPerformedSet(PerformedSetDTO performedSetDTO);
    
    PerformedSetDTO updatePerformedSet(Long id, PerformedSetDTO performedSetDTO);
    
    void deletePerformedSet(Long id);
}
