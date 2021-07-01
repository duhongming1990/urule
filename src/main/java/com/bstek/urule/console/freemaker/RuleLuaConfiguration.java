package com.bstek.urule.console.freemaker;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.IOException;
/**
 * @Author duhongming
 * @Email duhongming@lixiang.com
 * @Date 2021/7/1 15:50
 * @Description 
 */
public class RuleLuaConfiguration extends Configuration implements FreeMakerTemplate {

    /**
     * 模板路径
     */
    private static final String TEMPLATE_DIR = "/templates/rule/lua/";
    /**
     * 模板起始文件
     */
    private static final String TEMPLATE_FILE = "index.ftl";

    public RuleLuaConfiguration() {
        // 设置字符集
        setDefaultEncoding("UTF-8");
        // 加载模板数据
        setClassForTemplateLoading(RuleLuaConfiguration.class, TEMPLATE_DIR);
    }

    /**
     * 获取模板对象
     *
     * @return
     * @throws IOException
     */
    @Override
    public Template getTemplate() throws IOException {
        return super.getTemplate(TEMPLATE_FILE);
    }

    @Override
    public String processTemplate(Object data) {
        return null;
    }
}