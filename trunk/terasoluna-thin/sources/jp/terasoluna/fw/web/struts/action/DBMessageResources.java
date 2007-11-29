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

package jp.terasoluna.fw.web.struts.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import java.util.Map;
import java.util.Properties;

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.fw.util.ClassLoadException;
import jp.terasoluna.fw.util.ClassUtil;
import jp.terasoluna.fw.util.PropertyUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.MessageResourcesFactory;
/**
 * メッセージリソースクラス。
 *
 * <p>
 * メッセージリソース機能とは、JSP内で表示するエラーメッセージなど、
 * 特定のキーに対してメッセージを取得する機能である。<br>
 * このクラスを使用することによって、メッセージリソース定義ファイル
 * （通常Strutsで使われるプロパティファイル形式のメッセージリソース）だけでなく、
 * クラスロード時にDBを参照し、DBからのメッセージリソースを使用することが
 * 可能である。
 * </p>
 * 
 * <h5>サンプルを用いた解説</h5>
 * 
 * <p>
 * DB内で設定されたメッセージリソースは、全モジュール内で
 * 共有されるが、メッセージリソース定義ファイルのメッセージリソースは、
 * Strutsの各モジュールごとに独立する。
 * 以下サンプルを用いて、このクラスを使用したときに、メッセージリソース機能が
 * どのように振舞うか解説する。
 * </p>
 * 
 * <h6>サンプル設定</h6>
 * <p>
 * 例として、モジュールA、モジュールBという複数のモジュールが存在し、
 * モジュール毎のメッセージリソースの設定（メッセージリソース定義ファイル）に
 * 下記のような指定があるとする。<br>
 * <br>
 * <table border="1">
 *   <caption>メッセージリソース一覧</caption>
 *   <tr>
 *     <td>モジュール名</td>
 *     <td>メッセージキー</td>
 *     <td>メッセージ文言</td>
 *     <td>メッセージの登録先</td>
 *   </tr>
 *   <tr>
 *     <td rowspan="2">モジュールA</td>
 *     <td>message.propMessageResource</td>
 *     <td>"moduleA"</td>
 *     <td>メッセージリソース定義ファイル</td>
 *   </tr>
 *   <tr>
 *     <td>message.dbMessageResource</td>
 *     <td>"DB"</td>
 *     <td>DB</td>
 *   </tr>
 *   <tr>
 *     <td rowspan="2">モジュールB</td>
 *     <td>message.propMessageResource</td>
 *     <td>"moduleB"</td>
 *     <td>メッセージリソース定義ファイル</td>
 *   </tr>
 *   <tr>
 *     <td>message.subMessageResource</td>
 *     <td>"subModule"</td>
 *     <td>メッセージリソース定義ファイル</td>
 *   </tr>
 * </table>
 * </p>
 * 
 * <h6>モジュール間のメッセージリソースの可視範囲</h6>
 * 上の表で、モジュールAのメッセージリソースの可視範囲は、
 * <ul>
 *   <li>モジュールA内で定義されたメッセージリソース定義ファイル内の
 *       <code>message.propMessageResource</code>
 *   </li>
 *   <li>DB内のメッセージリソースである<code>message.dbMessageResource</code>
 *   </li>
 * </ul>
 * である。<br>
 * モジュールBのメッセージリソースの可視範囲は、
 * <ul>
 *   <li>モジュールBで定義されたメッセージリソース定義ファイル内の
 *       <code>message.propMessageResource</code></li>
 *   <li>モジュールBで定義されたメッセージリソース定義ファイル内の
 *       <code>message.subMessageResource</code></li>
 *   <li>モジュールAのDB内のメッセージリソースである
 *       <code>message.dbMessageResource</code></li>
 * </ul>
 * である。<br>
 * モジュールAとモジュールBでは、同じメッセージリソースのキー
 * <code>message.propMessageResource</code>が見えているが、各モジュールで
 * 取得できるメッセージは、
 * <ul>
 *   <li>モジュールAは"moduleA"</li>
 *   <li>モジュールBは"moduleB"</li>
 * </ul>
 * となる。
 * メッセージリソース定義ファイルによるメッセージリソースの設定に関しては、
 * 各モジュール間で設定は共有できないことに注意する。<br>
 * それに対し、モジュール間で共有されるDBのメッセージリソースは、
 * <code>message.dbMessageResource</code>に対して取得できるメッセージは、
 * モジュールA、モジュールBともに "DB"である。<br>
 * また、Strutsの仕様として、モジュールBのメッセージリソース定義ファイルで
 * 設定されたメッセージリソースをモジュールAから取得することはできない。
 * （モジュールAから<code>module.subMessageResource</code>のキーで
 * 参照しても、メッセージは取得できない。）
 * <h6>注意点</h6>
 * このように、メッセージリソースの扱いについては下記の注意点がある。
 * <ul>
 *   <li>メッセージリソース定義ファイルに定義されたメッセージ文言を
 *       取得しようとした場合、同じメッセージキーを定義していても、
 *       設定されたモジュールが異なるならば、使用されるモジュールによって
 *       値が異なる</li>
 *   <li>DB内にメッセージリソースを設定した場合、他のモジュールでも
 *       参照が可能</li>
 *   <li>DB内のメッセージリソースとメッセージリソース定義ファイルの
 *       メッセージリソースでキーが同一の時、メッセージリソース定義ファイルの
 *       メッセージ文言が取得される</li>
 *   <li>DBMessageResourcesを用いた場合、国際化対応は一切されない。
 *       DBから取得するメッセージリソースだけでなく、メッセージリソース定義
 *       ファイルをロケールに応じて複数用意しても、意味はない。<br>
 *       国際化対応を行う必要がある場合、struts-config.xmlの
 *       &lt;message-resources&gt;要素のfactory属性で、Strutsが提供する
 *       PropertyMessageResourcesFactoryかTERASOLUNAの提供する
 *       PropertyMessageResourcesExFactoryを用いる必要がある。
 *       なお、その場合はDB内のメッセージリソースは取得できなくなる。</li>
 * </ul>
 * また、１つのキーに対して、DBMessageResourcesのメッセージ取得優先順位は、
 * 下記の通りとなる。<br>
 * <ol>
 *   <li>メッセージリソース定義ファイルで定義されたメッセージリソース</li>
 *   <li>DB内に定義されたメッセージリソース</li>
 *   <li>業務共通メッセージリソース定義ファイル
 *       （application-messages.properties）で
 *       定義されたメッセージリソース</li>
 *   <li>システムメッセージリソース定義ファイル
 *       （system-messages.properties）で
 *       定義されたメッセージリソース</li>
 * </ol>
 * 業務共通メッセージリソース・システムメッセージリソースに関する内容は、
 * {@link GlobalMessageResources}を参照のこと。
 * 
 * <h5>使用方法</h5>
 * このクラスを利用するには、Struts設定ファイル(struts-config.xml)中の
 * &lt;message-resource&gt;要素に、
 * <ul>
 *   <li>parameter属性にメッセージリソース定義ファイル名から
 *       拡張子(<code>.properties</code>)を取り除いたもの</li>
 *   <li>factory属性に、<code>DBMessageResourcesFactory</code></li>
 * </ul>
 * を指定する。下記はStruts設定ファイル(struts-config.xml)の設定例である。<br>
 * <code><pre>
 * &lt;struts-config&gt;
 *   …
 *   &lt;message-resources parameter="MessageResources"
 *                      factory="jp.terasoluna.fw.web.struts.action.DBMessageResourcesFactory"
 *   /&gt;
 *   …
 * &lt;/struts-config&gt;
 * </pre></code>
 * 
 * <h5>DB内のメッセージリソースを取得する設定例</h5>
 * DBからメッセージリソースを取得するために、システム設定プロパティファイル
 * （system.properties）で、下記のように設定する。
 * <pre><code>
 * messages.sql=&lt;SQL(SELECT)文&gt;
 * messages.dao=MessageResourcesDAOの実装クラス
 * </code></pre>
 *
 *
 * 具体例は以下のようになる。
 * <pre><code>
 * messages.sql=SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGES
 * messages.dao=jp.terasoluna.fw.web.struts.action.MessageResourcesDAOImpl
 * </code></pre>
 * 
 * <h6>設定上の注意点</h6>
 * <ul>
 *  <li>設定するSQLは、第１カラム（上記設定例ではMESSAGE_KEY）にメッセージキーが
 *      設定され、第２カラム（MESSAGE_VALUE）にメッセージ文言が格納される
 *      結果セットを返すものでなければならない。</li>
 *  <li>設定するDAOは、MessageResourcesDAOを実装したクラスでなければならず、
 *      かつ引数なしのコンストラクタを持つクラスでなければならない。
 *      TERASOLUNAが提供するMessageResourcesDAOImplは同インタフェースを実装
 *      し、かつ引数なしのコンストラクタを実装している。
 *      <strong>{@link MessageResourcesDAOImpl}の使用法については
 *      同クラスのJavadocを参照のこと。</strong></li>
 * </ul>
 * 
 * <h5>メッセージリソース定義ファイル（プロパティファイル）の
 *     メッセージリソースの設定例</h5>
 * プロパティファイルを用いたメッセージリソースの定義は、プロパティファイルの
 * 定義どおりに登録される。
 * <pre><code>
 * &lt;メッセージキー&gt;=&lt;メッセージ文言&gt;
 * </code></pre>
 * 具体例は以下のようになる。
 * <pre><code>
 * errors.requiredArray={0}番目の{1}は必須入力です。
 * errors.alphaNumericStringArray={0}番目の{1}は半角英数字でなくてはなりません。
 * </code></pre>
 *
 * @see jp.terasoluna.fw.web.struts.action.GlobalMessageResources
 * @see jp.terasoluna.fw.web.struts.action.DBMessageResourcesFactory
 * @see jp.terasoluna.fw.web.struts.action.MessageResourcesDAO
 * @see jp.terasoluna.fw.web.struts.action.MessageResourcesDAOImpl
 *
 */
public class DBMessageResources extends MessageResources {
    
    /**
	 * シリアルバージョンID
	 */
	private static final long serialVersionUID = 8244415315747028752L;

	/**
     * システム設定プロパティファイル（system.properties）内で
     * DAOを取得する際に使用するキー。
     */
    public static final String MESSAGES_DAO = "messages.dao";
    
    /**
     * システム設定プロパティファイル（system.properties）内で
     * SQLを取得する際に使用するキー。
     */
    public static final String MESSAGES_SQL = "messages.sql";   
 
    /**
     *  メッセージの取得失敗を表すエラーコード。
     */
    private static final String DB_MESSAGE_RESOURCES_ERROR
        = "errors.db.message.resources";
    
    /**
     *  メッセージリソースの初期化失敗を表すエラーコード。
     */
    private static final String DB_MESSAGE_RESOURCES_ERROR_INIT
        = "errors.db.message.resources.init";
    
    /**
     * ログクラス。
     */ 
    @SuppressWarnings("hiding")
	private static Log log = LogFactory.getLog(DBMessageResources.class);

    /**
     * DBから取得したメッセージキーとメッセージ文言を格納するMap。
     * クラスで共有化される。
     */
    private static Map dbMessages = null;

    /**
     * メッセージリソース定義ファイルから取得したメッセージキーと
     * メッセージ文言を格納するMap。
     * 
     * DBのメッセージリソースとは異なり、Strutsのモジュール単位で
     * 独立させることができる。
     */
    private Map<String, String> messages = new HashMap<String, String>();

    /**
     * 指定されたパラメータによってDBMessageResourcesを生成する。
     *
     * @param factory メッセージリソースファクトリ
     * @param config メッセージリソース定義ファイル名
     */
    public DBMessageResources(MessageResourcesFactory factory,
                              String config) {
        super(factory, config);
        if (log.isDebugEnabled()) {
            log.debug("call DBMessageResources()");
        }
        // DBのメッセージリソースが未設定の時、DBメッセージリソースを取得する。
        if (dbMessages == null) {
            dbInit();
        }
        propertyInit(config);
    }

    /**
     * 指定されたパラメータによってDBMessageResourcesを生成する。
     * 
     * @param factory メッセージリソースファクトリ
     * @param config メッセージリソース定義ファイル名
     * @param returnNull <code>org.apache.struts.util.MessageResources</code>
     *                   クラスの <code>returnNull</code>
     *                   <code>false</code> 指定時、キーに該当するメッセージが
     *                   存在しない場合???Locale.key???という形式でメッセージを
     *                   返却する。
     */
    public DBMessageResources(MessageResourcesFactory factory,
                              String config, boolean returnNull) {
        super(factory, config, returnNull);
        if (log.isDebugEnabled()) {
            log.debug("call DBMessageResources()");
        }
        // DBのメッセージリソースが未設定の時、DBメッセージリソースを取得する。
        if (dbMessages == null) {
            dbInit();
        }
        propertyInit(config);
    }
    
    /**
     * DB内のメッセージキーとメッセージ文言のペアを取得する。
     */
    protected static void dbInit() {
        
        if (log.isDebugEnabled()) {
            log.debug("call dbInit()");
        }
        
        // 一度呼ばれたことが分かるようにdbMessagesに空のMapを設定
        dbMessages = new HashMap();
        
        // システム設定プロパティファイル（system.properties）からプロパティを
        // 取得する
        String daoName = PropertyUtil.getProperty(MESSAGES_DAO);
        String sql = PropertyUtil.getProperty(MESSAGES_SQL);
        if (daoName == null && sql == null) {
            // どちらのキーも定義されていなければ終了する
            return;
        } else if (daoName == null || sql == null) {
            // 一方しか定義されていない場合は警告ログを出して、終了する
            if (log.isWarnEnabled()) {
                log.warn("defined only one of the pair - " + MESSAGES_DAO
                         + " and " + MESSAGES_SQL + ".");
            }
            return;
        }
        
        // DAOのインスタンスを生成
        MessageResourcesDAO dao = null;
        try {
            dao = (MessageResourcesDAO) ClassUtil.create(daoName); 
        } catch (ClassLoadException e) {
            // 定義されているクラスがロードできなかった場合
            log.error("\"" + daoName + "\" cannot loaded.", e);
            throw new SystemException(e, DB_MESSAGE_RESOURCES_ERROR_INIT);
        } catch (ClassCastException e) {
            // 定義されているクラスがMessageResourcesDAOを実装していない場合
            log.error("\"" + daoName + "\" not implemented"
                      + " MessageResourcesDAO", e);
            throw new SystemException(e, DB_MESSAGE_RESOURCES_ERROR_INIT);
        }
        
        // Messageの詰まったMapを取得。
        dbMessages = dao.queryMessageMap(sql);

    }
    
    /**
     *  メッセージリソース定義ファイルからメッセージキーとメッセージ文言の
     *  ペアを取得する。
     *
     * @param propertyFile メッセージリソース定義ファイル名
     */
    protected void propertyInit(String propertyFile) {
        Properties props = null;
        Iterator names = null;
        
        if (log.isDebugEnabled()) {
            log.debug("call propertyInit()");
        }
        
        // メッセージリソース定義ファイルのロード
        props = PropertyUtil.loadProperties(propertyFile);
        if (props == null) {
            log.error(
                "Message resources file \"" + propertyFile + "\" is illegal.");
            return;
        }
        // ハッシュマップへの詰め替えを行う
        names = props.keySet().iterator();
        while (names.hasNext()) {
            String key = (String) names.next();
            if (log.isDebugEnabled()) {
                log.debug(
                    "Saving property message key [" + key + "]"
                    + "value [" + props.getProperty(key) + "]");
            }
            messages.put(key, props.getProperty(key));
        }
    }



    /**
     * 指定されたキーにもとづきメッセージ文言を取得する。
     * 同じキーが異なるメッセージの定義場所に存在する時、取得される優先順位は
     * 下記のようになる。
     * <ol>
     *  <li>メッセージリソース定義ファイルのメッセージリソース</li>
     *  <li>DB内のメッセージキーとメッセージ文言の入っているメッセージリソース
     *      </li>
     *  <li>業務共通メッセージリソースファイル定義ファイル
     *      （application-messages.properties）のメッセージリソース</li>
     *  <li>システムメッセージリソース定義ファイル（system-messages.properties）
     *      のメッセージリソース</li>
     * </ol>
     * <p>
     *  すべてのメッセージリソースに初期化処理が行われなかった場合、
     *  あるいは、どの定義からもメッセージキーに該当する値が
     *  取得できなかった場合、生成時のreturnNull指定によって、
     *  nullか、あるいはStrutsの形式（???Locale.key???）で返却される。
     * </p>
     * <p>
     *  なお、ここで指定されているロケールは考慮されない。
     *  すなわちgetMessage(key)と同じ動作をする。
     * </p>
     *
     * @param locale メッセージロケール。考慮されない
     * @param key メッセージキー
     * 
     * @return localeとkeyに対応するメッセージ文言
     */
    @Override
    public String getMessage(Locale locale, String key) {
        MessageResources globalMessageResources = null;
        
        if (log.isDebugEnabled()) {
            log.debug("call getMessage(Locale, String)");
        }
        
        if (key == null || "".equals(key)) {
            log.error("Message key 'null' or empty not allowed.");
            throw new SystemException(
                null, DB_MESSAGE_RESOURCES_ERROR);
        }
        
        // メッセージリソース定義ファイルからのメッセージ文言取得
        if (messages != null) {
            String retMessage = messages.get(key);
            if (retMessage != null) {
                return retMessage;
            }
        }

        // DBからのメッセージ文言取得
        if (dbMessages != null) {
            String retMessage = (String) dbMessages.get(key);
            if (retMessage != null) {
                return retMessage;
            }
        }

        // 業務共通・システムからのメッセージ文言取得
        globalMessageResources = GlobalMessageResources.getInstance();
        String retMessage = globalMessageResources.getMessage(locale, key);
        if (retMessage != null) {
            return retMessage;
        }
        
        // どこにもない場合はreturnNullの設定の有無に沿って返すものを変える
        if (!returnNull) {
            return "???" + messageKey(locale, key) + "???";
        }
        return null;        
    }

    /**
     * 指定されたキーにもとづきメッセージ文言を取得する。
     * 同じキーが異なるメッセージの定義場所に存在する時、取得される優先順位は
     * 下記のようになる。
     * <ol>
     *  <li>メッセージリソース定義ファイル</li>
     *  <li>DB内のメッセージキーとメッセージ文言の入っているテーブル</li>
     *  <li>業務共通メッセージリソースファイル定義ファイル
     *      （application-messages.properties）のメッセージ</li>
     *  <li>システムメッセージリソース定義ファイル（system-messages.properties）
     *      のメッセージ</li>
     * </ol>
     *  すべてのメッセージリソースに初期化処理が行われなかった場合、
     *  あるいは、どの定義からもメッセージキーに該当する値が
     *  取得できなかった場合、nullが返却される。
     *
     * @param key メッセージキー
     * 
     * @return keyに対応するメッセージ文言
     */
    @Override
    public String getMessage(String key) {
        Locale locale = null;
        return getMessage(locale , key);
    }
    
}
