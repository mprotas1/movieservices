from typing import Annotated
from database import engine, SessionLocal
from sqlalchemy.orm import Session
from fastapi import FastAPI, HTTPException, Depends
import model
import movies_repository
import sql


app = FastAPI()
sql.Base.metadata.create_all(bind=engine)


def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()


db_dependency = Annotated[Session, Depends(get_db)]


@app.post("/movies")
async def create_movie(movie: model.Movie, db = db_dependency):
    db_movie = sql.Movies(title=movie.title,
                          director=movie.director,
                          duration_in_minutes=movie.duration_in_minutes,
                          year=movie.year,
                          studio=movie.studio)
    db.add(db_movie)
    db.commit()
    db.refresh(db_movie)


@app.get("/movies")
async def get_all_movies():
    movies_repository.get_movies()
    return {"message": "Movies"}