package com.jwtme;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jwtme.model.Role;
import com.jwtme.model.User;
import com.jwtme.repository.RoleRepository;
import com.jwtme.repository.UserRepository;
import com.jwtme.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserServiceTest {

    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private RoleRepository mockRoleRepository;
    @Mock
    private BCryptPasswordEncoder mockBCryptPasswordEncoder;

    private UserService userServiceUnderTest;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private User user;

    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() {
        initMocks(this);
        userServiceUnderTest = new UserService(mockUserRepository,
                                               mockRoleRepository,
                                               mockBCryptPasswordEncoder);
//        user = User.builder()
//                .id(1)
//                .name("Gustavo")
//                .lastName("Ponce")
//                .email("test@test.com")
//                .build();
        
        user = new User();
        user.setId(1);
        user.setName("Gustavo");
        user.setLastName("Ponce");
        user.setEmail("test@test.com");

        Mockito.when(mockUserRepository.save(any()))
                .thenReturn(user);
        Mockito.when(mockUserRepository.findByEmail(anyString()))
                .thenReturn(user);
    }
    
    @Test
    public void inserir() {
    	userRepository.save(popularUser());
    }

    @Test
    public void inserir2() {
    	userService.saveUser(popularUser());
    }

    @Test
    public void testFindUserByEmail() {
        // Setup
        final String email = "test@test.com";

        // Run the test
        final User result = userServiceUnderTest.findUserByEmail(email);

        // Verify the results
        assertEquals(email, result.getEmail());
    }

    @Test
    public void testSaveUser() {
        // Setup
//        final String email = "test@test.com";
//
//        // Run the test
//        User result = userServiceUnderTest.saveUser(popularUser());
//
//        // Verify the results
//        assertEquals(email, result.getEmail());
    }
    
    private User popularUser() {
    	bCryptPasswordEncoder = new BCryptPasswordEncoder();
    	
    	user = new User();
        user.setId(1);
        user.setName("Willians");
        user.setLastName("Martins");
        user.setEmail("contato@williansmartins.com");
        user.setPassword("senha");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        
        Role userRole = roleRepository.findByRole("ADMIN");
        
//        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        
        Set<Role> roles = new HashSet<>();
		roles.add(userRole);
        user.setRoles(roles);
        
        return user;
    }
}
