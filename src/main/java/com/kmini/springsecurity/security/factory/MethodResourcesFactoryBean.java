package com.kmini.springsecurity.security.factory;


import com.kmini.springsecurity.service.SecurityResourceService;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.LinkedHashMap;
import java.util.List;

public class MethodResourcesFactoryBean implements FactoryBean<LinkedHashMap<String, List<ConfigAttribute>>> {


    private final SecurityResourceService securityResourceService;
    private LinkedHashMap<String, List<ConfigAttribute>> resourceMap;

    public MethodResourcesFactoryBean(SecurityResourceService securityResourceService) {
        this.securityResourceService = securityResourceService;
    }

    @Override
    public LinkedHashMap<String, List<ConfigAttribute>> getObject() {

        if (resourceMap == null) {
            init();
        }
        return resourceMap;
    }

    private void init() {
        resourceMap = securityResourceService.getMethodResourceList();
    }

    @Override
    public Class<?> getObjectType() {
        return LinkedHashMap.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
