package cn.xs.erp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;

@SpringBootApplication(exclude = PageHelperAutoConfiguration.class)
@EnableTransactionManagement
@ServletComponentScan(basePackages="cn.xs.erp.*")
public class XsErpApplication {
	
	public static void main(String[] args) {

		SpringApplication application = new SpringApplication(XsErpApplication.class);
		application.addListeners(new ApplicationEventListener());
		application.run(args);
	}

}
