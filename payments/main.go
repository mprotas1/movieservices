package main

import (
	"context"
)

func main() {
	conn := GetDbConnection()
	defer conn.Close(context.Background())
}
