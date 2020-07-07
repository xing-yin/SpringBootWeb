package com.alany.other.unittest;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * PersonService的单元测试用例
 *
 * @author yinxing
 * @date 2019/8/28
 */
// 让 JUnit 运行 Spring 的测试环境， 获得 Spring 环境的上下文的支持
@RunWith(SpringRunner.class)
// 获取启动类，加载配置，确定装载 Spring 程序的装载方法，它回去寻找 主配置启动类（被 @SpringBootApplication 注解的）
@SpringBootTest
public class PersonDOServiceImplTest {

    /**
     * 模拟对象
     */
    @Mock
    private PersonDao personDao;
    /**
     * 被测试类
     */
    private PersonService personService;

//    @BeforeClass
//    public static void setUpClass(){}

    /**
     * 在@Test标注的测试方法之前运行
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        // 初始化测试用例类中由 Mockito 的注解标注的所有模拟对象(如上面 @Mock 标注的 personDao)
        MockitoAnnotations.initMocks(this);
        // 用模拟对象创建被测试类对象
        personService = new PersonService(personDao);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void updatePersonName() {
        Person person = new Person(1, "jack");
        // 设置模拟对象的返回预期值
        when(personDao.getPersonById(1)).thenReturn(person);
        // 执行测试
        boolean isUpdate = personService.updateByIdAndUsername(1, "david");
        // 验证更新是否成功
        assertTrue(isUpdate);
        // 验证模拟对象的 getPersonById(1) 方法是否被调用了一次
        verify(personDao).getPersonById(1);
        // 得到一个抓取器
        ArgumentCaptor<Person> personArgumentCaptor = ArgumentCaptor.forClass(Person.class);
        // 验证模拟对象的 updatePerson() 是否被调用一次，并抓取调用时传入的参数值
        verify(personDao).updatePerson(personArgumentCaptor.capture());
        // 获取抓取到的参数值
        Person updatePerson = personArgumentCaptor.getValue();
        // 验证调用时的参数值
        assertEquals("david", updatePerson.getUsername());
        // 检查模拟对象上是否还有未验证的交互
        verifyNoMoreInteractions(personDao);
    }

    @Test
    public void notUpdateIfPersonNotFound() {
        // 设置模拟对象的返回预期值
        when(personDao.getPersonById(1)).thenReturn(null);
        // 执行测试
        boolean isUpdate = personService.updateByIdAndUsername(1, "david");
        // 验证更新是否失败
        assertFalse(isUpdate);
        // 验证模拟对象的 getPersonById(1) 方法是否被调用了一次
        verify(personDao).getPersonById(1);
        // 验证模拟对象是否没有发生任何交互
        verifyZeroInteractions(personDao);
    }

    @Test
    public void testUpdate(){
        System.out.println("update");
    }

    @Test
    public void updateByIdAndUsername() throws Exception {
    }

//    @AfterClass
//    public static void tearDownClass(){}

    /**
     * a.对于模拟测试，在测试用例类中要先声明依赖的各个模拟对象，在setUp()中用MockitoAnnotations.initMocks()初始化所有模拟对象。
     * b.在进行模拟测试时，要先设置模拟对象上方法的返回预期值，执行测试时会调用模拟对象上的方法，因此要验证这些方法是否被调用，并且传入的参数值是否符合预期。
     * c.对于自己创建测试所需的所有的数据，（比如从数据库中拿真实的数据，或写入到数据库中）可能由另一个团队在负责，以适配不同的数据库系统。这样的依赖关系无疑使单元测试比较麻烦，有了模拟测试框架，就可以最大限度地降低单元测试时的依赖耦合
     */

}