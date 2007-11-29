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

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import jp.terasoluna.fw.web.RequestUtil;

import org.apache.struts.taglib.TagUtils;

/**
 * <p>拡張 <code>form</code> タグ。</p>
 *
 * <p>
 *  <code>Struts</code> の提供する <code>&lt;html:form&gt;</code> タグを拡張する。
 *  機能として、アクション <code>URL</code> にキャッシュ避け用ランダム
 *  <code>ID</code> を追加する。
 * </p>
 *
 * <br>
 *
 * <h5>タグがサポートする属性</h5>
 * <p> <code>&lt;html:form&gt;</code> タグの <code>API</code> を参照。</p>
 *
 * <br>
 *
 * <h5>カスタムタグのスクリプティング変数</h5>
 * <p> <code>&lt;html:form&gt;</code> タグの <code>API</code> を参照。</p>
 *
 * <br>
 *
 * <h5>使用方法</h5>
 * <p> <code>&lt;html:form&gt;</code> タグの <code>API</code> を参照。</p>
 *
 *
 *
 */
public class FormTag extends org.apache.struts.taglib.html.FormTag {

    /**
	 * シリアルバージョンID
	 */
	private static final long serialVersionUID = 5890474956835784840L;

	/**
     * キャッシュ避け用ランダムIDのパラメータ名。
     */
    private static final String RANDOM_ID_KEY = RandomUtil.RANDOM_ID_KEY;

    /**
     * <code>&lt;form&gt;</code>の開始タグをランダムパラメータ付で
     * 生成する。
     *
     * @return formの開始タグ
     */
    @Override
    protected String renderFormStartElement() {
        HttpServletResponse response =
            (HttpServletResponse) this.pageContext.getResponse();

        StringBuilder results = new StringBuilder("<form");
        results.append(" name=\"");
        results.append(beanName);
        results.append("\"");
        results.append(" method=\"");
        results.append(method == null ? "post" : method);
        results.append("\" action=\"");
        results.append(
            response.encodeURL(
                getActionMappingURL(super.action, super.pageContext)));

        results.append("\"");

        if (styleClass != null) {
            results.append(" class=\"");
            results.append(styleClass);
            results.append("\"");
        }
        if (enctype != null) {
            results.append(" enctype=\"");
            results.append(enctype);
            results.append("\"");
        }
        if (onreset != null) {
            results.append(" onreset=\"");
            results.append(onreset);
            results.append("\"");
        }
        if (onsubmit != null) {
            results.append(" onsubmit=\"");
            results.append(onsubmit);
            results.append("\"");
        }
        if (style != null) {
            results.append(" style=\"");
            results.append(style);
            results.append("\"");
        }
        if (styleId != null) {
            results.append(" id=\"");
            results.append(styleId);
            results.append("\"");
        }
        if (target != null) {
            results.append(" target=\"");
            results.append(target);
            results.append("\"");
        }
        results.append(">");
        return results.toString();
    }

    /**
     * アクションURLにキャッシュ避け用ランダムIDを追加する。
     *
     * @param action アクションパス
     * @param pageContext ページ情報
     * @return ランダムIDを付加したアクションURL
     */
    protected String getActionMappingURL(@SuppressWarnings("hiding") String action,
                                         PageContext pageContext) {
        TagUtils tagUtils = TagUtils.getInstance();

        //URLの取得。
        String url =
            tagUtils.getActionMappingURL(action, pageContext);

        //URLにすでにランダムIDが付加されている場合は削除する。
        url = RequestUtil.deleteUrlParam(url, RANDOM_ID_KEY);

        if (url.indexOf("?") < 0) {
            url = url.concat("?");
        } else {
            url = url.concat("&");
        }

        //URLにランダムIDを付加して返却する。
        return url + RANDOM_ID_KEY + "=" + RandomUtil.generateRandomID();
    }

}
