package com.alany.other.unittest.example;

import com.alany.other.unittest.PersonDOServiceImplTest;
import com.alany.other.unittest.PracticeTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * TestSuite
 * 如果有多个测试类, 可以合并成一个测试套件进行测试, 运行一个 Test Suite, 那么就会运行在这个 Test Suite 中的所用的测试.
 *
 * @author yinxing
 * @date 2019/8/29
 */
// 1. 更改测试运行方式为 Suite
@RunWith(Suite.class)
// 2. 将测试类传入进来
@Suite.SuiteClasses({JunitTest.class, PersonDOServiceImplTest.class, PracticeTest.class})
public class AllTestSuite {


}
