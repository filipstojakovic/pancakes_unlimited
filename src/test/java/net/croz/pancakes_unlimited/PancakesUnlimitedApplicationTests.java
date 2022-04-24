package net.croz.pancakes_unlimited;

import net.croz.pancakes_unlimited.security.models.AuthorizedUsers;
import net.croz.pancakes_unlimited.utils.JsonParserUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PancakesUnlimitedApplicationTests {

	@Test
	void contextLoads() throws Exception{
//		AuthorizedUsers users = new ObjectMapper().readValue(new ClassPathResource("users.json").getInputStream(), AuthorizedUsers.class);
//		System.out.println(users);

//		List<User> users1 = new AuthorizedUsers().getAuthorizedUsers();
//		System.out.println(users1);

		AuthorizedUsers users2 = JsonParserUtil.getObjectFromJson("users.json",AuthorizedUsers.class);
		System.out.println(users2);
	}

}
