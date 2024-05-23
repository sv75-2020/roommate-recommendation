package com.ftn.sbnz.service.config;

import com.ftn.sbnz.service.security.TokenAuthenticationFilter;
import com.ftn.sbnz.service.security.RestAuthenticationEntryPoint;
import com.ftn.sbnz.model.services.AdminUserService;
import com.ftn.sbnz.model.services.UserService;
import com.ftn.sbnz.service.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig {
    @Bean
    public AdminUserService userDetailsService() {
        return new AdminUserService();
    }



    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // 1. koji servis da koristi da izvuce podatke o korisniku koji zeli da se autentifikuje
        // prilikom autentifikacije, AuthenticationManager ce sam pozivati loadUserByUsername() metodu ovog servisa
        authProvider.setUserDetailsService(userDetailsService());
        // 2. kroz koji enkoder da provuce lozinku koju je dobio od klijenta u zahtevu
        // da bi adekvatan hash koji dobije kao rezultat hash algoritma uporedio sa onim koji se nalazi u bazi (posto se u bazi ne cuva plain lozinka)
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }



    // Handler za vracanje 401 kada klijent sa neodogovarajucim korisnickim imenom i lozinkom pokusa da pristupi resursu
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;


    // Registrujemo authentication manager koji ce da uradi autentifikaciju korisnika za nas
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // Injektujemo implementaciju iz TokenUtils klase kako bismo mogli da koristimo njene metode za rad sa JWT u TokenAuthenticationFilteru
    @Autowired
    private TokenUtils tokenUtils;

    // Definisemo prava pristupa za zahteve ka odredjenim URL-ovima/rutama
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // Disable CSRF protection as it's not needed for stateless authentication
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No session will be created by Spring Security
        .and()
        .authorizeRequests()
        .anyRequest().permitAll() // Allow all requests without authentication
        .and()
        .addFilterBefore(new TokenAuthenticationFilter(tokenUtils, userDetailsService()), BasicAuthenticationFilter.class); // Your custom filter

        return http.build();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // Autentifikacija će biti ignorisana ispod navedenih putanja (kako bismo ubrzali pristup resursima)
        // Zahtevi koji se mečuju za web.ignoring().antMatchers() nemaju pristup SecurityContext-u
        // Dozvoljena POST metoda na ruti /api/user/login, za svaki drugi tip HTTP metode greska je 401 Unauthorized
        return (web) -> web.ignoring()
                .antMatchers(HttpMethod.GET, "/api/login")
                .antMatchers(HttpMethod.POST, "/api/user/register")
                .antMatchers(HttpMethod.POST, "/api/ingredient")

                // Ovim smo dozvolili pristup statickim resursima aplikacije
                .antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "/favicon.ico",
                        "/*/*.html", "/*/*.css", "/*/*.js");
    }
}
