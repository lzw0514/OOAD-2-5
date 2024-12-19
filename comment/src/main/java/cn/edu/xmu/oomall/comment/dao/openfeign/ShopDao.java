//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.comment.dao.openfeign;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.InternalReturnObject;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.oomall.comment.mapper.openfeign.ShopPoMapper;
import cn.edu.xmu.oomall.comment.dao.bo.Shop;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class ShopDao {

    private ShopPoMapper shopPoMapper;

    @Autowired
    public ShopDao(ShopPoMapper shopPoMapper) {
        this.shopPoMapper = shopPoMapper;
    }

    /**
     * author Wuyuzhu
     */
    public Shop findById(Long id) {
       InternalReturnObject<Shop> ret = this.shopPoMapper.getShopById(id);
        if (ReturnNo.OK.getErrNo() == ret.getErrno()) {
            return ret.getData();
        } else {
            log.debug("ShopDaoFeign: findById {}", ReturnNo.getReturnNoByCode(ret.getErrno()));
            throw new BusinessException(ReturnNo.getReturnNoByCode(ret.getErrno()), ret.getErrmsg());
        }
    }
}
