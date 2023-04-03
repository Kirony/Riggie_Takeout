package com.zzh.riggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzh.riggie.entity.Orders;


public interface OrdersService extends IService<Orders> {
    public void submit(Orders orders);
}
