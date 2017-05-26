package com.whn.waf.common.module.service;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.whn.waf.common.base.constant.ErrorCode;
import com.whn.waf.common.base.service.BaseService;
import com.whn.waf.common.context.Module;
import com.whn.waf.common.exception.WafBizException;
import com.whn.waf.common.module.domain.Application;
import com.whn.waf.common.module.repository.ApplicationRepository;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/5/26.
 */
@Service
public class ApplicationService extends BaseService<Application, String> {

    private static Logger logger = Logger.getLogger(ApplicationService.class);

    @Resource
    private ApplicationRepository applicationRepository;


    @Override
    protected Module module() {
        return new Module("application");
    }

    public Application save(Application application) {
        String appName = application.getName();
        checkNameMatchEnNum(appName);
        try {
            Application result = add(application);
            return result;
        } catch (Exception e) {
            logger.info(e);
            throw WafBizException.of(ErrorCode.APPLICATION_NAME_DUPLICATE);
        }
    }

    void checkNameMatchEnNum(String applicationName) {
        if (!applicationName.matches("^[a-z][a-z0-9_-]*$")) {
            throw WafBizException.of(ErrorCode.APPLICATION_NAME_ONLY_INPUT_EN_NUM);
        }
    }

    /**
     * 更新Application
     */
    public Application update(String id, Application newApp) {
        Application oldApp = super.findOne(id);
        // 判断application是否存在
        if (oldApp == null) {
            throw WafBizException.of(ErrorCode.DATA_NOT_FOUND);
        }
        // 这些属性不能变，防御性拷贝
        newApp.setId(id);
        newApp.setName(oldApp.getName());
        newApp.setCreatedTime(oldApp.getCreatedTime());
        Application result = this.update(newApp);
        return result;
    }


    /**
     * 通过应用的名称来获取应用
     */
    public Application findByName(String applicationName) {
        return applicationRepository.findByName(applicationName);
    }

    public List<String> findAllApplicationNames() {
        List<Application> allApplications = applicationRepository.findAll();
        return Lists.transform(allApplications, new Function<Application, String>() {
            @Override
            public String apply(Application input) {
                return input.getName();
            }
        });
    }

}
