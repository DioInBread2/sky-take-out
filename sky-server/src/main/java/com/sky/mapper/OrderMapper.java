package com.sky.mapper;

import com.sky.dto.GoodsSalesDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {
    /**
     * 插入订单
     * @return
     */
    Long insert(Orders orders);


    /**
     * 根据订单号查询订单
     * @param orderNumber
     */
    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    /**
     * 修改订单信息
     * @param orders
     */
    void update(Orders orders);

    /**
     * 根据订单状态和下单时间查询订单
     * @param status
     * @param orderTime
     * @return
     */
    @Select("select id from orders where status = #{status} and order_time < #{orderTime}")
    List<Long> getStatusAndOrderTimeLT(Integer status, LocalDateTime orderTime);

    void updateBatch(List<Long> ordIds, Integer status,String cancelReason,LocalDateTime cancelTime);

    @Select("select * from orders where id = #{id}")
    Orders getById(Integer id);

    /**
     * 动态统计营业额数据
     * @param begin
     * @param end
     * @return
     */
    List<Double> sumByRange(LocalDate begin, LocalDate end);

    /**
     * 动态查询订单数量
     * @param map
     * @return
     */
    Integer countByMap(Map map);

    /**
     * 销量前十
     * @return
     */
    List<GoodsSalesDTO> getSalesTop10(LocalDateTime begin, LocalDateTime end);


    Double sumByMap(Map map);
}
