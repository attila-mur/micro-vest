import { Link, useLocation } from 'react-router-dom';
import { useTranslation } from 'react-i18next';

const navItems = [
  { path: '/browse', key: 'browse' },
  { path: '/portfolio', key: 'portfolio' },
  { path: '/about', key: 'about' },
  { path: '/marketplace', key: 'marketplace' },
];

export default function Header() {
  const { t, i18n } = useTranslation();
  const { pathname } = useLocation();

  const toggleLang = () => {
    i18n.changeLanguage(i18n.language === 'hu' ? 'en' : 'hu');
  };

  const scrollToTop = () => {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };

  return (
    <header className="sticky top-0 z-30 bg-gradient-to-r from-primary-dark to-emerald-900 shadow-md">
      <div className="flex items-center px-4 py-3">
        <Link
          to="/"
          onClick={scrollToTop}
          aria-label="MicroVest home"
          className="flex items-center gap-2.5 shrink-0"
        >
          <span className="flex h-9 w-9 items-center justify-center rounded-lg bg-white text-xl">
            🌱
          </span>
          <h1 className="font-display text-lg font-bold text-white tracking-wide">
            MicroVest
          </h1>
        </Link>

        <nav className="ml-auto flex items-center gap-1" aria-label="Main navigation">
          {navItems.map(({ path, key }) => {
            const active = pathname === path;
            return (
              <Link
                key={key}
                to={path}
                aria-current={active ? 'page' : undefined}
                className={`rounded-lg px-2.5 py-1.5 text-[11px] font-semibold transition-colors ${
                  active
                    ? 'bg-white/25 text-white'
                    : 'text-emerald-200 hover:bg-white/10 hover:text-white'
                }`}
              >
                {t(`nav.${key}`)}
              </Link>
            );
          })}

          <button
            onClick={toggleLang}
            aria-label="Switch language"
            className="ml-1 rounded-lg border border-white/30 px-2 py-1 text-[11px] font-bold text-white transition-colors hover:bg-white/15"
          >
            {i18n.language === 'hu' ? 'EN' : 'HU'}
          </button>
        </nav>
      </div>
    </header>
  );
}
