import React from "react";
import Searchbar from "../layout/searchbar";

const Dashboard = () => {
  return (
    <div className="content">
      <div className="content-header">
        <div>
          <h1>Mein Dashboard</h1>
          <h2>Willkommen auf deinem persönlichen Dashboard</h2>
        </div>
        <Searchbar />
      </div>

      <div className="content-container">
        <div className="content-card"></div>
        <div className="content-card"></div>
      </div>
    </div>
  );
};
export default Dashboard;
