package main

import (
	"strconv"
	"strings"
)

func GetPricingProperties() Properties {
	return GetProperties("pricing.properties")
}

func GetStandardPrice() float64 {
	stringProps := GetPricingProperties().Entries["pricing.standard-price"]
	val, err := strconv.ParseFloat(strings.TrimSpace(stringProps), 64)
	if err != nil {
		panic(err)
	}
	return val
}

func GetEconomyRate() float64 {
	stringProps := GetPricingProperties().Entries["pricing.economy-rate"]
	val, err := strconv.ParseFloat(strings.TrimSpace(stringProps), 64)
	if err != nil {
		panic(err)
	}
	return val
}

func GetStandardRate() float64 {
	stringProps := GetPricingProperties().Entries["pricing.standard-rate"]
	val, err := strconv.ParseFloat(strings.TrimSpace(stringProps), 64)
	if err != nil {
		panic(err)
	}
	return val
}

func GetVipRate() float64 {
	stringProps := GetPricingProperties().Entries["pricing.vip-rate"]
	val, err := strconv.ParseFloat(strings.TrimSpace(stringProps), 64)
	if err != nil {
		panic(err)
	}
	return val
}
