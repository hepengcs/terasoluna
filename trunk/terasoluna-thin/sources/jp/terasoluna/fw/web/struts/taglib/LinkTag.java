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

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.jsp.JspException;

import jp.terasoluna.fw.web.RequestUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.logic.IterateTag;

/**
 * <p>拡張 <code>link</code> タグ。</p>
 *
 * <p>
 *  <code>Struts</code> の提供する <code>&lt;html:link&gt;</code> タグを拡張する。
 *  機能として、アクション <code>URL</code> にキャッシュ避け用ランダム
 *  <code>ID</code> を追加する。
 *  ネストされていない<code></code>iterateTag</code>の
 *  <code>BODY</code>内で<code>LinkTag</code>を使用するときのみ
 *  <code>indexed</code>属性、<code>indexId</code>属性が有効となる。
 * </p>
 *
 * <br>
 *
 * <h5>タグがサポートする属性</h5>
 * <p><code>&lt;html:link&gt;</code>タグの<code>API</code> を参照。</p>
 *
 * <br>
 *
 * <h5>カスタムタグのスクリプティング変数</h5>
 * <p> <code>&lt;html:link&gt;</code> タグの <code>API</code> を参照。</p>
 *
 * <br>
 *
 * <h5>使用方法</h5>
 * <p> <code>&lt;html:link&gt;</code> タグの <code>API</code> を参照。</p>
 *
 *
 *
 */
public class LinkTag extends org.apache.struts.taglib.html.LinkTag {

    /**
     * シリアルバージョンID
     */
    private static final long serialVersionUID = -2143604832271336809L;

    /**
     * ログクラス。
     */
    private static Log log = LogFactory.getLog(LinkTag.class);
    
    /**
     * キャッシュ避け用ランダムIDのパラメータ名。
     */
    private static final String RANDOM_ID_KEY = RandomUtil.RANDOM_ID_KEY;

    /**
     * <p>URLにキャッシュ避け用ランダムIDを追加する。</p>
     *
     * <p>ホットスポットがないため、<code>Struts 1.2.4</code> の
     * <code>LinkTag.calculate()</code> のコードをコピーして変更している。
     * <code>Struts</code> のバージョンを変更した場合は
     * 見直しが必要となるので注意すること。</p>
     *
     * @return 処理制御指示
     * @exception JspException エラーが発生したとき
     */
    @SuppressWarnings("unchecked")
    @Override
    protected String calculateURL() throws JspException {

        // Identify the parameters we will add to the completed URL
        Map<String, String> params = TagUtils.getInstance().computeParameters(
                pageContext, paramId, paramName, paramProperty, paramScope,
                name, property, scope, transaction);

        // ========== 【拡張箇所】↓ここから==========
        if (params == null) {
            params = new HashMap<String, String>();
        }

        //すでにランダムIDが存在する場合は削除する。
        forward = RequestUtil.deleteUrlParam(forward, RANDOM_ID_KEY);
        href = RequestUtil.deleteUrlParam(href, RANDOM_ID_KEY);
        page = RequestUtil.deleteUrlParam(page, RANDOM_ID_KEY);
        action = RequestUtil.deleteUrlParam(action, RANDOM_ID_KEY);

        //パラメータマップにランダムIDを追加。
        params.put(RANDOM_ID_KEY, RandomUtil.generateRandomID());

        // if "indexed=true", add "index=x" parameter to query string
        // * @since Struts 1.1
        if (indexed) {

           // look for outer iterate tag
           IterateTag iterateTag =
               (IterateTag) findAncestorWithClass(this, IterateTag.class);
           if (iterateTag == null) {
               // iterateTagのbody内で使用されていない場合は例外を投げる
               JspException e = new JspException
                   (messages.getMessage("indexed.noEnclosingIterate"));
               TagUtils.getInstance().saveException(pageContext, e);
               
               log.error("iterateTag is null.");
               throw e;
           }

           if (indexId != null) {
               params.put(indexId, Integer.toString(iterateTag.getIndex()));
           } else {
               params.put("index", Integer.toString(iterateTag.getIndex()));
           }
        }
        // ========== 【拡張箇所】↑ここまで==========

        String url = null;
        try {
            url = TagUtils.getInstance().computeURLWithCharEncoding(
                    pageContext, forward, href, page, action, module, params,
                    anchor, false, useLocalEncoding);
        } catch (MalformedURLException e) {
            TagUtils.getInstance().saveException(pageContext, e);
            
            log.error("Malformed URL has occurred.");
            throw new JspException
                (messages.getMessage("rewrite.url", e.toString()));
        }
        return (url);

    }
}
