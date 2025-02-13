package model

import (
	"github.com/google/uuid"
	"time"
)

const (
	PaymentSuccessful = "PAYMENT_SUCCESSFUL"
	PaymentFailed     = "PAYMENT_FAILED"
	PaymentPending    = "PAYMENT_PENDING"
)

type PaymentEntity struct {
	ID            uuid.UUID `json:"id"`
	ReservationID string    `json:"reservationId"`
	UserID        int64     `json:"userId"`
	Amount        float64   `json:"amount"`
	Status        string    `json:"status"`
	CreatedAt     time.Time `json:"createdAt"`
	UpdatedAt     time.Time `json:"updatedAt"`
}

type ReservationBookedDTO struct {
	ReservationID string  `json:"reservationId"`
	UserID        int64   `json:"userId"`
	Amount        float64 `json:"amount"`
}

type PaymentDTO struct {
	PaymentID     string  `json:"paymentId"`
	ReservationID string  `json:"reservationId"`
	UserID        int64   `json:"userId"`
	Amount        float64 `json:"amount"`
	Status        string  `json:"status"`
}

type ProblemDetail struct {
	Type     string `json:"type"`
	Title    string `json:"title"`
	Status   int    `json:"status"`
	Detail   string `json:"detail"`
	Instance string `json:"instance"`
}

func (reservationDTO *ReservationBookedDTO) ToEntity(status string) *PaymentEntity {
	return &PaymentEntity{
		ID:            uuid.New(),
		ReservationID: reservationDTO.ReservationID,
		UserID:        reservationDTO.UserID,
		Amount:        reservationDTO.Amount,
		Status:        status,
		CreatedAt:     time.Now().UTC(),
		UpdatedAt:     time.Now().UTC(),
	}
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
