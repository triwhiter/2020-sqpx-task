package com.ctgu.fmall.mapper;

import com.ctgu.fmall.entity.ShopCart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhen
 * @since 2020-07-31
 */
@Repository
public interface ShopCartMapper extends BaseMapper<ShopCart> {

    @Select("select ANY_VALUE(product.store) as store,ANY_VALUE(product_image.img_url) as img_url, ANY_VALUE(product.name) as name,format(ANY_VALUE(product.promote_price),2) as promote_price,FORMAT(ANY_VALUE(product.original_price),2) as original_price,ANY_VALUE(product.id) as pid,ANY_VALUE(shopcart_num) as shopcart_num" +
            " from product, product_image, shop_cart where " +
            "shop_cart.uid = #{uid} and product.id = shop_cart.pid and product.id = product_image.pid group by product_image.pid;")
    List<Map> getShopCartInfo(int uid);

    @Delete("delete from shop_cart where uid = #{uid} and pid = #{pid}")
    boolean delShopCartById(int uid, int pid);

    @Delete("delete from shop_cart where uid = #{uid}")
    boolean delAllShopCart(int uid);
}
