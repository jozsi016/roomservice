package hu.jnagy.roomservice.service;

import hu.jnagy.roomservice.model.Room;
import hu.jnagy.roomservice.repository.RoomRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class RoomServiceTest {

    RoomService roomService;
    RoomRepository roomRepository;

    @Before
    public void setUp() {
        roomRepository = new RoomRepository();
        roomService = new RoomService(roomRepository);
    }

    @Test
    public void shouldCreateRoom() {
        //Given
        long expectedId = 31;
        roomService.createRoom(31, 4000);
        //When
        Room actualRoom = roomService.getRoomById(31L);
        //Then
        assertNotNull(actualRoom);
    }

    @Test
    public void shouldDeleteRoomById() {
        //Given
        roomService.deleteRoomById(12L);
        //When
        Room actualRoom = roomService.getRoomById(12L);
        //Then
        assertNull(actualRoom);
    }

    @Test
    public void shouldRoomById() {
        //Given
        //When
        Room room = roomService.getRoomById(12L);
        //Then
        assertNotNull(room);
    }

    @Test
    public void shouldGetAllRoom() {
        //Given
        int expected = 30;
        List<Room> rooms = roomService.getAllRoom();
        //When
        int actualRoomsSize = rooms.size();
        //Then
        assertEquals(expected, actualRoomsSize);
    }
}
