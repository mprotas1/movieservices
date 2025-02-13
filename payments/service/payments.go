package service

import (
	"fmt"
	"log"
	"math/rand"
	"payments/db"
	"payments/model"
	"payments/queue"
	"time"
)

func EnqueuePayment(dto model.ReservationBookedDTO) model.PaymentDTO {
	fmt.Println("Enqueueing payment for reservation: ", dto.ReservationID)

	var entity = dto.ToEntity("PAYMENT_PENDING")

	payment, err := db.SavePayment(entity)

	if err != nil {
		log.Println("Error saving payment: ", err)
		panic(err)
	}

	go ProcessPayment(payment)

	fmt.Println("Payment enqueued: ", payment.ID)
	return model.PaymentDTO{
		PaymentID:     payment.ID.String(),
		ReservationID: payment.ReservationID,
		UserID:        payment.UserID,
		Amount:        payment.Amount,
		Status:        payment.Status,
	}
}

func FindAllPayments() []model.PaymentDTO {
	entities, err := db.FindAll()

	if err != nil {
		log.Println("Error finding all payments: ", err)
		panic(err)
	}

	var payments []model.PaymentDTO
	for _, entity := range entities {
		payments = append(payments, model.PaymentDTO{
			PaymentID:     entity.ID.String(),
			ReservationID: entity.ReservationID,
			UserID:        entity.UserID,
			Amount:        entity.Amount,
			Status:        entity.Status,
		})
	}

	fmt.Println("Found all payments: ", len(payments))
	return payments
}

func FindUserPayments(userId int) ([]model.PaymentDTO, error) {
	entities, err := db.FindByUserId(userId)

	if err != nil {
		log.Println("Error finding user payments: ", err)
		return nil, err
	}

	var payments []model.PaymentDTO
	for _, entity := range entities {
		payments = append(payments, model.PaymentDTO{
			PaymentID:     entity.ID.String(),
			ReservationID: entity.ReservationID,
			UserID:        entity.UserID,
			Amount:        entity.Amount,
			Status:        entity.Status,
		})
	}

	fmt.Println("Found user payments: ", len(payments))
	return payments, nil
}

func ProcessPayment(payment *model.PaymentEntity) {
	fmt.Println("Asynchronously processing payment: ", payment.ID)
	WaitSeconds(5, 10)

	GeneratePaymentStatus(payment)

	entity, err := db.UpdatePayment(*payment)

	if err != nil {
		fmt.Println("Error updating payment: ", err)
		panic(err)
	}

	dto := model.PaymentDTO{
		PaymentID:     entity.ID.String(),
		ReservationID: entity.ReservationID,
		UserID:        entity.UserID,
		Amount:        entity.Amount,
		Status:        entity.Status,
	}
	err = queue.NotifyPaymentStatus(dto)
	if err != nil {
		return
	}
	sprintf := fmt.Sprintf("Payment processed: %s with status: %s", entity.ID, entity.Status)
	fmt.Println(sprintf)
}

func GetUserPayments(userId int) []model.PaymentDTO {
	entities, err := FindUserPayments(userId)

	if err != nil {
		log.Println("Error finding user payments: ", err)
		panic(err)
	}

	var payments []model.PaymentDTO
	for _, entity := range entities {
		payments = append(payments, model.PaymentDTO{
			PaymentID:     entity.PaymentID,
			ReservationID: entity.ReservationID,
			UserID:        entity.UserID,
			Amount:        entity.Amount,
			Status:        entity.Status,
		})
	}

	fmt.Println("Found user payments: ", len(payments))
	return payments
}

func GeneratePaymentStatus(payment *model.PaymentEntity) {
	success := rand.Intn(2) == 1

	if success {
		payment.Status = model.PaymentSuccessful
	} else {
		payment.Status = model.PaymentFailed
	}
}

func WaitSeconds(from int, to int) {
	rand.Seed(time.Now().UnixNano())
	seconds := rand.Intn(to-from) + from
	time.Sleep(time.Duration(seconds) * time.Second)
}
