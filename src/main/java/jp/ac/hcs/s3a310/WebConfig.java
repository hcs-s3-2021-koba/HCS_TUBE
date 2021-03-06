package jp.ac.hcs.s3a310;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.client.RestTemplate;

public class WebConfig {
	/** 出力パス */
	public static final String OUTPUT_PATH = "target/";

	/** タスク情報のCSVファイル名 */
	public static final String FILENAME_TASK_CSV = "reportlist.csv";

	/** タスク情報のCSVファイル名 */
	public static final String FILENAME_APPLICATION_CSV = "applicationlist.csv";

	@Bean
	public MessageSource messageSource() {

		//メッセージプロパティのファイル設定
		ReloadableResourceBundleMessageSource bean = new ReloadableResourceBundleMessageSource();
		bean.setBasename("classpath:messages");
		bean.setDefaultEncoding("UTF-8");

		return bean;
	}

	@Bean
	public LocalValidatorFactoryBean localValidatorFactoryBean() {

		// バリデーションのメッセージ設定
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.setValidationMessageSource(messageSource());

		return localValidatorFactoryBean;
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
