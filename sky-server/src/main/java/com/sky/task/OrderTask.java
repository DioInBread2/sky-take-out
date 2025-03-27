package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 定时任务类，定时处理超时订单
 */
@Component
@Slf4j
public class OrderTask {

    @Autowired
    private OrderMapper orderMapper;
    /**
     *处理超时订单
     */
    @Scheduled( cron = "0 * * * * ? ") // 每分钟触发一次
    public void processTimeoutOrder() {
        log.info("定时处理超时订单");
        log.info("十五分钟前{}",LocalDateTime.now().minusMinutes(15));
        List<Long> orids = orderMapper.getStatusAndOrderTimeLT(Orders.PENDING_PAYMENT, LocalDateTime.now().minusMinutes(15));
        if(orids != null && !orids.isEmpty()){
            orderMapper.updateBatch(orids,Orders.CANCELLED,"订单超时，自动取消",LocalDateTime.now());
        }
    }

    /**
     * 处理派送中订单
     */
    @Scheduled( cron = "0 0 1 * * ? ")//每天凌晨一点触发一次
    public void processDeliveredOrder(){
        log.info("定时处理派送中订单");
        List<Long> orids = orderMapper.getStatusAndOrderTimeLT(Orders.DELIVERY_IN_PROGRESS, LocalDateTime.now());
        if(orids != null && !orids.isEmpty()){
            orderMapper.updateBatch(orids,Orders.COMPLETED,"",null);
        }
    }
}
