package hu.jnagy.roomservice.repository;

import hu.jnagy.roomservice.model.Room;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RoomRepository {
    private final Map<Long, Room> rooms = initRooms();

    public Map<Long, Room> getRooms() {
        return rooms;
    }

    public Room addRoom(Room room) {
        return rooms.put(room.getId(), room);
    }

    public Map<Long, Room> initRooms() {
        Map<Long, Room> rooms = new HashMap<>();
        for (long i = 1; i <= 30; i++) {
            rooms.put(i, new Room(i, 5000));
        }
        return rooms;
    }
}
