import { useEffect, useRef, useState } from 'react';
import { formatCurrency, formatPercent } from '../utils/format';
import RiskBadge from './RiskBadge';
import FundingBar from './FundingBar';

export default function CompanyCard({ company, onClick }) {
  const growth = company.revenueGrowthPercent;
  const isPositive = growth >= 0;

  return (
    <button
      onClick={() => onClick(company)}
      aria-label={`View details for ${company.name}`}
      className="group w-full rounded-2xl bg-surface p-4 shadow-sm ring-1 ring-border/50 text-left transition-all duration-200 hover:shadow-md hover:-translate-y-0.5 focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-primary-dark"
    >
      <div className="flex items-start justify-between gap-3">
        <div className="flex items-center gap-3 min-w-0">
          <span className="text-3xl flex-shrink-0" role="img" aria-hidden="true">
            {company.logoEmoji}
          </span>
          <div className="min-w-0">
            <h3 className="font-display text-base text-text leading-snug truncate">
              {company.name}
            </h3>
            <p className="text-xs text-text-muted mt-0.5">{company.city}</p>
          </div>
        </div>
        <div className="flex flex-col items-end gap-1 flex-shrink-0">
          <span className="inline-block rounded-full bg-primary/15 px-2 py-0.5 text-[10px] font-semibold text-primary-dark">
            {company.category}
          </span>
          {company.featured && (
            <span className="inline-block rounded-full bg-warning/15 px-2 py-0.5 text-[10px] font-semibold text-warning">
              Featured
            </span>
          )}
        </div>
      </div>

      <div className="mt-3 flex items-center justify-between">
        <div>
          <p className="text-[10px] uppercase tracking-wider text-text-muted font-medium">
            Revenue
          </p>
          <p className="font-mono text-sm font-semibold text-text">
            {formatCurrency(company.revenueLastYear)}
          </p>
        </div>
        <div className="text-right">
          <p className="text-[10px] uppercase tracking-wider text-text-muted font-medium">
            Growth
          </p>
          <p
            className={`font-mono text-sm font-semibold ${
              isPositive ? 'text-success' : 'text-danger'
            }`}
          >
            {isPositive ? '\u25B2' : '\u25BC'} {formatPercent(Math.abs(growth))}
          </p>
        </div>
        <RiskBadge level={company.riskLevel} />
      </div>

      <div className="mt-3">
        <FundingBar raised={company.fundingRaised} goal={company.fundingGoal} />
      </div>
    </button>
  );
}
