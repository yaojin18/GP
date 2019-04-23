package cn.com.gp.pattern.strategy;

public interface TemplateAnalysisService {

	/**
	 * 根据传入的类型解析数据
	 * @param source
	 * @param clazz
	 * @return
	 */
	public <T> T parse(Object source, Class<T> clazz); 
}
