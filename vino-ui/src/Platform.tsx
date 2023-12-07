import React from "react";
import Router from "./Router";
import Navigation from "./layout/navigation";
import Searchbar from "./layout/searchbar";

const Platform = () => {
  return (
    <div className="platform">
      <Navigation />

      <div className="content">
        <div className="content-header">
          <div>
            <h1>Mein Dashboard</h1>
            <h2>Willkommen auf deinem persönlichen Dashboard</h2>
          </div>
          <Searchbar />
        </div>

        <Router />
      </div>
    </div>
  );
};
export default Platform;
