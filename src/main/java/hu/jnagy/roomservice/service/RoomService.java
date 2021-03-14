package hu.jnagy.roomservice.service;

import hu.jnagy.roomservice.model.Room;
import hu.jnagy.roomservice.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private final RoomRepository roomRepo;

    public RoomService(RoomRepository roomRepo) {
        this.roomRepo = roomRepo;
    }

    public Room createRoom(long id, double price) {
        Room room = new Room(id, price);
        return roomRepo.addRoom(room);
    }

    public void deleteRoomById(Long roomId) {
        roomRepo.getRooms().remove(roomId);
    }

    public Room getRoomById(Long roomId) {
       return roomRepo.getRooms().get(roomId);
    }

    public List<Room> getAllRoom(){
        return roomRepo.getRooms().values().stream().collect(Collectors.toList());
    }
}
