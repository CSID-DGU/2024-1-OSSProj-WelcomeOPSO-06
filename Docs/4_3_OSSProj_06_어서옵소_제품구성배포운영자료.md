# A4.3 OSS 프로젝트 제품 구성, 배포 및 운영 자료  


## 1. 프로젝트 제품 구성

### 구현 기능
  1. 회원가입/로그인/로그아웃 -> [주요코드](https://github.com/CSID-DGU/2024-1-OSSProj-WelcomeOPSO-06/blob/mmm/src/backend/src/main/java/com/backend/backend/controller/UserApiController.java)
  2. 모임 생성 -> [주요코드](src/backend/src/main/java/com/backend/backend/controller/MeetingController.java)
  3. 모임 가입 -> [주요코드](src/backend/src/main/java/com/backend/backend/controller/ParticipantController.java)
  4. 홍보 게시글 목록(홈화면) ->  [주요코드](src/backend/src/main/java/com/backend/backend/controller/BoardController.java)
  5. 주최모임 목록 확인 ->  [주요코드](src/backend/src/main/java/com/backend/backend/controller/MeetingController.java)
  6. 참여모임 목록 확인 ->  [주요코드](src/backend/src/main/java/com/backend/backend/controller/MeetingController.java)
  7. 주최자-큐알코드 생성 ->  [주요코드](src/backend/src/main/java/com/backend/backend/controller/QRcodeController.java)
  8. 참여자-큐알코드 스캔으로 출석 인증 ->  [주요코드](src/backend/src/main/java/com/backend/backend/controller/ScanController.java)
  9. 주최자-참여자 출석 목록 조회 ->  [주요코드](src/backend/src/main/java/com/backend/backend/controller/AttendController.java)
  10. 참여자-해당모임의 본인 출석 조회 ->  [주요코드](src/backend/src/main/java/com/backend/backend/controller/AttendController.java)
     
  ※ 백엔드에서 추가 개발된 기능은 API명세서 확인 -> [바로가기 링크](https://www.notion.so/API-9e8f00b6085e476bb837cbb3f8a85c44)

  ※ 자세한 구성도 보기 -> [바로가기 링크](https://github.com/CSID-DGU/2024-1-OSSProj-WelcomeOPSO-06/blob/mmm/src/overview.md)

### 사용 오픈소스
- Zxing: QR 코드 생성 오픈소스 -> https://github.com/zxing/zxing
- 회원가입, 로그인, 로그아웃 ->  ['스프링 부트3 백엔드 개발자 되기' 교재 소스코드](https://github.com/shinsunyoung/springboot-developer)
- 게시판 및 댓글 -> [깃허브 바로가기](https://github.com/dev-dykim/Spring-project-board)
  
## 2. 프로젝트 제품 환경구성 및 이용 방법  
### 프론트엔드

#### 1. [Node.js 설치]
  ##### Node.js 공식 홈페이지 접속 https://nodejs.org/en
  LTS와 Current 버전 중 원하는 버전을 선택하여 설치한다.
  - LTS : 기업을 위해 3년간 지원하는 버전이다. 짝수 버전만 LTS 버전이 될 수 있다. 서버를 안정적으로 운영해야 할 경우 선택한다. 하지만 최신 기능을 사용하지 못할 수 있다.
  - Current : 최신 기능을 담고 있는 버전이다. 실험적인 기능이 들어 있어 예기치 못한 에러가 발생할 수 있다. 서버에 신기능이 필요하거나 학습용으로 사용할 때 적합하다. 단, 짝수 버전은 나중에 LTS가 되므로 Current일 때부터 사용하는 것을 고려해볼 만하다.

##### 밑 명령어를 통해 올바르게 설치되었는지 확인
  - node -v
  - npm -v
1. cd Src -> cd frontend -> cd src
2. `npm install`
3. `npm start`

### 백엔드
#### 1. Java 설치 (JDK)
- 스프링 부트 애플리케이션을 실행하기 위해 JDK가 필요하다. 본 프로젝트는 JDK 17을 사용하였다.
    
#### 2. 데이터 베이스 변경
- 본인의 데이터 베이스로 변경해야 사용가능하다.
- 변경방법
  - src\backend\src\main\resources\application.properties 파일에서 아래 사진 부분의 url을 본인 데이터베이스 경로로 수정하고, 계정명과 비밀번호를 변경한다.
  ![image](https://github.com/CSID-DGU/2024-1-OSSProj-WelcomeOPSO-06/assets/144206885/e577d725-fde8-4240-91a6-d6f7420bf831)

#### 3. 애플리케이션 실행
- src\backend\src\main\java\com\backend\backend\BackendApplication.java 파일을 실행하여 스프링 부트 애플리케이션을 시작한다.
- 이후 src/frontend/src에서 npm start 명령어로 웹 앱을 로컬 시스템에서 실행할 수 있다.

### Swagger 사용법
- Swagger는 백엔드의 API를 테스트 해볼 수 있는 오픈 소스 소프트웨어 프레임워크로, 본 프로젝트 소스코드에 포함되어 있어 프론트에서 구현되지 않은 기능도 테스트 해볼 수 있다.
- Swagger 기능 제거 방법
  - src/backend/src/main/java/com/backend/config 폴더에서 SwaggerConfig.java 파일을 삭제
  - 같은 폴더 내의 WebSecurityConfig.java 파일 내의 .requestMatchers("/login", "/signup","/user", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**").permitAll()의 뒤에 세 url을 삭제
  - build.gradle에서 implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0' 의존성을 삭제한다.
- Swagger 테스트 하는 법
1. 백엔드 서버를 실행 후, 회원가입, 로그인을 진행하여 개발자 도구에서 네트워크 란에 request header에서 세션 쿠키를 복사한다.
  ![image](https://github.com/CSID-DGU/2024-1-OSSProj-WelcomeOPSO-06/assets/144206885/a89e3f68-a19c-4edb-ad6d-ad2a4a5bb9c8)

  
2. http://localhost:8080/swagger-ui/index.html 로 접속하고 오른쪽 자물쇠 버튼을 눌러 세션 쿠키를 붙여 넣으면 해당 사용자 정보로 api를 이용할 수 있다.
  ![image](https://github.com/CSID-DGU/2024-1-OSSProj-WelcomeOPSO-06/assets/144206885/d2a0d4e0-ef61-476e-8794-a371703375da)

## 3. 프로젝트 제품 시연 시나리오  
  
### 작동화면
 1. 기본기능(회원가입, 로그인, 로그아웃)
 ![기본기능](https://github.com/CSID-DGU/2024-1-OSSProj-WelcomeOPSO-06/assets/144206885/fb35ac69-b2b0-473e-83e9-014e2a1cf1f4)
 - 비회원은 회원가입 후에 이용가능하다.
   
2. 주최자 기능(모임생성, 출석 큐알 생성, 출석목록 조회)
   ![주최자](https://github.com/CSID-DGU/2024-1-OSSProj-WelcomeOPSO-06/assets/144206885/11d29b79-3fd4-4830-b4ac-d143bf473dae)
 - 모임을 생성하고 참여자가 있는 경우 큐알을 생성하여 해당 날짜에 대한 출석을 체크할 수 있고, 출석 목록을 조회할 수 있다.
   
3. 참여자 기능(게시글 조회, 모임가입, 출석 큐알 스캔, 본인 출석 정보조회) 
  ![참여자](https://github.com/CSID-DGU/2024-1-OSSProj-WelcomeOPSO-06/assets/144206885/3a60a1e8-f2f6-4c89-a52e-374e88e5d3e4)
 - 홈 페이지의 홍보게시글을 통해 해당 모임에 가입할 수 있고, 큐알을 스캔하여 출석을 인증할 수 있다. 이미 15초가 지난 큐알의 경우, 만료된 코드로 출석을 인증할 수 없다.

   
※ 기능별 자세한 설명은 최종보고서 참고 -> [바로가기 링크](https://github.com/CSID-DGU/2024-1-OSSProj-WelcomeOPSO-06/blob/mmm/Docs/3_1_OSSProj_06_%EC%96%B4%EC%84%9C%EC%98%B5%EC%86%8C_%EC%B5%9C%EC%A2%85%EB%B3%B4%EA%B3%A0%EC%84%9C_.md#5-%EA%B0%9C%EB%B0%9C-%EA%B3%BC%EC%A0%95)

※ 출석인증 시연영상 -> [바로가기 링크](https://github.com/CSID-DGU/2024-1-OSSProj-WelcomeOPSO-06/blob/mmm/Docs/%EC%8B%9C%EC%97%B0%EB%8F%99%EC%98%81%EC%83%81.mp4)
