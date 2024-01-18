import React, { useContext, useEffect } from 'react';
import { HeaderContext } from '../layout/contentHeader';

const Dashboard = () => {
  const { setHeaderContent } = useContext(HeaderContext);

  useEffect(() => {
    setHeaderContent({
      header: 'Dashboard',
      text: 'Hier findest du deine aktuellen Statistiken',
      buttons: [],
    });
  }, []);

  return (
    <>
      <div className="content-container">
        <div className="content-card"></div>
        <div className="content-card"></div>
        <span className="icon-wine-glass"></span>
      </div>
    </>
  );
};
export default Dashboard;
