import React, { useEffect, useState } from "react";

const AttendanceJoin = (props) => {
  const [participants, setParticipants] = useState([]);
  const { boardId } = props;
  useEffect(() => {
    fetch(`/api/meetings/${boardId}/attendance`)
      .then((response) => response.json())
      .then((data) => setParticipants(data))
      .catch((error) => console.error("Error fetching attendance:", error));
  }, [boardId]);
  console.log(boardId);
  if (!boardId) {
    return (
      <div>
        <h2>출석 상태</h2>
        <div>출석상태 미확인</div>
      </div>
    );
  }

  return (
    <div>
      <h2>출석 상태</h2>
      <div>
        {participants.map((participant, index) => (
          <li key={index} style={{ listStyleType: "none", color: "black" }}>
            출석 상태:{" "}
            {participant.attendStatus}
          </li>
        ))}
      </div>
    </div>
  );
};

export default AttendanceJoin;
