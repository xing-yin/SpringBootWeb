package com.alany.other.unittest.example;

import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertThat;

/**
 * 参数化测试
 *
 * @author yinxing
 * @date 2019/11/14
 */

/**
 * 1.更改默认的测试运行器为RunWith(Parameterized.class)
 */
@RunWith(Parameterized.class)
public class PramterTest {

    /**
     * Junit 4 引入了一个新的功能参数化测试。参数化测试允许开发人员使用不同的值反复运行同一个测试。你将遵循 5 个步骤来创建参数化测试。
     * <p>
     * * 用 @RunWith(Parameterized.class)来注释 test 类。
     * * 创建一个由 @Parameters 注释的公共的静态方法，它返回**一个对象的集合(数组)**来作为测试数据集合。
     * * 创建一个公共的构造函数，它接受和一行测试数据相等同的东西。
     * * 为每一列测试数据创建一个实例变量。
     * * 用实例变量作为测试数据的来源来创建你的测试用例。
     */

    /**
     * 2.声明变量存放预期值和测试数据
     */
    private String firstName;
    private String lastName;

    /**
     * 3.声明一个返回值为 Collection 的公共静态方法，并使用 @Parameters 进行修饰
     */
    @Parameterized.Parameters
    public static List<Object[]> param() {
        // 这里给来那个测试用例
        return Arrays.asList(new Object[][]{{"Mike", "Black"}, {"Cilcln", "Smith"}});
    }

    /**
     * 4.为测试类声明一个带有参数的公共构造函数，并在其中位置声明变量赋值
     */
    public PramterTest(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * 5.进行测试，会发现它会将所有的测试勇烈测试一遍
     */
    @Test
    public void test(){
        String name = firstName + ":" + lastName;
        assertThat("Mike:Black", Is.is(name));
    }
}
