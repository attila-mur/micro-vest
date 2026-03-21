export default function Header({ currentPage, onNavigate }) {
  const scrollToTop = () => {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };

  return (
    <header className="sticky top-0 z-30 bg-gradient-to-r from-primary-dark to-green-700 shadow-md">
      <div className="flex items-center px-4 py-3">
        <button
          onClick={scrollToTop}
          aria-label="Scroll to top"
          className="flex items-center gap-2.5"
        >
          <span
            className="flex h-9 w-9 items-center justify-center rounded-lg bg-white text-xl"
            role="img"
            aria-label="MicroVest"
          >
            🌱
          </span>
          <h1 className="font-display text-lg font-bold text-white tracking-wide">
            MicroVest
          </h1>
        </button>

        <nav className="ml-auto flex gap-1" aria-label="Main navigation">
          <NavButton
            active={currentPage === 'browse'}
            onClick={() => onNavigate('browse')}
            label="Browse"
          >
            <BrowseIcon />
          </NavButton>
          <NavButton
            active={currentPage === 'portfolio'}
            onClick={() => onNavigate('portfolio')}
            label="Portfolio"
          >
            <PortfolioIcon />
          </NavButton>
        </nav>
      </div>
    </header>
  );
}

function NavButton({ active, onClick, label, children }) {
  return (
    <button
      onClick={onClick}
      aria-label={label}
      aria-current={active ? 'page' : undefined}
      className={`flex items-center gap-1.5 rounded-lg px-3 py-1.5 text-xs font-semibold transition-colors ${
        active
          ? 'bg-white/25 text-white'
          : 'text-green-200 hover:bg-white/10 hover:text-white'
      }`}
    >
      {children}
      <span>{label}</span>
    </button>
  );
}

function BrowseIcon() {
  return (
    <svg
      width="16"
      height="16"
      viewBox="0 0 24 24"
      fill="none"
      stroke="currentColor"
      strokeWidth={2}
      strokeLinecap="round"
      strokeLinejoin="round"
    >
      <circle cx="11" cy="11" r="8" />
      <line x1="21" y1="21" x2="16.65" y2="16.65" />
    </svg>
  );
}

function PortfolioIcon() {
  return (
    <svg
      width="16"
      height="16"
      viewBox="0 0 24 24"
      fill="none"
      stroke="currentColor"
      strokeWidth={2}
      strokeLinecap="round"
      strokeLinejoin="round"
    >
      <rect x="2" y="7" width="20" height="14" rx="2" ry="2" />
      <path d="M16 7V5a2 2 0 0 0-2-2h-4a2 2 0 0 0-2 2v2" />
    </svg>
  );
}
