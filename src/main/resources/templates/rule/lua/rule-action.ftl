<#if o.actionType=="ConsolePrint" && rule.debug==true>
        print("${o.value.content}")
</#if>
<#if o.actionType=="ExecuteCommonFunction">
        -- ${o.parameter.name}
        ${o.name}(${o.parameter.objectParameter.content})
</#if>
