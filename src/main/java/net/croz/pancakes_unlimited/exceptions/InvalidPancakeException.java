package net.croz.pancakes_unlimited.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidPancakeException extends HttpException
{
    public InvalidPancakeException()
    {
        super(HttpStatus.BAD_REQUEST, null);
    }

    public InvalidPancakeException(Object data)
    {
        super(HttpStatus.BAD_REQUEST, data);
    }
}