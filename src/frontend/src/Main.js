import React, { useEffect, useState } from "react";
import BoardItem from "./BoardItem";
import Nav1 from "./Nav123";
import Top from "./Top";
import "./css/Main.css";

const Main = () => {
  const [boardItems, setBoardItems] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch("/api/main");
        if (!response.ok) {
          throw new Error("Network response was not ok");
        }
        const data = await response.json();
        const itemsArray = Object.values(data);
        setBoardItems(itemsArray[1]);
        console.log(itemsArray[1]);
        setLoading(false);
      } catch (error) {
        setError(error);
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  const getTimeDiff = (timeString) => {
    const currentTime = new Date();
    const targetTime = new Date(timeString);
    const diff = Math.floor((currentTime - targetTime) / (1000 * 60 * 60));
    return diff;
  };

  if (loading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>Error: {error.message}</div>;
  }

  return (
    <div >
      <Top />
      <div className="board" style={{ height: "600px", overflow: "auto" }}>
        <h2>게시판</h2>
        {boardItems.map((item) => (
          <div className="board-item" key={item.id} >
            <BoardItem
              id={item.id}
              title={item.title}
              content={item.contents}
              time={getTimeDiff(item.createdAt)}
              views={item.views}
              comments={item.comments}
              meeting={item.meetingId}
            />
          </div>
        ))}
      </div>
      <Nav1 />
    </div>
  );
};

export default Main;
