import React, { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
const today = new Date(); // 현재 날짜와 시간을 나타내는 Date 객체 생성
const year = today.getFullYear(); // 연도 가져오기
const month = String(today.getMonth() + 1).padStart(2, '0'); // 월 가져오기 (0부터 시작하므로 +1 필요), 두 자리로 만들기
const day = String(today.getDate()).padStart(2, '0'); // 일 가져오기, 두 자리로 만들기

const currentDate = `${year}-${month}-${day}`; // "YYYY-MM-DD" 형식의 날짜 문자열 생성

console.log(currentDate);
const Attendance = () => {
  const location = useLocation();
  const { boardId } = location.state || {};
  const [participants, setParticipants] = useState([]);

  useEffect(() => {
    
      fetch(`/api/meetings/${boardId}/attendance-list/${currentDate}`)
        .then((response) => response.json())
        .then((data) => setParticipants(data))
        .catch((error) => console.error("Error fetching attendance:", error));
    
  }, [boardId]);
  console.log(participants);
  if (!boardId) {
    return <div>모임 정보가 없습니다.</div>;
  }

  return (
    <div>
      <h2>참여자 출석 조회 페이지</h2>
      <h3>모임 ID: {boardId}</h3>
      <div>
        <h3>출석자 목록</h3>
        <div>
          {participants.map((participant, index) => (
            <li key={index} style={{ listStyleType: 'none', color: 'black' }}>
              User Email: {participant.participantEmail},출석 상태: {participant.attendStatus}
              
            </li>
          ))}
        </div>
      </div>
    </div>
  );
};

export default Attendance;
