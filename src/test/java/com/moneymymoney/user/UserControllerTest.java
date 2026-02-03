package com.moneymymoney.user;
// JUnit
import org.junit.jupiter.api.Test;

// Spring MVC Test
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

// Collections
import java.util.List;

// Mockito
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

// MockMvc builders + matchers
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;



@WebMvcTest(UserController.class)
public class UserControllerTest {
    //здесь мокаем сервисы
    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc; //для отправки HTTP запросов


    @Test
    void addUser_WhenValidUser_addUserInBD() throws Exception {

        User testUser = new User();   //айди, нейм, емайл, логин, пассворд
        testUser.setId(1L);
        testUser.setName("Юлия");
        testUser.setEmail("julia@mail.ru");
        testUser.setLogin("12345");
        testUser.setPassword("11111");

        //мокаем  юзерсервис
        when(userService.saveUser(any(User.class))).thenReturn(testUser);
        //вызываю эндпоинт
//проверяю ашттп статус и джсон ответа
        mockMvc.perform(
                post("/users/add")
                .contentType(MediaType.APPLICATION_JSON) //отправляю ашттп
                .content("{\"name\":\"Юлия\",\"email\":\"julia@mail.ru\",\"login\":\"12345\",\"password\":\"11111\"}"))
                //дальше проверки
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("julia@mail.ru"))
                .andExpect(jsonPath("$.name").value("Юлия"));

        //проверка вызова сервиса
        verify(userService).saveUser(any(User.class));

    }

    @Test
    void getAllUsers_arrayOfAllUsers_allUsers() throws  Exception {
        //создаем два юзера
        User testUserOne = new User();
        testUserOne.setId(1L);
        testUserOne.setName("Лиза");

        User testUserTwo = new User();
        testUserTwo.setId(2L);
        testUserTwo.setName("Женя");

        List<User> testUsers = List.of(testUserOne,testUserTwo);


        //мокаем юзерсервис

        when(userService.getAllUsers()).thenReturn(testUsers);

        //вызываю эндпоинт и чекаю ашттп статус и  джсон ответа
        mockMvc.perform(get("/users/all"))


                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Лиза"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Женя"));

        verify(userService).getAllUsers();


    }

    @Test
    void getUserByEmail_getEmailAndFindUser_foundUserByEmail() throws Exception {
        User testUser = new User();
        testUser.setId(1L);
        testUser.setName("Юлия");//айди, нейм, емайл, логин, пассворд
        testUser.setEmail("test@mail.ru");
        testUser.setLogin("12345");
        testUser.setPassword("11111");

        //мокаем сервис
        when(userService.getUserByEmail("test@mail.ru")).thenReturn(testUser);

        //вызываю эндпоинт
        mockMvc.perform(get("/users/test@mail.ru"))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@mail.ru"))
                .andExpect(jsonPath("$.id").exists());

        verify(userService).getUserByEmail("test@mail.ru");


    }

    @Test
    void registerWithCard_takeDTOOfUserAndRegister_registerOfUser() throws  Exception {
//дто вводим
        DTO testDTO = new DTO();
        testDTO.setName("Юлия");
        testDTO.setEmail("julia@mail.ru");
        testDTO.setLogin("12345");
        testDTO.setPassword("11111");
        testDTO.setCardNumber("1234 5678 9001 8990");
        testDTO.setCardType("MIR");

        //делаем юзера который сервис превратит в юзер
        User testUser = new User();
        testUser.setId(1L);
        testUser.setName("Юлия");
        testUser.setEmail("julia@mail.ru");
        testUser.setLogin("12345");


//мокаем сервис
        when(userService.register(any(DTO.class))).thenReturn(testUser);

//вызываю эндпоинт
        mockMvc.perform(
                post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Юлия\",\"email\":\"julia@mail.ru\",\"login\":\"12345\"}"))


                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Юлия"))
                .andExpect(jsonPath("$.email").value("julia@mail.ru"))
        .andExpect(jsonPath("$.login").value("12345"));

        verify(userService).register(any(DTO.class));


    }

}
