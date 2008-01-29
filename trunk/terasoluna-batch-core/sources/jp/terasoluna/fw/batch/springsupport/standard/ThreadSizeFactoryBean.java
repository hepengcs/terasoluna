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

package jp.terasoluna.fw.batch.springsupport.standard;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * スレッドプールのサイズを生成するクラス。
 * 分割ジョブの多重度×２をスレッドプールサイズとして返す。
 * 
 */
public class ThreadSizeFactoryBean implements FactoryBean, InitializingBean {

    /**
     * 多重度
     */
    private int multiplicity = 0;

    /**
     * 生成するスレッド数。
     */
    private Integer threadSize = null;

    /**
     * <code>FactoryBean</code>の実装。
     * 生成するスレッド数のインスタンスを返す。
     * 
     * @return 生成するスレッド数
     */
    public Object getObject() {
        return this.threadSize;
    }

    /**
     * <code>FactoryBean</code>の実装。
     * <code>FactoryBean</code>から生成されるインスタンスのタイプ。
     * 
     * @return Integer.class
     */
    public Class getObjectType() {
        return Integer.class;
    }

    /**
     * <code>FactoryBean</code>の実装。
     * <code>Singleton</code>であるかを評価する。
     * 
     * @return <code>true</code>
     */
    public boolean isSingleton() {
        return true;
    }

    /**
     * 多重度を設定する。
     * 
     * @param multiplicity 多重度
     */
    public void setMultiplicity(int multiplicity) {
        this.multiplicity = multiplicity;
    }

    /**
     * <code>InitializingBean</code>の実装。
     * インスタンス作成時の初期実行。
     * 多重度×２を<code>threadSize</code>に設定する。
     * 
     */
    public void afterPropertiesSet() {
//        this.threadSize = new Integer(multiplicity * 2);
        this.threadSize = Integer.valueOf(multiplicity * 2);
    }

}
