package queue

import (
	"github.com/confluentinc/confluent-kafka-go/kafka"
	"log"
)

const (
	PaymentSuccessfulTopic = "payment-successful"
	PaymentFailedTopic     = "payment-failed"
)

func Notify(topic string, message []byte) error {
	producer, err := kafka.NewProducer(&kafka.ConfigMap{
		"bootstrap.servers": "localhost:9092",
	})

	if err != nil {
		return err
	}

	defer producer.Close()

	deliveryChan := make(chan kafka.Event, 10000)
	defer close(deliveryChan)

	err = producer.Produce(&kafka.Message{
		TopicPartition: kafka.TopicPartition{
			Topic:     &topic,
			Partition: kafka.PartitionAny,
		},
		Value: message,
	}, deliveryChan)

	if err != nil {
		return err
	}

	e := <-deliveryChan
	m := e.(*kafka.Message)
	if m.TopicPartition.Error != nil {
		return m.TopicPartition.Error
	}

	log.Printf("Message delivered to topic %s [%d] at offset %v\n", *m.TopicPartition.Topic, m.TopicPartition.Partition, m.TopicPartition.Offset)
	return nil
}
