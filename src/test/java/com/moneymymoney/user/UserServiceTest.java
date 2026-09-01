package com.moneymymoney.user;

import static org.mockito.ArgumentMatchers.any;

import com.moneymymoney.card.CardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private CardRepository cardRepository;
    @InjectMocks
    private UserService userService;

    @Test
    void saveUser_saveUserInBD_UserInBD() {
        User testUser = new User();
        testUser.setEmail("test@mail.ru");


        //мокать можно только репозитории
        when(userRepository.save(testUser)).thenReturn(testUser);

        //вызываем сервис
        User checkUser = userService.saveUser(testUser);

        assertEquals(checkUser,testUser);
        verify(userRepository).save(testUser);
    }

    @Test
    void getAllUsers_arrayOfUsers_allUsers() {

        User testUserOne = new User();
        testUserOne.setEmail("one@mail.ru");
        User testUserTwo = new User();
        testUserTwo.setEmail("two@mail.ru");
        User testUserThree = new User();
        testUserThree.setEmail("three@mail.ru");

        List<User> arraysOfUser = List.of(testUserOne, testUserTwo,testUserThree);


        //мокаем репозиторий
        when(userRepository.findAll()).thenReturn(arraysOfUser);

        //дальше из сервиса вызываем найденное
        List<User> checkUsers = userService.getAllUsers();

        assertEquals(checkUsers,arraysOfUser);
        verify(userRepository).findAll();


    }

    @Test
    void register_registerOfEveryUser_registerOfUser() {
        DTO testUser = new DTO();

testUser.setName("Юлия");
testUser.setEmail("julia@mail.ru");
testUser.setLogin("12345");
testUser.setPassword("11111");

User saveUser = new User();
saveUser.setId(1L);
saveUser.setName(testUser.getName());
saveUser.setEmail(testUser.getEmail());
saveUser.setLogin(testUser.getLogin());
saveUser.setPassword(testUser.getPassword());



        //мок репозитория
        when(userRepository.save(any(User.class))).thenReturn(saveUser);

        User checkUser = userService.register(testUser);

        assertEquals(checkUser.getName(), testUser.getName());
        verify(userRepository).save(any(User.class));
    }
}
