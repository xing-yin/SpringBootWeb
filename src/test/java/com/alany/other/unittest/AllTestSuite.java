package com.alany.other.unittest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * TestSuite
 * 如果有多个测试类, 可以合并成一个测试套件进行测试, 运行一个 Test Suite, 那么就会运行在这个 Test Suite 中的所用的测试.
 *
 * @author yinxing
 * @date 2019/8/29
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({MockTest.class, PersonServiceTest.class, PracticeTest.class})
public class AllTestSuite {


}
