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

type PaymentService struct {
	PaymentNotifier queue.PaymentNotifier
}

func (s *PaymentService) EnqueuePayment(dto model.ReservationBookedDTO) model.PaymentDTO {
	fmt.Println("Enqueueing payment for reservation:", dto.ReservationID)

	payment, err := db.SavePayment(dto.NewPayment())
	if err != nil {
		log.Println("Error saving payment:", err)
		panic(err)
	}

	go s.ProcessPayment(payment)

	log.Println("Payment enqueued:", payment.ID)
	return *payment.ToDTO()
}

func (s *PaymentService) FindAllPayments() []model.PaymentDTO {
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

	log.Println("Found all payments: ", len(payments))
	return payments
}

func (s *PaymentService) FindUserPayments(userId int) ([]model.PaymentDTO, error) {
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

	log.Println("Found user payments: ", len(payments))
	return payments, nil
}

func (s *PaymentService) ProcessPayment(payment *model.PaymentEntity) {
	log.Println("Asynchronously processing payment: ", payment.ID)
	WaitSeconds(5, 10)

	GeneratePaymentStatus(payment)

	entity, err := db.UpdatePayment(*payment)

	if err != nil {
		log.Println("Error updating payment: ", err)
		panic(err)
	}

	err = s.PaymentNotifier.NotifyPaymentStatus(*entity.ToDTO())

	if err != nil {
		return
	}

	formatted := fmt.Sprintf("Payment: %s processed with status: %s", entity.ID, entity.Status)
	log.Println(formatted)
}

func (s *PaymentService) GetUserPayments(userId int) []model.PaymentDTO {
	dtos, err := s.FindUserPayments(userId)

	if err != nil {
		log.Println("Error finding user payments: ", err)
		panic(err)
	}

	var payments []model.PaymentDTO
	for _, dto := range dtos {
		payments = append(payments, dto)
	}

	log.Printf("Found: %d user payments\n", len(payments))
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
