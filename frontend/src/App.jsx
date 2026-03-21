import { useState, useCallback } from 'react';
import Header from './components/Header';

import BrowsePage from './pages/BrowsePage';
import PortfolioPage from './pages/PortfolioPage';
import CompanyDrawer from './components/CompanyDrawer';
import { getCompany, invest } from './api/client';

export default function App() {
  const [currentPage, setCurrentPage] = useState('browse');
  const [selectedCompany, setSelectedCompany] = useState(null);
  const [drawerLoading, setDrawerLoading] = useState(false);

  const handleSelectCompany = useCallback(async (companySummary) => {
    setDrawerLoading(true);
    setSelectedCompany(companySummary);
    try {
      const detail = await getCompany(companySummary.id);
      setSelectedCompany(detail);
    } catch {
      setSelectedCompany(companySummary);
    } finally {
      setDrawerLoading(false);
    }
  }, []);

  const handleCloseDrawer = useCallback(() => {
    setSelectedCompany(null);
  }, []);

  const handleInvest = useCallback(async (optionId, amount) => {
    if (!selectedCompany) return;
    await invest(selectedCompany.id, optionId, amount);
  }, [selectedCompany]);

  return (
    <div className="min-h-screen bg-bg">
      <div className="mx-auto max-w-app">
        <Header currentPage={currentPage} onNavigate={setCurrentPage} />
        <main>
          {currentPage === 'browse' ? (
            <BrowsePage onSelectCompany={handleSelectCompany} />
          ) : (
            <PortfolioPage />
          )}
        </main>

      </div>

      {selectedCompany && (
        <CompanyDrawer
          company={selectedCompany}
          loading={drawerLoading}
          onClose={handleCloseDrawer}
          onInvest={handleInvest}
        />
      )}
    </div>
  );
}
