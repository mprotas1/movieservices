package main

func CalculateSeatPricing(screeningDTO ScreeningDTO) []PricedScreeningSeatDTO {
	var result []PricedScreeningSeatDTO

	for _, seat := range screeningDTO.Seats {
		pricedSeat := BuildPricedSeat(seat)
		result = append(result, pricedSeat)
	}

	return result
}

func BuildPricedSeat(seat ScreeningSeatDTO) PricedScreeningSeatDTO {
	var pricedSeat PricedScreeningSeatDTO
	pricedSeat.ID = seat.ID
	pricedSeat.ScreeningRoomId = seat.ScreeningRoomId
	pricedSeat.Row = seat.Row
	pricedSeat.Column = seat.Column
	pricedSeat.SeatType = seat.SeatType
	pricedSeat.Price = GetPriceForSeatType(seat.SeatType)
	return pricedSeat
}

func GetPriceForSeatType(seatType string) float64 {
	switch seatType {
	case "ECONOMY":
		return GetEconomySeatPrice()
	case "STANDARD":
		return GetStandardSeatPrice()
	case "VIP":
		return GetVipSeatPrice()
	default:
		return GetStandardPrice()
	}
}

func GetEconomySeatPrice() float64 {
	return GetStandardPrice() * GetEconomyRate()
}

func GetStandardSeatPrice() float64 {
	return GetStandardPrice() * GetStandardRate()
}

func GetVipSeatPrice() float64 {
	return GetStandardPrice() * GetVipRate()
}
