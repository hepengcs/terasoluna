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

package jp.terasoluna.fw.service.rich;

/**
 * TERASOLUNAが定義する
 * サービス層のクラスが実装することが出来るインタフェース。
 * 
 * <p>
 * 業務開発者は、業務ロジックのパラメータである任意の型のJavaBeanを受け取り、
 * 業務ロジック結果の任意の型のJavaBeanを返却する処理を実装すること。
 * </p>
 * 
 * <p>
 * トランザクション境界となるため、あらかじめ定義した
 * トランザクションプロキシの抽象定義を継承してBean定義を行うこと。
 * また、回復不可能なエラーが発生した場合は、
 * プロジェクトで定められたルールで任意の例外をスローすること。
 * </p>
 * 
 * <p>
 * 【Bean定義例】<br>
 * <code><pre>
 *   &lt;bean id="maxBLogic" parent="baseTransactionProxy"&gt;
 *     &lt;property name="target"&gt;
 *       &lt;bean class="jp.terasoluna.sample2.service.blogic.MaxBLogic"/&gt;
 *     &lt;/property&gt;
 *   &lt;/bean&gt;
 * </pre></code>
 *   ※　baseTransactionProxyは、トランザクションプロキシのベース定義。
 * </p>
 * 
 * <p>
 * 通常、サービス層のクラスは、
 * DIコンテナを利用した場合、1つのサービスに対して1つのインタフェースとする、
 * フレームワーク特定のインタフェースに依存しないPOJOであることが望ましいが、
 * TERASOLUNAでは、1つのインタフェースで複数のサービスを実現する仕組みも用意している。
 * <code>BLogic</code>インタフェースを実装したクラスを利用すると、
 * プレゼンテーション層は、サービス層のクラスを統一的に扱うことが出来るため、
 * リクエストごとにプレゼンテーション層の呼び出しクラスを実装する必要がなくなるメリットがある。
 * </p>
 * 
 * <p>
 * プレゼンテーション層からの呼び出しについては、
 * <code>BLogicController</code>を参考にすること。
 * </p>
 * <p>
 * {@link jp.terasoluna.fw.web.rich.springmvc.controller.BLogicController}
 * クラスでは、実装クラスのexecuteメソッドの引き数の型をもとに、コマンドオブジェクトの型を自動判定している。
 * よって実装クラスでは、「execute」という名称のメソッドは、本インタフェースを実装した1メソッドのみ定義し、
 * オーバーロード(メソッド名が同一で引数の型、数、並び順が異なるメソッドを複数定義)しないこと。
 * </p>
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.controller.BLogicController
 * 
 * @param <P> 業務パラメータ
 * @param <R> 業務結果
 */
public interface BLogic < P , R > {

    /**
     * 業務ロジックを実行する。
     * 
     * <p>
     * 実装クラスにて本メソッド以外に
     * 引数の型、数が異なるexecuteメソッドを定義してはならない。
     * </p>
     * 
     * @param params 業務処理パラメータ
     * @return 業務処理結果
     */
     R execute(P params);
}
