import { useEffect, useState } from 'react';
import CompanyCard from '../components/CompanyCard';
import { getCompanies } from '../api/client';

export default function BrowsePage({ onSelectCompany }) {
  const [companies, setCompanies] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const fetchCompanies = async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await getCompanies();
      setCompanies(data);
    } catch (err) {
      setError('Unable to load companies. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchCompanies();
  }, []);

  return (
    <div className="px-4 py-5">
      <h2 className="font-display text-2xl text-text">Discover</h2>
      <p className="mt-1 text-sm text-text-muted">
        Support local businesses you believe in
      </p>

      <div className="mt-5 space-y-4">
        {loading && (
          <>
            <StartingHint />
            <SkeletonList />
          </>
        )}

        {error && (
          <ErrorState message={error} onRetry={fetchCompanies} />
        )}

        {!loading && !error && companies.length === 0 && <EmptyState />}

        {!loading &&
          !error &&
          companies.map((company) => (
            <CompanyCard
              key={company.id}
              company={company}
              onClick={onSelectCompany}
            />
          ))}
      </div>
    </div>
  );
}

function StartingHint() {
  const [visible, setVisible] = useState(false);
  useEffect(() => {
    const timer = setTimeout(() => setVisible(true), 3000);
    return () => clearTimeout(timer);
  }, []);
  if (!visible) return null;
  return (
    <p className="text-center text-xs text-text-muted animate-pulse mb-3">
      Waking up the server &mdash; this can take a moment...
    </p>
  );
}

function SkeletonList() {
  return (
    <>
      {[...Array(4)].map((_, i) => (
        <div
          key={i}
          className="rounded-2xl bg-surface p-4 shadow-sm ring-1 ring-border/50"
        >
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
          <div className="mt-3 skeleton-shimmer h-2 rounded-full" />
        </div>
      ))}
    </>
  );
}

function ErrorState({ message, onRetry }) {
  return (
    <div className="rounded-2xl bg-surface p-8 text-center shadow-sm ring-1 ring-border/50">
      <span className="text-3xl" role="img" aria-label="Warning">
        😕
      </span>
      <p className="mt-3 text-sm text-text-muted">{message}</p>
      <button
        onClick={onRetry}
        aria-label="Retry loading companies"
        className="mt-4 rounded-lg bg-primary-dark px-5 py-2 text-sm font-semibold text-white transition-colors hover:bg-green-700"
      >
        Try Again
      </button>
    </div>
  );
}

function EmptyState() {
  return (
    <div className="rounded-2xl bg-surface p-8 text-center shadow-sm ring-1 ring-border/50">
      <span className="text-3xl" role="img" aria-label="Search">
        🔍
      </span>
      <p className="mt-3 text-sm text-text-muted">
        No companies found right now. Check back soon!
      </p>
    </div>
  );
}
