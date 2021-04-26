package com.sysu.guli.service.trade.service.impl;

import com.sysu.guli.service.trade.entity.Order;
import com.sysu.guli.service.trade.mapper.OrderMapper;
import com.sysu.guli.service.trade.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author khannoob
 * @since 2021-04-26
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
