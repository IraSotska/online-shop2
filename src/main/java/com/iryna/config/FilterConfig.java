package com.iryna.config;

import com.iryna.security.SecurityService;
import com.iryna.web.filter.AdminFilter;
import com.iryna.web.filter.GuestFilter;
import com.iryna.web.filter.UserFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class FilterConfig {

    private final SecurityService securityService;

    @Bean
    public FilterRegistrationBean<UserFilter> userFilter() {
        FilterRegistrationBean<UserFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new UserFilter(securityService));
        registrationBean.addUrlPatterns("/cart/*");
        registrationBean.setOrder(2);

        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<AdminFilter> adminFilter() {
        FilterRegistrationBean<AdminFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AdminFilter(securityService));
        registrationBean.addUrlPatterns("/products/remove/*");
        registrationBean.addUrlPatterns("/products/add/*");
        registrationBean.addUrlPatterns("/products/edit/*");
        registrationBean.setOrder(3);

        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<GuestFilter> guestFilter() {
        FilterRegistrationBean<GuestFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new GuestFilter(securityService));
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);

        return registrationBean;
    }
}
