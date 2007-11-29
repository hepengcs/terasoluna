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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>
 *  <code>HTML</code> の <code>body</code> タグを拡張する
 *  <code>body</code> タグの実装クラス。
 * </p>
 * <p>
 *  <code>PageContext</code> に <code>&quot;ON_LOAD&quot;</code> をキーに
 *  埋め込まれたスクリプトを <code>onLoad</code> イベント処理に追加する。<br>
 *  このタグで生成する<code>HTML &lt;body&gt;</code> タグでは、<code>onLoad
 *  </code>イベント処理時のスクリプトとして、JavaScript関数の <code>__onLoad__()
 *  </code>を呼び出す。JavaScript関数 <code>__onLoad__()</code> の定義は、
 *  このタグで生成するため、HTML内に同名のJavaScriptを記述してはならない。
 * </p>
 * <p>
 *  <code>&quot;styleClass&quot;</code>、<code>&quot;bgcolor&quot;</code>、
 *  <code>&quot;text&quot;</code>、<code>&quot;link&quot;</code>、
 *  <code>&quot;vlink&quot;</code>、<code>&quot;alink&quot;</code> といった
 *  このタグの属性は、そのままこのタグが生成するHTMLの <code>&lt;body&gt;</code>
 *  タグの属性として展開される。
 * </p>
 *
 * <br>
 *
 * <h5>タグがサポートする属性</h5>
 * <p><code>body</code> タグでは、以下の属性をサポートする。</p>
 * <br><br>
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
 *    <td> <code>onload</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     画面表示時に実行するJavaScript。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>onunload</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     画面アンロード時に実行するJavaScript。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>styleClass</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     スタイルシートのクラス名。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>bgcolor</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     背景色。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>background</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     背景に設定する画像。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>text</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     テキスト文字の色。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>link</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     リンク部分の色。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>vlink</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     既に選択されたリンク部分の色。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>alink</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     選択中のリンク部分の色。
 *    </td>
 *   </tr>
 *  </table>
 * </div>
 * <br><br>
 * <h5>カスタムタグのスクリプティング変数</h5>
 * <p>このタグによって設定されるスクリプティング変数はありません。</p>
 * <br>
 * <h5>使用方法</h5>
 * <li>JSP</li>
 * <code><pre>
 *  &lt;%
 *    String&nbsp;script=&quot;任意のスクリプト文&quot;;
 *    pageContext.setAttribute(&quot;ON_LOAD&quot;,&nbsp;script);
 *  %&gt;
 *  ・・・
 *  &lt;ts:body&gt;
 *  ・・・
 *  &lt;/ts:body&gt;
 * </pre></code>
 * <li>生成されたHTML</li>
 * <code><pre>
 *  &lt;body onLoad="__onLoad__()"&gt;
 *    &lt;script type="text/javascript"&gt;
 *      &lt;!--
 *        function __onLoad__() {
 *          //キーが"ON_LOAD"のpageContextに格納されたスクリプト文
 *        }
 *      //-->
 *    &lt;/script&gt;
 *  ・・・
 *  &lt;/body&gt;
 * </pre></code>
 *
 * @see jp.terasoluna.fw.web.struts.taglib.MessagesPopupTag
 *
 */
public class BodyTag extends TagSupport {

    /**
	 * シリアルバージョンID
	 */
	private static final long serialVersionUID = -3249997220773531400L;

	/**
     * ログクラス。
     */
    private static Log log = LogFactory.getLog(BodyTag.class);
    
    /**
     * <code>onLoad</code> イベントの処理に追加するスクリプトを取り出すための
     * リクエスト属性のキー。
     */
    public static final String ON_LOAD_KEY = "ON_LOAD";

    /**
     * 画面表示時に実行するJavaScript。
     */
    private String onload = null;

    /**
     * 画面アンロード時に実行するJavaScript。
     */
    private String onunload = null;

    /**
     * スタイルシートのクラス名。
     */
    private String styleClass = null;

    /**
     * 背景色。
     */
    private String bgcolor = null;

    /**
     * 背景に設定する画像。
     */
    private String background = null;

    /**
     * テキスト文字の色。
     */
    private String text = null;

    /**
     * リンク部分の色。
     */
    private String link = null;

    /**
     * 既に選択されたリンク部分の色。
     */
    private String vlink = null;

    /**
     * 選択中のリンク部分の色。
     */
    private String alink = null;

    /**
     * 画面表示時に実行するJavaScriptを設定する。
     *
     * @param value JavaScript
     */
    public void setOnload(String value) {
        this.onload = value;
    }

    /**
     * 画面アンロード時に実行するJavaScriptを設定する。
     *
     * @param value JavaScript
     */
    public void setOnunload(String value) {
        this.onunload = value;
    }

    /**
     * スタイルシートのクラス名を設定する。
     *
     * @param value クラス名
     */
    public void setStyleClass(String value) {
        this.styleClass = value;
    }

    /**
     * 背景色を設定する。
     *
     * @param value 背景色
     */
    public void setBgcolor(String value) {
        this.bgcolor = value;
    }

    /**
     * 背景画像を設定する。
     *
     * @param value 背景色
     */
    public void setBackground(String value) {
        this.background = value;
    }

    /**
     * テキスト文字の色を設定する。
     *
     * @param value テキスト文字の色
     */
    public void setText(String value) {
        this.text = value;
    }

    /**
     * リンク部分の色を設定する。
     *
     * @param value リンク部分の色
     */
    public void setLink(String value) {
        this.link = value;
    }

    /**
     * 既に選択されたリンク部分の色を設定する。
     *
     * @param value 既に選択されたリンク部分の色
     */
    public void setVlink(String value) {
        this.vlink = value;
    }

    /**
     * 選択中のリンク部分の色を設定する。
     *
     * @param value 選択中のリンク部分の色
     */
    public void setAlink(String value) {
        this.alink = value;
    }

    /**
     * タグ評価開始時に呼ばれるメソッド。
     *
     * @return 処理制御指示。常に <code>EVAL_BODY_INCLUDE</code>
     * @throws JspException JSP例外
     */
    @Override
    public int doStartTag() throws JspException {

        HttpServletRequest req
            = (HttpServletRequest) pageContext.getRequest();
        String onLoadBody = (String) req.getAttribute(ON_LOAD_KEY);

        StringBuilder tag = new StringBuilder();
        tag.append("<body");
        if (onLoadBody != null || onload != null) {
            tag.append(" onLoad=\"__onLoad__()\"");
        }
        if (this.onunload != null) {
            tag.append(" onUnLoad=\"" + this.onunload + "\"");
        }
        if (this.styleClass != null) {
            tag.append(" class=\"" + this.styleClass + "\"");
        }
        if (this.bgcolor != null) {
            tag.append(" bgcolor=\"" + this.bgcolor + "\"");
        }
        if (this.background != null) {
            if (this.background.startsWith("/")) {
                this.background = req.getContextPath() + this.background;
            } else {
                this.background = req.getContextPath() + "/" + this.background;
            }
            tag.append(" background=\"" + this.background + "\"");
        }
        if (this.text != null) {
            tag.append(" text=\"" + this.text + "\"");
        }
        if (this.link != null) {
            tag.append(" link=\"" + this.link + "\"");
        }
        if (this.vlink != null) {
            tag.append(" vlink=\"" + this.vlink + "\"");
        }
        if (this.alink != null) {
            tag.append(" alink=\"" + this.alink + "\"");
        }
        tag.append(">" + System.getProperty("line.separator"));

        StringBuilder func = null;
        if (onLoadBody != null || onload != null) {
            func = new StringBuilder();
            func.append("<script type=\"text/javascript\">"
                        + System.getProperty("line.separator"));
            func.append("<!--" + System.getProperty("line.separator"));
            func.append("function __onLoad__() {"
                        + System.getProperty("line.separator"));
            if (onload != null) {
                func.append("  ");
                func.append(onload);
                if (!onload.endsWith(";")) {
                    func.append(";");
                }
                func.append(System.getProperty("line.separator"));
            }
            if (onLoadBody != null) {
                func.append(onLoadBody);
            }
            func.append("}" + System.getProperty("line.separator"));
            func.append("//-->" + System.getProperty("line.separator"));
            func.append("</script>" + System.getProperty("line.separator"));
        }

        try {
            JspWriter out = pageContext.getOut();
            out.print(tag.toString());
            if (func != null) {
                out.print(func.toString());
            }
        } catch (IOException e) {
            log.error("Output failed.");
            throw new JspTagException(e.toString());
        }

        return EVAL_BODY_INCLUDE;
    }

    /**
     * タグ評価終了時に呼ばれるメソッド。
     *
     * @return 処理制御指示。常に <code>EVAL_PAGE</code>
     * @throws JspException JSP例外
     */
    @Override
    public int doEndTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            out.print("</body>");
        } catch (IOException e) {
            log.error("Output failed");
            throw new JspTagException(e.toString());
        }

        return EVAL_PAGE;
    }

    /**
     * タグハンドラ解放時の処理。
     */
    @Override
    public void release() {
        super.release();
        this.onload = null;
        this.onunload = null;
        this.styleClass = null;
        this.bgcolor = null;
        this.background = null;
        this.text = null;
        this.link = null;
        this.vlink = null;
        this.alink = null;
    }

}
