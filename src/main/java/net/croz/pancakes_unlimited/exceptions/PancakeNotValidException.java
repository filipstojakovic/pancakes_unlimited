package net.croz.pancakes_unlimited.exceptions;

import org.springframework.http.HttpStatus;

public class PancakeNotValidException extends HttpException
{
    public PancakeNotValidException()
    {
        super(HttpStatus.BAD_REQUEST, null);
    }

    public PancakeNotValidException(Object data)
    {
        super(HttpStatus.BAD_REQUEST, data);
    }
}