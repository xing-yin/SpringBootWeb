package com.alany.mybatis.simpleDemo;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SqlSession 简单查询演示类
 *
 * @author yinxing
 * @date 2019/9/10
 */

public class SelectDemo {

    public static void main(String[] args) throws IOException {
        /**
         * 1.加载mybatis的配置文件，初始化mybatis，创建SqlSessionFactory（即创建SqlSession的工厂）
         * 这里只做演示，SqlSessionFactory临时创建出来，在实际的使用中，SqlSessionFactory只需要创建一次，当作单例来使用
         */
        InputStream inputStream = Resources.getResourceAsStream("mybatisConfig.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession sqlSession = null;
        try {
            /**
             * 2.用 SqlSessionFactory 创建一个 SqlSession，进行数据库操作
             */
            sqlSession = factory.openSession();
            // some code ...
            /**
             * 3.使用 SqlSession 查询
             */
            Map<String, Object> params = new HashMap<>();
            params.put("id", 2);
            List<Person> personList1 = sqlSession.selectList("com.alany.mybatis.simpleDemo.PersonMapper.java.findListById", params);
            List<Person> personList2 = sqlSession.selectList("com.alany.mybatis.simpleDemo.PersonMapper.java.findListById");
            sqlSession.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            sqlSession.rollback();
        } finally {
            /**
             * 4.关闭资源
             */
            if (sqlSession != null) {
                sqlSession.close();
            }
        }


    }
}
