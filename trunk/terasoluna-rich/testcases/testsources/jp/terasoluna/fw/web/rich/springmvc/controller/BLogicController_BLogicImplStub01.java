/*
 * Copyright (c) 2007 NTT DATA Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.terasoluna.fw.web.rich.springmvc.controller;

import jp.terasoluna.fw.service.rich.AbstractBLogic;

/**
 * BLogicインタフェース実装クラスのスタブ。
 */
public class BLogicController_BLogicImplStub01
    extends AbstractBLogic<Object, Object> {
    protected Object command = null;

    /**
     * executeメソッドのスタブ。
     * @param command パラメータオブジェクト。
     * @return 引数を返す。
     */
    public Object execute(Object command) {
        this.command = command;
        return command;
    }

}
