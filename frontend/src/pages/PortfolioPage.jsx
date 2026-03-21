import { useEffect, useState } from 'react';
import PortfolioCard from '../components/PortfolioCard';
import { getPortfolio } from '../api/client';
import { formatCurrency, formatPercent } from '../utils/format';

export default function PortfolioPage() {
  const [portfolio, setPortfolio] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const fetchPortfolio = async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await getPortfolio();
      setPortfolio(data);
    } catch (err) {
      setError('Unable to load your portfolio. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchPortfolio();
  }, []);

  if (loading) return <LoadingState />;
  if (error) return <ErrorState message={error} onRetry={fetchPortfolio} />;
  if (!portfolio || !portfolio.investments || portfolio.investments.length === 0) {
    return <EmptyState />;
  }

  return (
    <div className="px-4 py-5">
      <h2 className="font-display text-2xl text-text">Your Portfolio</h2>
      <p className="mt-1 text-sm text-text-muted">Track your local investments</p>

      <SummaryCard portfolio={portfolio} />

      <InvestmentList investments={portfolio.investments} />
    </div>
  );
}

function SummaryCard({ portfolio }) {
  const { totalInvested, estimatedValue } = portfolio;
  const gain = estimatedValue - totalInvested;
  const gainPct = totalInvested > 0 ? (gain / totalInvested) * 100 : 0;
  const isPositive = gain >= 0;

  return (
    <div className="mt-5 rounded-2xl bg-gradient-to-br from-primary-dark to-green-800 p-5 text-white shadow-lg">
      <p className="text-xs uppercase tracking-wider text-green-200 font-medium">
        Estimated Value
      </p>
      <p className="mt-1 font-mono text-3xl font-bold">
        {formatCurrency(estimatedValue)}
      </p>
      <div className="mt-3 flex items-center gap-4">
        <div>
          <p className="text-[10px] uppercase tracking-wider text-green-200">
            Total Invested
          </p>
          <p className="font-mono text-sm font-semibold">
            {formatCurrency(totalInvested)}
          </p>
        </div>
        <div>
          <p className="text-[10px] uppercase tracking-wider text-green-200">
            Total Gain
          </p>
          <p
            className={`font-mono text-sm font-semibold ${
              isPositive ? 'text-green-200' : 'text-red-300'
            }`}
          >
            {isPositive ? '+' : ''}
            {formatCurrency(gain)} ({isPositive ? '\u25B2' : '\u25BC'}{' '}
            {formatPercent(Math.abs(gainPct))})
          </p>
        </div>
      </div>
    </div>
  );
}

function InvestmentList({ investments }) {
  const grouped = groupByStatus(investments);
  const statusOrder = ['Active', 'Pending', 'Exited'];

  return (
    <div className="mt-6 space-y-6">
      {statusOrder.map((status) => {
        const items = grouped[status];
        if (!items || items.length === 0) return null;
        return (
          <section key={status}>
            <h3 className="font-display text-sm text-text-muted mb-3">
              {status} ({items.length})
            </h3>
            <div className="space-y-3">
              {items.map((inv) => (
                <PortfolioCard key={inv.id} investment={inv} />
              ))}
            </div>
          </section>
        );
      })}
    </div>
  );
}

function groupByStatus(investments) {
  return investments.reduce((acc, inv) => {
    const key = inv.status || 'Active';
    if (!acc[key]) acc[key] = [];
    acc[key].push(inv);
    return acc;
  }, {});
}

function LoadingState() {
  return (
    <div className="px-4 py-5">
      <div className="skeleton-shimmer h-7 w-40 rounded mb-2" />
      <div className="skeleton-shimmer h-4 w-56 rounded mb-5" />
      <div className="skeleton-shimmer h-32 rounded-2xl mb-6" />
      <div className="space-y-3">
        {[...Array(3)].map((_, i) => (
          <div
            key={i}
            className="skeleton-shimmer h-28 rounded-2xl"
          />
        ))}
      </div>
    </div>
  );
}

function ErrorState({ message, onRetry }) {
  return (
    <div className="px-4 py-5">
      <h2 className="font-display text-2xl text-text">Your Portfolio</h2>
      <div className="mt-5 rounded-2xl bg-surface p-8 text-center shadow-sm ring-1 ring-border/50">
        <span className="text-3xl" role="img" aria-label="Error">
          😕
        </span>
        <p className="mt-3 text-sm text-text-muted">{message}</p>
        <button
          onClick={onRetry}
          aria-label="Retry loading portfolio"
          className="mt-4 rounded-lg bg-primary-dark px-5 py-2 text-sm font-semibold text-white transition-colors hover:bg-green-700"
        >
          Try Again
        </button>
      </div>
    </div>
  );
}

function EmptyState() {
  return (
    <div className="px-4 py-5">
      <h2 className="font-display text-2xl text-text">Your Portfolio</h2>
      <div className="mt-5 rounded-2xl bg-surface p-8 text-center shadow-sm ring-1 ring-border/50">
        <span className="text-3xl" role="img" aria-label="Seedling">
          🌱
        </span>
        <p className="mt-3 font-display text-base text-text">
          Start your investment journey
        </p>
        <p className="mt-1 text-sm text-text-muted">
          Browse local businesses and make your first investment to see it here.
        </p>
      </div>
    </div>
  );
}
