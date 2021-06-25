<#if o.actionType=="ConsolePrint" && (!(rule.debug??) || rule.debug == true)>
        -- print console
        print("${o.value.content}")
</#if>
<#if o.actionType=="ExecuteCommonFunction">
        -- ${o.parameter.name}
        ${o.name}(${o.parameter.objectParameter.content})
</#if>
<#if o.actionType=="VariableAssign">
        -- ${o.variableLabel}
        ${o.variableName} = ${o.value.content}
</#if>
