from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from sqlalchemy.ext.declarative import declarative_base
from settings import get_settings


def get_db_url():
    username = get_settings().user
    password = get_settings().password
    host = get_settings().host
    port = get_settings().port
    database = get_settings().database
    return f"postgresql://{username}:{password}@{host}:{port}/{database}"


engine = create_engine(get_db_url())

SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)

Base = declarative_base()