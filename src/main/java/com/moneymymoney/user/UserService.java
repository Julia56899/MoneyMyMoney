package com.moneymymoney.user;

import com.moneymymoney.card.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UserService  {
    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserService(UserRepository userRepository, CardRepository cardRepository) {
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
    }

    //необходимые доп функции к сервису
    // Добавить нового пользователя
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // Получить всех пользователей
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Найти пользователя по email
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    //дальше сервис должен принимать регистарционную форму дто, создавать уже пользователя и кард, связывать карту с пользователем  и сохранять все в бд
//регистрация через дто
    @Transactional
    public User register(DTO dto) {
        System.out.println("===Register started===");
        System.out.println("Name:" + dto.getName());
        System.out.println("Email:" + dto.getEmail());
        System.out.println("Card:" + dto.getCardNumber());
//создаем базу пользователя
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setLogin(dto.getLogin());

//шифруем пароль
        System.out.println("===PASSWORD ENCODING===");
        System.out.println("Original password:"+ dto.getPassword());
        String encoded = passwordEncoder.encode(dto.getPassword());
        System.out.println("Encoded password:" + encoded);
        user.setPassword(encoded);

        System.out.println("Saving user...");
        //сохраняем его
        User saveUser = userRepository.save(user);
        System.out.println("Saved user with ID:" + saveUser.getId());
        //создаем карту
        if (dto.getCardNumber() != null && !dto.getCardNumber().isEmpty()) {
            System.out.println("Creating card...");
            com.moneymymoney.card.Card card = new com.moneymymoney.card.Card();

            card.setCardNumber(dto.getCardNumber());
            card.setBalance(0.0);
            card.setCardType(dto.getCardType());
            card.setUser(saveUser);

            cardRepository.save(card);
            System.out.println("Card created");
        }
        System.out.println("registration the end");
        return saveUser;
    }
}
