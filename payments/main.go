package main

import (
	"payments/queue"
	"payments/rest"
)

func main() {
	rest.Init()
	queue.Listen(queue.ReservationPaymentTopic)
}
