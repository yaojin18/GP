package cn.com.gp.pattern.strategy;

import java.util.Map;

public class TemplateAnalysisTest {

	public static void main(String[] args) {
		
		/**
		 * 真实业务中，需要将某一数据解析成Map和实体两种数据格式 
		 */
		new TemplateAnalysis().parse(null, Map.class);
	}
}
