// Platform.tsx
import React, { useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import Router from './Router';
import Navigation from './layout/navigation';
import Searchbar from './layout/searchbar';
import { useSelector } from 'react-redux';
import { storeType } from './redux/storeType';

const Platform = () => {
  const location = useLocation();
  const navigate = useNavigate();

  const user = useSelector((state: storeType) => state.user);

  // navigate to login if not authenticated
  useEffect(() => {
    if (!user.token || user.sessionEnd < new Date().getTime()) {
      navigate('/login');
    }
  }, [user]);

  if (
    location.pathname === '/login' ||
    location.pathname === '/register' ||
    location.pathname === '/forgot-password'
  ) {
    return <Router />;
  }

  if (!user.token) {
    // dont render anything if not authenticated
    return <div></div>;
  }

  // background: linear-gradient(180deg, #0f1924 50%, #8bb7ff21);

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
        <Router />
      </div>
    </div>
  );
};

export default Platform;
