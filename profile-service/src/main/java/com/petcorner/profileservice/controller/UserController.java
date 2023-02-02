package com.petcorner.profileservice.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.JsonObject;
import com.petcorner.profileservice.exception.ResourceNotFoundException;
import com.petcorner.profileservice.feignproxy.AdoptServiceProxy;
import com.petcorner.profileservice.model.Role;
import com.petcorner.profileservice.model.User;
import com.petcorner.profileservice.queue.CustomMessage;
import com.petcorner.profileservice.queue.MQConfig;
import com.petcorner.profileservice.repository.UserRepo;
import com.petcorner.profileservice.security.CurrentUser;
import com.petcorner.profileservice.security.UserPrincipal;
import com.petcorner.profileservice.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@CrossOrigin
@RestController
@RequestMapping("/profile/v2")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    @Autowired
    private RabbitTemplate template;

    private final UserService userService;
    @Autowired
    private AdoptServiceProxy proxy;
    private UserRepo userRepository;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/user/save")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("api/v1/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    @PostMapping("/role/addtouser")
    public ResponseEntity<Role> addRoleToUser(@RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = userService.getUser(username);
                String[] roles = decodedJWT.getClaim("roles").asArray(String.class);

                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 15 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            } catch (Exception e) {
                log.error("Error loggin in: {}", e.getMessage());
                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);

            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

//    @PostMapping("/animal/addtouser")
//    public String addAnimalToUser(@RequestBody String request) throws JSONException {
//        log.info(request);
//        JSONObject jsonObject = new JSONObject(request);
//        log.info(jsonObject.toString());
//
////        log.info(requestEntity.getBody().toString());
////        return requestEntity.getBody().toString();
////        HashMap<String, String> uriVariables = new HashMap<>();
////        uriVariables.put("from",from);
////        uriVariables.put("to",to);
////
//        ResponseEntity<JSONObject> responseEntity = new RestTemplate().getForEntity
//                ("http://localhost:8000/api/v1/animals/add",
//                        JSONObject.class, jsonObject);
//
//        return jsonObject.toString();
//
//    }


    @GetMapping("/user-info/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {

        return ResponseEntity.ok().body(userService.getUser(username));
    }


//    @GetMapping("/user-animals/{username}")
//    public ResponseEntity<List<JsonObject>> getAnimalsByUser(@PathVariable String username) {
//        CustomMessage message = new CustomMessage();
//        message.setData(username);
//        message.setMessage("Get animals for user: "+username);
//        message.setMessageId(UUID.randomUUID().toString());
//        message.setMessageDate(new Date());
//        template.convertAndSend(MQConfig.EXCHANGE,
//                MQConfig.ROUTING_KEY, message);
//        System.out.println(message.getData());
//
//
//        return ResponseEntity.ok().body(userService.getUser(username));
//    }
    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }
    @GetMapping("/hello")
    public String getHello() {
        return "Hello";
    }


    @PostMapping("/animal/add-animal")
    public String addAnimal(@RequestBody Object animal, @RequestHeader (HttpHeaders.AUTHORIZATION) String token) throws JSONException {
        return proxy.addAnimal(animal, token);
        }

    @PostMapping("/animal/add-animal-queue")
    public String addAnimalToUser(@RequestBody Object animal) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(animal);
        CustomMessage message = new CustomMessage();
        message.setData(json);
        message.setMessage("Create animal");
        message.setMessageId(UUID.randomUUID().toString());
        message.setMessageDate(new Date());
        template.convertAndSend(MQConfig.EXCHANGE,
                MQConfig.ROUTING_KEY, message);
        System.out.println(message.getData());

        return "Animal Sent";
    }

    @PostMapping("/animal/delete-animal-queue")
    public String removeAnimalOfUser(@RequestBody Object animal) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(animal);
        CustomMessage message = new CustomMessage();
        message.setData(json);
        message.setMessage("Delete animal");
        message.setMessageId(UUID.randomUUID().toString());
        message.setMessageDate(new Date());
        template.convertAndSend(MQConfig.EXCHANGE,
                MQConfig.ROUTING_KEY, message);
        System.out.println(message.getData());

        return "Animal Sent";
    }



//    @RabbitListener(queues = MQConfig.QUEUE)
//    public void listener(CustomMessage message) {
//        System.out.println(message);
//
//    }



}
@Data
class RoleToUserForm {
    private String username;
    private String roleName;
}