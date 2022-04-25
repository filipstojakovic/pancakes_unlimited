package net.croz.pancakes_unlimited.exceptions;

import org.springframework.http.HttpStatus;

public class ConflictException extends HttpException
{
    public ConflictException()
    {
        super(HttpStatus.CONFLICT, null);
    }

    public ConflictException(Object data)
    {
        super(HttpStatus.CONFLICT, data);
    }
}
