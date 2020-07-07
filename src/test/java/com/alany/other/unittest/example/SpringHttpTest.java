package com.alany.other.unittest.example;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertThat;

/**
 * @author yinxing
 * @date 2019/11/14
 */
@RunWith(SpringRunner.class)
@org.springframework.boot.test.context.SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringHttpTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void findList() {
        ResponseEntity<List> result = restTemplate.getForEntity("/person/list", List.class);
        assertThat(result.getBody(), Matchers.notNullValue());
    }
}
