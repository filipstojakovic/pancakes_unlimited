package net.croz.pancakes_unlimited.services;

import net.croz.pancakes_unlimited.models.dtos.OrderDTO;
import net.croz.pancakes_unlimited.models.requests.OrderRequest;
import net.croz.pancakes_unlimited.services.crudinterfaces.IFindService;
import net.croz.pancakes_unlimited.services.crudinterfaces.IInsertService;

public interface OrderService extends IFindService<Integer,OrderDTO>, IInsertService<OrderRequest,OrderDTO>
{
    OrderDTO findByOrderNumber(String orderNumber);
}
