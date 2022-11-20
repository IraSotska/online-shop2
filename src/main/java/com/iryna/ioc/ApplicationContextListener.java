package com.iryna.ioc;

import com.study.ioc.context.impl.GenericApplicationContext;
import com.study.ioc.reader.sax.XmlBeanDefinitionReader;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ApplicationContextListener implements ServletContextListener {

    public static final String APPLICATION_CONTEXT = "context";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("HELLO");
        var genericApplicationContext = new GenericApplicationContext(new XmlBeanDefinitionReader("context.xml"));
        sce.getServletContext().setAttribute(APPLICATION_CONTEXT, genericApplicationContext);
    }
}
