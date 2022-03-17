package de.oette.lecture.A06.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * user:pass in base64 -> dXNlcjpwYXNz
 * curl -XGET localhost:8090/secure-demo/private -H "Authorization: Basic dXNlcjpwYXNz"
 */
@Configuration  //das ist eine kLasse, die beim hochfahren der Anwendung über den KLassenpfad gescannt wirdm Spring erfasst Klasse und weiß dass estwas zu konfigurienen ist
@EnableWebSecurity  //sorgt dafür dass weitere KOnfigurationsklassen geladen werden, spring hat ne reihe von vordef. Klassen, mit der Annot. werden diese geladen
public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("User").password(passwordEncoder().encode("pass"))  //welchen Benutzer und welches PAssword will ich benutzten
                .authorities("ROLE_USER");
    }

    @Override  //methode "configure wird hiermit überschrieben
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/swagger-ui.html").permitAll()  //keine Einschränkungen bei der Webseite/url
                .antMatchers("/secure-demo/public").permitAll() //keine Einschränkungen bei der Webseite/url
                .antMatchers("/secure-demo/private").authenticated()  //Einschränkungen bei der Webseite/url
                .and()
                .httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}