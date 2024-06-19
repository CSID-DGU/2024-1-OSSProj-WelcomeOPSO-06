import React, { useEffect, useState } from "react";
import { Link, useLocation } from "react-router-dom";
import Nav1 from "./Nav123";
import Top from "./Top";
import "./css/Detail.css";

const BoardDetail = () => {
  const location = useLocation();
  const pathname = location.pathname;
  const boardId = parseInt(pathname.split("/")[2]);
  const boardId1 = 4;
  const { state } = location;
  const [showQR, setShowQR] = useState(false);
  const [qrTimeLimit, setQRTimeLimit] = useState(15); // QR 제한 시간
  const [lateTimeLimit, setLateTimeLimit] = useState(10); // 지각 시간
  const [meetingDetails, setMeetingDetails] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [qrImage, setQrImage] = useState(null);
  const currentTime = new Date();
  const isoCurrentTime = currentTime.toISOString();
  const qrTimeLimitMinutes = qrTimeLimit;
  const lateTimeLimitMinutes = lateTimeLimit;
  const attendanceTime = new Date(currentTime);
  attendanceTime.setHours(attendanceTime.getHours() + 9);
  attendanceTime.setMinutes(attendanceTime.getMinutes() + qrTimeLimitMinutes);
  const isoAttendanceTime = attendanceTime.toISOString();
  const lateTime = new Date(attendanceTime);
  lateTime.setHours(lateTime.getHours());
  lateTime.setMinutes(lateTime.getMinutes() + lateTimeLimitMinutes);
  const isoLateTime = lateTime.toISOString();
  const currentDate = new Date();
  const dateString = `${currentDate.getFullYear()}-${(
    currentDate.getMonth() + 1
  )
    .toString()
    .padStart(2, "0")}-${currentDate.getDate().toString().padStart(2, "0")}`;
  console.log(currentTime);
  console.log(dateString, isoLateTime, isoAttendanceTime);
  const abc = {
    id:null,
    date: dateString,
    attendanceTime: isoAttendanceTime,
    lateTime: isoLateTime,
  };
  const [seconds, setSeconds] = useState(16);
  const [isRunning, setIsRunning] = useState(false); // 타이머가 실행 중인지 여부를 나타내는 상태
  const [participants, setParticipants] = useState([]);
  useEffect(() => {
    if (boardId) {
      fetch(`/api/meetings/${boardId}/participants`)
        .then((response) => response.json())
        .then((data) => setParticipants(data))
        .catch((error) => console.error("Error fetching participants:", error));
    }
  }, [boardId]);
  useEffect(() => {
    let intervalId;
    if (isRunning) {
      intervalId = setInterval(() => {
        setSeconds(prevSeconds => {
          let newSeconds = prevSeconds - 1;
          if (newSeconds < 0) {
            newSeconds =16;
          }
          return newSeconds;
        });
      }, 1000);
    }

    // Clean up the interval on unmount or when the timer stops
    return () => clearInterval(intervalId);
  }, [isRunning]); // isRunning이 변경될 때마다 useEffect가 실행됩니다.

  const startTimer = () => {
    setIsRunning(true); // 타이머 시작
  };
  const handleShowQR = async () => {
    setShowQR(true);
    try {
      const meetingId = 123; // 실제 meetingId 값으로 대체해야 합니다.
      const response = await fetch(
        `/api/meetings/${boardId}/start-attendance`,
        {
          method: "POST", // 요청 메서드 설정
          headers: {
            "Content-Type": "application/json", // 요청 헤더 설정
          },
          body: JSON.stringify(new Date().toISOString().slice(0,10) ), // 요청 본문 설정
        }
      );

      if (response.ok) {
        console.log("출석부 생성 요청이 성공했습니다.");
        // 성공적으로 요청을 보냈다면 여기에서 다음 작업을 수행할 수 있습니다.
      } else {
        console.log(response);
        console.error("출석부 생성 요청이 실패했습니다.");
      }
    } catch (error) {
      console.error("요청을 보내는 중 에러가 발생했습니다:", error);
    }
  };
  
  const handleCreateQR = async () => {
    startTimer();
    try {
      const createQR = async () => {
        const response = await fetch(`/api/meetings/${boardId}/qr-code`, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(abc),
        });
        if (!response.ok) {
          startTimer();
          throw new Error("Network response was not ok");
        }
        // 이미지 데이터를 받아오기
        const imageBlob = await response.blob();
        const imageUrl = URL.createObjectURL(imageBlob);
        setQrImage(imageUrl);
      };
  
      // 최초 한번은 즉시 요청 보냄
      await createQR();
  
      // 15초마다 요청 보내도록 타이머 설정
      const intervalId = setInterval(createQR, 15 * 1000);
  
      // 컴포넌트가 언마운트될 때 타이머 해제
      return () => clearInterval(intervalId);
    } catch (error) {
      // 오류 처리
      console.error("Error creating QR:", error);
      alert("QR 생성 중 오류가 발생했습니다.");
    }
  };

  useEffect(() => {
    console.log(boardId);
    if (boardId !== 0) {
      const fetchMeetingDetails = async () => {
        try {
          const response = await fetch("/api/main");
          if (!response.ok) {
            throw new Error("Network response was not ok");
          }
          const data = await response.json();
          console.log(data.response);

          const details = data.response.find((item) => item.meetingId === boardId); //id -> meetingid로 바꿔야하고 boardId도 바꿔야함
          console.log(details);
          if (details) {
            console.log(details);
            setMeetingDetails(details);
          } else {
            setError("Meeting details not found");
          }
          setLoading(false);
        } catch (error) {
          setError(error.message);
          setLoading(false);
        }
      };

      fetchMeetingDetails();
    } else {
      setError("Meeting ID not provided");
      setLoading(false);
    }
  }, [state]);

  if (loading) {
    return <div>Loading...</div>;
  }

  if (!meetingDetails) {
    return <div>No meeting details found</div>;
  }

  const { id, title, contents, createdAt, modifiedAt, commentList } =
    meetingDetails;

  return (
    <div style={{ overflow: "auto" }}> 
      <Top />
      <div >
        <h2>모임 상세 페이지</h2>
        <h3 style={{ fontSize: "20px", color: "black" }}>제목: {title}</h3>
        <p>내용: {contents}</p>
        <p style={{ fontSize: "14px", color: "gray" }}>
          작성 시간: {createdAt}
        </p>
        <p style={{ fontSize: "14px", color: "gray" }}>
          수정 시간: {modifiedAt}
        </p>
        
        <div>
          <h2>참여자 목록</h2>
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
        <button className="attendanceButton">
          <Link to={`/attendance/${boardId}`} state={{ boardId }}>
            참여자 출석 조회
          </Link>
        </button>
        <button className="Qrbutton" onClick={handleShowQR}>
          출석 진행
        </button>
        <div style={{ height: "300px", overflow: "auto" }}>

        
        {showQR && (
          <div className="floating-qr">
            <h3>QR 생성</h3>
            <div>
              <label htmlFor="qrTimeLimit">QR 제한 시간 (분): </label>
              <input
                type="number"
                id="qrTimeLimit"
                value={qrTimeLimit}
                onChange={(e) => setQRTimeLimit(e.target.value)}
              />
            </div>
            <div>
              <label htmlFor="lateTimeLimit">지각 시간 (분): </label>
              <input
                type="number"
                id="lateTimeLimit"
                value={lateTimeLimit}
                onChange={(e) => setLateTimeLimit(e.target.value)}
              />
            </div>
            <button onClick={handleCreateQR}>생성하기</button>
            
            
              <img src={qrImage} alt="" />
            <p>{seconds}초 남았습니다.</p>
          </div>
        )}
        <div>
          <h3>댓글</h3>
          {commentList.length === 0 ? (
            <p>댓글이 없습니다</p>
          ) : (
            commentList.map((comment, index) => (
              <div key={index}>
                <p>{comment}</p>
              </div>
            ))
          )}
        </div>
        </div>
        <Nav1 />
      </div>
    </div>
  );
};

export default BoardDetail;
