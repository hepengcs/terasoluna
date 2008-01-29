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

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import jp.terasoluna.fw.web.codelist.CodeBean;
import jp.terasoluna.fw.web.codelist.CodeListLoader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * defineCodeList タグの実装クラス。
 *
 * <p>
 * サーブレットコンテキストから
 * 属性 id で指定された CodeListLoader を探し出し、
 * その中に保存されているコードリストを取得する。
 * 見つからない場合は空のコードリストの取得を行う。
 * </p>
 *
 * コードリスト内の要素はプロパティ名が id 、 name
 * で格納されているため、アクセスするためには、タグ内のプロパティに
 * これら要素名を用いる。
 * コードリストの読み込みは、
 * {@link jp.terasoluna.fw.web.codelist.CodeListLoader}
 * インタフェースの実装クラスを参照のこと。
 *
 *
 * <strong>タグがサポートする属性</strong><br>
 * <p> defineCodeList タグでは、以下の属性をサポートする。</p>
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
 *    この属性からCodeListLoaderを検索する。つまり
 *    {@link jp.terasoluna.fw.web.codelist.CodeListLoader}
 *    インタフェースを実装したbeanの名前を指定する。
 *    このタグ宣言以降、&lt;logic:iterator&gt;タグ、
 *    &lt;html:options&gt;タグなどでコードリストが参照できる。
 *  </tr>
 * </table>
 * </div>
 * </p>
 * <strong>カスタムタグのスクリプティング変数</strong><br>
 * <p>このタグによって設定されるスクリプティング変数はありません。</p>
 * <br>
 * <strong>使用方法</strong><br>
 * <p>
 * 以下の例は、CodeListLoader インタフェースを実装したクラスの bean を
 * &quot;loader1&quot;と言う名前で定義して使用する場合の設定例である。<br>
 * 定義の方法は
 * {@link jp.terasoluna.fw.web.codelist.DBCodeListLoader}、
 * 及び
 * {@link jp.terasoluna.fw.web.codelist.MappedCodeListLoader}
 * を参照。
 * </p>
 *
 * <strong>JSP 内での記述例。</strong><br>
 *
 * <code><pre>
 *  &lt;ts:defineCodeList id=<b>"loader1"</b> /&gt;
 *  …
 *  &lt;html:select property="selectOptions"&gt;
 *    &lt;html:options collection=<b>"loader1"</b>
 *                  labelProperty=<b>"name"</b>
 *                  property=<b>"id"</b>/&gt;
 *  &lt;/html:select&gt;
 *  …
 * </pre></code>
 * コードリストのサイズ取得に関しては、{@link WriteCodeCountTag} を参照
 * </p>
 *
 * @see jp.terasoluna.fw.web.codelist.CodeListLoader
 * @see jp.terasoluna.fw.web.codelist.DBCodeListLoader
 * @see jp.terasoluna.fw.web.codelist.MappedCodeListLoader
 * @see jp.terasoluna.fw.web.taglib.WriteCodeCountTag
 *
 */
public class DefineCodeListTag extends TagSupport {

    /**
     * シリアルバージョンID
     */
    private static final long serialVersionUID = 7550280327858449058L;

    /**
     * ログクラス。
     */
     private static Log log =
         LogFactory.getLog(DefineCodeListTag.class);

    /**
     * タグ評価開始時に呼ばれるメソッド。
     *
     * <p>
     * サーブレットコンテキストから、ApplicationContextを取得し、
     * &quot;page&quot; 属性で指定された id
     * で CodeListLoader を取得し、その中の
     * コードリストを pageContext に登録する。
     *
     * コードリストが発見できない場合、空のArrayListを
     *  pageContext に登録する。
     * なお、登録時のスコープは &quot;page&quot; 属性とする。
     * </p>
     *
     * @return 処理制御指示。常に EVAL_BODY_INCLUDE
     * @throws JspException JSP 例外
     */
    @Override
    public int doStartTag() throws JspException {
        if (log.isDebugEnabled()) {
            log.debug("doStartTag() called.");
        }

        if ("".equals(id)) {
            // idが存在しない場合
            log.error("id is required.");
            throw new JspTagException("id is required.");
        }
        // サーブレットコンテキストより、ApplicationContextを取得する。
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

        CodeBean[] codeBeanArray = loader.getCodeBeans();
        if (codeBeanArray == null) {
            // codeBeanListがnullの場合空のArrayListを設定する。
            if (log.isWarnEnabled()) {
                log.warn("Codebean is null. CodeListLoader(bean id:"
                        + id + ")");
            }
            pageContext.setAttribute(id ,
                    new ArrayList() , PageContext.PAGE_SCOPE);
        } else {
            // コードリストを登録する。
            pageContext.setAttribute(
                    id , codeBeanArray , PageContext.PAGE_SCOPE);
        }

        return EVAL_BODY_INCLUDE;
    }

    /**
     * タグ評価終了時に呼ばれるメソッド。
     *
     * @return 処理制御指示。常に EVAL_PAGE
     * @throws JspException JSP 例外
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
