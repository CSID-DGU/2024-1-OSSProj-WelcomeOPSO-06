// BoardItem.js

import React from "react";
import { Link } from "react-router-dom";

const BoardItem = ({ id, title, content, time, views, comments, timeDiff }) => {
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
      <Link
        to={{
          pathname: `/post/${id}`,
          state: { id, title, content, time, views, comments, timeDiff },
        }}
      >
        <div className="board-it">
          <h2>{title}</h2>
          <p>{content}</p>
          <div className="metadata">
            <span>작성 시간: {getTimeText()}</span>
            <span>조회수: {views}</span>
            <span>댓글 수: {comments}</span>
          </div>
        </div>
      </Link>
    </div>
  );
};

export default BoardItem;
