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

import java.util.concurrent.Semaphore;

/**
 * @author Jacky.gao
 * @since 2015年9月29日
 */
public class BatchThreadPlus implements Runnable {

	private Semaphore semaphore;
	private Business business;
	private KnowledgeSession session;

	public BatchThreadPlus(KnowledgePackage knowledgePackage, Business business, Semaphore semaphore) {
		session = KnowledgeSessionFactory.newKnowledgeSession(knowledgePackage);
		this.business = business;
		this.semaphore = semaphore;
	}
	public BatchThreadPlus(KnowledgePackage[] knowledgePackages,Business business,Semaphore semaphore) {
		session = KnowledgeSessionFactory.newKnowledgeSession(knowledgePackages);
		this.business = business;
		this.semaphore = semaphore;
	}

	@Override
	public void run() {
		Thread thread=Thread.currentThread();
		String oldThreadName=thread.getName();
		thread.setName("urule-"+oldThreadName);
		try{
			semaphore.acquire();
			business.execute(session);
			semaphore.release();
		}catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			thread.setName(oldThreadName);
		}
	}
}
