import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import Nav1 from "./Nav123";
import Top from "./Top";
import "./css/Manage.css";

const Manage = () => {
  const [myCreatedGroups, setMyCreatedGroups] = useState([]);
  const [myJoinedGroups, setMyJoinedGroups] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [newGroupName, setNewGroupName] = useState("");
  const [newGroupId, setNewGroupId] = useState("");

  // 미팅정보 가져오기
  const [meeting, setMeeting] = useState([
    {
      id: 1,
      meetingName: "첫번째 모임",
      userEmail: "11@11",
    },
    {
      id: 2,
      meetingName: "첫번째 모임",
      userEmail: "11@11",
    },
  ]);
  const [meeting2, setMeeting2] = useState([
    {
      id: 3,
      meetingName: "첫번째 참여 모임",
      userEmail: "11@11",
    },
    {
      id: 4,
      meetingName: "두번째 참여 모임",
      userEmail: "11@11",
    },
  ]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  useEffect(() => {
    fetch("http://localhost:8080/meetings/participant")
      .then((response) => {
        if (!response.ok) {
          throw new Error("Network response was not ok");
        }
        return response.json();
      })
      .then((data) => {
        setMeeting2(data);
        setLoading(false);
      })
      .catch((error) => {
        setError(error);
        setLoading(false);
      });
  }, []);

  useEffect(() => {
    fetch("http://localhost:8080/meetings/organizer")
      .then((response) => {
        if (!response.ok) {
          throw new Error("Network response was not ok");
        }
        return response.json();
      })
      .then((data) => {
        setMeeting(data);
        setLoading(false);
      })
      .catch((error) => {
        setError(error);
        setLoading(false);
      });
  }, []);

  if (loading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return (
      <div>
        <Top />
        <div className="manage-container">
          <div className="manage-header">
            <h2>내가 만든 모임</h2>
            <button className="make-button" onClick={() => setShowModal(true)}>
              모임 만들기
            </button>
          </div>
          <div>
            {meeting.map((meeting, index) => (
              <Link to={`/board/${meeting.id}`} key={index}>
                <div className="groupname">
                  <p>모임 이름: {meeting.meetingName}</p>
                  <p>이메일: {meeting.userEmail}</p>
                </div>
              </Link>
            ))}
          </div>
          <h2>내가 참여 중인 모임</h2>
          <div>
            {meeting2.map((meeting2, index) => (
              <Link to={`/board/${meeting2.id}`} key={index}>
                <div className="groupname">
                  <p>모임 이름: {meeting2.meetingName}</p>
                  <p>이메일: {meeting2.userEmail}</p>
                </div>
              </Link>
            ))}
          </div>
        </div>
        <Nav1 />
      </div>
    );
  }

  const handleCreateGroup = async () => {
    try {
      const response = await fetch("http://localhost:8080/meetings", {
        method: "get",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ id: newGroupId, name: newGroupName }),
      });

      if (response.ok) {
        const newGroup = await response.json();
        setMyCreatedGroups([...myCreatedGroups, newGroup]);
        setShowModal(false);
        setNewGroupName("");
        setNewGroupId("");
      } else {
        console.error("Failed to create group");
      }
    } catch (error) {
      console.error("Error creating group:", error);
    }
  };

  return (
    <div>
      <Top />
      <div className="manage-container">
        <div className="manage-header">
          <h2>내가 만든 모임</h2>
          <button className="make-button" onClick={() => setShowModal(true)}>
            모임 만들기
          </button>
        </div>
        <div>
          {meeting.map((meeting, index) => (
            <Link to={`/board/${meeting.id}`} key={index}>
              <div className="groupname">
                <p>모임 이름: {meeting.meetingName}</p>
                <p>이메일: {meeting.userEmail}</p>
              </div>
            </Link>
          ))}
        </div>
        <h2>내가 참여 중인 모임</h2>
        <div>
          {meeting2.map((meeting2, index) => (
            <Link to={`/board/${meeting2.id}`} key={index}>
              <div className="groupname">
                <p>모임 이름: {meeting2.meetingName}</p>
                <p>이메일: {meeting2.userEmail}</p>
              </div>
            </Link>
          ))}
        </div>
      </div>
      <Nav1 />
    </div>
  );
};

export default Manage;
