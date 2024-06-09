// App.js

import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./Home";
import Main from "./Main";
import Detail from "./Detail";
import Notice from "./Notice";
import Manage from "./Manage";
import QRScanner from "./QRScanner";
import JoinDetail from "./Join-detail";
import CreateMeeting from "./CreateMeeting";
import Post from "./post";
import "./css/App.css";

function App() {
  return (
    <Router>
      <div className="App">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/main" element={<Main />} />
          <Route path="/my-created-meetings/:id" element={<Detail />} />
          <Route path="/my-joined-meetings/:id" element={<JoinDetail />} />
          <Route path="/notice" element={<Notice />} />
          <Route path="/manage" element={<Manage />} />
          <Route path="/qrscanner" element={<QRScanner />} />
          <Route path="/create-meeting" element={<CreateMeeting />} />
          <Route path="/post/:id" element={<Post />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
