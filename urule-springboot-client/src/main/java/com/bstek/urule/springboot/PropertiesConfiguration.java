package com.bstek.urule.springboot;

import com.bstek.urule.URulePropertyPlaceholderConfigurer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @author Jacky.gao
 * @since 2016年10月12日
 */
@Component
public class PropertiesConfiguration extends URulePropertyPlaceholderConfigurer implements InitializingBean{
	@Override
	public void afterPropertiesSet() throws Exception {
		Properties props = new Properties();
		props.setProperty("urule.prop", "classpath:configure.properties");
		setProperties(props);
	}
}
