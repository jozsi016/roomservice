package hu.jnagy.roomservice.controller;

import hu.jnagy.roomservice.model.Room;
import hu.jnagy.roomservice.responsetype.RoomResponse;
import hu.jnagy.roomservice.responsetype.RoomsResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RoomControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldReturnRoom() {
        //given
        Room expected = new Room(3, 5000);
        //when
        ResponseEntity<RoomResponse> room = this.restTemplate.getForEntity("http://localhost:" + port + "/room/3", RoomResponse.class);
        Room actual = room.getBody().getRoom();
        //then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnRooms() {
        //given
        Room expected = new Room(1, 5000);
        //when
        ResponseEntity<RoomsResponse> rooms = this.restTemplate.getForEntity("http://localhost:" + port + "/rooms", RoomsResponse.class);
        //then
        Room actual = rooms.getBody().getRooms().get(0);
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldCreateRoom() {
        //given
        Room expected = new Room(31, 4000);
        //when
        this.restTemplate.put("http://localhost:" + port + "/room?roomId=31&unitPrice=4000", Long.class, Double.class);
        //then
        ResponseEntity<RoomsResponse> room = this.restTemplate.getForEntity("http://localhost:" + port + "/rooms", RoomsResponse.class);
        Room actual = room.getBody().getRooms().get(30);
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldDeleteRoomById() {
        //given
        Room deleted = new Room(1, 5000);
        Room expected = new Room(2, 5000);
        //when
        this.restTemplate.delete("http://localhost:" + port + "/room/1");
        //then
        ResponseEntity<RoomsResponse> actual = restTemplate.getForEntity("http://localhost:" + port + "/rooms", RoomsResponse.class);
        assertThat(actual.getStatusCode(), is(HttpStatus.OK));
        assertThat(actual.getBody().getRooms(), hasItem(expected));
        assertThat(actual.getBody().getRooms(), not(hasItem(deleted)));
    }

}

