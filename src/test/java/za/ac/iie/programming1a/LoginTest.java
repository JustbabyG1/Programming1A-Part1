package za.ac.iie.programming1a;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    @Test
    void testUsernameCorrectlyFormatted() {

        Login user;
        user = new Login(
                "kyl_1",
                "Ch&sec@ke99!",
                "+27838968976",
                "John",
                "Smith"
        );

        assertTrue(user.checkUserName());
    }

    @Test
    void testUsernameIncorrectlyFormatted() {

        Login user = new Login(
                "kyle!!!!!!",
                "Ch&sec@ke99!",
                "+27838968976",
                "John",
                "Smith"
        );

        assertFalse(user.checkUserName());
    }

    @Test
    void testPasswordMeetsComplexityRequirements() {

        Login user = new Login(
                "kyl_1",
                "Ch&sec@ke99!",
                "+27838968976",
                "John",
                "Smith"
        );

        assertTrue(user.checkPasswordComplexity());
    }

    @Test
    void testPasswordDoesNotMeetComplexityRequirements() {

        Login user = new Login(
                "kyl_1",
                "password",
                "+27838968976",
                "John",
                "Smith"
        );

        assertFalse(user.checkPasswordComplexity());
    }

    @Test
    void testCellPhoneCorrectlyFormatted() {

        Login user = new Login(
                "kyl_1",
                "Ch&sec@ke99!",
                "+27838968976",
                "John",
                "Smith"
        );

        assertTrue(user.checkCellPhoneNumber());
    }

    @Test
    void testCellPhoneIncorrectlyFormatted() {

        Login user = new Login(
                "kyl_1",
                "Ch&sec@ke99!",
                "08966553",
                "John",
                "Smith"
        );

        assertFalse(user.checkCellPhoneNumber());
    }

    @Test
    void testLoginSuccessful() {

        Login user = new Login(
                "kyl_1",
                "Ch&sec@ke99!",
                "+27838968976",
                "John",
                "Smith"
        );

        assertTrue(user.loginUser("kyl_1", "Ch&sec@ke99!"));
    }

    @Test
    void testLoginFailed() {

        Login user = new Login(
                "kyl_1",
                "Ch&sec@ke99!",
                "+27838968976",
                "John",
                "Smith"
        );

        assertFalse(user.loginUser("wrong", "wrong"));
    }

    @Test
    void testSuccessfulLoginMessage() {

        Login user = new Login(
                "kyl_1",
                "Ch&sec@ke99!",
                "+27838968976",
                "John",
                "Smith"
        );

        assertEquals(
                "Welcome John Smith, it is great to see you again.",
                user.returnLoginStatus(true)
        );
    }

    @Test
    void testFailedLoginMessage() {

        Login user = new Login(
                "kyl_1",
                "Ch&sec@ke99!",
                "+27838968976",
                "John",
                "Smith"
        );

        assertEquals(
                "Username or password incorrect, please try again.",
                user.returnLoginStatus(false)
        );
    }
}