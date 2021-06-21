<#list o?keys as key>
    <#if key=="consolePrint">
        if isDebug then
            print("${ruleSetBean.ruleName}: ${o[key]}")
        end
    </#if>
</#list>