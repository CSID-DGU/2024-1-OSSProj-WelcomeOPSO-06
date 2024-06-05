// src/Notice.js
import React from "react";
import Nav1 from "./Nav123";
import "./css/Notice.css";

const notices = [
  {
    id: 1,
    title: "[필독] 서비스 이용 안내",
    date: "2024-05-01",
    content: "서비스 이용 방법에 대한 안내사항입니다. 꼭 읽어주세요.",
  },
  {
    id: 2,
    title: "정기 점검 안내",
    date: "2024-05-15",
    content: "서비스 정기 점검 일정에 대해 안내드립니다.",
  },
  {
    id: 3,
    title: "새로운 기능 업데이트",
    date: "2024-06-01",
    content: "새로운 기능이 추가되었습니다. 자세한 내용은 아래를 참고하세요.",
  },
];

const updates = [
  "2024-06-02: UI 개선 작업 완료",
  "2024-06-05: 서버 안정화 작업 진행",
  "2024-06-10: 채팅 기능 업데이트",
  "2024-06-15: 보안 패치 적용",
  "2024-06-20: 신규 회원가입 이벤트 시작",
];

const Notice = () => {
  return (
    <div className="notice-container">
      <h1>공지사항</h1>
      <h3>필독</h3>
      <ul className="notice-list">
        {notices.map((notice) => (
          <li key={notice.id} className="notice-item">
            <h2 className="notice-title">{notice.title}</h2>
            <p className="notice-date">작성일: {notice.date}</p>
            <p className="notice-content">{notice.content}</p>
          </li>
        ))}
      </ul>
      <h3>
        {updates.map((update, index) => (
          <p key={index} className="update-item123">
            {update}
          </p>
        ))}
      </h3>
      <Nav1 />
    </div>
  );
};

export default Notice;
