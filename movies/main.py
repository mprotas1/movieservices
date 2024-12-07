from typing import Annotated, List
from sqlalchemy.orm import Session
from fastapi import FastAPI, HTTPException, Depends
from models import Movie
from models import Genre


app = FastAPI()


@app.post("/movies")
async def create_movie():
    return {"message": "Create a movie"}


@app.get("/movies")
async def get_all_movies():
    return {"message": "Movies"}