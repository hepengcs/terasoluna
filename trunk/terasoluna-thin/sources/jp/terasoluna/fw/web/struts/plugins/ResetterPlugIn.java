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

import jp.terasoluna.fw.util.ClassLoadException;
import jp.terasoluna.fw.util.ClassUtil;
import jp.terasoluna.fw.web.struts.reset.Resetter;
import jp.terasoluna.fw.web.struts.reset.ResetterImpl;
import jp.terasoluna.fw.web.struts.reset.ResetterResources;

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
 * フォームのリセット設定をロードするプラグイン。
 *   
 * PlugIn機能を使用し、サーブレット初期化時にフォームのリセット機能の
 * 設定を読み込み、サーブレットコンテキストに保存する。
 *   
 * <p>
 * フォームリセット定義ファイル(reset.xml)の設定情報は
 * {@link jp.terasoluna.fw.web.struts.reset.ResetterResources}
 * のインスタンスとしてサーブレットコンテキストに保存される。<br>
 * 実行時には、
 * {@link jp.terasoluna.fw.web.struts.form.FormEx}#reset()
 * メソッドから任意の
 * {@link jp.terasoluna.fw.web.struts.reset.Resetter}
 * が呼び出され、
 * {@link jp.terasoluna.fw.web.struts.reset.ResetterResources}
 * に格納されている設定情報に従ってリセット処理を行なう。<br>
 * リセット処理を委譲するクラスは 、
 * {@link jp.terasoluna.fw.web.struts.reset.Resetter}
 * を実装した任意のクラス。<br>
 *   <br><br>
 * </p>
 *
 * <strong>使用方法</strong><br>
 * この機能を使用するには Struts設定ファイル(struts-config.xml)
 * に以下のように設定する。<br>
 * resetter はリセット処理を行なう実装クラス
 * （※ resetter は省略可。省略時はデフォルトを使用。
 *   デフォルト="jp.terasoluna.fw.web.struts.reset.ResetterImpl")
 * resources にはフォームリセット定義ファイル(reset.xml)、
 * digesterRulesには、フォームリセットルール定義ファイル(reset-rules.xml)
 * を指定する。
 * （digesterRulesは省略可。）<br>
 * <code><pre>
 * &lt;plug-in className="jp.terasoluna.fw.web.struts.plugins.ResetterPlugIn"&gt;
 *   &lt;set-property
 *     property="resetter"
 *     value="jp.terasoluna.fw.web.struts.reset.ResetterImpl"/&gt;
 *   &lt;set-property
 *     property="resources"
 *     value="/WEB-INF/reset.xml"/&gt;
 *   &lt;set-property
 *     property="digesterRules"
 *     value="/WEB-INF/reset-rules.xml"/&gt;
 * &lt;/plug-in&gt;
 * </pre></code><br>
 * フォームリセット定義ファイル(reset.xml)にはアクションごとに
 * リセット対象フィールドを設定する。<br>
 * 指定範囲リセット機能を使用する場合は、select 属性を
 * true に設定する。(詳細については
 * {@link jp.terasoluna.fw.web.struts.reset.Resetter}
 * を参照。)
 * <code><pre>
 * &lt;reset&gt;
 *   &lt;action path="/resetAction"&gt;
 *     &lt;property-reset name="field1" /&gt;
 *     &lt;property-reset name="field2" select="true" /&gt;
 *   &lt;/action&gt;
 *   ・・・
 * &lt;/reset&gt;
 * </pre></code>
 * <br>
 * <p>
 * 公開識別子、およびDTDのURLを変更する場合は、<br>
 * getPublicIdentifier()とgetDtdUrl()をオーバーライドすること。
 * </p>
 *
 * @see jp.terasoluna.fw.web.struts.reset.ActionReset
 * @see jp.terasoluna.fw.web.struts.reset.FieldReset
 * @see jp.terasoluna.fw.web.struts.reset.Resetter
 * @see jp.terasoluna.fw.web.struts.reset.ResetterResources
 * @see jp.terasoluna.fw.web.struts.form.FormEx
 * @see 
 *  jp.terasoluna.fw.web.struts.form.DynaValidatorActionFormEx
 * @see jp.terasoluna.fw.web.struts.form.ValidatorActionFormEx
 * 
 */
public class ResetterPlugIn implements PlugIn {

    /**
     * ログクラス。
     */
    private static Log log = LogFactory.getLog(ResetterPlugIn.class);

    /**
     * デフォルトの フォームリセット定義ファイル(reset.xml) のパス。
     */
    private static final String DIGESTER_RULES_PATH =
        "/WEB-INF/reset-rules.xml";

    /**
     * フォームリセットルール定義ファイル(reset-rules.xml) の情報を設定済みの
     * Digesterインスタンスの参照
     */
    private static Digester digester = null;

    /**
     * デフォルトリセッタクラスの完全修飾クラス名
     */
    private static final String DEFAULT_RESETTER =
        ResetterImpl.class.getName();

    /**
     * DTDの公開識別子。
     */
    private String publicIdentifier =
        "-//NTTDATA//DTD TERASOLUNA for Spring reset 1.0//JA";

    /**
     * DTDパス。
     */
    private String dtdUrl =
        "/jp/terasoluna/fw/web/struts/plugins/dtd/reset.dtd";

    /**
     * リセッタクラスの完全修飾クラス名。
     */
    private String resetter = null;

    /**
     * フォームリセット定義ファイル(reset.xml) のパス。
     */
    private String resourcesPath = null;

    /**
     * フォームリセットルール定義ファイル(reset-rules.xml) のパス。
     */
    private String digesterRules = null;

    /**
     * 終了時処理。
     */
    public void destroy() {
        // 何もしない
    }

    /**
     * 初期化時処理。
     *
     * @param servlet このPlugInを起動した ActionServlet。
     * @param config この PlugIn の属する ModuleConfig。
     * @exception ServletException 初期化時に発生した例外をラップした例外。
     */
    public void init(ActionServlet servlet, ModuleConfig config)
        throws ServletException {

        if (log.isDebugEnabled()) {
            log.debug("init() called.");
        }

        // Resetterの初期化処理
        initResetter(servlet, config);

        // ResetterResourcesの初期化処理
        initResources(servlet, config);
    }

    /**
     * リセット処理クラスの初期化処理。
     * 
     * Struts設定ファイル(struts-config.xml)
     * に設定したリセット処理クラスを取得する。
     * 未設定の場合はデフォルトリセット処理クラスを取得する。
     * @param servlet このプラグインを起動したサーブレット。
     * @param config モジュールコンフィグ
     * @exception ServletException リセット処理クラスの初期化処理時に発生する例外
     */
    protected void initResetter(ActionServlet servlet, ModuleConfig config)
        throws ServletException {

        // Resetterのインスタンスを格納する変数
        Resetter resetterObj = null;

        try {
            if (this.resetter == null || "".equals(this.resetter)) {
                resetterObj = (Resetter) ClassUtil.create(DEFAULT_RESETTER);
            } else {
                resetterObj = (Resetter) ClassUtil.create(this.resetter);
            }
        } catch (ClassLoadException e) {
            log.error("", e);
            throw new ServletException(e);
        }

        // Resetterをサーブレットコンテキストに追加
        servlet.getServletContext().setAttribute(
            Resetter.RESETTER_KEY + config.getPrefix(),
            resetterObj);
    }

    /**
     * リセット初期化処理。
     * 
     * フォームリセット定義ファイル(reset.xml)、
     * フォームリセットルール定義ファイル(reset-rules.xml)を利用して、
     * 設定情報を
     * {@link 
     *   jp.terasoluna.fw.web.struts.reset.ResetterResources}
     * に読み込む。
     *
     * @param servlet このプラグインを起動したサーブレット。
     * @param config モジュールコンフィグ
     * @exception ServletException リソースファイルが見つからない時に
     * 発生する例外
     */
    protected void initResources(ActionServlet servlet, ModuleConfig config)
        throws ServletException {

        if (this.resourcesPath == null || "".equals(this.resourcesPath)) {
            log.error("resources file location is not specified");
            throw new ServletException(
                "resources file location is not specified");
        }
        StringTokenizer st = new StringTokenizer(resourcesPath, ",");
        List<String> pathList = new ArrayList<String>();
        while (st.hasMoreTokens()) {
            String path = st.nextToken();
            path = path.trim();
            if (log.isDebugEnabled()) {
                log.debug("reset file=" + path);
            }
            pathList.add(path);
        }
        if (digester == null) {
            if (digesterRules == null) {
                // ルールファイル未設定のとき、デフォルトのルールファイルを
                // 設定する。
                digesterRules = DIGESTER_RULES_PATH;
            }
            // Digesterの生成
            try {
                digester =
                    DigesterLoader.createDigester(
                        servlet.getServletContext().getResource(
                            digesterRules));
                            digester.setValidating(true);
            } catch (MalformedURLException e) {
                log.error("", e);
                throw new ServletException(e);
            } catch (XmlLoadException e) {
                log.error("", e);
                throw new ServletException(e);
            }
        }
        ResetterResources resetterResources = new ResetterResources();
        try {
            for (int i = 0; i < pathList.size(); i++) {
                digester.push(resetterResources);
        	    URL url = this.getClass().getResource(getDtdUrl());
        	    if (url != null) {
        	        digester.register(getPublicIdentifier(), url.toString());
        	    }
                digester.parse(
                    servlet.getServletContext().getResourceAsStream(
                    pathList.get(i)));
            }
        } catch (IOException e) {
            log.error("", e);
            throw new ServletException(e);
        } catch (SAXException e) {
            log.error("", e);
            throw new ServletException(e);
        }
        // ResetterResourcesをサーブレットコンテキストに追加
        servlet.getServletContext().setAttribute(
            ResetterResources.RESETTER_RESOURCES_KEY + config.getPrefix(),
            resetterResources);
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
     * Struts設定ファイル(struts-config.xml)
     * に設定されているリセッタの完全修飾名を
     * 設定する。
     * 
     * @param string
     * リセッタの完全修飾名
     */
    public void setResetter(String string) {
        resetter = string;
    }

    /**
     * フォームリセット定義ファイル(reset.xml)のパス名を設定する。
     * 
     * @param string
     * フォームリセット定義ファイル(reset.xml)のパス名
     */
    public void setResources(String string) {
        resourcesPath = string;
    }

    /**
     * フォームリセットルール定義ファイル(reset-rules.xml)を設定する。
     *
     * @param digesterRules フォームリセットルール定義ファイル(reset-rules.xml)
     * のパス名
     */
    public void setDigesterRules(String digesterRules) {
        this.digesterRules = digesterRules;
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
