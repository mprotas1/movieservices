package queue

import (
	"fmt"
	"github.com/confluentinc/confluent-kafka-go/kafka"
	"log"
)

const (
	ReservationPaymentTopic = "reservation_payment"
)

func Listen(topic string) {
	log.Println("Received message on topic: ", topic)
	listener, err := kafka.NewConsumer(&kafka.ConfigMap{
		"bootstrap.servers": "localhost:9092",
		"group.id":          "payments",
		"auto.offset.reset": "earliest",
	})

	if err != nil {
		panic(err)
	}

	defer listener.Close()

	err = listener.SubscribeTopics([]string{topic}, nil)

	if err != nil {
		panic(err)
	}

	for {
		msg, err := listener.ReadMessage(-1)
		if err == nil {
			fmt.Println("Received message: ", string(msg.Value))
		} else {
			fmt.Println("Error reading message: ", err)
		}
	}

}

func test() {

}
