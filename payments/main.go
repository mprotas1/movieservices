package main

import (
	"payments/queue"
	"payments/rest"
	"payments/service"
)

func main() {
	paymentService := &service.PaymentService{}

	// Inicjalizujemy kolejkę z interfejsem PaymentProcessor
	queue.InitQueue(paymentService)

	// Uruchamiamy Kafka Consumer w gorutynie
	go queue.Listen(queue.ReservationPaymentTopic)
	rest.Init()

	select {}
}
