package db

import (
	"context"
	"payments/model"
)

func SavePayment(entity *model.PaymentEntity) (*model.PaymentEntity, error) {
	sql := `INSERT INTO payments (id, reservation_id, user_id, amount, status, created_at, updated_at) VALUES ($1, $2, $3, $4, $5, $6, $7) RETURNING id, reservation_id, user_id, amount, status, created_at, updated_at`

	var payment model.PaymentEntity
	err := GetDbConnection().QueryRow(context.Background(), sql, entity.ID, entity.ReservationID, entity.UserID, entity.Amount, entity.Status, entity.CreatedAt, entity.UpdatedAt).Scan(&payment.ID, &payment.ReservationID, &payment.UserID, &payment.Amount, &payment.Status, &payment.CreatedAt, &payment.UpdatedAt)

	if err != nil {
		panic(err)
	}

	return &payment, nil
}

func FindAll() ([]model.PaymentEntity, error) {
	sql := `SELECT * FROM payments`

	rows, err := GetDbConnection().Query(context.Background(), sql)

	if err != nil {
		panic(err)
	}

	var payments []model.PaymentEntity
	for rows.Next() {
		var payment model.PaymentEntity
		err := rows.Scan(&payment.ID, &payment.ReservationID, &payment.UserID, &payment.Amount, &payment.Status, &payment.CreatedAt, &payment.UpdatedAt)

		if err != nil {
			panic(err)
		}

		payments = append(payments, payment)
	}

	return payments, nil
}

func FindByUserId(userId int) ([]model.PaymentEntity, error) {
	sql := `SELECT * FROM payments WHERE user_id=$1`

	rows, err := GetDbConnection().Query(context.Background(), sql, userId)

	if err != nil {
		panic(err)
	}

	var payments []model.PaymentEntity
	for rows.Next() {
		var payment model.PaymentEntity
		err := rows.Scan(&payment.ID, &payment.ReservationID, &payment.UserID, &payment.Amount, &payment.Status, &payment.CreatedAt, &payment.UpdatedAt)

		if err != nil {
			panic(err)
		}

		payments = append(payments, payment)
	}

	return payments, nil
}

func UpdatePayment(payment model.PaymentEntity) (*model.PaymentEntity, error) {
	sql := `UPDATE payments SET status=$1, updated_at=$2 WHERE id=$3 RETURNING id, reservation_id, user_id, amount, status, created_at, updated_at`

	var updatedPayment model.PaymentEntity
	err := GetDbConnection().QueryRow(context.Background(), sql, payment.Status, payment.UpdatedAt, payment.ID).Scan(&updatedPayment.ID, &updatedPayment.ReservationID, &updatedPayment.UserID, &updatedPayment.Amount, &updatedPayment.Status, &updatedPayment.CreatedAt, &updatedPayment.UpdatedAt)

	if err != nil {
		panic(err)
	}

	return &updatedPayment, nil
}
