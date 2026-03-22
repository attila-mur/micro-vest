import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import PortfolioCard from '../components/PortfolioCard';
import { getPortfolio } from '../api/client';
import { formatCurrency } from '../utils/format';

export default function PortfolioPage() {
  const { t } = useTranslation();
  const [portfolio, setPortfolio] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const fetchPortfolio = async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await getPortfolio();
      setPortfolio(data);
    } catch {
      setError(true);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchPortfolio();
  }, []);

  if (loading) return <LoadingState />;
  if (error) {
    return (
      <div className="px-4 py-5">
        <h2 className="font-display text-2xl text-text">{t('portfolio.title')}</h2>
        <div className="mt-5 rounded-2xl bg-surface p-8 text-center shadow-sm ring-1 ring-border/50">
          <p className="text-sm text-text-muted">{t('common.error')}</p>
          <button
            onClick={fetchPortfolio}
            className="mt-4 rounded-lg bg-primary-dark px-5 py-2 text-sm font-semibold text-white"
          >
            {t('common.retry')}
          </button>
        </div>
      </div>
    );
  }
  if (!portfolio || !portfolio.investments || portfolio.investments.length === 0) {
    return (
      <div className="px-4 py-5">
        <h2 className="font-display text-2xl text-text">{t('portfolio.title')}</h2>
        <div className="mt-5 rounded-2xl bg-surface p-8 text-center shadow-sm ring-1 ring-border/50">
          <span className="text-3xl" role="img" aria-hidden="true">🌱</span>
          <p className="mt-3 text-sm text-text-muted">{t('portfolio.empty')}</p>
          <Link
            to="/browse"
            className="mt-3 inline-block rounded-lg bg-primary-dark px-5 py-2 text-sm font-semibold text-white"
          >
            {t('portfolio.emptyAction')}
          </Link>
        </div>
      </div>
    );
  }

  const grouped = groupByStatus(portfolio.investments);
  const statusOrder = ['Active', 'Pending', 'Exited'];

  return (
    <div className="px-4 py-5">
      <h2 className="font-display text-2xl text-text">{t('portfolio.title')}</h2>
      <p className="mt-1 text-sm text-text-muted">{t('portfolio.subtitle')}</p>

      <div className="mt-5 rounded-2xl bg-gradient-to-br from-primary-dark to-emerald-900 p-5 text-white shadow-lg">
        <p className="text-xs uppercase tracking-wider text-emerald-200 font-medium">
          {t('portfolio.totalInvested')}
        </p>
        <p className="mt-1 font-mono text-3xl font-bold">
          {formatCurrency(portfolio.totalInvested)}
        </p>
      </div>

      <div className="mt-6 space-y-6">
        {statusOrder.map((status) => {
          const items = grouped[status];
          if (!items || items.length === 0) return null;
          return (
            <section key={status}>
              <h3 className="font-display text-sm text-text-muted mb-3">
                {t(`portfolio.status${status}`)} ({items.length})
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
      <div className="skeleton-shimmer h-24 rounded-2xl mb-6" />
      <div className="space-y-3">
        {[...Array(3)].map((_, i) => (
          <div key={i} className="skeleton-shimmer h-24 rounded-2xl" />
        ))}
      </div>
    </div>
  );
}
