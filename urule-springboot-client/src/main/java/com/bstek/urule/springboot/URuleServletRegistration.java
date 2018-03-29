package com.bstek.urule.springboot;

import com.bstek.urule.KnowledgePackageReceiverServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author Jacky.gao
 * @since 2016年10月12日
 */
@Component
public class URuleServletRegistration {
	@Bean
	public ServletRegistrationBean registerURuleServlet(){
		return new ServletRegistrationBean(new KnowledgePackageReceiverServlet(),
				"/knowledgepackagereceiver");
	}
}
