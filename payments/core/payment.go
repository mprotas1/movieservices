package core

import "payments/model"

type PaymentProcessor interface {
	EnqueuePayment(dto model.ReservationBookedDTO) model.PaymentDTO
}
