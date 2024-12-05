import psycopg2 as pg

from settings import get_settings

def get_connection():
    settings = get_settings()
    return pg.connect(
        database=settings.database,
        user=settings.user,
        password=settings.password,
        host=settings.host,
        port=settings.port
    )

def get_movies():
    connection = get_connection()
    statement = connection.cursor().execute('SELECT * FROM movies')
    statement
    print(get_connection())

if __name__ == '__main__':
    get_movies()