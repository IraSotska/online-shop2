package com.iryna.web.template;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;


public class PageGenerator {

    public String generatePage(String filename, Map<String, Object> templateData) {
        var stream = new StringWriter();
        try {
            var configuration = new Configuration();
            configuration.setClassForTemplateLoading(this.getClass(), "/templates/");
            var template = configuration.getTemplate(filename);
            template.process(templateData, stream);
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
        return stream.toString();
    }
}
