![제목 없는 디자인](https://github.com/CSID-DGU/2024-1-OSSProj-WelcomeOPSO-06/assets/144206885/d1310641-d07c-41ef-b017-1003dc670260)
# 프로젝트 명: QR코드 스마트 출석 모임 시스템 

## I. 프로젝트 수행팀 개요

* 수행 학기:  2024학년도 1학기
* 프로젝트명:  QR코드 스마트 출석 모임 시스템 
* Key Words :  출석체크, QR 코드, 모임, 플로팅, 부정출석
* 팀명: 어서옵소    

| 구분 | 성명   | 학번       | 소속학과         | 연계전공       | 이메일             |
| ---- | ------ | ---------- | ---------------- | -------------- | ------------------ |
| 팀장 | 장원혁 | 2018110202 | 영어통번역전공   | 융합SW연계전공 | jwhmark@gmail.com  |
| 팀원 | 김영찬 | 2019112479 | 산업시스템공학과 | 융합SW연계전공 | qzwx7530@dgu.ac.kr |
| 팀원 | 김민형 | 2019110747 | 정치외교학전공   | 융합SW연계전공 | ab000701@naver.com |              

* 지도교수:  SW교육원 이길섭 교수, 박효순 교수   
 

## II. 프로젝트 수행 결과  

### 1. 프로젝트 개요  

#### 1.1 개발 동기  

- 동국대학교 출석인증방식(숫자코드) 한계 극복


#### 1.2 개발 목표  

1) QR 코드 스캔으로 출석 인증
2) QR 코드의 타임 아웃 기능으로 부정출석 방지


#### 1.3 최종결과물  

 ##### 1. 기본기능(회원가입, 로그인, 로그아웃)
 ![기본기능](https://github.com/CSID-DGU/2024-1-OSSProj-WelcomeOPSO-06/assets/144206885/fb35ac69-b2b0-473e-83e9-014e2a1cf1f4)
 - 비회원은 회원가입 후에 이용가능하다.
   
##### 2. 주최자 기능(모임생성, 출석 큐알 생성, 출석목록 조회)
   ![주최자](https://github.com/CSID-DGU/2024-1-OSSProj-WelcomeOPSO-06/assets/144206885/11d29b79-3fd4-4830-b4ac-d143bf473dae)
 - 모임을 생성하고 참여자가 있는 경우 큐알을 생성하여 해당 날짜에 대한 출석을 체크할 수 있고, 출석 목록을 조회할 수 있다.
   
##### 3. 참여자 기능(게시글 조회, 모임가입, 출석 큐알 스캔, 본인 출석 정보조회) 
  ![참여자](https://github.com/CSID-DGU/2024-1-OSSProj-WelcomeOPSO-06/assets/144206885/3a60a1e8-f2f6-4c89-a52e-374e88e5d3e4)
 - 홈 페이지의 홍보게시글을 통해 해당 모임에 가입할 수 있고, 큐알을 스캔하여 출석을 인증할 수 있다. 이미 15초가 지난 큐알의 경우, 만료된 코드로 출석을 인증할 수 없다.

   
※ 기능별 자세한 설명은 최종보고서 참고 -> [바로가기 링크](https://github.com/CSID-DGU/2024-1-OSSProj-WelcomeOPSO-06/blob/mmm/Docs/3_1_OSSProj_06_%EC%96%B4%EC%84%9C%EC%98%B5%EC%86%8C_%EC%B5%9C%EC%A2%85%EB%B3%B4%EA%B3%A0%EC%84%9C_.md#5-%EA%B0%9C%EB%B0%9C-%EA%B3%BC%EC%A0%95)

※ 출석인증 시연영상 -> [바로가기 링크](https://github.com/CSID-DGU/2024-1-OSSProj-WelcomeOPSO-06/blob/mmm/Docs/%EC%8B%9C%EC%97%B0%EB%8F%99%EC%98%81%EC%83%81.mp4)

#### 1.4 기대 효과  

![image](https://github.com/CSID-DGU/2024-1-OSSProj-WelcomeOPSO-06/assets/144206885/4da98088-05dd-4236-83f6-b4cd9ce84cc3)
![image](https://github.com/CSID-DGU/2024-1-OSSProj-WelcomeOPSO-06/assets/144206885/5f6d15df-f77d-49a9-af11-6fd0ddf69846)
### 2. 프로젝트 수행 내용  

#### 2.1 프로젝트 진행과정 

- 역할
  - 프론트엔드: 김영찬
  - 백엔드: 김민형, 장원혁
- 프로젝트 진행 일정
  <img width="981" alt="간트차트" src="https://github.com/CSID-DGU/2024-1-OSSProj-WelcomeOPSO-06/assets/144206885/a2b3383e-6576-4483-bcc7-7403846b467b">

#### 2.2 프로젝트 구현내용  
- 유스케이스
<img src="https://github.com/CSID-DGU/2024-1-OSSProj-WelcomeOPSO-06/assets/144206885/ee8347ef-4e51-4bad-bf5c-5f8bcc285f79">

- 시스템 설계 -> [바로가기 링크](https://github.com/CSID-DGU/2024-1-OSSProj-WelcomeOPSO-06/blob/mmm/src/overview.md)

### 3. 프로젝트 자료  

#### 3.1 프로젝트 OSS 구성  

- Zxing: QR 코드 생성 오픈소스 -> https://github.com/zxing/zxing
- 회원가입, 로그인, 로그아웃 ->  ['스프링 부트3 백엔드 개발자 되기' 교재 소스코드](https://github.com/shinsunyoung/springboot-developer)
- 게시판 및 댓글 -> [깃허브 바로가기](https://github.com/dev-dykim/Spring-project-board)

#### 3.2 프로젝트 주요 문서 
- [수행계획서](https://github.com/CSID-DGU/2024-1-OSSProj-WelcomeOPSO-06/blob/main/Docs/1_1_OSSProj_06_%EC%96%B4%EC%84%9C%EC%98%B5%EC%86%8C_%EC%88%98%ED%96%89%EA%B3%84%ED%9A%8D%EC%84%9C.md)

- [제안발표자료](https://github.com/CSID-DGU/2024-1-OSSProj-WelcomeOPSO-06/blob/main/Docs/1_2_OSSProj_%ED%8C%80%EB%B2%88%ED%98%B8_%ED%8C%80%EB%AA%85_%EC%88%98%ED%96%89%EA%B3%84%ED%9A%8D%EB%B0%9C%ED%91%9C%EC%9E%90%EB%A3%8C.ppt)

- [중간보고서](https://github.com/CSID-DGU/2024-1-OSSProj-WelcomeOPSO-06/blob/main/Docs/2_1_OSSProj_06_%EC%96%B4%EC%84%9C%EC%98%B5%EC%86%8C_%EC%A4%91%EA%B0%84%EB%B3%B4%EA%B3%A0%EC%84%9C_.md)

- [중간발표자료](https://github.com/CSID-DGU/2024-1-OSSProj-WelcomeOPSO-06/blob/main/Docs/2_2_OSSProj_06_%EC%96%B4%EC%84%9C%EC%98%B5%EC%86%8C_%EC%A4%91%EA%B0%84%EB%B0%9C%ED%91%9C%EC%9E%90%EB%A3%8C_.ppt)

- [최종보고서](https://github.com/CSID-DGU/2024-1-OSSProj-WelcomeOPSO-06/blob/main/Docs/3_1_OSSProj_06_%EC%96%B4%EC%84%9C%EC%98%B5%EC%86%8C_%EC%B5%9C%EC%A2%85%EB%B3%B4%EA%B3%A0%EC%84%9C_.md)

- [최종발표자료](https://github.com/CSID-DGU/2024-1-OSSProj-WelcomeOPSO-06/blob/main/Docs/3_2_OSSProj_06_%EC%96%B4%EC%84%9C%EC%98%B5%EC%86%8C_%EC%B5%9C%EC%A2%85%EB%B0%9C%ED%91%9C%EC%9E%90%EB%A3%8C_.ppt)

- [이슈관리](https://github.com/CSID-DGU/2024-1-OSSProj-WelcomeOPSO-06/issues)

- [범위 및 일정관리](https://github.com/CSID-DGU/2024-1-OSSProj-WelcomeOPSO-06/blob/main/Docs/4_1_OSSProj_06_%EC%96%B4%EC%84%9C%EC%98%B5%EC%86%8C_%EB%B2%94%EC%9C%84_%EC%9D%BC%EC%A0%95_%EC%9D%B4%EC%8A%88%EA%B4%80%EB%A6%AC.md)

- [회의록](https://github.com/CSID-DGU/2024-1-OSSProj-WelcomeOPSO-06/blob/main/Docs/4_2_OSSProj_06_%EC%96%B4%EC%84%9C%EC%98%B5%EC%86%8C_%ED%9A%8C%EC%9D%98%EB%A1%9D.md)

- [제품구성배포운영자료](https://github.com/CSID-DGU/2024-1-OSSProj-WelcomeOPSO-06/blob/main/Docs/4_3_OSSProj_06_%EC%96%B4%EC%84%9C%EC%98%B5%EC%86%8C_%EC%A0%9C%ED%92%88%EA%B5%AC%EC%84%B1%EB%B0%B0%ED%8F%AC%EC%9A%B4%EC%98%81%EC%9E%90%EB%A3%8C.md)

#### 3.3 참고자료  

1. 한국외대 전자출결 관리 시스템 메뉴얼, [https://at2.hufs.ac.kr/guide.html](https://at2.hufs.ac.kr/guide.html)
2. 박선주, QR코드를활용한스마트폰기반출석체크시스템, 한국정보교육학회, 2014년 1월.
3. 조유현,"다른 사람인거 모르겠지?"..中유학생, 수업부터 시험까지 통째 대리출석, 파이낸셜 뉴스, https://n.news.naver.com/article/014/0004994892?sid=102 , 2023년 4월.
4. 이원영, 최아영, 강의실 내에서 쉽게 이뤄지는 대리출석과 출튀, 덕성여대 신문, https://www.dspress.org/news/articleView.html?idxno=531월 , 2015년 3월. 
5. gukanucar, jwt-project, 깃허브, https://github.com/gurkanucar/jwt-project?tab=readme-ov-file , 2022년 1월
