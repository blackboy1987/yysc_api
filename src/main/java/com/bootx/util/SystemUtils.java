
package com.bootx.util;

import com.bootx.common.Template;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.util.Assert;

/**
 * @author black
 */
public final class SystemUtils {

	/**
	 * 不可实例化
	 */
	private SystemUtils() {
	}


	/**
	 * 获取模板配置
	 * 
	 * @param id
	 *            ID
	 * @return 模板配置
	 */
	public static Template getTemplateConfig(String id) {
		Assert.hasText(id, "[Assertion failed] - id must have text; it must not be null, empty, or blank");

		Template template = null;
		try {
			Document document = new SAXReader().read(SystemUtils.class.getResourceAsStream("/shopxx.xml"));
			org.dom4j.Element element = (org.dom4j.Element) document.selectSingleNode("/shopxx/template[@id='" + id + "']");
			if (element != null) {
				template = getTemplateConfig(element);
			}
		} catch (DocumentException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
        return template;
	}


	/**
	 * 获取模板配置
	 * 
	 * @param element
	 *            元素
	 * @return 模板配置
	 */
	private static Template getTemplateConfig(org.dom4j.Element element) {
		Assert.notNull(element, "[Assertion failed] - element is required; it must not be null");

		String id = element.attributeValue("id");
		String type = element.attributeValue("type");
		String name = element.attributeValue("name");
		String templatePath = element.attributeValue("templatePath");
		String staticPath = element.attributeValue("staticPath");
		String description = element.attributeValue("description");


		Template template = new Template();
		template.setId(id);
		template.setType(Template.Type.valueOf(type));
		template.setName(name);
		template.setTemplatePath(templatePath);
		template.setStaticPath(staticPath);
		template.setDescription(description);
		return template;
	}

}