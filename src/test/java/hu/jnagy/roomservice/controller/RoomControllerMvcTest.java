package hu.jnagy.roomservice.controller;

import hu.jnagy.roomservice.model.Room;
import hu.jnagy.roomservice.service.RoomService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoomController.class)
public class RoomControllerMvcTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RoomService roomServiceMock;

    @Test
    public void shouldReturnRoom() throws Exception {
        //given
        Room room = new Room(3, 5000);
        String expected = "{\"id\":3,\"unitPrice\":5000.0}";
        //when
        when(roomServiceMock.getRoomById(any())).thenReturn(room);
        //then
        this.mockMvc.perform(get("/room/3")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString(expected)));
    }

    @Test
    public void shouldReturnRooms() throws Exception {
        //given
        List<Room> rooms = new ArrayList<>();
        Room room1 = new Room(3, 5000);
        Room room2 = new Room(4, 5000);
        rooms.add(room1);
        rooms.add(room2);
        String expected = "{\"id\":3,\"unitPrice\":5000.0}";
        //when
        when(roomServiceMock.getAllRoom()).thenReturn(rooms);
        //then
        this.mockMvc.perform(get("/rooms")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString(expected)));
    }

    @Test
    public void shouldCreateRoom() throws Exception {
        when(roomServiceMock.createRoom(anyLong(),anyDouble())).thenReturn(new Room(31,4000));
        this.mockMvc.perform(put("/room?roomId=31&unitPrice=4000")).andDo(print()).andExpect(status().isOk());
        verify(roomServiceMock, times(1)).createRoom(31, 4000);
    }

    @Test
    public void shouldDeleteRoomById() throws Exception {
        this.mockMvc.perform(delete("/room/3")).andDo(print()).andExpect(status().isOk());
        verify(roomServiceMock, times(1)).deleteRoomById((long) 3);
    }
}
