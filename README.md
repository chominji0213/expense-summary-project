# 🧾 가계부 요약봇 (Expense Summary Bot)

지출 내역을 Spring Boot(JPA) 웹 백엔드에서 CRUD로 관리하고, FastAPI로 감싼 LLM 요약 기능을 붙여 이 웹 앱이 호출하는 구조의 가계부 서비스입니다. "기존 스프링 웹 서비스에 LLM 기능을 붙인다"는 최종 프로젝트 패턴을 미리 연습하는 첫 프로젝트로, 순수 챗봇이 아니라 **레거시(Spring) 시스템과 AI(FastAPI) 서버를 REST로 연동하는 구조**에 초점을 맞췄습니다.

## 이 프로젝트로 연습한 것

- **Spring Data JPA 기반 CRUD**: Entity/Repository/Service/Controller 계층 구조로 지출 내역 등록·조회·수정·삭제
- **Spring Boot ↔ FastAPI 서버 간 연동**: `RestClient`로 스프링 부트가 별도의 파이썬(FastAPI) 서버를 호출하고, 그 응답을 다시 클라이언트에게 전달하는 구조
- **FastAPI + LangChain으로 LLM 요약 기능 구현**: 지출 목록을 프롬프트로 구성해 Gemini에게 요약을 요청
- **외부 서버 호출 실패에 대한 에러 처리**: FastAPI 서버가 꺼져있거나 응답에 실패해도 앱이 죽지 않고 안내 메시지 반환

## 기술 스택

**Backend (지출 관리)**
- Java 17, Spring Boot 4.1.1
- Spring Data JPA, Spring Web (RestClient)
- Oracle Database 21c XE (Docker)
- Lombok

**AI 서버 (요약)**
- Python, FastAPI, Pydantic
- LangChain (`init_chat_model`)
- Gemini API (`gemini-3.1-flash-lite`)

## 주요 기능

- 지출 내역 등록/전체조회/단건조회/수정/삭제 (REST API)
- 이번 달 지출 내역을 LLM이 카테고리별 합계·최다 지출 카테고리·소비 패턴으로 요약
- 지출 내역이 없을 때, FastAPI 서버 연결이 실패했을 때 각각 안내 메시지로 처리

## 아키텍처

```
클라이언트
   │  GET /api/expense/summary
   ▼
[Spring Boot]
   ExpenseController ── ExpenseService ── ExpenseRepository ── Oracle DB (expense 테이블)
        │
        ▼ (지출 목록이 있으면)
   SummaryService ── RestClient로 POST 호출
        │
        ▼
[FastAPI]  POST /summary
   요청받은 지출 목록을 프롬프트로 구성
        │
        ▼
   Gemini API 호출 → 요약 문장 생성
        │
        ▼
   {"summary": "..."} 응답
        │
        ▼
Spring Boot가 요약 문장을 그대로 클라이언트에 반환
```

## 프로젝트 구조

```
expense-summary-project/
├── expense-summary-project-backend/   # Spring Boot (지출 CRUD + FastAPI 연동)
│   └── src/main/java/com/example/demo/
│       ├── entity/       # Expense
│       ├── repository/   # ExpenseRepository
│       ├── service/      # ExpenseService, SummaryService
│       ├── controller/   # ExpenseController
│       └── dto/          # SummaryRequestDto, SummaryResponseDto
└── expense-summary-project-ai/        # FastAPI (LLM 요약 서버)
    └── main.py           # POST /summary 엔드포인트
```

## 로컬 실행 방법

### 1. Oracle DB 컨테이너 실행

```bash
docker run -d --name oracle-xe -p 1522:1521 -e ORACLE_PWD=oracle1234 gvenzl/oracle-xe
```

컨테이너가 뜨면 `expense_summary` 계정과 스키마를 생성합니다.

### 2. Spring Boot 백엔드 실행

`expense-summary-project-backend/src/main/resources/application.properties`에서 DB 접속 정보를 확인한 뒤, 이클립스 또는 아래 명령으로 실행합니다.

```bash
cd expense-summary-project-backend
./mvnw spring-boot:run
```

8080 포트에서 실행됩니다.

### 3. FastAPI 요약 서버 실행

```bash
cd expense-summary-project-ai
python -m venv .venv
.venv\Scripts\activate
pip install -r requirements.txt
```

`.env.example`을 참고해 `.env` 파일을 만들고 `GOOGLE_API_KEY`를 채운 뒤:

```bash
python main.py
```

8000 포트에서 실행됩니다.

### 4. 동작 확인

```
GET  http://localhost:8080/api/expense          # 전체 조회
POST http://localhost:8080/api/expense          # 등록
GET  http://localhost:8080/api/expense/summary   # LLM 요약
```

## 알게 된 점 / 트러블슈팅

- **`@PathVariable`과 URL 경로 변수명은 반드시 일치해야 함**: `@GetMapping("/{id}")`에 매개변수명이 다르면(`expenseId`) "Missing URI template variable" 에러 발생. 경로와 매개변수명을 통일해 해결.
- **`RestTemplate`보다 `RestClient` 사용**: Spring 6.1+ 권장 방식에 맞춰 최신 API인 `RestClient`로 FastAPI 연동을 구현.
- **외부 서버 호출은 항상 try-catch로 감싸야 함**: FastAPI 서버가 꺼져있는 상태에서 호출하면 예외가 그대로 전파되어 500 에러 화면이 노출됨. 모든 예외를 잡아 사용자 친화적 메시지로 대체.

## 참고

이 프로젝트는 배포하지 않고 로컬 환경에서 동작 확인까지만 진행했습니다.
