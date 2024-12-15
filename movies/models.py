from sqlalchemy import Column, Integer, String, Enum
from sqlalchemy.orm import Mapped, mapped_column
from database import Base
from schemas import Genre


class Movie(Base):
    __tablename__ = 'movies'
    id: Mapped[int] = mapped_column(primary_key=True, autoincrement=True)
    title: Mapped[str] = mapped_column(String, index=True)
    director: Mapped[str] = mapped_column(String, index=True)
    studio: Mapped[str] = mapped_column(String, index=True)
    genre: Mapped[Genre] = mapped_column(Enum(Genre), index=True)
    duration_in_minutes: Mapped[int] = mapped_column(Integer)
    year: Mapped[int] = mapped_column(Integer)