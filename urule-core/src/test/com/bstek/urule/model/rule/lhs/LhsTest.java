package com.bstek.urule.model.rule.lhs;

import com.bstek.urule.model.rule.RuleSet;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class LhsTest {
    @SneakyThrows
    @Test
    public void test() {
        Resource resource = new ClassPathResource("urule.json");
        String s = IOUtils.toString(resource.getInputStream());
//        Lhs lhs = JSON.parseObject(s, Lhs.class);
        ObjectMapper objectMapper = new ObjectMapper();
        RuleSet ruleSet = objectMapper.readValue(s, RuleSet.class);
        System.out.println("ruleSet = " + ruleSet);
    }

}
