package com.alirismaurera.salasdereunioes.controller;


import com.alirismaurera.salasdereunioes.exception.ResourceNotFoundException;
import com.alirismaurera.salasdereunioes.model.Room;
import com.alirismaurera.salasdereunioes.repository.RoomRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @GetMapping("/rooms")
    public List<Room> getAllRooms(){
        return roomRepository.findAll();
    }

    @GetMapping("/rooms/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable(value = "id") long id) throws ResourceNotFoundException {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found: " + id));
        return ResponseEntity.ok().body(room);
    }

    @PostMapping("/rooms")
    public Room createRoom(@Valid @RequestBody Room room){
        return roomRepository.save(room);
    }

    @PutMapping("/rooms/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable(value = "id") long id, @RequestBody @Valid Room room) throws ResourceNotFoundException{
        Room room1 = roomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room not found: " + id));
        room1.setName(room.getName());
        room1.setDate(room.getDate());
        room1.setStartHour(room.getStartHour());
        room1.setEndHour(room.getEndHour());

        final Room updateroom = roomRepository.save(room1);
        return ResponseEntity.ok(updateroom);
    }

    @DeleteMapping("/rooms/{id}")
    public Map<String , Boolean> deleteRoom(@PathVariable(value = "id") long id) throws ResourceNotFoundException{
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found: " + id));
        roomRepository.delete(room);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);
        return response;
    }
}
