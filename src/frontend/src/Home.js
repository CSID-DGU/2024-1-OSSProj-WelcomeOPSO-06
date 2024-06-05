import React from "react";
import { Link } from "react-router-dom";
import "./css/Home.css"; // CSS 파일 import
import logo from "./asset/123.png";

const Home = () => {
  return (
    <div className="Homediv">
      <img className="logo" src={logo} alt="로고" />
      <p className="logotext">3초만에 끝나는 간편한 출석체크</p>
      <Link to="/login">
        <button>시작하기</button>
      </Link>
    </div>
  );
};

export default Home;
