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

package jp.terasoluna.fw.web.rich.springmvc.bind.creator;


import javax.servlet.http.HttpServletRequest;
import jp.terasoluna.fw.oxm.mapper.OXMapper;
import jp.terasoluna.fw.oxm.mapper.castor.CastorOXMapperImpl;
import jp.terasoluna.fw.oxm.xsd.SchemaValidator;
import jp.terasoluna.fw.oxm.xsd.xerces.SchemaValidatorImpl;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.web.rich.springmvc.bind.creator.XMLServletRequestDataBinderCreator;
import junit.framework.TestCase;
import org.springframework.web.bind.ServletRequestDataBinder;

/**
 * {@link jp.terasoluna.fw.web.rich.springmvc.bind.creator.XMLServletRequestDataBinderCreator}
 * クラスのブラックボックステスト。
 * <p>
 * <h4>【クラスの概要】</h4>
 * XML形式で定義されたリクエストデータをバインドするクラスを生成する役割を持つクラス。<br>
 * ・前提条件<br>
 * createメソッドはコントローラから呼び出され、引数のrequest,command,requestNameがnullの状態は存在しない。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.bind.creator.XMLServletRequestDataBinderCreator
 */
public class XMLServletRequestDataBinderCreatorTest extends TestCase {

    /**
     * このテストケースを実行する為の GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner
                .run(XMLServletRequestDataBinderCreatorTest.class);
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
    public XMLServletRequestDataBinderCreatorTest(String name) {
        super(name);
    }

    /**
     * testCreate01() <br>
     * <br>
     * (正常系)<br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) request:not null<br>
     * (引数) command:not null<br>
     * (引数) requestName:not null<br>
     * (状態) this.oxmapper:not null<br>
     * <br>
     * 期待値：(戻り値) result:ServletRequestDataBinder{<br>
     * target = 引数のcommand<br>
     * oxmapper = this.oxmapper,<br>
     * schemaValidator = this.schemaValidator<br> }<br>
     * <br>
     * XML形式のバインダを生成するテスト。戻り値のXMLServletRequestDataBinderに、
     * 引数のcommandと属性のoxmapper,schemaValidatorが正常にセットされることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreate01() throws Exception {
        // 前処理
        XMLServletRequestDataBinderCreator creator = new XMLServletRequestDataBinderCreator();
        OXMapper oxmapper = new CastorOXMapperImpl();
        SchemaValidator schemaValidator = new SchemaValidatorImpl();
        HttpServletRequest request = new MockHttpServletRequest();
        Object command = new Object();
        String str = "abc";
        
        UTUtil.setPrivateField(creator, "oxmapper", oxmapper);        
        UTUtil.setPrivateField(creator, "schemaValidator", schemaValidator);
        
        // テスト実施
        ServletRequestDataBinder result = creator.create(request, command, str);
        
        assertSame(command, (UTUtil.getPrivateField(result,
        "target")));
        assertSame(oxmapper, (UTUtil.getPrivateField(result,
        "oxmapper")));
        assertSame(schemaValidator, (UTUtil.getPrivateField(result,
        "schemaValidator")));
    }

    /**
     * testCreate02() <br>
     * <br>
     * (正常系)<br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) request:not null<br>
     * (引数) command:not null<br>
     * (引数) requestName:not null<br>
     * (状態) this.oxmapper:not null<br>
     * <br>
     * 期待値：(戻り値) result:ServletRequestDataBinder{<br>
     * target = 引数のcommand<br>
     * oxmapper = this.oxmapper,<br>
     * schemaValidator = this.schemaValidator,<br> }<br>
     * <br>
     * XML形式のバインダを生成するテスト。戻り値のXMLServletRequestDataBinderに、引数のcommandと属性のoxmapper,schemaValidatorが正常にセットされることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreate02() throws Exception {
        // 前処理
        XMLServletRequestDataBinderCreator creator = new XMLServletRequestDataBinderCreator();
        OXMapper oxmapper = new CastorOXMapperImpl();
        SchemaValidator schemaValidator = null;
        HttpServletRequest request = new MockHttpServletRequest();
        Object command = new Object();
        String str = "abc";
        
        UTUtil.setPrivateField(creator, "oxmapper", oxmapper);        
        UTUtil.setPrivateField(creator, "schemaValidator", schemaValidator);
        
        // テスト実施
        ServletRequestDataBinder result = creator.create(request, command, str);
        
        assertSame(command, (UTUtil.getPrivateField(result,
        "target")));
        assertSame(oxmapper, (UTUtil.getPrivateField(result,
        "oxmapper")));
        assertNull(UTUtil.getPrivateField(result,
        "schemaValidator"));
        
    } 
    
    /**
     * testGetOxmapper01() <br>
     * <br>
     * (正常系)<br>
     * 観点：A <br>
     * <br>
     * 入力値：(状態) this.binder:not null<br>
     * <br>
     * 期待値：(戻り値) binder:not null<br>
     * <br>
     * 属性が正しく返却されることを確認する。 <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetOxmapper01() throws Exception {
        // 前処理
        XMLServletRequestDataBinderCreator creator = new XMLServletRequestDataBinderCreator();
        OXMapper oxmapper = new CastorOXMapperImpl();
        UTUtil.setPrivateField(creator, "oxmapper", oxmapper);

        // テスト実施
        OXMapper result = creator.getOxmapper();

        // 判定
        assertSame(oxmapper, result);

    }

    /**
     * testSetOxmapper01() <br>
     * <br>
     * (正常系)<br>
     * 観点：A,C <br>
     * <br>
     * 入力値：(引数) binder:not null<br>
     * (状態) this.binder:null<br>
     * <br>
     * 期待値：(状態変化) this.binder:not null<br>
     * <br>
     * 引数が属性に正しく設定されることを確認する。 <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetOxmapper01() throws Exception {
        // 前処理
        XMLServletRequestDataBinderCreator creator = new XMLServletRequestDataBinderCreator();
        UTUtil.setPrivateField(creator, "oxmapper", null);

        OXMapper oxmapper = new CastorOXMapperImpl();

        // テスト実施
        creator.setOxmapper(oxmapper);

        // 判定
        OXMapper result = ((OXMapper) UTUtil.getPrivateField(creator,
                "oxmapper"));

        assertSame(oxmapper, result);

    }

    /**
     * testGetSchemaValidator01() <br>
     * <br>
     * (正常系)<br>
     * 観点：A <br>
     * <br>
     * 入力値：(状態) this.binder:not null<br>
     * <br>
     * 期待値：(戻り値) binder:not null<br>
     * <br>
     * 属性が正しく返却されることを確認する。 <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetSchemaValidator01() throws Exception {
        // 前処理
        XMLServletRequestDataBinderCreator creator = new XMLServletRequestDataBinderCreator();
        SchemaValidator schemaValidator = new SchemaValidatorImpl();
        UTUtil.setPrivateField(creator, "schemaValidator", schemaValidator);

        // テスト実施
        SchemaValidator result = creator.getSchemaValidator();

        // 判定
        assertSame(schemaValidator, result);

    }

    /**
     * testSetSchemaValidator01() <br>
     * <br>
     * (正常系)<br>
     * 観点：A,C <br>
     * <br>
     * 入力値：(引数) binder:not null<br>
     * (状態) this.binder:null<br>
     * <br>
     * 期待値：(状態変化) this.binder:not null<br>
     * <br>
     * 引数が属性に正しく設定されることを確認する。 <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetSchemaValidator01() throws Exception {
        // 前処理
        XMLServletRequestDataBinderCreator creator = new XMLServletRequestDataBinderCreator();
        UTUtil.setPrivateField(creator, "oxmapper", null);

        SchemaValidator schemaValidator = new SchemaValidatorImpl();

        // テスト実施
        creator.setSchemaValidator(schemaValidator);

        // 判定
        SchemaValidator result = ((SchemaValidator) UTUtil.getPrivateField(creator,
                "schemaValidator"));

        assertSame(schemaValidator, result);

    }
    
    /**
     * testAfterPropertiesSet01() <br>
     * <br>
     * (異常系)<br>
     * 観点：A,C,G <br>
     * <br>
     * 入力値：(状態) this.oxmapper:null<br>
     * <br>
     * 期待値：(状態変化) 例外:IllegalStateException{<br>
     * message="OXMapper isn't set in ServletRequestDataBinder. Check
     * Spring Bean definition file."<br> }<br>
     * (状態変化) ログ:ログレベル：error<br>
     * OXMapper isn't set in ServletRequestDataBinder. Check Spring
     * Bean definition file.<br>
     * <br>
     * 本クラスがインスタンス化された直後に、oxmapper属性が設定されていない場合、例外が発生することを確認する。 <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAfterPropertiesSet01() throws Exception {
        // 前処理
        XMLServletRequestDataBinderCreator creator = new XMLServletRequestDataBinderCreator();
        UTUtil.setPrivateField(creator, "oxmapper", null);

        // テスト実施
        try {
            creator.afterPropertiesSet();
            fail();
        } catch (IllegalStateException e) {

            String message = "OXMapper isn't set in ServletRequestDataBinder. "
                    + "Check Spring Bean definition file.";

            // 判定
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message));

        }
    }

    /**
     * testAfterPropertiesSet02() <br>
     * <br>
     * (正常系)<br>
     * 観点：A<br>
     * <br>
     * 入力値：(状態) this.oxmapper:not null<br>
     * <br>
     * 期待値： <br>
     * 本クラスがインスタンス化された直後に、oxmapper属性が設定されている場合、何も行わないことを確認する。 <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAfterPropertiesSet02() throws Exception {
        // 前処理
        XMLServletRequestDataBinderCreator creator = new XMLServletRequestDataBinderCreator();
        OXMapper oxmapper = new CastorOXMapperImpl();
        UTUtil.setPrivateField(creator, "oxmapper", oxmapper);

        // テスト実施
        creator.afterPropertiesSet();

        // （例外が発生しなければTrue)
    }

}
