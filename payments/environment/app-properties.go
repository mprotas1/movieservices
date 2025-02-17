package environment

func GetKafkaAddress() string {
	return GetOrDefault("KAFKA_ADDRESS", "localhost:9092")
}

func GetKafkaGroupId() string {
	return GetOrDefault("KAFKA_GROUP_ID", "payment-group")
}

func GetDBUsername() string {
	return GetOrDefault("DB_USERNAME", "postgres")
}

func GetDBPassword() string {
	return GetOrDefault("DB_PASSWORD", "postgres")
}

func GetDBHost() string {
	return GetOrDefault("DB_HOST", "localhost")
}

func GetDBPort() string {
	return GetOrDefault("DB_PORT", "5432")
}

func GetDBName() string {
	return GetOrDefault("DB_NAME", "payments")
}

func GetOrDefault(property string, defaultValue string) string {
	value, err := GetEnvironmentProperty(property)

	if err != nil {
		return defaultValue
	}

	return value
}
