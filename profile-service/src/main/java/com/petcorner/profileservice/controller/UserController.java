package com.petcorner.profileservice.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
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
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


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



    @GetMapping("/user-info/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok().body(userService.getUser(username));
    }


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



    @PostMapping("/animalTherapy/add-animalTherapy")
    public String addAnimalTherapy(@RequestBody Object animalTherapy, @RequestHeader (HttpHeaders.AUTHORIZATION) String token) throws JSONException {
        return proxy.addAnimalTherapy(animalTherapy, token);
    }

    @PostMapping("/animalTherapy/add-animalTherapy-queue")
    public String addAnimalTherapyToUser(@RequestBody Object animalTherapy) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(animalTherapy);
        CustomMessage message = new CustomMessage();
        message.setData(json);
        message.setMessage("Create animal therapy");
        message.setMessageId(UUID.randomUUID().toString());
        message.setMessageDate(new Date());
        template.convertAndSend("pet-therapy",
                MQConfig.ROUTING_KEY, message);
        System.out.println(message.getData());

        return "Animal therapy Sent";
    }

    @PostMapping("/animalTherapy/delete-animalTherapy-queue")
    public String removeAnimalTherapyOfUser(@RequestBody Object animalTherapy) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(animalTherapy);
        CustomMessage message = new CustomMessage();
        message.setData(json);
        message.setMessage("Delete animal therapy");
        message.setMessageId(UUID.randomUUID().toString());
        message.setMessageDate(new Date());
        template.convertAndSend("pet-therapy",
                MQConfig.ROUTING_KEY, message);
        System.out.println(message.getData());

        return "Animal therapy Sent";
    }

    @PostMapping("/animalTrainer/add-animalTrainer")
    public String addAnimalTrainer(@RequestBody Object animalTrainer, @RequestHeader (HttpHeaders.AUTHORIZATION) String token) throws JSONException {
        return proxy.addAnimalTrainer(animalTrainer, token);
    }

    @PostMapping("/animalTrainer/add-animalTrainer-queue")
    public String addAnimalTrainerToUser(@RequestBody Object animalTrainer) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(animalTrainer);
        CustomMessage message = new CustomMessage();
        message.setData(json);
        message.setMessage("Create animal trainer");
        message.setMessageId(UUID.randomUUID().toString());
        message.setMessageDate(new Date());
        template.convertAndSend("pet-trainer",
                MQConfig.ROUTING_KEY, message);
        System.out.println(message.getData());

        return "Animal trainer Sent";
    }

    @PostMapping("/animalTrainer/delete-animalTrainer-queue")
    public String removeAnimalTrainerOfUser(@RequestBody Object animalTrainer) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(animalTrainer);
        CustomMessage message = new CustomMessage();
        message.setData(json);
        message.setMessage("Delete animal trainer");
        message.setMessageId(UUID.randomUUID().toString());
        message.setMessageDate(new Date());
        template.convertAndSend("pet-trainer",
                MQConfig.ROUTING_KEY, message);
        System.out.println(message.getData());

        return "Animal trainer Sent";
    }

    @PostMapping("/animalSitter/add-animalSitter")
    public String addAnimalSitter(@RequestBody Object animalSitter, @RequestHeader (HttpHeaders.AUTHORIZATION) String token) throws JSONException {
        return proxy.addAnimalSitter(animalSitter, token);
    }

    @PostMapping("/animalSitter/add-animalSitter-queue")
    public String addAnimalSitterToUser(@RequestBody Object animalSitter) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(animalSitter);
        CustomMessage message = new CustomMessage();
        message.setData(json);
        message.setMessage("Create animal sitter");
        message.setMessageId(UUID.randomUUID().toString());
        message.setMessageDate(new Date());
        template.convertAndSend("pet-sitter",
                MQConfig.ROUTING_KEY, message);
        System.out.println(message.getData());

        return "Animal sitter Sent";
    }

    @PostMapping("/animalSitter/delete-animalSitter-queue")
    public String removeAnimalSitterOfUser(@RequestBody Object animalSitter) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(animalSitter);
        CustomMessage message = new CustomMessage();
        message.setData(json);
        message.setMessage("Delete animal sitter");
        message.setMessageId(UUID.randomUUID().toString());
        message.setMessageDate(new Date());
        template.convertAndSend("pet-sitter",
                MQConfig.ROUTING_KEY, message);
        System.out.println(message.getData());

        return "Animal sitter Sent";
    }


    @PostMapping("/animal/add-animal-adopt-queue")
    public String addAnimalADopt(
            @RequestParam("file") MultipartFile file,
          @RequestParam("age") String age,@RequestParam("description") String descr,
          @RequestParam("owner") String owner,@RequestParam("microchip") String chip,
          @RequestParam("name") String name,@RequestParam("sex") String sex,
          @RequestParam("size") String size, @RequestParam("provenance") String prov,
            @RequestParam("type") String type
        ) throws IOException, JSONException {

        String image = Base64.getEncoder().encodeToString(file.getBytes());
        JSONObject animal = new JSONObject();
        animal.put("image", image);
        animal.put("age",age);
        animal.put("owner",owner);
        animal.put("size",size);
        animal.put("type",type);
        animal.put("description",descr);
        animal.put("microchip", chip);
        animal.put("sex", sex);
        animal.put("provenance", prov);
        animal.put("name", name);

        CustomMessage message = new CustomMessage();
        message.setData(animal.toString());
        message.setMessage("Create animal");
        message.setMessageId(UUID.randomUUID().toString());
        message.setMessageDate(new Date());
        template.convertAndSend(MQConfig.EXCHANGE,
                MQConfig.ROUTING_KEY, message);
        System.out.println(message.getData());

        return "Animal Sent";
    }


    @PostMapping("/animal/delete-animal-adopt-queue")
    public String deleteAnimalAdopt(@RequestParam("param") String param) throws IOException, JSONException {

        JSONObject animal = new JSONObject();
        if(param.contains("@")){
            animal.put("owner", param);
        }else {
            animal.put("microchip", param);
        }



        CustomMessage message = new CustomMessage();
        message.setData(animal.toString());
        message.setMessage("Delete animal");
        message.setMessageId(UUID.randomUUID().toString());
        message.setMessageDate(new Date());
        template.convertAndSend(MQConfig.EXCHANGE,
                MQConfig.ROUTING_KEY, message);
        System.out.println(message.getData());

        return "Animal Sent";
    }


    @PostMapping("/user/save/add-user-sitter-queue")
    public String addUserSitter(
            @RequestParam("image") MultipartFile image,
            @RequestParam("age") String age,@RequestParam("name") String name,
            @RequestParam("surname") String surname,@RequestParam("locality") String locality,
            @RequestParam("email") String email,@RequestParam("personalDescription") String personalDescription,
            @RequestParam("sizeAllowed") int size, @RequestParam("animalsAllowed") String animalsAllowed,
            @RequestParam("serviceOffered") String serviceOffered
    ) throws IOException, JSONException {

        String imageByte = Base64.getEncoder().encodeToString(image.getBytes());
        JSONObject user = new JSONObject();
        user.put("image", imageByte);
        user.put("age",age);
        user.put("name",name);
        user.put("surname",surname);
        user.put("locality",locality);
        user.put("email",email);
        user.put("personalDescription", personalDescription);
        user.put("serviceOffered", serviceOffered);
        user.put("sizeAllowed", size);
        user.put("animalsAllowed", animalsAllowed);
        CustomMessage message = new CustomMessage();
        message.setData(user.toString());
        message.setMessage("Create animal sitter");
        message.setMessageId(UUID.randomUUID().toString());
        message.setMessageDate(new Date());
        template.convertAndSend("pet-sitter",
                MQConfig.ROUTING_KEY, message);
        System.out.println(message.getData());
        return "Sitter sent";

    }


    @PostMapping("/user/delete/delete-user-sitter-queue")
    public String deleteUserSitter(@RequestParam("email") String email) throws IOException, JSONException {

        JSONObject user = new JSONObject();
        user.put("email",email);


        CustomMessage message = new CustomMessage();
        message.setData(user.toString());
        message.setMessage("Delete animal Sitter");
        message.setMessageId(UUID.randomUUID().toString());
        message.setMessageDate(new Date());
        template.convertAndSend("pet-sitter",
                MQConfig.ROUTING_KEY, message);
        System.out.println(message.getData());

        return "sitter Sent";
    }



    @PostMapping("/user/save/add-user")
    public ResponseEntity<User> addUserV2(
            @RequestParam("image") String file,
            @RequestParam("address") String address,@RequestParam("name") String name,@RequestParam("username") String username,
            @RequestParam("city") String city,@RequestParam("providerId") String providerId,
            @RequestParam("country") String country,@RequestParam("provider") String provider,
            @RequestParam("piva") String piva, @RequestParam("password") String password,
            @RequestParam("cod_fisc") String cod_fisc
    ) throws IOException, JSONException {

//        String image = Base64.getEncoder().encodeToString(file.getBytes());
        JSONObject user = new JSONObject();
        user.put("image", file);
        user.put("address",address);
        user.put("city",city);
        user.put("country",country);
        user.put("piva",piva);
        user.put("cod_fisc",cod_fisc);
        user.put("name", name);
        user.put("username", username);
        user.put("providerId", providerId);
        user.put("provider", provider);
        user.put("password", password);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("api/v1/user/save").toUriString());
        User newUser = new ObjectMapper().readValue(user.toString(), User.class);
        return ResponseEntity.created(uri).body(userService.saveUser(newUser));

    }







}
@Data
class RoleToUserForm {
    private String username;
    private String roleName;
}