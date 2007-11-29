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

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.terasoluna.fw.web.thin.BlockageControlFilter;
import jp.terasoluna.fw.web.thin.ServerBlockageControlFilter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * フォワード先の振り分け処理を行う。
 *
 * <p>
 *  Strutsが提供しているDispatcherAction、
 *  LookUpDispatchActionとは異なり、
 *  リクエストパラメータの値を用いて、遷移先が決定される。<br>
 *  この機能で利用するリクエストパラメータキーは、下記の優先順位で決定される。
 *  <ol>
 *   <li>eventプロパティで指定された値</li>
 *   <li>eventプロパティが指定されていない場合、
 *       &quot;event&quot;を
 *       リクエストパラメータのキーとして取得できる値</li>
 *  </ol>
 *
 *  下記に記した定義ファイルは
 *  リクエストパラメータのキーをcustom-eventに設定する例である。
 * </p>
 * <p>
 * <strong>Bean定義ファイルでeventプロパティにより、
 *  遷移先に用いられるリクエストパラメータキーをcustom-eventに設定する</strong>
 * </p>
 * <p>
 * <strong>Bean定義ファイルの設定</strong>
 *  <code><pre>
 *  &lt;bean name="/dispatch" scope="prototype"
 *    class="jp.terasoluna.fw.web.struts.actions.DispatchAction"&gt;
 *    <strong>&lt;property name="event"&gt;
 *      &lt;value&gt;custom-event&lt;/value&gt;
 *    &lt;/property&gt;</strong>
 *  &lt;/bean&gt;
 *  </pre></code>
 * </p>
 * <p>
 * <strong>struts-config.xmlの設定</strong>
 * <code><pre>
 *  &lt;action path="/dispatch"
 *    name="_sampleForm"
 *    scope="session"
 *    input="/prev.jsp"&gt;
 *    &lt;forward name="regist" path="/userRegist.do"/&gt;
 *    &lt;forward name="search" path="/userSearch.do"/&gt;
 *    &lt;forward name="update" path="/userUpdate.do"/&gt;    
 *    &lt;forward name="default" path="/prev.do"/&gt;
 *  &lt;/action&gt;
 * </pre></code>
 *  ここで、eventパラメータが省略された場合、
 *  リクエストパラメータのキーは&quot;event&quot;となる。
 * </p>
 * <p>
 *  次に、上記で決定されたキーを用いてリクエストパラメータを検索し
 *  値を取得する。その結果、以下の優先順位で遷移先文字列が決定される。
 *  <ol>
 *   <li>検索されたリクエストパラメータの値のうち、
 *       先頭に&quot;forward_&quot;がついているもの</li>
 *   <li>該当するリクエストパラメータの値が見つからない場合、
 *       <strong>リクエストパラメータのキーそのもの</strong>の先頭に
 *       &quot;forward_&quot;が付いているもの</li>
 *   <li>上記ともに見つからない場合、遷移先は&quot;default&quot;になる</li>
 *   <li>また、event=&quot;XXXX&quot;、&quot;forward_XXXX&quot;といった
 *       存在し得ない不正な遷移先が指定された場合も、&quot;default&quot;を
 *       遷移先とする。</li>
 *  </ol>
 *
 *  上記の結果、先頭に&quot;forward_&quot;が
 *  付いているものは、取り除かれた文字列が&quot;#input&quot;
 *  であったとき、struts-config.xmlのinput属性
 *  （上記例では/logon.jsp）が遷移先となる。<br>
 *  &quot;#input&quot;ではないとき、先頭の
 *  &quot;forward_&quot;を取り除いた文字列が遷移先文字列となる。
 * </p>
 * <p>
 * <strong>JSPでの記述例</strong>
 * </p>
 * <p>
 * <code><pre>
 * ・・・
 *  &lt;html:submit property="forward_regist" value=" 登録 " /&gt;
 *  &lt;html:submit property="forward_search" value=" 再検索 " /&gt;
 *  &lt;html:image  property="forward_update" src="./image/update.gif" /&gt;  
 * ・・・
 * </pre></code>
 * 　（ここではリクエストパラメータの<strong>値にforward_...</strong>
 *  が存在しない場合）<br>
 *  画面上の「登録」ボタンが押下されたとき、次の遷移先はregist
 *  になり、「再検索」ボタンが押下されたとき、次の遷移先はsearch
 *  になり、更新画像ボタンが押下されたとき、次の遷移先はupdate
 *  になる。
 * </p>
 *
 */
public class DispatchAction extends ActionEx {

    /**
     * ログクラス。
     */
    private static Log log = LogFactory.getLog(DispatchAction.class);

    /**
     * リクエストパラメータに振り分け指示がなかった場合の、
     * デフォルトの遷移先の論理フォワード名。
     */
    private static final String FORWARD_DEFAULT = "default";

    /**
     * 振り分け指示を識別するための、
     * リクエストパラメータのキーのプリフィックス。
     */
    private static final String FORWARD_PREFIX = "forward_";

    /**
     * 遷移先を表すプロパティ名。
     */
    private String event = null;

    /**
     * 遷移先を表すプロパティ名を設定する。
     *
     * @param value 遷移先プロパティ名
     */
     public void setEvent(String value) {
         this.event = value;
     }

    /**
     * フォワード先の振り分け処理を行う。
     * <p>
     *   フォワード先を振り分けた後、サーバ閉塞通過フラグを
     *   削除する。<br>
     *   実際の遷移先は、doDetamineForward()が決定している。
     * </p>
     *
     * @param mapping アクションマッピング
     * @param form アクションフォーム
     * @param req <code>HTTP</code>リクエスト
     * @param res <code>HTTP</code>レスポンス
     * @return 遷移先情報
     */
    @Override
    public ActionForward doExecute(ActionMapping mapping,
                                          ActionForm form,
                                          HttpServletRequest req,
                                          HttpServletResponse res) {
        if (log.isDebugEnabled()) {
            log.debug("doExecute() called.");
        }
        // リクエストにキャンセルフラグが設定されていることを確認し、
        // cancelled()メソッドを実行するかどうかを決定する。
        if (isCancelled(req)) {
            ActionForward af = cancelled(mapping, form, req, res);
            if (af != null) {
                return af;
            }
        }

        if (event == null) {
            // eventが指定なしの時、"event"をデフォルトとして設定する。
            event = "event";
        }

        String forward = doDetermineForward(req.getParameterMap(), event);

        ActionForward actionForward = null;
        if ("#input".equalsIgnoreCase(forward)) {
            actionForward = new ActionForward(mapping.getInput());
        } else {
            actionForward = mapping.findForward(forward);
        }

        // フォワード先が見つからない場合、"default" で指定されている
        // アクションフォワードを返却する。
        if (actionForward == null) {
            if (log.isWarnEnabled()) {
                log.warn("forward name[" + forward + "] is invalid by user request.");
            }
            actionForward = mapping.findForward(FORWARD_DEFAULT);
        }

        // フォワード先で閉塞チェック等を有効とするため
        // THRU_FILTERフラグを削除する
        // サーバ閉塞
        req.removeAttribute(ServerBlockageControlFilter
            .SERVER_BLOCKAGE_THRU_KEY);
        // 業務閉塞
        req.removeAttribute(BlockageControlFilter.BLOCKAGE_THRU_KEY);

        if (log.isDebugEnabled()) {
            log.debug("forward = " + forward + " (" + ((actionForward == null)
                ? "null" : actionForward.getPath()) + ")");
        }
        return actionForward;
    }

    /**
     * リクエストパラメータに基づいてフォワード先をディスパッチする。
     * paramsがnullの場合は、デフォルトの文字列を返却する。
     *
     * @param params リクエストパラメータ（マップ形式）
     * @param event アクションマッピングに指定されたイベント名
     * @return 振り分け先の論理フォワード名
     */
    protected String doDetermineForward(Map params, @SuppressWarnings("hiding") String event) {
        if (params != null) {
            if (exists(params, event)) {
                String[] eventValues = (String[]) params.get(event);
                for (int i = 0; i < eventValues.length; i++) {
                    if (eventValues[i].startsWith(FORWARD_PREFIX)) {
                        return eventValues[i].substring(FORWARD_PREFIX.length());
                    }
                }
            }
            Iterator iter = params.keySet().iterator();
            while (iter.hasNext()) {
                String key = (String) iter.next();
                if (key.startsWith(FORWARD_PREFIX)) {
                    String forward = key.substring(FORWARD_PREFIX.length());
                    if(forward.endsWith(".x") || forward.endsWith(".y")){
                        forward = forward.substring(0, forward.length() - 2);
                    }
                    return forward;
                }
            }
        }
        return FORWARD_DEFAULT;
    }


    /**
     * リクエストパラメータに、nameで指定した名称のパラメータが
     * 存在しているかを判定する。
     *
     * @param params リクエストパラメータ（マップ形式）
     * @param name リクエストパラメータ名
     * @return リクエストパラメータ名が存在しているならば <code>true</code>
     */
    protected boolean exists(Map params, String name) {
        return params.containsKey(name);
    }

    /**
     * リクエストにキャンセルフラグが設定されている場合の遷移先を
     * 決定する。注意点として、ActionForwardはnullとして返却されるため、
     * キャンセル時の遷移先は、
     * このクラスを継承したクラスのオーバライドメソッドで実装する必要がある。
     *
     * @param mapping アクションマッピング
     * @param form アクションフォーム
     * @param request HTTPリクエスト
     * @param response HTTPレスポンス
     * @return アクションフォワード
     */
    protected ActionForward cancelled(ActionMapping mapping,
                                      ActionForm form,
                                      HttpServletRequest request,
                                      HttpServletResponse response) {
        return null;
    }

}
