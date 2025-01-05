package main

import (
	uuid "github.com/google/uuid"
)

type ScreeningDTO struct {
	ScreeningId         uuid.UUID          `json:"screeningId"`
	MovieId             int32              `json:"movieId"`
	ScreeningRoomId     uuid.UUID          `json:"screeningRoomId"`
	CinemaId            uuid.UUID          `json:"cinemaId"`
	StartTime           string             `json:"startTime"`
	EndTime             string             `json:"endTime"`
	MovieTitle          string             `json:"movieTitle"`
	ScreeningRoomNumber int32              `json:"screeningRoomNumber"`
	Seats               []ScreeningSeatDTO `json:"seats"`
}

type ScreeningSeatDTO struct {
	ID              uuid.UUID `json:"id"`
	ScreeningId     uuid.UUID `json:"screeningId"`
	ScreeningRoomId uuid.UUID `json:"screeningRoomId"`
	Row             int32     `json:"row"`
	Column          int32     `json:"column"`
	SeatType        string    `json:"type"`
}

type PricedScreeningSeatDTO struct {
	ID              uuid.UUID `json:"id"`
	ScreeningRoomId uuid.UUID `json:"screeningRoomId"`
	Row             int32     `json:"row"`
	Column          int32     `json:"column"`
	SeatType        string    `json:"type"`
	Price           float64   `json:"price"`
}

type Properties struct {
	Entries map[string]string
}
