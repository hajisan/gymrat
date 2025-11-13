package com.example.gymrat_backend.controller;

import com.example.gymrat_backend.dto.response.HomeResponse;
import com.example.gymrat_backend.service.HomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/home")
public class HomeController {

    private final HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    /**
     * GET /api/home/summary - Hent forsidedata
     *
     * Returnerer:
     * - Ugestatistik (træninger, sets, volumen)
     * - Seneste træning (dato + note)
     */
    @GetMapping("/summary")
    public ResponseEntity<HomeResponse> getHomeSummary() {
        HomeResponse summary = homeService.getHomeSummary();
        return ResponseEntity.ok(summary);
    }
}