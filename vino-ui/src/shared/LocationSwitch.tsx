import React, { useEffect, useState } from 'react';
import '../styles/locationSwitch.css';

interface LocationSwitchProps {
  onChange?: (state: string) => void;
}

const LocationSwitch = ({ onChange }: LocationSwitchProps) => {
  const [state, setState] = useState(false);

  useEffect(() => {
    if (state) {
      if (onChange) onChange('RESTAURANT');
    } else {
      if (onChange) onChange('STORE');
    }
  }, [state]);

  //switch component with two icons
  return (
    <>
      <div className="location-switch">
        <button type="button" className="button transparent" onClick={() => setState(true)}>
          <span className="icon icon-restaurant" />
          {''}
        </button>
        <button type="button" className="button transparent" onClick={() => setState(false)}>
          <span className="icon icon-store" />
          {''}
        </button>
        <div className={`slider ${state ? '' : 'right'}`}></div>
      </div>
    </>
  );
};

export default LocationSwitch;
