import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./css/CreateMeeting.css"; // CSS 파일 임포트

const CreateMeeting = () => {
  const [newGroupName, setNewGroupName] = useState("adfas");
  const navigate = useNavigate();

  const handleCreateGroup = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch("/api/meetings", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ meetingName: newGroupName }),
      });
      console.log(response);
      if (response.ok) {
        const newGroup = await response.json();
        alert("모임이 성공적으로 생성되었습니다.");
        navigate("/manage"); // 모임 생성 후 관리 페이지로 이동
      } else {
        console.error("Failed to create group");
        alert("모임 생성 중 오류가 발생했습니다.");
      }
    } catch (error) {
      console.error("Error creating group:", error);
      alert("네트워크 오류가 발생했습니다.");
    }
  };

  return (
    <div className="create-meeting-container">
      <h2>새 모임 만들기</h2>
      <form onSubmit={handleCreateGroup}>
        <label>
          모임 이름:
          <input
            type="text"
            value={newGroupName}
            onChange={(e) => setNewGroupName(e.target.value)}
            required
          />
        </label>
        <button type="submit">생성</button>
        <button type="button" onClick={() => navigate("/manage")}>
          취소
        </button>
      </form>
    </div>
  );
};

export default CreateMeeting;
