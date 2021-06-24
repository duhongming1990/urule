
-- generate method call

-- ##### define scene vehicle control execution function start #####
<#list rules as rule>
<#if rule.enabled==true>

-- 规则名称:${rule.name},规则描述:${rule.remark}
function mud_sense.func${rule?counter}(param1)
    -- 如果
    if (${rule.lhs.expression}) then
        -- 那么
        <#list rule.rhs.actions as o>
            <#include "rule-action.ftl">
        </#list>
    else
        -- 否则
        <#list rule.other.actions as o>
            <#include "rule-action.ftl">
        </#list>
    end
end
</#if>
</#list>

-- ##### define scene vehicle control execution function end #####
