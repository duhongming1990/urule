
-- 生成方法调用

<#list ruleSet as ruleSetBean>
-- 定义场景车控执行函数
function mud_sense.func${ruleSetBean?counter}(param1)
    <#assign rule = ruleSetBean.rule>
    if (${rule.conditionExpression}) then
    <#list rule.operation as o>
        <#include "rule-action.ftl">
    </#list>
    else
    <#list rule.otherwise as o>
        <#include "rule-action.ftl">
    </#list>
    end
end
</#list>