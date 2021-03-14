package hu.jnagy.roomservice.controller;

import hu.jnagy.roomservice.localexception.RecourseNotFoundException;
import hu.jnagy.roomservice.model.Room;
import hu.jnagy.roomservice.responsetype.ErrorResponse;
import hu.jnagy.roomservice.responsetype.RoomResponse;
import hu.jnagy.roomservice.responsetype.RoomsResponse;
import hu.jnagy.roomservice.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/rooms")
    public ResponseEntity<RoomsResponse> getRooms() {
        List<Room> allRoom = roomService.getAllRoom();
        RoomsResponse body = new RoomsResponse.Builder().withRooms(allRoom).build();
        if (allRoom.isEmpty()) {
            throw new RecourseNotFoundException("The room not available!");
        } else {
            return ResponseEntity.ok(body);
        }
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<RoomResponse> getRoom(@PathVariable Long roomId) {
        Room roomById = roomService.getRoomById(roomId);
        RoomResponse response = new RoomResponse.Builder().withRoom(roomById).build();
        if (roomById == null) {
            throw new RecourseNotFoundException("The room not available!");
        } else {
            return ResponseEntity.ok(response);
        }
    }

    @PutMapping("/room")
    public ResponseEntity<RoomResponse> createRoom(@RequestParam Long roomId, double unitPrice) {
        Room room = roomService.createRoom(roomId, unitPrice);
        RoomResponse response = new RoomResponse.Builder().withRoom(room).build();
        if(room == null) {
            throw new RecourseNotFoundException("The room not created");
        } else {
            return ResponseEntity.ok(response);
        }

    }

    @DeleteMapping("/room/{roomId}")
    public void deleteRoom(@PathVariable Long roomId) {
        roomService.deleteRoomById(roomId);
    }

    @ExceptionHandler({RecourseNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleException(RecourseNotFoundException e) {
        ErrorResponse response =  new ErrorResponse.Builder().withCause(e.getMessage()).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
