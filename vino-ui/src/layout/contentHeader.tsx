import React, { createContext, useContext } from 'react';
import Searchbar from './searchbar';

type HeaderContentType = {
  header: string;
  text: string;
  buttons: Array<ButtonType>;
  headerContent?: any;
  setHeaderContent?: any;
};

export type ButtonType = {
  text?: string;
  type?: 'primary' | 'secondary' | 'close';
  icon?: string;
  disabled?: boolean;
  onClick?: () => void;
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
      <div className="button-container">
        {headerContent.buttons.map((button) => {
          if (button.type === 'close') {
            return (
              <button
                className={`button ${button.type}`}
                disabled={button.disabled || false}
                onClick={button.onClick}
              >
                {''}
                <span className={`icon icon-close`}></span>
              </button>
            );
          }
          return (
            <button
              className={`button ${button.type}`}
              disabled={button.disabled || undefined}
              onClick={button.onClick}
            >
              <span className={`icon icon-${button.icon}`}></span>
              {button.text}
            </button>
          );
        })}
      </div>
      <Searchbar />
    </div>
  );
};

export { HeaderContext };
export type { HeaderContentType };

export default ContentHeader;
