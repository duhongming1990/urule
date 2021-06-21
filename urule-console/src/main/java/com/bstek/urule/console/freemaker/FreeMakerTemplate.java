package com.bstek.urule.console.freemaker;

import freemarker.template.Template;

import java.io.IOException;

public interface FreeMakerTemplate {
    /**
     * 获取FreeMaker模板对象
     *
     * @return
     * @throws IOException
     */
    Template getTemplate() throws IOException;

    /**
     * 处理模板
     *
     * @param data
     * @return
     */
    String processTemplate(Object data);
}
