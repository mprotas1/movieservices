from pydantic_settings import BaseSettings


class Settings(BaseSettings):
    database: str = 'moviesdb'
    user: str = 'user'
    password: str = 'secret'
    host: str = 'moviesdb'
    port: int = 5432

    class Config:
        env_prefix = 'DB_'
        env_file = '.env'


def get_settings():
    return Settings()