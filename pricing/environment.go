package main

import "os"

func GetPort() string {
	port, isSet := os.LookupEnv("SERVER_PORT")

	if !isSet {
		return ":8001"
	}

	return port
}
