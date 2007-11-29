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
 * WriteCodeValue タグ。
 *
 * <p>
 * サーブレットコンテキストから
 * 属性 codeList で指定された CodeListLoader を探し出し、
 * その中に保存されているコードリストから値を取得し、出力する。
 * 見つからない場合は何も出力しない。
 * </p>
 *
 * コードリストの読み込みは、
 * {@link jp.terasoluna.fw.web.codelist.CodeListLoader}
 * インタフェースの実装クラスを参照のこと。
 *
 * <strong>タグがサポートする属性</strong><br>
 * <p> writeCodeValue タグでは、以下の属性をサポートする。</p>
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
 *   <td> <code>codeList</code> </td>
 *   <td> <code>-</code> </td>
 *   <td> <code>true</code> </td>
 *   <td> <code>true</code> </td>
 *   <td align="left">
 *    この属性からCodeListLoaderを検索する。つまり
 *    {@link jp.terasoluna.fw.web.codelist.CodeListLoader}
 *    インタフェースを実装したbeanの名前を指定する。
 *  </tr>
 *  <tr>
 *   <td> <code>key</code> </td>
 *   <td> <code>-</code> </td>
 *   <td> <code>false</code> </td>
 *   <td> <code>true</code> </td>
 *   <td align="left">
 *    取得したコードリストから値を取得するためのキーを直接指定する。
 *  </tr>
 *  <tr>
 *   <td> <code>name</code> </td>
 *   <td> <code>-</code> </td>
 *   <td> <code>false</code> </td>
 *   <td> <code>true</code> </td>
 *   <td align="left">
 *    取得したコードリストから値を取得するためのキーを保持するBeanの名前。
 *    key属性が指定されていた場合は、無効。
 *  </tr>
 *  <tr>
 *   <td> <code>property</code> </td>
 *   <td> <code>-</code> </td>
 *   <td> <code>false</code> </td>
 *   <td> <code>true</code> </td>
 *   <td align="left">
 *    取得したコードリストから値を取得するためのキーを保持するBeanのプロパティ。
 *    key属性が指定されていた場合は、無効。
 *  </tr>
 *  <tr>
 *   <td> <code>scope</code> </td>
 *   <td> <code>-</code> </td>
 *   <td> <code>false</code> </td>
 *   <td> <code>true</code> </td>
 *   <td align="left">
 *    取得したコードリストから値を取得するためのキーを保持するBeanが
 *    存在するスコープ。
 *  </tr>
 * </table>
 * </div>
 * </p>
 * <strong>カスタムタグのスクリプティング変数</strong><br>
 * <p>このタグによって設定されるスクリプティング変数はありません。</p>
 * <br>
 * <strong>使用方法</strong><br>
 * <p>
 * 以下の例は、CodeListLoader インタフェースを実装したクラスの bean が
 * &quot;loader1&quot;と言う名前で定義されており、
 * その中から&quot;key1&quot;というキーで取得できる値を
 * 出力する場合の設定例である。<br>
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
 *  &lt;t:writeCodeValue codeList=<b>"loader1"</b> key=<b>"key1"</b> /&gt;
 * </pre></code>
 * コードリストのサイズ取得に関しては、{@link WriteCodeCountTag} を参照
 * </p>
 *
 * @see jp.terasoluna.fw.web.codelist.CodeListLoader
 * @see jp.terasoluna.fw.web.codelist.DBCodeListLoader
 * @see jp.terasoluna.fw.web.codelist.MappedCodeListLoader
 * @see jp.terasoluna.fw.web.taglib.DefineCodeListTag
 * @see jp.terasoluna.fw.web.taglib.WriteCodeCountTag
 *
 */
public class WriteCodeValueTag extends TagSupport {

    /**
	 * シリアルバージョンID
	 */
	private static final long serialVersionUID = 8199383777405058816L;

	/**
     * ログクラス。
     */
     private static Log log =
         LogFactory.getLog(WriteCodeValueTag.class);

     /**
      * コードリスト名。
      */
     private String codeList = null;

     /**
      * コードリストからvalueを取得するためのキー。
      */
     private String key = null;

     /**
      *  propertyで指定した値を取り出す為のBean名。
      */
     private String name = null;

     /**
      *  nameによって指定されたBean上でアクセスされるプロパティ名。
      */
     private String property = null;

     /**
      *  nameによって指定したbeanを取り出す為に検索するスコープ。
      */
     private String scope = null;

     /**
      * Bean名を設定する。
      *
      * @param name Bean名
      */
     public void setName(String name) {
         this.name = name;
     }

     /**
      * プロパティ名を設定する。
      *
      * @param property プロパティ名
      */
     public void setProperty(String property) {
         this.property = property;
     }

     /**
      * スコープを設定する。
      *
      * @param scope スコープ
      */
     public void setScope(String scope) {
         this.scope = scope;
     }

     /**
      * キーを設定する。
      *
      * @param key コードリストのキー
      */
     public void setKey(String key) {
         this.key = key;
     }

     /**
      * コードリスト名を設定する。
      *
      * @param codeList コードリスト名
      */
     public void setCodeList(String codeList) {
         this.codeList = codeList;
     }


    /**
     * タグ評価開始時に呼ばれるメソッド。
     *
     * <p>
     * サーブレットコンテキストから、ApplicationContextを取得し、
     * &quot;codeList&quot; 属性で指定された id
     * で CodeListLoader を取得し、その中のコードリストから
     * 値を取得し、出力する。
     * &quot;key&quot; 属性が指定されていれば、そのキー値を取得し、
     * 指定されなければ、&quot;name&quot; 属性で指定されるbeanから
     * キーを取得して用いる。
     *
     * コードリストが発見できない場合や、
     * キーが存在しない場合は、何も出力しない。
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

        if ("".equals(codeList) || codeList == null) {
            // codeListが指定されない場合
            log.error("codeList is required.");
            throw new JspTagException("codeList is required.");
        }

        if (key == null && name == null) {
            // keyとname共に指定されない場合
            log.error("key and name is required.");
            throw new JspTagException("key and name is required.");
        }

        String codeKey = null;

        if ((key == null) && (name != null)) {
            if (TagUtil.lookup(pageContext, name, scope) == null) {
                log.error("bean id:" + name + " is not defined.");
                throw new JspTagException("bean id:" + name
                        + " is not defined.");
            }
            Object bean = null;
            try {
                bean = TagUtil.lookup(pageContext, name, property, scope);
            } catch (JspException e) {
                // 何もしない
            }
            if (bean == null) {
                log.error("Cannot get property[" + name + "."
                        + property + "].");
                throw new JspTagException("Cannot get property[" + name + "."
                        + property + "].");
            }
			codeKey = bean.toString();
        } else {
            codeKey = key;
        }

        // サーブレットコンテキストより、ApplicationContextを取得する。
        ServletContext sc = pageContext.getServletContext();
        ApplicationContext context =
            WebApplicationContextUtils.getRequiredWebApplicationContext(sc);

        CodeListLoader loader = null;

        if (context.containsBean(codeList)) {
            try {
                loader = (CodeListLoader) context.getBean(codeList);
            } catch (ClassCastException e) {
                // 取得したBeanがCodeListLoaderではなかったら例外をスロー
                String errorMessage = "bean id:" + codeList
                        + " is not instance of CodeListLoader.";
                log.error(errorMessage);
                throw new JspTagException(errorMessage, e);
            }
        }

        JspWriter out = pageContext.getOut();

        if (loader == null) {
            log.error("CodeListLoader:" + codeList + " is not defined.");
            throw new JspTagException("CodeListLoader:" + codeList
                    + " is not defined.");
        }

        CodeBean[] codeBeanArray = loader.getCodeBeans();
        if (codeBeanArray == null) {
            // codeBeanListがnullの場合
            if (log.isWarnEnabled()) {
                log.warn("Codebean is null. CodeListLoader(bean id:"
                        + codeList + ")");
            }
            // 何もしない
        } else {
            try {
                // 正常に取得できた場合はコードリストの値を出力する。
                for (int i = 0; i < codeBeanArray.length; i++) {
                    if (codeKey.equals(codeBeanArray[i].getId())) {
                        out.print(codeBeanArray[i].getName());
                        break;
                    }
                }
                // 何もしない
            } catch (IOException ioe) {
                log.error("", ioe);
                throw new JspTagException(ioe);
            }

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
        this.codeList = null;
        this.key = null;
        this.name = null;
        this.property = null;
        this.scope = null;
    }

}
