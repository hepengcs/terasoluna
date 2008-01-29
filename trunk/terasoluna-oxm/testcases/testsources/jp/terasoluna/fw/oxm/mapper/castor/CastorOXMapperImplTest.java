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

package jp.terasoluna.fw.oxm.mapper.castor;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.oxm.mapper.OXMapper;
import jp.terasoluna.fw.oxm.mapper.castor.CastorOXMapperImpl;
import jp.terasoluna.fw.oxm.mapper.castor.exception.CastorMappingException;
import jp.terasoluna.fw.oxm.mapper.castor.exception.CastorMarshalException;
import jp.terasoluna.fw.oxm.mapper.castor.exception.CastorUnsupportedEncodingException;
import jp.terasoluna.fw.oxm.mapper.castor.exception.CastorValidationException;
import jp.terasoluna.fw.oxm.serialize.XMLSerializerEx;
import junit.framework.TestCase;

import org.apache.xerces.dom.DocumentImpl;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.mapping.loader.AbstractMappingLoader2;
import org.exolab.castor.mapping.xml.ClassMapping;
import org.exolab.castor.xml.ClassDescriptorResolver;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.exolab.castor.xml.util.XMLClassDescriptorAdapter;
import org.w3c.dom.Document;

/**
 * {@link jp.terasoluna.fw.oxm.mapper.castor.CastorOXMapperImpl}
 * クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * Castorを利用したオブジェクト-XML変換クラス。<br>
 * 前提条件：<br>
 * getCastorMappingの引数mappingClass、getUrlメソッドの引数pathがnullになることはない。
 * </p>
 * 
 * @see jp.terasoluna.fw.oxm.mapper.castor.CastorOXMapperImpl
 */
public class CastorOXMapperImplTest extends TestCase {

    /**
     * このクラスのパッケージパス <br>
     * このクラスがjp.co.sample.SampleTestの場合、パッケージパスは「jp/co/sample/」。 <br>
     */
    private String packagePath = this.getClass().getPackage().getName()
            .replace(".", "/")
            + "/";

    /**
     * 定義ファイルのsuffix
     */
    private String mappingSuffix = ".xml";

    /**
     * このテストケースを実行する為の GUI アプリケーションを起動する。
     * 
     * @param args
     *            java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(CastorOXMapperImplTest.class);
    }

    /**
     * 初期化処理を行う。
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * 終了処理を行う。
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * コンストラクタ。
     * 
     * @param name
     *            このテストケースの名前。
     */
    public CastorOXMapperImplTest(String name) {
        super(name);
    }

    /**
     * testUnmarshalDocumentObject01() <br>
     * <br>
     * 
     * (異常系) <br>
     * 観点：A,C,G <br>
     * <br>
     * 入力値：(引数) doc:null<br>
     * (引数) out:not null<br>
     * 
     * <br>
     * 期待値：(状態変化) 例外:IllegalArgumentException{<br>
     * message = "DOM tree is null!"<br> }<br>
     * (状態変化) ログ:ログレベル:error<br>
     * DOM tree is null.<br>
     * 
     * <br>
     * 引数docがnullの場合、例外がスローされることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testUnmarshalDocumentObject01() throws Exception {
        // 前処理
        OXMapper oxmapper = new CastorOXMapperImpl();

        Document doc = null;
        Object out = new Object();

        try {
            // テスト実施
            oxmapper.unmarshal(doc, out);
            fail();
        } catch (IllegalArgumentException e) {
            // 判定
            String message = "DOM tree is null.";
            assertEquals(message, e.getMessage());

            // ログ確認
            assertTrue(LogUTUtil.checkError(message));
        }
    }

    /**
     * testUnmarshalDocumentObject02() <br>
     * <br>
     * 
     * (異常系) <br>
     * 観点：A,C,G <br>
     * <br>
     * 入力値：(引数) doc:not null<br>
     * (引数) out:null<br>
     * (状態) createUnmarshaller():呼び出されたことを確認する。<br>
     * 引数が渡されたことを確認する。<br>
     * (状態) unmarshaller.unmarshal():呼び出されたことを確認する。<br>
     * 引数が渡されたことを確認する。<br>
     * <br>
     * MarshalExceptionをスローする。<br>
     * 
     * <br>
     * 期待値：(状態変化) 例外:CastorMarshalException{<br>
     * cause = MarshalException<br> }<br>
     * (状態変化) ログ:ログレベル:error<br>
     * Castor unmarshal failure.<br>
     * 
     * <br>
     * アンマーシャル時にMarshalExceptionがスローされた場合、ラップしたCastorMarshalExceptionがスローされ、ログが出力されることを確認する。
     * <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testUnmarshalDocumentObject02() throws Exception {
        // 前処理
        CastorOXMapperImpl_CastorOXMapperImplStub01 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub01();
        CastorOXMapperImpl_UnmarshallerStub01 unmarshaller = new CastorOXMapperImpl_UnmarshallerStub01();
        oxmapper.unmarshaller = unmarshaller;

        Document doc = new DocumentImpl();
        Object out = null;

        try {
            // テスト実施
            oxmapper.unmarshal(doc, out);
            fail();
        } catch (CastorMarshalException e) {
            // 判定
            String message = "Castor unmarshal failure.";
            assertSame(MarshalException.class, e.getCause().getClass());

            // ログ確認
            assertTrue(LogUTUtil.checkError(message, e.getCause()));

            // createUnmarshallerメソッドに引数が渡されたことの確認
            assertNull(oxmapper.out);

            // unmarshaller.unmarshalメソッドに引数が渡されたことの確認
            assertSame(doc, unmarshaller.node);
        }
    }

    /**
     * testUnmarshalDocumentObject03() <br>
     * <br>
     * 
     * (異常系) <br>
     * 観点：A,G <br>
     * <br>
     * 入力値：(引数) doc:not null<br>
     * (引数) out:not null<br>
     * (状態) createUnmarshaller():呼び出されたことを確認する。<br>
     * 引数が渡されたことを確認する。<br>
     * (状態) unmarshaller.unmarshal():呼び出されたことを確認する。<br>
     * 引数が渡されたことを確認する。<br>
     * <br>
     * ValidationExceptionをスローする。<br>
     * 
     * <br>
     * 期待値：(状態変化) 例外:CastorValidationException{<br>
     * cause = ValidationException<br> }<br>
     * (状態変化) ログ:ログレベル:error<br>
     * Castor validation error.<br>
     * 
     * <br>
     * アンマーシャル時にValidationExceptionがスローされた場合、ラップしたCastorValudationExceptionがスローされ、ログが出力されることを確認する。
     * <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testUnmarshalDocumentObject03() throws Exception {
        // 前処理
        CastorOXMapperImpl_CastorOXMapperImplStub01 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub01();
        CastorOXMapperImpl_UnmarshallerStub02 unmarshaller = new CastorOXMapperImpl_UnmarshallerStub02();
        oxmapper.unmarshaller = unmarshaller;

        Document doc = new DocumentImpl();
        Object out = new Object();

        try {
            // テスト実施
            oxmapper.unmarshal(doc, out);
            fail();
        } catch (CastorValidationException e) {
            // 判定
            String message = "Castor validation error.";
            assertSame(ValidationException.class, e.getCause().getClass());

            // ログ確認
            assertTrue(LogUTUtil.checkError(message, e.getCause()));

            // createUnmarshallerメソッドに引数が渡されたことの確認
            assertSame(out, oxmapper.out);

            // unmarshaller.unmarshalメソッドに引数が渡されたことの確認
            assertSame(doc, unmarshaller.node);
        }
    }

    /**
     * testUnmarshalDocumentObject04() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) doc:not null<br>
     * (引数) out:not null<br>
     * (状態) createUnmarshaller():呼び出されたことを確認する。<br>
     * 引数が渡されたことを確認する。<br>
     * (状態) unmarshaller.unmarshal():呼び出されたことを確認する。<br>
     * 引数が渡されたことを確認する。<br>
     * 
     * <br>
     * 期待値： <br>
     * 正常系のパターン。<br>
     * unmarshalメソッドが呼び出されたことの確認を行う。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testUnmarshalDocumentObject04() throws Exception {
        // 前処理
        CastorOXMapperImpl_CastorOXMapperImplStub01 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub01();
        CastorOXMapperImpl_UnmarshallerStub03 unmarshaller = new CastorOXMapperImpl_UnmarshallerStub03();
        oxmapper.unmarshaller = unmarshaller;

        Document doc = new DocumentImpl();
        Object out = new Object();

        // テスト実施
        oxmapper.unmarshal(doc, out);

        // createUnmarshallerメソッドに引数が渡されたことの確認
        assertSame(out, oxmapper.out);

        // unmarshaller.unmarshalメソッドに引数が渡されたことの確認
        assertSame(doc, unmarshaller.node);
    }

    /**
     * testUnmarshalReaderObject01() <br>
     * <br>
     * 
     * (異常系) <br>
     * 観点：A,C,G <br>
     * <br>
     * 入力値：(引数) Reader:null<br>
     * (引数) out:not null<br>
     * 
     * <br>
     * 期待値：(状態変化) 例外:IllegalArgumentException{<br>
     * message = "Reader is null!!"<br> }<br>
     * (状態変化) ログ:ログレベル:error<br>
     * Reader is null.<br>
     * 
     * <br>
     * 引数docがnullの場合、例外がスローされることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testUnmarshalReaderObject01() throws Exception {
        // 前処理
        OXMapper oxmapper = new CastorOXMapperImpl();

        Reader reader = null;
        Object out = new Object();

        try {
            // テスト実施
            oxmapper.unmarshal(reader, out);
            fail();
        } catch (IllegalArgumentException e) {
            // 判定
            String message = "Reader is null.";
            assertEquals(message, e.getMessage());

            // ログ確認
            assertTrue(LogUTUtil.checkError(message));
        }
    }

    /**
     * testUnmarshalReaderObject02() <br>
     * <br>
     * 
     * (異常系) <br>
     * 観点：A,C,G <br>
     * <br>
     * 入力値：(引数) Reader:not null<br>
     * (引数) out:null<br>
     * (状態) createUnmarshaller():呼び出されたことを確認する。<br>
     * 引数が渡されたことを確認する。<br>
     * (状態) unmarshaller.unmarshal():呼び出されたことを確認する。<br>
     * 引数が渡されたことを確認する。<br>
     * <br>
     * MarshalExceptionをスローする<br>
     * 
     * <br>
     * 期待値：(状態変化) 例外:CastorMarshalException{<br>
     * cause = MarshalException<br> }<br>
     * (状態変化) ログ:ログレベル:error<br>
     * Castor unmarshal failure.<br>
     * 
     * <br>
     * アンマーシャル時にMarshalExceptionがスローされた場合、ラップしたCastorMarshalExceptionがスローされ、ログが出力されることを確認する。
     * <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testUnmarshalReaderObject02() throws Exception {
        // 前処理
        CastorOXMapperImpl_CastorOXMapperImplStub01 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub01();
        CastorOXMapperImpl_UnmarshallerStub01 unmarshaller = new CastorOXMapperImpl_UnmarshallerStub01();
        oxmapper.unmarshaller = unmarshaller;

        Reader reader = new StringReader("");
        Object out = null;

        try {
            // テスト実施
            oxmapper.unmarshal(reader, out);
            fail();
        } catch (CastorMarshalException e) {
            // 判定
            String message = "Castor unmarshal failure.";
            assertSame(MarshalException.class, e.getCause().getClass());

            // ログ確認
            assertTrue(LogUTUtil.checkError(message, e.getCause()));

            // createUnmarshallerメソッドに引数が渡されたことの確認
            assertNull(oxmapper.out);

            // unmarshaller.unmarshalメソッドに引数が渡されたことの確認
            assertSame(reader, unmarshaller.reader);
        }
    }

    /**
     * testUnmarshalReaderObject03() <br>
     * <br>
     * 
     * (異常系) <br>
     * 観点：A,G <br>
     * <br>
     * 入力値：(引数) Reader:not null<br>
     * (引数) out:not null<br>
     * (状態) createUnmarshaller():呼び出されたことを確認する。<br>
     * 引数が渡されたことを確認する。<br>
     * (状態) unmarshaller.unmarshal():呼び出されたことを確認する。<br>
     * 引数が渡されたことを確認する。<br>
     * <br>
     * ValidationExceptionをスローする<br>
     * 
     * <br>
     * 期待値：(状態変化) 例外:CastorValidationException{<br>
     * cause = ValidationException<br> }<br>
     * (状態変化) ログ:ログレベル:error<br>
     * Castor validation error.<br>
     * 
     * <br>
     * アンマーシャル時にValidationExceptionがスローされた場合、ラップしたCastorValudationExceptionがスローされ、ログが出力されることを確認する。
     * <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testUnmarshalReaderObject03() throws Exception {
        // 前処理
        CastorOXMapperImpl_CastorOXMapperImplStub01 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub01();
        CastorOXMapperImpl_UnmarshallerStub02 unmarshaller = new CastorOXMapperImpl_UnmarshallerStub02();
        oxmapper.unmarshaller = unmarshaller;

        Reader reader = new StringReader("");
        Object out = new Object();

        try {
            // テスト実施
            oxmapper.unmarshal(reader, out);
            fail();
        } catch (CastorValidationException e) {
            // 判定
            String message = "Castor validation error.";
            assertSame(ValidationException.class, e.getCause().getClass());

            // ログ確認
            assertTrue(LogUTUtil.checkError(message, e.getCause()));

            // createUnmarshallerメソッドに引数が渡されたことの確認
            assertSame(out, oxmapper.out);

            // unmarshaller.unmarshalメソッドに引数が渡されたことの確認
            assertSame(reader, unmarshaller.reader);
        }
    }

    /**
     * testUnmarshalReaderObject04() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) Reader:not null<br>
     * (引数) out:not null<br>
     * (状態) createUnmarshaller():呼び出されたことを確認する。<br>
     * 引数が渡されたことを確認する。<br>
     * (状態) unmarshaller.unmarshal():呼び出されたことを確認する。<br>
     * 引数が渡されたことを確認する。<br>
     * 
     * <br>
     * 期待値： <br>
     * 正常系のパターン。<br>
     * unmarshalメソッドが呼び出されたことの確認を行う。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testUnmarshalReaderObject04() throws Exception {
        // 前処理
        CastorOXMapperImpl_CastorOXMapperImplStub01 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub01();
        CastorOXMapperImpl_UnmarshallerStub03 unmarshaller = new CastorOXMapperImpl_UnmarshallerStub03();
        oxmapper.unmarshaller = unmarshaller;

        Reader reader = new StringReader("");
        Object out = new Object();

        // テスト実施
        oxmapper.unmarshal(reader, out);

        // createUnmarshallerメソッドに引数が渡されたことの確認
        assertSame(out, oxmapper.out);

        // unmarshaller.unmarshalメソッドに引数が渡されたことの確認
        assertSame(reader, unmarshaller.reader);

    }

    /**
     * testUnmarshalInputStreamStringObject01() <br>
     * <br>
     * 
     * (異常系) <br>
     * 観点：A,C,G <br>
     * <br>
     * 入力値：(引数) is:null<br>
     * (引数) argCharset:not null<br>
     * (引数) out:not null<br>
     * 
     * <br>
     * 期待値：(状態変化) 例外:IllegalArgumentException{<br>
     * message="InputStream is null."<br> }<br>
     * (状態変化) ログ:ログレベル:error<br>
     * InputStream is null.<br>
     * 
     * <br>
     * 引数isがnullの場合、例外がスローされ、ログが出力されることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testUnmarshalInputStreamStringObject01() throws Exception {
        // 前処理
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();

        // 引数
        InputStream in = null;
        String argCharset = "";
        Object out = new Object();

        // テスト実施
        try {
            oxmapper.unmarshal(in, argCharset, out);
            fail();
        } catch (IllegalArgumentException e) {
            // 判定
            String message = "InputStream is null.";
            assertEquals(message, e.getMessage());

            // ログ確認
            assertTrue(LogUTUtil.checkError(message));
        }

    }

    /**
     * testUnmarshalInputStreamStringObject02() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A,C <br>
     * <br>
     * 入力値：(引数) is:not null<br>
     * (引数) argCharset:""<br>
     * (引数) out:not null<br>
     * (引数) unmarshal():呼び出されたことを確認する。<br>
     * 引数が渡されたことを確認する。<br>
     * (引数) close():呼び出されたことを確認する。<br>
     * 引数が渡されたことを確認する。<br>
     * (状態) this.charset:"UTF-8"<br>
     * 
     * <br>
     * 期待値： <br>
     * 引数argCharsetが空文字の場合、文字コードとして"UTF-8"が設定され、unmarshalメソッド、isr.closeメソッドが呼び出されることを確認する。<br>
     * ログが出力されることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testUnmarshalInputStreamStringObject02() throws Exception {
        // 前処理
        CastorOXMapperImpl_CastorOXMapperImplStub02 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub02();
        UTUtil.setPrivateField(oxmapper, "charset", "UTF-8");

        // 引数
        CastorOXMapperImpl_InputStreamStub01 in = new CastorOXMapperImpl_InputStreamStub01();
        String argCharset = "";
        Object out = new Object();

        // テスト実施
        oxmapper.unmarshal(in, argCharset, out);

        // 判定
        // unmarshalメソッドに引数が渡されたことの確認
        assertSame(in, UTUtil.getPrivateField(oxmapper.reader, "lock"));
        assertSame(out, oxmapper.out);
        assertEquals("UTF8", oxmapper._charset);

        // closeメソッドが呼び出されたことの確認
        assertTrue(in.isClose);
    }

    /**
     * testUnmarshalInputStreamStringObject03() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A,C <br>
     * <br>
     * 入力値：(引数) is:not null<br>
     * (引数) argCharset:null<br>
     * (引数) out:null<br>
     * (引数) unmarshal():呼び出されたことを確認する。<br>
     * 引数が渡されたことを確認する。<br>
     * (引数) close():IOExceptionをスローする。<br>
     * (状態) this.charset:"UTF-8"<br>
     * 
     * <br>
     * 期待値： <br>
     * 引数argCharsetがnullの場合、文字コードとして"UTF-8"が設定され、unmarshalメソッドが呼び出されることを確認する。<br>
     * ストリームをクローズする際にIOExceptionがスローされた場合、ログが出力されることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testUnmarshalInputStreamStringObject03() throws Exception {
        // 前処理
        CastorOXMapperImpl_CastorOXMapperImplStub02 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub02();
        UTUtil.setPrivateField(oxmapper, "charset", "UTF-8");

        // 引数
        CastorOXMapperImpl_InputStreamStub02 in = new CastorOXMapperImpl_InputStreamStub02();
        String argCharset = null;
        Object out = null;

        // テスト実施
        oxmapper.unmarshal(in, argCharset, out);

        // 判定
        // unmarshalメソッドに引数が渡されたことの確認
        assertSame(in, UTUtil.getPrivateField(oxmapper.reader, "lock"));
        assertNull(out);
        assertEquals("UTF8", oxmapper._charset);

        // ログ確認
        String message = "Failed to close input stream.";
        assertTrue(LogUTUtil.checkError(message, new IOException()));

    }

    /**
     * testUnmarshalInputStreamStringObject04() <br>
     * <br>
     * 
     * (異常系) <br>
     * 観点：A,G <br>
     * <br>
     * 入力値：(引数) is:not null<br>
     * (引数) argCharset:"abc"<br>
     * (引数) out:not null<br>
     * (引数) close():呼び出されなかったことを確認する。<br>
     * <br>
     * 期待値：(状態変化) 例外:CastorUnsupportedEncodingException{<br>
     * cause = UnsupportedEncodingException<br> }<br>
     * (状態変化) ログ:ログレベル:error<br>
     * Character encoding error.<br>
     * 
     * <br>
     * 引数argCharsetが不正な文字コードの場合、例外がスローされ、、ログが出力されることを確認する。<br>
     * isr.closeメソッドが呼び出されなかったことを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testUnmarshalInputStreamStringObject04() throws Exception {
        // 前処理
        CastorOXMapperImpl_CastorOXMapperImplStub02 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub02();

        // 引数
        CastorOXMapperImpl_InputStreamStub01 in = new CastorOXMapperImpl_InputStreamStub01();
        String argCharset = "abc";
        Object out = new Object();

        // テスト実施
        try {
            oxmapper.unmarshal(in, argCharset, out);
            fail();
        } catch (CastorUnsupportedEncodingException e) {
            // 判定
            assertSame(UnsupportedEncodingException.class, e.getCause()
                    .getClass());

            // ログ確認
            String message = "Character encoding error.";
            assertTrue(LogUTUtil.checkError(message, e.getCause()));

            // closeメソッドが呼び出されなかったことの確認
            assertFalse(in.isClose);
        }

    }

    /**
     * testUnmarshalInputStreamStringObject05() <br>
     * <br>
     * 
     * (異常系) <br>
     * 観点：A,G <br>
     * <br>
     * 入力値：(引数) is:not null<br>
     * (引数) argCharset:"UTF-8"<br>
     * (引数) out:not null<br>
     * (引数) unmarshal():呼び出されたことを確認する。<br>
     * 引数が渡されたことを確認する。<br>
     * (引数) close():呼び出されたことを確認する。<br>
     * 引数が渡されたことを確認する。<br>
     * (状態) this.charset:"abc"<br>
     * 0
     * 
     * <br>
     * 期待値： <br>
     * 引数argCharsetの文字コードが設定され、unmarshalメソッド、isr.closeメソッドが呼び出されることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testUnmarshalInputStreamStringObject05() throws Exception {
        // 前処理
        CastorOXMapperImpl_CastorOXMapperImplStub02 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub02();
        UTUtil.setPrivateField(oxmapper, "charset", "abc");

        // 引数
        CastorOXMapperImpl_InputStreamStub01 in = new CastorOXMapperImpl_InputStreamStub01();
        String argCharset = "UTF-8";
        Object out = new Object();

        // テスト実施
        oxmapper.unmarshal(in, argCharset, out);

        // 判定
        // unmarshalメソッドに引数が渡されたことの確認
        assertSame(in, UTUtil.getPrivateField(oxmapper.reader, "lock"));
        assertSame(out, oxmapper.out);
        assertEquals("UTF8", oxmapper._charset);

        // closeメソッドが呼び出されなかったことの確認
        assertTrue(in.isClose);
    }

    /**
     * testCreateUnmarshaller01() <br>
     * <br>
     * 
     * (異常系) <br>
     * 観点：A,C,G <br>
     * <br>
     * 入力値：(引数) out:null<br>
     * (状態) this.cache:false<br>
     * (状態) this.mappingFileCache:空のHashMap<br>
     * 
     * <br>
     * 期待値：(状態変化) 例外:IllegalArgumentException{<br>
     * message = "Unmarshal object is null."<br> }<br>
     * (状態変化) ログ:ログレベル:error<br>
     * Unmarshal object is null.<br>
     * (状態変化) this.mappingFileCache:空のHashMap<br>
     * 
     * <br>
     * 引数outがnullの場合、例外がスローされ、ログが出力されることを確認する <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testCreateUnmarshaller01() throws Exception {
        // 前処理
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();

        // キャッシュ
        UTUtil.setPrivateField(oxmapper, "cache", false);
        Map<Class, Mapping> map = new HashMap<Class, Mapping>();
        UTUtil.setPrivateField(oxmapper, "mappingFileCache", map);

        // 引数
        Object out = null;

        // テスト実施
        try {
            oxmapper.createUnmarshaller(out);
            fail();
        } catch (IllegalArgumentException e) {

            // 判定
            String message = "Unmarshal object is null.";
            assertEquals(message, e.getMessage());

            // ログ確認
            assertTrue(LogUTUtil.checkError(message));
        }
    }

    /**
     * testCreateUnmarshaller02() <br>
     * <br>
     * 
     * (異常系) <br>
     * 観点：A,G <br>
     * <br>
     * 入力値：(引数) out:CastorOXMapperImpl_Stub01<br>
     * (状態) マッピング定義ファイル:不正なマッピング定義ファイル<br>
     * <?xml version="1.0"?><br>
     * <!DOCTYPE mapping PUBLIC "-//EXOLAB/Castor Object Mapping DTD Version
     * 1.0//EN" "http://castor.exolab.org/mapping.dtd"><br>
     * <mapping><br>
     * <class name="jp.terasoluna.fw.oxm.mapper.castor.xxxDTO"><br>
     * <field name="param1" type="int" /><br>
     * <field name="param2" type="date" /><br>
     * <field name="param3" type="string" /><br>
     * </class><br>
     * </mapping><br>
     * (状態) getCastorMapping():呼び出されたことを確認する。<br>
     * 引数が渡されたことを確認する。<br>
     * (状態) this.cache:false<br>
     * (状態) this.mappingFileCache:空のHashMap<br>
     * 
     * <br>
     * 期待値：(状態変化) 例外:CastorMappingException{<br>
     * cause = MappingException<br> }<br>
     * (状態変化) ログ:ログレベル:error<br>
     * Castor mapping file is invalid.<br>
     * (状態変化) this.mappingFileCache:空のHashMap<br>
     * 
     * <br>
     * マッピング定義ファイルの定義に問題がある場合、例外がスローされ、ログが出力されることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testCreateUnmarshaller02() throws Exception {
        // 前処理
        CastorOXMapperImpl_CastorOXMapperImplStub03 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub03();
        oxmapper.mappingPath = packagePath
                + "CastorOXMapperImpl_JavaBeanStub02" + mappingSuffix;

        // キャッシュ
        UTUtil.setPrivateField(oxmapper, "cache", false);
        Map<Class, Mapping> map = new HashMap<Class, Mapping>();
        UTUtil.setPrivateField(oxmapper, "mappingFileCache", map);

        // 引数
        Object out = new Object();

        // テスト実施
        try {
            oxmapper.createUnmarshaller(out);
            fail();
        } catch (CastorMappingException e) {
            assertSame(MappingException.class, e.getCause().getClass());
            // ログ確認
            String message = "Castor mapping file is invalid. "
                    + "- [root-classpath]/java/lang/Object.xml";
            assertTrue(LogUTUtil.checkError(message, e.getCause()));
        }

    }

    /**
     * testCreateUnmarshaller03() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A,D <br>
     * <br>
     * 入力値：(引数) out:CastorOXMapperImpl_Stub01<br>
     * (状態) マッピング定義ファイル:正常なマッピング定義ファイル<br>
     * <?xml version="1.0"?><br>
     * <!DOCTYPE mapping PUBLIC "-//EXOLAB/Castor Object Mapping DTD Version
     * 1.0//EN" "http://castor.exolab.org/mapping.dtd"><br>
     * <mapping><br>
     * <class
     * name=""jp.terasoluna.fw.oxm.mapper.castor.CastorOXMapperImpl_Stub01"><br>
     * <field name="param1" type="int" /><br>
     * <field name="param2" type="date" /><br>
     * <field name="param3" type="string" /><br>
     * </class><br>
     * </mapping><br>
     * (状態) getCastorMapping():呼び出されたことを確認する。<br>
     * 引数が渡されたことを確認する。<br>
     * (状態) this.cache:false<br>
     * (状態) this.mappingFileCache:空のHashMap<br>
     * 
     * <br>
     * 期待値：(戻り値) Unmarshaller:Unmarshaller{<br>
     * _validate = false;<br>
     * _cdResolver._mappingLoader._javaClass[0]=
     * .CastorOXMapperImpl_Stub01.class<br> }<br>
     * (状態変化) this.mappingFileCache:空のHashMap<br>
     * 
     * <br>
     * マッピング定義に問題がない場合、定義されたクラスを設定したアンマーシャラを返却することを確認する。<br>
     * マッピングオブジェクトのキャッシュが行なわれないことを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testCreateUnmarshaller03() throws Exception {
        // 前処理
        CastorOXMapperImpl_CastorOXMapperImplStub03 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub03();
        oxmapper.mappingPath = packagePath
                + "CastorOXMapperImpl_JavaBeanStub01" + mappingSuffix;
        Class testClass = CastorOXMapperImpl_JavaBeanStub01.class;

        // キャッシュ
        UTUtil.setPrivateField(oxmapper, "cache", false);
        Map<Class, Mapping> map = new HashMap<Class, Mapping>();
        UTUtil.setPrivateField(oxmapper, "mappingFileCache", map);

        // 引数
        Object out = new Object();

        // テスト実施
        Unmarshaller unmarshaller = oxmapper.createUnmarshaller(out);
        // 判定
        // unmarshalメソッドに引数が渡されたことの確認
        assertEquals(out.getClass(), oxmapper.mappingClass);
        // Unmarshallerのvalidationがfalseになっていることの確認
        Boolean validate = (Boolean) UTUtil.getPrivateField(unmarshaller,
                "_validate");
        assertFalse(validate.booleanValue());
        // UnmarshallerにCastorOXMapperImpl_Stub01クラスが設定されていることを確認する
        ClassDescriptorResolver _cdResolver = (ClassDescriptorResolver) UTUtil
                .getPrivateField(unmarshaller, "_cdResolver");
        AbstractMappingLoader2 ml = (AbstractMappingLoader2) _cdResolver
                .getMappingLoader();
        Vector descriptors = (Vector) UTUtil
                .getPrivateField(ml, "_descriptors");
        assertEquals(1, descriptors.size());
        XMLClassDescriptorAdapter adapter = (XMLClassDescriptorAdapter) descriptors
                .get(0);
        assertEquals(testClass, adapter.getJavaClass());

        // マッピングオブジェクトがキャッシュされていないことを確認する
        Map resultMap = (HashMap) UTUtil.getPrivateField(oxmapper,
                "mappingFileCache");
        assertEquals(0, resultMap.size());
    }

    /**
     * testCreateUnmarshaller04() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A,D <br>
     * <br>
     * 入力値：(引数) out:CastorOXMapperImpl_Stub01<br>
     * (状態) マッピング定義ファイル:正常なマッピング定義ファイル<br>
     * <?xml version="1.0"?><br>
     * <!DOCTYPE mapping PUBLIC "-//EXOLAB/Castor Object Mapping DTD Version
     * 1.0//EN" "http://castor.exolab.org/mapping.dtd"><br>
     * <mapping><br>
     * <class
     * name=""jp.terasoluna.fw.oxm.mapper.castor.CastorOXMapperImpl_Stub01"><br>
     * <field name="param1" type="int" /><br>
     * <field name="param2" type="date" /><br>
     * <field name="param3" type="string" /><br>
     * </class><br>
     * </mapping><br>
     * (状態) getCastorMapping():呼び出されたことを確認する。<br>
     * 引数が渡されたことを確認する。<br>
     * (状態) this.cache:true<br>
     * (状態) this.mappingFileCache:空のHashMap<br>
     * 
     * <br>
     * 期待値：(戻り値) Unmarshaller:Unmarshaller{<br>
     * _validate = false;<br>
     * _cdResolver._mappingLoader._javaClass[0]=
     * .CastorOXMapperImpl_Stub01.class<br> }<br>
     * (状態変化) this.mappingFileCache:要素数が1のHashMap<br>
     * HashMap<br> ｛<br>
     * 引数のClassインスタンス=Mappingインスタンス<br> }<br>
     * 
     * <br>
     * マッピング定義に問題がない場合、定義されたクラスを設定したアンマーシャラを返却することを確認する。<br>
     * マッピングオブジェクトのキャッシュが行なわれていることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testCreateUnmarshaller04() throws Exception {
        // 前処理
        CastorOXMapperImpl_CastorOXMapperImplStub03 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub03();
        oxmapper.mappingPath = packagePath
                + "CastorOXMapperImpl_JavaBeanStub01" + mappingSuffix;
        Class testClass = CastorOXMapperImpl_JavaBeanStub01.class;

        // キャッシュ
        UTUtil.setPrivateField(oxmapper, "cache", true);
        Map<Class, Mapping> map = new HashMap<Class, Mapping>();
        UTUtil.setPrivateField(oxmapper, "mappingFileCache", map);

        // 引数
        Object out = new Object();

        // テスト実施
        Unmarshaller unmarshaller = oxmapper.createUnmarshaller(out);
        // 判定
        // unmarshalメソッドに引数が渡されたことの確認
        assertEquals(out.getClass(), oxmapper.mappingClass);
        // Unmarshallerのvalidationがfalseになっていることの確認
        Boolean validate = (Boolean) UTUtil.getPrivateField(unmarshaller,
                "_validate");
        assertFalse(validate.booleanValue());
        // UnmarshallerにCastorOXMapperImpl_Stub01クラスが設定されていることを確認する
        ClassDescriptorResolver _cdResolver = (ClassDescriptorResolver) UTUtil
                .getPrivateField(unmarshaller, "_cdResolver");
        AbstractMappingLoader2 ml = (AbstractMappingLoader2) _cdResolver
                .getMappingLoader();
        Vector descriptors = (Vector) UTUtil
                .getPrivateField(ml, "_descriptors");
        assertEquals(1, descriptors.size());
        XMLClassDescriptorAdapter adapter = (XMLClassDescriptorAdapter) descriptors
                .get(0);
        assertEquals(testClass, adapter.getJavaClass());

        // マッピングオブジェクトがキャッシュされていることを確認する
        Map resultMap = (HashMap) UTUtil.getPrivateField(oxmapper,
                "mappingFileCache");
        assertEquals(1, resultMap.size());
        assertSame(oxmapper.mapping, resultMap.get(out.getClass()));

    }

    /**
     * testCreateUnmarshaller05() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A,D <br>
     * <br>
     * 入力値：(引数) out:CastorOXMapperImpl_Stub01<br>
     * (状態) マッピング定義ファイル:正常なマッピング定義ファイル<br>
     * <?xml version="1.0"?><br>
     * <!DOCTYPE mapping PUBLIC "-//EXOLAB/Castor Object Mapping DTD Version
     * 1.0//EN" "http://castor.exolab.org/mapping.dtd"><br>
     * <mapping><br>
     * <class
     * name=""jp.terasoluna.fw.oxm.mapper.castor.CastorOXMapperImpl_Stub01"><br>
     * <field name="param1" type="int" /><br>
     * <field name="param2" type="date" /><br>
     * <field name="param3" type="string" /><br>
     * </class><br>
     * </mapping><br>
     * (状態) getCastorMapping():呼び出されたことを確認する。<br>
     * 引数が渡されたことを確認する。<br>
     * (状態) this.cache:true<br>
     * (状態) this.mappingFileCache:要素数が1のHashMap<br>
     * HashMap<br> ｛<br>
     * 引数のClassインスタンス=Mappingインスタンス<br> }<br>
     * 
     * <br>
     * 期待値：(戻り値) Unmarshaller:Unmarshaller{<br>
     * _validate = false;<br>
     * _cdResolver._mappingLoader._javaClass[0]=
     * .CastorOXMapperImpl_Stub01.class<br> }<br>
     * (状態変化) this.mappingFileCache:要素数が1のHashMap<br>
     * HashMap<br> ｛<br>
     * 引数のClassインスタンス=Mappingインスタンス<br> }<br>
     * 
     * <br>
     * マッピング定義に問題がない場合、定義されたクラスを設定したアンマーシャラを返却することを確認する。<br>
     * マッピングオブジェクトのキャッシュが行なわれず、要素数が増えていないことを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testCreateUnmarshaller05() throws Exception {
        // 前処理
        CastorOXMapperImpl_CastorOXMapperImplStub03 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub03();
        oxmapper.mappingPath = packagePath
                + "CastorOXMapperImpl_JavaBeanStub01" + mappingSuffix;
        Class testClass = CastorOXMapperImpl_JavaBeanStub01.class;

        // 引数
        Object out = new Object();

        // キャッシュ
        UTUtil.setPrivateField(oxmapper, "cache", true);
        Map<Class, Mapping> map = new HashMap<Class, Mapping>();
        Mapping mapping = new Mapping();
        map.put(out.getClass(), mapping);
        UTUtil.setPrivateField(oxmapper, "mappingFileCache", map);

        // テスト実施
        Unmarshaller unmarshaller = oxmapper.createUnmarshaller(out);
        // 判定
        // unmarshalメソッドに引数が渡されたことの確認
        assertEquals(out.getClass(), oxmapper.mappingClass);
        // Unmarshallerのvalidationがfalseになっていることの確認
        Boolean validate = (Boolean) UTUtil.getPrivateField(unmarshaller,
                "_validate");
        assertFalse(validate.booleanValue());
        // UnmarshallerにCastorOXMapperImpl_Stub01クラスが設定されていることを確認する
        ClassDescriptorResolver _cdResolver = (ClassDescriptorResolver) UTUtil
                .getPrivateField(unmarshaller, "_cdResolver");
        AbstractMappingLoader2 ml = (AbstractMappingLoader2) _cdResolver
                .getMappingLoader();
        Vector descriptors = (Vector) UTUtil
                .getPrivateField(ml, "_descriptors");
        assertEquals(1, descriptors.size());
        XMLClassDescriptorAdapter adapter = (XMLClassDescriptorAdapter) descriptors
                .get(0);
        assertEquals(testClass, adapter.getJavaClass());

        // マッピングオブジェクトがキャッシュされていないことを確認する
        Map resultMap = (HashMap) UTUtil.getPrivateField(oxmapper,
                "mappingFileCache");
        assertEquals(1, resultMap.size());
        assertSame(mapping, resultMap.get(out.getClass()));
    }

    /**
     * testCreateUnmarshaller06() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A,D <br>
     * <br>
     * 入力値：<br>
     * (引数) out:CastorOXMapperImpl_Stub01<br>
     * (状態) preserveWhitespaceAtUnmarshal : true<br>
     * (状態) マッピング定義ファイル:正常なマッピング定義ファイル<br>
     * <?xml version="1.0"?><br>
     * <!DOCTYPE mapping PUBLIC "-//EXOLAB/Castor Object Mapping DTD Version
     * 1.0//EN" "http://castor.exolab.org/mapping.dtd"><br>
     * <mapping><br>
     * <class
     * name=""jp.terasoluna.fw.oxm.mapper.castor.CastorOXMapperImpl_Stub01"><br>
     * <field name="param1" type="int" /><br>
     * <field name="param2" type="date" /><br>
     * <field name="param3" type="string" /><br>
     * </class><br>
     * </mapping><br>
     * (状態) getCastorMapping():呼び出されたことを確認する。<br>
     * 引数が渡されたことを確認する。<br>
     * (状態) this.cache:false<br>
     * (状態) this.mappingFileCache:空のHashMap<br>
     * 
     * <br>
     * 期待値：<br>
     * (状態変化) Unmarshallerのフィールド_wsPreserve : true<br>
     * 
     * <br>
     * setPreserveWhitespaceAtUnmarshalで設定されたオプションが Unmarshallerに渡されていることを確認する。<br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testCreateUnmarshaller06() throws Exception {
        // 前処理
        CastorOXMapperImpl_CastorOXMapperImplStub03 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub03();
        oxmapper.mappingPath = packagePath
                + "CastorOXMapperImpl_JavaBeanStub01" + mappingSuffix;

        // キャッシュ
        UTUtil.setPrivateField(oxmapper, "cache", false);
        Map<Class, Mapping> map = new HashMap<Class, Mapping>();
        UTUtil.setPrivateField(oxmapper, "mappingFileCache", map);

        // 引数
        Object out = new Object();

        UTUtil.setPrivateField(oxmapper, "preserveWhitespaceAtUnmarshal", true);

        // テスト実施
        Unmarshaller unmarshaller = oxmapper.createUnmarshaller(out);

        // 判定
        Boolean wsPreserve = (Boolean) UTUtil.getPrivateField(unmarshaller,
                "_wsPreserve");
        assertTrue(wsPreserve);
    }

    /**
     * testCreateUnmarshaller07() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A,D <br>
     * <br>
     * 入力値：<br>
     * (引数) out:CastorOXMapperImpl_Stub01<br>
     * (状態) preserveWhitespaceAtUnmarshal : false<br>
     * (状態) マッピング定義ファイル:正常なマッピング定義ファイル<br>
     * <?xml version="1.0"?><br>
     * <!DOCTYPE mapping PUBLIC "-//EXOLAB/Castor Object Mapping DTD Version
     * 1.0//EN" "http://castor.exolab.org/mapping.dtd"><br>
     * <mapping><br>
     * <class
     * name=""jp.terasoluna.fw.oxm.mapper.castor.CastorOXMapperImpl_Stub01"><br>
     * <field name="param1" type="int" /><br>
     * <field name="param2" type="date" /><br>
     * <field name="param3" type="string" /><br>
     * </class><br>
     * </mapping><br>
     * (状態) getCastorMapping():呼び出されたことを確認する。<br>
     * 引数が渡されたことを確認する。<br>
     * (状態) this.cache:false<br>
     * (状態) this.mappingFileCache:空のHashMap<br>
     * 
     * <br>
     * 期待値：<br>
     * (状態変化) Unmarshallerのフィールド_wsPreserve : false<br>
     * 
     * <br>
     * setPreserveWhitespaceAtUnmarshalで設定されたオプションが Unmarshallerに渡されていることを確認する。<br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testCreateUnmarshaller07() throws Exception {
        // 前処理
        CastorOXMapperImpl_CastorOXMapperImplStub03 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub03();
        oxmapper.mappingPath = packagePath
                + "CastorOXMapperImpl_JavaBeanStub01" + mappingSuffix;

        // キャッシュ
        UTUtil.setPrivateField(oxmapper, "cache", false);
        Map<Class, Mapping> map = new HashMap<Class, Mapping>();
        UTUtil.setPrivateField(oxmapper, "mappingFileCache", map);

        // 引数
        Object out = new Object();

        UTUtil.setPrivateField(oxmapper, "preserveWhitespaceAtUnmarshal", false);

        // テスト実施
        Unmarshaller unmarshaller = oxmapper.createUnmarshaller(out);

        // 判定
        Boolean wsPreserve = (Boolean) UTUtil.getPrivateField(unmarshaller,
                "_wsPreserve");
        assertFalse(wsPreserve);
    }

    /**
     * testMarshal01() <br>
     * <br>
     * 
     * (異常系) <br>
     * 観点：A,C,G <br>
     * <br>
     * 入力値：(引数) in:not null<br>
     * (引数) writer:not null<br>
     * (状態) createMarshaller():呼び出されたことを確認する。<br>
     * 引数が渡されたことを確認する。<br>
     * (状態) marshaller.marshal():MarshalExceptionをスローする<br>
     * 
     * <br>
     * 期待値：(状態変化) 例外:CastorMarshalException{<br>
     * cause = MarshalException<br> }<br>
     * (状態変化) ログ:ログレベル:error<br>
     * Writer object is invalid.<br>
     * 
     * <br>
     * 不正なオブジェクトがマーシャラに設定された場合、例外がスローされることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testMarshal01() throws Exception {
        // 前処理
        CastorOXMapperImpl_CastorOXMapperImplStub01 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub01();
        CastorOXMapperImpl_MarshallerStub01 marshaller = new CastorOXMapperImpl_MarshallerStub01(
                new StringWriter());
        oxmapper.marshaller = marshaller;

        Object in = new Object();
        Writer writer = new StringWriter();

        try {
            // テスト実施
            oxmapper.marshal(in, writer);
            fail();
        } catch (CastorMarshalException e) {
            // 判定
            assertSame(MarshalException.class, e.getCause().getClass());

            // ログ確認
            String message = "Castor marshal failure.";
            assertTrue(LogUTUtil.checkError(message, e.getCause()));

            // createMarshallerメソッドに引数が渡されたことの確認
            assertSame(in, oxmapper.in);
            assertSame(writer, oxmapper.writer);

        }
    }

    /**
     * testMarshal02() <br>
     * <br>
     * 
     * (異常系) <br>
     * 観点：A,C,G <br>
     * <br>
     * 入力値：(引数) in:not null<br>
     * (引数) writer:not null<br>
     * (状態) createMarshaller():呼び出されたことを確認する。<br>
     * 引数が渡されたことを確認する。<br>
     * (状態) marshaller.marshal():ValidationExceptionをスローする<br>
     * 
     * <br>
     * 期待値：(状態変化) 例外:CastorValidationException{<br>
     * cause = ValidationException<br> }<br>
     * (状態変化) ログ:ログレベル:error<br>
     * Castor validation error.<br>
     * 
     * <br>
     * マーシャル時にバリデーションエラーが発生した場合、例外がスローされることを確認する。<br>
     * （実際にはこの例外はスローされない） <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testMarshal02() throws Exception {
        // 前処理
        CastorOXMapperImpl_CastorOXMapperImplStub01 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub01();
        CastorOXMapperImpl_MarshallerStub02 marshaller = new CastorOXMapperImpl_MarshallerStub02(
                new StringWriter());
        oxmapper.marshaller = marshaller;

        Object in = new Object();
        Writer writer = new StringWriter();

        try {
            // テスト実施
            oxmapper.marshal(in, writer);
            fail();
        } catch (CastorValidationException e) {
            // 判定
            assertSame(ValidationException.class, e.getCause().getClass());

            // ログ確認
            String message = "Castor validation error.";
            assertTrue(LogUTUtil.checkError(message, e.getCause()));

            // createMarshallerメソッドに引数が渡されたことの確認
            assertSame(in, oxmapper.in);
            assertSame(writer, oxmapper.writer);
        }
    }

    /**
     * testMarshal03() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：<br>
     * (引数) in:null<br>
     * (引数) writer:null<br>
     * (状態) createMarshaller():呼び出されたことを確認する。<br>
     * 引数が渡されたことを確認する。<br>
     * (状態) marshaller.marshal():呼び出されたことを確認する。<br>
     * 引数が渡されたことを確認する。<br>
     * 
     * <br>
     * 期待値： <br>
     * 正常系のパターン。<br>
     * 引数out,writerがnullの場合、marsharlメソッドが呼び出されたことの確認を行う。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testMarshal03() throws Exception {
        // 前処理
        CastorOXMapperImpl_CastorOXMapperImplStub01 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub01();
        CastorOXMapperImpl_MarshallerStub03 marshaller = new CastorOXMapperImpl_MarshallerStub03(
                new StringWriter());
        oxmapper.marshaller = marshaller;

        CastorOXMapperImpl_UnmarshallerStub01 unmarshaller = new CastorOXMapperImpl_UnmarshallerStub01();
        oxmapper.unmarshaller = unmarshaller;

        Object in = null;
        Writer writer = null;

        // テスト実施
        oxmapper.marshal(in, writer);

        // 判定
        // createMarshallerメソッドに引数が渡されたことの確認
        assertNull(oxmapper.in);
        assertNull(oxmapper.writer);

        // marshallerメソッドに引数が渡されたことの確認
        assertNull(marshaller.in);

    }

    /**
     * testMarshal04() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：<br>
     * (引数) in:not null<br>
     * (引数) writer:not null<br>
     * (状態) createMarshaller():呼び出されたことを確認する。<br>
     * 引数が渡されたことを確認する。<br>
     * (状態) marshaller.marshal():呼び出されたことを確認する。<br>
     * 引数が渡されたことを確認する。<br>
     * 
     * <br>
     * 期待値： <br>
     * 正常系のパターン。<br>
     * marsharlメソッドが呼び出されたことの確認を行う。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testMarshal04() throws Exception {
        // 前処理
        CastorOXMapperImpl_CastorOXMapperImplStub01 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub01();
        CastorOXMapperImpl_MarshallerStub03 marshaller = new CastorOXMapperImpl_MarshallerStub03(
                new StringWriter());
        oxmapper.marshaller = marshaller;

        Object in = new Object();
        Writer writer = new StringWriter();

        // テスト実施
        oxmapper.marshal(in, writer);

        // 判定
        // createMarshallerメソッドに引数が渡されたことの確認
        assertSame(in, oxmapper.in);
        assertSame(writer, oxmapper.writer);

        // marshallerメソッドに引数が渡されたことの確認
        assertSame(in, marshaller.in);

    }

    /**
     * testCreateMarshaller01() <br>
     * <br>
     * 
     * (異常系) <br>
     * 観点：A,C,G <br>
     * <br>
     * 入力値：(引数) in:null<br>
     * (引数) writer:not null<br>
     * (状態) this.cache:false<br>
     * (状態) this.mappingFileCache:空のHashMap<br>
     * 
     * <br>
     * 期待値：(状態変化) 例外:IllegalArgumentException{<br>
     * message="Marshal object is null."<br> }<br>
     * (状態変化) ログ:ログレベル:error<br>
     * Marshal object is null.<br>
     * (状態変化) this.mappingFileCache:空のHashMap<br>
     * 
     * <br>
     * 引数inがnullの場合、例外がスローされることを確認する <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testCreateMarshaller01() throws Exception {
        // 前処理
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();

        // 引数
        Object in = null;
        Writer writer = new StringWriter();

        // テスト実施
        try {
            oxmapper.createMarshaller(in, writer);
            fail();
        } catch (IllegalArgumentException e) {

            // 判定
            String message = "Marshall object is null.";
            assertEquals(message, e.getMessage());

            // ログ確認
            assertTrue(LogUTUtil.checkError(message));
        }
    }

    /**
     * testCreateMarshaller02() <br>
     * <br>
     * 
     * (異常系) <br>
     * 観点：A,C,G <br>
     * <br>
     * 入力値：(引数) in:not null<br>
     * (引数) writer:null<br>
     * (状態) this.cache:false<br>
     * (状態) this.mappingFileCache:空のHashMap<br>
     * 
     * <br>
     * 期待値：(状態変化) 例外:IllegalArgumentException{<br>
     * message="Writer is null."<br> }<br>
     * (状態変化) ログ:ログレベル:error<br>
     * Writer is null.<br>
     * (状態変化) this.mappingFileCache:空のHashMap<br>
     * 
     * <br>
     * 引数writerがnullの場合、例外がスローされることを確認する <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testCreateMarshaller02() throws Exception {
        // 前処理
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();

        // 引数
        Object in = new Object();
        Writer writer = null;

        // テスト実施
        try {
            oxmapper.createMarshaller(in, writer);
            fail();
        } catch (IllegalArgumentException e) {

            // 判定
            String message = "Writer is null.";
            assertEquals(message, e.getMessage());

            // ログ確認
            assertTrue(LogUTUtil.checkError(message));
        }
    }

    /**
     * testCreateMarshaller03() <br>
     * <br>
     * 
     * (異常系) <br>
     * 観点：A,C,G <br>
     * <br>
     * 入力値：(引数) in:CastorOXMapperImpl_Stub01<br>
     * (引数) writer:not null<br>
     * (状態) マッピング定義ファイル:不正なマッピング定義ファイル<br>
     * <?xml version="1.0"?><br>
     * <!DOCTYPE mapping PUBLIC "-//EXOLAB/Castor Object Mapping DTD Version
     * 1.0//EN" "http://castor.exolab.org/mapping.dtd"><br>
     * <mapping><br>
     * <class name="jp.terasoluna.fw.oxm.mapper.castor.xxxDTO"><br>
     * <field name="param1" type="int" /><br>
     * <field name="param2" type="date" /><br>
     * <field name="param3" type="int" /><br>
     * </class><br>
     * </mapping><br>
     * (状態) getCastorMapping():呼び出されたことを確認する。<br>
     * 引数が渡されたことを確認する。<br>
     * (状態) this.cache:false<br>
     * (状態) this.mappingFileCache:空のHashMap<br>
     * 
     * <br>
     * 期待値：(状態変化) 例外:CastorMappingException{<br>
     * cause = MappingException<br> }<br>
     * (状態変化) ログ:ログレベル:error<br>
     * Castor mapping file is invalid.<br>
     * (状態変化) this.mappingFileCache:空のHashMap<br>
     * 
     * <br>
     * マッピング定義ファイルが不正な場合、例外がスローされることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testCreateMarshaller03() throws Exception {
        // 前処理
        CastorOXMapperImpl_CastorOXMapperImplStub03 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub03();
        oxmapper.mappingPath = packagePath
                + "CastorOXMapperImpl_JavaBeanStub02" + mappingSuffix;

        // 引数
        Object in = new Object();
        Writer writer = new StringWriter();

        // テスト実施
        try {
            oxmapper.createMarshaller(in, writer);
            fail();
        } catch (CastorMappingException e) {
            assertSame(MappingException.class, e.getCause().getClass());
            // ログ確認
            String message = "Castor mapping file is invalid. "
                    + "- [root-classpath]/java/lang/Object.xml";
            assertTrue(LogUTUtil.checkError(message, e.getCause()));
        }
    }

    /**
     * testCreateMarshaller04() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A,D <br>
     * <br>
     * 入力値：(引数) in:CastorOXMapperImpl_Stub01<br>
     * (引数) writer:not null<br>
     * (状態) マッピング定義ファイル:正常なマッピング定義ファイル<br>
     * <?xml version="1.0"?><br>
     * <!DOCTYPE mapping PUBLIC "-//EXOLAB/Castor Object Mapping DTD Version
     * 1.0//EN" "http://castor.exolab.org/mapping.dtd"><br>
     * <mapping><br>
     * <class
     * name="jp.terasoluna.fw.oxm.mapper.castor.CastorOXMapperImpl_Stub01"><br>
     * <field name="param1" type="int" /><br>
     * <field name="param2" type="date" /><br>
     * <field name="param3" type="string" /><br>
     * </class><br>
     * </mapping><br>
     * (状態) getCastorMapping():呼び出されたことを確認する。<br>
     * 引数が渡されたことを確認する。<br>
     * (状態) this.cache:false<br>
     * (状態) this.mappingFileCache:空のHashMap<br>
     * 
     * <br>
     * 期待値：(戻り値) Marshaller:Marshaller{<br>
     * _validate = false;<br>
     * _cdResolver._mappingLoader._javaClass[0]=CastorOXMapperImpl_Stub01.class<br> }<br>
     * (状態変化) this.mappingFileCache:要素数が1のHashMap<br>
     * HashMap<br> ｛<br>
     * 引数のClassインスタンス=Mappingインスタンス<br> }<br>
     * 
     * <br>
     * マッピング定義ファイルが正しい場合、正常にマーシャラが生成されることを確認する。<br>
     * マッピングオブジェクトのキャッシュが行なわれないことを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testCreateMarshaller04() throws Exception {
        // 前処理
        CastorOXMapperImpl_CastorOXMapperImplStub03 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub03();
        oxmapper.mappingPath = packagePath
                + "CastorOXMapperImpl_JavaBeanStub01" + mappingSuffix;
        Class testClass = CastorOXMapperImpl_JavaBeanStub01.class;

        // キャッシュ
        UTUtil.setPrivateField(oxmapper, "cache", false);
        Map<Class, Mapping> map = new HashMap<Class, Mapping>();
        UTUtil.setPrivateField(oxmapper, "mappingFileCache", map);

        // 引数
        Object in = new Object();
        Writer writer = new StringWriter();

        // テスト実施
        Marshaller marshaller = oxmapper.createMarshaller(in, writer);

        // 判定
        // marshalメソッドに引数が渡されたことの確認
        // 引数out
        assertSame(in.getClass(), oxmapper.mappingClass);

        // Marshallerのvalidationがfalseになっていることの確認
        Boolean validate = (Boolean) UTUtil.getPrivateField(marshaller,
                "_validate");
        assertFalse(validate.booleanValue());
        // MarshallerにCastorOXMapperImpl_Stub01クラスが設定されていることを確認する
        ClassDescriptorResolver _cdResolver = (ClassDescriptorResolver) UTUtil
                .getPrivateField(marshaller, "_cdResolver");
        AbstractMappingLoader2 ml = (AbstractMappingLoader2) _cdResolver
                .getMappingLoader();
        Vector descriptors = (Vector) UTUtil
                .getPrivateField(ml, "_descriptors");
        assertEquals(1, descriptors.size());
        XMLClassDescriptorAdapter adapter = (XMLClassDescriptorAdapter) descriptors
                .get(0);
        assertEquals(testClass, adapter.getJavaClass());

        // マッピングオブジェクトがキャッシュされていないことを確認する
        Map resultMap = (HashMap) UTUtil.getPrivateField(oxmapper,
                "mappingFileCache");
        assertEquals(0, resultMap.size());
    }

    /**
     * testCreateMarshaller05() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A,D <br>
     * <br>
     * 入力値：(引数) in:CastorOXMapperImpl_Stub01<br>
     * (引数) writer:not null<br>
     * (状態) マッピング定義ファイル:正常なマッピング定義ファイル<br>
     * <?xml version="1.0"?><br>
     * <!DOCTYPE mapping PUBLIC "-//EXOLAB/Castor Object Mapping DTD Version
     * 1.0//EN" "http://castor.exolab.org/mapping.dtd"><br>
     * <mapping><br>
     * <class
     * name="jp.terasoluna.fw.oxm.mapper.castor.CastorOXMapperImpl_Stub01"><br>
     * <field name="param1" type="int" /><br>
     * <field name="param2" type="date" /><br>
     * <field name="param3" type="string" /><br>
     * </class><br>
     * </mapping><br>
     * (状態) getCastorMapping():呼び出されたことを確認する。<br>
     * 引数が渡されたことを確認する。<br>
     * (状態) this.cache:true<br>
     * (状態) this.mappingFileCache:空のHashMap<br>
     * 
     * <br>
     * 期待値：(戻り値) Marshaller:Marshaller{<br>
     * _validate = false;<br>
     * _cdResolver._mappingLoader._javaClass[0]=CastorOXMapperImpl_Stub01.class<br> }<br>
     * (状態変化) this.mappingFileCache:要素数が1のHashMap<br>
     * HashMap<br> ｛<br>
     * 引数のClassインスタンス=Mappingインスタンス<br> }<br>
     * 
     * <br>
     * マッピング定義ファイルが正しい場合、正常にマーシャラが生成されることを確認する。<br>
     * マッピングオブジェクトのキャッシュが行なわれていることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testCreateMarshaller05() throws Exception {
        // 前処理
        CastorOXMapperImpl_CastorOXMapperImplStub03 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub03();
        oxmapper.mappingPath = packagePath
                + "CastorOXMapperImpl_JavaBeanStub01" + mappingSuffix;
        Class testClass = CastorOXMapperImpl_JavaBeanStub01.class;

        // キャッシュ
        UTUtil.setPrivateField(oxmapper, "cache", true);
        Map<Class, Mapping> map = new HashMap<Class, Mapping>();
        UTUtil.setPrivateField(oxmapper, "mappingFileCache", map);

        // 引数
        Object in = new Object();
        Writer writer = new StringWriter();

        // テスト実施
        Marshaller marshaller = oxmapper.createMarshaller(in, writer);

        // 判定
        // marshalメソッドに引数が渡されたことの確認
        // 引数out
        assertSame(in.getClass(), oxmapper.mappingClass);

        // Marshallerのvalidationがfalseになっていることの確認
        Boolean validate = (Boolean) UTUtil.getPrivateField(marshaller,
                "_validate");
        assertFalse(validate.booleanValue());
        // MarshallerにCastorOXMapperImpl_Stub01クラスが設定されていることを確認する
        ClassDescriptorResolver _cdResolver = (ClassDescriptorResolver) UTUtil
                .getPrivateField(marshaller, "_cdResolver");
        AbstractMappingLoader2 ml = (AbstractMappingLoader2) _cdResolver
                .getMappingLoader();
        Vector descriptors = (Vector) UTUtil
                .getPrivateField(ml, "_descriptors");
        assertEquals(1, descriptors.size());
        XMLClassDescriptorAdapter adapter = (XMLClassDescriptorAdapter) descriptors
                .get(0);
        assertEquals(testClass, adapter.getJavaClass());

        // マッピングオブジェクトがキャッシュされていることを確認する
        Map resultMap = (HashMap) UTUtil.getPrivateField(oxmapper,
                "mappingFileCache");
        assertEquals(1, resultMap.size());
        assertSame(oxmapper.mapping, resultMap.get(in.getClass()));

    }

    /**
     * testCreateMarshaller06() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点:A,D <br>
     * <br>
     * 入力値：(引数) in:CastorOXMapperImpl_Stub01<br>
     * (引数) writer:not null<br>
     * (状態) マッピング定義ファイル:正常なマッピング定義ファイル<br>
     * <?xml version="1.0"?><br>
     * <!DOCTYPE mapping PUBLIC "-//EXOLAB/Castor Object Mapping DTD Version
     * 1.0//EN" "http://castor.exolab.org/mapping.dtd"><br>
     * <mapping><br>
     * <class
     * name="jp.terasoluna.fw.oxm.mapper.castor.CastorOXMapperImpl_Stub01"><br>
     * <field name="param1" type="int" /><br>
     * <field name="param2" type="date" /><br>
     * <field name="param3" type="string" /><br>
     * </class><br>
     * </mapping><br>
     * (状態) getCastorMapping():呼び出されたことを確認する。<br>
     * 引数が渡されたことを確認する。<br>
     * (状態) this.cache:true<br>
     * (状態) this.mappingFileCache:要素数が1のHashMap<br>
     * HashMap<br> ｛<br>
     * 引数のClassインスタンス=Mappingインスタンス<br> }<br>
     * 
     * <br>
     * 期待値：(戻り値) Marshaller:Marshaller{<br>
     * _validate = false;<br>
     * _cdResolver._mappingLoader._javaClass[0]=CastorOXMapperImpl_Stub01.class<br> }<br>
     * (状態変化) this.mappingFileCache:要素数が1のHashMap<br>
     * HashMap<br> ｛<br>
     * 引数のClassインスタンス=Mappingインスタンス<br> }<br>
     * 
     * <br>
     * マッピング定義ファイルが正しい場合、正常にマーシャラが生成されることを確認する。<br>
     * マッピングオブジェクトのキャッシュが行なわれず、要素数が増えていないことを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testCreateMarshaller06() throws Exception {
        // 前処理
        CastorOXMapperImpl_CastorOXMapperImplStub03 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub03();
        oxmapper.mappingPath = packagePath
                + "CastorOXMapperImpl_JavaBeanStub01" + mappingSuffix;
        Class testClass = CastorOXMapperImpl_JavaBeanStub01.class;

        // 引数
        Object in = new Object();
        Writer writer = new StringWriter();

        // キャッシュ
        UTUtil.setPrivateField(oxmapper, "cache", true);
        Map<Class, Mapping> map = new HashMap<Class, Mapping>();
        Mapping mapping = new Mapping();
        map.put(in.getClass(), mapping);
        UTUtil.setPrivateField(oxmapper, "mappingFileCache", map);

        // テスト実施
        Marshaller marshaller = oxmapper.createMarshaller(in, writer);

        // 判定
        // marshalメソッドに引数が渡されたことの確認
        // 引数out
        assertSame(in.getClass(), oxmapper.mappingClass);

        // Marshallerのvalidationがfalseになっていることの確認
        Boolean validate = (Boolean) UTUtil.getPrivateField(marshaller,
                "_validate");
        assertFalse(validate.booleanValue());
        // MarshallerにCastorOXMapperImpl_Stub01クラスが設定されていることを確認する
        ClassDescriptorResolver _cdResolver = (ClassDescriptorResolver) UTUtil
                .getPrivateField(marshaller, "_cdResolver");
        AbstractMappingLoader2 ml = (AbstractMappingLoader2) _cdResolver
                .getMappingLoader();
        Vector descriptors = (Vector) UTUtil
                .getPrivateField(ml, "_descriptors");
        assertEquals(1, descriptors.size());
        XMLClassDescriptorAdapter adapter = (XMLClassDescriptorAdapter) descriptors
                .get(0);
        assertEquals(testClass, adapter.getJavaClass());

        // マッピングオブジェクトがキャッシュされていないことを確認する
        Map resultMap = (HashMap) UTUtil.getPrivateField(oxmapper,
                "mappingFileCache");
        assertEquals(1, resultMap.size());
        assertSame(mapping, resultMap.get(in.getClass()));
    }

    /**
     * testCreateMarshaller07() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点:A,D <br>
     * <br>
     * 入力値：(引数) in:CastorOXMapperImpl_Stub01<br>
     * (引数) writer:not null<br>
     * (状態) マッピング定義ファイル:正常なマッピング定義ファイル<br>
     * <?xml version="1.0"?><br>
     * <!DOCTYPE mapping PUBLIC "-//EXOLAB/Castor Object Mapping DTD Version
     * 1.0//EN" "http://castor.exolab.org/mapping.dtd"><br>
     * <mapping><br>
     * <class
     * name="jp.terasoluna.fw.oxm.mapper.castor.CastorOXMapperImpl_Stub01"><br>
     * <field name="param1" type="int" /><br>
     * <field name="param2" type="date" /><br>
     * <field name="param3" type="string" /><br>
     * </class><br>
     * </mapping><br>
     * (状態) this.sharedResolverForMarshaller:キャッシュされていることを確認する（1回目）<br>
     * (状態) this.sharedResolverForMarshaller:キャッシュされていることを確認する（2回目）<br>
     * (状態) this.sharedResolverForMarshaller:1回目と2回目のインスタンスが同じであることを確認する<br>
     * 
     * <br>
     * マッピング定義ファイルが正しい場合、正常にClassDescriptorResolverがキャッシュされていることを確認する。<br>
     * ClassDescriptorResolverのキャッシュが行なわれ、2回目に同一インスタンスであることを確認する。<br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testCreateMarshaller07() throws Exception {
        // 前処理
        CastorOXMapperImpl_CastorOXMapperImplStub03 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub03();
        oxmapper.mappingPath = packagePath
                + "CastorOXMapperImpl_JavaBeanStub01" + mappingSuffix;

        // 引数
        Object in = new Object();
        Writer writer = new StringWriter();

        // キャッシュ
        UTUtil.setPrivateField(oxmapper, "cache", true);
        Map<Class, Mapping> map = new HashMap<Class, Mapping>();
        Mapping mapping = new Mapping();
        map.put(in.getClass(), mapping);
        UTUtil.setPrivateField(oxmapper, "mappingFileCache", map);

        // テスト実施
        Marshaller marshaller = oxmapper.createMarshaller(in, writer);
        assertNotNull(marshaller);

        // リゾルバがキャッシュされていることの確認
        Object resolver1 = UTUtil.getPrivateField(oxmapper,
                "sharedResolverForMarshaller");
        assertNotNull(resolver1);
        assertTrue(resolver1 instanceof org.exolab.castor.xml.XMLClassDescriptorResolver);
        assertTrue(resolver1 instanceof org.exolab.castor.xml.ClassDescriptorResolver);

        // テスト実施
        marshaller = oxmapper.createMarshaller(in, writer);
        assertNotNull(marshaller);

        // リゾルバがキャッシュされていることの確認
        Object resolver2 = UTUtil.getPrivateField(oxmapper,
                "sharedResolverForMarshaller");
        assertNotNull(resolver2);
        assertTrue(resolver2 instanceof org.exolab.castor.xml.XMLClassDescriptorResolver);
        assertTrue(resolver2 instanceof org.exolab.castor.xml.ClassDescriptorResolver);

        // 同じインスタンスであることの確認
        assertEquals(resolver1, resolver2);

    }

    /**
     * testCreateMarshaller08() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A,D <br>
     * <br>
     * 入力値：<br>
     * (引数) in:CastorOXMapperImpl_Stub01<br>
     * (引数) writer:not null<br>
     * (引数) suppressXSIType : true<br>
     * (状態) マッピング定義ファイル:正常なマッピング定義ファイル<br>
     * <?xml version="1.0"?><br>
     * <!DOCTYPE mapping PUBLIC "-//EXOLAB/Castor Object Mapping DTD Version
     * 1.0//EN" "http://castor.exolab.org/mapping.dtd"><br>
     * <mapping><br>
     * <class
     * name="jp.terasoluna.fw.oxm.mapper.castor.CastorOXMapperImpl_Stub01"><br>
     * <field name="param1" type="int" /><br>
     * <field name="param2" type="date" /><br>
     * <field name="param3" type="string" /><br>
     * </class><br>
     * </mapping><br>
     * (状態) getCastorMapping():呼び出されたことを確認する。<br>
     * 引数が渡されたことを確認する。<br>
     * (状態) this.cache:true<br>
     * (状態) this.mappingFileCache:空のHashMap<br>
     * 
     * <br>
     * 期待値：<br>
     * (状態変化) Marshaller の _suppressXSIType : true<br>
     * 
     * <br>
     * suppressXSIType の設定が Marshaller に渡されることを確認する。<br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testCreateMarshaller08() throws Exception {
        // 前処理
        CastorOXMapperImpl_CastorOXMapperImplStub03 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub03();
        oxmapper.mappingPath = packagePath
                + "CastorOXMapperImpl_JavaBeanStub01" + mappingSuffix;

        // キャッシュ
        UTUtil.setPrivateField(oxmapper, "cache", true);
        Map<Class, Mapping> map = new HashMap<Class, Mapping>();
        UTUtil.setPrivateField(oxmapper, "mappingFileCache", map);

        // 引数
        Object in = new Object();
        Writer writer = new StringWriter();

        UTUtil.setPrivateField(oxmapper, "suppressXSIType", true);

        // テスト実施
        Marshaller marshaller = oxmapper.createMarshaller(in, writer);

        // 判定
        Boolean suppressXSIType = (Boolean) UTUtil.getPrivateField(marshaller, "_suppressXSIType");
        
        assertTrue(suppressXSIType);
    }

    /**
     * testCreateMarshaller09() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A,D <br>
     * <br>
     * 入力値：<br>
     * (引数) in:CastorOXMapperImpl_Stub01<br>
     * (引数) writer:not null<br>
     * (引数) suppressXSIType : true<br>
     * (状態) マッピング定義ファイル:正常なマッピング定義ファイル<br>
     * <?xml version="1.0"?><br>
     * <!DOCTYPE mapping PUBLIC "-//EXOLAB/Castor Object Mapping DTD Version
     * 1.0//EN" "http://castor.exolab.org/mapping.dtd"><br>
     * <mapping><br>
     * <class
     * name="jp.terasoluna.fw.oxm.mapper.castor.CastorOXMapperImpl_Stub01"><br>
     * <field name="param1" type="int" /><br>
     * <field name="param2" type="date" /><br>
     * <field name="param3" type="string" /><br>
     * </class><br>
     * </mapping><br>
     * (状態) getCastorMapping():呼び出されたことを確認する。<br>
     * 引数が渡されたことを確認する。<br>
     * (状態) this.cache:true<br>
     * (状態) this.mappingFileCache:空のHashMap<br>
     * 
     * <br>
     * 期待値：<br>
     * (状態変化) Marshaller の _suppressXSIType : false<br>
     * 
     * <br>
     * suppressXSIType の設定が Marshaller に渡されることを確認する。<br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testCreateMarshaller09() throws Exception {
        // 前処理
        CastorOXMapperImpl_CastorOXMapperImplStub03 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub03();
        oxmapper.mappingPath = packagePath
                + "CastorOXMapperImpl_JavaBeanStub01" + mappingSuffix;

        // キャッシュ
        UTUtil.setPrivateField(oxmapper, "cache", true);
        Map<Class, Mapping> map = new HashMap<Class, Mapping>();
        UTUtil.setPrivateField(oxmapper, "mappingFileCache", map);

        // 引数
        Object in = new Object();
        Writer writer = new StringWriter();

        UTUtil.setPrivateField(oxmapper, "suppressXSIType", false);

        // テスト実施
        Marshaller marshaller = oxmapper.createMarshaller(in, writer);

        // 判定
        Boolean suppressXSIType = (Boolean) UTUtil.getPrivateField(marshaller, "_suppressXSIType");
        
        assertFalse(suppressXSIType);
    }    

    /**
     * testCreateMarshaller10() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A,D <br>
     * <br>
     * 入力値：<br>
     * (引数) in:CastorOXMapperImpl_Stub01<br>
     * (引数) writer:not null<br>
     * (引数) preserveWhitespaceAtMarshal : true<br>
     * (状態) マッピング定義ファイル:正常なマッピング定義ファイル<br>
     * <?xml version="1.0"?><br>
     * <!DOCTYPE mapping PUBLIC "-//EXOLAB/Castor Object Mapping DTD Version
     * 1.0//EN" "http://castor.exolab.org/mapping.dtd"><br>
     * <mapping><br>
     * <class
     * name="jp.terasoluna.fw.oxm.mapper.castor.CastorOXMapperImpl_Stub01"><br>
     * <field name="param1" type="int" /><br>
     * <field name="param2" type="date" /><br>
     * <field name="param3" type="string" /><br>
     * </class><br>
     * </mapping><br>
     * (状態) getCastorMapping():呼び出されたことを確認する。<br>
     * 引数が渡されたことを確認する。<br>
     * (状態) this.cache:true<br>
     * (状態) this.mappingFileCache:空のHashMap<br>
     * 
     * <br>
     * 期待値：<br>
     * (状態変化) Marshaller の ContentHandler の preserveWhitespace : true<br>
     * 
     * <br>
     * preserveWhitespace の設定が Marshaller の ContentHandler に渡されることを確認する。<br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testCreateMarshaller10() throws Exception {
        // 前処理
        CastorOXMapperImpl_CastorOXMapperImplStub03 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub03();
        oxmapper.mappingPath = packagePath
                + "CastorOXMapperImpl_JavaBeanStub01" + mappingSuffix;

        // キャッシュ
        UTUtil.setPrivateField(oxmapper, "cache", true);
        Map<Class, Mapping> map = new HashMap<Class, Mapping>();
        UTUtil.setPrivateField(oxmapper, "mappingFileCache", map);

        // 引数
        CastorOXMapperImpl_JavaBeanStub01 in = new CastorOXMapperImpl_JavaBeanStub01();
        Writer writer = new StringWriter();

        UTUtil.setPrivateField(oxmapper, "preserveWhitespaceAtMarshal", true);

        // テスト実施
        Marshaller marshaller = oxmapper.createMarshaller(in, writer);
        marshaller.marshal(in);
        
        // 判定
        XMLSerializerEx handler = (XMLSerializerEx) UTUtil.getPrivateField(marshaller, "_handler");
        Boolean preserveWhitespace = (Boolean) UTUtil.getPrivateField(handler, "preserveWhitespace");
        
        assertTrue(preserveWhitespace);
    }       

    /**
     * testCreateMarshaller11() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A,D <br>
     * <br>
     * 入力値：<br>
     * (引数) in:CastorOXMapperImpl_Stub01<br>
     * (引数) writer:not null<br>
     * (引数) preserveWhitespaceAtMarshal : false<br>
     * (状態) マッピング定義ファイル:正常なマッピング定義ファイル<br>
     * <?xml version="1.0"?><br>
     * <!DOCTYPE mapping PUBLIC "-//EXOLAB/Castor Object Mapping DTD Version
     * 1.0//EN" "http://castor.exolab.org/mapping.dtd"><br>
     * <mapping><br>
     * <class
     * name="jp.terasoluna.fw.oxm.mapper.castor.CastorOXMapperImpl_Stub01"><br>
     * <field name="param1" type="int" /><br>
     * <field name="param2" type="date" /><br>
     * <field name="param3" type="string" /><br>
     * </class><br>
     * </mapping><br>
     * (状態) getCastorMapping():呼び出されたことを確認する。<br>
     * 引数が渡されたことを確認する。<br>
     * (状態) this.cache:true<br>
     * (状態) this.mappingFileCache:空のHashMap<br>
     * 
     * <br>
     * 期待値：<br>
     * (状態変化) Marshaller の ContentHandler の preserveWhitespace : false<br>
     * 
     * <br>
     * preserveWhitespace の設定が Marshaller の ContentHandler に渡されることを確認する。<br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testCreateMarshaller11() throws Exception {
        // 前処理
        CastorOXMapperImpl_CastorOXMapperImplStub03 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub03();
        oxmapper.mappingPath = packagePath
                + "CastorOXMapperImpl_JavaBeanStub01" + mappingSuffix;

        // キャッシュ
        UTUtil.setPrivateField(oxmapper, "cache", true);
        Map<Class, Mapping> map = new HashMap<Class, Mapping>();
        UTUtil.setPrivateField(oxmapper, "mappingFileCache", map);

        // 引数
        CastorOXMapperImpl_JavaBeanStub01 in = new CastorOXMapperImpl_JavaBeanStub01();
        Writer writer = new StringWriter();

        UTUtil.setPrivateField(oxmapper, "preserveWhitespaceAtMarshal", false);

        // テスト実施
        Marshaller marshaller = oxmapper.createMarshaller(in, writer);
        marshaller.marshal(in);

        // 判定
        XMLSerializerEx handler = (XMLSerializerEx) UTUtil.getPrivateField(marshaller, "_handler");
        Boolean preserveWhitespace = (Boolean) UTUtil.getPrivateField(handler, "preserveWhitespace");
        
        assertFalse(preserveWhitespace);
    }     
    
    /**
     * testGetCastorMapping01() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) mappingClass:Classインスタンス<br>
     * (状態) this.cache:true<br>
     * (状態) this.mappingFileCache:要素数が1のHashMap<br>
     * HashMap<br> ｛<br>
     * 引数のClassインスタンス=Mappingインスタンス<br> }<br>
     * 
     * <br>
     * 期待値：(戻り値) Mapping:キャッシュされたMappingインスタンス<br>
     * (状態変化) this.mappingFileCache:要素数が1のHashMap<br>
     * HashMap<br> ｛<br>
     * 引数のClassインスンス=Mappingインスタンス<br> }<br>
     * 
     * <br>
     * 引数のインスタンスをキーとしたマッピングインスタンスがキャッシュに存在する場合、キャッシュされたインスタンスが返却されることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    @SuppressWarnings("unchecked")
    public void testGetCastorMapping01() throws Exception {
        // 前処理
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();
        UTUtil.setPrivateField(oxmapper, "cache", true);

        Class testClass = CastorOXMapperImpl_JavaBeanStub01.class;
        Mapping mapping = new Mapping();
        HashMap map = (HashMap) UTUtil.getPrivateField(oxmapper,
                "mappingFileCache");
        map.put(testClass, mapping);

        // テスト実施
        Mapping result = oxmapper.getCastorMapping(testClass);

        // 判定
        assertSame(mapping, result);

    }

    /**
     * testGetCastorMapping02() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) mappingClass:Classインスタンス<br>
     * (状態) this.cache:true<br>
     * (状態) this.mappingFileCache:not null<br>
     * (状態) getUrl():null<br>
     * 
     * <br>
     * 期待値：(戻り値) null<br>
     * 
     * <br>
     * 引数のオブジェクトと同じパスにファァイルが存在しない場合、nullが返却されることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testGetCastorMapping02() throws Exception {
        // 前処理
        CastorOXMapperImpl_CastorOXMapperImplStub04 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub04();
        UTUtil.setPrivateField(oxmapper, "cache", true);

        Class testClass = CastorOXMapperImpl_JavaBeanStub01.class;

        // テスト実施
        Mapping result = oxmapper.getCastorMapping(testClass);
        assertNull(result);
    }

    /**
     * testGetCastorMapping03() <br>
     * <br>
     * 
     * (異常系) <br>
     * 観点：A,G <br>
     * <br>
     * 入力値：(引数) mappingClass:Classインスタンス<br>
     * (状態) this.cache:true<br>
     * (状態) this.mappingFileCache:not null<br>
     * (状態) getUrl():呼び出されたことを確認する。<br>
     * 引数が渡されたことを確認する。<br>
     * (状態) マッピング定義ファイル:不正なマッピング定義ファイル<br>
     * <?xml version="1.0"?><br>
     * <!DOCTYPE mapping PUBLIC "-//EXOLAB/Castor Object Mapping DTD Version
     * 1.0//EN" "http://castor.exolab.org/mapping.dtd"><br>
     * <mapping><br>
     * <class name="jp.terasoluna.fw.oxm.mapper.castor.xxxDTO"><br>
     * <field name="param1" type="int" /><br>
     * <field name="param2" type="date" /><br>
     * <field name="param3" type="string" /><br>
     * </class><br>
     * </mapping<br>
     * 
     * <br>
     * 期待値：(状態変化) 例外:MappingException<br>
     * (状態変化) ログ:ログレベル:error<br>
     * Nested error: XML document structures must start and end within the same
     * entity.<br>
     * 
     * <br>
     * マッピング定義ファイルの形式が不正な場合、例外がスローされることを確認する。<br>
     * （マッピング定義の形式に問題があると、パース時に発生するSAXExceptionをラップしたMappingExceptionがスローされる）
     * <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testGetCastorMapping03() throws Exception {
        // 前処理
        CastorOXMapperImpl_CastorOXMapperImplStub05 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub05();
        UTUtil.setPrivateField(oxmapper, "cache", true);

        // getUrlメソッドの呼び出し確認に使用するクラス
        Class testClass = CastorOXMapperImpl_JavaBeanStub01.class;

        // 形式が不正なマッピング定義ファイルのパス
        oxmapper.path = packagePath + "CastorOXMapperImpl_JavaBeanStub03"
                + mappingSuffix;

        // テスト実施
        try {
            Unmarshaller unmarshaller = new Unmarshaller(new Object());
            Mapping mapping = oxmapper.getCastorMapping(testClass);
            unmarshaller.setMapping(mapping);
            fail();
        } catch (MappingException e) {
            // 判定
            assertSame(MappingException.class, e.fillInStackTrace().getClass());

            // ログ確認
            String message = "Nested error: XML document structures must start and end within the same entity.";
            assertTrue(e.getMessage().startsWith(message));
        }
    }

    /**
     * testGetCastorMapping04() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A,D <br>
     * <br>
     * 入力値：(引数) mappingClass:Classインスタンス<br>
     * (状態) this.cache:false<br>
     * (状態) this.mappingFileCache:空のHashMap<br>
     * (状態) getUrl():呼び出されたことを確認する。<br>
     * 引数が渡されたことを確認する。<br>
     * (状態) マッピング定義ファイル:正常なマッピング定義ファイル<br>
     * 
     * <br>
     * 期待値：(戻り値) Mapping:生成されたMappingインスタンス<br>
     * (状態変化) this.mappingFileCache:空のHashMap<br>
     * 
     * <br>
     * マッピング定義ファイルの形式が正常な場合、マッピングインスタンスが返却されることを確認する。 this.cacheがfalseの場合のパターン。
     * <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testGetCastorMapping04() throws Exception {
        // 前処理
        CastorOXMapperImpl_CastorOXMapperImplStub05 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub05();
        UTUtil.setPrivateField(oxmapper, "cache", false);

        // getUrlメソッドの呼び出し確認に使用するクラス
        Class testClass = CastorOXMapperImpl_JavaBeanStub01.class;

        // 正常なマッピング定義ファイルのパス
        oxmapper.path = packagePath + "CastorOXMapperImpl_JavaBeanStub01"
                + mappingSuffix;

        // 空のMapを設定する
        UTUtil.setPrivateField(oxmapper, "mappingFileCache", new HashMap());

        // テスト実施
        Unmarshaller unmarshaller = new Unmarshaller(new Object());
        Mapping mapping = oxmapper.getCastorMapping(testClass);
        unmarshaller.setMapping(mapping);

        // 判定
        // getUrlメソッドに引数が渡されていることを確認する
        assertEquals(testClass, oxmapper.mappingClass);

        // マッピング定義がロードされていることを確認する
        ClassMapping[] classMappingList = mapping.getRoot().getClassMapping();
        assertEquals(1, classMappingList.length);
        assertEquals(testClass.getName(), classMappingList[0].getName());

    }

    /**
     * testGetCastorMapping05() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A,D <br>
     * <br>
     * 入力値：(引数) mappingClass:Classインスタンス<br>
     * (状態) this.cache:true<br>
     * (状態) this.mappingFileCache:空のHashMap<br>
     * (状態) getUrl():呼び出されたことを確認する。<br>
     * 引数が渡されたことを確認する。<br>
     * (状態) マッピング定義ファイル:正常なマッピング定義ファイル<br>
     * 
     * <br>
     * 期待値：(戻り値) Mapping:生成されたMappingインスタンス<br>
     * (状態変化) this.mappingFileCache:要素数が1のHashMap<br>
     * HashMap<br> ｛<br>
     * 引数のClassインスンス=生成されたMappingインスタンス<br> }<br>
     * 
     * <br>
     * マッピング定義ファイルの形式が正常な場合、マッピングインスタンスが返却されることを確認する。<br>
     * this.cacheがtrueでマッピングインスタンスがキャッシュに存在しないパターン。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testGetCastorMapping05() throws Exception {
        // 前処理
        CastorOXMapperImpl_CastorOXMapperImplStub05 oxmapper = new CastorOXMapperImpl_CastorOXMapperImplStub05();
        UTUtil.setPrivateField(oxmapper, "cache", true);

        // getUrlメソッドの呼び出し確認に使用するクラス
        Class testClass = CastorOXMapperImpl_JavaBeanStub01.class;

        // 正常なマッピング定義ファイルのパス
        oxmapper.path = packagePath + "CastorOXMapperImpl_JavaBeanStub01"
                + mappingSuffix;

        // キャッシュを空にする
        UTUtil.setPrivateField(oxmapper, "mappingFileCache", new HashMap());

        // テスト実施
        Unmarshaller unmarshaller = new Unmarshaller(new Object());
        Mapping mapping = oxmapper.getCastorMapping(testClass);
        unmarshaller.setMapping(mapping);

        // 判定
        // getUrlメソッドに引数が渡されていることを確認する
        assertEquals(testClass, oxmapper.mappingClass);

        // マッピング定義がロードされていることを確認する
        ClassMapping[] classMappingList = mapping.getRoot().getClassMapping();
        assertEquals(1, classMappingList.length);
        assertEquals(testClass.getName(), classMappingList[0].getName());

    }

    /**
     * testGetUrl01() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) mappingClass:not null<br>
     * （対応するマッピング定義ファイルが存在するクラス）<br>
     * 
     * <br>
     * 期待値：(戻り値) URL:URLインスタンス<br>
     * 
     * <br>
     * 引数のクラスに対応するパス（mappingClassのパッケージパス + mappingClassのクラス名 +
     * ".xml"）にマッピング定義ファイルが存在する場合、 URLインスタンスを返却することを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testGetUrl01() throws Exception {
        // 前処理
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();

        // 対応するマッピング定義ファイルが存在するクラス
        Class mappingClass = CastorOXMapperImpl_JavaBeanStub01.class;

        // テスト実施
        URL result = oxmapper.getUrl(mappingClass);

        // 判定
        // 生成されたURLインスタンスがpathを含んでいることを確認する
        String mappingPath = packagePath + mappingClass.getSimpleName()
                + mappingSuffix;
        assertTrue(result.getPath().contains(mappingPath));
    }

    /**
     * testGetUrl02() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) mappingClass:not null<br>
     * （対応するマッピング定義ファイルが存在するクラス）<br>
     * 
     * <br>
     * 期待値：(戻り値) URL:null<br>
     * 
     * <br>
     * 引数のクラスに対応するパス（mappingClassのパッケージパス + mappingClassのクラス名 +
     * ".xml"）にマッピング定義ファイルが存在しない場合、 nullを返却することを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testGetUrl02() throws Exception {
        // 前処理
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();

        // 対応するマッピング定義ファイルが存在しないクラス
        Class mappingClass = CastorOXMapperImpl.class;

        // テスト実施
        URL result = oxmapper.getUrl(mappingClass);

        // 判定
        assertNull(result);
    }

    /**
     * testIsCache01() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：(状態) this.cache:true<br>
     * 
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     * 
     * <br>
     * 属性が正しく返却されることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testIsCache01() throws Exception {
        // 前処理
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();
        UTUtil.setPrivateField(oxmapper, "cache", false);

        // テスト実施
        boolean result = oxmapper.isCache();

        // 判定
        assertFalse(result);
    }

    /**
     * testSetCache01() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) cache:false<br>
     * (状態) this.cache:true<br>
     * 
     * <br>
     * 期待値：(状態変化) this.cache:false<br>
     * 
     * <br>
     * 引数の値が属性に正しく設定されることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testSetCache01() throws Exception {
        // 前処理
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();
        UTUtil.setPrivateField(oxmapper, "cache", true);

        // テスト実施
        oxmapper.setCache(false);

        // 判定
        assertFalse(((Boolean) UTUtil.getPrivateField(oxmapper, "cache"))
                .booleanValue());
    }

    /**
     * testGetCharset01() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：(状態) this.charset:"EUC"<br>
     * 
     * <br>
     * 期待値：(戻り値) charset:"EUC"<br>
     * 
     * <br>
     * 属性の値が正しく返却されることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testGetCharset01() throws Exception {
        // 前処理
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();
        UTUtil.setPrivateField(oxmapper, "charset", "EUC");

        // テスト実施
        String result = oxmapper.getCharset();

        // 判定
        assertEquals("EUC", result);

    }

    /**
     * testSetCharset01() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) charset:"EUC"<br>
     * (状態) this.charset:"UTF-8"<br>
     * 
     * <br>
     * 期待値：(状態変化) this.charset:"EUC"<br>
     * 
     * <br>
     * 引数の値が属性に正しく設定されることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testSetCharset01() throws Exception {
        // 前処理
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();
        UTUtil.setPrivateField(oxmapper, "charset", "UTF-8");

        // テスト実施
        oxmapper.setCharset("EUC");

        // 判定
        assertEquals("EUC", UTUtil.getPrivateField(oxmapper, "charset"));
    }

    /**
     * testSetSuppressXSIType01() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) suppressXSIType:true<br>
     * (状態) this.suppressXSIType:false<br>
     * 
     * <br>
     * 期待値：(状態変化) this.suppressXSIType:true<br>
     * 
     * <br>
     * 引数の値が属性に正しく設定されることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testSetSuppressXSIType01() throws Exception {
        // 前処理
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();
        UTUtil.setPrivateField(oxmapper, "suppressXSIType", false);

        // テスト実施
        oxmapper.setSuppressXSIType(true);

        // 判定
        assertEquals(true, UTUtil.getPrivateField(oxmapper, "suppressXSIType"));
    }

    /**
     * testSetPreserveWhitespaceAtUnmarshal01() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) whitespacePreserve:true<br>
     * (状態) this.whitespacePreserve:false<br>
     * 
     * <br>
     * 期待値：(状態変化) this.whitespacePreserve:true<br>
     * 
     * <br>
     * 引数の値が属性に正しく設定されることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testSetPreserveWhitespaceAtUnmarshal01() throws Exception {
        // 前処理
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();
        UTUtil
                .setPrivateField(oxmapper, "preserveWhitespaceAtUnmarshal",
                        false);

        // テスト実施
        oxmapper.setPreserveWhitespaceAtUnmarshal(true);

        // 判定
        assertEquals(true, UTUtil.getPrivateField(oxmapper,
                "preserveWhitespaceAtUnmarshal"));
    }

    /**
     * testSetPreserveWhitespaceAtMarshal01() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) whitespacePreserve:true<br>
     * (状態) this.whitespacePreserve:false<br>
     * 
     * <br>
     * 期待値：(状態変化) this.whitespacePreserve:true<br>
     * 
     * <br>
     * 引数の値が属性に正しく設定されることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testSetPreserveWhitespaceAtMarshal01() throws Exception {
        // 前処理
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();
        UTUtil.setPrivateField(oxmapper, "preserveWhitespaceAtMarshal", false);

        // テスト実施
        oxmapper.setPreserveWhitespaceAtMarshal(true);

        // 判定
        assertEquals(true, UTUtil.getPrivateField(oxmapper,
                "preserveWhitespaceAtMarshal"));
    }
    
    /**
     * testSetIndenting01() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) indenting:false<br>
     * (状態) this.indenting:true<br>
     * 
     * <br>
     * 期待値：(状態変化) this.indenting:false<br>
     * 
     * <br>
     * 引数の値が属性に正しく設定されることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testSetIndenting01() throws Exception {
        // 前処理
        CastorOXMapperImpl oxmapper = new CastorOXMapperImpl();
        UTUtil.setPrivateField(oxmapper, "indenting", true);

        // テスト実施
        oxmapper.setIndenting(false);

        // 判定
        assertEquals(false, UTUtil.getPrivateField(oxmapper, "indenting"));
    }

}
