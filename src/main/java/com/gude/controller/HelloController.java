package com.gude.controller;

/**
 * @Author Gude
 * @Date 2015/12/27.
 */

import com.gude.entity.User;
import com.gude.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/user")
public class HelloController {
    Logger logger = Logger.getLogger(this.getClass());
    @Resource
    UserService userService;

    @RequestMapping("/save")
    public String save() {
        User user = new User();
        user.setPassword("432243");
        user.setUsername("afsdsfaaaa");
        userService.save(user);
        return "hello";
    }

    @RequestMapping("/find")
    public String find() {
        long start = System.currentTimeMillis();
        User user = userService.findById(3);
        logger.info(user.toString());
        long end = System.currentTimeMillis();
        logger.info("耗时：" + (end - start));
        return "hello";
    }

    @RequestMapping("/listAll")
    public String listAll() {
        long start = System.currentTimeMillis();
        List<User> list = userService.listAll();
        logger.info(list.toString());
        long end = System.currentTimeMillis();
        logger.info("耗时：" + (end - start));
        return "hello";
    }

    @RequestMapping("/redd")
    public String good(){
        System.out.println("n你好2");
        System.out.println("sb22");
        return "hello";
    }
}
