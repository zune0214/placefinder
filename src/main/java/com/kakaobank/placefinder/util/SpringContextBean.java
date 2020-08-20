package com.kakaobank.placefinder.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SpringContextBean implements ApplicationContextAware {

    private static SpringContextBean springContextBean;
    private ApplicationContext applicationContext;

    @PostConstruct
    public void registerInstance() {

        springContextBean = this;
    }

    public static <T> T getBean(Class<T> clazz) {

        return springContextBean.applicationContext.getBean(clazz);
    }

    public static Object getBean(String beanName) {

        if (springContextBean.applicationContext.containsBean(beanName)) {
            return springContextBean.applicationContext.getBean(beanName);
        }
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        this.applicationContext = applicationContext;
    }
}
