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

package jp.terasoluna.fw.batch.openapi;


/**
 * ジョブ前・後、親ジョブ前・後、先頭チャンク前、最終チャンク後処理用インタフェース。<br>
 * <br>
 * <strong>設定例</strong><br>
 * 開発者が作成した前・後処理をジョブBean定義ファイルに設定する。<br>
 * <br>
 * <p>
 * 【ジョブ後処理の設定例】<br>
 * 対象データの処理が終了した後、かつ通常ジョブおよび子ジョブが終了する前に実行
 * される。</p>
 * <pre>
 * &lt;bean id=&quot;jobPostLogicList&quot;
 *    class=&quot;org.springframework.beans.factory.config.ListFactoryBean&quot;&gt;
 *    &lt;property name=&quot;sourceList&quot;&gt;
 *        &lt;list&gt;
 *            &lt;bean
 *                class=&quot;jp.terasoluna.batch.sample.checksample.Sample01PostLogic&quot;&gt;
 *                &lt;property name=&quot;queryDAO&quot; ref=&quot;queryDAO&quot; /&gt;
 *                &lt;property name=&quot;updateDAO&quot; ref=&quot;updateDAO&quot; /&gt;
 *            &lt;/bean&gt;
 *            &lt;bean
 *                class=&quot;jp.terasoluna.batch.sample.checksample.Sample02PostLogic&quot;&gt;
 *                &lt;property name=&quot;queryDAO&quot; ref=&quot;queryDAO&quot; /&gt;
 *                &lt;property name=&quot;updateDAO&quot; ref=&quot;updateDAO&quot; /&gt;
 *            &lt;/bean&gt;
 *        &lt;/list&gt;
 *    &lt;/property&gt;
 * &lt;/bean&gt;
 * </pre>
 * 
 * <p>
 * 【親ジョブ後処理の設定例】<br>
 * 全ての分割キーの処理が終了後（全ての子ジョブが終了後）に行われる。
 * </p>
 * <pre>
 * &lt;bean name=&quot;parentjobPostLogicList&quot; class=&quot;org.springframework.beans.factory.config.ListFactoryBean&quot;&gt;
 *    &lt;property name=&quot;sourceList&quot;&gt;
 *        &lt;list&gt;
 *            &lt;bean
 *                class=&quot;jp.terasoluna.batch.sample.checksample.dummy.DummySample01PostLogic&quot;&gt;
 *            &lt;/bean&gt;
 *        &lt;/list&gt;
 *    &lt;/property&gt;
 * &lt;/bean&gt;
 * </pre>
 *
 * <p>
 * 【最終チャンク後処理の設定例】<br>
 * 最終チャンクの処理が終了後、ジョブ後処理が起動される前
 * （ジョブ後処理が設定されていた場合）に起動される。
 * </p>
 * <pre>
 * &lt;bean id=&quot;lastchunkPostLogicList&quot;
 *    class=&quot;org.springframework.beans.factory.config.ListFactoryBean&quot;&gt;
 *    &lt;property name=&quot;sourceList&quot;&gt;
 *        &lt;list&gt;
 *            &lt;bean
 *                class=&quot;jp.terasoluna.batch.sample.checksample.dummy.DummySample01PostLogic&quot;&gt;
 *            &lt;/bean&gt;
 *        &lt;/list&gt;
 *    &lt;/property&gt;
 * &lt;/bean&gt;
 * </pre>
 *
 * @param <T> ジョブコンテキストのパラメータクラス
 */
public interface SupportLogic<T extends JobContext> {

    /**
     * ジョブ後処理を実行する。
     *
     * @param jobContext ジョブコンテキスト
     * @return ジョブ後処理結果
     */
    BLogicResult execute(T jobContext);
}
