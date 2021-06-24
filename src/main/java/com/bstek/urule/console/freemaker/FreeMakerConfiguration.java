package com.bstek.urule.console.freemaker;

import lombok.Getter;


@Getter
public enum FreeMakerConfiguration {

    RULE_LUA_GENERATE("规则引擎Lua脚本生成", new RuleLuaConfiguration()),
    ;

    FreeMakerConfiguration(String name, FreeMakerTemplate freeMakerTemplate) {
        this.name = name;
        this.freeMakerTemplate = freeMakerTemplate;
    }

    /**
     * 模板名称
     */
    private String name;

    /**
     * 模板对象
     */
    private FreeMakerTemplate freeMakerTemplate;
}