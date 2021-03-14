package hu.jnagy.roomservice.responsetype;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import hu.jnagy.roomservice.model.Room;

import java.util.Objects;

@JsonDeserialize(builder = RoomResponse.Builder.class)
public final class RoomResponse {
    private final Room room;

    private RoomResponse(RoomResponse.Builder builder) {
        this.room = builder.room;
    }

    public Room getRoom() {
        return room;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomResponse that = (RoomResponse) o;
        return Objects.equals(room, that.room);
    }

    @Override
    public int hashCode() {
        return Objects.hash(room);
    }

    @Override
    public String toString() {
        return "RoomResponse{" +
                "room=" + room +
                '}';
    }

    public static class Builder {
        private Room room;

        public Builder withRoom(Room room) {
            this.room = room;
            return this;
        }

        public RoomResponse build() {
            return new RoomResponse(this);
        }
    }
}



