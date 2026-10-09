from fastapi import FastAPI
from pydantic import BaseModel

from langchain.chat_models import init_chat_model
from langchain_core.messages import HumanMessage
from dotenv import load_dotenv

load_dotenv()

app = FastAPI()

#요청 Body 모양 정의
class ExpenseItem(BaseModel):
    category: str
    amount: int
    date: str
    memo: str = ""

#Spring에서 받은 요청형태 정의
class SummaryRequest(BaseModel):
    expenses: list[ExpenseItem]

#Spring에 돌려줄 응답형태 정의
class SummaryResponse(BaseModel):
    summary: str

#Spring에서 받을 엔드포인트
@app.post('/summary', response_model=SummaryResponse)
def summarize(request: SummaryRequest):
    if not request.expenses:
        return SummaryResponse(summary='이번달 지출 내역이 없습니다.')

    expense_lines = '\n'.join(
        f'{e.date} {e.category} {e.amount}원  {e.memo}' for e in request.expenses
    )

    llm = init_chat_model(model='gemini-3.1-flash-lite', model_provider='google_genai')
    prompt = f'''
        다음은 이번달 지출내역이야
        {expense_lines}

        카테고리별 지출 합계, 가장많이쓴 카테고리, 전체적인 소비패턴에 대해 짧은 요약을 한국어로 작성해줘. 
        카테고리별 지출합계는 되도록 표 형태로 보여줄수있음 좋겠어.
    '''
    result = llm.invoke([HumanMessage(content=prompt)])
    summary_text = result.content[0]['text']

    return SummaryResponse(summary=summary_text)

if __name__ == "__main__":
    import uvicorn
    uvicorn.run("main:app", host="0.0.0.0", port=8000, reload=True)
