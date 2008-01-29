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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import jp.terasoluna.fw.web.thin.AuthorizationControlFilter;
import jp.terasoluna.fw.web.thin.AuthorizationController;

/**
 * <p>
 *  <code>ifAuthorized</code> タグの実装クラス。
 * </p>
 *
 * <p>
 *  リクエストが <code>path</code> 属性で指定されたパスに対してアクセス権が
 *  ある場合にのみ、タグのボディ部分を出力する。アクセス権がない場合には、
 *  単に無視される。アクセス権のチェックは、
 *  {@link
 *  jp.terasoluna.fw.web.thin.AuthorizationController}
 *  へ委譲される。
 * </p>
 *
 * <br>
 * <h5>タグがサポートする属性</h5>
 * <p>{@link IfAuthorizedTag} では、以下の属性をサポートする。</p>
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
 *    <td> <code>path</code> </td>
 *    <td> - </td>
 *    <td> <code>true</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     対象となる <code>path</code>。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>blockId</code> </td>
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
 * <code><pre>
 * &lt;t:ifAuthorized parh="/pathToSomewhere"&gt;
 *   ... // 特定ユーザのみの表示項目等
 * &lt;/t:ifAuthorized&gt;
 * </pre></code></p>
 *
 * @see jp.terasoluna.fw.web.taglib.IfAuthorizedBlockTag
 * @see
 * jp.terasoluna.fw.web.thin.AuthorizationController
 *
 */
public class IfAuthorizedTag extends TagSupport {
    
    /**
     * シリアルバージョンID
     */
    private static final long serialVersionUID = -4929834014631292954L;

    /**
     * パス名。
     */
    private String path = null;

    /**
     * ブロックID。
     */
    private String blockId = null;

    /**
     * パス名を設定する。
     *
     * @param path パス名。
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * ブロックIDを設定する。
     *
     * @param blockId ブロックID
     */
    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    /**
     * タグ評価開始時に呼ばれるメソッド。<code>AccessConrol</code>
     * にチェックを委譲し、<code>path</code>
     * に対してアクセス権限があるときにはタグのボディ部分を出力し、
     * アクセス権限がないときにはボディ部分をスキップする。
     *
     * @return 処理制御指示
     * @throws JspException JSP例外
     */
    @Override
    public int doStartTag() throws JspException {

        HttpServletRequest req
            = (HttpServletRequest) pageContext.getRequest();

        // AuthorizationControllerを取得
        AuthorizationController ac
             = AuthorizationControlFilter.getAuthorizationController();

        // アクセス権限チェック
        boolean isAuthorized = ac.isAuthorized(this.path, req);

        if (isAuthorized) {
            if (this.blockId != null) {
                // アクセス権限チェック結果をブロック情報に追加して保存
                pageContext.setAttribute(this.blockId, new Boolean(true));
            }
            // アクセス権限があるときはボディ評価
            return EVAL_BODY_INCLUDE;
        }
        // 権限がないときはボディ評価をスキップ
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
        this.path = null;
        this.blockId = null;
    }

}
