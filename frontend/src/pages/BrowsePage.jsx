import { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import CompanyCard from '../components/CompanyCard';
import { getCompanies } from '../api/client';

export default function BrowsePage() {
  const { t } = useTranslation();
  const [companies, setCompanies] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [filter, setFilter] = useState('All');

  const fetchCompanies = async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await getCompanies();
      setCompanies(data);
    } catch {
      setError(true);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchCompanies();
  }, []);

  const categories = ['All', ...new Set(companies.map((c) => c.category))];
  const filtered = filter === 'All' ? companies : companies.filter((c) => c.category === filter);

  return (
    <div className="px-4 py-5">
      <h2 className="font-display text-2xl text-text">{t('browse.title')}</h2>
      <p className="mt-1 text-sm text-text-muted">{t('browse.subtitle')}</p>

      {!loading && !error && companies.length > 0 && (
        <div className="mt-4 flex gap-2 overflow-x-auto pb-1 no-scrollbar">
          {categories.map((cat) => (
            <button
              key={cat}
              onClick={() => setFilter(cat)}
              className={`shrink-0 rounded-full px-3 py-1 text-xs font-semibold transition-colors ${
                filter === cat
                  ? 'bg-primary-dark text-white'
                  : 'bg-surface text-text-muted ring-1 ring-border/50 hover:bg-border/30'
              }`}
            >
              {cat === 'All' ? t('browse.filterAll') : cat}
            </button>
          ))}
        </div>
      )}

      <div className="mt-4 space-y-4">
        {loading && <SkeletonList />}

        {error && (
          <div className="rounded-2xl bg-surface p-8 text-center shadow-sm ring-1 ring-border/50">
            <p className="text-sm text-text-muted">{t('common.error')}</p>
            <button
              onClick={fetchCompanies}
              className="mt-4 rounded-lg bg-primary-dark px-5 py-2 text-sm font-semibold text-white"
            >
              {t('common.retry')}
            </button>
          </div>
        )}

        {!loading && !error && filtered.length === 0 && (
          <p className="text-center text-sm text-text-muted py-8">{t('browse.noResults')}</p>
        )}

        {!loading && !error && filtered.map((company) => (
          <CompanyCard key={company.id} company={company} />
        ))}
      </div>
    </div>
  );
}

function SkeletonList() {
  return (
    <>
      {[...Array(4)].map((_, i) => (
        <div key={i} className="rounded-2xl bg-surface p-4 shadow-sm ring-1 ring-border/50">
          <div className="flex items-start gap-3">
            <div className="skeleton-shimmer h-10 w-10 rounded-xl" />
            <div className="flex-1 space-y-2">
              <div className="skeleton-shimmer h-4 w-3/4 rounded" />
              <div className="skeleton-shimmer h-3 w-1/2 rounded" />
            </div>
          </div>
          <div className="mt-4 flex gap-4">
            <div className="skeleton-shimmer h-8 flex-1 rounded" />
            <div className="skeleton-shimmer h-8 flex-1 rounded" />
            <div className="skeleton-shimmer h-8 flex-1 rounded" />
          </div>
        </div>
      ))}
    </>
  );
}
