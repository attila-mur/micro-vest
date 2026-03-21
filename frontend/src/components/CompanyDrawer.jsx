import { useEffect } from 'react';
import { formatCurrency, formatPercent } from '../utils/format';
import InvestmentOptions from './InvestmentOptions';
import FundingBar from './FundingBar';
import { getPlStatement } from '../api/client';

export default function CompanyDrawer({ company, loading, onClose, onInvest }) {
  useEffect(() => {
    document.body.style.overflow = 'hidden';
    return () => {
      document.body.style.overflow = '';
    };
  }, []);

  useEffect(() => {
    const handleEsc = (e) => {
      if (e.key === 'Escape') onClose();
    };
    window.addEventListener('keydown', handleEsc);
    return () => window.removeEventListener('keydown', handleEsc);
  }, [onClose]);

  const handleDownloadPl = async () => {
    try {
      const response = await getPlStatement(company.id);
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', `${company.name}-PL-Statement.pdf`);
      document.body.appendChild(link);
      link.click();
      link.remove();
      window.URL.revokeObjectURL(url);
    } catch {
      // Silently handle - user can retry
    }
  };

  const growth = company.revenueGrowthPercent;
  const isPositive = growth != null && growth >= 0;

  return (
    <div className="fixed inset-0 z-50 flex items-end justify-center">
      <div
        className="drawer-overlay absolute inset-0 bg-black/40"
        onClick={onClose}
        aria-label="Close company details"
      />
      <div
        className="drawer-panel relative z-10 w-full max-w-app rounded-t-3xl bg-surface shadow-2xl"
        style={{ maxHeight: '90vh' }}
        role="dialog"
        aria-modal="true"
        aria-label={`Details for ${company.name}`}
      >
        <DrawerHeader company={company} onClose={onClose} />

        <div className="overflow-y-auto px-5 pb-8" style={{ maxHeight: 'calc(90vh - 80px)' }}>
          {loading ? (
            <DrawerSkeleton />
          ) : (
            <DrawerContent
              company={company}
              growth={growth}
              isPositive={isPositive}
              onDownloadPl={handleDownloadPl}
              onInvest={onInvest}
            />
          )}
        </div>
      </div>
    </div>
  );
}

function DrawerHeader({ company, onClose }) {
  return (
    <div className="sticky top-0 z-10 rounded-t-3xl bg-surface px-5 pt-3 pb-4">
      <div className="mx-auto mb-3 h-1 w-10 rounded-full bg-border" />
      <div className="flex items-start justify-between">
        <div className="flex items-center gap-3">
          <span className="text-4xl" role="img" aria-hidden="true">
            {company.logoEmoji}
          </span>
          <div>
            <h2 className="font-display text-xl text-text leading-tight">
              {company.name}
            </h2>
            <p className="text-sm text-text-muted">
              {company.category} &middot; {company.city}
            </p>
          </div>
        </div>
        <button
          onClick={onClose}
          aria-label="Close"
          className="rounded-full p-1.5 text-text-muted hover:bg-gray-100 transition-colors"
        >
          <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
            <line x1="18" y1="6" x2="6" y2="18" />
            <line x1="6" y1="6" x2="18" y2="18" />
          </svg>
        </button>
      </div>
      {company.tagline && (
        <p className="mt-2 text-sm font-medium text-primary-dark italic">
          {company.tagline}
        </p>
      )}
    </div>
  );
}

function DrawerContent({ company, growth, isPositive, onDownloadPl, onInvest }) {
  return (
    <>
      <StatsGrid company={company} growth={growth} isPositive={isPositive} />

      {company.fundingGoal && (
        <div className="mt-5">
          <FundingBar raised={company.fundingRaised} goal={company.fundingGoal} />
        </div>
      )}

      {company.description && (
        <section className="mt-6">
          <h3 className="font-display text-base text-text mb-2">Our Story</h3>
          <p className="text-sm text-text-muted leading-relaxed whitespace-pre-line">
            {company.description}
          </p>
        </section>
      )}

      {company.founderStatement && (
        <section className="mt-5 rounded-xl bg-bg p-4 border-l-4 border-primary">
          <p className="text-sm text-text leading-relaxed italic">
            &ldquo;{company.founderStatement}&rdquo;
          </p>
          <p className="mt-2 text-xs font-semibold text-primary-dark">
            &mdash; Founder
          </p>
        </section>
      )}

      {company.investmentPlan && (
        <section className="mt-6">
          <h3 className="font-display text-base text-text mb-2">
            Investment Plan
          </h3>
          <p className="text-sm text-text-muted leading-relaxed">
            {company.investmentPlan}
          </p>
        </section>
      )}

      {company.investmentOptions && company.investmentOptions.length > 0 && (
        <section className="mt-6">
          <h3 className="font-display text-base text-text mb-3">
            Investment Tiers
          </h3>
          <InvestmentOptions options={company.investmentOptions} onInvest={onInvest} />
        </section>
      )}

      <button
        onClick={onDownloadPl}
        aria-label={`Download P&L statement for ${company.name}`}
        className="mt-6 flex w-full items-center justify-center gap-2 rounded-xl border border-border bg-surface-alt py-3 text-sm font-medium text-text transition-colors hover:bg-gray-50"
      >
        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
          <path d="M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4" />
          <polyline points="7 10 12 15 17 10" />
          <line x1="12" y1="15" x2="12" y2="3" />
        </svg>
        Download P&L Statement
      </button>
    </>
  );
}

function StatsGrid({ company, growth, isPositive }) {
  return (
    <div className="mt-4 grid grid-cols-2 gap-3">
      <StatItem label="Revenue" value={formatCurrency(company.revenueLastYear)} />
      <StatItem
        label="Growth"
        value={`${isPositive ? '\u25B2' : '\u25BC'} ${formatPercent(Math.abs(growth ?? 0))}`}
        valueClass={isPositive ? 'text-success' : 'text-danger'}
      />
      <StatItem label="Employees" value={company.employeeCount} />
      <StatItem label="Founded" value={company.foundedYear} />
    </div>
  );
}

function StatItem({ label, value, valueClass = 'text-text' }) {
  return (
    <div className="rounded-xl bg-surface-alt p-3">
      <p className="text-[10px] uppercase tracking-wider text-text-muted font-medium">
        {label}
      </p>
      <p className={`font-mono text-sm font-semibold mt-0.5 ${valueClass}`}>
        {value}
      </p>
    </div>
  );
}

function DrawerSkeleton() {
  return (
    <div className="space-y-4 mt-4">
      <div className="grid grid-cols-2 gap-3">
        {[...Array(4)].map((_, i) => (
          <div key={i} className="skeleton-shimmer h-16 rounded-xl" />
        ))}
      </div>
      <div className="skeleton-shimmer h-2 rounded-full" />
      <div className="space-y-2 mt-6">
        <div className="skeleton-shimmer h-4 w-24 rounded" />
        <div className="skeleton-shimmer h-3 rounded" />
        <div className="skeleton-shimmer h-3 rounded" />
        <div className="skeleton-shimmer h-3 w-3/4 rounded" />
      </div>
    </div>
  );
}
