from pydantic import BaseModel
from typing import List, Annotated
from database import engine, SessionLocal

class Movie(BaseModel):
    title: str
    director: str
    duration_in_minutes: int
    year: int
    studio: str