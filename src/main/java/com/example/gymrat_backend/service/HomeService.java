package com.example.gymrat_backend.service;

import com.example.gymrat_backend.dto.response.HomeResponse;

public interface HomeService {

    /**
     * Henter summary data til forsiden
     * - Ugestatistik (sidste 7 dage)
     * - Seneste træning
     */
    HomeResponse getHomeSummary();
}