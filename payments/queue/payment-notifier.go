package queue

import (
	"encoding/json"
	"payments/model"
)

type PaymentNotifier struct {
	KafkaProducer KafkaProducer
}

func (paymentNotifier PaymentNotifier) NotifyPaymentStatus(paymentDTO model.PaymentDTO) error {
	paymentStatus := paymentDTO.ToStatus()
	message, err := json.Marshal(paymentStatus)

	if err != nil {
		return err
	}

	return paymentNotifier.KafkaProducer.Notify(PaymentStatusTopic, message)
}
