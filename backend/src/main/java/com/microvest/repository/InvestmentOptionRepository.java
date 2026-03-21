package com.microvest.repository;

import java.util.List;

import com.microvest.model.InvestmentOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestmentOptionRepository extends JpaRepository<InvestmentOption, Long> {

    List<InvestmentOption> findByCompanyId(Long companyId);
}
