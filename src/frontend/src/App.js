// App.js

import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./Home";
import Main from "./Main";
import Detail from "./Detail";
import Notice from "./Notice";
import Manage from "./Manage";
import "./css/App.css";

function App() {
  return (
    <Router>
      <div className="App">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/main" element={<Main />} />
          <Route path="/board/:id" element={<Detail />} />
          <Route path="/notice" element={<Notice />} />
          <Route path="/manage" element={<Manage />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
