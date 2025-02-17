package main

import (
	"github.com/gin-gonic/gin"
)

const ContextPath = "/api/v1/pricing"

func InitRest() {
	router := GetRouter()
	err := router.Run(GetPort())
	if err != nil {
		panic(err)
	}
}

func GetRouter() *gin.Engine {
	router := gin.Default()
	router.POST(ContextPath, CalculatePrice)
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
		Instance: ContextPath,
	}
}
