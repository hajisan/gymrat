package com.example.gymrat_backend.service;

import com.example.gymrat_backend.dto.PerformedSetDTO;
import com.example.gymrat_backend.exception.ResourceNotFoundException;
import com.example.gymrat_backend.exception.ValidationException;
import com.example.gymrat_backend.mapper.PerformedSetMapper;
import com.example.gymrat_backend.model.PerformedExercise;
import com.example.gymrat_backend.model.PerformedSet;
import com.example.gymrat_backend.repository.PerformedExerciseRepository;
import com.example.gymrat_backend.repository.PerformedSetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PerformedSetServiceImpl implements PerformedSetService {
    
    private final PerformedSetRepository performedSetRepository;
    private final PerformedExerciseRepository performedExerciseRepository;
    private final PerformedSetMapper performedSetMapper;
    
    public PerformedSetServiceImpl(PerformedSetRepository performedSetRepository,
                                  PerformedExerciseRepository performedExerciseRepository,
                                  PerformedSetMapper performedSetMapper) {
        this.performedSetRepository = performedSetRepository;
        this.performedExerciseRepository = performedExerciseRepository;
        this.performedSetMapper = performedSetMapper;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<PerformedSetDTO> getAllPerformedSets() {
        return performedSetRepository.findAll().stream()
                .map(performedSetMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public PerformedSetDTO getPerformedSetById(Long id) {
        PerformedSet performedSet = performedSetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Udført sæt", "id", id));
        return performedSetMapper.toDTO(performedSet);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<PerformedSetDTO> getPerformedSetsByPerformedExerciseId(Long performedExerciseId) {
        List<PerformedSet> performedSets = 
            performedSetRepository.findByPerformedExercisePerformedExerciseIdOrderBySetNumberAsc(performedExerciseId);
        return performedSets.stream()
                .map(performedSetMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public PerformedSetDTO createPerformedSet(PerformedSetDTO performedSetDTO) {
        // Valider at enten reps eller durationSeconds er sat
        if (performedSetDTO.getReps() == null && performedSetDTO.getDurationSeconds() == null) {
            throw new ValidationException("Enten 'reps' eller 'durationSeconds' skal være angivet");
        }
        
        // Hent performed exercise
        PerformedExercise performedExercise = performedExerciseRepository
                .findById(performedSetDTO.getPerformedExerciseId())
                .orElseThrow(() -> new ResourceNotFoundException("Udført øvelse", "id", 
                        performedSetDTO.getPerformedExerciseId()));
        
        // Opret performed set
        PerformedSet performedSet = performedSetMapper.toEntity(performedSetDTO);
        performedSet.setPerformedExercise(performedExercise);
        
        PerformedSet savedPerformedSet = performedSetRepository.save(performedSet);
        return performedSetMapper.toDTO(savedPerformedSet);
    }
    
    @Override
    public PerformedSetDTO updatePerformedSet(Long id, PerformedSetDTO performedSetDTO) {
        PerformedSet existingPerformedSet = performedSetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Udført sæt", "id", id));
        
        performedSetMapper.updateEntityFromDTO(performedSetDTO, existingPerformedSet);
        
        // Opdater performed exercise hvis det er ændret
        if (performedSetDTO.getPerformedExerciseId() != null && 
            !performedSetDTO.getPerformedExerciseId().equals(
                    existingPerformedSet.getPerformedExercise().getPerformedExerciseId())) {
            PerformedExercise performedExercise = performedExerciseRepository
                    .findById(performedSetDTO.getPerformedExerciseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Udført øvelse", "id", 
                            performedSetDTO.getPerformedExerciseId()));
            existingPerformedSet.setPerformedExercise(performedExercise);
        }
        
        PerformedSet updatedPerformedSet = performedSetRepository.save(existingPerformedSet);
        return performedSetMapper.toDTO(updatedPerformedSet);
    }
    
    @Override
    public void deletePerformedSet(Long id) {
        if (!performedSetRepository.existsById(id)) {
            throw new ResourceNotFoundException("Udført sæt", "id", id);
        }
        performedSetRepository.deleteById(id);
    }
}
