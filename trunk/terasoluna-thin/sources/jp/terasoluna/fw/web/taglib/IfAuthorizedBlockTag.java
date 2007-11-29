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

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>
 *  <code>ifAuthorizedBlock</code> タグの実装クラス。
 * </p>
 *
 * <p>
 *  {@link IfAuthorizedTag} の結果を <code>blockId</code>
 *  毎に制御する為のタグで、
 *  <code>blockId</code> で {@link IfAuthorizedTag} と紐付けられ、
 *  ボディ内を表示するかどうかを判定する。<br>
 *  また、このタグを入れ子状にすることで、アクセス権限毎の制御を柔軟に行なう事が可能になる。
 *  入れ子状にする場合は、親タグの <code>blockId</code>属性と子タグの<code>parentBlockId</code>
 *  タグが紐付けられ、ボディ内を表示するかどうかを判定する。
 * </p>
 *
 * <br>
 * <h5>タグがサポートする属性</h5>
 * <p>{@link IfAuthorizedBlockTag} では、以下の属性をサポートする。</p>
 * <div align="center">
 *  <table width="90%" border="1" bgcolor="#FFFFFF">
 *   <tr>
 *    <td> <b>属性名</b> </td>
 *    <td> <b>デフォルト値</b> </td>
 *    <td> <b>必須性</b> </td>
 *    <td> <b>実行時式</b> </td>
 *    <td> <b>概要</b> </td>
 *   </tr>
 *   <tr>
 *    <td> <code>blockId</code> </td>
 *    <td> - </td>
 *    <td> <code>true</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     対象となる <code>blockId</code>。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>parentBlockId</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     このタグの親となる {@link IfAuthorizedBlockTag}
 *     と紐付ける為の <code>blockId</code>。
 *    </td>
 *   </tr>
 *  </table>
 * </div>
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
 * &lt;t:ifAthorizedBlock blockId="ABC" &gt;
 *     ボディ内の <code>blockId</code> で紐付けられた
 *     {@link IfAuthorizedBlockTag} が表示される場合のみ表示される。
 *
 *     &lt;t:ifAthorizedBlock blockId="EFG" parentBlockId="ABC" &gt;
 *      ボディ内の <code>blockId</code> で紐付けられた {@link IfAuthorizedTag}
 *      が表示される場合のみ表示される。
 *
 *         &lt;t:ifAthorized path="/sample1/test.do blockId="EFG" &gt;
 *             指定されたパスへのアクセス権限がある場合、出力される。
 *         &lt;/t:ifAthorized&gt;
 *
 *     &lt;/t:ifAthorizedBlock&gt;
 * &lt;/t:ifAthorizedBlock&gt;
 * </pre></code></p>
 *
 *
 * @see jp.terasoluna.fw.web.taglib.IfAuthorizedTag
 *
 */
public class IfAuthorizedBlockTag extends BodyTagSupport {

    /**
	 * シリアルバージョンID
	 */
	private static final long serialVersionUID = 1062137481352416171L;

	/**
     * ログクラス
     */
    private static Log log
                  = LogFactory.getLog(IfAuthorizedBlockTag.class);

    /**
     * ブロックID。
     */
    private String blockId = null;

    /**
     * 親ブロックID。
     */
    private String parentBlockId = null;

    /**
     * ブロックIDを設定する。
     *
     * @param blockId ブロックID
     */
    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    /**
     * 親ブロックIDを設定する。
     *
     * @param parentBlockId 親ブロックID
     */
    public void setParentBlockId(String parentBlockId) {
        this.parentBlockId = parentBlockId;
    }

    /**
     * タグ評価開始時に呼ばれるメソッド。
     *
     * @return 処理制御指示
     * @throws JspException JSP例外
     */
    @Override
    public int doStartTag() throws JspException {

        if (this.blockId == null) {
            throw new JspException("blockId is required.");
        }

        // ボディ評価
        return EVAL_BODY_BUFFERED;
    }

    /**
     * タグボディ終了時に呼ばれるメソッド。
     *
     * @return 処理制御指示
     * @throws JspException JSP例外
     */
    @Override
    public int doAfterBody() throws JspException {

        // ブロック情報を取得
        Boolean blockInfo = null;
        try {
            // pageContext からブロック情報を取得
            blockInfo = (Boolean) pageContext.getAttribute(this.blockId);
        } catch (ClassCastException e) {
            if (log.isWarnEnabled()) {
                log.warn("Class cast error." , e);   
            }
        }

        // ボディ出力すべきかどうか
        boolean outputBody = false;
        if (blockInfo != null && blockInfo.booleanValue()) {
            //blockInfoがnullでは無くtrueの場合
            outputBody = true;
        }

        // 親ブロック情報を取得
        if (this.parentBlockId != null) {
            // アクセス権限チェック結果を保存
            pageContext.setAttribute(
                this.parentBlockId, new Boolean(outputBody));
        }

        // ボディ出力
        if (outputBody) {
            try {
                bodyContent.writeOut(bodyContent.getEnclosingWriter());
            } catch (IOException e) {
                log.error("Output error.");
                throw new JspException(e);
            }
        }

        // ボディ再評価はしない
        return SKIP_BODY;
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

    /**
     * タグハンドラ解放時の処理。
     */
    @Override
    public void release() {
        super.release();
        this.blockId = null;
        this.parentBlockId = null;
    }

}
