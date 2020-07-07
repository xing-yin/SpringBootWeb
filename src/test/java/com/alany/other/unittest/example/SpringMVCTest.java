package com.alany.other.unittest.example;

import com.alany.SpringbootwebApplication;
import com.alany.admin.service.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Spring MVC 测试
 * <p>
 * 当你想对 Spring MVC 控制器编写单元测试代码时，可以使用@WebMvcTest注解。
 * 它提供了自配置的 MockMvc，可以不需要完整启动 HTTP 服务器就可以快速测试 MVC 控制器。
 *
 * @author yinxing
 * @date 2019/11/14
 */
@RunWith(SpringRunner.class)
@WebMvcTest(SpringbootwebApplication.class)
public class SpringMVCTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PersonService personService;

    @Before
    public void setUp() {
        // 数据打桩，设置该方法返回的 body 一直是空的
        Mockito.when(personService.findList()).thenReturn(new ArrayList<>());
    }

    @Test
    public void findList() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/person/list"))
                // 期待返回状态吗码200
                .andExpect(status().isOk())
                .andDo(print());

    }

}

/**
 * 使用@WebMvcTest注解时，只有一部分的 Bean 能够被扫描得到，它们分别是：
 * <p>
 * 1.@Controller
 * 2.@ControllerAdvice
 * 3.@JsonComponent
 * 4.Filter
 * 5.WebMvcConfigurer
 * 6.HandlerMethodArgumentResolver
 * 其他常规的@Component（包括@Service、@Repository等）Bean 则不会被加载到 Spring 测试环境上下文中。
 * 所以在上面使用了数据打桩。
 */
