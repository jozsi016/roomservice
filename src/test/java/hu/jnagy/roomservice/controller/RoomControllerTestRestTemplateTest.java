package hu.jnagy.roomservice.controller;

import hu.jnagy.roomservice.model.Room;
import hu.jnagy.roomservice.responsetype.ErrorResponse;
import hu.jnagy.roomservice.responsetype.RoomResponse;
import hu.jnagy.roomservice.service.RoomService;
import hu.jnagy.roomservice.responsetype.RoomsResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RoomControllerTestRestTemplateTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private RoomService roomServiceMock;

    @Test
    public void shouldReturnRoom() throws Exception {
        //given
        Room expectedRoom = new Room(3, 5000);
        //when
        when(roomServiceMock.getRoomById(any())).thenReturn(expectedRoom);
        //then
        ResponseEntity<RoomResponse> actual = this.testRestTemplate.getForEntity("/room/1", RoomResponse.class);
        assertThat(actual.getStatusCode(), is(HttpStatus.OK));
        assertThat(actual.getBody().getRoom(), is(expectedRoom));
    }

    @Test
    public void shouldReturnNotFoundWhenRoomNumberWrong(){
        ResponseEntity<ErrorResponse> actual = this.testRestTemplate.getForEntity("/room/1", ErrorResponse.class);
        assertThat(actual.getStatusCode(), is(HttpStatus.NOT_FOUND));
        assertThat(actual.getBody().getCause(), is("The room not available!"));
    }

    @Test
    public void shouldReturnRooms() throws Exception {
        //given
        List<Room> rooms = new ArrayList<>();
        Room room1 = new Room(3, 5000);
        Room room2 = new Room(4, 5000);
        rooms.add(room1);
        rooms.add(room2);
        //when
        when(roomServiceMock.getAllRoom()).thenReturn(rooms);
        //then
        ResponseEntity<RoomsResponse> actual = this.testRestTemplate.getForEntity("/rooms", RoomsResponse.class);
        assertThat(actual.getStatusCode(), is(HttpStatus.OK));
        assertThat(actual.getBody().getRooms().get(0), is(room1));
    }

    @Test
    public void shouldReturnNotFoundWhenRoomsEmpty() throws Exception {
        ResponseEntity<ErrorResponse> actual = this.testRestTemplate.getForEntity("/rooms", ErrorResponse.class);
        assertThat(actual.getBody().getCause(), is("The room not available!"));
    }


    @Test
    public void whenStatusMethodNotAllowed() throws Exception {
        //given
        Room room = new Room(3, 5000);
        //when
        when(roomServiceMock.getRoomById(any())).thenReturn(room);
        //then
        ResponseEntity<RoomsResponse> actual = this.testRestTemplate.getForEntity("/room", RoomsResponse.class);
        assertThat(actual.getStatusCode(), is(HttpStatus.METHOD_NOT_ALLOWED));
    }

    @Test
    public void whenStatusBadRequest() throws Exception {
        //given
        Room room = new Room(3, 5000);
        //when
        when(roomServiceMock.getRoomById(any())).thenReturn(room);
        //then
        ResponseEntity<RoomsResponse> actual = this.testRestTemplate.getForEntity("/room/%%20", RoomsResponse.class);
        assertThat(actual.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void whenStatusNotFound() throws Exception {
        //given
        Room room = new Room(3, 5000);
        //when
        when(roomServiceMock.getRoomById(any())).thenReturn(room);
        //then
        ResponseEntity<RoomsResponse> actual = this.testRestTemplate.getForEntity("/", RoomsResponse.class);
        assertThat(actual.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    public void whenBasicAuth() throws Exception {
        //given
        Room room = new Room(3, 5000);
        //when
        when(roomServiceMock.getRoomById(any())).thenReturn(room);
        //then
        ResponseEntity<RoomsResponse> actual = this.testRestTemplate.withBasicAuth("name", "1234").exchange("/room/1", HttpMethod.DELETE, HttpEntity.EMPTY, RoomsResponse.class);
        assertThat(actual.getStatusCode(), is(HttpStatus.OK));
    }

}
