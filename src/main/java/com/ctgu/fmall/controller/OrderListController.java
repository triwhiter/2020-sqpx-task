package com.ctgu.fmall.controller;


import com.ctgu.fmall.dto.OrderDTO;
import com.ctgu.fmall.entity.OrderDetail;
import com.ctgu.fmall.entity.OrderList;
import com.ctgu.fmall.service.OrderDetailService;
import com.ctgu.fmall.service.OrderListService;
import com.ctgu.fmall.utils.ResultUtil;
import com.ctgu.fmall.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhen
 * @since 2020-08-02
 */
@RestController
@RequestMapping("/orderList")
public class OrderListController {

    @Autowired
    private OrderListService orderListService;

    @Autowired
    OrderDetailService orderDetailService;

    @GetMapping("/getOrderInfo/{uid}")
    @ApiOperation("获取用户订单")
    public Result getOrderListInfoByUid(@PathVariable("uid") int uid){
        return orderListService.getOrderListInfoByUid(uid);
    }

    @GetMapping("getOrderInfoPage/{uid}/{page}/{num}")
    @ApiOperation("获取当前用户订单，实现分页")
    public Result getOrderInfoPage(@PathVariable("uid") int uid,
                                   @PathVariable("page") int page,
                                   @PathVariable("num") int num){
        return orderListService.getOrderInfoPage(uid,page,num);
    }

    @PostMapping("/")
    @ApiOperation("用户下单")
    @Transactional
    public Result add(@RequestBody @Valid OrderDTO orderDTO){
        OrderList orderList=new OrderList(orderDTO);
        try{
        orderListService.save(orderList);
        for(int i=0;i <orderDTO.getPids().size();i++){
            OrderDetail detail = new OrderDetail();
            detail.setNumber(orderDTO.getNums().get(i));
            detail.setPid(orderDTO.getPids().get(i));
            detail.setOid(orderList.getId());
            orderDetailService.save(detail);
        }
        return ResultUtil.success();
        }catch (Exception e){
            return ResultUtil.error(e.getMessage());
        }
    }

}

