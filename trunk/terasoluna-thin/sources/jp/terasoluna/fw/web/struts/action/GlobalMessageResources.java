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

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import jp.terasoluna.fw.util.PropertyUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.MessageResourcesFactory;

/**
 * 業務共通、システムのメッセージリソースを生成するクラス。
 *
 * <p>
 * Strutsの仕様として、メッセージリソース定義ファイルを利用する場合、
 * その定義はモジュールごとに独立しているため、すべてのモジュールに共通する
 * メッセージリソースを一元的に定義することができない。<br>
 * TERASOLUNAではモジュールすべてに共通な、
 * 業務共通メッセージや、システムメッセージを利用する方法を提供している。<br>
 * このクラスは、システム（フレームワーク）のメッセージリソースと、
 * 業務共通のメッセージリソースを保持し、TERASOLUNAフレームワークが提供する、
 * どのメッセージリソースクラスを用いても参照されるようになっている。<br>
 * </p>
 * <h5>制限事項</h5>
 * <ol>
 *  <li>TERASOLUNAフレームワークで提供するMessageResourcesは、
 *      全てこのクラスからシステム、業務共通のメッセージリソースを取得する
 *      必要がある。</li>
 *  <li>ここで取得されたメッセージリソースは国際化対応されない。</li>
 * </ol>
 * <h5>業務共通のメッセージリソース設定</h5>
 * <p>
 * 業務共通のメッセージリソースは、デフォルトで業務共通メッセージリソース定義
 * ファイルから取得する。
 * 業務共通メッセージリソース定義ファイルのデフォルトのファイル名は
 * application-messages.propertiesに設定されている。
 * ファイル名を変更する場合は、
 * システム設定プロパティファイル（system.properties）
 * に以下のキーで設定を行なう。<br>
 * <pre><code>
 * application.messages=sample1-messages
 * </code></pre>
 * application.messagesに対応する文字列は、.propertiesを除いたファイル名である。
 * <b>必ず.propertiesは除いて記述する。</b><br>
 * </p>
 * <h5>業務共通メッセージリソース定義ファイルの応用</h5>
 * 業務共通のメッセージは業務共通メッセージリソース定義ファイル
 * （application-messages.properties）
 * あるいは、システム設定プロパティファイル（system.properties）
 * で定義したファイル名のファイルの中で、
 * 下記のようにキー（add.message.file.x）を
 * 使用して外部ファイルを指定することにより、
 * メッセージリソースを追加することができる。<br>
 * <code><pre>
 * add.message.file.1=app1-message
 * add.message.file.2=app2-message
 * </pre></code>
 * プロパティキー末尾は1で始まる通番であり、途中で通番が途切れている場合は、
 * そこで外部ファイル読み込み終了となる。<br>
 * <h5>システムのメッセージリソース設定</h5>
 * GlobalMessageResourcesでは、
 * デフォルトでsystem-message.propertiesをロードし、
 * ここからシステムのメッセージを取得している。
 * このシステムメッセージリソース定義ファイル名は、変更できない。
 *
 * @see jp.terasoluna.fw.web.struts.action.DBMessageResourcesFactory
 * @see jp.terasoluna.fw.web.struts.action.DBMessageResources
 * @see jp.terasoluna.fw.web.struts.action.PropertyMessageResourcesExFactory
 * @see jp.terasoluna.fw.web.struts.action.PropertyMessageResourcesEx
 *
 */
public final class GlobalMessageResources extends MessageResources {

    /**
     * シリアルバージョンID
     */
    private static final long serialVersionUID = -1224092114113256826L;

    /**
     * ログクラス。
     */
    @SuppressWarnings("hiding")
    private static Log log
        = LogFactory.getLog(GlobalMessageResources.class);

    /**
     * フレームワークのメッセージが保持される。
     */
    private Map<String, String> fwMessages = new HashMap<String, String>();

    /**
     * システム一意となるメッセージを保持する。
     */
    private Map<String, String> globalMessages = new HashMap<String, String>();

    /**
     * フレームワークメッセージリソース名。
     */
    private static final String SYSTEM_MESSAGE
        = "system-messages";

    /**
     * デフォルトのアプリケーションメッセージリソース名。
     */
    private static final String DEFAULT_APPLICATION_MESSAGE
        = "application-messages";

    /**
     * <code>system.properties</code>に記述される業務共通
     * メッセージリソース名。
     */
    private static final String APPLICATION_CONFIG_KEY
        = "application.messages";

    /**
     * ルートのメッセージファイルに記述する、追加用外部メッセージファイル。
     */
    private static final String ADD_MESSAGES_FILE
        = "add.message.file.";

    /**
     * シングルトンオブジェクト。
     */
    private static GlobalMessageResources globalMessageResources
        = null;

    /**
     * このクラスのシングルトンインスタンスを返却する。
     *
     * @return GlobalMessageResources このクラスのシングルトンインスタンス
     */
    public static GlobalMessageResources getInstance() {
        if (globalMessageResources == null) {
            synchronized (GlobalMessageResources.class) {
                GlobalMessageResources createdResources
                    = new GlobalMessageResources(
                            null, SYSTEM_MESSAGE);
                globalMessageResources = createdResources;
            }
        }
        return globalMessageResources;
    }

    /**
     *  指定されたパラメータによって
     *  <code>GlobalMessageResources</code> を生成する。
     *
     * @param factory メッセージリソースファクトリ
     * @param config この <code>MessageResource</code> に対する設定パラメータ
     */
    private GlobalMessageResources(MessageResourcesFactory factory,
                                   String config) {
        super(factory, config);
        globalInit();
        applicationInit();
    }

    /**
     * プロパティファイルから、内部マップへの詰め替えを行なう。
     */
    private synchronized void globalInit() {

        // メッセージをクリア
        fwMessages.clear();

        // メッセージリソースファイルをロードする。
        Properties prop = PropertyUtil.loadProperties(this.config);
        if (prop == null) {
            // 処理を行なわず終了する。
            return;
        }
        Enumeration keyEnum = prop.propertyNames();
        while (keyEnum.hasMoreElements()) {
            Object keyObj = keyEnum.nextElement();
            Object value = prop.get(keyObj);
            if (log.isDebugEnabled()) {
                log.debug(
                    "Saving framework message key [" + keyObj + "]"
                    + "value [" + value + "]");
            }
            fwMessages.put((String) keyObj, (String) value);
        }
    }

    /**
     * 業務共通メッセージリソースファイルのロードと
     * メッセージリソースの取得を行なう。
     */
    private synchronized void applicationInit() {
        String appKey = PropertyUtil.getProperty(APPLICATION_CONFIG_KEY);
        if (appKey == null) {
            // キーが見つからない場合、デフォルトの
            // メッセージリソース名を設定する。
            appKey = DEFAULT_APPLICATION_MESSAGE;
        }

        // 業務共通のルートプロパティの取得
        Properties prop = PropertyUtil.loadProperties(appKey);
        if (prop == null) {
            // 処理を行なわず終了する。
            return;
        }
        // ルートの業務共通メッセージとして設定されているメッセージリソースを
        // Mapに登録する。
        Map<String, String> rootAppricationMap = getRootApplicationMap(prop);
        // 追加読み込みを行なうメッセージリソースを取得する。
        Map<String, String> addApplicationMap =
        	getAddApplicationMap(prop, appKey);
        // マップのマージ
        addApplicationMap.putAll(rootAppricationMap);
        globalMessages = addApplicationMap;
    }

    /**
     * 業務共通のルートメッセージファイル内に記述されたメッセージ一覧マップを
     * 返却する。存在しない場合も空のマップを返却する。
     *
     * @param prop ルートのプロパティファイル
     * @return ルートのプロパティファイルに記述されたメッセージ
     */
    private Map<String, String> getRootApplicationMap(Properties prop) {
        Map<String, String> rootApplicationMap = new HashMap<String, String>();
        // キー一覧取得
        Iterator it = prop.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            // キーのうち、add.message.file.で始まっているものは除外する。
            if (key.startsWith(ADD_MESSAGES_FILE)) {
                continue;
            }
            String value = prop.getProperty(key);
            if (log.isDebugEnabled()) {
                log.debug("Saving root-application message key [" + key + "]"
                          + "value [" + value + "]");
            }
            rootApplicationMap.put(key, value);
        }
        return rootApplicationMap;
    }

    /**
     * 業務共通のルートメッセージから、外部のメッセージリソースファイル一覧を
     * 取得し、メッセージを取得する。
     * 外部ファイルが存在しない場合も空のマップを返却する。
     * ルートのファイル名とロードするファイル名が一致している場合は、
     * 永久ループを回避する為、読み飛ばされる。
     *
     * @param prop ルートのプロパティファイル
     * @param rootProperty ルートのプロパティファイル名
     * @return 外部のメッセージマップ
     */
    private Map<String, String> getAddApplicationMap(Properties prop,
    		String rootProperty) {
        Map<String, String> addApplicationMap = new HashMap<String, String>();
        List<String> fileNameList = new ArrayList<String>();
        for (int i = 1; ; i++) {
            // 外部ファイル名取得
            String fileName = prop.getProperty(ADD_MESSAGES_FILE + i);
            if (fileName == null) {
                // ファイル名が取得できない場合は、終了
                break;
            } else if (fileName.equals(rootProperty)) {
                // ルートのファイル名と一致している為、終了
                // (永久ループ回避)
                break;
            }
            fileNameList.add(fileName);
        }

        // 取得されたファイル名から、順次外部ファイルをロードし、
        // マップに追加する。
        Iterator fileNameIt = fileNameList.iterator();
        while (fileNameIt.hasNext()) {
            String outerFileName = (String) fileNameIt.next();
            Properties outerProp = PropertyUtil.loadProperties(outerFileName);
            // 外部ファイル名が適切でないときは飛ばす
            if (outerProp == null) {
                if (log.isWarnEnabled()) {
                    log.warn("\"" + outerFileName + "\" is illegal.");
                }
                continue;
            }
            // 外部ファイルのキー一覧
            Iterator outerFileKeyIt = outerProp.keySet().iterator();
            while (outerFileKeyIt.hasNext()) {
                String outerMessageKey = (String) outerFileKeyIt.next();
                String outerValue = outerProp.getProperty(outerMessageKey);
                if (log.isDebugEnabled()) {
                    log.debug("Saving outer-file-application message key ["
                              + outerMessageKey + "]" + "value [" + outerValue
                              + "]");
                }
                addApplicationMap.put(outerMessageKey, outerValue);
            }
        }
        return addApplicationMap;
    }

    /**
     * メッセージを返却する。
     * メッセージ取得の優先順位は、
     * <ol>
     *   <li>業務共通のメッセージリソース</li>
     *   <li>システムのメッセージリソース</li>
     * </ol>
     * の順となる。
     *
     * @param locale ロケールオブジェクト
     * @param key メッセージリソースキー
     * @return メッセージ
     */
    @Override
    public String getMessage(Locale locale, String key) {
        if (key == null) {
            return null;
        }
        // 業務共通のメッセージを検索し、見つかったらリターンする。
        String globalMsg = globalMessages.get(key);
        if (globalMsg != null) {
            return globalMsg;
        }
        // フレームワークのメッセージを返却する。
        return fwMessages.get(key);
    }
}
