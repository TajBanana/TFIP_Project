package tajbanana.sudokuserver;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tajbanana.sudokuserver.services.JwtFilter;


@Configuration
public class AppConfig {

    private final JwtFilter myfilter;

    public AppConfig(JwtFilter myfilter) {
        this.myfilter = myfilter;
    }

    @Bean
    public FilterRegistrationBean<JwtFilter> registerJwtFilter(JwtFilter filter) {

        FilterRegistrationBean<JwtFilter> regFilterBean = new FilterRegistrationBean<>();
        regFilterBean.setFilter(filter);
        regFilterBean.addUrlPatterns("/secure/*");
        regFilterBean.setEnabled(true);

        return regFilterBean;
    }
}
