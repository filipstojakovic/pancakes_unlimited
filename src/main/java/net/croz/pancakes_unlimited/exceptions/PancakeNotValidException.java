package net.croz.pancakes_unlimited.exceptions;

import org.springframework.http.HttpStatus;

public class PancakeNotValidException extends HttpException
{
    public static final String ONLY_ONE_BASE_ALLOWED = "Pancake does not have exactly one ingredient of type baza";
    public static final String AT_LEAST_ONE_FIL_ALLOWED = "Pancake does not have at least one ingredient of type fil";
    public PancakeNotValidException()
    {
        super(HttpStatus.BAD_REQUEST, null);
    }

    public PancakeNotValidException(String data)
    {
        super(HttpStatus.BAD_REQUEST, data);
    }
    public PancakeNotValidException(Object data)
    {
        super(HttpStatus.BAD_REQUEST, data);
    }
}