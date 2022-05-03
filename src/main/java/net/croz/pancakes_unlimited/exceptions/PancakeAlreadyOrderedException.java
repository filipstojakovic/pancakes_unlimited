package net.croz.pancakes_unlimited.exceptions;

import org.springframework.http.HttpStatus;

public class PancakeAlreadyOrderedException  extends HttpException
{
    public PancakeAlreadyOrderedException()
    {
        super(HttpStatus.BAD_REQUEST, null);
    }

    public PancakeAlreadyOrderedException(Object data)
    {
        super(HttpStatus.BAD_REQUEST, data);
    }
}