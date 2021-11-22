package java.com.revature.app.services;

import com.revature.app.daos.UserDAO;
import com.revature.app.exceptions.InvalidRequestException;
import com.revature.app.exceptions.ResourcePersistenceException;
import com.revature.app.models.User;
import com.revature.app.services.UserService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class UserServiceTestSuite {
    UserService sut;
    UserDAO mockUserDAO;

    @Before
    public void testCaseSetup() {
        mockUserDAO = mock(UserDAO.class);
        sut = new UserService(mockUserDAO);
    }

    @After
    public void testCaseCleanUp() {
        sut = null;
    }

    @Test
    public void test_isUserValid_returnsTrue_givenValidUser() {

        // AAA pattern: Arrange, Act, Assert

        // Arrange
        User validUser = new User("valid", "valid", "valid", "valid", "valid", "valid");

        // Act
        boolean actualResult = sut.isUserValid(validUser);

        // Assert
        Assert.assertTrue("Expected user to be considered valid", actualResult);
    }

    @Test
    public void test_registerNewUser_returnsTrue_givenValidUser() {

        // Arrange
        User validUser = new User("valid", "valid", "valid", "valid", "valid", "valid");
        when(mockUserDAO.findByEmail(validUser.getEmail())).thenReturn(null);
        when(mockUserDAO.save(validUser)).thenReturn(validUser);

        // Act
        boolean actualResult = sut.registerNewUser(validUser);

        // Assert
        Assert.assertTrue("Expected result to be true with valid user provided.", actualResult);
        verify(mockUserDAO, times(1)).save(validUser);
    }

    @Test(expected = ResourcePersistenceException.class)
    public void test_registerNewUser_throwsResourcePersistenceException_givenValidUserWithTakenUsername() {

        // Arrange
        User validUser = new User("valid", "valid", "valid", "valid", "valid", "valid");
        when(mockUserDAO.findByEmail(validUser.getEmail())).thenReturn(null);
        when(mockUserDAO.save(validUser)).thenReturn(validUser);

        // Act
        try {
            boolean actualResult = sut.registerNewUser(validUser);
        } finally {
            // Assert
            verify(mockUserDAO, times(0)).save(validUser);
        }
    }

    @Test(expected = InvalidRequestException.class)
    public void test_registerNewUser_throwsInvalidRequestException_givenInvalidUser() {
        sut.registerNewUser(null);
    }

    @Test
    public void testGetSessionUser() {
    }

    @Test
    public void testAuthenticateUser() {
        // Arrange
        User validUser = new User("valid", "valid", "valid", "valid", "valid", "valid");
        when(mockUserDAO.findByEmailAndPassword(validUser.getEmail(), validUser.getPassword())).thenReturn(null);
        when(mockUserDAO.save(validUser)).thenReturn(validUser);

        // Act
        boolean actualResult = sut.registerNewUser(validUser);

        // Assert
        Assert.assertTrue("Expected result to be true with valid user provided.", actualResult);
        verify(mockUserDAO, times(1)).save(validUser);
    }

    @Test
    public void testRegisterNewUser() {
        // Arrange
        User validUser = new User("valid", "valid", "valid", "valid", "valid", "valid");
        when(mockUserDAO.findByEmail(validUser.getEmail())).thenReturn(null);
        when(mockUserDAO.save(validUser)).thenReturn(validUser);

        // Act
        boolean actualResult = sut.registerNewUser(validUser);

        // Assert
        Assert.assertTrue("Expected result to be true with valid user provided.", actualResult);
        verify(mockUserDAO, times(1)).save(validUser);
    }
}
