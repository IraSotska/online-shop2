package com.iryna.ioc;

import com.study.ioc.context.impl.GenericApplicationContext;
import com.study.ioc.reader.sax.XmlBeanDefinitionReader;

public class ApplicationContext {

    private static final GenericApplicationContext genericApplicationContext = new GenericApplicationContext(new XmlBeanDefinitionReader("context.xml"));

    public static <T> T getService(Class<T> serviceType) {
        return serviceType.cast(genericApplicationContext.getBean(serviceType));
    }
}
