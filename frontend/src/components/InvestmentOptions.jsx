import { useState } from 'react';
import { formatCurrency, formatPercent } from '../utils/format';

export default function InvestmentOptions({ options, onInvest }) {
  const [confirming, setConfirming] = useState(null);
  const [investing, setInvesting] = useState(false);
  const [success, setSuccess] = useState(false);

  const handleInvestClick = (option) => {
    setConfirming(option);
    setSuccess(false);
  };

  const handleConfirm = async () => {
    if (!confirming) return;
    setInvesting(true);
    try {
      await onInvest(confirming.id, confirming.minimumInvestment);
      setSuccess(true);
      setTimeout(() => {
        setConfirming(null);
        setSuccess(false);
      }, 2000);
    } catch {
      // Keep modal open for retry
    } finally {
      setInvesting(false);
    }
  };

  const handleCancel = () => {
    setConfirming(null);
    setSuccess(false);
  };

  return (
    <>
      <div className="space-y-3">
        {options.map((option) => (
          <TierCard
            key={option.id}
            option={option}
            onInvest={() => handleInvestClick(option)}
          />
        ))}
      </div>

      {confirming && (
        <ConfirmModal
          option={confirming}
          investing={investing}
          success={success}
          onConfirm={handleConfirm}
          onCancel={handleCancel}
        />
      )}
    </>
  );
}

function TierCard({ option, onInvest }) {
  return (
    <div className="rounded-xl border border-border bg-surface-alt p-4">
      <div className="flex items-center justify-between mb-2">
        <span className="font-display text-sm text-text">{option.tier}</span>
        <span className="rounded-full bg-primary/15 px-2 py-0.5 text-[10px] font-semibold text-primary-dark">
          {formatPercent(option.expectedAnnualReturn)} annual return
        </span>
      </div>
      <div className="flex items-center gap-4 text-xs text-text-muted mb-3">
        <span>
          Min: <span className="font-mono font-semibold text-text">{formatCurrency(option.minimumInvestment)}</span>
        </span>
        <span>
          Lock: <span className="font-semibold text-text">{option.lockupMonths} months</span>
        </span>
      </div>
      {option.perks && (
        <p className="text-xs text-text-muted mb-3 italic">{option.perks}</p>
      )}
      <button
        onClick={onInvest}
        aria-label={`Invest in ${option.tier} tier`}
        className="w-full rounded-lg bg-primary-dark py-2 text-sm font-semibold text-white transition-colors hover:bg-green-700 focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-primary-dark focus-visible:ring-offset-2"
      >
        Invest
      </button>
    </div>
  );
}

function ConfirmModal({ option, investing, success, onConfirm, onCancel }) {
  return (
    <div className="fixed inset-0 z-[60] flex items-center justify-center px-6">
      <div className="absolute inset-0 bg-black/30" onClick={onCancel} />
      <div className="relative z-10 w-full max-w-sm rounded-2xl bg-surface p-6 shadow-xl">
        {success ? (
          <div className="text-center">
            <span className="text-4xl" role="img" aria-label="Success">
              🎉
            </span>
            <p className="mt-3 font-display text-lg text-text">
              Investment Submitted!
            </p>
            <p className="mt-1 text-sm text-text-muted">
              Your investment is being processed.
            </p>
          </div>
        ) : (
          <>
            <h3 className="font-display text-lg text-text">
              Confirm Investment
            </h3>
            <p className="mt-2 text-sm text-text-muted">
              You are investing in the{' '}
              <span className="font-semibold text-text">{option.tier}</span> tier.
            </p>
            <div className="mt-4 rounded-xl bg-surface-alt p-3">
              <p className="text-xs text-text-muted">Amount</p>
              <p className="font-mono text-xl font-semibold text-text">
                {formatCurrency(option.minimumInvestment)}
              </p>
            </div>
            <div className="mt-5 flex gap-3">
              <button
                onClick={onCancel}
                aria-label="Cancel investment"
                className="flex-1 rounded-lg border border-border py-2.5 text-sm font-medium text-text transition-colors hover:bg-gray-50"
              >
                Cancel
              </button>
              <button
                onClick={onConfirm}
                disabled={investing}
                aria-label="Confirm investment"
                className="flex-1 rounded-lg bg-primary-dark py-2.5 text-sm font-semibold text-white transition-colors hover:bg-green-700 disabled:opacity-50"
              >
                {investing ? 'Processing...' : 'Confirm'}
              </button>
            </div>
          </>
        )}
      </div>
    </div>
  );
}
