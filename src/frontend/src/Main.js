import React from "react";
import Nav1 from "./Nav123";
import Top from "./Top";
import BoardItem from "./BoardItem";
import { Link } from "react-router-dom";

import "./css/Main.css";

const Main = () => {
  const getTimeDiff = (timeString) => {
    const currentTime = new Date();
    const targetTime = new Date(timeString);
    const diff = Math.floor((currentTime - targetTime) / (1000 * 60 * 60));

    return diff;
  };
  return (
    <div>
      <Top></Top>
      <div className="board">
        <h2>게시판</h2>

        <div className="board-item">
          <BoardItem
            id={1}
            title="첫 번째 게시글"
            content="이것은 첫 번째 게시글의 내용입니다."
            time={getTimeDiff("2024-05-12 10:00")}
            views={100}
            comments={5}
          />
        </div>
        <div className="board-item">
          <BoardItem
            title="두 번째 게시글"
            content="이것은 두 번째 게시글의 내용입니다."
            time={getTimeDiff("2024-05-12T10:00:00")}
            views={50}
            comments={3}
          />
        </div>
      </div>
      <Nav1 />
    </div>
  );
};

export default Main;
