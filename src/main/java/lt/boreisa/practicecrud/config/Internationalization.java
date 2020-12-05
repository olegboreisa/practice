package lt.boreisa.practicecrud.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.nio.charset.StandardCharsets;
import java.util.Locale;


@Configuration
public class Internationalization implements WebMvcConfigurer {

    /**
     * In order for our application to be able to determine which locale is currently being used, we need to add a LocaleResolver bean
     */

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);
        return slr;
    }

    /**
     * Next, we need to add an interceptor bean that will switch to a new locale based on the value of the lang parameter appended to a request
     */

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    /**
     * In order to take effect, this bean needs to be added to the application's interceptor registry.
     *
     * To achieve this, our @Configuration class has to implement the WebMvcConfigurer interface and override the addInterceptors() method
     */

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    /**
     * In order to take effect, this bean needs to be added to the application's interceptor registry.
     *
     * To achieve this, our @Configuration class has to implement the WebMvcConfigurer interface and override the addInterceptors() method
     */
    /**
     * Changing default message source location
     */

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setBasenames("i18n/messages");
        resourceBundleMessageSource.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
        return resourceBundleMessageSource;
    }
}
