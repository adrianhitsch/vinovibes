import React from "react";
import Router from "./Router";
import Navigation from "./layout/navigation";
import Searchbar from "./layout/searchbar";

const Platform = () => {
  return (
    <div className="platform">
      <Navigation />
      <Router />
      <Searchbar />
    </div>
  );
};
export default Platform;
