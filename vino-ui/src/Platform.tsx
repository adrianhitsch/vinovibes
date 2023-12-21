import React, { useState } from 'react';
import Router from './Router';
import Navigation from './layout/navigation';
import ContentHeader, { HeaderContentType, HeaderContext } from './layout/contentHeader';

const Platform = () => {
  const [headerContent, setHeaderContent] = useState<HeaderContentType>({
    header: '',
    text: '',
    buttons: [],
  });

  return (
    <HeaderContext.Provider value={{ ...headerContent, setHeaderContent }}>
      <div className="platform">
        <Navigation />
        <div className="content">
          <ContentHeader />
          <Router />
        </div>
      </div>
    </HeaderContext.Provider>
  );
};
export default Platform;
