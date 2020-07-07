package com.alany.other.unittest.example;

import com.alany.SpringbootwebApplication;
import com.alany.admin.service.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Spring MVC 测试
 * <p>
 * 也可以注入Spring 上下文的环境到 MockMvc 中
 *
 * @author yinxing
 * @date 2019/11/14
 */
@RunWith(SpringRunner.class)
@org.springframework.boot.test.context.SpringBootTest(classes = SpringbootwebApplication.class)
public class SpringMVC2Test {

    private MockMvc mvc;

    /**
     * 注意需要首先使用 WebApplicationContext 构建 MockMvc
     */
    @Autowired
    private WebApplicationContext ctx;

    @MockBean
    private PersonService personService;

    @Before
    public void setUp() {
        // 初始化 MVC 的环境
        mvc = MockMvcBuilders.webAppContextSetup(ctx).build();
        Mockito.when(personService.findList()).thenReturn(new ArrayList<>());
    }

    @Test
    public void findList() throws Exception {
        mvc.perform(get("/person/list")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                // 期待返回状态码 200
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
