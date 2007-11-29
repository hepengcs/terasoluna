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

package jp.terasoluna.fw.util;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;


/**
 * {@link GenericsUtil}のテストのためのスタブクラス。
 * 
 */
public class GenericsUtil_FutureTaskStub01<V> extends FutureTask<V>{

    /**
     * @param callable
     */
    public GenericsUtil_FutureTaskStub01(Callable<V> callable) {
        super(callable);
        // TODO Auto-generated constructor stub
    }

}
