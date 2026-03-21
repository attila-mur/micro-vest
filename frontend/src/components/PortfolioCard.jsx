import { formatCurrency, formatPercent, formatDate } from '../utils/format';

const statusStyles = {
  Active: 'bg-success/15 text-success',
  Pending: 'bg-warning/15 text-warning',
  Exited: 'bg-gray-100 text-text-muted',
};

export default function PortfolioCard({ investment }) {
  const { company, amountInvested, estimatedValue, investedAt, status, option } =
    investment;

  const gain = estimatedValue != null ? estimatedValue - amountInvested : null;
  const gainPct =
    gain != null && amountInvested > 0 ? (gain / amountInvested) * 100 : null;
  const isPositive = gain != null && gain >= 0;

  return (
    <div className="rounded-2xl bg-surface p-4 shadow-sm ring-1 ring-border/50">
      <div className="flex items-start justify-between">
        <div className="flex items-center gap-3 min-w-0">
          <span className="text-2xl flex-shrink-0" role="img" aria-hidden="true">
            {company?.logoEmoji}
          </span>
          <div className="min-w-0">
            <h3 className="font-display text-sm text-text leading-snug truncate">
              {company?.name}
            </h3>
            {option?.tier && (
              <p className="text-xs text-text-muted">{option.tier} tier</p>
            )}
          </div>
        </div>
        <span
          className={`rounded-full px-2 py-0.5 text-[10px] font-semibold flex-shrink-0 ${
            statusStyles[status] || statusStyles.Active
          }`}
        >
          {status}
        </span>
      </div>

      <div className="mt-3 grid grid-cols-2 gap-3">
        <div>
          <p className="text-[10px] uppercase tracking-wider text-text-muted font-medium">
            Invested
          </p>
          <p className="font-mono text-sm font-semibold text-text">
            {formatCurrency(amountInvested)}
          </p>
        </div>
        <div className="text-right">
          <p className="text-[10px] uppercase tracking-wider text-text-muted font-medium">
            Current Value
          </p>
          <p className="font-mono text-sm font-semibold text-text">
            {estimatedValue != null ? formatCurrency(estimatedValue) : '--'}
          </p>
        </div>
      </div>

      <div className="mt-2 flex items-center justify-between">
        <p className="text-xs text-text-muted">
          {formatDate(investedAt)}
        </p>
        {gainPct != null && (
          <p
            className={`font-mono text-xs font-semibold ${
              isPositive ? 'text-success' : 'text-danger'
            }`}
          >
            {isPositive ? '+' : ''}
            {formatCurrency(gain)} ({isPositive ? '\u25B2' : '\u25BC'}{' '}
            {formatPercent(Math.abs(gainPct))})
          </p>
        )}
      </div>
    </div>
  );
}
