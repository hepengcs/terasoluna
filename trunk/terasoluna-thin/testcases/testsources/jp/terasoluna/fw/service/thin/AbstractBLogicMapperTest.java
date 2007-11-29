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

package jp.terasoluna.fw.service.thin;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.fw.util.ClassLoadException;
import jp.terasoluna.fw.util.PropertyAccessException;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpServletResponse;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.service.thin.AbstractBLogicMapper} クラスの
 * ブラックボックステスト。
 *
 * <p>
 * <h4>【クラスの概要】</h4>
 * ビジネスロジック入出力情報反映抽象クラス。<br>
 * BLogicIOPlugInによって生成されたBLogicResourcesをもとに、
 * Web層のオブジェクトと、ビジネスロジック間のデータのマッピングを行う機能を
 * 集約した抽象クラスである。<br>
 * <br>
 * 前提条件：<br>
 * 抽象クラスAbstractBLogicMapperを実装したテスト用クラスを作成し、
 * 各テストケースの前提条件にあるメソッドを実装すること。
 * <p>
 *
 * @see jp.terasoluna.fw.service.thin.AbstractBLogicMapper
 */
@SuppressWarnings("unused")
public class AbstractBLogicMapperTest extends TestCase {

    private static final String TESTBEAN01_NAME = "jp.terasoluna.fw.service.thin.AbstractBLogicMapper_BeanStub01";

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     *
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(AbstractBLogicMapperTest.class);
    }

    /**
     * 初期化処理を行う。
     *
     * @throws Exception このメソッドで発生した例外
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * 終了処理を行う。
     *
     * @throws Exception このメソッドで発生した例外
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * コンストラクタ。
     *
     * @param name このテストケースの名前。
     */
    public AbstractBLogicMapperTest(String name) {
        super(name);
    }

    /**
     * testMapBLogicParams01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (引数) io:null<br>
     *         (状態) getValueFromForm():－<br>
     *         (状態) getValueFromRequest():－<br>
     *         (状態) getValueFromSession():－<br>
     *         (状態) getValueFromError():－<br>
     *         (状態) inputBeanName:－<br>
     *         (状態) property(取得元：取得元のキー名 → 業務ロジック内キー名）:マッピング定義なし<br>
     *
     * <br>
     * 期待値：(戻り値) Object:null<br>
     *         (状態変化) 例外:－<br>
     *         (状態変化) ログ:－<br>
     *
     * <br>
     * 引数のBLogicIOがnullで渡された時、nullを返却すること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testMapBLogicParams01() throws Exception {

        // 擬似リクエスト
        HttpServletRequest request = new MockHttpServletRequest();

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO設定
        BLogicIO io = null;

        // AbstractBLogicMapperImpl生成
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // テスト実施・判定
        assertNull(mapper.mapBLogicParams(request, response, io));
    }

    /**
     * testMapBLogicParams02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (引数) io:not null（io.getBLogicParams().size() == 0）<br>
     *         (状態) getValueFromForm():－<br>
     *         (状態) getValueFromRequest():－<br>
     *         (状態) getValueFromSession():－<br>
     *         (状態) getValueFromError():－<br>
     *         (状態) inputBeanName:定義なし<br>
     *         (状態) property(取得元：取得元のキー名 → 業務ロジック内キー名）:マッピング定義なし<br>
     *
     * <br>
     * 期待値：(戻り値) Object:null<br>
     *         (状態変化) 例外:－<br>
     *         (状態変化) ログ:－<br>
     *
     * <br>
     * マッピング定義（blogic-params）が記述されていない時、nullを返却すること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testMapBLogicParams02() throws Exception {
        // 擬似リクエスト
        HttpServletRequest request = new MockHttpServletRequest();

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO設定
        BLogicIO io = new BLogicIO();

        // AbstractBLogicMapperImpl生成
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // テスト実施・判定
        assertNull(mapper.mapBLogicParams(request, response, io));

    }

    /**
     * testMapBLogicParams03()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (引数) io:not null<br>
     *                （io.getBLogicParams().size()  > 0）<br>
     *         (状態) getValueFromForm():－<br>
     *         (状態) getValueFromRequest():－<br>
     *         (状態) getValueFromSession():－<br>
     *         (状態) getValueFromError():－<br>
     *         (状態) inputBeanName:存在しないJavaBean名<br>
     *         (状態) property(取得元：取得元のキー名 → 業務ロジック内キー名）:request：<br>
     *                 "requestValue"→"blogicRequestValue"<br>
     *
     * <br>
     * 期待値：(戻り値) Object:－<br>
     *         (状態変化) 例外:SystemException：<br>
     *                    メッセージキー："errors.blogic.mapper.create"<br>
     *                    ラップした例外：ClassLoadException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：<br>
     *                    "bean creation failure."<br>
     *
     * <br>
     * inputBeanNameに存在しないJavaBean名を定義した時、SystemExceptionが発生すること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testMapBLogicParams03() throws Exception {
        // 擬似リクエスト
        HttpServletRequest request = new MockHttpServletRequest();

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO設定
        BLogicIO io = new BLogicIO();
        io.setInputBeanName("nothingClass");
        BLogicProperty property = new BLogicProperty();
        property.setSource("request");
        property.setProperty("requestValue");
        property.setBLogicProperty("blogicRequestValue");
        io.setBLogicParam(property);

        // AbstractBLogicMapperImpl生成
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // テスト実施
        try {
            Object result = mapper.mapBLogicParams(request, response, io);
            fail();
        } catch (SystemException e) {
            // 判定
            assertEquals("errors.blogic.mapper.create", e.getErrorCode());
            assertEquals(ClassLoadException.class.getName(), e.getCause()
                    .getClass().getName());
            assertTrue(LogUTUtil.checkError("bean creation failure."));
        }
    }

    /**
     * testMapBLogicParams04()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (引数) io:not null<br>
     *                （io.getBLogicParams().size()  > 0）<br>
     *         (状態) getValueFromForm():引数propName+"_FromForm"を返却<br>
     *         (状態) getValueFromRequest():－<br>
     *         (状態) getValueFromSession():－<br>
     *         (状態) getValueFromError():－<br>
     *         (状態) inputBeanName:"jp.terasoluna.fw.service.thin.AbstractBLogicMapper_BeanStub01"<br>
     *         (状態) property(取得元：取得元のキー名 → 業務ロジック内キー名）:form：<br>
     *                 "userName"→"blogicUserName"<br>
     *
     * <br>
     * 期待値：(戻り値) Object:[blogicUserName="userName_FromForm"]<br>
     *         (状態変化) 例外:－<br>
     *         (状態変化) ログ:－<br>
     *
     * <br>
     * propertyの指定が1件の場合、取得元に対応する入力値取得メソッドを呼び出し、業務ロジックの入力情報Beanのフィールドにマッピングすること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testMapBLogicParams04() throws Exception {
        // 擬似リクエスト
        HttpServletRequest request = new MockHttpServletRequest();

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO設定
        BLogicIO io = new BLogicIO();
        io.setInputBeanName(TESTBEAN01_NAME);
        BLogicProperty property = new BLogicProperty();
        property.setSource("form");
        property.setProperty("userName");
        property.setBLogicProperty("blogicUserName");
        io.setBLogicParam(property);

        // AbstractBLogicMapperImpl生成
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // テスト実施・判定
        AbstractBLogicMapper_BeanStub01 result = (AbstractBLogicMapper_BeanStub01) mapper
                .mapBLogicParams(request, response, io);
        assertEquals("userName_FromForm", result.getBlogicUserName());

    }

    /**
     * testMapBLogicParams05()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (引数) io:not null<br>
     *                （io.getBLogicParams().size()  > 0）<br>
     *         (状態) getValueFromForm():引数propName+"_FromForm"を返却<br>
     *         (状態) getValueFromRequest():引数propName+"_FromRequest"を返却<br>
     *         (状態) getValueFromSession():引数propName+"_FromSession"を返却<br>
     *         (状態) getValueFromApplication():引数propName+"_FromApplication"を返却<br>
     *         (状態) getValueFromError():－<br>
     *         (状態) inputBeanName:"jp.terasoluna.fw.service.thin.AbstractBLogicMapper_BeanStub01"<br>
     *         (状態) property(取得元：取得元のキー名 → 業務ロジック内キー名）:form：<br>
     *                 "userName"→"blogicUserName"<br>
     *                request：<br>
     *                 "requestValue"→"blogicRequestValue"<br>
     *                session：<br>
     *                 "sessionValue"→"blogicSessionValue"<br>
     *                application：<br>
     *                 "applicationValue"→"blogicApplicationValue"<br>
     *
     * <br>
     * 期待値：(戻り値) Object:[blogicUserName="userName_FromForm"]<br>
     *                  [blogicRequestValue="rrequestValue_FromRequest"]<br>
     *                  [blogicSessionValue="sessionValue_FromSession"]<br>
     *                  [blogicApplicationValue="applicationValue_FromApplication"]<br>
     *         (状態変化) 例外:－<br>
     *         (状態変化) ログ:－<br>
     *
     * <br>
     * propertyの指定が複数件の場合、取得元に対応する入力値取得メソッドを呼び出し、業務ロジックの入力情報Beanのフィールドにマッピングすること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testMapBLogicParams05() throws Exception {
        // 擬似リクエスト
        HttpServletRequest request = new MockHttpServletRequest();

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO設定
        BLogicIO io = new BLogicIO();
        io.setInputBeanName(TESTBEAN01_NAME);
        BLogicProperty property = new BLogicProperty();
        property.setSource("form");
        property.setProperty("userName");
        property.setBLogicProperty("blogicUserName");
        io.setBLogicParam(property);
        BLogicProperty property2 = new BLogicProperty();
        property2.setSource("request");
        property2.setProperty("requestValue");
        property2.setBLogicProperty("blogicRequestValue");
        io.setBLogicParam(property2);
        BLogicProperty property3 = new BLogicProperty();
        property3.setSource("session");
        property3.setProperty("sessionValue");
        property3.setBLogicProperty("blogicSessionValue");
        io.setBLogicParam(property3);
        BLogicProperty property4 = new BLogicProperty();
        property4.setSource("application");
        property4.setProperty("applicationValue");
        property4.setBLogicProperty("blogicApplicationValue");
        io.setBLogicParam(property4);

        // AbstractBLogicMapperImpl生成
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // テスト実施・判定
        AbstractBLogicMapper_BeanStub01 result = (AbstractBLogicMapper_BeanStub01) mapper
                .mapBLogicParams(request, response, io);
        assertEquals("userName_FromForm", result.getBlogicUserName());
        assertEquals("requestValue_FromRequest", result.getBlogicRequestValue());
        assertEquals("sessionValue_FromSession", result.getBlogicSessionValue());
        assertEquals("applicationValue_FromApplication", result.getBlogicApplicationValue());
    }

    /**
     * testMapBLogicParams06()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (引数) io:not null<br>
     *                （io.getBLogicParams().size()  > 0）<br>
     *         (状態) getValueFromForm():引数propName+"_FromForm"を返却<br>
     *         (状態) getValueFromRequest():－<br>
     *         (状態) getValueFromSession():－<br>
     *         (状態) getValueFromError():－<br>
     *         (状態) inputBeanName:"jp.terasoluna.fw.service.thin.AbstractBLogicMapper_BeanStub01"<br>
     *         (状態) property(取得元：取得元のキー名 → 業務ロジック内キー名）:form：<br>
     *                 "userName"→指定なし<br>
     *
     * <br>
     * 期待値：(戻り値) Object:[userName="userName_FromForm"]<br>
     *         (状態変化) 例外:－<br>
     *         (状態変化) ログ:－<br>
     *
     * <br>
     * 業務ロジック内キーが指定されていない場合、入力情報Beanのマッピング先のフィールド名は取得元のキー名となること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testMapBLogicParams06() throws Exception {
        // 擬似リクエスト
        HttpServletRequest request = new MockHttpServletRequest();

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO設定
        BLogicIO io = new BLogicIO();
        io.setInputBeanName(TESTBEAN01_NAME);
        BLogicProperty property = new BLogicProperty();
        property.setSource("form");
        property.setProperty("userName");
        io.setBLogicParam(property);

        // AbstractBLogicMapperImpl生成
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // テスト実施・判定
        AbstractBLogicMapper_BeanStub01 result = (AbstractBLogicMapper_BeanStub01) mapper
                .mapBLogicParams(request, response, io);
        assertEquals("userName_FromForm", result.getUserName());
    }

    /**
     * testMapBLogicParams07()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (引数) io:not null<br>
     *                （io.getBLogicParams().size()  > 0）<br>
     *         (状態) getValueFromForm():引数propName+"_FromForm"を返却<br>
     *         (状態) getValueFromRequest():－<br>
     *         (状態) getValueFromSession():－<br>
     *         (状態) getValueFromError():－<br>
     *         (状態) inputBeanName:"jp.terasoluna.fw.service.thin.AbstractBLogicMapper_BeanStub01"<br>
     *         (状態) property(取得元：取得元のキー名 → 業務ロジック内キー名）:page:<br>
     *                 "userName" → "blogicUserName"<br>
     *
     * <br>
     * 期待値：(戻り値) Object:－<br>
     *         (状態変化) 例外:SystemException：<br>
     *                    メッセージキー："errors.blogic.mapper.getvalue"<br>
     *                    置換文字列１：<br>
     *                    "userName"<br>
     *                    ラップした例外：BLogicMapperException(NoSuchMethodException)<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：<br>
     *                    "no such method."<br>
     *
     * <br>
     * 取得元に想定しない文字列を指定した時、SystemExceptionが発生すること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testMapBLogicParams07() throws Exception {
        // 擬似リクエスト
        HttpServletRequest request = new MockHttpServletRequest();

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO設定
        BLogicIO io = new BLogicIO();
        io.setInputBeanName(TESTBEAN01_NAME);
        BLogicProperty property = new BLogicProperty();
        // 取得元に想定外の文字列を設定
        property.setSource("page");
        property.setProperty("userName");
        property.setBLogicProperty("blogicUserName");
        io.setBLogicParam(property);

        // AbstractBLogicMapperImpl生成
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // テスト実施
        try {
            Object result = mapper.mapBLogicParams(request, response, io);
            fail();
        } catch (SystemException e) {
            // 判定
            assertEquals("errors.blogic.mapper.getvalue", e.getErrorCode());
            assertEquals(BLogicMapperException.class.getName(), e.getCause()
                    .getClass().getName());
            assertEquals("userName", e.getOptions()[0]);
            // NoSuchMethodExceptionのチェック
            assertEquals(NoSuchMethodException.class.getName(), e.getCause()
                    .getCause().getClass().getName());
            assertTrue(LogUTUtil.checkError("no such method."));
        }

    }

    /**
     * testMapBLogicParams08()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (引数) io:not null<br>
     *                （io.getBLogicParams().size()  > 0）<br>
     *         (状態) getValueFromForm():－<br>
     *         (状態) getValueFromRequest():－<br>
     *         (状態) getValueFromSession():－<br>
     *         (状態) getValueFromError():Exception発生<br>
     *         (状態) inputBeanName:"jp.terasoluna.fw.service.thin.AbstractBLogicMapper_BeanStub01"<br>
     *         (状態) property(取得元：取得元のキー名 → 業務ロジック内キー名）:error：<br>
     *                 "userName"→"blogicUserName"<br>
     *
     * <br>
     * 期待値：(戻り値) Object:－<br>
     *         (状態変化) 例外:SystemException：<br>
     *                    メッセージキー："errors.blogic.mapper.getvalue"<br>
     *                    置換文字列１：<br>
     *                    "userName"<br>
     *                    ラップした例外：BLogicMapperException(InvocationTargetException)<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：<br>
     *                    "exception is thrown out by invokeMethod."<br>
     *
     * <br>
     * リフレクションで実行するメソッドで想定外の例外が発生した時、SystemExceptionが発生すること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testMapBLogicParams08() throws Exception {
        // 擬似リクエスト
        HttpServletRequest request = new MockHttpServletRequest();

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO設定
        BLogicIO io = new BLogicIO();
        io.setInputBeanName(TESTBEAN01_NAME);
        BLogicProperty property = new BLogicProperty();
        property.setSource("error");
        property.setProperty("userName");
        property.setBLogicProperty("blogicUserName");
        io.setBLogicParam(property);

        // AbstractBLogicMapperImpl生成
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // テスト実施
        try {
            Object result = mapper.mapBLogicParams(request, response, io);
            fail();
        } catch (SystemException e) {
            // 判定
            assertEquals("errors.blogic.mapper.getvalue", e.getErrorCode());
            assertEquals(BLogicMapperException.class.getName(), e.getCause()
                    .getClass().getName());
            assertEquals("userName", e.getOptions()[0]);
            // InvocationTargetExceptionのチェック
            assertEquals(InvocationTargetException.class.getName(), e
                    .getCause().getCause().getClass().getName());
            assertTrue(LogUTUtil
                    .checkError("exception is thrown out by invokeMethod."));
        }

    }

    /**
     * testMapBLogicParams09()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (引数) io:not null<br>
     *                （io.getBLogicParams().size()  > 0）<br>
     *         (状態) getValueFromForm():引数propName+"_FromForm"を返却<br>
     *         (状態) getValueFromRequest():－<br>
     *         (状態) getValueFromSession():－<br>
     *         (状態) getValueFromError():－<br>
     *         (状態) inputBeanName:"jp.terasoluna.fw.service.thin.AbstractBLogicMapper_BeanStub01"<br>
     *         (状態) property(取得元：取得元のキー名 → 業務ロジック内キー名）:form：<br>
     *                 "userName"→"nothing"（存在しないフィールド）<br>
     *
     * <br>
     * 期待値：(戻り値) Object:－<br>
     *         (状態変化) 例外:SystemException：<br>
     *                    メッセージキー："errors.blogic.mapper.setproperty"<br>
     *                    置換文字列１：<br>
     *                    "nothing"<br>
     *                    ラップした例外：BLogicMapperException(PropertyAccessException)<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：<br>
     *                    "setBeanProperty failure."<br>
     *
     * <br>
     * 業務ロジック内キー名で入力情報Beanに存在しないフィールドを指定した時、SystemExceptionが発生すること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testMapBLogicParams09() throws Exception {
        // 擬似リクエスト
        HttpServletRequest request = new MockHttpServletRequest();

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO設定
        BLogicIO io = new BLogicIO();
        io.setInputBeanName(TESTBEAN01_NAME);
        BLogicProperty property = new BLogicProperty();
        property.setSource("form");
        property.setProperty("userName");
        property.setBLogicProperty("nothing");
        io.setBLogicParam(property);

        // AbstractBLogicMapperImpl生成
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // テスト実施
        try {
            Object result = mapper.mapBLogicParams(request, response, io);
            fail();
        } catch (SystemException e) {
            // 判定
            assertEquals("errors.blogic.mapper.setproperty", e.getErrorCode());
            assertEquals(BLogicMapperException.class.getName(), e.getCause()
                    .getClass().getName());
            assertEquals("nothing", e.getOptions()[0]);
            // PropertyAccessExceptionのチェック
            assertEquals(PropertyAccessException.class.getName(), e.getCause()
                    .getCause().getClass().getName());
            assertTrue(LogUTUtil.checkError("setBeanProperty failure."));
        }
    }

    /**
     * testMapBLogicParams10()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (引数) io:not null<br>
     *                （io.getBLogicParams().size()  > 0）<br>
     *         (状態) getValueFromForm():－<br>
     *         (状態) getValueFromRequest():－<br>
     *         (状態) getValueFromSession():－<br>
     *         (状態) getValueFromError():－<br>
     *         (状態) inputBeanName:"jp.terasoluna.fw.service.thin.AbstractBLogicMapper_BeanStub01"<br>
     *         (状態) property(取得元：取得元のキー名 → 業務ロジック内キー名）:""（空文字）：<br>
     *                 "userName" → "blogicUserName"<br>
     *
     * <br>
     * 期待値：(戻り値) Object:－<br>
     *         (状態変化) 例外:SystemException：<br>
     *                    メッセージキー："errors.blogic.mapper.source"<br>
     *                    ラップした例外：BLogicMapperException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：<br>
     *                    "source is illegal."<br>
     *
     * <br>
     * 取得元に""（空文字）を指定した時、SystemExceptionが発生すること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testMapBLogicParams10() throws Exception {
        // 擬似リクエスト
        HttpServletRequest request = new MockHttpServletRequest();

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO設定
        BLogicIO io = new BLogicIO();
        io.setInputBeanName(TESTBEAN01_NAME);
        BLogicProperty property = new BLogicProperty();
        property.setSource("");
        property.setProperty("userName");
        property.setBLogicProperty("blogicUserName");
        io.setBLogicParam(property);

        // AbstractBLogicMapperImpl生成
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // テスト実施
        try {
            Object result = mapper.mapBLogicParams(request, response, io);
            fail();
        } catch (SystemException e) {
            // 判定
            assertEquals("errors.blogic.mapper.source", e.getErrorCode());
            assertEquals(BLogicMapperException.class.getName(), e.getCause()
                    .getClass().getName());
            assertTrue(LogUTUtil.checkError("source is illegal."));
        }
    }

    /**
     * testMapBLogicParams11()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (引数) io:not null<br>
     *                （io.getBLogicParams().size()  > 0）<br>
     *         (状態) getValueFromForm():－<br>
     *         (状態) getValueFromRequest():－<br>
     *         (状態) getValueFromSession():－<br>
     *         (状態) getValueFromError():－<br>
     *         (状態) inputBeanName:"jp.terasoluna.fw.service.thin.AbstractBLogicMapper_BeanStub01"<br>
     *         (状態) property(取得元：取得元のキー名 → 業務ロジック内キー名）:null：<br>
     *                 "userName" → "blogicUserName"<br>
     *
     * <br>
     * 期待値：(戻り値) Object:－<br>
     *         (状態変化) 例外:SystemException：<br>
     *                    メッセージキー："errors.blogic.mapper.source"<br>
     *                    ラップした例外：BLogicMapperException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：<br>
     *                    "source is illegal."<br>
     *
     * <br>
     * 取得元にnullを指定した時、SystemExceptionが発生すること。（本来ありえない）
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testMapBLogicParams11() throws Exception {
        // 擬似リクエスト
        HttpServletRequest request = new MockHttpServletRequest();

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO設定
        BLogicIO io = new BLogicIO();
        io.setInputBeanName(TESTBEAN01_NAME);
        BLogicProperty property = new BLogicProperty();
        property.setSource(null);
        property.setProperty("userName");
        property.setBLogicProperty("blogicUserName");
        io.setBLogicParam(property);

        // AbstractBLogicMapperImpl生成
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // テスト実施
        try {
            Object result = mapper.mapBLogicParams(request, response, io);
            fail();
        } catch (SystemException e) {
            // 判定
            assertEquals("errors.blogic.mapper.source", e.getErrorCode());
            assertEquals(BLogicMapperException.class.getName(), e.getCause()
                    .getClass().getName());
            assertTrue(LogUTUtil.checkError("source is illegal."));
        }
    }

    /**
     * testMapBLogicParams12()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (引数) io:not null<br>
     *                （io.getBLogicParams().size()  > 0）<br>
     *         (状態) getValueFromForm():－<br>
     *         (状態) getValueFromRequest():－<br>
     *         (状態) getValueFromSession():－<br>
     *         (状態) getValueFromError():－<br>
     *         (状態) inputBeanName:null<br>
     *         (状態) property(取得元：取得元のキー名 → 業務ロジック内キー名）:request：<br>
     *                 "requestValue"→"blogicRequestValue"<br>
     *
     * <br>
     * 期待値：(戻り値) Object:null<br>
     *
     * <br>
     * inputBeanNameがnullのとき、nullが返却されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testMapBLogicParams12() throws Exception {
        // 擬似リクエスト
        HttpServletRequest request = new MockHttpServletRequest();

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO設定
        BLogicIO io = new BLogicIO();
        io.setInputBeanName(null);
        BLogicProperty property = new BLogicProperty();
        property.setSource("request");
        property.setProperty("requestValue");
        property.setBLogicProperty("blogicRequestValue");
        io.setBLogicParam(property);

        // AbstractBLogicMapperImpl生成
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // テスト実施
        assertNull(mapper.mapBLogicParams(request, response, io));
    }

    /**
     * testMapBLogicParams13()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (引数) io:not null<br>
     *                （io.getBLogicParams().size()  > 0）<br>
     *         (状態) getValueFromForm():－<br>
     *         (状態) getValueFromRequest():－<br>
     *         (状態) getValueFromSession():－<br>
     *         (状態) getValueFromError():－<br>
     *         (状態) inputBeanName:"jp.terasoluna.fw.service.thin.AbstractBLogicMapper_BeanStub01"<br>
     *         (状態) property(取得元：取得元のキー名 → 業務ロジック内キー名）:マッピング定義なし。<br>
     *
     * <br>
     * 期待値：(戻り値) Object:null<br>
     *
     * <br>
     * blogic-ioにproperty指定がない場合、入力値クラスが生成されないことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testMapBLogicParams13() throws Exception {
        // 擬似リクエスト
        HttpServletRequest request = new MockHttpServletRequest();

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO設定
        BLogicIO io = new BLogicIO();
        io.setInputBeanName(TESTBEAN01_NAME);

        // AbstractBLogicMapperImpl生成
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // テスト実施
        assertNull(mapper.mapBLogicParams(request, response, io));
    }

    /**
     * testMapBLogicResult01()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (引数) io:not null<br>
     *         (引数) result:null<br>
     *         (状態) setValueToForm():－<br>
     *         (状態) setValueToRequest():－<br>
     *         (状態) setValueToSession():－<br>
     *         (状態) setValueToError():－<br>
     *         (状態) property(取得元：業務ロジック内キー名 → 反映先のキー名）:－<br>
     *
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) 例外:SystemException：<br>
     *                    メッセージキー："errors.blogic.mapper.result"<br>
     *                    ラップした例外：BLogicMapperException(NullPointerException)<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：<br>
     *                    "result is null."<br>
     *         (状態変化) request:ー<br>
     *
     * <br>
     * 引数のBLogicResultがnullで渡された時、SystemExceptionが発生すること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testMapBLogicResult01() throws Exception {
        // 擬似リクエスト
        HttpServletRequest request = new MockHttpServletRequest();

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO設定
        BLogicIO io = new BLogicIO();

        // BLogicResult設定
        BLogicResult result = null;

        // AbstractBLogicMapperImpl生成
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // テスト実施
        try {
            mapper.mapBLogicResult(request, response, io, result);
            fail();
        } catch (SystemException e) {
            // 判定
            assertEquals("errors.blogic.mapper.result", e.getErrorCode());
            assertEquals(BLogicMapperException.class.getName(), e.getCause()
                    .getClass().getName());
            // NullPointerExceptionのチェック
            assertEquals(NullPointerException.class.getName(), e.getCause()
                    .getCause().getClass().getName());
            assertTrue(LogUTUtil.checkError("BLogicResult is null."));
        }
    }

    /**
     * testMapBLogicResult02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (引数) io:null<br>
     *         (引数) result:not null（result.getResultObject()==null）<br>
     *         (状態) setValueToForm():－<br>
     *         (状態) setValueToRequest():－<br>
     *         (状態) setValueToSession():－<br>
     *         (状態) setValueToError():－<br>
     *         (状態) property(取得元：業務ロジック内キー名 → 反映先のキー名）:－<br>
     *
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) 例外:－<br>
     *         (状態変化) ログ:－<br>
     *         (状態変化) request:なし<br>
     *
     * <br>
     * 引数のBlogicIOがnull、かつ、resultが持つresultObjectがnullの時、何もせずに処理を終了すること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testMapBLogicResult02() throws Exception {
        // 擬似リクエスト
        HttpServletRequest request = new MockHttpServletRequest();

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO設定
        BLogicIO io = null;

        // BLogicResult設定
        BLogicResult result = new BLogicResult();
        result.setResultObject(null);

        // AbstractBLogicMapperImpl生成
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // テスト実施
        mapper.mapBLogicResult(request, response, io, result);

        // 判定
        assertFalse(request.getAttributeNames().hasMoreElements());
    }

    /**
     * testMapBLogicResult03()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (引数) io:null<br>
     *         (引数) result:not null（result.getResultObject()!=null）<br>
     *         (状態) setValueToForm():－<br>
     *         (状態) setValueToRequest():－<br>
     *         (状態) setValueToSession():－<br>
     *         (状態) setValueToError():－<br>
     *         (状態) property(取得元：業務ロジック内キー名 → 反映先のキー名）:－<br>
     *
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) 例外:SystemException：<br>
     *                    メッセージキー："errors.blogic.mapper.notnull"<br>
     *                    ラップした例外：BLogicMapperException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：<br>
     *                    "bean is not null."<br>
     *         (状態変化) request:－<br>
     *
     * <br>
     * 引数のBlogicIOがnull、かつ、resultが持つresultObjectがnullでない時、SystemExceptionが発生すること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testMapBLogicResult03() throws Exception {
        // 擬似リクエスト
        HttpServletRequest request = new MockHttpServletRequest();

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO設定
        BLogicIO io = null;

        // BLogicResult設定
        BLogicResult result = new BLogicResult();
        result.setResultObject("not null");

        // AbstractBLogicMapperImpl生成
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // テスト実施
        try {
            mapper.mapBLogicResult(request, response, io, result);
            fail();
        } catch (SystemException e) {
            // 判定
            assertEquals("errors.blogic.mapper.notnull", e.getErrorCode());
            assertEquals(BLogicMapperException.class.getName(), e.getCause()
                    .getClass().getName());
            assertTrue(LogUTUtil.checkError("bean is not null."));
        }
    }

    /**
     * testMapBLogicResult04()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (引数) io:not null（io.getBLogicResults().size() == 0）<br>
     *         (引数) result:not null（result.getResultObject()==null）<br>
     *         (状態) setValueToForm():－<br>
     *         (状態) setValueToRequest():－<br>
     *         (状態) setValueToSession():－<br>
     *         (状態) setValueToError():－<br>
     *         (状態) property(取得元：業務ロジック内キー名 → 反映先のキー名）:マッピング定義なし<br>
     *
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) 例外:－<br>
     *         (状態変化) ログ:－<br>
     *         (状態変化) request:なし<br>
     *
     * <br>
     * マッピング定義（blogic-results）が記述されておらず、かつ、resultが持つresultObjectがnullの時、何もせずに処理を終了すること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testMapBLogicResult04() throws Exception {
        // 擬似リクエスト
        HttpServletRequest request = new MockHttpServletRequest();

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO設定
        BLogicIO io = new BLogicIO();

        // BLogicResult設定
        BLogicResult result = new BLogicResult();
        result.setResultObject(null);

        // AbstractBLogicMapperImpl生成
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // テスト実施
        mapper.mapBLogicResult(request, response, io, result);

        // 判定
        assertFalse(request.getAttributeNames().hasMoreElements());
    }

    /**
     * testMapBLogicResult05()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (引数) io:not null（io.getBLogicResults().size() == 0）<br>
     *         (引数) result:not null（result.getResultObject()!=null）<br>
     *         (状態) setValueToForm():－<br>
     *         (状態) setValueToRequest():－<br>
     *         (状態) setValueToSession():－<br>
     *         (状態) setValueToError():－<br>
     *         (状態) property(取得元：業務ロジック内キー名 → 反映先のキー名）:マッピング定義なし<br>
     *
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) 例外:SystemException：<br>
     *                    メッセージキー："errors.blogic.mapper.notnull"<br>
     *                    ラップした例外：BLogicMapperException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：<br>
     *                    "bean is not null."<br>
     *         (状態変化) request:－<br>
     *
     * <br>
     * マッピング定義（blogic-results）が記述されておらず、かつ、resultが持つresultObjectがnullでない時、SystemExceptionが発生すること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testMapBLogicResult05() throws Exception {
        // 擬似リクエスト
        HttpServletRequest request = new MockHttpServletRequest();

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO設定
        BLogicIO io = new BLogicIO();

        // BLogicResult設定
        BLogicResult result = new BLogicResult();
        result.setResultObject("not null");

        // AbstractBLogicMapperImpl生成
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // テスト実施
        try {
            mapper.mapBLogicResult(request, response, io, result);
            fail();
        } catch (SystemException e) {
            // 判定
            assertEquals("errors.blogic.mapper.notnull", e.getErrorCode());
            assertEquals(BLogicMapperException.class.getName(), e.getCause()
                    .getClass().getName());
            assertTrue(LogUTUtil.checkError("bean is not null."));
        }
    }

    /**
     * testMapBLogicResult06()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (引数) io:not null<br>
     *                （io.getBLogicResults().size() > 0）<br>
     *         (引数) result:not null<br>
     *                （result.getResultObject()!=null）<br>
     *         (状態) setValueToRequest():－<br>
     *         (状態) setValueToSession():－<br>
     *         (状態) setValueToError():－<br>
     *         (状態) property(取得元：業務ロジック内キー名 → 反映先のキー名）:form：<br>
     *                "nothing"（存在しないフィールド） →"userName"<br>
     *
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) 例外:SystemException：<br>
     *                    メッセージキー："errors.blogic.mapper.getproperty"<br>
     *                    置換文字列１：<br>
     *                    "nothing"<br>
     *                    ラップした例外：BLogicMapperException(PropertyAccessException)<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：<br>
     *                    "getBeanProperty failure."<br>
     *         (状態変化) request:－<br>
     *
     * <br>
     * 業務ロジック内キー名で出力情報Beanに存在しないフィールドを指定した時、SystemExceptionが発生すること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testMapBLogicResult06() throws Exception {
        // 擬似リクエスト
        HttpServletRequest request = new MockHttpServletRequest();

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO設定
        BLogicIO io = new BLogicIO();
        BLogicProperty property = new BLogicProperty();
        property.setDest("form");
        property.setProperty("userName");
        property.setBLogicProperty("nothing");
        io.setBLogicResult(property);

        // BLogicResult設定
        BLogicResult result = new BLogicResult();
        AbstractBLogicMapper_BeanStub01 bean = new AbstractBLogicMapper_BeanStub01();
        result.setResultObject(bean);

        // AbstractBLogicMapperImpl生成
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // テスト実施
        try {
            mapper.mapBLogicResult(request, response, io, result);
            fail();
        } catch (SystemException e) {
            // 判定
            assertEquals("errors.blogic.mapper.getproperty", e.getErrorCode());
            assertEquals("nothing", e.getOptions()[0]);
            assertEquals(BLogicMapperException.class.getName(), e.getCause()
                    .getClass().getName());
            // PropertyAccessExceptionのチェック
            assertEquals(PropertyAccessException.class.getName(), e.getCause()
                    .getCause().getClass().getName());
            assertTrue(LogUTUtil.checkError("getBeanProperty failure."));
        }
    }

    /**
     * testMapBLogicResult07()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (引数) io:not null<br>
     *                （io.getBLogicResults().size() > 0）<br>
     *         (引数) result:result.getResultObject() [blogicUserName="茅場太郎"]<br>
     *         (状態) setValueToForm():requestに「反映先キー名+"_ToForm"」をキーに引数valueを設定<br>
     *         (状態) setValueToRequest():－<br>
     *         (状態) setValueToSession():－<br>
     *         (状態) setValueToError():－<br>
     *         (状態) property(取得元：業務ロジック内キー名 → 反映先のキー名）:form：<br>
     *                "blogicUserName"→ "userName"<br>
     *
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) 例外:－<br>
     *         (状態変化) ログ:－<br>
     *         (状態変化) request:[userName_ToForm="茅場太郎"]<br>
     *
     * <br>
     * propertyの指定が1件の場合、反映先に対応する出力値反映メソッドを呼び出し、反映先のフィールドにマッピングすること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testMapBLogicResult07() throws Exception {
        // 擬似リクエスト
        HttpServletRequest request = new MockHttpServletRequest();

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO設定
        BLogicIO io = new BLogicIO();
        BLogicProperty property = new BLogicProperty();
        property.setDest("form");
        property.setProperty("userName");
        property.setBLogicProperty("blogicUserName");
        io.setBLogicResult(property);

        // BLogicResult設定
        BLogicResult result = new BLogicResult();
        AbstractBLogicMapper_BeanStub01 bean = new AbstractBLogicMapper_BeanStub01();
        bean.setBlogicUserName("茅場太郎");
        result.setResultObject(bean);

        // AbstractBLogicMapperImpl生成
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // テスト実施
        mapper.mapBLogicResult(request, response, io, result);

        // 判定
        assertEquals("茅場太郎", request.getAttribute("userName_ToForm"));
    }

    /**
     * testMapBLogicResult08()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (引数) io:not null<br>
     *                （io.getBLogicResults().size() > 0）<br>
     *         (引数) result:result.getResultObject() [blogicUserName="茅場太郎"、blogicRequestValue="requestValue、blogicSessionValue="sessionValue"]<br>
     *         (状態) setValueToForm():requestに「反映先キー名+"_ToForm"」をキーに引数valueを設定<br>
     *         (状態) setValueToRequest():requestに「反映先キー名+"_ToRequest"」をキーに引数valueを設定<br>
     *         (状態) setValueToSession():requestに「反映先キー名+"_ToSession"」をキーに引数valueを設定<br>
     *         (状態) setValueToError():－<br>
     *         (状態) property(取得元：業務ロジック内キー名 → 反映先のキー名）:form：<br>
     *                "blogicUserName"→ "userName"<br>
     *                request：<br>
     *                "blogicRequestValue" →"requestValue"<br>
     *                session：<br>
     *                "blogicSessionValue" →"sessionValue"<br>
     *
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) 例外:－<br>
     *         (状態変化) ログ:－<br>
     *         (状態変化) request:[userName_ToForm="茅場太郎"]<br>
     *                    [requestValue_ToRequest="requestValue"]<br>
     *                    [sessionValue_ToSession="sessionValue"]<br>
     *
     * <br>
     * propertyの指定が複数件の場合、反映先に対応する出力値反映メソッドを呼び出し、反映先のフィールドにマッピングすること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testMapBLogicResult08() throws Exception {
        // 擬似リクエスト
        HttpServletRequest request = new MockHttpServletRequest();

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO設定
        BLogicIO io = new BLogicIO();
        BLogicProperty property = new BLogicProperty();
        property.setDest("form");
        property.setProperty("userName");
        property.setBLogicProperty("blogicUserName");
        io.setBLogicResult(property);
        BLogicProperty property2 = new BLogicProperty();
        property2.setDest("request");
        property2.setProperty("requestValue");
        property2.setBLogicProperty("blogicRequestValue");
        io.setBLogicResult(property2);
        BLogicProperty property3 = new BLogicProperty();
        property3.setDest("session");
        property3.setProperty("sessionValue");
        property3.setBLogicProperty("blogicSessionValue");
        io.setBLogicResult(property3);

        // BLogicResult設定
        BLogicResult result = new BLogicResult();
        AbstractBLogicMapper_BeanStub01 bean = new AbstractBLogicMapper_BeanStub01();
        bean.setBlogicUserName("茅場太郎");
        bean.setBlogicRequestValue("requestValue");
        bean.setBlogicSessionValue("sessionValue");
        result.setResultObject(bean);

        // AbstractBLogicMapperImpl生成
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // テスト実施
        mapper.mapBLogicResult(request, response, io, result);

        // 判定
        assertEquals("茅場太郎", request.getAttribute("userName_ToForm"));
        assertEquals("requestValue", request
                .getAttribute("requestValue_ToRequest"));
        assertEquals("sessionValue", request
                .getAttribute("sessionValue_ToSession"));
    }

    /**
     * testMapBLogicResult09()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (引数) io:not null<br>
     *                （io.getBLogicResults().size() > 0）<br>
     *         (引数) result:result.getResultObject() [userName="茅場太郎"、blogicUserName=null]<br>
     *         (状態) setValueToForm():requestに「反映先キー名+"_ToForm"」をキーに引数valueを設定<br>
     *         (状態) setValueToRequest():－<br>
     *         (状態) setValueToSession():－<br>
     *         (状態) setValueToError():－<br>
     *         (状態) property(取得元：業務ロジック内キー名 → 反映先のキー名）:form：<br>
     *                指定なし→ "userName"<br>
     *
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) 例外:－<br>
     *         (状態変化) ログ:－<br>
     *         (状態変化) request:[userName_ToForm="茅場太郎"]<br>
     *
     * <br>
     * 業務ロジック内キーが指定されていない場合、反映値として取得するフィールドの名前は反映先のキー名と同じになること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testMapBLogicResult09() throws Exception {
        // 擬似リクエスト
        HttpServletRequest request = new MockHttpServletRequest();

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO設定
        BLogicIO io = new BLogicIO();
        BLogicProperty property = new BLogicProperty();
        property.setDest("form");
        property.setProperty("userName");
        io.setBLogicResult(property);

        // BLogicResult設定
        BLogicResult result = new BLogicResult();
        AbstractBLogicMapper_BeanStub01 bean = new AbstractBLogicMapper_BeanStub01();
        bean.setUserName("茅場太郎");
        result.setResultObject(bean);

        // AbstractBLogicMapperImpl生成
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // テスト実施
        mapper.mapBLogicResult(request, response, io, result);

        // 判定
        assertEquals("茅場太郎", request.getAttribute("userName_ToForm"));
    }

    /**
     * testMapBLogicResult10()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (引数) io:not null<br>
     *                （io.getBLogicResults().size() > 0）<br>
     *         (引数) result:not null<br>
     *         (状態) setValueToRequest():－<br>
     *         (状態) setValueToSession():－<br>
     *         (状態) setValueToError():－<br>
     *         (状態) property(取得元：業務ロジック内キー名 → 反映先のキー名）:page:<br>
     *                  "blogicUserName"→"userName"<br>
     *
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) 例外:SystemException：<br>
     *                    メッセージキー："errors.blogic.mapper.setvalue"<br>
     *                    置換文字列１：<br>
     *                    "userName"<br>
     *                    ラップした例外：BLogicMapperException(NoSuchMethodException)<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：<br>
     *                    "no such method."<br>
     *         (状態変化) request:－<br>
     *
     * <br>
     * 反映先に想定しない文字列を指定した時、SystemExceptionが発生すること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testMapBLogicResult10() throws Exception {
        // 擬似リクエスト
        HttpServletRequest request = new MockHttpServletRequest();

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO設定
        BLogicIO io = new BLogicIO();
        BLogicProperty property = new BLogicProperty();
        // 取得元に想定外の文字列を設定
        property.setDest("page");
        property.setProperty("userName");
        property.setBLogicProperty("blogicUserName");
        io.setBLogicResult(property);

        // BLogicResult設定
        BLogicResult result = new BLogicResult();
        AbstractBLogicMapper_BeanStub01 bean = new AbstractBLogicMapper_BeanStub01();
        result.setResultObject(bean);

        // AbstractBLogicMapperImpl生成
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // テスト実施
        try {
            mapper.mapBLogicResult(request, response, io, result);
            fail();
        } catch (SystemException e) {
            // 判定
            assertEquals("errors.blogic.mapper.setvalue", e.getErrorCode());
            assertEquals(BLogicMapperException.class.getName(), e.getCause()
                    .getClass().getName());
            assertEquals("userName", e.getOptions()[0]);
            // NoSuchMethodExceptionのチェック
            assertEquals(NoSuchMethodException.class.getName(), e.getCause()
                    .getCause().getClass().getName());
            assertTrue(LogUTUtil.checkError("no such method."));
        }
    }

    /**
     * testMapBLogicResult11()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (引数) io:not null<br>
     *                （io.getBLogicResults().size() > 0）<br>
     *         (引数) result:not null<br>
     *         (状態) setValueToForm():－<br>
     *         (状態) setValueToRequest():－<br>
     *         (状態) setValueToSession():－<br>
     *         (状態) setValueToError():Exception発生<br>
     *         (状態) property(取得元：業務ロジック内キー名 → 反映先のキー名）:error：<br>
     *                "blogicUserName"→ "userName"<br>
     *
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) 例外:SystemException：<br>
     *                    メッセージキー："errors.blogic.mapper.setvalue"<br>
     *                    置換文字列１：<br>
     *                    "userName"<br>
     *                    ラップした例外：BLogicMapperException(InvocationTargetException)<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：<br>
     *                    "exception is thrown out by invokeMethod."<br>
     *         (状態変化) request:－<br>
     *
     * <br>
     * リフレクションで実行するメソッドで想定外の例外が発生した時、SystemExceptionが発生すること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testMapBLogicResult11() throws Exception {
        // 擬似リクエスト
        HttpServletRequest request = new MockHttpServletRequest();

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO設定
        BLogicIO io = new BLogicIO();
        BLogicProperty property = new BLogicProperty();
        property.setDest("error");
        property.setProperty("userName");
        property.setBLogicProperty("blogicUserName");
        io.setBLogicResult(property);

        // BLogicResult設定
        BLogicResult result = new BLogicResult();
        AbstractBLogicMapper_BeanStub01 bean = new AbstractBLogicMapper_BeanStub01();
        result.setResultObject(bean);

        // AbstractBLogicMapperImpl生成
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // テスト実施
        try {
            mapper.mapBLogicResult(request, response, io, result);
            fail();
        } catch (SystemException e) {
            // 判定
            assertEquals("errors.blogic.mapper.setvalue", e.getErrorCode());
            assertEquals(BLogicMapperException.class.getName(), e.getCause()
                    .getClass().getName());
            assertEquals("userName", e.getOptions()[0]);
            // InvocationTargetExceptionのチェック
            assertEquals(InvocationTargetException.class.getName(), e
                    .getCause().getCause().getClass().getName());
            assertTrue(LogUTUtil
                    .checkError("exception is thrown out by invokeMethod."));
        }
    }

    /**
     * testMapBLogicResult12()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (引数) io:not null<br>
     *                （io.getBLogicResults().size() > 0）<br>
     *         (引数) result:not null<br>
     *         (状態) setValueToForm():－<br>
     *         (状態) setValueToRequest():－<br>
     *         (状態) setValueToSession():－<br>
     *         (状態) setValueToError():－<br>
     *         (状態) property(取得元：業務ロジック内キー名 → 反映先のキー名）:""（空文字）：<br>
     *                 "blogicUserName" →"userName"<br>
     *
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) 例外:SystemException：<br>
     *                    メッセージキー："errors.blogic.mapper.dest"<br>
     *                    ラップした例外：BLogicMapperException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：<br>
     *                    "dest is illegal."<br>
     *         (状態変化) request:－<br>
     *
     * <br>
     * 反映先に""（空文字）を指定した時、SystemExceptionが発生すること。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testMapBLogicResult12() throws Exception {
        // 擬似リクエスト
        HttpServletRequest request = new MockHttpServletRequest();

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO設定
        BLogicIO io = new BLogicIO();
        BLogicProperty property = new BLogicProperty();
        property.setDest("");
        property.setProperty("userName");
        property.setBLogicProperty("blogicUserName");
        io.setBLogicResult(property);

        // BLogicResult設定
        BLogicResult result = new BLogicResult();
        AbstractBLogicMapper_BeanStub01 bean = new AbstractBLogicMapper_BeanStub01();
        result.setResultObject(bean);

        // AbstractBLogicMapperImpl生成
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // テスト実施
        try {
            mapper.mapBLogicResult(request, response, io, result);
            fail();
        } catch (SystemException e) {
            // 判定
            assertEquals("errors.blogic.mapper.dest", e.getErrorCode());
            assertEquals(BLogicMapperException.class.getName(), e.getCause()
                    .getClass().getName());
            assertTrue(LogUTUtil.checkError("dest is illegal."));
        }
    }

    /**
     * testMapBLogicResult13()
     * <br><br>
     *
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (引数) io:not null<br>
     *                （io.getBLogicResults().size() > 0）<br>
     *         (引数) result:not null<br>
     *         (状態) setValueToForm():－<br>
     *         (状態) setValueToRequest():－<br>
     *         (状態) setValueToSession():－<br>
     *         (状態) setValueToError():－<br>
     *         (状態) property(取得元：業務ロジック内キー名 → 反映先のキー名）:null：<br>
     *                 "blogicUserName"→ "userName"<br>
     *
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) 例外:SystemException：<br>
     *                    メッセージキー："errors.blogic.mapper.dest"<br>
     *                    ラップした例外：BLogicMapperException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：<br>
     *                    "dest is illegal."<br>
     *         (状態変化) request:－<br>
     *
     * <br>
     * 反映先にnullを指定した時、SystemExceptionが発生すること。（本来ありえない）
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testMapBLogicResult13() throws Exception {
        // 擬似リクエスト
        HttpServletRequest request = new MockHttpServletRequest();

        // 擬似レスポンス
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO設定
        BLogicIO io = new BLogicIO();
        BLogicProperty property = new BLogicProperty();
        property.setDest(null);
        property.setProperty("userName");
        property.setBLogicProperty("blogicUserName");
        io.setBLogicResult(property);

        // BLogicResult設定
        BLogicResult result = new BLogicResult();
        AbstractBLogicMapper_BeanStub01 bean = new AbstractBLogicMapper_BeanStub01();
        result.setResultObject(bean);

        // AbstractBLogicMapperImpl生成
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // テスト実施
        try {
            mapper.mapBLogicResult(request, response, io, result);
            fail();
        } catch (SystemException e) {
            // 判定
            assertEquals("errors.blogic.mapper.dest", e.getErrorCode());
            assertEquals(BLogicMapperException.class.getName(), e.getCause()
                    .getClass().getName());
            assertTrue(LogUTUtil.checkError("dest is illegal."));
        }
    }

}
