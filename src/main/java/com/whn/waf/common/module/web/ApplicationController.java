package com.whn.waf.common.module.web;


import com.whn.waf.common.module.domain.Application;
import com.whn.waf.common.module.service.ApplicationService;
import com.whn.waf.common.utils.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by Li Jian on 2016/12/31.
 */
@RestController
@RequestMapping("/v0.1/applications")
public class ApplicationController {

    @Resource
    private ApplicationService applicationService;

    /**
     * 新增Application
     */
    @RequestMapping(method = RequestMethod.POST)
    public Application create(@RequestBody Application application) {
        ValidatorUtil.validateAndThrow(application);
        return applicationService.save(application);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Application update(@PathVariable String id,
                              @RequestBody Application application) {

        return applicationService.update(id, application);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Application getApplication(@PathVariable String id) {
        return applicationService.findOne(id);
    }

    @RequestMapping(value = "/names/{name}", method = RequestMethod.GET)
    public Application findByName(@PathVariable String name) {
        return applicationService.findByName(name);
    }

}
