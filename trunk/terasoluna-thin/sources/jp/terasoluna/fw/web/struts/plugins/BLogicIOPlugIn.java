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

package jp.terasoluna.fw.web.struts.plugins;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletException;

import jp.terasoluna.fw.service.thin.AbstractBLogicMapper;
import jp.terasoluna.fw.service.thin.BLogicMapper;
import jp.terasoluna.fw.service.thin.BLogicResources;
import jp.terasoluna.fw.util.ClassLoadException;
import jp.terasoluna.fw.util.ClassUtil;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.apache.commons.digester.xmlrules.XmlLoadException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;
import org.xml.sax.SAXException;

/**
 * ビジネスロジック入出力の設定情報をロードするプラグイン。
 *
 * <p>StrutsのPlugIn機能を使用し、サーブレット初期化時に
 * ビジネスロジック入出力の設定を読み込み、サーブレットコンテキスト
 * に保存する。<br>
 * この機能を使用するにはstruts-config.xmlに以下のように設定する。
 * （ digesterRules、mapperClassは省略可。）
 * なお、resourcesはカンマでファイルを連結できる。
 * </p>
 * <code><pre>
 * &lt;plug-in
 *   className="jp.terasoluna.fw.web.struts.plugins.BLogicIOPlugIn"&gt;
 *    &lt;set-property
 *        property="resources"
 *        value="/WEB-INF/blogic-io.xml"/&gt;
 *    &lt;set-property
 *        property="digesterRules"
 *        value="/WEB-INF/blogic-io-rules.xml"/&gt;
 *    &lt;set-property
 *        property="mapperClass"
 *        value="jp.terasoluna.fw.service.thin.BLogicMapper"/&gt;
 * &lt;/plug-in&gt;
 * </pre></code>
 * <p>ビジネスロジック入出力情報反映クラスを拡張クラスに変更する場合は、
 * mapperClassのvalue属性にAbstractBLogicMapperのサブクラス、
 * またはBLogicMapperを継承したBLogicMapper拡張クラスを設定する。</p>
 *
 * blogic-io.xmlにはアクションごとにビジネスロジック
 * の入出力情報を設定する。<br>
 * 下記はblogic-io.xmlの設定である。
 * <code><pre>
 * &lt;blogic-io&gt;
 *   &lt;action path="/logon/logonAction"&gt;
 *     &lt;blogic-param bean-name="jp.terasoluna.sample.blogic.LogonBean"&gt;
 *       &lt;set-property property="userName"
 *                        blogic-property="userName"
 *                        source="form" /&gt;
 *       &lt;set-property property="sessionId"
 *                        blogic-property="id"
 *                        source="session" /&gt;
 *     &lt;/blogic-param&gt;
 *     &lt;blogic-result&gt;
 *       &lt;set-property property="resultStr"
 *                        blogic-property="result"
 *                        dest="form" /&gt;
 *       &lt;set-property property="USER_VALUE_OBJECT"
 *                        blogic-property="uvo"
 *                        dest="session" /&gt;
 *     &lt;/blogic-result&gt;
 *   &lt;/action&gt;
 *   ・・・
 * &lt;/blogic-io&gt;
 * </pre></code>
 * <p>
 * ビジネスロジックの入力値情報は&lt;blogic-param&gt;
 * 要素内で設定する。bean-name属性には入力値を格納する
 * JavaBeanのクラス名を指定する。このJavaBeanは、後述する
 * blogic-property属性に指定されるプロパティを所持していなければならない。<br>
 * 但し、入力値が存在しないビジネスロジックの場合は、bean-name属性を省略
 * することで、引数がnullのビジネスロジックを実行可能である。<br>
 * 入力値情報の元となるデータはアクションフォームやセッションなどのWeb層に
 * 設定されている情報であり、どこからデータを取得するかは
 * &lt;set-property&gt;要素のsource属性に指定された文字列で識別する。
 * デフォルトでは、form,sessionのどちらかをsource属性に記述することで
 * 情報元がアクションフォームかセッションかを識別する。<br>
 * 入力元のデータのプロパティ名はproperty属性で指定する。
 * すなわち、<br>
 * property="field1" source="form"と設定した場合は、
 * actionForm.get("field1")が実行され、
 * property="field2" source="session"と設定した場合は、
 * session.getAttribute("field2")が実行される。<br>
 * 上記の結果取得された値は、前述のbean-name属性で指定された
 * JavaBeanインスタンスのプロパティ値としてビジネスロジックから取得できる。
 * JavaBeanから値を取得する場合は、blogic-propertyで指定した値が
 * プロパティ名となる。blogic-property属性が指定されない場合、
 * property属性と同じ値をJavaBeanのプロパティ名とする。
 * </p>
 * <p>
 * <code><pre>
 *  &lt;set-property property="field1" blogic-property="blogicField1"
 *                   source="form" /&gt;
 * </pre></code>
 * と設定し、ビジネスロジック内からアクションフォームのfield1
 * の値を取得する場合、
 * bean.getBlogicField1()
 * を実行することでアクションフォームの値が取得できる。<br>
 * 入力元のデータがセッションの値でも同様であり、
 * <code><pre>
 *  &lt;set-property property="field2" blogic-property="blogicField2"
 *                   source="session" /&gt;
 * </pre></code>
 * と設定し、ビジネスロジック内からfield2というキーでセッションに格納
 * されている値を取得する場合、
 * bean.getBlogicField2()
 * を実行すれば良い。
 * つまり、ビジネスロジック側からはデータの入力元がアクションフォーム
 * であるのか、セッションであるのかを意識する必要はない。<br>
 * この設定情報はBLogicResourcesのインスタンスに
 * 読み込まれ、サーブレットコンテキストに保存される。</p>
 * <br>
 * <p>
 * 公開識別子、およびDTDのURLを変更する場合は、<br>
 * getPublicIdentifier()とgetDtdUrl()をオーバーライドすること。
 * </p>
 *
 * @see jp.terasoluna.fw.web.struts.actions.BLogicAction
 * @see jp.terasoluna.fw.service.thin.BLogic
 * @see jp.terasoluna.fw.service.thin.BLogicResources
 * @see jp.terasoluna.fw.service.thin.BLogicResult
 * @see jp.terasoluna.fw.service.thin.AbstractBLogicMapper
 * @see jp.terasoluna.fw.service.thin.BLogicMapper
 *
 */
public class BLogicIOPlugIn implements PlugIn {

    /**
     * ログクラス。
     */
    private static Log log = LogFactory.getLog(BLogicIOPlugIn.class);

    /**
     * サーブレットコンテキストに登録されるBLogicMapperの
     * プリフィックスキー。
     */
    public static final String BLOGIC_MAPPER_KEY = "BLOGIC_MAPPER";

    /**
     * デフォルトのblogic-io-rules.xml のパス。
     */
    private static final String DIGESTER_RULES_PATH =
        "/WEB-INF/blogic-io-rules.xml";

    /**
     * DTDの公開識別子。
     */
    private String publicIdentifier =
        "-//NTTDATA//DTD TERASOLUNA for Spring blogic-io 1.0//JA";

    /**
     * DTDパス。
     */
    private String dtdUrl =
        "/jp/terasoluna/fw/web/struts/plugins/dtd/blogic-io.dtd";

    /**
     * このプラグインを生成したサーブレット。
     */
    private ActionServlet servlet = null;

    /**
     * ビジネスロジック入出力情報ルール定義ファイル
     * （blogic-io-rules.xml）。
     */
    private String digesterRules = null;

    /**
     * ビジネスロジック入出力情報定義ファイル（blogic-io.xml）。
     */
    private String resources = null;

    /**
     * BLogicMapperを指定したクラスファイル名。
     * 指定なしの場合、デフォルトのクラス名が使用される。
     */
    private String mapperStr = null;

    /**
     * ビジネスロジック入出力情報を保持するインスタンス。
     */
    private BLogicResources blogicResources = null;

    /**
     * ビジネスロジック入出力情報マッパー。
     */
    private AbstractBLogicMapper blogicMapper = null;

    /**
     * Digesterインスタンス。
     */
    private static Digester digester = null;

    /**
     * 終了時処理。
     */
    public void destroy() {
        // 何もしない

    }

    /**
     * PlugInの初期化時処理。
     *
     * <p>BLogicResources、及び BLogicMapperを
     * サーブレットコンテキストに登録する。</p>
     *
     * <p>
     *  このプラグインを起動したサーブレット、モジュールコンフィグが
     *  nullであるとき、 NullPointerExceptionが
     *  発生する
     * </p>
     *
     * @param servlet このプラグインを起動したサーブレット
     * @param config モジュールコンフィグ
     * @exception ServletException 初期化時例外
     */
    public void init(ActionServlet servlet, ModuleConfig config)
        throws ServletException {

        this.servlet = servlet;
        String moduleName = config.getPrefix();

        // BLogicResourcesをサーブレットコンテキストに登録
        initResources();
        servlet.getServletContext().setAttribute(
            BLogicResources.BLOGIC_RESOURCES_KEY + moduleName, blogicResources);

        // BLogicMapperをサーブレットコンテキストに登録
        initMapper();
        servlet.getServletContext().setAttribute(BLOGIC_MAPPER_KEY + moduleName,
            this.blogicMapper);
    }

    /**
     * ビジネスロジック入出力情報初期化処理。
     *
     * <p>blogic-io.xml、blogic-io-rules.xml
     * を利用して、設定情報をBeanに読み込む。</p>
     *
     * <p>XML設定ファイルの形式不正、IO例外が発生した時、
     * ServletExceptionにラップしスローする。</p>
     *
     * @exception ServletException ビジネスロジック入出力情報初期化処理時に
     * 発生する例外
     */
    private void initResources() throws ServletException {
        if (resources == null || "".equals(resources)) {
            if (log.isDebugEnabled()) {
                log.debug("resources file location is not specified");
            }
            return;
        }
        StringTokenizer st = new StringTokenizer(resources, ",");
        List<String> pathList = new ArrayList<String>();
        while (st.hasMoreTokens()) {
            String path = st.nextToken();
            path = path.trim();
            if (log.isDebugEnabled()) {
                log.debug("blogic io file=" + path);
            }
            pathList.add(path);
        }
        try {
            if (digester == null) {
                if (digesterRules == null) {
                    // ルールファイル未設定の時、デフォルトのルールファイルを
                    // 使用する。
                    digesterRules = DIGESTER_RULES_PATH;
                }
                digester = DigesterLoader.createDigester(
                    servlet.getServletContext().getResource(digesterRules));
                digester.setValidating(true);
            }
            blogicResources = new BLogicResources();
            for (int i = 0; i < pathList.size(); i++) {
                digester.push(blogicResources);
                URL url = this.getClass().getResource(getDtdUrl());
                if (url != null) {
                    digester.register(getPublicIdentifier(), url.toString());
                }
                digester.parse(
                    servlet.getServletContext().getResourceAsStream(
                    pathList.get(i)));
            }
        } catch (MalformedURLException e) {
            log.error("", e);
            throw new ServletException(e);
        } catch (IOException e) {
            log.error("", e);
            throw new ServletException(e);
        } catch (SAXException e) {
            log.error("", e);
            throw new ServletException(e);
        } catch (XmlLoadException e) {
            log.error("", e);
            throw new ServletException(e);
        }

    }

    /**
     * 公開識別子を返却する。
     * 公開識別子を変更する場合は、このメソッドをオーバーライドする。
     * @return 公開識別子
     */
    public String getPublicIdentifier() {
        return publicIdentifier;
    }

    /**
     * DTDのURLを返却する。
     * DTDのURLを変更する場合は、このメソッドをオーバーライドする。
     * @return DTDのURL
     */
    public String getDtdUrl(){
        return dtdUrl;
    }

    /**
     * マッパー登録処理。
     *
     * <p>BLogicMapperをロードし、設定情報を
     * インスタンスフィールドに登録する。</p>
     *
     * <p>インスタンス内部に保持されている BLogicMapper
     * のクラスロードに失敗した時、 ServletExceptionを
     * スローする。</p>
     *
     * @throws ServletException サーブレット例外
     */
    private void initMapper() throws ServletException {
        Object[] args = {resources};
        if (this.mapperStr != null) {
            try {
                this.blogicMapper =
                    (AbstractBLogicMapper) ClassUtil.create(
                            this.mapperStr, args);
            } catch (ClassLoadException e) {
                log.error("", e);
                throw new ServletException(e);
            } catch (ClassCastException e) {
                log.error("", e);
                throw new ServletException(e);
            }
        } else {
            this.blogicMapper = new BLogicMapper(resources);
        }
    }

    /**
     * blogic-io-rules.xmlを設定する。
     *
     * @param digesterRules blogic-io-rules.xml
     */
    public void setDigesterRules(String digesterRules) {
        this.digesterRules = digesterRules;
    }

    /**
     * blogic-io.xmlを設定する。
     *
     * @param resources blogic-io.xml
     */
    public void setResources(String resources) {
        this.resources = resources;
    }

    /**
     * ビジネスロジック入出力情報反映クラスを設定する。
     *
     * @param mapperStr ビジネスロジック入出力情報反映クラス名
     */
    public void setMapperClass(String mapperStr) {
        this.mapperStr = mapperStr;
    }

    /**
     * 公開識別子を設定する。
     * @param publicIdentifier 公開識別子
     */
    public void setPublicIdentifier(String publicIdentifier) {
        this.publicIdentifier = publicIdentifier;
    }

    /**
     * DTDのURLを設定する。
     * @param dtdUrl DTDのURL
     */
    public void setDtdUrl(String dtdUrl) {
        this.dtdUrl = dtdUrl;
    }

}
