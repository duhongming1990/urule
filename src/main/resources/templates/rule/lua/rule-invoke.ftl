
-- generate method call

-- ##### define scene vehicle control execution function start #####
<#list rules as rule>
<#if !(rule.enabled??) || rule.enabled == true>

-- 规则名称:${rule.name},规则描述:${rule.remark}
function mud_sense.func${rule?counter}(<#list rule.lhs.parameters as parameter>${parameter}<#sep>,</#sep></#list>)
    -- 如果
    if (${rule.lhs.expression}) then
        -- 那么
        <#if rule.rhs?? && rule.rhs.actions??>
        <#list rule.rhs.actions as o>
            <#include "rule-action.ftl">
        </#list>
        </#if>
    else
        -- 否则
        <#if rule.other?? && rule.other.actions??>
        <#list rule.other.actions as o>
            <#include "rule-action.ftl">
        </#list>
        </#if>
    end
end
</#if>
</#list>

-- ##### define scene vehicle control execution function end #####
