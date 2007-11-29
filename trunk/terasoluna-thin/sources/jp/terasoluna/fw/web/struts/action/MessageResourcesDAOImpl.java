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
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import jp.terasoluna.fw.exception.SystemException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;

/**
 * MessageResourcesDAOの実装クラス。
 * 
 * DBMessageResourcesFactoryをメッセージリソースファクトリクラスとして
 * 用いる場合、DBMessageResourcesはMessageResourcesDAOを実装したクラスを
 * 使用する。詳細は{@link DBMessageResources}を参照のこと。
 *
 * <h5>使用方法</h5>
 * DBMessageResourcesDAOImplはSpringフレームワークを利用した実装である。
 * しかしStrutsフレームワークのアーキテクチャではDIコンテナを取得する術を
 * 持たないため、データソースの定義を専用に行う必要がある。<br>
 * DBアクセスに必要なデータソースの定義をdbMessageResources.xmlという名前の
 * Bean定義ファイルに下記のように定義し、クラスパス上に置き、以下のように書く。<br>
 * ※このBean定義ファイルはBLogicなどの設定に用いるものとは別に用意すること。<br>
 * <strong>JNDIからデータソースを取得する場合</strong><br>
 * <pre><code>
 * &lt;bean id="sampleDataSource" 
 *       class="org.springframework.jndi.JndiObjectFactoryBean" 
 *       abstract="false" scope="singleton" 
 *       lazy-init="default" 
 *       autowire="default" 
 *       dependency-check="default"&gt;
 *   &lt;property name="jndiName" 
 *             value="sampleJndiDataSource" /&gt; 
 * &lt;/bean&gt;
 * </code></pre>
 * 各要素の属性はシステムによって異なるので確認すること。<br>
 * <br>
 * <strong>JNDIを利用しない場合</strong><br>
 * <pre><code>
 * &lt;bean id="dataSource"
 *       class="org.springframework.jdbc.datasource.DriverManagerDataSource"&gt;
 *   &lt;property name="driverClassName"&gt;
 *     &lt;value&gt;oracle.jdbc.OracleDriver&lt;/value&gt;
 *   &lt;/property&gt;
 *   &lt;property name="url"&gt;
 *     &lt;value&gt;jdbc:oracle:thin:@192.0.34.166:1521:ORCL&lt;/value&gt;
 *   &lt;/property&gt;
 *   &lt;property name="username"&gt;&lt;value&gt;username&lt;/value&gt;&lt;/property&gt;
 *   &lt;property name="password"&gt;&lt;value&gt;password&lt;/value&gt;&lt;/property&gt;
 * &lt;/bean&gt;
 * </code></pre>
 * 上記の設定のうち、次の4つはシステムによって異なるので確認すること。
 * <ol>
 *   <li>ドライバクラス名（oracle.jdbc.OracleDriverの部分）</li>
 *   <li>URL（jdbc:oracle:thin:@192.0.34.166:1521:ORCLの部分）</li>
 *   <li>ユーザ名（usernameの部分）</li>
 *   <li>パスワード（passwordの部分）</li>
 * </ol>
 * 
 * なお、Webアプリケーションで使用する場合は基本的にJNDIを用いるほうを推奨する。
 * 
 */
public class MessageResourcesDAOImpl implements MessageResourcesDAO {

    /**
     * データソースの定義されているBean定義ファイル。
     */
    public static final String BEANS_XML
        = "dbMessageResources.xml";
    
    /**
     * データソースbeanを取得する際のid値。
     */
    private static final String DATASOURCE_BEAN_ID
        = "dataSource";
    
    /**
     * データソースの取得失敗を表すエラーコード。
     */
    private static final String MESSAGE_RESOURCES_DAO_IMPL_ERROR
        = "errors.message.resources.dao.impl";
    
    /**
     * ログクラス。
     */
    private static Log log 
        = LogFactory.getLog(MessageResourcesDAOImpl.class);
    
    /**
     * メッセージリソースを取得する。
     * 
     * 与えられたSQLの第一カラムをMapのキー、第二カラムをMapの値として
     * 設定したMapを返す。
     * SQLで返された結果セットの第一カラムに重複があった場合は、どれか1つしか
     * 登録されていないMapを返す。
     * 
     * @param sql メッセージリソースを取得するためのSQL
     * 
     * @return キーにメッセージキー、値にメッセージ文言の入ったMap
     */
    @SuppressWarnings("unchecked")
	public Map<String, String> queryMessageMap(String sql) {
        
        if (log.isDebugEnabled()) {
            log.debug("call queryMessageMap()");
        }

        // データソースのオブジェクトを生成するDIコンテナを生成
        ApplicationContext context = null;
        try {
            context = new ClassPathXmlApplicationContext(getBeansXml());
        } catch (BeansException e) {
            // ファイルがない、XML構文がinvalidである、など
            log.error(getBeansXml() + " not found or not " 
                      + "-//SPRING//DTD BEAN//EN or invalid or anything else.",
                      e);
            throw new SystemException(e, MESSAGE_RESOURCES_DAO_IMPL_ERROR);
        }
        
        // DIコンテナからデータソースを取得
        DataSource ds = null;

        try {
            ds = (DataSource) context.getBean(DATASOURCE_BEAN_ID);
        } catch (NoSuchBeanDefinitionException e) {
            log.error("\"dataSource\" not defined.", e);
          throw new SystemException(e, MESSAGE_RESOURCES_DAO_IMPL_ERROR);
        } catch (ClassCastException e) {
            log.error("\"dataSource\" not implemented DataSource.", e);
            throw new SystemException(e, MESSAGE_RESOURCES_DAO_IMPL_ERROR);
        } catch (BeansException e) {
            log.error("getBean() failed", e);
            throw new SystemException(e, MESSAGE_RESOURCES_DAO_IMPL_ERROR);
        }
        
        // O/Rマッピング
        DBMessageBeanMappingSqlQuery query
            = new DBMessageBeanMappingSqlQuery(ds, sql);
        
        try {
            query.compile();
        } catch (InvalidDataAccessApiUsageException e) {
            log.error("SQL is null or something wrong.", e);
            throw new SystemException(e, MESSAGE_RESOURCES_DAO_IMPL_ERROR);
        }
        
        List<DBMessageBean> dbMessageList = null;
        try {
            dbMessageList = query.execute();
        } catch (DataAccessException e) {
            log.error("Data access exception.", e);
            throw new SystemException(e, MESSAGE_RESOURCES_DAO_IMPL_ERROR);
        }
        
        if (log.isDebugEnabled()) {
            log.debug("db message acquired");
        }
        
        // メッセージリソースをMapに詰め替え
        Map<String, String> dbMessages 
            = new HashMap<String, String>(dbMessageList.size());
        Iterator<DBMessageBean> iterator = dbMessageList.iterator();
        DBMessageBean dbmb = null;
        while (iterator.hasNext()) {
            dbmb = iterator.next();
            if (log.isDebugEnabled()) {
                log.debug("Saving db message key [" + dbmb.getKey() + "], "
                          + "value [" + dbmb.getValue() + "]");
            }
            dbMessages.put(dbmb.getKey(), dbmb.getValue());
        }
        return dbMessages;
    }

    /**
     * データソースが定義されたBean定義ファイル名を取得する。
     * 
     * @return データソースが定義されたBean定義ファイル名
     */
    protected String getBeansXml() {
        return BEANS_XML;
    }

}
