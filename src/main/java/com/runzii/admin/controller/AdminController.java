package com.runzii.admin.controller;

import com.runzii.exception.ParamErrorException;
import com.runzii.model.entity.User;
import com.runzii.model.form.UserForm;
import com.runzii.repository.UserRepository;
import com.runzii.service.UserService;
import com.runzii.utils.DTOUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by runzii on 16-5-5.
 */
@RequestMapping("/admin")
@Controller
public class AdminController {

    @RequestMapping(value = "", method = GET)
    public String index() {
        return "admin/index";
    }

    @RequestMapping("/signin")
    public String signin(Principal principal, RedirectAttributes ra) {
        return principal == null ? "admin/signin" : "redirect:/admin";
    }
}
