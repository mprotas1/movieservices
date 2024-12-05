from fastapi import FastAPI

import movies_repository

app = FastAPI()

@app.get("/movies")
async def get_movies():
    # Get movies from the database
    movies_repository.get_movies()
    return {"message": "Movies"}