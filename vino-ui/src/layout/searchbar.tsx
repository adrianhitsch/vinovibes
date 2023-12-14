import React, { useState } from "react";
import { AutoComplete } from "primereact/autocomplete";
import toast, { Toaster } from "react-hot-toast";

const Searchbar = () => {
  const [notifications, setNotifications] = useState(0);

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
        <button
          type="button"
          className="bell-button"
          onClick={() => setNotifications((prevState) => prevState + 1)}
        >
          <img src="svg/bell-solid.svg" />
          {notifications > 0 && (
            <span className="bell-notifications">
              {notifications < 10 ? notifications : "9+"}
            </span>
          )}
        </button>
      </div>
      <Toaster />
    </>
  );
};
export default Searchbar;
