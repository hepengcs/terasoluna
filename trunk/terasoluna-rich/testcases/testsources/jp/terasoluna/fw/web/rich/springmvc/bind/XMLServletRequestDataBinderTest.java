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

package jp.terasoluna.fw.web.rich.springmvc.bind;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletInputStream;
import jp.terasoluna.fw.oxm.mapper.OXMapper;
import jp.terasoluna.fw.oxm.mapper.castor.CastorOXMapperImpl;
import jp.terasoluna.fw.oxm.xsd.SchemaValidator;
import jp.terasoluna.fw.oxm.xsd.message.ErrorMessages;
import jp.terasoluna.fw.oxm.xsd.xerces.SchemaValidatorImpl;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.web.rich.springmvc.bind.XMLServletRequestDataBinder;
import jp.terasoluna.fw.web.rich.springmvc.bind.creator.exception.XMLRequestIOException;
import junit.framework.TestCase;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.w3c.dom.Document;
import com.sun.org.apache.xerces.internal.dom.DocumentImpl;

/**
 * {@link jp.terasoluna.fw.web.rich.springmvc.bind.XMLServletRequestDataBinder}
 * クラスのブラックボックステスト。
 * <p>
 * <h4>【クラスの概要】</h4>
 * XML形式のリクエストデータをコマンドオブジェクトにバインドするクラス。<br>
 * 前提条件：creatorから生成されるため、コンストラクタの引数target,oxmapperがnullの状態は存在しない。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.bind.XMLServletRequestDataBinder
 */
public class XMLServletRequestDataBinderTest extends TestCase {

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
    public XMLServletRequestDataBinderTest(String name) {
        super(name);
    }

    /**
     * testXMLServletRequestDataBinder01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) target:not null<br>
     *         (引数) objectName:not null<br>
     *         (引数) oxmapper:not null<br>
     *         (引数) schemaValidator:not null<br>
     *         
     * <br>
     * 期待値：(状態変化) this.bindingResult.target:not null<br>
     *         (状態変化) this.oxmapper:not null<br>
     *         (状態変化) this.schemaValidator:not null<br>
     *         
     * <br>
     * 引数の値が正常に属性に設定されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testXMLServletRequestDataBinder01() throws Exception {
        // 前処理
        OXMapper oxmapper = new CastorOXMapperImpl();
        SchemaValidator schemaValidator = new SchemaValidatorImpl();
        Object target = new Object();
        String objectName = "";

        // テスト実施
        XMLServletRequestDataBinder servletDataBinder = new XMLServletRequestDataBinder(
                target, oxmapper, schemaValidator, objectName);

        // 判定        
        // 引数が属性に設定されたことの確認
        assertSame(oxmapper, UTUtil.getPrivateField(
                servletDataBinder, "oxmapper"));
        assertSame(schemaValidator, UTUtil.getPrivateField(
                servletDataBinder, "schemaValidator"));
        // 呼び出し確認
        BindingResult errors = servletDataBinder.getBindingResult();
        assertSame(target, errors.getTarget());
    }

    /**
     * testXMLServletRequestDataBinder02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,C
     * <br><br>
     * 入力値：(引数) target:not null<br>
     *         (引数) oxmapper:not null<br>
     *         (引数) schemaValidator:null<br>
     *         (引数) objectName:not null<br>
     *         
     * <br>
     * 期待値：(状態変化) this.bindingResult.target:not null<br>
     *         (状態変化) this.oxmapper:not null<br>
     *         (状態変化) this.schemaValidator:null<br>
     *         
     * <br>
     * 引数の値が正常に属性に設定されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testXMLServletRequestDataBinder02() throws Exception {
        // 前処理
        OXMapper oxmapper = new CastorOXMapperImpl();
        SchemaValidator schemaValidator = null;
        Object target = new Object();
        String objectName = "target";

        // テスト実施
        XMLServletRequestDataBinder servletDataBinder = new XMLServletRequestDataBinder(
                target, oxmapper, schemaValidator, objectName);

        // 判定
        // 引数が属性に設定されたことの確認
        assertSame(oxmapper, UTUtil.getPrivateField(servletDataBinder, "oxmapper"));
        assertNull(UTUtil.getPrivateField(servletDataBinder, "schemaValidator"));
        // 呼び出し確認
        BindingResult errors = servletDataBinder.getBindingResult();
        assertSame(target, errors.getTarget());
    }

    /**
     * testBind01()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：A,G
     * <br><br>
     * 入力値：(引数) request:不正な入力ストリーム<br>
     *         (状態) this.oxmapper:not null<br>
     *         (状態) this.schemaValidator:not null<br>
     *         (状態) this.bindingResult.target:not null<br>
     *         (状態) this.bindingResult.errors:要素数0のerrors<br>
     *         (状態) request.servletInputStream:not null<br>
     *         (状態) request.getInputStream():IOExceptionをスローする<br>
     *         (状態) request.env:not null<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:XMLRequestIOException{<br>
     *                      cause = IOException<br>
     *                    }<br>
     *         (状態変化) ログ:ログレベル：error<br>
     *                    Request stream is Invalid.<br>
     *         
     * <br>
     * request.getInputStream()メソッドの実行時にIO例外が発生した場合、XMLRequestIOExceptionがスローされ、ログが出力されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testBind01() throws Exception {
        // 前処理
        OXMapper oxmapper = null;
        SchemaValidator schemaValidator = null;
        Object target = new Object();
        String objectName = "target";
        
        XMLServletRequestDataBinder servletDataBinder = new XMLServletRequestDataBinder(
                target, oxmapper, schemaValidator, objectName);

        XMLServletRequestDataBinder_MockHttpServletRequestStub01 request = new XMLServletRequestDataBinder_MockHttpServletRequestStub01();

        try {
            // テスト実施
            servletDataBinder.bind(request);
            fail();
        } catch (XMLRequestIOException e) {
            // 判定
            assertSame(IOException.class, e.getCause().getClass());

            // ログ確認
            String message = "Request stream error.";
            assertTrue(LogUTUtil.checkError(message, e.getCause()));
            
        }
    }

    /**
     * testBind02()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A,C
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (状態) this.oxmapper:not null<br>
     *         (状態) this.schemaValidator:null<br>
     *         (状態) this.bindingResult.target:not null<br>
     *         (状態) this.bindingResult.errors:要素数0のerrors<br>
     *         (状態) request.servletInputStream:null<br>
     *         (状態) request.env:not null<br>
     *         (状態) oxmapper.unmarshal(InputStream, String, Object):呼び出されたことの確認を行う。<br>
     *                引数が渡されたことの確認を行う。<br>
     *         
     * <br>
     * 期待値：
     * <br>
     * 属性のschemaValidatorがnullの場合、unmarshal(InputStream, String, Object)メソッドが実行されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testBind02() throws Exception {
        // 前処理
        XMLServletRequestDataBinder_OXMapperStub01 oxmapper = new XMLServletRequestDataBinder_OXMapperStub01();
        SchemaValidator schemaValidator = null;
        Object out = new Object();
        String objectName = "target";
        
        XMLServletRequestDataBinder servletDataBinder = new XMLServletRequestDataBinder(
                out, oxmapper, schemaValidator, objectName);

        MockHttpServletRequest request = new MockHttpServletRequest();
        ServletInputStream inputStream = null;
        request.setInputStream(inputStream);
        request.setCharacterEncoding("abc");

        // テスト実施
        servletDataBinder.bind(request);

        // 判定
        // 呼び出し確認
        assertNull(oxmapper.is);
        assertEquals("abc", oxmapper.argCharset);
        assertSame(out, oxmapper.out);

    }

    /**
     * testBind03()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：A,G
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (状態) this.oxmapper:not null<br>
     *         (状態) this.schemaValidator:not null<br>
     *         (状態) this.bindingResult.target:not null<br>
     *         (状態) this.bindingResult.errors:要素数0のerrors<br>
     *         (状態) request.servletInputStream:not null<br>
     *         (状態) request.env:not null<br>
     *         (状態) validate():呼び出されたことの確認を行う。<br>
     *                引数が渡されたことの確認を行う。<br>
     *         (状態) in.close():IOExceptionをスローする<br>
     *         (状態) oxmapper.unmarshal(Document, Object):呼び出されたことの確認を行う。<br>
     *                引数が渡されたことの確認を行う。<br>
     *         
     * <br>
     * 期待値：(状態変化) ログ:ログレベル：error<br>
     *                    "Failed to close request stream.", IOException<br>
     *         
     * <br>
     * 属性のschemaValidatorがnot nullの場合、validateメソッドが実行され、unmarshal(Document, Object)メソッドが実行されることを確認する。<br>
     * ストリームのクローズに失敗した場合、ログが出力され、処理が続行されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testBind03() throws Exception {
        // 前処理
        XMLServletRequestDataBinder_OXMapperStub01 oxmapper = new XMLServletRequestDataBinder_OXMapperStub01();
        XMLServletRequestDataBinder_SchemaValidatorStub01 schemaValidator = new XMLServletRequestDataBinder_SchemaValidatorStub01();
        Object out = new Object();
        String objectName = "target";
        
        XMLServletRequestDataBinder_XMLServletRequestDataBinderStub01 servletDataBinder = new XMLServletRequestDataBinder_XMLServletRequestDataBinderStub01(
                out, oxmapper, schemaValidator, objectName);

        MockHttpServletRequest request = new MockHttpServletRequest();
        XMLServletRequestDataBinder_ServletInputStreamStub01 inputStream = new XMLServletRequestDataBinder_ServletInputStreamStub01();
        request.setInputStream(inputStream);
        request.setCharacterEncoding("abc");

        Document doc = new DocumentImpl();
        servletDataBinder.doc = doc;

        // テスト実施
        servletDataBinder.bind(request);

        // 判定
        // 呼び出し確認
        assertSame(inputStream, servletDataBinder.in);
        assertSame(doc, oxmapper.doc);
        assertSame(out, oxmapper.out);

        // ログ確認
        String message = "Failed to close request stream.";
        assertTrue(LogUTUtil.checkError(message, new IOException()));
    }

    /**
     * testBind04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,C
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (状態) this.oxmapper:not null<br>
     *         (状態) this.schemaValidator:not null<br>
     *         (状態) this.bindingResult.target:not null<br>
     *         (状態) this.bindingResult.errors:要素数1のerrors<br>
     *         (状態) request.servletInputStream:not null<br>
     *         (状態) request.env:not null<br>
     *         (状態) validate():呼び出されたことの確認を行う。<br>
     *                引数が渡されたことの確認を行う。<br>
     *         (状態) in.close():呼び出されたことの確認を行う。<br>
     *         
     * <br>
     * 期待値：
     * <br>
     * 属性のschemaValidatorがnot null、BindExceptionにエラーが格納されている場合、validateメソッドが実行されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testBind04() throws Exception {
        // 前処理
        XMLServletRequestDataBinder_OXMapperStub01 oxmapper = new XMLServletRequestDataBinder_OXMapperStub01();
        SchemaValidator schemaValidator = new SchemaValidatorImpl();
        Object out = new Object();
        String objectName = "target";

        XMLServletRequestDataBinder_XMLServletRequestDataBinderStub01 servletDataBinder = new XMLServletRequestDataBinder_XMLServletRequestDataBinderStub01(
                out, oxmapper, schemaValidator, objectName);
        FieldError error = new FieldError("", "", null, false, new String[] {},
                new Object[] {}, "");
        BindingResult errors = servletDataBinder.getBindingResult();
        errors.addError(error);

        MockHttpServletRequest request = new MockHttpServletRequest();
        XMLServletRequestDataBinder_ServletInputStreamStub02 inputStream = new XMLServletRequestDataBinder_ServletInputStreamStub02();
        request.setInputStream(inputStream);
        request.setCharacterEncoding("abc");

        Document doc = new DocumentImpl();
        servletDataBinder.doc = doc;

        // テスト実施
        servletDataBinder.bind(request);

        // 判定
        // 呼び出し確認
        assertSame(inputStream, servletDataBinder.in);
        assertTrue(inputStream.read);

    }

    /**
     * testValidate01()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：A,C,G
     * <br><br>
     * 入力値：(引数) in:null<br>
     *         (状態) this.schemaValidator:not null<br>
     *         (状態) this.bindingResult.target:not null<br>
     *         (状態) this.bindingResult.errors:要素数0のerrors<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:IllegalArgumentException{<br>
     *                      message = InputStream is null.<br>
     *                    }<br>
     *         (状態変化) ログ:ログレベル：error<br>
     *                    InputStream is null.<br>
     *         
     * <br>
     * 引数inがnullの場合、IllegalArgumentExceptionがスローされ、ログが出力されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidate01() throws Exception {
        // 前処理
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();
        SchemaValidator schemaValidator = new SchemaValidatorImpl();
        Object out = new Object();
        String objectName = "target";

        XMLServletRequestDataBinder servletDataBinder = new XMLServletRequestDataBinder(
                out, oxmapper, schemaValidator, objectName);

        ServletInputStream inputStream = null;

        // テスト実施
        try {
            servletDataBinder.validate(inputStream);
            fail();
        } catch (IllegalArgumentException e) {
            // 判定
            String message = "InputStream is null.";
            assertSame(message, e.getMessage());

            // ログ確認
            assertTrue(LogUTUtil.checkError(message));
        }

    }

    /**
     * testValidate02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,D
     * <br><br>
     * 入力値：(引数) in:not null<br>
     *         (状態) this.schemaValidator:not null<br>
     *         (状態) this.bindingResult.target:not null<br>
     *         (状態) this.bindingResult.errors:要素数0のerrors<br>
     *         (状態) schemaValidator.validate():呼び出されたことの確認を行う。<br>
     *                引数が渡されたことの確認を行う。<br>
     *                <br>
     *                ErrorMassagesにエラーを格納しない。<br>
     *         
     * <br>
     * 期待値：(戻り値) -:Documentインスタンス<br>
     *         (状態変化) this.bindingResult.errors:要素数0のerrors<br>
     *         
     * <br>
     * 形式チェックでエラーが見つからなかった場合、this.bindingResult.errorにエラーが格納されていないことを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidate02() throws Exception {
        // 前処理
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();
        XMLServletRequestDataBinder_SchemaValidatorStub01 schemaValidator = new XMLServletRequestDataBinder_SchemaValidatorStub01();
        Document doc = new DocumentImpl();
        schemaValidator.doc = doc;        
        Object target = new Object();
        String objectName = "target";

        XMLServletRequestDataBinder servletDataBinder = new XMLServletRequestDataBinder(
                target, oxmapper, schemaValidator, objectName);
        ServletInputStream inputStream = new XMLServletRequestDataBinder_ServletInputStreamStub02();

        // テスト実施
        Document result = servletDataBinder.validate(inputStream);
        
        // 判定
        // 戻り値
        assertSame(doc, result);
        
        // 呼び出し確認
        assertSame(inputStream, schemaValidator.in);
        assertSame(target, schemaValidator.object);
        assertSame(ErrorMessages.class, schemaValidator.errorMessages.getClass());
        
        // 状態変化
        BindingResult errors = servletDataBinder.getBindingResult();
        assertEquals(0, errors.getErrorCount());
        
    }

    /**
     * testValidate03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,D
     * <br><br>
     * 入力値：(引数) in:not null<br>
     *         (状態) this.schemaValidator:not null<br>
     *         (状態) this.bindingResult.target:not null<br>
     *         (状態) this.bindingResult.errors:要素数0のerrors<br>
     *         (状態) schemaValidator.validate():呼び出されたことの確認を行う。<br>
     *                引数が渡されたことの確認を行う。<br>
     *                <br>
     *                ErrorMassagesに１つのエラーを格納する。<br>
     *                ErrorMessage{<br>
     *                  field = "abc",<br>
     *                  key = "123",<br>
     *                  replaceValues[]{"a"}<br>
     *                }<br>
     *         (状態) createReplaceValues():1度呼び出されたことの確認を行う。<br>
     *                引数が渡されたことの確認を行う。<br>
     *                String[]{"jkl","j"}を返却する<br>
     *         
     * <br>
     * 期待値：(戻り値) -:Documentインスタンス<br>
     *         (状態変化) this.bindingResult.errors:要素数1のerrors<br>
     *                    FieldError{<br>
     *                      objectName = not null,<br>
     *                      field = "abc",<br>
     *                      rejectedValue = null,<br>
     *                      bindingFailure = false,<br>
     *                      codes[] = {"abc.null.123", "abc.123", "abc"},<br>
     *                      arguments[] = {"abc","a"},<br>
     *                      defaultMessage = null<br>
     *                    }<br>
     *         
     * <br>
     * 形式チェックでエラーが１つ見つかった場合、this.bindingResult.errorにFieldErrorインスタンスが１つ格納されることを確認する。<br>
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidate03() throws Exception {
        // 前処理
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();
        XMLServletRequestDataBinder_SchemaValidatorStub02 schemaValidator = new XMLServletRequestDataBinder_SchemaValidatorStub02();
        Document doc = new DocumentImpl();
        schemaValidator.doc = doc;
        
        Object target = new Object();
        String objectName = "target";
        
        //DataBinder d = new DataBinder(target,"");

        XMLServletRequestDataBinder_XMLServletRequestDataBinderStub02 servletDataBinder = new XMLServletRequestDataBinder_XMLServletRequestDataBinderStub02(
                target, oxmapper, schemaValidator, objectName);
        
        ServletInputStream inputStream = new XMLServletRequestDataBinder_ServletInputStreamStub02();
                        
        // テスト実施
         Document result = servletDataBinder.validate(inputStream);
        
        // 判定
        // 戻り値
        assertSame(doc, result);
        
        // schemaValidator.validateメソッドの呼び出し確認
        assertSame(inputStream, schemaValidator.in);
        assertSame(target, schemaValidator.object);
        assertSame(ErrorMessages.class, schemaValidator.errorMessages.getClass());
        
        // createReplaceValuesメソッドの呼び出し確認
        assertEquals(1, servletDataBinder.count);
        assertEquals("abc", servletDataBinder.field);
        assertEquals("a", servletDataBinder.replaceValues[0]);
        assertEquals(1, servletDataBinder.replaceValues.length);
        
        // 生成されたFieldErrorの確認
        BindingResult errors = servletDataBinder.getBindingResult();
        List errorResults = errors.getAllErrors();
        assertEquals(1, errorResults.size());
        FieldError errorResult = null;
        errorResult = (FieldError) errorResults.get(0);
        assertNotNull(errorResult.getObjectName());
        assertEquals("abc", errorResult.getField());
        assertNull(errorResult.getRejectedValue());
        assertFalse(errorResult.isBindingFailure());
        assertEquals("123", errorResult.getCode());
        assertEquals(3, errorResult.getCodes().length);
        assertEquals("jkl", errorResult.getArguments()[0]);
        assertEquals("j", errorResult.getArguments()[1]);       
        assertEquals(2, errorResult.getArguments().length);
        assertNull(errorResult.getDefaultMessage());
        
    }

    /**
     * testValidate04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,D
     * <br><br>
     * 入力値：(引数) in:not null<br>
     *         (状態) this.schemaValidator:not null<br>
     *         (状態) this.bindingResult.target:not null<br>
     *         (状態) this.bindingResult.errors:要素数0のerrors<br>
     *         (状態) schemaValidator.validate():呼び出されたことの確認を行う。<br>
     *                引数が渡されたことの確認を行う。<br>
     *                <br>
     *                ErrorMassagesに３つのエラーを格納する。<br>
     *                ErrorMessage{<br>
     *                {<br>
     *                  field = "abc",<br>
     *                  key = "123",<br>
     *                  replaceValues[]{"a"}<br>
     *                  }<br>
     *                },<br>
     *                ErrorMessage<br>
     *                {<br>
     *                  field = "def",<br>
     *                  key = "456",<br>
     *                  replaceValues[]{"b"}<br>
     *                  }<br>
     *                },<br>
     *                ErrorMessage<br>
     *                {<br>
     *                  field = "ghi",<br>
     *                  key = "789",<br>
     *                  replaceValues[]{"c"}<br>
     *                }<br>
     *         (状態) createReplaceValues():3度呼び出されたことの確認を行う。<br>
     *                引数が渡されたことの確認を行う。<br>
     *                <br>
     *                １回目に呼び出されたとき、String[]{"jkl","j"}を返却する<br>
     *                ２回目に呼び出されたとき、String[]{"mno","m"}を返却する<br>
     *                ３回目に呼び出されたとき、String[]{"pqr","p"}を返却する<br>
     *         
     * <br>
     * 期待値：(戻り値) -:Documentインスタンス<br>
     *         (状態変化) this.bindingResult.errors:要素数3のerrors<br>
     *                    FieldError<br>
     *                    {<br>
     *                      objectName = not null,<br>
     *                      field = "abc",<br>
     *                      rejectedValue = null,<br>
     *                      bindingFailure = false,<br>
     *                      codes[] = {"123"},<br>
     *                      arguments[] = {"jkl","j"},<br>
     *                      defaultMessage = null<br>
     *                    },<br>
     *                    FieldError<br>
     *                    {<br>
     *                      objectName = not null,<br>
     *                      field = "def",<br>
     *                      rejectedVal = null,<br>
     *                      bindingFailure = false,<br>
     *                      codes[] = {"456"},<br>
     *                      arguments[] = {"mno","k"},<br>
     *                      defaultMessage = null<br>
     *                    },<br>
     *                    FieldError<br>
     *                    {<br>
     *                      objectName = not null,<br>
     *                      field = "ghi",<br>
     *                      rejectedVal = null,<br>
     *                      bindingFailure = false,<br>
     *                      codes[] = {"789"},<br>
     *                      arguments[] = {"pqr","l"},<br>
     *                      defaultMessage = null<br>
     *                    }<br>
     *         
     * <br>
     * 形式チェックでエラーが３つ見つかった場合、this.bindingResult.errorにFieldErrorインスタンスが３つ格納されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidate04() throws Exception {
        // 前処理
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();
        XMLServletRequestDataBinder_SchemaValidatorStub03 schemaValidator = new XMLServletRequestDataBinder_SchemaValidatorStub03();
        Document doc = new DocumentImpl();
        schemaValidator.doc = doc;
        String objectName = "target";
        
        Object target = new Object();

        XMLServletRequestDataBinder_XMLServletRequestDataBinderStub03 servletDataBinder = new XMLServletRequestDataBinder_XMLServletRequestDataBinderStub03(
                target, oxmapper, schemaValidator, objectName);
        ServletInputStream inputStream = new XMLServletRequestDataBinder_ServletInputStreamStub02();

        // テスト実施
        Document result = servletDataBinder.validate(inputStream);
        
        // 判定
        // 戻り値
        assertSame(doc, result);
        
        // schemaValidator.validateメソッドの呼び出し確認
        assertSame(inputStream, schemaValidator.in);
        assertSame(target, schemaValidator.object);
        assertSame(ErrorMessages.class, schemaValidator.errorMessages.getClass());
        
        // createReplaceValuesメソッドの呼び出し確認        
        assertEquals(3, servletDataBinder.count);        
        assertEquals("abc", servletDataBinder.field01);
        assertEquals("a", servletDataBinder.replaceValues01[0]);
        assertEquals(1, servletDataBinder.replaceValues01.length);
        assertEquals("def", servletDataBinder.field02);
        assertEquals("b", servletDataBinder.replaceValues02[0]);
        assertEquals(1, servletDataBinder.replaceValues02.length);
        assertEquals("ghi", servletDataBinder.field03);
        assertEquals("c", servletDataBinder.replaceValues03[0]);
        assertEquals(1, servletDataBinder.replaceValues03.length);
        
        // 生成されたFieldErrorの確認
        BindingResult errors = servletDataBinder.getBindingResult();
        List errorResults = errors.getAllErrors();
        assertEquals(3, errorResults.size());
        FieldError errorResult = null;
        
        // 1つ目のFieldError
        errorResult = (FieldError) errorResults.get(0);
        assertNotNull(errorResult.getObjectName());
        assertEquals("abc", errorResult.getField());
        assertNull(errorResult.getRejectedValue());
        assertFalse(errorResult.isBindingFailure());
        assertEquals("123", errorResult.getCode());
        assertEquals(3, errorResult.getCodes().length);
        assertEquals("jkl", errorResult.getArguments()[0]);
        assertEquals("j", errorResult.getArguments()[1]);       
        assertEquals(2, errorResult.getArguments().length);
        assertNull(errorResult.getDefaultMessage());
        
        // 2つ目のFieldError
        errorResult = (FieldError) errorResults.get(1);
        assertNotNull(errorResult.getObjectName());
        assertEquals("def", errorResult.getField());
        assertNull(errorResult.getRejectedValue());
        assertFalse(errorResult.isBindingFailure());
        assertEquals("456", errorResult.getCode());
        assertEquals(3, errorResult.getCodes().length);
        assertEquals("mno", errorResult.getArguments()[0]);
        assertEquals("k", errorResult.getArguments()[1]);       
        assertEquals(2, errorResult.getArguments().length);
        assertNull(errorResult.getDefaultMessage());

        // 3つ目のFieldError
        errorResult = (FieldError) errorResults.get(2);
        assertNotNull(errorResult.getObjectName());
        assertEquals("ghi", errorResult.getField());
        assertNull(errorResult.getRejectedValue());
        assertFalse(errorResult.isBindingFailure());
        assertEquals("789", errorResult.getCode());
        assertEquals(3, errorResult.getCodes().length);
        assertEquals("pqr", errorResult.getArguments()[0]);
        assertEquals("l", errorResult.getArguments()[1]);       
        assertEquals(2, errorResult.getArguments().length);
        assertNull(errorResult.getDefaultMessage());

    }

    /**
     * testCreateReplaceValues01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,C
     * <br><br>
     * 入力値：(引数) field:"abc"<br>
     *         (引数) replaceValues:要素数3のString[]{"value","str1","str2"}<br>
     *         
     * <br>
     * 期待値：(戻り値) resultReplaceValues:要素数4のString[]{"abc","str1","str2","value"}<br>
     *         
     * <br>
     * 以下のルールで格納された置換文字列が返却されることを確認する。<br>
     * ・引数fieldの値が置換文字列の先頭<br>
     * ・引数replaceValuesの一番目の値が置換文字列の末尾
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateReplaceValues01() throws Exception {
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();
        SchemaValidator schemaValidator = new SchemaValidatorImpl();
        Object target = new Object();
        String objectName = "target";
        
        XMLServletRequestDataBinder servletDataBinder = new XMLServletRequestDataBinder(
                target, oxmapper, schemaValidator, objectName);

        String field = "abc";
        String[] replaceValues = new String[]{"value","str1","str2"};
            
        // テスト実施
        String[] result = servletDataBinder.createReplaceValues(field, replaceValues);

        // 判定
        assertEquals(4, result.length);
        assertEquals("abc", result[0]);
        assertEquals("str1", result[1]);
        assertEquals("str2", result[2]);
        assertEquals("value", result[3]);
        
    }

    /**
     * testCreateReplaceValues02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,C
     * <br><br>
     * 入力値：(引数) field:""<br>
     *         (引数) replaceValues:要素数3のString[]{"value","str1","str2"}<br>
     *         
     * <br>
     * 期待値：(戻り値) resultReplaceValues:要素数4のString[]<br>
     *                  String{"","str1","str2","value"}<br>
     *         
     * <br>
     * 引数fieldの値が空文字の場合、先頭に空文字が格納された置換文字列が返却されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateReplaceValues02() throws Exception {
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();
        SchemaValidator schemaValidator = new SchemaValidatorImpl();
        Object target = new Object();
        String objectName = "target";
        
        XMLServletRequestDataBinder servletDataBinder = new XMLServletRequestDataBinder(
                target, oxmapper, schemaValidator, objectName);

        String field = "";
        String[] replaceValues = new String[]{"value","str1","str2"};
            
        // テスト実施
        String[] result = servletDataBinder.createReplaceValues(field, replaceValues);

        // 判定
        assertEquals(4, result.length);
        assertEquals("", result[0]);
        assertEquals("str1", result[1]);
        assertEquals("str2", result[2]);
        assertEquals("value", result[3]);
    }

    /**
     * testCreateReplaceValues03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,C
     * <br><br>
     * 入力値：(引数) field:null<br>
     *         (引数) replaceValues:要素数3のString[]{null,null,null}<br>
     *         
     * <br>
     * 期待値：(戻り値) resultReplaceValues:要素数4のString[]<br>
     *                  String[]{"",null,null,null}<br>
     *         
     * <br>
     * 引数fieldの値がnullの場合、先頭に空文字が格納された置換文字列が返却されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateReplaceValues03() throws Exception {
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();
        SchemaValidator schemaValidator = new SchemaValidatorImpl();
        Object target = new Object();
        String objectName = "target";
        
        XMLServletRequestDataBinder servletDataBinder = new XMLServletRequestDataBinder(
                target, oxmapper, schemaValidator, objectName);

        String field = null;
        String[] replaceValues = new String[]{null,null,null};
            
        // テスト実施
        String[] result = servletDataBinder.createReplaceValues(field, replaceValues);

        // 判定
        assertEquals(4, result.length);
        assertEquals("", result[0]);
        assertNull(result[1]);
        assertNull(result[2]);
        assertNull(result[3]);
    }

    /**
     * testCreateReplaceValues04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,D
     * <br><br>
     * 入力値：(引数) field:"abc"<br>
     *         (引数) replaceValues:要素数0のString[]{}<br>
     *         
     * <br>
     * 期待値：(戻り値) resultReplaceValues:要素数1のString[]<br>
     *                  String[]{"abc"}<br>
     *         
     * <br>
     * 引数replaceValuesのサイズが0の場合、fieldのみが格納された置換文字列が返却されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateReplaceValues04() throws Exception {
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();
        SchemaValidator schemaValidator = new SchemaValidatorImpl();
        Object target = new Object();
        String objectName = "target";
        
        XMLServletRequestDataBinder servletDataBinder = new XMLServletRequestDataBinder(
                target, oxmapper, schemaValidator, objectName);

        String field = "abc";
        String[] replaceValues = new String[]{};
            
        // テスト実施
        String[] result = servletDataBinder.createReplaceValues(field, replaceValues);

        // 判定
        assertEquals(1, result.length);
        assertEquals("abc", result[0]);
    }

    /**
     * testCreateReplaceValues05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,D
     * <br><br>
     * 入力値：(引数) field:"abc"<br>
     *         (引数) replaceValues:要素数1のString[]{"value"}<br>
     *         
     * <br>
     * 期待値：(戻り値) resultReplaceValues:要素数2のString[]<br>
     *                  String[]{"abc","value"}<br>
     *         
     * <br>
     * 引数replaceValuesのサイズが1の場合、以下のルールで格納された置換文字列が返却されることを確認する。<br>
     * ・引数fieldの値が置換文字列の先頭<br>
     * ・引数replaceValuesの一番目の値が置換文字列の末尾
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateReplaceValues05() throws Exception {
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();
        SchemaValidator schemaValidator = new SchemaValidatorImpl();
        Object target = new Object();
        String objectName = "target";
        
        XMLServletRequestDataBinder servletDataBinder = new XMLServletRequestDataBinder(
                target, oxmapper, schemaValidator, objectName);

        String field = "abc";
        String[] replaceValues = new String[]{"value"};
            
        // テスト実施
        String[] result = servletDataBinder.createReplaceValues(field, replaceValues);

        // 判定
        assertEquals(2, result.length);
        assertEquals("abc", result[0]);
        assertEquals("value", result[1]);
    }

    /**
     * testCreateReplaceValues06()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,C,D
     * <br><br>
     * 入力値：(引数) field:"abc"<br>
     *         (引数) replaceValues:null<br>
     *         
     * <br>
     * 期待値：(戻り値) resultReplaceValues:String[]{"abc"}<br>
     *         
     * <br>
     * 引数replaceValuesのサイズがnullの場合、fieldのみが格納された置換文字列が返却されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateReplaceValues06() throws Exception {
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();
        SchemaValidator schemaValidator = new SchemaValidatorImpl();
        Object target = new Object();
        String objectName = "target";
        
        XMLServletRequestDataBinder servletDataBinder = new XMLServletRequestDataBinder(
                target, oxmapper, schemaValidator, objectName);

        String field = "abc";
        String[] replaceValues = null;
            
        // テスト実施
        String[] result = servletDataBinder.createReplaceValues(field, replaceValues);

        // 判定
        assertEquals(1, result.length);
        assertEquals("abc", result[0]);
    }

}
