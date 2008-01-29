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

package jp.terasoluna.fw.web.struts.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.Globals;

/**
 * <p>
 *  <code>ifErrors</code>タグの実装クラス。
 * </p>
 * <p>
 *  入力チェックエラーがある場合、あるいは出力パラメータにエラー情報が
 *  設定されている場合に、タグのボディ部分を出力する。
 * </p>
 * <p>
 *  <code>Struts</code> の標準的な処理方法では、エラーがある場合には
 *  リクエスト属性にエラー情報リストが設定される。 {@link IfErrorsTag}
 *  では、リクエスト属性をチェックしエラー情報リストが設定されている場合に、
 *  ボディ部分を出力する。
 *  リクエストにエラー情報リストが設定されていない場合には、単に無視される。
 * </p>
 * <p>
 *  {@link IfErrorsTag} とは逆に、エラーがない場合にボディ部分を
 *  出力したい場合には、{@link IfNotErrorsTag} を用いること。
 * </p>
 *
 * <br>
 *
 * <h5>タグがサポートする属性</h5>
 * <p>このタグによって設定される属性はありません。</p>
 *
 * <br>
 *
 * <h5>カスタムタグのスクリプティング変数</h5>
 * <p>このタグによって設定されるスクリプティング変数はありません。</p>
 *
 * <br>
 *
 * <h5>使用方法</h5>
 * <p><code><pre>
 * &lt;ts:ifErrors &gt;
 *   ... // エラーがある場合の表示項目等
 * &lt;/ts:ifErrors&gt;
 * </pre></code></p>
 *
 */
public class IfErrorsTag extends TagSupport {

    /**
     * シリアルバージョンID
     */
    private static final long serialVersionUID = -936805750667584148L;

    /**
     * タグ評価開始時に呼ばれるメソッド。リクエストにエラー情報リストが
     * 設定されているときにはボディ部分を出力し、設定されていないときには
     * ボディ部分をスキップする。
     *
     * @return 処理制御指示
     * @throws JspException JSP例外
     */
    @Override
    public int doStartTag() throws JspException {

        HttpServletRequest req
            = (HttpServletRequest) pageContext.getRequest();
        HttpSession session = req.getSession(true);

        if (req.getAttribute(Globals.ERROR_KEY) != null) {
            // リクエストにエラー情報が含まれるときはボディ評価
            return EVAL_BODY_INCLUDE;
        } else if (session.getAttribute(Globals.ERROR_KEY) != null) {
            // セッションにエラー情報が含まれるときはボディ評価
            return EVAL_BODY_INCLUDE;
        } else {
            // エラー情報が含まれないときはボディ評価をスキップ
            return SKIP_BODY;
        }
    }

    /**
     * タグ評価終了時に呼ばれるメソッド。
     *
     * @return 処理制御指示
     * @throws JspException JSP例外
     */
    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

}
