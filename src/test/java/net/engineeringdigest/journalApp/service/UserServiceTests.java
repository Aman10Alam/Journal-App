package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.respository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Disabled
    @ParameterizedTest
    @ArgumentsSource(UserArgumentsProvider.class)
    public void testAdd(User user){
        assertTrue(userService.saveNewUser(user));
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,10,12",
            "3,5,9"
    })
    @Disabled
    public void test(int a,int b,int expected){
        assertEquals(expected,a+b);
    }
}
