from sqlalchemy import Boolean, Column, ForeignKey, Integer, String
from database import Base


class Movies(Base):
    __tablename__ = "movies"

    id = Column(Integer, primary_key=True, index=True)
    title = Column(String, index=True)
    director = Column(String)
    duration_in_minutes = Column(Integer)
    year = Column(Integer)
    studio = Column(String)