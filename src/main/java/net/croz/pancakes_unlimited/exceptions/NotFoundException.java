package net.croz.pancakes_unlimited.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends HttpException
{
    public NotFoundException()
    {
        super(HttpStatus.NOT_FOUND, null);
    }

    public NotFoundException(Object data)
    {
        super(HttpStatus.NOT_FOUND, data);
    }

    public NotFoundException(Class<?> clazz, int id)
    {
        super(HttpStatus.NOT_FOUND, clazz.getSimpleName() + " with id: " + id + " does not exist");
    }
}