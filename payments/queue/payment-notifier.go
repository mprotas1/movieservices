package queue

import (
	"encoding/json"
	"payments/model"
)

func NotifyPaymentStatus(paymentDTO model.PaymentDTO) error {
	topic := GetTopic(paymentDTO)
	message, err := json.Marshal(paymentDTO)

	if err != nil {
		return err
	}

	return Notify(topic, message)
}

func GetTopic(paymentDTO model.PaymentDTO) string {
	if paymentDTO.IsFailed() {
		return PaymentFailedTopic
	}

	if paymentDTO.IsSuccessful() {
		return PaymentSuccessfulTopic
	}

	return ""
}
