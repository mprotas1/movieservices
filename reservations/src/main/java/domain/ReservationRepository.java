package domain;

public interface ReservationRepository {
    Reservation save(Reservation reservation);
    Reservation findById(ReservationId id);
    Reservation findByScreeningIdAndSeatId(ScreeningId screeningId, SeatId seatId);
    void delete(Reservation reservation);
    void deleteById(ReservationId id);
}
