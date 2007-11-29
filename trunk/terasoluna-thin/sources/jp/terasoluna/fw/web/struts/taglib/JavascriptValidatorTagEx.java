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

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import jp.terasoluna.fw.web.struts.form.FieldChecksEx;

import org.apache.struts.taglib.html.JavascriptValidatorTag;

/**
 * Strutsが提供する、JavascriptValidatorTagクラスを拡張したクラス。
 * プロパティファイルから取得した半角カナ、全角カナ文字列を
 * Javascriptの変数として出力する。
 * <br>
 *
 * <h5>タグがサポートする属性</h5>
 * <p> <code>&lt;html:javascript&gt;</code> タグの <code>API</code> を参照。</p>
 *
 * @see jp.terasoluna.fw.web.struts.form.FieldChecksEx
 */
public class JavascriptValidatorTagEx extends JavascriptValidatorTag {

    /**
	 * シリアルバージョンID
	 */
	private static final long serialVersionUID = 879915691393426820L;

	/**
     * タグの評価開始時に呼ばれる。
     * 半角カナ、全角カナのリストをFieldCheckExクラスの変数から
     * 取得し、JavaScriptの変数として出力する。
     * @return 処理結果
     * @throws JspException 処理失敗時の例外
     */
    @Override
    public int doStartTag() throws JspException {
        JspWriter writer = pageContext.getOut();
        try {
            writer.print(this.renderKanaList());

        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return super.doStartTag();
    }

    /**
     * FieldCheckExクラスから半角カナ、全角カナ文字列を取得し、
     * JavaScriptの変数定義の文字列として編集し、返却する。
     *
     * @return 半角カナ、全角カナの変数定義文字列
     */
    protected String renderKanaList() {
        StringBuilder builder = new StringBuilder();
        builder.append(super.renderStartElement());
        if ("true".equalsIgnoreCase(htmlComment)) {
            builder.append(HTML_BEGIN_COMMENT);
        }
        builder.append("var hankakuKanaList = \"");
        builder.append(FieldChecksEx.getHankakuKanaList() + "\";" + "\n");
        builder.append("var zenkakuKanaList = \"");
        builder.append(FieldChecksEx.getZenkakuKanaList() + "\";" + "\n");
        builder.append(super.getJavascriptEnd());
        return builder.toString();
    }

}
