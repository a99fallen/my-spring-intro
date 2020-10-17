package pl.sda.projects.adverts.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private DataSource dataSource;

    public SecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                    .dataSource(dataSource)
                    .passwordEncoder(passwordEncoder())
                    .usersByUsernameQuery("SELECT username, password, active FROM users WHERE username = ?")
                    .authoritiesByUsernameQuery("SELECT username, 'ROLE_USER' FROM users WHERE username = ?");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/register").permitAll()       //dostęp do strony zezwolony dla wszystkich
                    .antMatchers("/login").permitAll()          //dostęp do strony zezwolony dla wszystkich
                    .anyRequest().authenticated()                           //zabezpieczenie pozostałych stron
                    .and()                  //wychodzimy o poziom wyżej, żeby mieć dostępne tamte metody
                .formLogin()                //włączenie formularza
                    .defaultSuccessUrl("/")                                 //po zalogowaniu wchodzimy na daną stronę
                    .and()
                .logout()                                                   //co po wylogowaniu
                    .logoutSuccessUrl("/login");                            //wychodzin na tę stronę
    }
}
