# 냉장고 관리 애플리케이션 - Server

### 2022 홍익대학교 졸업 프로젝트

## 프로젝트 배경 및 동기
- 코로나19로 인해 다양한 식재료 배송 서비스의 활성화
- 식재료의 다양화와 식재료 구매주문 횟수가 증가되어 통합적인 관리의 필요성이 증가됨

## 프로젝트 목적
- 냉장고 속의 식재료를 핸드폰 어플리케이션으로 어디서든 손쉽게 관리할 수 있도록 하고자 함
- 바코드/QR코드 등을 이용해 손쉽게 제품 정보를 저장할 수 있게 함
- 냉장고 속의 식재료를 사용할 수 있는 음식 레시피를 추천해 유통기한이 임박한 재료들을 사용할 수 있도록 유도

## 개발 환경
- 메인 서버: AWS EC2
  - EC2 OS: Ubuntu 18.04 - nginx 1.14.0, Gradle 6.7.1
- DataBase: AWS RDS
  - version: MySQL 8.0.28

- 프레임워크: SpringBoot
- Tool: AQueryTool(ERD 설계), DataGrip(쿼리 작성 및 테스트), IntelliJ(코드 작성 IDE), PostMan(API 테스트)

## 소프트웨어 구성도
<img width="451" alt="구성도" src="https://user-images.githubusercontent.com/77482065/203695178-accc4896-f66e-4eb5-99b5-37f0475ef463.png">

## ERD 구성도
<img width="688" alt="ERD" src="https://user-images.githubusercontent.com/77482065/203695658-0b4aefe4-9bb0-421e-939f-ec6494ce77ec.png">

## 구현한 기능 목록
- 회원가입/로그인
- 회원정보 수정/삭제(탈퇴)
- 식재료 리스트 관리
  - 사용자의 식재료 정보 추가
  - 사용자 입력으로 추가 -> **QR코드 인식으로 손 쉬운 저장 기능 구현**
  - 식재료 수정/삭제
- 유통기한 관리
  - 사용자의 식재료에 대한 유통기한 알림
  - 캘린더로 이번달에 유통기한이 끝나는 식재료 한눈에 보기
- 레시피 추천/추가
  - 사용자의 재료로 조리할 수 있는 레시피 추천
  - 사용자가 직접 새로운 레시피 추가
  - 레시피 좋아요 기능으로 다시 보고싶은 레시피 모으기

### 구현한 QR코드 인식 기능
![qrcode_reader](https://user-images.githubusercontent.com/77482065/203701826-60a7dd91-a256-4a77-8b89-80da94c137e9.gif)


## 기능 구현(API 작성) 예시
- 만들어 놓은 화면별 프로토타입 이미지를 바탕으로 해당 화면에 필요한 정보를 입력받거나 가져올 쿼리 생성
- 작성한 쿼리를 바탕으로 SpringBoot 코드를 작성해 간단한 API 생성
- 우선 가장 기본이 되는 회원가입, 로그인 API 부터 작성
- Postman을 통해 API 작동 확인
- 전체적인 API 작성 후 예외 처리 및 새로운 기능 추가에 맞춰 코드 업데이트 예정

  ### 1. 쿼리 작성
  - datagrip의 콘솔창에서 필요한 쿼리 생성
    <img width="431" alt="쿼리작성" src="https://user-images.githubusercontent.com/77482065/203697024-9617f826-ec70-4123-a0e2-1920c256dd1b.png">
  - 설계된 UI 디자인에 따른 화면별 쿼리 작성
    <img width="841" alt="쿼리작성2" src="https://user-images.githubusercontent.com/77482065/203697180-1485b6d1-c5c3-485e-ab8b-e8f6e02c80f4.png">
  
  ### 2. API 구현 (ex. 회원가입)
  각 API가 실행되는 순서: Route - Controller - Provider/Service - DAO
    - Route: Request에서 보낸 라우팅 처리
    - Controller: Request를 처리하고 Response 해주는 곳. (Provider/Service에 넘겨주고 다시 받아온 결과값을 형식화), 형식적 Validation
    - Provider/Service: 비즈니스 로직 처리, 의미적 Validation
    - DAO: Data Access Object의 줄임말. Query가 작성되어 있는 곳.

  *Request -> Route -> Controller -> Service/Provider -> DAO -> DB*

  *DB -> DAO -> Service/Provider -> Controller -> Route -> Response*

  - API 구성도
  <img width="910" alt="회원가입API" src="https://user-images.githubusercontent.com/77482065/203699093-405e2221-c177-4361-b365-c0e92e4f28f0.png">

  - 회원가입 API 호출 예시
  <img width="751" alt="회원가입1" src="https://user-images.githubusercontent.com/77482065/203704535-3c43f0b5-538f-4dcf-935d-5ba08ad7f762.png">
  <img width="769" alt="회원가입2" src="https://user-images.githubusercontent.com/77482065/203704725-f0c455bf-150c-4e27-a514-ab1454671f63.png">
  <img width="776" alt="회원가입3" src="https://user-images.githubusercontent.com/77482065/203705048-f0903518-722f-483d-b819-61b3e37df415.png">
  <img width="722" alt="회원가입4" src="https://user-images.githubusercontent.com/77482065/203705225-d2426538-e462-4b06-9629-9d5c01dadc32.png">
  <img width="722" alt="회원가입4" src="https://user-images.githubusercontent.com/77482065/203706871-9d53c8c3-f7ca-4a00-89f2-e4b85dc05b4b.png">

