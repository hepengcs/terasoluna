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

package jp.terasoluna.fw.web.rich.context.support;

import javax.servlet.http.HttpServletRequest;

/**
 * 制御情報を扱うクラスが実装すべきインタフェース。
 * 
 * <p>
 * フレームワークやサービス層のクラスは、本インタフェースを
 * 呼び出して、制御情報を参照することが出来る。
 * </p>
 * 
 * <p>
 * 本インタフェースを呼び出す際は、本インタフェースの実装クラスをDIコンテナより設定して利用する。
 * 通常は実装クラスとして、DefaultRequestContextSupportImplを利用すればよい。
 * DefaultRequestContextSupportImplでは業務の要件が満たせない場合にのみ、
 * 抽象クラスAbstractRequestContextSupportまたは本インタフェースを実装した
 * 業務要件を満たすクラスを作成し、利用すること。
 * </p>
 * 
 * <p>
 * 制御情報を参照するクラスは、以下のようにBean定義すること。
 * </p>
 * 
 * <p>
 * 【<code>Bean定義ファイル</code>の設定例】<br>
 * <code><pre>
 *  &lt;bean id="xxxx" class="xxxx.Xxx"&gt;
 *    &lt;property name="ctxSupport" ref="ctxSupport"/&gt;
 *  &lt;/bean&gt;
 *  ※ctxSupportは、RequestContextSupport実装クラスのBean定義。
 *  詳細は、本インタフェースの実装クラスを参照すること。
 * </pre></code>
 * </p>
 * 
 * <p>
 * 制御情報を参照する際の実装を、以下にしめす。
 * </p>
 * 
 * <p>
 * 【<code>Xxx.java</code>の実装例】<br>
 * <code><pre>
 * public class Xxx {
 *   // 属性で定義
 *   RequestContextSupport ctxSupport;
 *   // セッターを用意(DIコンテナにより実行される)
 *   public void setCtxSupport(RequestContextSupport ctxSupport) {
 *       this.ctxSupport = ctxSupport;
 *   }
 *   // 制御情報を参照するロジック
 *   protected void execute(String urlPath) {
 *   　        ・・・・
 *       String requestName = ctxSupport.getRequestName();
 *       ・・・・
 *   }
 * }
 * </pre></code>
 * </p>
 */
public interface RequestContextSupport {
    /**
     *　リクエスト名を取得する。
     *
     * @return リクエスト名
     */
    String getRequestName();

    /**
     * 業務プロパティを取得する。
     * システムごとに独自に保持したい情報は、このメソッドで取得する。
     *
     * @param key キー
     * @return 業務プロパティ
     */
    Object getProperty(String key);


    /**
     * 返却値の型を指定して、業務プロパティを取得する。
     * 業務ごとに独自に保持したい情報は、このメソッドで取得する。
     *
     * @param key キー
     * @param <E> 返却値の型
     * @return 業務プロパティ
     */
    <E> E getProperty(String key, Class<E> clazz);
    
    /**
     * String型の業務プロパティを取得する。
     * 業務ごとに独自に保持したい情報は、このメソッドで取得する。
     *
     * @param key キー
     * @return 業務プロパティ
     */
    String getPropertyString(String key);
    
    
    /**
     * 制御情報を生成する。
     * @param request HTTPリクエスト
     */
    void generateContext(HttpServletRequest request);
    
    /**
     * 制御情報を破棄する。
     */
    void destroyContext();
}
