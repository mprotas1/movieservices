package environment

import (
	"errors"
	"os"
)

func GetEnvironmentProperty(property string) (string, error) {
	value, found := os.LookupEnv(property)

	if !found {
		return "", errors.New("Property not found")
	}

	return value, nil
}
