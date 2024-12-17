package domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Reservation {
    private ReservationId reservationId;
    private UserId userId;
    private ScreeningId screeningId;
    private SeatId seatId;
    private ReservationStatus status;

    public Reservation(UserId userId, ScreeningId screeningId, SeatId seatId) {
        this.reservationId = new ReservationId();
        this.userId = userId;
        this.screeningId = screeningId;
        this.seatId = seatId;
        this.status = ReservationStatus.PENDING;
    }

    public void confirm() {
        this.status = ReservationStatus.CONFIRMED;
    }

    public void cancel() {
        this.status = ReservationStatus.CANCELLED;
    }

}
