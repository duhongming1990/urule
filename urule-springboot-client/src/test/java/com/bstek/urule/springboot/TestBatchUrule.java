package com.bstek.urule.springboot;

import com.bstek.urule.Utils;
import com.bstek.urule.runtime.*;
import com.bstek.urule.runtime.service.KnowledgeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestBatchUrule {

    @Test
    public void test() throws IOException {

        //从Spring中获取KnowledgeService接口实例
        KnowledgeService service = (KnowledgeService) Utils.getApplicationContext().getBean(KnowledgeService.BEAN_ID);
        //通过KnowledgeService接口获取指定的资源包"aaa"
        KnowledgePackage knowledgePackage = service.getKnowledge("mydemo/demo");

        //通过取的KnowledgePackage对象创建BatchSession对象,在这个对象中，我们将开启5个线程，每个线程最多放置10个Bussiness接口实例运行
//        BatchSession batchSession = KnowledgeSessionFactory.newBatchSession(knowledgePackage, 5, 10);
        BatchSessionPlus batchSessionPlus = KnowledgeSessionFactory.newBatchSessionPlus(knowledgePackage);
        System.out.println(batchSessionPlus);
        for (int i = 0; i < 100; i++) {
            batchSessionPlus.addBusiness(new Business() {
                @Override
                public void execute(KnowledgeSession session) {
//                    Employee employee = new Employee();
//                    employee.setSalary(11080);
//                    //将业务数据对象Employee插入到KnowledgeSession中
                    session.insert(new Object());
                    session.startProcess("demo");
                }
            });
        }
    }
}
