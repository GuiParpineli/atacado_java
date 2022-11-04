package com.wf.apiwf.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wf.apiwf.entity.SystemUser;
import com.wf.apiwf.entity.dto.UserDTO;
import com.wf.apiwf.exceptions.ResourceNotFoundException;
import com.wf.apiwf.exceptions.UserLoginException;
import com.wf.apiwf.security.JwtUtil;
import com.wf.apiwf.service.impl.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService service;

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    private static final Logger log = Logger.getLogger(UserController.class);

    @Autowired
    public UserController(UserService service, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.service = service;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticaitionToken(@RequestBody SystemUser systemUser) throws Exception {
        try {
            log.info("tentando efetuar login try " + systemUser.getUsername());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(systemUser.getUsername(), systemUser.getPassword()));
        } catch (BadCredentialsException e) {
            throw new UserLoginException("Usuario ou senha Incorretos");
        }

        final UserDetails userDetails = service.loadUserByUsername(systemUser.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new SystemUser((jwt)).getJwt());
    }

    @GetMapping
    public ResponseEntity obterTodos() throws ResourceNotFoundException {
        List<SystemUser> systemUserSalvo;
        List<UserDTO> userDTOS = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            systemUserSalvo = service.getAll();
            for (SystemUser s : systemUserSalvo) {
                userDTOS.add(mapper.convertValue(s, UserDTO.class));
            }

        } catch (Exception e) {
            throw new ResourceNotFoundException("Nenhum usuario cadastrado");
        }
        return new ResponseEntity(userDTOS, HttpStatus.OK);
    }

    @GetMapping("/buscarId")
    public ResponseEntity obterUserPorId(@RequestParam Long id) {
        ObjectMapper mapper = new ObjectMapper();
        Optional<SystemUser> user = service.get(id);
        if (user.isEmpty()) {
            return new ResponseEntity("Usuario não cadastrado", HttpStatus.NOT_FOUND);
        }
        UserDTO userDTO = mapper.convertValue(user, UserDTO.class);
        return new ResponseEntity(userDTO, HttpStatus.OK);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity cadastrarUser(@RequestBody SystemUser systemUser) {
        SystemUser systemUserSave = service.save(systemUser);
        if (systemUserSave == null) {
            return new ResponseEntity("Usuario ja cadastrado", HttpStatus.BAD_REQUEST);
        }
        log.info("Usuario" + systemUser.getName() + " cadastrado com sucesso");
        return new ResponseEntity(systemUserSave, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity removerUser(@PathParam("id") Long id) throws ResourceNotFoundException {
        try {
            service.delete(id);
            return ResponseEntity.ok("Deletado");
        } catch (Exception e) {
            throw new ResourceNotFoundException("Nenhum usuario com o id informado");
        }
    }

    @GetMapping("/teste")
    public String home() {
        return "<h1> Welcome </h2>";
    }

}
