// Platform.tsx
import React from 'react';
import { useLocation } from 'react-router-dom';
import Router from './Router';
import Navigation from './layout/navigation';
import Searchbar from './layout/searchbar';

const Platform = () => {
  const location = useLocation();

  if (location.pathname === '/login') {
    return <Router />;
  }

  return (
    <div className="platform">
      <Navigation />

      <div className="content">
        <div className="content-header">
          <div>
            <h1>Mein Dashboard</h1>
            <h2>Willkommen auf deinem persönlichen Dashboard</h2>
          </div>
          <Searchbar />
        </div>
      </div>
      <Router />
    </div>
  );
};

export default Platform;
