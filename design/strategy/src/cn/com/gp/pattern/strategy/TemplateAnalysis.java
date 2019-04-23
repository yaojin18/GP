package cn.com.gp.pattern.strategy;

import java.util.HashMap;
import java.util.Map;

public class TemplateAnalysis {

	
	private static Map<String, TemplateAnalysisService> analysis = new HashMap<String, TemplateAnalysisService>();
	
	static {
		analysis.put(Map.class.getSimpleName(), new TemplateAnalysisByMap());
		analysis.put(Bean.class.getSimpleName(), new TemplateAnalysisByBean());
	}
	
	public <T> T parse(Object source, Class<T> targetClazz) {
		return analysis.get(targetClazz.getSimpleName()).parse(source, targetClazz);
	}
	
}
