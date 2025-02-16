package queue

import (
	"encoding/json"
	"log"
	"os"
	"os/signal"
	"payments/core"
	"payments/model"

	"github.com/confluentinc/confluent-kafka-go/kafka"
)

const ReservationPaymentTopic = "reservation_payment"

var paymentProcessor core.PaymentProcessor

func InitQueue(p core.PaymentProcessor) {
	paymentProcessor = p
}

func Listen(topic string) {
	consumer, err := kafka.NewConsumer(&kafka.ConfigMap{
		"bootstrap.servers": "localhost:9092",
		"group.id":          "payment-group",
		"auto.offset.reset": "earliest",
	})

	if err != nil {
		panic(err)
	}

	defer consumer.Close()

	err = consumer.SubscribeTopics([]string{topic}, nil)
	log.Println("Subscribed to topic:", topic)

	if err != nil {
		panic(err)
	}

	sigchan := make(chan os.Signal, 1)
	signal.Notify(sigchan, os.Interrupt)

	for {
		select {
		case <-sigchan:
			log.Println("Shutting down Kafka consumer...")
			return
		default:
			ev := consumer.Poll(100)
			switch e := ev.(type) {
			case *kafka.Message:
				log.Println("Received:", string(e.Value))

				if paymentProcessor != nil {
					reservationBookedDTO := &model.ReservationBookedDTO{}
					err := json.Unmarshal(e.Value, reservationBookedDTO)
					if err != nil {
						return
					}
					paymentProcessor.EnqueuePayment(*reservationBookedDTO)
				}

			case kafka.Error:
				log.Println("Kafka error:", e)
			}
		}
	}
}
