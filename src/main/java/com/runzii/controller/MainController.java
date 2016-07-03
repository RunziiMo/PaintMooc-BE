package com.runzii.controller;

import com.runzii.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by runzii on 16-4-12.
 */
@Controller
@RequestMapping("/api")
@Slf4j
public class MainController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = GET)
    public String main() {
        return "admin/signin";
    }

    @RequestMapping(value = "hello", method = GET)
    @ResponseBody
    public Map<String, Object> hello() {
        Map<String, Object> model = new HashMap<>();
        model.put("id", UUID.randomUUID().toString());
        model.put("content", "Hello World");
        return model;
    }

    @ApiOperation(value = "手动增加验证码")
    @RequestMapping(value = "/addVerify", method = POST)
    @ResponseBody
    public void addVerify(@RequestParam String phone, @RequestParam String verifyCode) {
        userService.createVerify(phone, verifyCode);
    }

}
