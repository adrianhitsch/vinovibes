import React, { useState } from 'react';
import { AutoComplete } from 'primereact/autocomplete';
import toast from 'react-hot-toast';

const Searchbar = () => {
  const [notifications, setNotifications] = useState(0);
  const [searchInput, setSearchInput] = useState('');
  const [showMobileSearch, setShowMobileSearch] = useState(false);

  const handleSearch = (e: any) => {
    if (window.innerWidth <= 1200) {
      if (showMobileSearch) {
        setShowMobileSearch(false);
      } else {
        setShowMobileSearch(true);
        setTimeout(() => {
          const mobileSearchInput = document.querySelector(
            '.search-input.mobile input',
          ) as HTMLInputElement;
          console.log(mobileSearchInput);
          mobileSearchInput?.focus();
        }, 100);
        return;
      }

      const searchInput = document.getElementById('search-input');

      searchInput?.classList.toggle('show');
    }

    if (searchInput) {
      toast.error(`${searchInput} wurde nicht gefunden`);
      return;
    }
    toast.error('Keine Weinempfehlung gefunden');
  };

  return (
    <>
      <div className="searchbar">
        <div className="search-input">
          <AutoComplete
            placeholder="Suche hier deinen Wein"
            id="search-input"
            value={searchInput}
            onChange={(e) => setSearchInput(e.target.value)}
          />
          <button type="button" className="button search-button" onClick={handleSearch}>
            <img src="svg/magnifying-glass-solid.svg" />
          </button>
        </div>
        <button
          type="button"
          className="bell-button"
          onClick={() => setNotifications((prevState) => prevState + 1)}
        >
          <img src="svg/bell-solid.svg" />
          {notifications > 0 && (
            <span className="bell-notifications">{notifications < 10 ? notifications : '9+'}</span>
          )}
        </button>
      </div>
      {showMobileSearch && (
        <>
          <div className="backdrop" onClick={() => setShowMobileSearch(false)}></div>
          <div className="search-input mobile">
            <AutoComplete
              placeholder="Suche hier deinen Wein"
              id="mobile-search-input"
              value={searchInput}
              onChange={(e) => setSearchInput(e.target.value)}
            />
            <button type="button" className="button search-button" onClick={handleSearch}>
              <img src="svg/magnifying-glass-solid.svg" />
            </button>
          </div>
        </>
      )}
    </>
  );
};
export default Searchbar;
