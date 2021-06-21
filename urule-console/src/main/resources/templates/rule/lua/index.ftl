-- define sense module
mud_sense = {}

<#include "rule-param.ftl">
<#include "rule-invoke.ftl">

-- define entry function
function invoke(...)
<#list rules as rule>
    mud_sense.func${rule?counter}(<#list rule.lhs.parameters as parameter> ${parameter}<#sep>,</#sep></#list>)
</#list>
end