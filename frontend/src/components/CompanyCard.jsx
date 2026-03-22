import { Link } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { formatCurrency } from '../utils/format';
import RiskBadge from './RiskBadge';

export default function CompanyCard({ company }) {
  const { t } = useTranslation();

  return (
    <Link
      to={`/company/${company.id}`}
      aria-label={company.name}
      className="group block w-full rounded-2xl bg-surface p-4 shadow-sm ring-1 ring-border/50 transition-all duration-200 hover:shadow-md hover:-translate-y-0.5 focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-primary-dark"
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
        <span className="inline-block rounded-full bg-primary/15 px-2 py-0.5 text-[10px] font-semibold text-primary-dark flex-shrink-0">
          {company.category}
        </span>
      </div>

      <div className="mt-3 flex items-center justify-between">
        <Stat label={t('browse.equityOffered')} value={`${company.equityOffered}%`} highlight />
        <Stat label={t('browse.amountSought')} value={formatCurrency(company.amountSought)} />
        <div>
          <p className="text-[10px] uppercase tracking-wider text-text-muted font-medium">{t('browse.risk')}</p>
          <RiskBadge level={company.riskLevel} />
        </div>
      </div>

      <div className="mt-2 flex items-center justify-between">
        <span className="text-[10px] text-text-muted">
          {t('browse.profitShare')}: <span className="font-mono font-semibold text-primary-dark">{company.profitSharePercent}%</span>
        </span>
      </div>
    </Link>
  );
}

function Stat({ label, value, highlight }) {
  return (
    <div>
      <p className="text-[10px] uppercase tracking-wider text-text-muted font-medium">{label}</p>
      <p className={`font-mono text-sm font-semibold ${highlight ? 'text-primary-dark' : 'text-text'}`}>
        {value}
      </p>
    </div>
  );
}
