package main

import (
	"github.com/gin-gonic/gin"
)

const SERVER_PORT = ":8001"
const CONTEXT_PATH = "/api/v1/pricing"

func main() {
	// with port 8001

	properties := GetProperties("pricing.properties").Entries

	for s := range properties {
		println(properties[s])
	}

	router := GetRouter()
	err := router.Run(SERVER_PORT)
	if err != nil {
		panic(err)
	}
}

func GetRouter() *gin.Engine {
	router := gin.Default()
	router.POST(CONTEXT_PATH, CalculatePrice)
	return router
}

func CalculatePrice(c *gin.Context) {
	// ToDo:
	// 1. Get the ScreeningDTO from the request
	// 2. Calculate the price for each seat
	// 3. Return the Array of PricedScreeningSeatDTO

	var screeningDTO ScreeningDTO
	if err := c.BindJSON(&screeningDTO); err != nil {
		c.JSON(400, gin.H{"error": err.Error()})
		return
	}

	result := CalculateSeatPricing(screeningDTO)
	c.IndentedJSON(200, result)
}
