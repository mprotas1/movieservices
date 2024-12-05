from pydantic_settings import BaseSettings

class Settings(BaseSettings):
    database: str = 'moviesdb'  # Domyślna wartość
    user: str = 'user'
    password: str = 'secret'
    host: str = 'localhost'
    port: int = 5432

    class Config:
        env_prefix = 'DB_'  # Prefix dla zmiennych środowiskowych (np. DB_DATABASE)
        env_file = '.env'  # Automatyczne wczytywanie zmiennych z pliku .env (jeśli istnieje)

def get_settings():
    return Settings()