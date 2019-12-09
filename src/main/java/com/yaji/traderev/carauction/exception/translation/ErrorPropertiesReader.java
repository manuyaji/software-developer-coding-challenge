package com.yaji.traderev.carauction.exception.translation;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.yaji.traderev.carauction.config.LocaleConfig;
import com.yaji.traderev.carauction.util.LocaleUtil;

public class ErrorPropertiesReader implements InitializingBean{
	
	private Map<Locale, ErrorProperties> localeErrorPropertiesMap;
	@Autowired
	private LocaleConfig localeConfig;
	
	@Autowired
	private LocaleUtil localeUtil;

	@Override
	public void afterPropertiesSet() throws Exception {
		localeErrorPropertiesMap = new HashMap<>();
		for(String localeStr: localeConfig.getListOfSupportedLocales()){
			Locale locale = localeUtil.createLocaleFromStr(localeStr);
			ErrorProperties ep = new ErrorProperties(locale);
			localeErrorPropertiesMap.put(locale, ep);
		}
	}
	
	public ErrorProperties getErrorProperties(Locale locale){
		return localeErrorPropertiesMap.get(locale);
	}
	
	
}
