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

type PaymentStatus struct {
	ReservationId string `json:"reservationId"`
	Status        string `json:"status"`
	UserId        int64  `json:"userId"`
}

func (reservationDTO *ReservationBookedDTO) NewPayment() *PaymentEntity {
	return &PaymentEntity{
		ID:            uuid.New(),
		ReservationID: reservationDTO.ReservationID,
		UserID:        reservationDTO.UserID,
		Amount:        reservationDTO.Amount,
		Status:        PaymentPending,
		CreatedAt:     time.Now().UTC(),
		UpdatedAt:     time.Now().UTC(),
	}
}

func (entity *PaymentEntity) ToDTO() *PaymentDTO {
	return &PaymentDTO{
		PaymentID:     entity.ID.String(),
		ReservationID: entity.ReservationID,
		UserID:        entity.UserID,
		Amount:        entity.Amount,
		Status:        entity.Status,
	}
}

func (paymentDTO *PaymentDTO) ToStatus() *PaymentStatus {
	return &PaymentStatus{
		ReservationId: paymentDTO.ReservationID,
		Status:        paymentDTO.Status,
		UserId:        paymentDTO.UserID,
	}
}

func (paymentDTO *PaymentDTO) IsSuccessful() bool {
	return paymentDTO.Status == PaymentSuccessful
}

func (paymentDTO *PaymentDTO) IsFailed() bool {
	return paymentDTO.Status == PaymentFailed
}

func (paymentDTO *PaymentDTO) IsPending() bool {
	return paymentDTO.Status == PaymentPending
}
