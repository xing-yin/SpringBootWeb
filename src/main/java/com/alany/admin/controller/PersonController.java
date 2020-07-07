package com.alany.admin.controller;

import com.alany.admin.entity.PersonDO;
import com.alany.admin.service.PersonService;
import com.alany.common.core.controller.model.ResponseResults;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yinxing
 * @date 2019/11/13
 */

@RestController
@Slf4j
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping(value = "/list")
    public ResponseResults list(){
        List<PersonDO> personDOList = personService.findList();
        return ResponseResults.ok(personDOList);
    }

    @PostMapping(value = "/add")
    public ResponseResults add(@RequestBody PersonDO personDO){
        if(personService.savePersonDO(personDO)){
            return ResponseResults.ok("").setMessage("success");
        }
        return  ResponseResults.error(HttpStatus.SC_BAD_REQUEST,"failed");
    }

    @DeleteMapping(value = "/delete")
    public ResponseResults delete(@RequestParam(value = "id") Long id){
        if(personService.deletePersonDO(id)){
            return ResponseResults.ok("").setMessage("success");
        }
        return  ResponseResults.error(HttpStatus.SC_BAD_REQUEST,"failed");
    }
}
