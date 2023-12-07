import { Toast } from "primereact/toast";
import React, { useEffect } from "react";
import toast, { Toaster } from "react-hot-toast";
import { Button } from "primereact/button";

const Navigation = () => {
  return (
    <div className="main-nav">
      <img src="vinoVibes.png" alt="VinoLogo" className="vino-logo" />
      <nav></nav>
    </div>
  );
};

export default Navigation;
