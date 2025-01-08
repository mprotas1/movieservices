package main

import (
	"context"
	"github.com/jackc/pgx/v5"
)

func SavePayment(entity *PaymentEntity, connection *pgx.Conn) (PaymentEntity, error) {
	sql := `INSERT INTO payments (id, reservation_id, user_id, amount, status, created_at, updated_at) VALUES ($1, $2, $3, $4, $5, $6, $7) RETURNING id, reservation_id, user_id, amount, status, created_at, updated_at`

	var payment PaymentEntity
	err := connection.QueryRow(context.Background(), sql, entity.ID, entity.ReservationID, entity.UserID, entity.Amount, entity.Status, entity.CreatedAt, entity.UpdatedAt).Scan(&payment.ID, &payment.ReservationID, &payment.UserID, &payment.Amount, &payment.Status, &payment.CreatedAt, &payment.UpdatedAt)
	if err != nil {
		return PaymentEntity{}, err
	}

	return payment, nil
}
