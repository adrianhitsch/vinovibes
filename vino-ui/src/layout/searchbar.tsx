import React from "react";
import { AutoComplete } from "primereact/autocomplete";
import toast, { Toaster } from "react-hot-toast";

const Searchbar = () => {
  return (
    <>
      <div className="searchbar">
        <div className="search-input">
          <AutoComplete placeholder="Suche hier deinen Wein" />
          <button
            type="button"
            className="button search-button"
            onClick={() => toast.error("Keine Weine gefunden")}
          >
            <img src="svg/magnifying-glass-solid.svg" />
          </button>
        </div>
        <button type="button" className="bell-button">
          <img src="svg/bell-solid.svg" />
        </button>
      </div>
      <Toaster />
    </>
  );
};
export default Searchbar;
