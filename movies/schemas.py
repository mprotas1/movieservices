from pydantic import BaseModel
from enum import Enum


class Genre(str, Enum):
    action = "Action"
    adventure = "Adventure"
    comedy = "Comedy"
    drama = "Drama"
    fantasy = "Fantasy"
    historical = "Historical"
    horror = "Horror"
    mystery = "Mystery"
    romance = "Romance"
    science_fiction = "Science Fiction"
    thriller = "Thriller"
    western = "Western"


class MovieBase(BaseModel):
    title: str
    director: str
    genre: str
    studio: str
    duration_in_minutes: int
    year: int


class MovieCreate(MovieBase):
    pass


class Movie(MovieBase):
    id: int

    class Config:
        orm_mode = True