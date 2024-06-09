// BoardItem.js

import React from "react";
import { Link } from "react-router-dom";

const BoardItem = ({ id, title, content, time, meeting , timeDiff }) => {
  const getTimeText = () => {
    if (timeDiff < 1) {
      return "방금 전";
    } else if (timeDiff < 24) {
      return `${timeDiff}시간 전`;
    } else {
      return time;
    }
  };

  return (
    <div className="board-item">
      <Link to={`/post/${id}`} state={{ id, title, content, meeting }}>
        <div className="board-it">
          <h2>{title}</h2>
          <div className="metadata">
            <p>{content}</p>
            <p>{time}시간 전</p>
          </div>
        </div>
      </Link>
    </div>
  );
};

export default BoardItem;
