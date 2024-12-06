from pydantic_settings import BaseSettings


class Settings(BaseSettings):
    database: str = 'moviesdb'
    user: str = 'user'
    password: str = 'secret'
    host: str = 'localhost'
    port: int = 5453

    class Config:
        env_prefix = 'DB_'
        env_file = '.env'


def get_settings():
    return Settings()