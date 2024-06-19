import React, { useEffect, useState } from "react";
import { Link, useLocation } from "react-router-dom";
import Nav1 from "./Nav123";
import Top from "./Top";
import Attendancejoin from "./attendancejoin";
import "./css/Detail.css";
const BoardDetail = () => {
  const location = useLocation();
  const pathname = location.pathname;
  const boardId = pathname.split("/")[2];
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
  lateTime.setHours(lateTime.getHours() + 9);
  lateTime.setMinutes(lateTime.getMinutes() + lateTimeLimitMinutes);
  const isoLateTime = lateTime.toISOString();
  const currentDate = new Date();
  const dateString = `${currentDate.getFullYear()}-${(
    currentDate.getMonth() + 1
  )
    .toString()
    .padStart(2, "0")}-${currentDate.getDate().toString().padStart(2, "0")}`;

  console.log(dateString, isoLateTime, isoAttendanceTime);
  const abc = {
    date: dateString,
    attendanceTime: isoAttendanceTime,
    lateTime: isoLateTime,
  };
  const handleShowQR = () => {
    setShowQR(true);
  };
  const handleCreateQR = async () => {
    try {
      const response = await fetch(`/api/meetings/${boardId}/qr-code`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(abc),
      });
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }
      // 이미지 데이터를 받아오기
      const imageBlob = await response.blob();
      const imageUrl = URL.createObjectURL(imageBlob);
      setQrImage(imageUrl);
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

          const details = data.response.find((item) => item.meetingId === parseInt(boardId));//id -> meetingid로 바꿔야하고 boardId도 바꿔야함
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
    <div>
      <Top />
      <div>
        <h2>모임 상세 페이지</h2>
        <h3 style={{ fontSize: "20px", color: "black" }}>{title}</h3>
        <p>{contents}</p>
        <p style={{ fontSize: "14px", color: "gray" }}>
          작성 시간: {createdAt}
        </p>
        <p style={{ fontSize: "14px", color: "gray" }}>
          수정 시간: {modifiedAt}
        </p>
        <div className="detailmain">
          <div className="detail_title">
            <h3>모임장소</h3>
          </div>
          <div className="detail_detail">
            <div>대한극장 | 서울 중구 퇴계로 212</div>
          </div>
        </div>         
        <Attendancejoin boardId={boardId}></Attendancejoin>
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
        <Link to="/qrscanner">
          <button className="Qrbutton">QR 스캔하기</button>
        </Link>
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
            {/* 생성된 QR 표시 */}
            {/* <QRCode value={qrCode} /> */}
            {/* QR 스캔하기 버튼 */}
            <Link to="/qrscanner">
              <button className="Qrbutton">QR 스캔하기</button>
            </Link>
          </div>
        )}
        <Nav1 />
      </div>
    </div>
  );
};

export default BoardDetail;
