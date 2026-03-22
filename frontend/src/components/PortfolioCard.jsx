import { useTranslation } from 'react-i18next';
import { formatCurrency, formatDate } from '../utils/format';

const statusStyles = {
  Active: 'bg-success/15 text-success',
  Pending: 'bg-warning/15 text-warning',
  Exited: 'bg-gray-100 text-text-muted',
};

export default function PortfolioCard({ investment }) {
  const { t } = useTranslation();
  const { companyName, logoEmoji, amountInvested, equityShareAcquired, investedAt, status } =
    investment;

  const statusKey = `portfolio.status${status}`;

  return (
    <div className="rounded-2xl bg-surface p-4 shadow-sm ring-1 ring-border/50">
      <div className="flex items-start justify-between">
        <div className="flex items-center gap-3 min-w-0">
          <span className="text-2xl flex-shrink-0" role="img" aria-hidden="true">
            {logoEmoji}
          </span>
          <div className="min-w-0">
            <h3 className="font-display text-sm text-text leading-snug truncate">
              {companyName}
            </h3>
          </div>
        </div>
        <span
          className={`rounded-full px-2 py-0.5 text-[10px] font-semibold flex-shrink-0 ${
            statusStyles[status] || statusStyles.Active
          }`}
        >
          {t(statusKey, status)}
        </span>
      </div>

      <div className="mt-3 grid grid-cols-2 gap-3">
        <div>
          <p className="text-[10px] uppercase tracking-wider text-text-muted font-medium">
            {t('portfolio.invested')}
          </p>
          <p className="font-mono text-sm font-semibold text-text">
            {formatCurrency(amountInvested)}
          </p>
        </div>
        <div className="text-right">
          <p className="text-[10px] uppercase tracking-wider text-text-muted font-medium">
            {t('portfolio.equity')}
          </p>
          <p className="font-mono text-sm font-semibold text-primary-dark">
            {equityShareAcquired}%
          </p>
        </div>
      </div>

      <p className="mt-2 text-xs text-text-muted">{formatDate(investedAt)}</p>
    </div>
  );
}
