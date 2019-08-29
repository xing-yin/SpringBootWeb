package com.alany.other.unittest;

import org.junit.*;

/**
 * junit 单元测试 常用注解
 *
 * @author yinxing
 * @date 2019/8/28
 */

public class MockTest {

    /**
     * 针对所有测试，也就是整个测试类中，在所有测试方法执行前，都会先执行由它注解的方法，而且只执行一次。
     * 当然，需要注意的是，修饰符必须是 public static void xxxx ；此 Annotation 是 JUnit 4 新增的功能。
     */
    @BeforeClass
    public static void setUpClass() {
        System.out.println("beforeClass");
    }

    @Before
    public void setUp() {
        System.out.println("before");
    }

    @Test
    public void testMethod1() {
        System.out.println("testMethod1");
    }

    /**
     * 测试期望超时时间
     */
    @Test(timeout = 1000)
    public void testMethod2() {
    }

    /**
     * 测试期望异常
     */
    @Test(expected = NullPointerException.class)
    public void testMethod3() {
        throw new NullPointerException();
    }

    /**
     * 忽略的测试方法，标注的含义就是“某些方法尚未完成，咱不参与此次测试”
     */
    @Ignore
    public void testMethod4() {
    }


    @After
    public void tearDown() {
        System.out.println("after");
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println("afterClass");
    }

    /**
     * setUpClass()、tearDownClass()、setUp()、tearDown()称为测试夹具（Fixture），
     * 就是测试运行程序（test runner）在运行测试方法之前进行初始化、或之后进行回收资源的工作。
     * <p></p>
     * JUnit 4之前是通过setUp、tearDown方法完成。在JUnit 4中，仍然可以在每个测试方法运行之前初始化字段和配置环境，当然也是通过注解完成。
     * 在JUnit 4中，通过@Before标注setUp方法；@After标注tearDown方法。在一个测试类中，甚至可以使用多个@Before来注解多个方法，这些方法都是在每个测试之前运行。
     * <p></p>
     * 说明一点，一个测试用例类可以包含多个打上@Test注解的测试方法，在运行时，每个测试方法都对应一个测试用例类的实例。
     * @Before是在每个测试方法运行前均初始化一次，同理@Ater是在每个测试方法运行完毕后均执行一次。
     * 也就是说，经这两个注解的初始化和注销，可以保证各个测试之间的独立性而互不干扰，它的缺点是效率低。
     * <p></p>
     * 另外，不需要在超类中显式调用初始化和清除方法，只要它们不被覆盖，测试运行程序将根据需要自动调用这些方法。
     * 超类中的@Before方法在子类的@Before方法之前调用（与构造函数调用顺序一致），@After方法是子类在超类之前运行。
     */
}
