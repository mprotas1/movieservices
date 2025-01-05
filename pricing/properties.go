package main

import (
	"bufio"
	"os"
	"strings"
)

func GetProperties(path string) Properties {
	file, err := os.Open(path)
	if err != nil {
		panic(err)
	}
	defer file.Close()

	properties := make(map[string]string)

	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		line := scanner.Text()
		parts := strings.SplitN(line, "=", 2)
		if len(parts) == 2 {
			key := strings.TrimSpace(parts[0])
			value := strings.TrimSpace(parts[1])
			properties[key] = value
		}
	}

	if err := scanner.Err(); err != nil {
		panic(err)
	}

	return Properties{Entries: properties}
}
