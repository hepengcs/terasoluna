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

package jp.terasoluna.fw.web.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import jp.terasoluna.fw.web.RequestUtil;
import jp.terasoluna.fw.web.thin.ServerBlockageControlFilter;
import jp.terasoluna.fw.web.thin.ServerBlockageController;

/**
 * <p><code>ifPreBlockade</code> タグの実装クラス</p>
 * <p>
 *  {@link IfPreBlockadeTag}
 * </p>
 *
 * <p>
 *  サーバが閉塞状態又は予閉塞状態の場合にのみ、タグのボディ部分を出力する。
 *  通常時には単に無視される。サーバ閉塞のチェックは、
 *  {@link
 *  jp.terasoluna.fw.web.thin.ServerBlockageController}
 *  へ委譲される。
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
 * <code><pre>
 * &lt;t:ifPreBlockade&gt;
 *   ... // サーバが閉塞状態又は予閉塞状態の場合にのみの表示項目等
 * &lt;/t:ifPreBlockade&gt;
 * </pre></code></p>
 *
 * @see
 * jp.terasoluna.fw.web.thin.ServerBlockageController
 *
 */
public class IfPreBlockadeTag extends TagSupport {

    /**
	 * シリアルバージョンID
	 */
	private static final long serialVersionUID = -7545297874735975274L;

	/**
     * タグ評価開始時に呼ばれるメソッド。
     *
     * @return 処理制御指示
     * @throws JspException 例外
     */
    @Override
    public int doStartTag() throws JspException {

        ServerBlockageController sbc =
            ServerBlockageControlFilter.getServerBlockageController();

        String pathInfo = RequestUtil.getPathInfo(pageContext.getRequest());

        if (sbc.isPreBlockaded() || sbc.isBlockaded(pathInfo)) {
            // 予閉塞状態または閉塞状態のときはボディ評価
            return EVAL_BODY_INCLUDE;
        }
		// ボディ評価をスキップ
		return SKIP_BODY;
    }

    /**
     * タグ評価終了時に呼ばれるメソッド。
     *
     * @return 処理制御指示
     * @throws JspException 例外
     */
    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

}
