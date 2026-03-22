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
      <div className="flex items-center justify-between px-4 py-2.5">
        <Link
          to="/"
          onClick={scrollToTop}
          aria-label="MicroVest home"
          className="flex items-center gap-2 shrink-0"
        >
          <span className="flex h-8 w-8 items-center justify-center rounded-lg bg-white text-lg">
            🌱
          </span>
          <h1 className="font-display text-base font-bold text-white tracking-wide">
            MicroVest
          </h1>
        </Link>

        <button
          onClick={toggleLang}
          aria-label="Switch language"
          className="rounded-lg border border-white/30 px-2 py-1 text-[11px] font-bold text-white transition-colors hover:bg-white/15"
        >
          {i18n.language === 'hu' ? 'EN' : 'HU'}
        </button>
      </div>

      <nav className="flex px-4 pb-2 gap-1 overflow-x-auto no-scrollbar" aria-label="Main navigation">
        {navItems.map(({ path, key }) => {
          const active = pathname === path || (path === '/browse' && pathname.startsWith('/company/'));
          return (
            <Link
              key={key}
              to={path}
              aria-current={active ? 'page' : undefined}
              className={`shrink-0 rounded-full px-3 py-1 text-[11px] font-semibold transition-colors ${
                active
                  ? 'bg-white/25 text-white'
                  : 'text-emerald-200 hover:bg-white/10 hover:text-white'
              }`}
            >
              {t(`nav.${key}`)}
            </Link>
          );
        })}
      </nav>
    </header>
  );
}
