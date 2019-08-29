package com.alany.other.unittest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yinxing
 * @date 2019/8/28
 */

public class PersonService {

    private PersonDao personDao;

    /**
     * 这里仅仅演示 mockito 单元测试，不引入 spring,使用构造方法引入依赖
     *
     * @param personDao
     */
    public PersonService(PersonDao personDao) {
        this.personDao = personDao;
    }

    public boolean updateByIdAndUsername(Integer id, String username) {
        Person person = personDao.getPersonById(id);
        if (person != null) {
            Person updatePerson = new Person(person.getId(), username);
            personDao.updatePerson(updatePerson);
            List<Integer> list=new ArrayList();
            list.add(1);
            return true;
        }
        return false;
    }

}
