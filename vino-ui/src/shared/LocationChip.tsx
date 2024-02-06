import React from 'react';
import { Chips } from 'primereact/chips';

export const LocationChip = (item: string) => {
  return (
    <div className="locationChip">
      <div className="icon-container">
        <span className="icon icon-location"></span>
      </div>
      <span>{item}</span>
    </div>
  );
};

export default LocationChip;
