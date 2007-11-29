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

package jp.terasoluna.fw.orm.ibatis.support;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.support.lob.LobHandler;

import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.orm.ibatis.support.ClobReaderTypeHandler;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.orm.ibatis.support.ClobReaderTypeHandler}
 * クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * iBATISから利用されるClobとストリームをマッピングする実装のiBATISのタイプハンドラ。
 * <p>
 * 
 * @see jp.terasoluna.fw.orm.ibatis.support.ClobReaderTypeHandler
 */
@SuppressWarnings("unused")
public class ClobReaderTypeHandlerTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ClobReaderTypeHandlerTest.class);
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
    public ClobReaderTypeHandlerTest(String name) {
        super(name);
    }

    /**
     * testClobReaderTypeHandler01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) lobHandler:not null<br>
     *         
     * <br>
     * 期待値：(状態変化) lobHandler:引き数lobHandlerと同一のlobHandler<br>
     *         
     * <br>
     * 引き数がnot nullの場合、属性に設定することのテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testClobReaderTypeHandler01() throws Exception {
        // 前処理
        LobHandler lob = new LobHandlerImpl01();

        // テスト実施
        ClobReaderTypeHandler handler = new ClobReaderTypeHandler(lob);

        // 判定
        assertSame(lob, UTUtil.getPrivateField(handler, "lobHandler"));
    }

    /**
     * testClobReaderTypeHandler02()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) lobHandler:null<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:IllegalStateException<br>
     *         
     * <br>
     * 引数がnullの場合、IllegalStateExceptionがスローをすることのテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testClobReaderTypeHandler02() throws Exception {
        // 前処理
        LobHandler lob = null;

        // テスト実施
        try {
            ClobReaderTypeHandler handler =
                new ClobReaderTypeHandler(lob);
            fail();
        } catch (IllegalStateException e) {
            // 判定            
        }
    }

    /**
     * testSetParameterInternal01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) value:Readerインスタンス<br>
     *         (状態) lobCreator.setClobAsCharacterStream():正常<br>
     *         
     * <br>
     * 期待値：(戻り値) void:正常<br>
     *         (状態変化) lobCreator.setClobAsCharacterStream():呼び出されていることを確認<br>
     *         
     * <br>
     * lobCreator.setClobAsCharacterStream()が実行されていることのテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetParameterInternal01() throws Exception {
        // 前処理
        LobHandler lob = new LobHandlerImpl01();
        
        PreparedStatement ps = null;
        int index = 0;
        // value : Reader
        StringReader value = new StringReader("");
        String jdbcType = null;
        // LobCreator実装クラス : 呼び出し確認
        LobCreatorImpl01 lobCreator = new LobCreatorImpl01();
        
        ClobReaderTypeHandler handler = new ClobReaderTypeHandler(lob);

        // テスト実施
        handler.setParameterInternal(ps, index, value, jdbcType, lobCreator);

        // 判定
        boolean b = ((Boolean) lobCreator.isSetClobAsCharacterStream)
        .booleanValue();
        assertTrue(b);
        
        value.close();
    }

    /**
     * testSetParameterInternal02()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) value:Reader以外のインスタンス<br>
     *         
     * <br>
     * 期待値：(状態変化) なし:ClassCastExceptinon<br>
     *         
     * <br>
     * ClassCastExceptionがスローされることのテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetParameterInternal02() throws Exception {
        // 前処理
        LobHandler lob = new LobHandlerImpl01();
        
        PreparedStatement ps = null;
        int index = 0;
        // value : Reader以外
        String value = "";
        String jdbcType = null;
        // LobCreator実装クラス
        LobCreatorImpl01 lobCreator = new LobCreatorImpl01();
        
        ClobReaderTypeHandler handler = new ClobReaderTypeHandler(lob);

        try {
            // テスト実施
            handler.setParameterInternal(ps, index, value, jdbcType, lobCreator);
            fail();
        } catch (ClassCastException e) {
            // 判定
        }
    }

    /**
     * testSetParameterInternal03()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(状態) lobCreator.setClobAsCharacterStream():SQLException<br>
     *         
     * <br>
     * 期待値：(状態変化) なし:SQLException<br>
     *         
     * <br>
     * SQLExceptionがスローされることのテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetParameterInternal03() throws Exception {
        // 前処理
        LobHandler lob = new LobHandlerImpl01();
        
        PreparedStatement ps = null;
        int index = 0;
        // value : -
        StringReader value = null;
        String jdbcType = null;
        // LobCreator実装クラス : SQLException
        LobCreatorImpl02 lobCreator = new LobCreatorImpl02();
        
        ClobReaderTypeHandler handler = new ClobReaderTypeHandler(lob);

        try {
            // テスト実施
            handler.setParameterInternal(ps, index, value, jdbcType, lobCreator);
            fail();
        } catch (SQLException e) {
            // 判定
        }
    }

    /**
     * testGetResultInternal01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) lobHandler.getClobAsCharacterStream():not null<br>
     *         
     * <br>
     * 期待値：(戻り値) Object:lobHandler.getClobAsCharacterStream()の戻り値と同一インスタンス<br>
     *         (状態変化) lobHandler.getClobAsCharacterStream():呼び出されていることを確認<br>
     *         
     * <br>
     * lobHandler.getClobAsCharacterStream()の戻り値と同一インスタンスを返却することのテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetResultInternal01() throws Exception {
        // 前処理
        LobHandlerImpl01 lob = new LobHandlerImpl01();
        
        ResultSet ps = null;
        int index = 0;
        
        // LobHandler実装クラスのgetClobAsCharacterStreamのリターン値 : 
        // StringReaderインスタンス
        StringReader sr = new StringReader("");
        UTUtil.setPrivateField(lob, "r", sr);
        
        ClobReaderTypeHandler handler =
            new ClobReaderTypeHandler(lob);

        // テスト実施
        StringReader reader =
            (StringReader) handler.getResultInternal(ps, index, lob);

        // 判定
        boolean b = ((Boolean) lob.isGetClobAsCharacterStream).booleanValue();
        assertTrue(b);
        assertSame(sr, reader);
        
        sr.close();
    }

    /**
     * testGetResultInternal02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) lobHandler.getClobAsCharacterStream():null<br>
     *         
     * <br>
     * 期待値：(戻り値) Object:null<br>
     *         (状態変化) lobHandler.getClobAsCharacterStream():呼び出されていることを確認<br>
     *         
     * <br>
     * nullを返却することのテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetResultInternal02() throws Exception {
        // 前処理
        LobHandlerImpl01 lob = new LobHandlerImpl01();
        
        ResultSet ps = null;
        int index = 0;
        
        // LobHandler実装クラスのgetClobAsCharacterStreamのリターン値 : null
        StringReader sr = null;
        UTUtil.setPrivateField(lob, "r", sr);
        
        ClobReaderTypeHandler handler =
            new ClobReaderTypeHandler(lob);

        // テスト実施
        StringReader reader =
            (StringReader) handler.getResultInternal(ps, index, lob);

        // 判定
        boolean b = ((Boolean) lob.isGetClobAsCharacterStream).booleanValue();
        assertTrue(b);
        assertNull(reader);
    }

    /**
     * testGetResultInternal03()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(状態) lobHandler.getClobAsCharacterStream():SQLException<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:SQLException<br>
     *         
     * <br>
     * SQLExceptionがスローされることのテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetResultInternal03() throws Exception {
        // 前処理
        // getClobAsCharacterStream : SQLException
        LobHandlerImpl02 lob = new LobHandlerImpl02();
        
        ResultSet ps = null;
        int index = 0;
        
        ClobReaderTypeHandler handler =
            new ClobReaderTypeHandler(lob);

        try {
            // テスト実施
            StringReader reader =
                (StringReader) handler.getResultInternal(ps, index, lob);
            fail();
        } catch (SQLException e) {
            // 判定
        }
    }

    /**
     * testValueOf01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) s:null<br>
     *         
     * <br>
     * 期待値：(戻り値) Object:null<br>
     *         
     * <br>
     * nullを返却することのテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testValueOf01() throws Exception {
        // 前処理
        LobHandlerImpl01 lob = new LobHandlerImpl01();
        
        ClobReaderTypeHandler handler =
            new ClobReaderTypeHandler(lob);
        
        String s = null;

        // テスト実施
        Object obj = handler.valueOf(s);

        // 判定
        assertNull(obj);
    }

    /**
     * testValueOf02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) s:""<br>
     *         
     * <br>
     * 期待値：(戻り値) Object:new StringReader("".getBytes())<br>
     *         
     * <br>
     * ""を文字ストリーム化したものを返却することのテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testValueOf02() throws Exception {
        // 前処理
        LobHandlerImpl01 lob = new LobHandlerImpl01();
        
        ClobReaderTypeHandler handler =
            new ClobReaderTypeHandler(lob);
        
        String s = "";

        // テスト実施
        Object obj = handler.valueOf(s);

        // 判定
        assertEquals(StringReader.class.getName(),
                obj.getClass().getName());
        String str = (String) UTUtil.getPrivateField(obj, "str");
        assertEquals("", str);
    }

    /**
     * testValueOf03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) s:"ABC"<br>
     *         
     * <br>
     * 期待値：(戻り値) Object:new StringReader("ABC".getBytes())<br>
     *         
     * <br>
     * "ABC"を文字ストリーム化したものを返却するテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testValueOf03() throws Exception {
        // 前処理
        LobHandlerImpl01 lob = new LobHandlerImpl01();
        
        ClobReaderTypeHandler handler =
            new ClobReaderTypeHandler(lob);
        
        String s = "ABC";

        // テスト実施
        Object obj = handler.valueOf(s);

        // 判定
        assertEquals(StringReader.class.getName(),
                obj.getClass().getName());
        String str = (String) UTUtil.getPrivateField(obj, "str");
        assertEquals("ABC", str);
    }
}