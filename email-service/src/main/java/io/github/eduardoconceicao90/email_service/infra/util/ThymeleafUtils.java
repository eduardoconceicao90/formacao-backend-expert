package io.github.eduardoconceicao90.email_service.infra.util;

import lombok.experimental.UtilityClass;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.util.Map;

@UtilityClass
public class ThymeleafUtils {

    private static final Context thymeleafContext = new Context();

    public static void addVariavelThymeleaf(Map<String, Object> variaveis) {
        variaveis.forEach(thymeleafContext::setVariable);
    }

    public static String processarTemplateThymeleaf(String templatePath) {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine.process(templatePath, thymeleafContext);
    }

}
