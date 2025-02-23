package main

import (
	"payments/queue"
	"payments/rest"
	"payments/service"
)

func main() {
	paymentService := &service.PaymentService{}
	queue.InitQueue(paymentService)
	go queue.Listen(queue.ReservationPaymentTopic)
	rest.Init()

	select {}
}
