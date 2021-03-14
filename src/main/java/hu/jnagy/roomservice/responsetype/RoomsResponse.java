package hu.jnagy.roomservice.responsetype;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import hu.jnagy.roomservice.model.Room;

import java.util.List;
import java.util.Objects;

@JsonDeserialize(builder = RoomsResponse.Builder.class)
public final class RoomsResponse {
    private final List<Room> rooms;

    private RoomsResponse(RoomsResponse.Builder builder) {
        this.rooms = builder.rooms;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomsResponse that = (RoomsResponse) o;
        return Objects.equals(rooms, that.rooms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rooms);
    }

    @Override
    public String toString() {
        return "RoomResponse{" +
                "rooms=" + rooms +
                '}';
    }

    public static class Builder {
        private List<Room> rooms;

        public Builder withRooms(List<Room> rooms) {
            this.rooms = rooms;
            return this;
        }

        public RoomsResponse build() {
            return new RoomsResponse(this);
        }
    }
}



