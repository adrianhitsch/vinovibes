import React from 'react';

const MobileNavigation = () => {
  const setActive = (e: any) => {
    const elements = document.querySelectorAll('button');
    console.log(elements);
    elements.forEach((el) => {
      el.classList.remove('active');
    });

    e.target.classList.add('active');
  };

  if (window.innerWidth > 575) {
    return null;
  }

  return (
    <div className="main-nav-mobile">
      <button type="button" className="button nav-button" onClick={setActive}>
        <span className="icon icon-dashboard"></span>
      </button>
      <button type="button" className="button nav-button" onClick={setActive}>
        <span className="icon icon-wine-glass"></span>
      </button>
      <button type="button" className="button menu-button round" onClick={setActive}>
        <span className="icon icon-plus"></span>
      </button>
      <button type="button" className="button nav-button" onClick={setActive}>
        <span className="icon icon-wine-bottle"></span>
      </button>
      <button type="button" className="button nav-button" onClick={setActive}>
        <span className="icon icon-user"></span>
      </button>
    </div>
  );
};

export default MobileNavigation;
