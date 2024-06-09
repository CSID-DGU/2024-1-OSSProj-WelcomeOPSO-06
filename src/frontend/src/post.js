import React, { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import Nav1 from "./Nav123";
import Top from "./Top";
import "./css/Detail.css";
const BoardDetail = () => {
  const location = useLocation();
  const { id, title, content, meeting } = location.state || {};
  const [showQR, setShowQR] = useState(false);
  const [participants, setParticipants] = useState([]);
  useEffect(() => {
    if (meeting) {
      fetch(`/api/meetings/${meeting}/participants`)
        .then((response) => response.json())
        .then((data) => setParticipants(data))
        .catch((error) => console.error("Error fetching participants:", error));
    }
  }, [meeting]);
  
  useEffect(() => {
    if (meeting) {
      fetch(`/api/meetings/${meeting}/participants`)
        .then((response) => response.json())
        .then((data) => setParticipants(data))
        .catch((error) => console.error("Error fetching participants:", error));
    }
  }, [meeting]);
  console.log(participants);
  const handleShowQR = () => {
    setShowQR(true);
  };

  if (!location.state) {
    return <div>게시글이 없습니다</div>;
  }
  const handleCreateGroup = async () => {
    try {
      const response = await fetch(`/api/meetings/${meeting}/register`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          // 여기에 필요한 경우 다른 헤더 추가 가능
        },
        // 여기에 필요한 경우 요청 바디 추가 가능
      });

      if (response.ok) {
        // 성공적으로 요청을 보낸 후 할 작업
        alert("모임에 성공적으로 참여했습니다.");
      } else {
        // 요청이 실패한 경우 에러 처리
        console.error("모임 참여에 실패했습니다.");
      }
    } catch (error) {
      // 네트워크 오류 등 예외 처리
      console.error("요청을 보내는 도중 오류가 발생했습니다:", error);
    }
  };

  return (
    <div>
      <Top></Top>
      <h2>게시글 상세 페이지</h2>
      <div>
        <h2>제목: {title}</h2>
        <p>내용: {content}</p>
        <div className="detailmain">
          <div className="detail_title">
            
          </div>
          <div className="detailmain">
            <div className="detail_title">
              <h3>참여자</h3>
            </div>
            <div className="detail_detail">
              {participants.length > 0 ? (
                participants.map((participant, index) => (
                  <div key={index}>
                    참여자 이메일: {participant.participantEmail}
                  </div>
                ))
              ) : (
                <div>참여자가 없습니다.</div>
              )}
            </div>
          </div>
        </div>
        <div className="detailmain">
          <div className="detail_title">
            <h3>모임장소</h3>
          </div>
          <div className="detail_detail">
            <div>대한극장 | 서울 중구 퇴계로 212</div>
          </div>
        </div>
        <div className="detailmain">
          <div className="detail_title">
            <h3>모임시간</h3>
          </div>
          <div className="detail_detail">
            <div>2024년 5월 12일 오전 11시 00분</div>
          </div>
        </div>
        <button className="Qrbutton" onClick={handleCreateGroup}>
          참여하기
        </button>

        <Nav1 />
      </div>
    </div>
  );
};

export default BoardDetail;
