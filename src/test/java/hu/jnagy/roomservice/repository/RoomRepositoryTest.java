package hu.jnagy.roomservice.repository;

import hu.jnagy.roomservice.model.Room;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class RoomRepositoryTest {

    RoomRepository roomRepo;
    Map<Long, Room> rooms;

    @Before
    public void setUp() {
        roomRepo = new RoomRepository();
        rooms = roomRepo.getRooms();
    }

    @Test
    public void shouldInitRooms() {
        //Given
        int expected = 30;
        //when
        int actualRoomsSize = rooms.values().size();
        //Then
        assertEquals(expected, actualRoomsSize);
    }

    @Test
    public void shouldAddRooms() {
        //given
        int expected = 31;
        Room room = new Room(31, 5000);
        roomRepo.addRoom(room);
        //when
        int actual = roomRepo.getRooms().size();
        //then
        assertEquals(expected, actual);
    }
}
