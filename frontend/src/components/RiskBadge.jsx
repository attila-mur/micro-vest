const riskStyles = {
  Low: 'bg-success/15 text-success',
  Medium: 'bg-warning/15 text-warning',
  High: 'bg-danger/15 text-danger',
};

export default function RiskBadge({ level }) {
  return (
    <div className="text-right">
      <p className="text-[10px] uppercase tracking-wider text-text-muted font-medium">
        Risk
      </p>
      <span
        className={`inline-block mt-0.5 rounded-full px-2 py-0.5 text-[10px] font-semibold ${
          riskStyles[level] || riskStyles.Medium
        }`}
      >
        {level}
      </span>
    </div>
  );
}
