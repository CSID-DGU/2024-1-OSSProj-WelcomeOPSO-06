import React from "react";

function Mypage() {
  const handleLogout = async () => {
    try {
      // 로그아웃을 요청하는 fetch 코드를 작성
      const response = await fetch("/logout", {
        // 필요에 따라 헤더나 바디 등을 설정
      });

      if (response.ok) {
        // 로그아웃이 성공하면 메시지 출력 또는 다른 작업 수행
        console.log("로그아웃 되었습니다.");
        window.location.href = '/';
        
      } else {
        // 로그아웃이 실패한 경우 에러 처리
        console.error("로그아웃에 실패했습니다.");
      }
    } catch (error) {
      // 네트워크 오류 등 예외 처리
      console.error("로그아웃 요청을 보내는 도중 오류가 발생했습니다:", error);
    }
  };

  return (
    <div>
      <h1>마이페이지</h1>
      <button onClick={handleLogout}>로그아웃</button>
    </div>
  );
}

export default Mypage;
