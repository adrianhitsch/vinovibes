import React, { useState } from 'react';

const style = `
  .location-switch {
      position: relative; 
      display: flex;
      justify-content: space-around;
      align-items: center;
      width: 100%;
      height: 40px;
      background-color: var(--surface-color);
      border-radius: 10px;
      padding: 0 8px;
  }
  .slider {
      position: absolute;
      width: 50%;
      height: calc(100% - 8px);
      border-radius: 5px;
      background-color: white;
      transition: transform 0.2s ease-in-out;
      z-index: 0;
      left: 4px;

      &.right {
        transform: translateX(calc(100% - 8px));
      }
  }
  .location-switch .button {
      z-index: 1;
      width: 50%;
      height: calc(100% - 8px);
      pointer-events: all;
      cursor: pointer;

      span {
          margin: 0 auto;
      }
  }
  `;
const LocationSwitch = () => {
  const [state, setState] = useState(false);

  //switch component with two icons
  return (
    <>
      <style>{style}</style>
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
