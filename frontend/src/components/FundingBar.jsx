import { useEffect, useRef, useState } from 'react';
import { formatCurrency } from '../utils/format';

export default function FundingBar({ raised, goal }) {
  const [width, setWidth] = useState(0);
  const ref = useRef(null);

  useEffect(() => {
    const timer = setTimeout(() => {
      const pct = Math.min((raised / goal) * 100, 100);
      setWidth(pct);
    }, 100);
    return () => clearTimeout(timer);
  }, [raised, goal]);

  return (
    <div>
      <div className="flex items-center justify-between mb-1">
        <span className="font-mono text-[10px] text-text-muted">
          {formatCurrency(raised)} raised
        </span>
        <span className="font-mono text-[10px] text-text-muted">
          {formatCurrency(goal)} goal
        </span>
      </div>
      <div
        ref={ref}
        className="h-2 w-full rounded-full bg-gray-100 overflow-hidden"
        role="progressbar"
        aria-valuenow={raised}
        aria-valuemin={0}
        aria-valuemax={goal}
        aria-label={`${formatCurrency(raised)} raised of ${formatCurrency(goal)} goal`}
      >
        <div
          className="funding-bar-fill h-full rounded-full bg-gradient-to-r from-primary to-primary-dark"
          style={{ width: `${width}%` }}
        />
      </div>
    </div>
  );
}
