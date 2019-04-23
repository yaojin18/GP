package cn.com.gp.pattern.strategy;

//模板解析成Map格式数据
public class TemplateAnalysisByMap implements TemplateAnalysisService {

	@Override
	public <T> T parse(Object source, Class<T> clazz) {
		System.out.println("这是解析成Map的数据格式");
		
		return null;
	}

}
