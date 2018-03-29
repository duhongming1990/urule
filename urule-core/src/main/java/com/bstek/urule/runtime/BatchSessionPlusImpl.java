/*******************************************************************************
 * Copyright 2017 Bstek
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package com.bstek.urule.runtime;

import com.bstek.urule.RuleException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author dhm
 * @since 2018年3月29日
 */
public class BatchSessionPlusImpl implements BatchSessionPlus {

	private ExecutorService executorService;
	private int batchSize;
	private List<Business> businessList = new ArrayList<Business>();
	private KnowledgePackage knowledgePackage;
	private KnowledgePackage[] knowledgePackages;
	private KnowledgeSession session;
	private CountDownLatch countDownLatch;
	private Semaphore semaphore;

	public BatchSessionPlusImpl(KnowledgePackage knowledgePackage, int threadSize, int batchSize) {
		this.executorService = Executors.newFixedThreadPool(threadSize);
		this.countDownLatch = new CountDownLatch(threadSize);
		this.semaphore = new Semaphore(batchSize);
		this.session=KnowledgeSessionFactory.newKnowledgeSession(knowledgePackage);
		this.knowledgePackage = knowledgePackage;
		this.batchSize = batchSize;
	}

	public BatchSessionPlusImpl(KnowledgePackage[] knowledgePackages, int threadSize, int batchSize) {
		this.executorService = Executors.newFixedThreadPool(threadSize);
		this.countDownLatch = new CountDownLatch(threadSize);
		this.semaphore = new Semaphore(batchSize);
		this.session=KnowledgeSessionFactory.newKnowledgeSession(knowledgePackage);
		this.knowledgePackages = knowledgePackages;
		this.batchSize = batchSize;
	}

	@Override
	public void addBusiness(Business business) {
		businessList.add(business);
	}

	private void doBusinesses() {
		for(Business business:businessList){
			try {
				BatchThreadPlus thread = null;
				if (knowledgePackage != null) {
					thread = new BatchThreadPlus(knowledgePackage, business,semaphore);
				} else if (knowledgePackages != null) {
					thread = new BatchThreadPlus(knowledgePackages, business,semaphore);
				} else {
					throw new RuleException("KnowledgePackage can not be null.");
				}
				executorService.execute(thread);
			}finally {
				countDownLatch.countDown();;
			}
		}
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		executorService.shutdown();
	}
}
