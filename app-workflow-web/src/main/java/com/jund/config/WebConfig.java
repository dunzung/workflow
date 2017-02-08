package com.jund.config;

import com.jund.workflow.http.UTF8StringHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

/**
 * web application config
 *
 * @author zhaochf
 */
@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {
    /**
     * modify by killer_duan
     *
     * @param registry
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        /**
         * BeanNameViewResolver:增加Excel解析器，主要针对实现AbstractView实例视图
         */
        registry.beanName();
        /**
         * 设置默认的数据解析方式:MappingJackson2JsonView,这个去掉，会影响Excel导入导出
         */
        //registry.enableContentNegotiation(new MappingJackson2JsonView());
        /**
         * 设置JSP页面访问拼接后缀
         */
        registry.jsp("/WEB-INF/views/", ".jsp");
    }

    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        HttpMessageConverter<String> utf8StringConverter = new UTF8StringHttpMessageConverter();
        converters.add(0, utf8StringConverter);
        super.extendMessageConverters(converters);
	}
    
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

//    @Bean
//    public CommonsMultipartResolver multipartResolver() {
//        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
//        resolver.setDefaultEncoding("UTF-8");
//        resolver.setMaxInMemorySize(4096);
//
//        // max upload file size: 10 MB
//        resolver.setMaxUploadSize(10485760);
//        return resolver;
//    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:/index");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {

    }

	/**
	 * @see WebMvcConfigurerAdapter#configureHandlerExceptionResolvers(List)
	 */
	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
//		CommonHandlerExceptionResolver exceptionResolver = new CommonHandlerExceptionResolver();
//		exceptionResolver.setOrder(50);
//		exceptionResolver.setWarnLogCategory("ExceptionResolver");
//		exceptionResolvers.add(exceptionResolver);
		super.configureHandlerExceptionResolvers(exceptionResolvers);
	}

    
}
