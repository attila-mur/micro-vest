package com.microvest.controller;

import java.util.List;

import com.microvest.dto.CompanyDetailDTO;
import com.microvest.dto.CompanySummaryDTO;
import com.microvest.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
@Slf4j
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping
    public ResponseEntity<List<CompanySummaryDTO>> getAllCompanies() {
        log.info("GET /api/companies");
        List<CompanySummaryDTO> companies = companyService.getAllCompanies();
        return ResponseEntity.ok(companies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDetailDTO> getCompanyById(@PathVariable Long id) {
        log.info("GET /api/companies/{}", id);
        CompanyDetailDTO company = companyService.getCompanyById(id);
        return ResponseEntity.ok(company);
    }

    @GetMapping("/{id}/pl-statement")
    public ResponseEntity<byte[]> getPlStatement(@PathVariable Long id) {
        log.info("GET /api/companies/{}/pl-statement", id);
        byte[] pdfBytes = companyService.generatePlStatement(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "pl-statement-company-" + id + ".pdf");
        headers.setContentLength(pdfBytes.length);

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }
}
