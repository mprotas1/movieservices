package db

import (
	"context"
	"fmt"
	"github.com/jackc/pgx/v5"
	_ "github.com/jackc/pgx/v5"
	"os"
)

const (
	host     = "localhost"
	port     = 5445
	username = "postgres"
	password = "postgres"
	dbname   = "paymentsdb"
)

func GetDbConnection() *pgx.Conn {
	connUrl := "postgres://postgres:postgres@localhost:5445/paymentsdb"

	conn, err := pgx.Connect(context.Background(), connUrl)
	if err != nil {
		fmt.Fprintf(os.Stderr, "Could not connect to database: %v\n", err)
		os.Exit(1)
	}

	return conn
}
