import { Routes, Route } from 'react-router-dom';
import Header from './components/Header';
import LandingPage from './pages/LandingPage';
import AboutPage from './pages/AboutPage';
import BrowsePage from './pages/BrowsePage';
import CompanyPage from './pages/CompanyPage';
import PortfolioPage from './pages/PortfolioPage';
import MarketplacePage from './pages/MarketplacePage';
import BusinessPage from './pages/BusinessPage';

export default function App() {
  return (
    <div className="min-h-screen bg-bg">
      <div className="mx-auto max-w-app">
        <Header />
        <main>
          <Routes>
            <Route path="/" element={<LandingPage />} />
            <Route path="/about" element={<AboutPage />} />
            <Route path="/browse" element={<BrowsePage />} />
            <Route path="/company/:id" element={<CompanyPage />} />
            <Route path="/portfolio" element={<PortfolioPage />} />
            <Route path="/marketplace" element={<MarketplacePage />} />
            <Route path="/business" element={<BusinessPage />} />
          </Routes>
        </main>
      </div>
    </div>
  );
}
