// App.js

import React, { useState } from "react";
import { Route, BrowserRouter as Router, Routes } from "react-router-dom";
import Detail from "./Detail";
import Home from "./Home";
import Main from "./Main";
import "./css/App.css";

function App() {
  const [hello, setHello] = useState('');


    
  return (
    <Router>
      <div className="App">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/main" element={<Main />} />
          <Route path="/board/:id" element={<Detail />} />
        </Routes>
      </div>
    </Router>
  );
}


export default App;
