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
package com.bstek.urule.runtime.builtinaction;

import com.bstek.urule.model.library.action.annotation.ActionBean;
import com.bstek.urule.model.library.action.annotation.ActionMethod;
import com.bstek.urule.model.library.action.annotation.ActionMethodParameter;

/**
 * @Author duhongming
 * @Email duhongming@lixiang.com
 * @Date 2021/6/25 9:34
 * @Description 信号派生
 */
@ActionBean(name = "信号派生")
public class SignalDerivationAction {

    @ActionMethod(name = "计时器")
    @ActionMethodParameter(names = {"信号名", "开始触发条件", "结束触发条件"})
    public String timer(String signalName, String startTriggerCondition, String endTriggerCondition) {
        return signalName + "_timer";
    }
}
