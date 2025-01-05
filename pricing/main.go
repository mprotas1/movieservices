package main

import (
	"github.com/gin-gonic/gin"
)

const SERVER_PORT = ":8001"
const CONTEXT_PATH = "/api/v1/pricing"

func main() {
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
	var screeningDTO ScreeningDTO

	if err := c.BindJSON(&screeningDTO); err != nil {
		c.JSON(400, BuildProblemDetail("Invalid Request Data", "Bad Request", 400))
		return
	}

	result := CalculateSeatPricing(screeningDTO)
	c.IndentedJSON(200, result)
}

func BuildProblemDetail(detail string, title string, status int) ProblemDetail {
	return ProblemDetail{
		Type:     "about:blank",
		Title:    title,
		Status:   status,
		Detail:   detail,
		Instance: CONTEXT_PATH,
	}
}
