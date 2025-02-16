package queue

import (
	"encoding/json"
	"payments/model"
)

type PaymentNotifier struct {
	KafkaProducer KafkaProducer
}

func (paymentNotifier PaymentNotifier) NotifyPaymentStatus(paymentDTO model.PaymentDTO) error {
	topic := GetTopic(paymentDTO)
	message, err := json.Marshal(paymentDTO)

	if err != nil {
		return err
	}

	return paymentNotifier.KafkaProducer.Notify(topic, message)
}

func GetTopic(paymentDTO model.PaymentDTO) string {
	if paymentDTO.IsFailed() {
		return PaymentFailedTopic
	}

	if paymentDTO.IsSuccessful() {
		return PaymentSuccessfulTopic
	}

	panic("Invalid payment status")
}
