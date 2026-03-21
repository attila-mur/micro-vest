package com.microvest.controller;

import com.microvest.dto.InvestRequest;
import com.microvest.dto.InvestmentDTO;
import com.microvest.dto.PortfolioDTO;
import com.microvest.service.PortfolioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/portfolio")
@RequiredArgsConstructor
@Slf4j
public class PortfolioController {

    private final PortfolioService portfolioService;

    @Value("${app.demo-user-id:demo-user}")
    private String demoUserId;

    @GetMapping
    public ResponseEntity<PortfolioDTO> getPortfolio() {
        log.info("GET /api/portfolio for user: {}", demoUserId);
        PortfolioDTO portfolio = portfolioService.getPortfolio(demoUserId);
        return ResponseEntity.ok(portfolio);
    }

    @PostMapping("/invest")
    public ResponseEntity<InvestmentDTO> invest(@Valid @RequestBody InvestRequest request) {
        log.info("POST /api/portfolio/invest for user: {}", demoUserId);
        InvestmentDTO investment = portfolioService.invest(demoUserId, request);
        return ResponseEntity.ok(investment);
    }
}
