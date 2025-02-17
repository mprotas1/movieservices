package db

import (
	"context"
	"fmt"
	"github.com/jackc/pgx/v5"
	_ "github.com/jackc/pgx/v5"
	"os"
	"payments/environment"
)

func GetDbConnection() *pgx.Conn {
	username := environment.GetDBUsername()
	password := environment.GetDBPassword()
	host := environment.GetDBHost()
	port := environment.GetDBPort()
	dbname := environment.GetDBName()

	connUrl := fmt.Sprintf("postgres://%s:%s@%s:%s/%s", username, password, host, port, dbname)

	conn, err := pgx.Connect(context.Background(), connUrl)
	if err != nil {
		fmt.Fprintf(os.Stderr, "Could not connect to database: %v\n", err)
		os.Exit(1)
	}

	return conn
}
