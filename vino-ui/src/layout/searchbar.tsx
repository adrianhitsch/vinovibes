import React from "react";
import { AutoComplete } from "primereact/autocomplete";
import toast, { Toaster } from "react-hot-toast";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

import { ReactSVG } from "react-svg";

const Searchbar = () => {
  return (
    <>
      <div className="searchbar">
        <AutoComplete placeholder="Suche hier deinen Wein" />
        <button
          type="button"
          className="search-button"
          onClick={() => toast.error("Keine Weine gefunden")}
        >
          <img src="svg/magnifying-glass-solid.svg" />
        </button>
      </div>
      <button type="button" className="bell-button">
        <img src="svg/bell-solid.svg" />
      </button>
      <Toaster />
    </>
  );
};
export default Searchbar;
