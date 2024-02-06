import React, { useState } from 'react';
import '../styles/locationSwitch.css';
const LocationSwitch = () => {
  const [state, setState] = useState(false);

  //switch component with two icons
  return (
    <>
      <div className="location-switch">
        <button type="button" className="button transparent" onClick={() => setState(true)}>
          <span className="icon icon-restaurant" />
          {''}
        </button>
        <button type="button" className="button transparent" onClick={() => setState(false)}>
          <span className="icon icon-shop" />
          {''}
        </button>
        <div className={`slider ${state ? '' : 'right'}`}></div>
      </div>
    </>
  );
};

export default LocationSwitch;
