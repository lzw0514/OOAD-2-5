package cn.edu.xmu.oomall.shop.dao.openfeign;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.oomall.shop.ShopTestApplication;
import cn.edu.xmu.oomall.shop.dao.bo.Region;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ShopTestApplication.class)
@Slf4j
public class RegionDaoTest {

    @Autowired
    private RegionDao regionDao;

    @Test
    public void testFindByIdSuccess() {
        // 确保测试环境中有地区 ID 为 5 的记录
        Region region = regionDao.findById(5L);

        // 验证返回值
        assertNotNull(region);
        assertEquals(5L, region.getId());
        log.debug("Test successful: {}", region);
    }

    @Test
    public void testFindByIdResourceNotExist() {
        // 确保测试环境中没有 ID 为 9999 的记录
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            regionDao.findById(99999999L);
        });

        // 验证异常内容
        assertEquals(ReturnNo.RESOURCE_ID_NOTEXIST, exception.getErrno());
        log.debug("Exception captured: {}", exception.getMessage());
    }

    @Test
    public void testRetrieveParentRegionsByIdSuccess() {
        // 确保测试环境中有地区 ID 为 1 的父地区记录
        List<Region> regions = regionDao.retrieveParentRegionsById(5L);

        // 验证返回值
        assertNotNull(regions);
        assertFalse(regions.isEmpty());
        log.debug("Test successful: {}", regions);
    }

    @Test
    public void testRetrieveParentRegionsByIdResourceNotExist() {
        // 确保测试环境中没有 ID 为 9999 的父地区记录
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            regionDao.retrieveParentRegionsById(99999999L);
        });

        // 验证异常内容
        assertEquals(ReturnNo.RESOURCE_ID_NOTEXIST, exception.getErrno());
        log.debug("Exception captured: {}", exception.getMessage());
    }
}