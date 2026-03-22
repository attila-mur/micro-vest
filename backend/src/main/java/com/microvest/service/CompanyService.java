package com.microvest.service;

import java.util.List;

import com.microvest.dto.CompanyDetailDTO;
import com.microvest.dto.CompanySummaryDTO;
import com.microvest.model.Company;
import com.microvest.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyService {

    private final CompanyRepository companyRepository;

    public List<CompanySummaryDTO> getAllCompanies() {
        log.info("Fetching all companies");
        return companyRepository.findAll().stream()
                .map(this::toSummaryDTO)
                .toList();
    }

    public CompanyDetailDTO getCompanyById(Long id) {
        log.info("Fetching company with id: {}", id);
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + id));
        return toDetailDTO(company);
    }

    private CompanySummaryDTO toSummaryDTO(Company company) {
        return CompanySummaryDTO.builder()
                .id(company.getId())
                .name(company.getName())
                .category(company.getCategory())
                .city(company.getCity())
                .logoEmoji(company.getLogoEmoji())
                .tagline(company.getTagline())
                .foundedYear(company.getFoundedYear())
                .employeeCount(company.getEmployeeCount())
                .revenueLastYear(company.getRevenueLastYear())
                .equityOffered(company.getEquityOffered())
                .amountSought(company.getAmountSought())
                .profitSharePercent(company.getProfitSharePercent())
                .riskLevel(company.getRiskLevel())
                .featured(company.isFeatured())
                .build();
    }

    private CompanyDetailDTO toDetailDTO(Company company) {
        return CompanyDetailDTO.builder()
                .id(company.getId())
                .name(company.getName())
                .category(company.getCategory())
                .city(company.getCity())
                .logoEmoji(company.getLogoEmoji())
                .tagline(company.getTagline())
                .foundedYear(company.getFoundedYear())
                .employeeCount(company.getEmployeeCount())
                .revenueLastYear(company.getRevenueLastYear())
                .equityOffered(company.getEquityOffered())
                .amountSought(company.getAmountSought())
                .profitSharePercent(company.getProfitSharePercent())
                .riskLevel(company.getRiskLevel())
                .featured(company.isFeatured())
                .description(company.getDescription())
                .founderStatement(company.getFounderStatement())
                .investmentPlan(company.getInvestmentPlan())
                .build();
    }
}
