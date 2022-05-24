package java.com.revature.app.services;

import com.revature.app.daos.CourseDAO;
import com.revature.app.daos.UserDAO;
import com.revature.app.exceptions.InvalidRequestException;
import com.revature.app.exceptions.ResourcePersistenceException;
import com.revature.app.models.User;
import com.revature.app.services.CourseService;
import com.revature.app.services.UserService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class CourseServiceTestSuite {
    UserService sut;
    CourseService sct;
    CourseDAO mockCourseDAO;
    UserDAO mockUserDAO;

    @Before
    public void testCaseSetup() {
        mockCourseDAO = mock(CourseDAO.class);
        mockUserDAO = mock(UserDAO.class);
        sut = new UserService(mockUserDAO);
        sct = new CourseService(mockCourseDAO, sut);
    }

    @After
    public void testCaseCleanUp() {
        sut = null;
        sct = null;
    }

    @Test
    public void testGetAllCourses() throws ResourcePersistenceException {
        sct.findAllCourses();
        verify(mockCourseDAO, times(1)).findAllCourses();
    }

    @Test
    public void testGetCourseById() throws ResourcePersistenceException {
        sct.findMyCourses();
        verify(mockCourseDAO, times(1)).findAllCourses();
    }

    @Test
    public void testGetCourseByUserId() throws ResourcePersistenceException {
        sct.findUnregisteredCourses();
        verify(mockCourseDAO, times(1)).findRegistrationsByUserId("1");
    }
}
