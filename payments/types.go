package main

const (
	PaymentSuccessful = "PAYMENT_SUCCESSFUL"
	PaymentFailed     = "PAYMENT_FAILED"
	PaymentPending    = "PAYMENT_PENDING"
)

type PaymentEntity struct {
	ID            string `json:"id"`
	ReservationID string `json:"reservationId"`
	UserID        string `json:"userId"`
	Amount        string `json:"amount"`
	Status        string `json:"status"`
	CreatedAt     string `json:"createdAt"`
	UpdatedAt     string `json:"updatedAt"`
}

type ReservationBookedDTO struct {
	ReservationID string  `json:"reservation_id"`
	UserID        string  `json:"user_id"`
	Amount        float64 `json:"amount"`
}

type PaymentDTO struct {
	PaymentID     string  `json:"paymentId"`
	ReservationID string  `json:"reservation_id"`
	UserID        string  `json:"user_id"`
	Amount        float64 `json:"amount"`
	Status        string  `json:"status"`
}

func (p *PaymentDTO) IsSuccessful() bool {
	return p.Status == PaymentSuccessful
}

func (p *PaymentDTO) IsFailed() bool {
	return p.Status == PaymentFailed
}

func (p *PaymentDTO) IsPending() bool {
	return p.Status == PaymentPending
}
