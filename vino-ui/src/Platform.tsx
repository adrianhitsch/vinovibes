import React from "react";
import Router from "./Router";
import Navigation from "./layout/navigation";

const Platform = () => {
  return (
    <div className="platform">
      <Navigation />
      <Router />
    </div>
  );
};
export default Platform;
