package com.twitter.TwitterEduApp;

import org.apache.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import com.twitter.TwitterEduApp.configurations.PictureUploadProperties;
import com.twitter.TwitterEduApp.learning.MyPrototypeBean;
import org.springframework.context.ApplicationContext;
import com.twitter.TwitterEduApp.learning.MySingletonBean;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
@EnableConfigurationProperties({PictureUploadProperties.class})
public class TwitterEduAppApplication {

	final static Logger logger = Logger.getLogger(TwitterEduAppApplication.class);

	@Autowired
	private ApplicationContext appContext;

	public static void main(String[] args) {
		SpringApplication.run(TwitterEduAppApplication.class, args);
		logger.info("This is info from log4j TwitterEduAppApplication ");
	}

	@Bean
	public CommandLineRunner run(ApplicationContext appContext) {
		return args -> {

			logger.info("This is info from log4j : ");

			MySingletonBean bean = appContext.getBean(MySingletonBean.class);
			bean.showMessage();
			System.out.println(bean + " MySingletonBean");

			Thread.sleep(1000);

			bean = appContext.getBean(MySingletonBean.class);
			bean.showMessage();
			System.out.println(bean + " MySingletonBean");

			MyPrototypeBean myPrototypeBean =  appContext.getBean(MyPrototypeBean.class);
			System.out.println(myPrototypeBean.getDateTime() + myPrototypeBean);

			Thread.sleep(1000);

			MyPrototypeBean myPrototypeBean2 =  appContext.getBean(MyPrototypeBean.class);
			System.out.println(myPrototypeBean2.getDateTime() + myPrototypeBean2);

		};
	}
}
