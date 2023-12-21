import React, { createContext, useContext } from 'react';
import Searchbar from './searchbar';

type HeaderContentType = {
  header: string;
  text: string;
  buttons: any[];
  headerContent?: any;
  setHeaderContent?: any;
};

const HeaderContext = createContext<HeaderContentType>({ header: '', text: '', buttons: [] });

const ContentHeader = () => {
  const headerContent = useContext(HeaderContext);

  return (
    <div className="content-header">
      <div>
        <h1>{headerContent.header}</h1>
        <h2>{headerContent.text}</h2>
      </div>
      <div className="button-container"></div>
      <Searchbar />
    </div>
  );
};

export { HeaderContext };
export type { HeaderContentType };

export default ContentHeader;
