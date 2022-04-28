package net.croz.pancakes_unlimited;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UnderTest
{
    @Test
    public void generateOrderNumberTest()
    {
        var uuid = UUID.randomUUID();
        String number = uuid.toString();
        System.out.println(UUID.randomUUID().toString().length());
    }
}
