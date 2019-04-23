package cn.com.gp.pattern.strategy;

public class TemplateAnalysisByBean implements TemplateAnalysisService {

	@Override
	public <T> T parse(Object source, Class<T> clazz) {
		
		System.out.println("这是解析成Bean的数据格式");
		return null;
	}

}
