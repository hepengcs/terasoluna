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

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import jp.terasoluna.fw.web.codelist.CodeBean;
import jp.terasoluna.fw.web.codelist.CodeListLoader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * writeCodeCount タグの実装クラス。
 *
 * <p>
 * コードリストのサイズを返却する。<br>
 * 内部処理として、サーブレットコンテキストから
 * （id）で指定された CodeListLoader を探し出し、
 * その中に保存されているコードリストを取得し、そのサイズを返却する。
 * 見つからない場合、サイズ 0 を返却する。
 * </p>
 *
 * <strong>タグがサポートする属性</strong><br>
 * <p> writeCodeCount タグでは、以下の属性をサポートする。</p>
 * <p>
 * <div align="center">
 * <table width="90%" border="1" bgcolor="#FFFFFF">
 *  <tr>
 *   <td> <b>属性名</b> </td>
 *   <td> <b>デフォルト値</b> </td>
 *   <td> <b>必須性</b> </td>
 *   <td> <b>実行時式</b> </td>
 *   <td> <b>概要</b> </td>
 *  </tr>
 *  <tr>
 *   <td> <code>id</code> </td>
 *   <td> <code>-</code> </td>
 *   <td> <code>true</code> </td>
 *   <td> <code>true</code> </td>
 *   <td align="left">
 *    この属性からコードリストを持つコードリストローダーを検索する。つまり
 *    {@link jp.terasoluna.fw.web.codelist.CodeListLoader}
 *    を実装した bean の名前を指定する。
 *    コードリストが見つからない場合、0が返却される。
 *   </td>
 *  </tr>
 * </table>
 * </div>
 * </p>
 * <strong>カスタムタグのスクリプティング変数</strong><br>
 * <p>このタグによって設定されるスクリプティング変数はありません。</p>
 * <br>
 * <strong>使用方法</strong><br>
 * 以下の例は、CodeListLoader インタフェースを実装したクラスの bean を
 * &quot;loader1&quot; と言う名前で定義して使用する場合の設定例である。<br>
 * 定義の方法は
 * {@link jp.terasoluna.fw.web.codelist.DBCodeListLoader}、
 * 及び
 * {@link jp.terasoluna.fw.web.codelist.MappedCodeListLoader}
 * を参照。
 * </p>
 *
 * <strong>JSP内での記述例。</strong><br>
 * <p>
 * <code><pre>
 *  …
 *  &lt;ts:writeCodeCount id=<Strong>"loader1"</Strong> /&gt;
 *  …
 * </pre></code>
 * コードリストの取得に関しては、{@link DefineCodeListTag} を参照。<br>
 *
 * </p>
 *
 * @see jp.terasoluna.fw.web.codelist.DBCodeListLoader
 * @see jp.terasoluna.fw.web.codelist.MappedCodeListLoader
 * @see jp.terasoluna.fw.web.taglib.DefineCodeListTag
 *
 */
public class WriteCodeCountTag extends TagSupport {

    /**
	 * シリアルバージョンID
	 */
	private static final long serialVersionUID = -2318799214314166540L;

	/**
     * ログクラス。
     */
     private static Log log =
         LogFactory.getLog(WriteCodeCountTag.class);

    /**
     * タグ評価開始時に呼ばれるメソッド。
     *
     * <p>
     *   サーブレットコンテキストからコードリストローダー
     * を検索し、コードリストを発見した場合、コードリストの要素数を
     * 返却する。
     * コードリストが発見できない場合、0が画面に書き込まれる。
     * </p>
     *
     * @return 処理制御指示。常に <code>EVAL_BODY_INCLUDE</code>
     * @throws JspException <code>JSP</code>例外
     */
    @Override
    public int doStartTag() throws JspException {
        if (log.isDebugEnabled()) {
            log.debug("doStartTag() called.");
        }

        JspWriter out = pageContext.getOut();

        try {
            if ("".equals(id)) {
                // idが存在しない場合
                log.error("id is required.");
                throw new JspTagException("id is required.");
            }

            // pageContextより、ApplicationContextを取得する。
            ServletContext sc = pageContext.getServletContext();
            ApplicationContext context =
                WebApplicationContextUtils.getRequiredWebApplicationContext(sc);

            CodeListLoader loader = null;

            try {
                loader = (CodeListLoader) context.getBean(id);
            } catch (ClassCastException e) {
                //取得したBeanがCodeListLoaderではなかったら例外をスロー
                String errorMessage = "bean id:" + id
                    + " is not instance of CodeListLoader.";
                log.error(errorMessage);
                throw new JspTagException(errorMessage, e);
            }

            CodeBean[] codeBeanList = loader.getCodeBeans();
            if (codeBeanList == null) {
                // codeBeanListがnullの場合0を出力する。
                if (log.isWarnEnabled()) {
                    log.warn("Codebean is null. CodeListLoader(bean id:"
                            + id + ")");
                }
                out.print(0);
            } else {
                // 正常に取得できた場合はコードリストの長さを出力する。
                out.print(codeBeanList.length);
            }

            return EVAL_BODY_INCLUDE;
        } catch (IOException ioe) {
            log.error("IOException caused.");
            throw new JspTagException(ioe.toString());
        }
    }

    /**
     * タグ評価終了時に呼ばれるメソッド。
     *
     * @return 処理制御指示。常に EVAL_PAGE
     * @throws JspException  JSP 例外
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
    }

}
