package com.zzh.riggie.controller;

import com.zzh.riggie.common.R;
import com.zzh.riggie.dto.OrdersDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/orderDetail")
public class OrderDetailController {

    @GetMapping("/{id}")
    public R<OrdersDto> get(@PathVariable Long id){
        return null;
    }
}
