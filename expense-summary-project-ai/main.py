"""
지출 요약 FastAPI 서버 - Spring Boot 백엔드가 지출 내역을 보내면,
LLM으로 요약 문장을 만들어 돌려주는 역할만 담당.

book-recommend-project의 llm_client.py처럼 별도 그래프를 짤 필요는 없고,
이번엔 단일 엔드포인트 하나만 있으면 되는 훨씬 단순한 구조야.
"""

# TODO: import
#   from fastapi import FastAPI
#   from pydantic import BaseModel
#   from langchain.chat_models import init_chat_model
#   from langchain_core.messages import HumanMessage
#   from dotenv import load_dotenv
#   from rich import print as rprint
#   load_dotenv()


# TODO: 요청/응답 스키마 정의 (FastAPI는 pydantic BaseModel로 요청 body 형태를 강제함)
#
#   class ExpenseItem(BaseModel):
#       category: str      # 지출 카테고리 (예: "식비", "교통")
#       amount: int         # 금액
#       date: str            # 지출 날짜 (예: "2026-08-01")
#       memo: str = ""        # 메모 (선택)
#
#   class SummaryRequest(BaseModel):
#       expenses: list[ExpenseItem]   # Spring Boot가 보내줄 이번 달 지출 목록
#
#   class SummaryResponse(BaseModel):
#       summary: str        # LLM이 만든 요약 문장


# TODO: FastAPI 앱 인스턴스 생성
#   app = FastAPI()


# TODO: 요약 엔드포인트
#   @app.post("/summary", response_model=SummaryResponse)
#   def summarize(request: SummaryRequest):
#       """
#       TODO 1: request.expenses가 비어있으면 -> {"summary": "이번 달 지출 내역이 없어요."} 반환
#
#       TODO 2: expenses 리스트를 프롬프트에 넣기 좋은 형태로 정리
#         (예: "\n".join([f"{e.date} {e.category} {e.amount}원 {e.memo}" for e in request.expenses]))
#
#       TODO 3: llm = init_chat_model('gemini-3.1-flash-lite', model_provider='google_genai')
#
#       TODO 4: 프롬프트 작성 - 카테고리별 합계, 가장 많이 쓴 카테고리, 전체적인 소비 패턴에 대한
#         짧은 요약을 만들어달라고 요청
#
#       TODO 5: try/except로 llm.invoke() 감싸기 (book-recommend-project의 generate_node 패턴 재사용)
#         실패 시 -> {"summary": "요약을 만드는 중 문제가 생겼어요."} 반환
#
#       TODO 6: {"summary": 추출한_텍스트} 형태로 반환
#       """
#       pass


if __name__ == "__main__":
    import uvicorn
    uvicorn.run("main:app", host="0.0.0.0", port=8000, reload=True)
