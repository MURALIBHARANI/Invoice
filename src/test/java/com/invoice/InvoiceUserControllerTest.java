package com.invoice;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.invoice.entity.UserInfo;
import com.invoice.impl.UserInfoService;
import com.invoice.model.AuthRequest;
import com.invoice.process.JwtService;

@SpringBootTest
class InvoiceUserControllerTest {

	@Autowired
	private UserInfoService authService;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private RestTemplate restTemplate;

	private MockRestServiceServer mockServer;
    @Autowired
    private JdbcTemplate jdbcTemplate;
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		mockServer = MockRestServiceServer.createServer(restTemplate);
	}

	@Test
	void createUserAuth() throws Exception {

		UserInfo authReq = new UserInfo();
		authReq.setName("murali");
		authReq.setPassword("Skava@123");
		authReq.setRoles("USER");
		authReq.setEmail("muralibharani007@gmail.com");
		Assertions.assertEquals(authService.addUser(authReq), "User Added Successfully");
		mockServer.verify();
	}

	@Test
	void genarateToken() throws Exception {

		AuthRequest authReq = new AuthRequest();
		authReq.setUsername("murali");
		authReq.setPassword("Skava@123");
		Assertions.assertTrue(!jwtService.generateToken(authReq.getUsername()).isEmpty());
		mockServer.verify();
	}


    @Test
    public void testFlywayMigrations() {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM USER_INFO", Integer.class);
        assertThat(count).isNotNull();
    }

}
