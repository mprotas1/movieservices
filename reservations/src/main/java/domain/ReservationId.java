package domain;

import java.util.UUID;

public class ReservationId {
    private UUID id;

    public ReservationId() {
        this.id = UUID.randomUUID();
    }

    public ReservationId(UUID id) {
        this.id = id;
    }

}
