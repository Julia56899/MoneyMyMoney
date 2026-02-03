package com.moneymymoney.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/add")
    //добавить пользователя
    public User addUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping("/all")
    //получить всех пользователей
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    @GetMapping("/{email}")
    //найти по емайл
    public User getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @PostMapping("/register")
    public UserResponseDTO registerWithCard(@RequestBody DTO registrationDTO) {
        System.out.println("=== REGISTER CONTROLLER CALLED ===");
        User user = userService.register(registrationDTO);

        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getLogin()
        );
    }
}
