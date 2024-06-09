import React, { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";

const Attendance = () => {
  const location = useLocation();
  const { meetingId } = location.state || {};
  const [participants, setParticipants] = useState([]);

  useEffect(() => {
    if (meetingId) {
      fetch(`/api/meetings/${meetingId}/attendance`)
        .then((response) => response.json())
        .then((data) => setParticipants(data))
        .catch((error) => console.error("Error fetching attendance:", error));
    }
  }, [meetingId]);
  console.log(participants);
  if (!meetingId) {
    return <div>모임 정보가 없습니다.</div>;
  }

  return (
    <div>
      <h2>참여자 출석 조회 페이지</h2>
      <h3>모임 ID: {meetingId}</h3>
      <div>
        <h3>출석자 목록</h3>
        <ul>
          {participants.map((participant, index) => (
            <li key={index}>
              ID: {participant.id}, Meeting Name: {participant.meetingName},
              User Email: {participant.userEmail}
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
};

export default Attendance;
