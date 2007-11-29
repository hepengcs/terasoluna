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

import java.util.Map;

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;

/**
 * {@link jp.terasoluna.fw.web.struts.action.MessageResourcesDAOImpl}
 * クラスのブラックボックステスト。
 *
 * <p>
 * <h4>【クラスの概要】</h4>
 * MessageResourcesDAOの実装クラス。
 * ※このクラスのテストを行う際にはMessageResourcesDAOImpl.sqlを用いて
 * DBにテーブルを作成しておく必要がある。
 * ※テストに際して、UTUtilを用いてデータベースを操作するため、
 * JDBCドライバをクラスパスに追加し、
 * utlib.confにDB接続用にアカウントとパスワードおよびIPアドレスの設定をすること。
 * ※同様に、データソースの設定を独自に行う必要があるため、
 * jp/terasoluna/fw/web/struts/action
 * /MessageResourcesDAOImpl_dbMessageResources04.xml
 * に対してもアカウントとパスワード、IPアドレスの設定を行うこと。
 * <p>
 *
 * @see jp.terasoluna.fw.web.struts.action.MessageResourcesDAOImpl
 */
public class MessageResourcesDAOImplTest extends TestCase {




    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     *
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(MessageResourcesDAOImplTest.class);
    }

    /**
     * 初期化を行う。
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        UTUtil.deleteAll("MESSAGE");
    }

    /**
     * 後処理を行う。
     * データベースを初期化する。
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        UTUtil.deleteAll("MESSAGE");
    }

    /**
     * testQueryMessageMap01()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) sql:"SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE"<br>
     *         (状態) dbMessageResources.xml:"aaaaa"<br>
     *                （存在しないファイル名）<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:SystemException：<br>
     *                    エラーコード："errors.message.resources.dao.impl"<br>
     *                    ラップした例外：BeansException<br>
     *         (状態変化) ログ:【エラーログ】<br>
     *                    ＜メッセージ＞<br>
     *                    "aaaaa not found or not -//SPRING//DTD BEAN//EN or invalid or anything else."<br>
     *                    ＜例外＞<br>
     *                    BeansException<br>
     *
     * <br>
     * Bean定義ファイルがなかった場合、例外をスローすることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testQueryMessageMap01() throws Exception {
        // 前処理
        MessageResourcesDAOImpl_MessageResourcesDAOImplStub01 dao
            = new MessageResourcesDAOImpl_MessageResourcesDAOImplStub01();
        dao.setTestBeansXmlName("aaaaa");

        // テスト実施
        try {
            dao.queryMessageMap("SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE");
            fail();
        } catch (SystemException e) {
            // 判定
            assertEquals("errors.message.resources.dao.impl",
                         e.getErrorCode());
            assertTrue(e.getCause() instanceof BeansException);
            assertTrue(LogUTUtil.checkError(
                       "jp/terasoluna/fw/web/struts/action/aaaaa not found or not -//SPRING//DTD BEAN//EN or invalid or anything else.",
                       e.getCause()));
        }

    }

    /**
     * testQueryMessageMap02()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) sql:"SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE"<br>
     *         (状態) dbMessageResources.xml:"MessageResourcesDAOImple_dbMessageResources01.xml"<br>
     *                （id属性が"dataSource"の<bean>要素がない）<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:SystemException：<br>
     *                    エラーコード："errors.message.resources.dao.impl"<br>
     *                    ラップした例外：NoSuchBeanDefinitionException<br>
     *         (状態変化) ログ:【エラーログ】<br>
     *                    ＜メッセージ＞<br>
     *                    ""dataSource" not defined."<br>
     *                    ＜例外＞<br>
     *                    NoSuchBeanDefinitionException<br>
     *
     * <br>
     * Bean定義ファイルにid属性がdataSourceのbean要素がなかった場合、例外をスローすることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testQueryMessageMap02() throws Exception {
        // 前処理
        MessageResourcesDAOImpl_MessageResourcesDAOImplStub01 dao
            = new MessageResourcesDAOImpl_MessageResourcesDAOImplStub01();
        dao.setTestBeansXmlName("MessageResourcesDAOImpl_dbMessageResources01.xml");

        // テスト実施
        try {
            dao.queryMessageMap(
                    "SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE");
            fail();
        } catch (SystemException e) {
            // 判定
            assertEquals("errors.message.resources.dao.impl", e.getErrorCode());
            assertEquals(NoSuchBeanDefinitionException.class.getName(),
                         e.getCause().getClass().getName());
            assertTrue(LogUTUtil.checkError(
                       "\"dataSource\" not defined.",
                       e.getCause()));
        }
    }

    /**
     * testQueryMessageMap03()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) sql:"SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE"<br>
     *         (状態) dbMessageResources.xml:"MessageResourcesDAOImple_dbMessageResources02.xml"<br>
     *                【ファイル内に定義されたdataSource】<br>
     *                <bean id="dataSource"<br>
     *                        class="java.lang.Object"><br>
     *                  …<br>
     *                </bean><br>
     *
     * <br>
     * 期待値：(状態変化) 例外:SystemException：<br>
     *                    エラーコード："errors.message.resources.dao.impl"<br>
     *                    ラップした例外：ClassCastException<br>
     *         (状態変化) ログ:【エラーログ】<br>
     *                    ＜メッセージ＞<br>
     *                    ""dataSource" not implemented DataSource."<br>
     *                    ＜例外＞<br>
     *                    ClassCastException<br>
     *
     * <br>
     * Bean定義ファイルにid属性がdataSourceだが、class属性がDataSourceの
     * 実装クラスでなかった場合、例外をスローすることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testQueryMessageMap03() throws Exception {
        // 前処理
        MessageResourcesDAOImpl_MessageResourcesDAOImplStub01 dao
            = new MessageResourcesDAOImpl_MessageResourcesDAOImplStub01();
        dao.setTestBeansXmlName("MessageResourcesDAOImpl_dbMessageResources02.xml");

        // テスト実施
        try {
            dao.queryMessageMap("SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE");
            fail();
        } catch (SystemException e) {
            // 判定
            assertEquals("errors.message.resources.dao.impl", e.getErrorCode());
            assertEquals(ClassCastException.class.getName(),
                         e.getCause().getClass().getName());
            assertTrue(LogUTUtil.checkError(
                       "\"dataSource\" not implemented DataSource.",
                       e.getCause()));
        }
    }

    /**
     * testQueryMessageMap04()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) sql:"SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE"<br>
     *         (状態) dbMessageResources.xml:"MessageResourcesDAOImple_dbMessageResources03.xml"<br>
     *                【ファイル内に定義されたdataSource】<br>
     *                <bean id="dataSource"<br>
     *                        class="org.springframework.jdbc.datasource.DriverManagerDataSource"
     *                        abstract="true"><br>
     *                  …<br>
     *                </bean><br>
     *
     * <br>
     * 期待値：(状態変化) 例外:SystemException：<br>
     *                    エラーコード："errors.message.resources.dao.impl"<br>
     *                    ラップした例外：BeansException<br>
     *         (状態変化) ログ:【エラーログ】<br>
     *                    ＜メッセージ＞<br>
     *                    "getBean() failed"<br>
     *                    ＜例外＞<br>
     *                    BeansException<br>
     *
     * <br>
     * Bean定義ファイルにid属性がdataSourceだが、abstract属性がtrueの場合、
     * 例外をスローすることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testQueryMessageMap04() throws Exception {
        // 前処理
        MessageResourcesDAOImpl_MessageResourcesDAOImplStub01 dao
            = new MessageResourcesDAOImpl_MessageResourcesDAOImplStub01();
        dao.setTestBeansXmlName("MessageResourcesDAOImpl_dbMessageResources03.xml");

        // テスト実施
        try {
            dao.queryMessageMap("SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE");
            fail();
        } catch (SystemException e) {
            // 判定
            assertEquals("errors.message.resources.dao.impl", e.getErrorCode());
            assertTrue(e.getCause() instanceof BeansException);
            assertTrue(LogUTUtil.checkError(
                       "getBean() failed",
                       e.getCause()));
        }
    }

    /**
     * testQueryMessageMap05()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) sql:null<br>
     *         (状態) dbMessageResources.xml:"MessageResourcesDAOImple_dbMessageResources04.xml"<br>
     *                【ファイル内に定義されたdataSource】<br>
     *                <bean id="dataSource"<br>
     *                        class="org.springframework.jdbc.datasource.DriverManagerDataSource"><br>
     *                  …<br>
     *                </bean><br>
     *
     * <br>
     * 期待値：(状態変化) 例外:SystemException：<br>
     *                    エラーコード："errors.message.resources.dao.impl"<br>
     *                    ラップした例外：InvalidDataAccessApiUsageException<br>
     *         (状態変化) ログ:【エラーログ】<br>
     *                    ＜メッセージ＞<br>
     *                    "SQL is null or something wrong."<br>
     *                    ＜例外＞<br>
     *                    InvalidDataAccessApiUsageException<br>
     *
     * <br>
     * sqlがnullの場合、例外がスローされることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testQueryMessageMap05() throws Exception {
        // 前処理
        MessageResourcesDAOImpl_MessageResourcesDAOImplStub01 dao
            = new MessageResourcesDAOImpl_MessageResourcesDAOImplStub01();
        dao.setTestBeansXmlName("MessageResourcesDAOImpl_dbMessageResources04.xml");

        // テスト実施
        try {
            dao.queryMessageMap(null);
            fail();
        } catch (SystemException e) {
            // 判定
            assertEquals("errors.message.resources.dao.impl", e.getErrorCode());
            assertEquals(InvalidDataAccessApiUsageException.class.getName(),
                         e.getCause().getClass().getName());
            assertTrue(LogUTUtil.checkError(
                       "SQL is null or something wrong.",
                       e.getCause()));
        }
    }

    /**
     * testQueryMessageMap06()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) sql:""<br>
     *         (状態) dbMessageResources.xml:"MessageResourcesDAOImple_dbMessageResources04.xml"<br>
     *                【ファイル内に定義されたdataSource】<br>
     *                <bean id="dataSource"<br>
     *                        class="org.springframework.jdbc.datasource.DriverManagerDataSource"><br>
     *                  …<br>
     *                </bean><br>
     *
     * <br>
     * 期待値：(状態変化) 例外:SystemException：<br>
     *                    エラーコード："errors.message.resources.dao.impl"<br>
     *                    ラップした例外：DataAccessException<br>
     *         (状態変化) ログ:【エラーログ】<br>
     *                    ＜メッセージ＞<br>
     *                    "Data access exception."<br>
     *                    ＜例外＞<br>
     *                    DataAccessException<br>
     *
     * <br>
     * sqlが空文字列の場合、例外がスローされることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testQueryMessageMap06() throws Exception {
        // 前処理
        MessageResourcesDAOImpl_MessageResourcesDAOImplStub01 dao
            = new MessageResourcesDAOImpl_MessageResourcesDAOImplStub01();
        dao.setTestBeansXmlName("MessageResourcesDAOImpl_dbMessageResources04.xml");

        // テスト実施
        try {
            dao.queryMessageMap("");
            fail();
        } catch (SystemException e) {
            // 判定
            assertEquals("errors.message.resources.dao.impl", e.getErrorCode());
            assertTrue(e.getCause() instanceof DataAccessException);
            assertTrue(LogUTUtil.checkError(
                       "Data access exception.",
                       e.getCause()));
        }
    }

    /**
     * testQueryMessageMap07()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) sql:"aaaaa"<br>
     *         (状態) dbMessageResources.xml:"MessageResourcesDAOImple_dbMessageResources04.xml"<br>
     *                【ファイル内に定義されたdataSource】<br>
     *                <bean id="dataSource"<br>
     *                        class="org.springframework.jdbc.datasource.DriverManagerDataSource"><br>
     *                  …<br>
     *                </bean><br>
     *
     * <br>
     * 期待値：(状態変化) 例外:SystemException：<br>
     *                    エラーコード："errors.message.resources.dao.impl"<br>
     *                    ラップした例外：DataAccessException<br>
     *         (状態変化) ログ:【エラーログ】<br>
     *                    ＜メッセージ＞<br>
     *                    "Data access exception."<br>
     *                    ＜例外＞<br>
     *                    DataAccessException<br>
     *
     * <br>
     * sqlが無効の場合、例外がスローされることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testQueryMessageMap07() throws Exception {
        // 前処理
        MessageResourcesDAOImpl_MessageResourcesDAOImplStub01 dao
            = new MessageResourcesDAOImpl_MessageResourcesDAOImplStub01();
        dao.setTestBeansXmlName("MessageResourcesDAOImpl_dbMessageResources04.xml");

        // テスト実施
        try {
            dao.queryMessageMap("aaaaa");
            fail();
        } catch (SystemException e) {
            // 判定
            assertEquals("errors.message.resources.dao.impl", e.getErrorCode());
            assertTrue(e.getCause() instanceof DataAccessException);
            assertTrue(LogUTUtil.checkError(
                       "Data access exception.",
                       e.getCause()));
        }
    }

    /**
     * testQueryMessageMap08()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) sql:"SELECT MESSAGE_KEY, MESSAGE_VALUE FROM AAAAA"<br>
     *         (状態) dbMessageResources.xml:"MessageResourcesDAOImple_dbMessageResources04.xml"<br>
     *                【ファイル内に定義されたdataSource】<br>
     *                <bean id="dataSource"<br>
     *                        class="org.springframework.jdbc.datasource.DriverManagerDataSource"><br>
     *                  …<br>
     *                </bean><br>
     *         (状態) MESSAGE_KEY:MESSAGEテーブルが存在しない<br>
     *         (状態) MESSAGE_VALUE:MESSAGEテーブルが存在しない<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:SystemException：<br>
     *                    エラーコード："errors.message.resources.dao.impl"<br>
     *                    ラップした例外：DataAccessException<br>
     *         (状態変化) ログ:【エラーログ】<br>
     *                    ＜メッセージ＞<br>
     *                    "Data access exception."<br>
     *                    ＜例外＞<br>
     *                    DataAccessException<br>
     *
     * <br>
     * 検索対象のテーブルが存在しない場合、例外がスローされることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testQueryMessageMap08() throws Exception {
        // 前処理
        MessageResourcesDAOImpl_MessageResourcesDAOImplStub01 dao
            = new MessageResourcesDAOImpl_MessageResourcesDAOImplStub01();
        dao.setTestBeansXmlName("MessageResourcesDAOImpl_dbMessageResources04.xml");

        // テスト実施
        try {
            dao.queryMessageMap("SELECT MESSAGE_KEY, MESSAGE_VALUE FROM AAAAA");
            fail();
        } catch (SystemException e) {
            // 判定
            assertEquals("errors.message.resources.dao.impl", e.getErrorCode());
            assertTrue(e.getCause() instanceof DataAccessException);
            assertTrue(LogUTUtil.checkError(
                       "Data access exception.",
                       e.getCause()));
        }
    }

    /**
     * testQueryMessageMap09()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) sql:"UPDATE MESSAGE SET MESSAGE_VALUE = '更新されたメッセージ'"<br>
     *         (状態) dbMessageResources.xml:"MessageResourcesDAOImple_dbMessageResources04.xml"<br>
     *                【ファイル内に定義されたdataSource】<br>
     *                <bean id="dataSource"<br>
     *                        class="org.springframework.jdbc.datasource.DriverManagerDataSource"><br>
     *                  …<br>
     *                </bean><br>
     *         (状態) MESSAGE_KEY:"TEST01"<br>
     *         (状態) MESSAGE_VALUE:"テストめっせーじ０１"<br>
     *
     * <br>
     * 期待値：(状態変化) 例外:SystemException：<br>
     *                    エラーコード："errors.message.resources.dao.impl"<br>
     *                    ラップした例外：DataAccessException<br>
     *         (状態変化) ログ:【エラーログ】<br>
     *                    ＜メッセージ＞<br>
     *                    "Data access exception."<br>
     *                    ＜例外＞<br>
     *                    DataAccessException<br>
     *
     * <br>
     * sqlが更新系の場合、例外がスローされることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testQueryMessageMap09() throws Exception {
        // 前処理
        MessageResourcesDAOImpl_MessageResourcesDAOImplStub01 dao
            = new MessageResourcesDAOImpl_MessageResourcesDAOImplStub01();
        dao.setTestBeansXmlName("MessageResourcesDAOImpl_dbMessageResources04.xml");

        String[][] data = {
            {"MESSAGE_KEY", "MESSAGE_VALUE"},
            {"TEST01", "テストめっせーじ０１"}
        };
        UTUtil.setData("MESSAGE", data);
        // テスト実施
        try {
            dao.queryMessageMap(
                    "UPDATE MESSAGE SET MESSAGE_VALUE = '更新されたメッセージ'");
            fail();
        } catch (SystemException e) {
            // 判定
            assertEquals("errors.message.resources.dao.impl", e.getErrorCode());
            assertTrue(e.getCause() instanceof DataAccessException);
            assertTrue(LogUTUtil.checkError(
                       "Data access exception.",
                       e.getCause()));
        }
    }

    /**
     * testQueryMessageMap10()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) sql:"SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE"<br>
     *         (状態) dbMessageResources.xml:"MessageResourcesDAOImple_dbMessageResources04.xml"<br>
     *                【ファイル内に定義されたdataSource】<br>
     *                <bean id="dataSource"<br>
     *                        class="org.springframework.jdbc.datasource.DriverManagerDataSource"><br>
     *                  …<br>
     *                </bean><br>
     *         (状態) MESSAGE_KEY:なし<br>
     *         (状態) MESSAGE_VALUE:なし<br>
     *         (状態) count:0<br>
     *
     * <br>
     * 期待値：(戻り値) Map<String, String>:空のMap<br>
     *
     * <br>
     * DBのメッセージリソースが0件の場合、空のMapが返されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testQueryMessageMap10() throws Exception {
        // 前処理
        MessageResourcesDAOImpl_MessageResourcesDAOImplStub01 dao
            = new MessageResourcesDAOImpl_MessageResourcesDAOImplStub01();
        dao.setTestBeansXmlName("MessageResourcesDAOImpl_dbMessageResources04.xml");

        // テスト実施
        Map<String, String> map = dao.queryMessageMap(
                "SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE");

        // 判定
        assertTrue(map.isEmpty());

    }

    /**
     * testQueryMessageMap11()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) sql:"SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE"<br>
     *         (状態) dbMessageResources.xml:"MessageResourcesDAOImple_dbMessageResources04.xml"<br>
     *                【ファイル内に定義されたdataSource】<br>
     *                <bean id="dataSource"<br>
     *                        class="org.springframework.jdbc.datasource.DriverManagerDataSource"><br>
     *                  …<br>
     *                </bean><br>
     *         (状態) MESSAGE_KEY:"TEST01"<br>
     *         (状態) MESSAGE_VALUE:"テストめっせーじ０１"<br>
     *         (状態) count:1<br>
     *
     * <br>
     * 期待値：(戻り値) Map<String, String>:["TEST01"->"テストめっせーじ０１"]<br>
     *
     * <br>
     * DBのメッセージリソースが1件の場合、1件入っているMapが返されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testQueryMessageMap11() throws Exception {
        // 前処理
        MessageResourcesDAOImpl_MessageResourcesDAOImplStub01 dao
            = new MessageResourcesDAOImpl_MessageResourcesDAOImplStub01();
        dao.setTestBeansXmlName("MessageResourcesDAOImpl_dbMessageResources04.xml");
        String[][] data = {
            {"MESSAGE_KEY", "MESSAGE_VALUE"},
            {"TEST01", "テストめっせーじ０１"}
        };
        UTUtil.setData("MESSAGE", data);
        // テスト実施
        Map<String, String> map = dao.queryMessageMap(
                "SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE");

        // 判定
        assertEquals(1, map.size());
        assertEquals("テストめっせーじ０１", map.get("TEST01"));
    }

    /**
     * testQueryMessageMap12()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) sql:"SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE"<br>
     *         (状態) dbMessageResources.xml:"MessageResourcesDAOImple_dbMessageResources04.xml"<br>
     *                【ファイル内に定義されたdataSource】<br>
     *                <bean id="dataSource"<br>
     *                        class="org.springframework.jdbc.datasource.DriverManagerDataSource"><br>
     *                  …<br>
     *                </bean><br>
     *         (状態) MESSAGE_KEY:"TEST01"<br>
     *                "TEST02"<br>
     *                "TEST03"<br>
     *         (状態) MESSAGE_VALUE:"テストめっせーじ０１"<br>
     *                "testMESSAGE02"<br>
     *                "試験的文零三"<br>
     *         (状態) count:3<br>
     *
     * <br>
     * 期待値：(戻り値) Map<String, String>:["TEST01"->"テストめっせーじ０１"]<br>
     *                  ["TEST02"->"testMessage02"]<br>
     *                  ["TEST03"->"試験的文零三"]<br>
     *
     * <br>
     * DBのメッセージリソースが3件の場合、3件入っているMapが返されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testQueryMessageMap12() throws Exception {
        // 前処理
        MessageResourcesDAOImpl_MessageResourcesDAOImplStub01 dao
            = new MessageResourcesDAOImpl_MessageResourcesDAOImplStub01();
        dao.setTestBeansXmlName("MessageResourcesDAOImpl_dbMessageResources04.xml");
        String[][] data = {
            {"MESSAGE_KEY", "MESSAGE_VALUE"},
            {"TEST01", "テストめっせーじ０１"},
            {"TEST02", "testMessage02"},
            {"TEST03", "試験的文零三"},
        };
        UTUtil.setData("MESSAGE", data);
        // テスト実施
        Map<String, String> map = dao.queryMessageMap(
                "SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE");

        // 判定
        assertEquals(3, map.size());
        assertEquals("テストめっせーじ０１", map.get("TEST01"));
        assertEquals("testMessage02", map.get("TEST02"));
        assertEquals("試験的文零三", map.get("TEST03"));
    }

    /**
     * testGetBeanXml01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) BEANS_XML:"dbMessageResources.xml"<br>
     *
     * <br>
     * 期待値：(戻り値) String:"dbMessageResources.xml"<br>
     *
     * <br>
     * 正常系１件のみテスト
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetBeanXml01() throws Exception {
        // 前処理
        MessageResourcesDAOImpl dao = new MessageResourcesDAOImpl();

        // テスト実施
        String actual = dao.getBeansXml();

        // 判定
        assertEquals("dbMessageResources.xml", actual);
    }



}
