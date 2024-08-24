package br.com.fiap.wavemail.controllers;

import br.com.fiap.wavemail.domain.dto.user.UserAddDto;
import br.com.fiap.wavemail.domain.dto.user.UserReturnDto;
import br.com.fiap.wavemail.domain.service.UserService;
import io.github.bucket4j.Bucket;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private Bucket bucket;


    @GetMapping("/find")
    public ResponseEntity<UserReturnDto> findUserById(@RequestParam("id") UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(id));
    }

    @GetMapping
    public ResponseEntity<Page<UserReturnDto>> findAllUsers(Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllusers(pageable));
    }

    @PutMapping
    public ResponseEntity<UserReturnDto> updateUser(@RequestParam("id") UUID id,
                                                    @RequestBody @Valid UserAddDto user){

        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(id, user));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestParam("id") UUID id){
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
