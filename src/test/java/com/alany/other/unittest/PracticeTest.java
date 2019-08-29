package com.alany.other.unittest;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * @author yinxing
 * @date 2019/6/12
 */

public class PracticeTest {

    @Test
    public void testList() {
        List mock = Mockito.mock(List.class);
        when(mock.get(0)).thenReturn(1);
        when(mock.get(1)).thenThrow(new RuntimeException());
        doThrow(new RuntimeException()).when(mock).remove(mock.get(1));
//        when(mock.get(0)).thenReturn(2);
        // mock.get(0) 返回 1
        assertEquals("预期返回1", 1, mock.get(0));
    }

    @Test
    public void testHttpServletRequest() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("foo")).thenReturn("test");
        assertEquals("预期返回test", "test", request.getParameter("foo"));
    }

    @Test
    public void testList2() {
        List mockList = Mockito.mock(List.class);
        when(mockList.get(anyInt())).thenReturn("element");
        System.out.println(mockList.get(1));
        System.out.println(mockList.get(999));
    }

    @Test
    public void testIterator() {
        Iterator i = mock(Iterator.class);
        when(i.next()).thenReturn("hello").thenReturn("mock");
        String result = i.next() + "," + i.next();
        assertEquals("预期返回hello,mock", "hello,mock", result);

        /**
         * doThrow(ExceptionX).when(x).methodCall
         * 它的含义是: 当调用了 x.methodCall 方法后, 抛出异常 ExceptionX.
         */
//        doThrow(new NoSuchElementException()).when(i).next();
//        i.next();
    }

    @Test
    public void testSpy() {
        List<String> list = new LinkedList<>();
        List<String> spyList = spy(list);

        // 对 spy.size() 进行定制
        when(spyList.size()).thenReturn(100);

        spyList.add("one");
        spyList.add("two");

        // 因为我们没有对 get(0), get(1) 方法进行定制,因此调用的真实对象的方法
        assertEquals(spyList.get(0), "one");
        assertEquals(spyList.get(1), "two");
        // 对 spyList.size() 方法进行定制
        assertEquals(spyList.size(), 100);
    }

}
