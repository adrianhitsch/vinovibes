// Platform.tsx
import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import Router from './Router';
import Navigation from './layout/navigation';
import Searchbar from './layout/searchbar';
import { useDispatch, useSelector } from 'react-redux';
import { storeType } from './redux/storeType';
import { logout, resetNewUser } from './redux/userSlice';
import toast, { Toaster } from 'react-hot-toast';
import ContentHeader, { HeaderContentType, HeaderContext } from './layout/contentHeader';
import MobileNavigation from './layout/mobileNavigation';

const Platform = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const [headerContent, setHeaderContent] = useState<HeaderContentType>({
    header: '',
    text: '',
    buttons: [],
  });

  const user = useSelector((state: storeType) => state.user);

  // navigate to login if not authenticated
  useEffect(() => {
    if (localStorage.getItem('path') !== null) {
      navigate(localStorage.getItem('path') as string);
      localStorage.removeItem('path');
    }

    if (!user.token || user.sessionEnd < new Date().getTime()) {
      dispatch(logout());

      if (
        location.pathname !== '/login' &&
        location.pathname !== '/register' &&
        !location.pathname.includes('/forgot-password') &&
        location.pathname !== '/otp'
      ) {
        navigate('/login');
      }
    }
    if (user.newUser) {
      toast.success(`Herzlich willkommen ${user.firstName}!`);
      dispatch(resetNewUser());
      navigate('/account');
    }
  }, [user.token, user.sessionEnd, user.newUser, user.firstName]);

  if (
    location.pathname === '/login' ||
    location.pathname === '/register' ||
    location.pathname === '/otp' ||
    location.pathname.includes('/forgot-password')
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
      <HeaderContext.Provider value={{ ...headerContent, setHeaderContent }}>
        <div className="platform">
          <Navigation />

          <div className="content">
            <ContentHeader />
            <Router />
          </div>
        </div>
      </HeaderContext.Provider>
      <MobileNavigation />
    </>
  );
};

export default Platform;
