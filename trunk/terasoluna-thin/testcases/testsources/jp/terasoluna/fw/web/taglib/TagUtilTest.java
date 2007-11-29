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
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import com.mockrunner.mock.web.MockServletConfig;

import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpSession;
import jp.terasoluna.utlib.MockServletContext;
import jp.terasoluna.utlib.TagUTUtil;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.utlib.exception.Exception_JspWriterImpl;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.taglib.TagUtil} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * カスタムタグ機能全般に通じるユーティリティクラス。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.taglib.TagUtil
 */
@SuppressWarnings("unused")
public class TagUtilTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(TagUtilTest.class);
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
    public TagUtilTest(String name) {
        super(name);
    }

    /**
     * testGetScope01()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) scopeName:null<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:JspException<br>
     *         
     * <br>
     * scopeNameがnullの時、例外をスローすること。※lookupメソッドのテストケースで内包できないパターン
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetScope01() throws Exception {
        // テスト実施
        try {
            int result = TagUtil.getScope(null);
            fail();
        } catch (JspException e) {
           // 判定 
        }
    }

    /**
     * testLookupPageContextStringStringString01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) pageContext:not null<br>
     *         (引数) name:"name"<br>
     *         (引数) property:null<br>
     *         (引数) scopeName:null<br>
     *         (状態) pageContext.attributes.get(name):TagUtil_BeanStub01[param1="page"]<br>
     *         (状態) pageContext.request.getAttribute(name):TagUtil_BeanStub01[param1="request"]<br>
     *         (状態) pageContext.session.getAttribute(name):TagUtil_BeanStub01[param1="session"]<br>
     *         (状態) pageContext.application.getAttribute(name):TagUtil_BeanStub01[param1="application"]<br>
     *         
     * <br>
     * 期待値：(戻り値) Object:TagUtil_BeanStub01[param1="page"]<br>
     *         
     * <br>
     * propertyがnull、scopeNameがnullの時、pageScopeから取得したObjectを返却すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLookupPageContextStringStringString01() throws Exception {
        // 前処理
        // TagUTUtil#getPageContext()を使用するため適当なtagを利用する
        TagSupport tag = TagUTUtil.create(TagSupport.class);
        PageContext pc = TagUTUtil.getPageContext(tag);
        
        // bean生成
        TagUtil_BeanStub01 bean1 = new TagUtil_BeanStub01(); 
        bean1.setParam1("page");
        TagUtil_BeanStub01 bean2 = new TagUtil_BeanStub01(); 
        bean2.setParam1("request");
        TagUtil_BeanStub01 bean3 = new TagUtil_BeanStub01(); 
        bean3.setParam1("session");
        TagUtil_BeanStub01 bean4 = new TagUtil_BeanStub01(); 
        bean4.setParam1("application");
        
        // beanをsetAttribute
        HashMap<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("name", bean1);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute("name", bean2);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("name", bean3);
        request.setSession(session);
        MockServletContext application = new MockServletContext();
        application.setAttribute("name", bean4);
        
        // PageContextに設定
        UTUtil.setPrivateField(pc, "attributes", attributes);
        UTUtil.setPrivateField(pc, "request", request);
        MockServletConfig config = new MockServletConfig();
        config.setServletContext(application);
        UTUtil.setPrivateField(pc, "config", config);
        
        // テスト実施
        Object result = TagUtil.lookup(pc, "name", null, null);
        
        // 判定
        assertEquals("page", ((TagUtil_BeanStub01) result).getParam1());
    }

    /**
     * testLookupPageContextStringStringString02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) pageContext:not null<br>
     *         (引数) name:"name"<br>
     *         (引数) property:null<br>
     *         (引数) scopeName:"page"<br>
     *         (状態) pageContext.attributes.get(name):TagUtil_BeanStub01[param1="page"]<br>
     *         (状態) pageContext.request.getAttribute(name):TagUtil_BeanStub01[param1="request"]<br>
     *         (状態) pageContext.session.getAttribute(name):TagUtil_BeanStub01[param1="session"]<br>
     *         (状態) pageContext.application.getAttribute(name):TagUtil_BeanStub01[param1="application"]<br>
     *         
     * <br>
     * 期待値：(戻り値) Object:TagUtil_BeanStub01[param1="page"]<br>
     *         
     * <br>
     * propertyがnull、scopeNameが"page"の時、pageScopeから取得したObjectを返却すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLookupPageContextStringStringString02() throws Exception {
        // 前処理
        // TagUTUtil#getPageContext()を使用するため適当なtagを利用する
        TagSupport tag = TagUTUtil.create(TagSupport.class);
        PageContext pc = TagUTUtil.getPageContext(tag);
        
        // bean生成
        TagUtil_BeanStub01 bean1 = new TagUtil_BeanStub01(); 
        bean1.setParam1("page");
        TagUtil_BeanStub01 bean2 = new TagUtil_BeanStub01(); 
        bean2.setParam1("request");
        TagUtil_BeanStub01 bean3 = new TagUtil_BeanStub01(); 
        bean3.setParam1("session");
        TagUtil_BeanStub01 bean4 = new TagUtil_BeanStub01(); 
        bean4.setParam1("application");
        
        // beanをsetAttribute
        HashMap<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("name", bean1);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute("name", bean2);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("name", bean3);
        request.setSession(session);
        MockServletContext application = new MockServletContext();
        application.setAttribute("name", bean4);
        
        // PageContextに設定
        UTUtil.setPrivateField(pc, "attributes", attributes);
        UTUtil.setPrivateField(pc, "request", request);
        MockServletConfig config = new MockServletConfig();
        config.setServletContext(application);
        UTUtil.setPrivateField(pc, "config", config);
        
        // テスト実施
        Object result = TagUtil.lookup(pc, "name", null, "page");
        
        // 判定
        assertEquals("page", ((TagUtil_BeanStub01) result).getParam1());
    }

    /**
     * testLookupPageContextStringStringString03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) pageContext:not null<br>
     *         (引数) name:"name"<br>
     *         (引数) scopeName:"page"<br>
     *         (状態) pageContext.attributes.get(name):null<br>
     *         (状態) pageContext.request.getAttribute(name):TagUtil_BeanStub01[param1="request"]<br>
     *         (状態) pageContext.session.getAttribute(name):TagUtil_BeanStub01[param1="session"]<br>
     *         (状態) pageContext.application.getAttribute(name):TagUtil_BeanStub01[param1="application"]<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:JspException<br>
     *         
     * <br>
     * propertyがnull、scopeNameが"page"の時、pageScopeにbeanが格納されてなければ、例外が発生すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLookupPageContextStringStringString03() throws Exception {
        // 前処理
        // TagUTUtil#getPageContext()を使用するため適当なtagを利用する
        TagSupport tag = TagUTUtil.create(TagSupport.class);
        PageContext pc = TagUTUtil.getPageContext(tag);
        
        // bean生成
        TagUtil_BeanStub01 bean2 = new TagUtil_BeanStub01(); 
        bean2.setParam1("request");
        TagUtil_BeanStub01 bean3 = new TagUtil_BeanStub01(); 
        bean3.setParam1("session");
        TagUtil_BeanStub01 bean4 = new TagUtil_BeanStub01(); 
        bean4.setParam1("application");
        
        // beanをsetAttribute
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute("name", bean2);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("name", bean3);
        request.setSession(session);
        MockServletContext application = new MockServletContext();
        application.setAttribute("name", bean4);
        
        // PageContextに設定
        UTUtil.setPrivateField(pc, "request", request);
        MockServletConfig config = new MockServletConfig();
        config.setServletContext(application);
        UTUtil.setPrivateField(pc, "config", config);
        
        // テスト実施
        try {
            Object result = TagUtil.lookup(pc, "name", null, "page");
        } catch (JspException e) {
            // 判定
        }
    }

    /**
     * testLookupPageContextStringStringString04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) pageContext:not null<br>
     *         (引数) name:"name"<br>
     *         (引数) property:null<br>
     *         (引数) scopeName:"request"<br>
     *         (状態) pageContext.attributes.get(name):TagUtil_BeanStub01[param1="page"]<br>
     *         (状態) pageContext.request.getAttribute(name):TagUtil_BeanStub01[param1="request"]<br>
     *         (状態) pageContext.session.getAttribute(name):TagUtil_BeanStub01[param1="session"]<br>
     *         (状態) pageContext.application.getAttribute(name):TagUtil_BeanStub01[param1="application"]<br>
     *         
     * <br>
     * 期待値：(戻り値) Object:TagUtil_BeanStub01[param1="request"]<br>
     *         
     * <br>
     * propertyがnull、scopeNameが"request"の時、requestScopeから取得したObjectを返却すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLookupPageContextStringStringString04() throws Exception {
        // 前処理
        // TagUTUtil#getPageContext()を使用するため適当なtagを利用する
        TagSupport tag = TagUTUtil.create(TagSupport.class);
        PageContext pc = TagUTUtil.getPageContext(tag);
        
        // bean生成
        TagUtil_BeanStub01 bean1 = new TagUtil_BeanStub01(); 
        bean1.setParam1("page");
        TagUtil_BeanStub01 bean2 = new TagUtil_BeanStub01(); 
        bean2.setParam1("request");
        TagUtil_BeanStub01 bean3 = new TagUtil_BeanStub01(); 
        bean3.setParam1("session");
        TagUtil_BeanStub01 bean4 = new TagUtil_BeanStub01(); 
        bean4.setParam1("application");
        
        // beanをsetAttribute
        HashMap<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("name", bean1);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute("name", bean2);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("name", bean3);
        request.setSession(session);
        MockServletContext application = new MockServletContext();
        application.setAttribute("name", bean4);
        
        // PageContextに設定
        UTUtil.setPrivateField(pc, "attributes", attributes);
        UTUtil.setPrivateField(pc, "request", request);
        MockServletConfig config = new MockServletConfig();
        config.setServletContext(application);
        UTUtil.setPrivateField(pc, "config", config);
        
        // テスト実施
        Object result = TagUtil.lookup(pc, "name", null, "request");
        
        // 判定
        assertEquals("request", ((TagUtil_BeanStub01) result).getParam1());
    }

    /**
     * testLookupPageContextStringStringString05()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) pageContext:not null<br>
     *         (引数) name:"name"<br>
     *         (引数) scopeName:"request"<br>
     *         (状態) pageContext.attributes.get(name):TagUtil_BeanStub01[param1="page"]<br>
     *         (状態) pageContext.request.getAttribute(name):null<br>
     *         (状態) pageContext.session.getAttribute(name):TagUtil_BeanStub01[param1="session"]<br>
     *         (状態) pageContext.application.getAttribute(name):TagUtil_BeanStub01[param1="application"]<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:JspException<br>
     *         
     * <br>
     * propertyがnull、scopeNameが"request"の時、requestScopeにbeanが格納されてなければ、例外が発生すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLookupPageContextStringStringString05() throws Exception {
        // 前処理
        // TagUTUtil#getPageContext()を使用するため適当なtagを利用する
        TagSupport tag = TagUTUtil.create(TagSupport.class);
        PageContext pc = TagUTUtil.getPageContext(tag);
        
        // bean生成
        TagUtil_BeanStub01 bean1 = new TagUtil_BeanStub01(); 
        bean1.setParam1("page");
        TagUtil_BeanStub01 bean3 = new TagUtil_BeanStub01(); 
        bean3.setParam1("session");
        TagUtil_BeanStub01 bean4 = new TagUtil_BeanStub01(); 
        bean4.setParam1("application");
        
        // beanをsetAttribute
        HashMap<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("name", bean1);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("name", bean3);
        request.setSession(session);
        MockServletContext application = new MockServletContext();
        application.setAttribute("name", bean4);
        
        // PageContextに設定
        UTUtil.setPrivateField(pc, "attributes", attributes);
        MockServletConfig config = new MockServletConfig();
        config.setServletContext(application);
        UTUtil.setPrivateField(pc, "config", config);
        
        // テスト実施
        try {
            Object result = TagUtil.lookup(pc, "name", null, "request");
        } catch (JspException e) {
            // 判定
        }
    }

    /**
     * testLookupPageContextStringStringString06()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) pageContext:not null<br>
     *         (引数) name:"name"<br>
     *         (引数) property:null<br>
     *         (引数) scopeName:"session"<br>
     *         (状態) pageContext.attributes.get(name):TagUtil_BeanStub01[param1="page"]<br>
     *         (状態) pageContext.request.getAttribute(name):TagUtil_BeanStub01[param1="request"]<br>
     *         (状態) pageContext.session.getAttribute(name):TagUtil_BeanStub01[param1="session"]<br>
     *         (状態) pageContext.application.getAttribute(name):TagUtil_BeanStub01[param1="application"]<br>
     *         
     * <br>
     * 期待値：(戻り値) Object:TagUtil_BeanStub01[param1="session"]<br>
     *         
     * <br>
     * propertyがnull、scopeNameが"session"の時、sessionScopeから取得したObjectを返却すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLookupPageContextStringStringString06() throws Exception {
        // 前処理
        // TagUTUtil#getPageContext()を使用するため適当なtagを利用する
        TagSupport tag = TagUTUtil.create(TagSupport.class);
        PageContext pc = TagUTUtil.getPageContext(tag);
        
        // bean生成
        TagUtil_BeanStub01 bean1 = new TagUtil_BeanStub01(); 
        bean1.setParam1("page");
        TagUtil_BeanStub01 bean2 = new TagUtil_BeanStub01(); 
        bean2.setParam1("request");
        TagUtil_BeanStub01 bean3 = new TagUtil_BeanStub01(); 
        bean3.setParam1("session");
        TagUtil_BeanStub01 bean4 = new TagUtil_BeanStub01(); 
        bean4.setParam1("application");
        
        // beanをsetAttribute
        HashMap<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("name", bean1);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute("name", bean2);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("name", bean3);
        request.setSession(session);
        MockServletContext application = new MockServletContext();
        application.setAttribute("name", bean4);
        
        // PageContextに設定
        UTUtil.setPrivateField(pc, "attributes", attributes);
        UTUtil.setPrivateField(pc, "request", request);
        MockServletConfig config = new MockServletConfig();
        config.setServletContext(application);
        UTUtil.setPrivateField(pc, "config", config);
        
        // テスト実施
        Object result = TagUtil.lookup(pc, "name", null, "session");
        
        // 判定
        assertEquals("session", ((TagUtil_BeanStub01) result).getParam1());
    }

    /**
     * testLookupPageContextStringStringString07()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) pageContext:not null<br>
     *         (引数) name:"name"<br>
     *         (引数) scopeName:"session"<br>
     *         (状態) pageContext.attributes.get(name):TagUtil_BeanStub01[param1="page"]<br>
     *         (状態) pageContext.request.getAttribute(name):TagUtil_BeanStub01[param1="request"]<br>
     *         (状態) pageContext.session.getAttribute(name):null<br>
     *         (状態) pageContext.application.getAttribute(name):TagUtil_BeanStub01[param1="application"]<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:JspException<br>
     *         
     * <br>
     * propertyがnull、scopeNameが"session"の時、sessionScopeにbeanが格納されてなければ、例外が発生すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLookupPageContextStringStringString07() throws Exception {
        // 前処理
        // TagUTUtil#getPageContext()を使用するため適当なtagを利用する
        TagSupport tag = TagUTUtil.create(TagSupport.class);
        PageContext pc = TagUTUtil.getPageContext(tag);
        
        // bean生成
        TagUtil_BeanStub01 bean1 = new TagUtil_BeanStub01(); 
        bean1.setParam1("page");
        TagUtil_BeanStub01 bean2 = new TagUtil_BeanStub01(); 
        bean2.setParam1("request");
        TagUtil_BeanStub01 bean4 = new TagUtil_BeanStub01(); 
        bean4.setParam1("application");
        
        // beanをsetAttribute
        HashMap<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("name", bean1);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute("name", bean2);
        MockServletContext application = new MockServletContext();
        application.setAttribute("name", bean4);
        
        // PageContextに設定
        UTUtil.setPrivateField(pc, "attributes", attributes);
        UTUtil.setPrivateField(pc, "request", request);
        MockServletConfig config = new MockServletConfig();
        config.setServletContext(application);
        UTUtil.setPrivateField(pc, "config", config);
        
        // テスト実施
        try {
            Object result = TagUtil.lookup(pc, "name", null, "session");
        } catch (JspException e) {
            // 判定
        }
    }

    /**
     * testLookupPageContextStringStringString08()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) pageContext:not null<br>
     *         (引数) name:"name"<br>
     *         (引数) property:null<br>
     *         (引数) scopeName:"application"<br>
     *         (状態) pageContext.attributes.get(name):TagUtil_BeanStub01[param1="page"]<br>
     *         (状態) pageContext.request.getAttribute(name):TagUtil_BeanStub01[param1="request"]<br>
     *         (状態) pageContext.session.getAttribute(name):TagUtil_BeanStub01[param1="session"]<br>
     *         (状態) pageContext.application.getAttribute(name):TagUtil_BeanStub01[param1="application"]<br>
     *         
     * <br>
     * 期待値：(戻り値) Object:TagUtil_BeanStub01[param1="application"]<br>
     *         
     * <br>
     * propertyがnull、scopeNameが"application"の時、applicationScopeから取得したObjectを返却すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLookupPageContextStringStringString08() throws Exception {
        // 前処理
        // TagUTUtil#getPageContext()を使用するため適当なtagを利用する
        TagSupport tag = TagUTUtil.create(TagSupport.class);
        PageContext pc = TagUTUtil.getPageContext(tag);
        
        // bean生成
        TagUtil_BeanStub01 bean1 = new TagUtil_BeanStub01(); 
        bean1.setParam1("page");
        TagUtil_BeanStub01 bean2 = new TagUtil_BeanStub01(); 
        bean2.setParam1("request");
        TagUtil_BeanStub01 bean3 = new TagUtil_BeanStub01(); 
        bean3.setParam1("session");
        TagUtil_BeanStub01 bean4 = new TagUtil_BeanStub01(); 
        bean4.setParam1("application");
        
        // beanをsetAttribute
        HashMap<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("name", bean1);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute("name", bean2);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("name", bean3);
        request.setSession(session);
        MockServletContext application = new MockServletContext();
        application.setAttribute("name", bean4);
        
        // PageContextに設定
        UTUtil.setPrivateField(pc, "attributes", attributes);
        UTUtil.setPrivateField(pc, "request", request);
        MockServletConfig config = new MockServletConfig();
        config.setServletContext(application);
        UTUtil.setPrivateField(pc, "config", config);
        
        // テスト実施
        Object result = TagUtil.lookup(pc, "name", null, "application");
        
        // 判定
        assertEquals("application", ((TagUtil_BeanStub01) result).getParam1());
    }

    /**
     * testLookupPageContextStringStringString09()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(引数) pageContext:not null<br>
     *         (引数) name:"name"<br>
     *         (引数) scopeName:"application"<br>
     *         (状態) pageContext.attributes.get(name):TagUtil_BeanStub01[param1="page"]<br>
     *         (状態) pageContext.request.getAttribute(name):TagUtil_BeanStub01[param1="request"]<br>
     *         (状態) pageContext.session.getAttribute(name):TagUtil_BeanStub01[param1="session]"]<br>
     *         (状態) pageContext.application.getAttribute(name):null<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:JspException<br>
     *         
     * <br>
     * propertyがnull、scopeNameが"application"の時、applicationScopeにbeanが格納されてなければ、例外が発生すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLookupPageContextStringStringString09() throws Exception {
        // 前処理
        // TagUTUtil#getPageContext()を使用するため適当なtagを利用する
        TagSupport tag = TagUTUtil.create(TagSupport.class);
        PageContext pc = TagUTUtil.getPageContext(tag);
        
        // bean生成
        TagUtil_BeanStub01 bean1 = new TagUtil_BeanStub01(); 
        bean1.setParam1("page");
        TagUtil_BeanStub01 bean2 = new TagUtil_BeanStub01(); 
        bean2.setParam1("request");
        TagUtil_BeanStub01 bean3 = new TagUtil_BeanStub01(); 
        bean3.setParam1("session");
        
        // beanをsetAttribute
        HashMap<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("name", bean1);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute("name", bean2);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("name", bean3);
        request.setSession(session);
        
        // PageContextに設定
        UTUtil.setPrivateField(pc, "attributes", attributes);
        UTUtil.setPrivateField(pc, "request", request);
        
        // テスト実施
        try {
            Object result = TagUtil.lookup(pc, "name", null, "application");
        } catch (JspException e) {
            // 判定
        }
    }

    /**
     * testLookupPageContextStringStringString10()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) pageContext:not null<br>
     *         (引数) scopeName:"nothing"<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:JspException<br>
     *         
     * <br>
     * scopeNameが想定外の文字列の時、例外が発生すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLookupPageContextStringStringString10() throws Exception {
        // 前処理
        // TagUTUtil#getPageContext()を使用するため適当なtagを利用する
        TagSupport tag = TagUTUtil.create(TagSupport.class);
        PageContext pc = TagUTUtil.getPageContext(tag);
        
        // テスト実施
        try {
            Object result = TagUtil.lookup(pc, "name", null, "nothing");
        } catch (JspException e) {
            // 判定
        }
    }

    /**
     * testLookupPageContextStringStringString11()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) pageContext:not null<br>
     *         (引数) name:"name"<br>
     *         (引数) property:"param1"<br>
     *         (引数) scopeName:"APPLICATION"<br>
     *         (状態) pageContext.application.getAttribute(name):TagUtil_BeanStub01[param1="application"]<br>
     *         
     * <br>
     * 期待値：(戻り値) Object:"application"<br>
     *         
     * <br>
     * beanを取得でき、propertyに指定されたフィールドが存在する時、そのフィールド値を返却すること。また、scopeNameが大文字でも正常終了すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLookupPageContextStringStringString11() throws Exception {
        // 前処理
        // TagUTUtil#getPageContext()を使用するため適当なtagを利用する
        TagSupport tag = TagUTUtil.create(TagSupport.class);
        PageContext pc = TagUTUtil.getPageContext(tag);
        
        // bean生成
        TagUtil_BeanStub01 bean4 = new TagUtil_BeanStub01(); 
        bean4.setParam1("application");
        
        // beanをsetAttribute
        MockServletContext application = new MockServletContext();
        application.setAttribute("name", bean4);
        
        // PageContextに設定
        MockServletConfig config = new MockServletConfig();
        config.setServletContext(application);
        UTUtil.setPrivateField(pc, "config", config);
        
        // テスト実施
        Object result = TagUtil.lookup(pc, "name", "param1", "APPLICATION");
        
        // 判定
        assertEquals("application", result);
    }

    /**
     * testLookupPageContextStringStringString12()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) pageContext:not null<br>
     *         (引数) name:"name"<br>
     *         (引数) property:"param2"<br>
     *         (引数) scopeName:"application"<br>
     *         (状態) pageContext.application.getAttribute(name):TagUtil_BeanStub01[getParam2でException発生]<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:JspException：<br>
     *                    ラップした例外：InvocatinTargetException<br>
     *         
     * <br>
     * beanを取得でき、propertyに指定されたフィールド値取得時に例外が発生した時、JspExceptionでInvocatinTargetExceptionをラップしてスローすること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLookupPageContextStringStringString12() throws Exception {
        // 前処理
        // TagUTUtil#getPageContext()を使用するため適当なtagを利用する
        TagSupport tag = TagUTUtil.create(TagSupport.class);
        PageContext pc = TagUTUtil.getPageContext(tag);
        
        // bean生成
        TagUtil_BeanStub01 bean4 = new TagUtil_BeanStub01(); 
        bean4.setParam1("application");
        
        // beanをsetAttribute
        MockServletContext application = new MockServletContext();
        application.setAttribute("name", bean4);
        
        // PageContextに設定
        MockServletConfig config = new MockServletConfig();
        config.setServletContext(application);
        UTUtil.setPrivateField(pc, "config", config);
        
        // テスト実施
        try {
            Object result = TagUtil.lookup(pc, "name", "param2", "application");
        } catch (JspException e) {
            // 判定
            assertEquals(InvocationTargetException.class.getName(), e
                    .getRootCause().getClass().getName());
        }
    }
    
    /**
     * testLookupPageContextStringStringString13()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) pageContext:not null<br>
     *         (引数) name:"name"<br>
     *         (引数) property:"param3"<br>
     *         (引数) scopeName:"application"<br>
     *         (状態) pageContext.application.getAttribute(name):TagUtil_BeanStub01[param3存在しない]<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:JspException：<br>
     *                    ラップした例外：NoSuchMethodException<br>
     *         
     * <br>
     * beanを取得でき、propertyに指定されたフィールドが存在しない時、JspExceptionでNoSuchMethodExceptionをラップしてスローすること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLookupPageContextStringStringString13() throws Exception {
        // 前処理
        // TagUTUtil#getPageContext()を使用するため適当なtagを利用する
        TagSupport tag = TagUTUtil.create(TagSupport.class);
        PageContext pc = TagUTUtil.getPageContext(tag);
        
        // bean生成
        TagUtil_BeanStub01 bean4 = new TagUtil_BeanStub01(); 
        bean4.setParam1("application");
        
        // beanをsetAttribute
        MockServletContext application = new MockServletContext();
        application.setAttribute("name", bean4);
        
        // PageContextに設定
        MockServletConfig config = new MockServletConfig();
        config.setServletContext(application);
        UTUtil.setPrivateField(pc, "config", config);
        
        // テスト実施
        try {
            Object result = TagUtil.lookup(pc, "name", "param4", "application");
        } catch (JspException e) {
            // 判定
            assertEquals(NoSuchMethodException.class.getName(), e
                    .getRootCause().getClass().getName());
        }
    }

    /**
     * testWrite01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) pageContext:not null<br>
     *         (引数) text:"text"<br>
     *         
     * <br>
     * 期待値：(状態変化) 出力内容:"text"<br>
     *         
     * <br>
     * 正しく出力されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testWrite01() throws Exception {
        // 前処理
        // TagUTUtil#getPageContext()を使用するため適当なtagを利用する
        TagSupport tag = TagUTUtil.create(TagSupport.class);
        PageContext pc = TagUTUtil.getPageContext(tag);

        // テスト実施
        TagUtil.write(pc, "text");

        // 判定
        assertEquals("text", pc.getOut().toString());
    }

    /**
     * testWrite02()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) pageContext:writer.print(text)実行時にIOExceptionが発生する<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:JspException：<br>
     *                    ラップした例外：IOException<br>
     *         
     * <br>
     * writerでIOExceptionが発生した時、JspExceptionでラップしてスローすること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testWrite02() throws Exception {
        // 前処理
        // TagUTUtil#getPageContext()を使用するため適当なtagを利用する
        TagSupport tag = TagUTUtil.create(TagSupport.class);
        PageContext pc = TagUTUtil.getPageContext(tag);

        // IOExceptionが発生するJspWriterを生成
        Exception_JspWriterImpl writer = new Exception_JspWriterImpl();
        writer.setTrue();
        UTUtil.setPrivateField(pc, "jspWriter", writer);

        // テスト実施
        try {
            TagUtil.write(pc, "text");
        } catch (JspException e) {
            // 判定
            assertEquals(IOException.class.getName(), e.getRootCause()
                    .getClass().getName());
        }
    }

    /**
     * testWriteln01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) pageContext:not null<br>
     *         (引数) text:"text(改行あり)"<br>
     *         
     * <br>
     * 期待値：(状態変化) 出力内容:"text(改行あり)"<br>
     *         
     * <br>
     * 正しく出力されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testWriteln01() throws Exception {
        // 前処理
        // TagUTUtil#getPageContext()を使用するため適当なtagを利用する
        TagSupport tag = TagUTUtil.create(TagSupport.class);
        PageContext pc = TagUTUtil.getPageContext(tag);

        // テスト実施
        TagUtil.writeln(pc, "text");

        // 判定
        String lineSeparator = System.getProperty("line.separator");
        assertEquals("text" + lineSeparator, pc.getOut().toString());
    }

    /**
     * testWriteln02()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) pageContext:writer.print(text)実行時にIOExceptionが発生する<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:JspException：<br>
     *                    ラップした例外：IOException<br>
     *         
     * <br>
     * writerでIOExceptionが発生した時、JspExceptionでラップしてスローすること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testWriteln02() throws Exception {
        // 前処理
        // TagUTUtil#getPageContext()を使用するため適当なtagを利用する
        TagSupport tag = TagUTUtil.create(TagSupport.class);
        PageContext pc = TagUTUtil.getPageContext(tag);

        // IOExceptionが発生するJspWriterを生成
        Exception_JspWriterImpl writer = new Exception_JspWriterImpl();
        writer.setTrue();
        UTUtil.setPrivateField(pc, "jspWriter", writer);
        
        // テスト実施
        try {
            TagUtil.writeln(pc, "text");
        } catch (JspException e) {
            // 判定
            assertEquals(IOException.class.getName(), e.getRootCause()
                    .getClass().getName());
        }
    }

    /**
     * testFilter01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) value:null<br>
     *         
     * <br>
     * 期待値：(戻り値) String:null<br>
     *         
     * <br>
     * 引数valueがnullの時、nullを返却すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testFilter01() throws Exception {
        // テスト実施
        String result = TagUtil.filter(null);
        // 判定
        assertNull(result);
    }

    /**
     * testFilter02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) value:""（空文字）<br>
     *         
     * <br>
     * 期待値：(戻り値) String:""（空文字）<br>
     *         
     * <br>
     * 引数valueが空文字の時、空文字を返却すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testFilter02() throws Exception {
        // テスト実施
        String result = TagUtil.filter("");
        // 判定
        assertEquals("", result);
    }

    /**
     * testFilter03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) value:あ<い>う&え"お'<br>
     *         
     * <br>
     * 期待値：(戻り値) String:あ&lt;い&gt;う&amp;え&quot;お&#39;<br>
     *         
     * <br>
     * 引数valueにフィルター対象文字列がある場合、フィルターして返却すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testFilter03() throws Exception {
        // テスト実施
        String result = TagUtil.filter("あ<い>う&え\"お'");
        // 判定
        assertEquals("あ&lt;い&gt;う&amp;え&quot;お&#39;", result);
    }

}
