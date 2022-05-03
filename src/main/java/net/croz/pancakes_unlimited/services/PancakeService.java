package net.croz.pancakes_unlimited.services;

import net.croz.pancakes_unlimited.models.dtos.PancakeDTO;
import net.croz.pancakes_unlimited.models.requests.PancakeRequest;
import net.croz.pancakes_unlimited.services.crudinterfaces.ICrudService;

public interface PancakeService extends ICrudService<Integer,PancakeRequest,PancakeDTO>
{
}
