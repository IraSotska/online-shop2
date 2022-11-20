package com.iryna.web.template;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import lombok.Setter;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

@Setter
public class PageGenerator {

    public String generatePage(String filename, Map<String, Object> templateData) {
        StringWriter stringWriter = new StringWriter();
        try {
            var configuration = new Configuration(Configuration.VERSION_2_3_31);
            configuration.setClassForTemplateLoading(this.getClass(), "/templates/");
            var template = configuration.getTemplate(filename);
            template.process(templateData, stringWriter);
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
        return stringWriter.toString();
    }
}
