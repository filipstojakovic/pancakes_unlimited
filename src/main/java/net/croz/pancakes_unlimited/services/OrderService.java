package net.croz.pancakes_unlimited.services;

import net.croz.pancakes_unlimited.models.dtos.OrderDTO;
import net.croz.pancakes_unlimited.models.requests.OrderRequest;
import net.croz.pancakes_unlimited.services.interfaces.IFindService;
import net.croz.pancakes_unlimited.services.interfaces.IInsertService;

public interface OrderService extends IFindService<Integer,OrderDTO>, IInsertService<OrderRequest,OrderDTO>
{
}
