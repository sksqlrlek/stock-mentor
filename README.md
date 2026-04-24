# 📈 StockMentor

> AI 기반 주식 투자 가이드 서비스
> 주식 입문자를 위한 LLM 기반 종목 분석 및 투자 전략 안내 플랫폼

<br>

## 목차

- [프로젝트 소개](#프로젝트-소개)
- [주요 기능](#주요-기능)
- [기술 스택](#기술-스택)
- [시스템 아키텍처](#시스템-아키텍처)
- [ERD](#erd)
- [API 명세](#api-명세)
- [화면 구성](#화면-구성)
- [실행 방법](#실행-방법)
- [트러블슈팅](#트러블슈팅)
- [회고](#회고)

<br>

## 프로젝트 소개

### 기획 배경

주식 투자에 관심이 생겨도 낯선 금융 용어와 복잡한 차트 분석에 막혀 포기하는 입문자가 많습니다.
기존 증권사 앱은 전문 투자자 중심으로 설계되어 있어, 초보자가 스스로 정보를 해석하기 어렵습니다.

이 서비스는 **LLM을 활용해 복잡한 주식 데이터를 자연어로 풀어 설명하고**, 개인 투자 성향에 맞는 전략과 리스크 가이드를 제공합니다.

### 핵심 원칙

StockMentor는 **직접 주식 매매를 수행하거나 투자 결정을 대신하지 않습니다.**
AI는 사용자가 스스로 더 나은 판단을 내릴 수 있도록 정보와 인사이트를 제공하는 **어시스턴트** 역할만 합니다.

| 항목 | 내용 |
|------|------|
| 개발 기간 | 2025.00 ~ 2025.00 (5주) |
| 개발 인원 | 1인 |
| 배포 주소 | 추후 업데이트 |

<br>

## 주요 기능

### ① 종목 검색 및 기본 정보 조회
- 종목명 또는 종목코드로 국내/해외 주식 검색
- 현재가, 등락률, 시가총액, PER, PBR, 52주 최고/최저가 표시
- 최근 6개월 ~ 1년 캔들스틱 차트 제공
- LLM이 해당 기업의 사업 내용을 쉬운 언어로 요약 설명

### ② AI 매매 전략 분석
- 이동평균선(MA), RSI, MACD, 볼린저 밴드 등 기술적 지표 자동 계산
- LLM이 지표를 분석하여 자연어로 매매 타이밍 설명
- 단기 / 중기 / 장기 관점별 전략 구분 제공

### ③ 리스크 관리 가이드
- 투자 예정 금액 입력 시 포지션 크기 및 분산 투자 비중 안내
- 투자 성향(공격형 / 중립형 / 안정형)에 따른 맞춤 리스크 가이드

### ④ 뉴스 감성 분석
- 종목 관련 최신 뉴스 자동 수집
- LLM이 뉴스를 긍정 / 중립 / 부정으로 분류 및 요약

### ⑤ 관심 종목 관리 (Watchlist)
- 관심 종목 저장 및 목록 관리
- 저장 종목의 현재가 및 등락률 한눈에 확인

### ⑥ 회원 시스템
- 회원가입 / 로그인 / 로그아웃 (JWT 인증)
- 투자 성향 및 관심 섹터 설정
- 성향에 따라 LLM 분석 톤 및 내용 개인화

<br>

## 기술 스택

### Backend
![Java](https://img.shields.io/badge/Java_21-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot_3.5-6DB33F?style=flat-square&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=flat-square&logo=springsecurity&logoColor=white)
![JPA](https://img.shields.io/badge/Spring_Data_JPA-6DB33F?style=flat-square&logo=spring&logoColor=white)

### Frontend
![React](https://img.shields.io/badge/React_18-61DAFB?style=flat-square&logo=react&logoColor=black)
![TailwindCSS](https://img.shields.io/badge/Tailwind_CSS-06B6D4?style=flat-square&logo=tailwindcss&logoColor=white)
![Recharts](https://img.shields.io/badge/Recharts-FF6384?style=flat-square)

### Database
![Oracle](https://img.shields.io/badge/Oracle_DB_21c-F80000?style=flat-square&logo=oracle&logoColor=white)

### External API
| API | 용도 |
|-----|------|
| Claude API (Anthropic) | 자연어 분석, 감성 분류, 전략 설명 생성 |
| KIS Developers API | 한국투자증권 무료 API, 국내 실시간 주가 데이터 |
| Naver 검색 API | 국내 주식 관련 뉴스 수집 (한국어 특화) |
| 카카오 OAuth2 | 소셜 로그인 |
| 네이버 OAuth2 | 소셜 로그인 |
| 구글 OAuth2 | 소셜 로그인 |

![KIS](https://img.shields.io/badge/KIS_Developers-E8003D?style=flat-square)
![Kakao](https://img.shields.io/badge/Kakao_OAuth2-FFCD00?style=flat-square&logo=kakao&logoColor=black)
![Naver](https://img.shields.io/badge/Naver_OAuth2-03C75A?style=flat-square&logo=naver&logoColor=white)
![Google](https://img.shields.io/badge/Google_OAuth2-4285F4?style=flat-square&logo=google&logoColor=white)
### 기술 선택 이유

**Maven + Spring Boot를 선택한 이유**
국내 취업 시장에서 가장 수요가 높은 서버 프레임워크입니다. Maven은 국내 기업에서 여전히 많이 사용하는 빌드 도구로, 의존성 관리와 빌드 흐름을 직접 경험하기 위해 선택했습니다.

**Oracle DB를 선택한 이유**
국내 대기업 및 금융권에서 여전히 가장 많이 사용하는 RDBMS입니다. 실무와 가장 가까운 환경에서 SQL 역량을 어필하기 위해 선택했습니다.

**Claude API를 선택한 이유**
한국어 응답 품질이 우수하고, 금융 관련 맥락을 자연스럽게 이해하는 성능이 검증되어 있습니다. 프롬프트 설계와 응답 파싱 로직을 직접 구현하며 LLM 연동 경험을 쌓을 수 있습니다.

<br>

## 시스템 아키텍처

```
[사용자 브라우저]
      │
      ▼
[React 18 (SPA)]
      │  REST API (JSON / JWT)
      ▼
[Spring Boot 3.5]
  ├── Spring Security (JWT 인증/인가)
  ├── Controller → Service → Repository
  │
  ├── [Oracle DB 21c XE]
  │     └── USERS / WATCHLIST / 
  │
  └── [외부 API 연동]
        ├── Claude API  →  AI 분석 텍스트 생성
        ├── KIS API     →  국내 주가 실시간 데이터
        ├── yfinance    →  미국 주가 데이터
        └── NewsAPI     →  종목 관련 뉴스
```

<br>

## ERD

> ERDCloud 작성 후 이미지 첨부 예정

<br>

## API 명세

### 회원 API

| Method | Endpoint | 설명 | 인증 |
|--------|----------|------|------|
| POST | /api/auth/signup | 회원가입 | ❌ |
| POST | /api/auth/login | 로그인 (JWT 발급) | ❌ |
| POST | /api/auth/logout | 로그아웃 | ✅ |
| GET | /api/users/me | 내 정보 조회 | ✅ |
| PUT | /api/users/me | 투자 성향 및 설정 수정 | ✅ |

### 종목 API

| Method | Endpoint | 설명 | 인증 |
|--------|----------|------|------|
| GET | /api/stocks/search?q={keyword} | 종목 검색 | ❌ |
| GET | /api/stocks/{code} | 종목 기본 정보 및 현재가 | ❌ |
| GET | /api/stocks/{code}/chart | 주가 차트 데이터 | ❌ |
| GET | /api/stocks/{code}/indicators | 기술적 지표 (RSI, MA 등) | ❌ |

### 뉴스 API

| Method | Endpoint | 설명 | 인증 |
|--------|----------|------|------|
| GET | /api/news?keyword={keyword} | 키워드 기반 뉴스 조회 | ❌ |

### AI 분석 API

| Method | Endpoint | 설명 | 인증 |
|--------|----------|------|------|
| POST | /api/analysis | AI 분석 요청 및 저장 | ✅ |
| GET | /api/analysis | 내 분석 히스토리 목록 | ✅ |
| GET | /api/analysis/{id} | 특정 분석 결과 상세 | ✅ |
| DELETE | /api/analysis/{id} | 분석 기록 삭제 | ✅ |

### 관심 종목 API

| Method | Endpoint | 설명 | 인증 |
|--------|----------|------|------|
| GET | /api/watchlist | 관심 종목 목록 | ✅ |
| POST | /api/watchlist | 관심 종목 추가 | ✅ |
| DELETE | /api/watchlist/{id} | 관심 종목 삭제 | ✅ |

<br>

## 화면 구성

> 개발 완료 후 스크린샷 추가 예정

| 화면 | 설명 |
|------|------|
| 홈 / 검색 | 종목 검색 및 인기 종목 목록 |
| 종목 상세 | 차트, 지표, AI 분석 결과 |
| AI 분석 결과 | 전략 / 리스크 / 뉴스 탭 구분 |
| 관심 종목 | Watchlist 목록 및 현재가 |
| 분석 히스토리 | 날짜별 과거 분석 기록 |
| 마이페이지 | 투자 성향 및 설정 관리 |

<br>

## 트러블슈팅

> 개발 중 마주친 문제와 해결 과정을 기록합니다.

| 문제 | 원인 | 해결 |
|------|------|------|
- KIS 모의투자 → 실전투자 전환 (credentials_type 오류)
- JwtFilter @Component 제거 (순환 참조)
- Oracle 리스너 연결 문제
- N+1 문제 (나중에 @EntityGraph로 해결 예정)

<br>

## 회고

> 프로젝트 완료 후 작성 예정

- **잘한 점**:
- **아쉬운 점**:
- **배운 점**:

<br>

---

> 이 프로젝트는 투자 정보 제공을 목적으로 하며, 실제 투자 결정은 사용자 본인의 판단과 책임 하에 이루어져야 합니다.
