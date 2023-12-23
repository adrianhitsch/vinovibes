import React, { useContext, useEffect } from 'react';
import { HeaderContext } from '../layout/contentHeader';

const Dashboard = () => {
  const { setHeaderContent } = useContext(HeaderContext);

  useEffect(() => {
    setHeaderContent({
      header: 'Dashboard',
      text: 'Hier findest du deine aktuellen Statistiken',
      buttons: [
        {
          text: 'Fertig',
          icon: 'tik-mark-gradient',
          type: 'secondary',
          onClick: () => console.log('test1'),
        },
        { type: 'close', onClick: () => handleClick() },
      ],
    });
  }, []);

  const handleClick = () => {
    console.log('test');
  };

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
