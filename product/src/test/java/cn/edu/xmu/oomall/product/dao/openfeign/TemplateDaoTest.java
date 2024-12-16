package cn.edu.xmu.oomall.product.dao.openfeign;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.oomall.product.ProductTestApplication;
import cn.edu.xmu.oomall.product.mapper.openfeign.ShopMapper;
import cn.edu.xmu.oomall.product.mapper.openfeign.po.Template;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = ProductTestApplication.class)
@Slf4j
public class TemplateDaoTest {

    @Autowired
    private TemplateDao templateDao;
    @Autowired
    private ShopMapper shopMapper;

    @Test
    public void testFindByIdSuccess() {
        // 确保测试环境中有模板 ID 为 1 的记录
        Template template = templateDao.findById(1L, 1L);

        // 验证返回值
        assertNotNull(template);
        assertEquals(1L, template.getId());
        log.debug("Test successful: {}", template);
    }

    @Test
    public void testFindByIdResourceNotExist() {
        // 确保测试环境中没有 ID 为 9999 的记录
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            templateDao.findById(1L, 9999L);
        });

        // 验证异常内容
        assertEquals(ReturnNo.RESOURCE_ID_NOTEXIST, exception.getErrno());
        log.debug("Exception captured: {}", exception.getMessage());
    }

    @Test
    public void testFindByIdOutScope() {
        // 确保测试环境中 ID 为 1 的模板属于其他 Shop
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            templateDao.findById(1L, 16L);
        });

        // 验证异常内容
        assertEquals(ReturnNo.RESOURCE_ID_OUTSCOPE, exception.getErrno());
        log.debug("Exception captured: {}", exception.getMessage());
    }
}
