package rest

import (
	"github.com/gin-gonic/gin"
	"payments/model"
	"payments/service"
	"payments/environment"
	"strconv"
)

const Port = ":8002"
const ContextPath = "/payments"

var paymentService = &service.PaymentService{}

func Init() {
	router := GetRouter()
	err := router.Run(environment.GetOrDefault("PORT", Port))
	if err != nil {
		panic(err)
	}
}

func GetRouter() *gin.Engine {
	router := gin.Default()
	router.POST(ContextPath, Pay)
	router.GET(ContextPath, FindAllPaymentsAPI)
	router.GET(ContextPath+"/users/:userId", FindUserPaymentsAPI)
	return router
}

func Pay(c *gin.Context) {
	var reservationDTO model.ReservationBookedDTO

	if err := c.BindJSON(&reservationDTO); err != nil {
		c.JSON(400, BuildProblemDetail("Invalid Request Data", "Bad Request", 400))
		return
	}

	payment := paymentService.EnqueuePayment(reservationDTO)
	c.IndentedJSON(200, payment)
}

func FindAllPaymentsAPI(c *gin.Context) {
	payments := paymentService.FindAllPayments()
	c.IndentedJSON(200, payments)
}

func FindUserPaymentsAPI(c *gin.Context) {
	userId := c.Param("userId")
	if userId == "" {
		c.JSON(400, BuildProblemDetail("Invalid Request Data", "Bad Request", 400))
		return
	}

	id, err := strconv.Atoi(userId)

	if err != nil {
		c.JSON(400, BuildProblemDetail("Invalid Request Data", "Bad Request", 400))
		return
	}

	payments := paymentService.GetUserPayments(id)
	c.IndentedJSON(200, payments)
}

func BuildProblemDetail(detail string, title string, status int) model.ProblemDetail {
	return model.ProblemDetail{
		Type:     "about:blank",
		Title:    title,
		Status:   status,
		Detail:   detail,
		Instance: ContextPath,
	}
}
