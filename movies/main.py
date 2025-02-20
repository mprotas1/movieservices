from fastapi import FastAPI, HTTPException, Depends
from sqlalchemy.orm import Session

from database import get_db, Base, engine
from models import Movie
from schemas import Movie as MovieSchema, MovieCreate

import uvicorn
import os


app = FastAPI()
Base.metadata.create_all(bind=engine)


@app.post("/movies", response_model=MovieSchema)
async def create_movie(movie: MovieCreate, db: Session = Depends(get_db)):
    new_movie = Movie(**movie.dict())
    db.add(new_movie)
    db.commit()
    db.refresh(new_movie)
    return new_movie


@app.get("/movies", response_model=list[MovieSchema])
async def get_all_movies(db: Session = Depends(get_db)):
    movies = db.query(Movie).all()
    print(movies)
    return movies


@app.get("/movies/{movie_id}", response_model=MovieSchema)
async def get_movie_by_id(movie_id: int, db: Session = Depends(get_db)):
    movie = db.query(Movie).filter(Movie.id == movie_id).first()
    if not movie:
        raise HTTPException(status_code=404, detail="Movie not found")
    return movie


if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=int(os.getenv("PORT", 8100)))