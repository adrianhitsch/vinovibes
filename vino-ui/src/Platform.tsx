// Platform.tsx
import React, { useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import Router from './Router';
import Navigation from './layout/navigation';
import Searchbar from './layout/searchbar';
import { useDispatch, useSelector } from 'react-redux';
import { storeType } from './redux/storeType';
import { logout, resetNewUser } from './redux/userSlice';
import toast from 'react-hot-toast';

const Platform = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const user = useSelector((state: storeType) => state.user);

  // navigate to login if not authenticated
  useEffect(() => {
    if (!user.token || user.sessionEnd < new Date().getTime()) {
      dispatch(logout());

      if (
        location.pathname !== '/login' &&
        location.pathname !== '/register' &&
        location.pathname !== '/forgot-password' &&
        location.pathname !== '/otp'
      ) {
        navigate('/login');
      }
    }
    if (user.newUser) {
      toast.success(`Herzlich willkommen ${user.firstName}!`);
      dispatch(resetNewUser());
      navigate('/profile');
    }
  }, [user.token, user.sessionEnd, user.newUser, user.firstName]);

  if (
    location.pathname === '/login' ||
    location.pathname === '/register' ||
    location.pathname === '/otp' ||
    location.pathname === '/forgot-password'
  ) {
    return (
      <>
        <Router />
      </>
    );
  }

  if (!user.token) {
    // dont render anything if not authenticated
    return <div></div>;
  }

  // background: linear-gradient(180deg, #0f1924 50%, #8bb7ff21);

  return (
    <>
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
    </>
  );
};

export default Platform;
