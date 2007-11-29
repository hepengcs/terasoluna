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

package jp.terasoluna.fw.web.struts.actions;

import jp.terasoluna.fw.service.thin.BLogic;
import jp.terasoluna.fw.service.thin.BLogicResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * <p> 
 *  本クラスは、BLogicの起動を行うクラスである。<br>
 *  Actionから直接BLogicの起動を行う。EJBには対応していない。
 * </p>
 * <p>
 *  BLogic実装クラスを実行するためには、
 *  Bean定義ファイルのBLogicActionのBean定義で、
 *  businessLogicプロパティに目的の業務ロジック実装クラス名を
 *  下記の例のように、&lt;property&gt;要素で指定する。
 *  あわせてstruts-config.xmlの設定例も以下に示す。
 * </p>
 * <p>
 * <strong>Bean定義ファイルの設定</strong>
 * <code><pre>
 * &lt;bean name="/logon/logonAction" singleton="false"
 *   <strong>class="jp.terasoluna.fw.web.struts.actions.BLogicAction"</strong>&gt
 *   <strong>&lt;property name="businessLogic"&gt;
 *     &lt;ref bean="LogonBLogic"&gt;&lt;/ref&gt;
 *   &lt;/property&gt;</strong>
 * &lt;/bean&gt
 * &lt;bean id="LogonBLogic" scope="prototype"
 *   <strong>class="jp.terasoluna.sample1.logon.blogic.LogonBLogic"</strong>&gt
 * &lt;/bean&gt
 * </pre></code>
 * </p>
 * <p>
 * <strong>struts-config.xmlのBLogicAction設定例</strong>
 * <code><pre>
 *    &lt;action path="/logon/logonAction"
 *       name="_logonForm"
 *       validate="true"
 *       scope="session"
 *       input="/logon/logon.jsp"&gt;
 *    &lt;forward name="success" path="/logon/selectGroupSCR.do"/&gt;
 *    &lt;forward name="failure" path="/logon/logonSCR.do"/&gt;
 *  &lt;/action&gt;
 * </pre></code>
 * </p>
 * <p>
 *  また、フォーム、セッションなどのWeb層と、
 *  ビジネスロジックの入出力値とのマッピングを
 *  blogic-io.xmlに対して、アクションパス名ごとに記述する必要がある。
 *  記述方法、詳細説明については、BLogicIOPlugInを参照のこと。
 * </p>
 *
 * @see jp.terasoluna.fw.web.struts.plugins.BLogicIOPlugIn
 * @see jp.terasoluna.fw.service.thin.BLogicResult
 *
 * @param <P> ビジネスロジックへの入力値となるJavaBeanを指定する
 *
 */
public class BLogicAction<P> extends AbstractBLogicAction<P> {

    /**
     * ログクラス。
     */
    private static Log log = LogFactory.getLog(BLogicAction.class);

    /**
     * 設定されるビジネスロジック。
     */
    private BLogic<P> businessLogic = null;

    /**
     * ビジネスロジックを設定する。
     *
     * @param businessLogic businessLogic を設定。
     */
    public void setBusinessLogic(BLogic<P> businessLogic) {
        this.businessLogic = businessLogic;
    }
    
    /**
     * ビジネスロジックを取得する。
     *
     * @return ビジネスロジック
     */
    public BLogic<P> getBusinessLogic() {
        return this.businessLogic;
    }
    
    /**
     * BLogicを起動する。
     * <p>
     * 条件として、クラス変数のbusinessLogicにはBLogicインタフェースを実装した
     * クラスが設定されている必要がある。
     * BLogicがnullのときは、nullを返却する。
     * </p>
     *
     * @param param BLogic入力情報
     * @return BLogic出力情報
     * @throws Exception 予期しない例外
     */
    @Override
    public BLogicResult doExecuteBLogic(P param) throws Exception {

        if (log.isDebugEnabled()) {
            log.debug("*** doExecuteBLogic() called. ***");
        }

        if (businessLogic == null) {
            return null;
        }

        if (log.isDebugEnabled()) {
            // DIされたビジネスロジックのクラス名を出力
            log.debug("*** Starting blogic["
                    + businessLogic.getClass().getName() + "]. ***");
        }

        BLogicResult result = businessLogic.execute(param);

        if (log.isDebugEnabled()) {
            log.debug("*** Finished blogic["
                    + businessLogic.getClass().getName() + "]. ***");
        }

        return result;
    }
}
