package com.doc.cloud.user.web;

import com.doc.cloud.base.vo.InfoVO;
import com.doc.cloud.user.dto.req.CreateUserReq;
import com.doc.cloud.user.pojo.User;
import com.doc.cloud.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by majun on 09/02/2018.
 */
@Controller
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "/user",method = RequestMethod.POST)
    @ResponseBody
    public InfoVO<User> createUser(@RequestBody CreateUserReq req){
        return userService.createUser(req.getUsername(),req.getPassword(),req.getEmail());
    }

}
