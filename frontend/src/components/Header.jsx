export default function Header() {
  return (
    <header className="sticky top-0 z-30 border-b border-border bg-surface/90 backdrop-blur-md px-4 py-3">
      <div className="flex items-center gap-2">
        <span className="text-2xl" role="img" aria-label="Seedling">
          🌱
        </span>
        <div>
          <h1 className="font-display text-xl text-text leading-tight">
            MicroVest
          </h1>
          <p className="text-xs text-text-muted leading-tight">
            Invest in the places you love.
          </p>
        </div>
      </div>
    </header>
  );
}
