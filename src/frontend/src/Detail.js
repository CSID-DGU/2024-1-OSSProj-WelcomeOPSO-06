import React, { useState } from "react";
import { useLocation } from "react-router-dom";
import Nav1 from "./Nav123";
import Top from "./Top";
import "./css/Detail.css";
const BoardDetail = () => {
  const location = useLocation();
  const { state } = location;
  const [showQR, setShowQR] = useState(false);
  const [qrTimeLimit, setQRTimeLimit] = useState(15); // QR 제한 시간
  const [lateTimeLimit, setLateTimeLimit] = useState(10); // 지각 시간

  const handleShowQR = () => {
    setShowQR(true);
  };

  if (!state) {
    return (
      <div>
        <Top></Top>
        <div>
          <h2>필동2가 범죄도시 보실분~</h2>
          <div className="detailmain">
            <div className="detail_title">
              <h3>참여자</h3>
            </div>
            <div className="detail_detail">
              <div>짱구(abc1234) 24/남</div>
              <div>짱구(abc1234) 24/남</div>
              <div>짱구(abc1234) 24/남</div>
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
          <button className="Qrbutton" onClick={handleShowQR}>
            QR 생성
          </button>
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
              <button>생성하기</button>
              {/* 생성된 QR 표시 */}
              {/* <QRCode value={qrCode} /> */}
            </div>
          )}
          <Nav1 />
        </div>
      </div>
    );
  }

  const { id, title, content, time, views, comments, timeDiff } = state;

  return (
    <div>
      <h2>게시글 상세 페이지</h2>
      <p>게시글 ID: {id}</p>
      <p>제목: {title}</p>
      <p>내용: {content}</p>
      <p>작성 시간: {time}</p>
      <p>조회수: {views}</p>
      <p>댓글 수: {comments}</p>
      {/* 게시글 내용 등을 여기에 표시 */}
    </div>
  );
};

export default BoardDetail;
