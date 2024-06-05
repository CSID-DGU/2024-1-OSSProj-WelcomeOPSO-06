import React from "react";
import { Link } from "react-router-dom";
import "./css/Nav.css";
import Notice from "./Notice.js";
const Nav = () => {
  return (
    <nav className="bottom-nav">
      <ul>
        <li>
          <Link to="/main">홈</Link>
        </li>
        <li>
          <Link to="/manage">모임관리</Link>
        </li>
        <li>채팅</li>
        <li>
          <Link to="/notice">공지사항</Link>
        </li>
        <li>마이페이지</li>
      </ul>
    </nav>
  );
};

export default Nav;
