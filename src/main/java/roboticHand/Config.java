package roboticHand;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import roboticHand.DAO.ActionRepository;
import roboticHand.DAO.QuestionRepository;
import roboticHand.DAO.UserRepository;
import roboticHand.Service.ActionImpl;
import roboticHand.Service.QuestionImpl;
import roboticHand.Service.UserServiceImpl;

//@EnableWebMvc
@Configuration
public class Config implements WebMvcConfigurer {


  @Bean
  public InternalResourceViewResolver viewResolver() {
    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
    resolver.setPrefix("/WEB-INF/pages/");
    resolver.setSuffix(".jsp");
    return resolver;
  }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");

    }

    @Bean
    public UserRepository userRepository(){
        return new UserServiceImpl();
    }

    @Bean
    public ActionRepository actionRepository() {
      return new ActionImpl();
    }

    @Bean
    public QuestionRepository questionRepository() {
        return new QuestionImpl();
    }
}