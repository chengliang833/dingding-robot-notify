package top.ulane.dingdingrobotnotify.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import wang.ulane.limitalgorithm.slide.NumSlider;

//@Component
@Configuration
//@PropertySource("classpath:application.properties")//Environment搞不进来，用EnvironmentAware吧
public class NumSliderBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor, EnvironmentAware {
	private static final Logger log = LoggerFactory.getLogger(NumSliderBeanDefinitionRegistryPostProcessor.class);
	
	private Environment environment;
	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
	
	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		//可选1，通过BeanDefinitionRegistry
		//可以改beandefinition
		log.info("into custom postProcessBeanDefinitionRegistry...");
		String[] beanNames = environment.getProperty("limiter.numSlider.beans").split(",");
		for(String beanName:beanNames){
			String timeLimit = environment.getProperty("limiter.numSlider."+beanName+".timeLimit");
			String maxSize = environment.getProperty("limiter.numSlider."+beanName+".maxSize");
			BeanDefinitionBuilder b = BeanDefinitionBuilder.genericBeanDefinition(NumSlider.class)
					.addPropertyValue("timeLimit", timeLimit)
					.addPropertyValue("maxSize", maxSize);
			registry.registerBeanDefinition(beanName, b.getBeanDefinition());
		}
	}
	
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		//可选2，通过BeanFactory
		//也可通过单独implements BeanFactoryPostProcessor后通过BeanFactory
		//bean已经生成
//		log.info("into custom postProcessBeanFactory...");
//		String[] beanNames = environment.getProperty("limiter.numSlider.beans").split(",");
//		for(String beanName:beanNames){
//			String timeLimit = environment.getProperty("limiter.numSlider."+beanName+".timeLimit");
//			String maxSize = environment.getProperty("limiter.numSlider."+beanName+".maxSize");
//			beanFactory.registerSingleton(beanName, new NumSlider(Long.parseLong(timeLimit), Integer.parseInt(maxSize)));
//		}
	
	}
	
	//第二次尝试
//	private String[] beans;
//	@Value("${limiter.numSlider.beans}")
//	private void setBeansStr(String beansStr){
//		this.beans = beansStr.split(",");
//	}
//	@Autowired
//	private ConfigurableEnvironment environment;
	
	
	//第一次尝试
//	@Value("${limiter.numSlider.maxSize}")
//	private int maxSize;
//	@Value("${limiter.numSlider.timeLimit}")
//	private long timeLimit;
//	@Bean
//	@Qualifier("sendMsgNumSlider")
//	public NumSlider numSlider(){
//		NumSlider numSlider = new NumSlider();
//		numSlider.setMaxSize(maxSize);
//		numSlider.setTimeLimit(timeLimit);
//		return numSlider;
//	}

}
