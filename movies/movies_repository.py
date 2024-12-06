import psycopg2 as pg
import uvicorn
from typing import Optional
from settings import get_settings
from fastapi import Depends, FastAPI, HTTPException, Query
from sqlmodel import Field, Session, SQLModel, create_engine, select


DATABASE_URL = "postgresql://user:secret@localhost:5453/moviesdb"
engine = create_engine(DATABASE_URL)


def get_session():
    with Session(engine) as session:
        yield session


def get_connection():
    settings = get_settings()
    return pg.connect(
        database=settings.database,
        user=settings.user,
        password=settings.password,
        host=settings.host,
        port=settings.port
    )


def cursor():
    connection = get_connection()
    return connection.cursor()


def get_movies():
    connection = get_connection()
