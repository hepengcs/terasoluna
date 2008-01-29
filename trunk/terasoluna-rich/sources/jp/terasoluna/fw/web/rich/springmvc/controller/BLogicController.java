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

import java.lang.reflect.Type;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import jp.terasoluna.fw.service.rich.BLogic;
import jp.terasoluna.fw.util.GenericsUtil;
import jp.terasoluna.fw.util.ProxyUtil;
/**
 * BLogicインタフェース実装クラス実行用リクエストコントローラ。
 *
 * <p>
 * <code>DispatcherServlet</code>から起動され、
 * <code>BLogic</code>インタフェースを実装した業務ロジッククラスを実行する。
 * </p>
 *
 * <p>
 * 業務ロジックの実行は、本クラスが実行するが、
 * トランザクション管理の責務は、BLogicが受け持つ。
 * 業務ロジック起動時の引数のJavaBeanは、リクエストをバインドしたJavaBean（コマンド）となる。
 * </p>
 *
 * <p>
 * 原則として、サブクラスの作成は不要であり、本クラスを直接利用する。
 * ただし、プロジェクトごとの要件によって、
 * 業務前処理、後処理が必要な場合は、プロジェクト単位でサブクラスを作成する。
 * </p>
 * 
 * <p>
 * あらかじめTERASOLUNAが提供している
 * リクエストコントローラのベース定義
 * xmlRequestBLogicExecuteControllerまたは
 * queryRequestBLogicExecuteControllerを継承してBean定義を行うこと。
 * また、以下のプロパティを設定すること。
 * </p>
 * 
 * <p>
 *   <table border="1" CELLPADDING="8">
 *     <th>属性名</th>
 *     <th>必須</th>
 *     <th>説明</th>
 *     
 *     <tr>
 *       <td align=center><b>blogic</b></td>
 *       <td>○</td>
 *       <td>起動するBLogicのBean名</td>
 *     </tr>
 *  
 *  </table>
 * 
 * </p>
 * 
 * 【<code>Bean定義ファイル</code>の設定例】<br>
 * <code><pre>
 *   &lt;bean name="/secure/blogic/max.do"
 *       parent="xmlRequestBLogicExecuteController" scope="singleton"&gt;
 *     &lt;property name="blogic" ref="maxBLogic"/&gt;
 *   &lt;/bean&gt;
 *   ※ xmlRequestBLogicExecuteControllerは、
 *   BLogicを起動するリクエストコントローラの抽象定義。
 *   詳細は、TerasolunaControllerを参照。
 * </pre></code>
 * </p>
 * 
 *　@see jp.terasoluna.fw.service.rich.BLogic
 *
 * 
 */
public class BLogicController extends TerasolunaController<Object, Object> {
    /**
     * ログクラス。
     */
    private static Log log = LogFactory.getLog(BLogicController.class);
    
    /**
     * Bean定義ファイルに設定されているBLogic実装クラス。
     */
    protected BLogic<Object, Object> blogic = null;

    /**
     * BLogic実装クラスを設定する。
     * @param blogic BLogic実装クラス。
     */
    public void setBlogic(BLogic<Object, Object> blogic) {
        this.blogic = blogic;
    }
    
    /**
     * BLogic実装クラスを設定する。
     * 
     * @param blogic
     *            BLogic実装クラス。
     */
    public void setBusinessLogic(BLogic<Object, Object> blogic) {

        this.blogic = blogic;
    }
    
    /**
     * DIコンテナによってインスタンス化された直後に呼ばれるメソッド。
     * 必須属性のNullチェックを行う。
     */
    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        if (this.blogic == null) {
            String message = "Cannot create BLogicController "
                + "without blogic(BLogic) being set. "
                + "Check Bean definition file.";
            log.error(message);
            throw new IllegalStateException(message);
        }
    }

    /**
     * コマンドオブジェクトの型を取得する。
     * {@link jp.terasoluna.fw.service.rich.BLogic}クラスの
     * executeメソッドの引数の型を返す。
     * executeメソッドがオーバーロードされている場合、例外を返す。
     * @return コマンドオブジェクトの型。
     */
    @SuppressWarnings("unchecked")
    @Override
    protected Type getCommandType() {
        return GenericsUtil.resolveParameterizedClass(BLogic.class, ProxyUtil
                .getTargetClass(blogic))[0];
    }
    
    /**
     *  BLogic#execute()を直接呼び出し、業務ロジックを実行する。
     *
     * @param command コマンドオブジェクト
     * @return モデルオブジェクト
     * @throws Exception 業務ロジックからスローされた例外
     */
    @Override
    protected Object executeService(Object command) throws Exception {
        return this.blogic.execute(command);
    }
}
