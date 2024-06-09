// App.js
import React from "react";
import { Route, BrowserRouter as Router, Routes } from "react-router-dom";
import CreateMeeting from "./CreateMeeting";
import Detail from "./Detail";
import Home from "./Home";
import JoinDetail from "./Join-detail";
import Main from "./Main";
import Manage from "./Manage";
import Notice from "./Notice";
import QRScanner from "./QRScanner";
import Attendance from "./attendance";
import "./css/App.css";
import Post from "./post";

function App() {
  return (
    <Router>
      <div className="App">
        <Routes>
          <Route path="/attendance/:boardId" element={<Attendance />} />
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
