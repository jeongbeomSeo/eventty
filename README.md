# Eventty

![](./images/eventty_logo.png)

## 프로젝트 소개 

저희 프로젝트는 사용자의 역할을 호스트(주최자)와 참여자로 나누어 행사를 주최를 하고, 참여(지원)를 할 수 있도록 중개해주는 중개 플랫폼입니다.

클라우드를 활용하여 클라우드 기반 데이터 플랫폼을 구상하였고, 해당 플랫폼에서 강력한 아키텍처이면서 많은 기업들에서 사용하고 있는 MSA를 경험하고 학습해보기 위해 선택하였습니다.

## 프로젝트 목표 

해당 프로젝트에서 MSA를 선택함으로써 현업에서 다루고 있는 보안, 통신 등의 문제를 경험하고 고민을 통해 해결하고자 하였습니다. 

단순히 기능을 구현하는 것에 그치는 것이 아닌 가지고 있는 CS 지식들을 활용하고 팀원들과의 논의를 통해 문제 해결 역량을 키우는 것에 목적을 두었습니다. 

## 프로젝트 구조 

![](./images/eventty_architecture.jpeg)

## 프로젝트 주요 관심사

### 공통
- 짧은 기간 동안 프로젝트를 성공적으로 이끌기 위해선? 
    - **방법론:** 애자일(AGILE) 방법론
    - **소통 방법:** Slack 
    - **학습 및 정보 공유:** Notion[(Eventty: Notion Main Page)](https://www.notion.so/eventty-25ac7c30c552480db838f366c4bc2c85)
    - **브랜치 관리:** Git Flow[(Eventty: Git Branch Model)](https://www.notion.so/Git-Branch-Model-0ae3bd6df50b4dffb946e0da68a048fb) 
    - **API 명세서:** Notion -> Swagger[(Eventty: Notion API Specs)](https://www.notion.so/API-0165c5bd7e62499a8414b3e0c419c583)
    - **Code Convention:** Frontend[(Eventty: Frontend Code Convention)](https://www.notion.so/Code-Convention-Frontend-9ec1532252d44c5db1d647137e7191f5), Backend[(Eventty: Backend Code Convention)](https://www.notion.so/Code-Convention-Backend-64cfd122f4ae40e7ac599bac97131098)

### 프론트엔드

- UI ➡️ [Frontend README](./src/frontend/README.md)

### 백엔드
- 기초에 집중
    - **SOLID 원칙**
    - **ACID와 Transaction**
    - **추상화, 캡슐화, 다형성 및 관심사 분리**
    - **예외 처리** ➡️ 예외 전환(Custom Exception)
- 모놀리식과의 차별점
    - **확장성** ➡️ 서버를 어떻게 분리할 것인가 ***(페이지 정리 필요)***
    - **REST API 통신** ➡️ Restful API 통신
    - **서버 간의 통신** ➡️ 동기 혹은 비동기 방식 통신
    - **개발 환경 구축** ➡️ Docker Compose 적용
    - **통신 보안적인 측면 고려** ➡️ 필터 수준 보안 => 게이트웨이 및 인증서버 수준 보안(링크)
    - **전체 보안 수준 고려** ➡️ JWT, CSRF 발급 및 검증 방식(링크)
    - **로깅 추적** ➡️ 구체화된 로깅 방식 및 예외 처리 방식 + 분산 추적
- 패키징 전략
    - **DDD:** Domain Drive Design
- 각종 패턴들
    - **싱글톤 패턴(Singleton Pattern)**
    - **파사드 패턴(Facade Pattern)**
    - **프록시 패턴(Proxy Pattern)**
    - **팩토리 패턴(Factory Pattern)**
    - **전략 패턴(Strategy Pattern)** 
- MSA를 적용해서 고려된 기능 => 심화 기능(MSA 환경 고려)
    - **Response Format 통일** ➡️ LSP 위배 고려, ResponseBodyAdvice 도입
    - **Context 객체 도입** ➡️ User ID, Authorities, Tracking ID
    - **Global Filter, AOP** ➡️ Context객체 생성, 권한 검증
    - **Web Flux** ➡️ 토큰 검증 요청 및 업데이트
    - **Custon RestTemplate** ➡️ 예외 상황 발생시 클라이언트 서버에서 분기점 처리
- 유지보수성
    - **Global Error Response** ➡️ Controller Advice, Exception Handler 도입
    - **필요한 매개변수만 건네주기** ➡️ Converter & Mapper & Utils
    - **Error Logging 구체화** & (Loggin Level 조정)

## 각 서버의 역할 

해당 프로젝트는 MSA를 기반으로 구성되어 있기 때문에 각 서버의 역할이 명확히 구분되어 있습니다.

### Frontend

Nginx 서버 위에서 동작하면서 사용자와 게이트웨이(서버 진입점)의 중간 다리 역할을 하면서 사용자로부터의 요청을 처리함과 동시에 사용자들로부터 백엔드 서버들을 숨겨주는 역할을 수행해 줍니다. 

- [Frontend README](./src/frontend/README.md)

### Gateway

게이트웨이 서버는 웹서버와 서비스 서버들의 중간에서 통신을 전달해주는 역할을 하는 서버입니다.

- [Gateway README](./src/gateway/README.md)
- [Eventty: Gateaway Server](https://www.notion.so/Gateway-Server-22309198dd9d43b1b3ad4f79bca375c3)

### Auth Server

(작성 예정)

### User Server

### Event Server

### Apply Server

## Trouble Shooting

- [서버간의 통신 - Exception Handling](https://www.notion.so/Exception-Handling-8a80d64459334402b221dae5de17f304)
- [서버간의 통신 - Response Format 통일 ](https://www.notion.so/Response-Format-a1484bbb86c04c1fb0c53fd0ccf475b5)
- (작성중) 

## UI

### 메인
![eventty_main](https://github.com/jeongbeomSeo/eventty/assets/90774229/e170a2a0-c842-457c-94ca-34a348d66253)


### 로그인 & 소셜 로그인
|  ![eventty_login](https://github.com/jeongbeomSeo/eventty/assets/90774229/ec16566f-4969-4ce9-bfa3-9f08885afc2f)  |  ![eventty_google](https://github.com/jeongbeomSeo/eventty/assets/90774229/435238d4-ac02-49dd-8c2a-53033c7c2926)  |
|:----------------------------------------------------------------------------------------------------------------:|:-----------------------------------------------------------------------------------------------------------------:|
|                                                       로그인                                                        |                                                      구글 로그인                                                       |

### 카테고리 조회
![eventty_category](https://github.com/jeongbeomSeo/eventty/assets/90774229/20309df2-cf4e-42a7-903f-d697a7e11eed)

### 키워드 검색
![eventty_keyword](https://github.com/jeongbeomSeo/eventty/assets/90774229/7a1affdb-0535-4913-bee0-73fb0cfa60f0)

### 행사 등록
| ![eventty_write](https://github.com/jeongbeomSeo/eventty/assets/90774229/170923ca-3f7c-416d-9d2a-25163c12fe2f)  |  ![eventty_event](https://github.com/jeongbeomSeo/eventty/assets/90774229/b79c8911-eb53-4193-b35f-84f4f77af6ae)  |
|:---------------------------------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------------------------------:|
|                                                      행사 등록                                                      |                                                    등록한 행사 조회                                                     |

### 행사 신청
![eventty_booking](https://github.com/jeongbeomSeo/eventty/assets/90774229/94bac571-fb67-4997-baec-42f5e103197e)

### 행사 신청 내역
![eventty_applices](https://github.com/jeongbeomSeo/eventty/assets/90774229/1257dde0-4b20-4105-a8c4-f0bc7b9e9c0d)

### Responsive
![eventty_responsive](https://github.com/jeongbeomSeo/eventty/assets/90774229/28ba69c6-2ca4-41da-96c1-a9385f1948e8)
