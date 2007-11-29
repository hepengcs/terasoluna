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
import javax.servlet.jsp.JspException;

import jp.terasoluna.fw.web.struts.form.ActionFormUtil;

import org.apache.struts.taglib.TagUtils;

/**
 * <p>拡張submitタグ。フォームのターゲットを指定します。</p>
 *
 * <p>
 * フォームのサブミット時にtargetの属性値を設定します。(使用方法を参照)
 * </p>
 *
 * <h5>タグがサポートする属性</h5>
 *
 * <p><code>submitTag</code> では、以下の属性をサポートする。</p>
 *
 * <div align="center">
 *  <table width="90%" border="1">
 *   <tr>
 *    <td> <b>属性名</b> </td>
 *    <td> <b>デフォルト値</b> </td>
 *    <td> <b>必須性</b> </td>
 *    <td> <b>実行時式</b> </td>
 *    <td> <b>概要</b> </td>
 *   </tr>
 *   <tr>
 *    <td> <code>target</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *      ターゲット先を指定する。
 *    </td>
 *   </tr>
 *  </table>
 * </div>
 * <p>
 *  その他の属性については、
 *  <code>&lt;html:submit&gt;</code> タグの <code>API</code> を参照。
 * </p>
 * <br>
 * <br>
 * <h5>カスタムタグのスクリプティング変数</h5>
 * <p> <code>&lt;html:submit&gt;</code> タグの <code>API</code> を参照。</p>
 * <br>
 * <h5>使用方法</h5>
 * <code><pre>
 *  &lt;ts:submit value=" submit " target=" rightFrame "/&gt;
 * </code></pre>
 *
 */
public class SubmitTag extends org.apache.struts.taglib.html.SubmitTag {

    /**
	 * シリアルバージョンID
	 */
	private static final long serialVersionUID = -795727425454129052L;

	/**
     * 指定するターゲット名
     */
    protected String target = null;

    /**
     * <p>ターゲット名を取得する。</p>
     *
     * @return target ターゲット名
     */
    public String getTarget() {
        return this.target;
    }

    /**
     * <p>ターゲット名を設定する。</p>
     *
     * @param target ターゲット名
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * <p>すべてのアロケートされた資源を解放する。</p>
     */
    @Override
    public void release() {
        super.release();
        this.target = null;
    }

    /**
     * <p>タグ終了時処理。</p>
     *
     * @return EVAL_PAGE
     * @exception JspException 例外
     */
    @Override
    public int doEndTag() throws JspException {

        // Acquire the label value we will be generating
        String label = value;
        if ((label == null) && (text != null)) {
            label = text;
        }
        if ((label == null) || (label.length() < 1)) {
            label = "Submit";
        }

        // Generate an HTML element
        StringBuffer results = new StringBuffer();

        //↓↓↓拡張箇所ここから
        if (target != null
                && pageContext.getAttribute("frameScript") == null) {
            HttpServletRequest req
                = (HttpServletRequest) pageContext.getRequest();
            String formName = ActionFormUtil.getActionFormName(req);
            results.append(System.getProperty("line.separator")
                + "<script type=\"text/javascript\">"
                + System.getProperty("line.separator"));
            results.append("<!--"
                + System.getProperty("line.separator"));
            results.append("  function __setFrameTarget(__frTarget) {"
                + System.getProperty("line.separator"));
            results.append("    document.");
            results.append(formName);
            results.append(".target = ");
            results.append("__frTarget;"
                + System.getProperty("line.separator"));
            results.append("  }"
                + System.getProperty("line.separator"));
            results.append("//-->"
                + System.getProperty("line.separator"));
            results.append("</script>"
                + System.getProperty("line.separator")
                + System.getProperty("line.separator"));

            pageContext.setAttribute("frameScript", "frameScript");
        }
        //↑↑↑拡張箇所ここまで

        results.append("<input type=\"submit\"");
        if (property != null) {
            results.append(" name=\"");
            results.append(property);
            if (indexed) {
                prepareIndex(results, null);
            }
            results.append("\"");
        }

        if (accesskey != null) {
            results.append(" accesskey=\"");
            results.append(accesskey);
            results.append("\"");
        }
        if (tabindex != null) {
            results.append(" tabindex=\"");
            results.append(tabindex);
            results.append("\"");
        }
        results.append(" value=\"");
        results.append(label);
        results.append("\"");

        //↓↓↓拡張箇所ここから
        if (target != null && getOnclick() == null) {
            setOnclick("__setFrameTarget(\'" + this.target + "\')");
        } else if (target != null && getOnclick() != null) {
            String old = getOnclick();
            setOnclick("__setFrameTarget(\'" + this.target + "\');" + old);
        }
        //↑↑↑拡張箇所ここまで

        results.append(prepareEventHandlers());
        results.append(prepareStyles());
        results.append(">");
        //TagUtilsオプションを取得。
        TagUtils tagUtils = TagUtils.getInstance();
        // Render this element to our writer
        tagUtils.write(pageContext, results.toString());

        // Evaluate the remainder of this page
        return EVAL_PAGE;
    }

}
