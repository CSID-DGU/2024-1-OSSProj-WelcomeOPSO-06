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
      id: "",
      meetingName: "주최중인 모임이 없습니다.",
      userEmail: "",
    },
    
  ]);
  const [meeting2, setMeeting2] = useState([
    {
      id: "",
      meetingName: "참여중인 모임이 없습니다.",
      userEmail: "",
    },
    
  ]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  useEffect(() => {
    fetch("/api/meetings/organizer")
      .then((response) => {
        if (!response.ok) {
          throw new Error("Network response was not ok");
        }
        return response.json();
      })
      .then((data) => {
        console.log(data);
        setMeeting2(data);
        setLoading(false);
      })
      .catch((error) => {
        setError(error);
        setLoading(false);
      });
  }, []);

  useEffect(() => {
    fetch("/api/meetings/partcipant")
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

  const handleCreateGroup = async () => {
    try {
      const response = await fetch("/meetings", {
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

          <Link to="/create-meeting">
            <button className="make-button">모임만들기</button>
          </Link>
        </div>
        <div style={{ height: "300px", overflow: "auto" }}>
          {meeting2.map((meeting2, index) => (
            <Link
              to={{
                pathname: `/my-created-meetings/${meeting2.id}`,
                state: meeting2,
              }}
              key={index}
            >
              <div className="groupname">
                <p>{meeting2.meetingName}</p>
                <p>{meeting2.userEmail}</p>
              </div>
            </Link>
          ))}
        </div>
        <h2>내가 참여 중인 모임</h2>
        <div style={{ height: "200px", overflow: "auto" }}>
          {meeting.map((meeting, index) => (
            <Link to={`/my-joined-meetings/${meeting.id}`} key={index}>
              <div className="groupname">
                <p>{meeting.meetingName}</p>
                <p> {meeting.userEmail}</p>
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
