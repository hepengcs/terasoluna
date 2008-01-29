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

package jp.terasoluna.fw.web.rich.springmvc.servlet.view.filedownload;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletResponse;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.rich.springmvc.servlet.view.filedownload.AbstractFileDownloadView} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * バイナリファイルをダウンロードする際に利用するView抽象クラス。<br>
 * 前提条件：model、HTTPリクエスト、HTTPレスポンスはNull値にならない。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.servlet.view.filedownload.AbstractFileDownloadView
 */
public class AbstractFileDownloadViewTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(AbstractFileDownloadViewTest.class);
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
    public AbstractFileDownloadViewTest(String name) {
        super(name);
    }

    /**
     * testRenderMergedOutputModel01()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：A,G
     * <br><br>
     * 入力値：(引数) model:not null<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) getInputStream():Nullを返すパターン。<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:IOException<br>
     *                    （FileDownload Failed. InputStream is null.）<br>
     *         (状態変化) ログ:ログレベル：エラー<br>
     *                    FileDownload Failed. InputStream is null.<br>
     *         
     * <br>
     * サブクラスで定義する入力ストリーム取得メソッドがNullを返したパターン。
     * IO例外を投げる。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testRenderMergedOutputModel01() throws Exception {
        // 前処理
        AbstractFileDownloadViewStub01 view = 
            new AbstractFileDownloadViewStub01();
        MockHttpServletResponse response = 
            new MockHttpServletResponse();
        Map<String, String> model = new HashMap<String, String>();

        // テスト実施
        try {
            view.renderMergedOutputModel(model, null, response);
            fail();
        } catch (IOException e) {
            // OK
            String expect = "FileDownload Failed. InputStream is null.";
            assertEquals(expect, e.getMessage());
            assertTrue(LogUTUtil.checkError(expect));
        }

        // 判定
        assertFalse(view.isAddResponseHeader);
    }

    /**
     * testRenderMergedOutputModel02()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：A,G
     * <br><br>
     * 入力値：(引数) model:not null<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) getInputStream():IO例外が発生したパターン。<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:IOException<br>
     *         (状態変化) ログ:ログレベル：エラーFileDownload Failed.<br>
     *         
     * <br>
     * サブクラスで定義する入力ストリーム取得メソッドがIO例外を投げたパターン。
     * ログ出力し、例外をそのまま投げる。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testRenderMergedOutputModel02() throws Exception {
        // 前処理
        AbstractFileDownloadViewStub02 view = 
            new AbstractFileDownloadViewStub02();
        MockHttpServletResponse response = 
            new MockHttpServletResponse();
        Map<String, String> model = new HashMap<String, String>();

        // テスト実施
        try {
            view.renderMergedOutputModel(model, null, response);
            fail();
        } catch (IOException e) {
            // OK
            assertTrue(LogUTUtil.checkError("FileDownload Failed.", e));
        }

        // 判定
        assertFalse(view.isAddResponseHeader);
    }

    /**
     * testRenderMergedOutputModel03()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：A,G
     * <br><br>
     * 入力値：(引数) model:not null<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) getInputStream():正常に入力ストリームを返すパターン。<br>
     *         
     * <br>
     * 期待値：(状態変化) inputStream.close():
     *         メソッドが呼び出されたことを確認する。<br>
     *         (状態変化) 例外:IOException<br>
     *         (状態変化) ログ:ログレベル：エラーFileDownload Failed.<br>
     *         
     * <br>
     * 出力ストリーム生成時に例外が発生する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testRenderMergedOutputModel03() throws Exception {
        // 前処理
        AbstractFileDownloadViewStub03 view = 
            new AbstractFileDownloadViewStub03();
        AbstractFileDownloadView_ByteArrayInputStreamStub01 in = 
            new AbstractFileDownloadView_ByteArrayInputStreamStub01(
                    "test".getBytes());
        view.setInputStream(in);
        AbstractFileDownloadView_MockHttpServletResponseStub01 response = 
            new AbstractFileDownloadView_MockHttpServletResponseStub01();
        Map<String, String> model = new HashMap<String, String>();

        // テスト実施
        try {
            view.renderMergedOutputModel(model, null, response);
            fail();
        } catch (IOException e) {
            // OK
            assertTrue(LogUTUtil.checkError("FileDownload Failed.", e));
        }

        // 判定
        assertTrue(in.isClose);
    }

    /**
     * testRenderMergedOutputModel04()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：A,G
     * <br><br>
     * 入力値：(引数) model:not null<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) addResponseHeader():正常に処理を行なうパターン。<br>
     *         (状態) getInputStream():正常に入力ストリームを返すパターン。<br>
     *         (状態) writeResponseStream（）:IO例外を返すパターン。<br>
     *         
     * <br>
     * 期待値：(状態変化) addResponseHeader():
     *         メソッドが呼び出されたことを確認する。<br>
     *                    引数を受け取ったことを確認する。<br>
     *         (状態変化) inputStream.close():
     *         メソッドが呼び出されたことを確認する。<br>
     *         (状態変化) outputStream.close():
     *         メソッドが呼び出されたことを確認する。<br>
     *         (状態変化) 例外:IOException<br>
     *         (状態変化) ログ:ログレベル：エラーFileDownload Failed.<br>
     *         
     * <br>
     * 書き込みメソッドwriteResponseStreamでIO例外が発生するパターンのテスト。
     * ログ出力し、例外をそのまま投げる。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testRenderMergedOutputModel04() throws Exception {
        // 前処理
        AbstractFileDownloadViewStub04 view = 
            new AbstractFileDownloadViewStub04();
        AbstractFileDownloadView_ByteArrayInputStreamStub01 in = 
            new AbstractFileDownloadView_ByteArrayInputStreamStub01(
                    "test".getBytes());
        view.setInputStream(in);
        MockHttpServletResponse response =  new MockHttpServletResponse();
        AbstractFileDownloadView_ServletOutputStreamStub01 out = 
            new AbstractFileDownloadView_ServletOutputStreamStub01();
        response.setOutputStream(out);
        Map<String, String> model = new HashMap<String, String>();

        // テスト実施
        try {
            view.renderMergedOutputModel(model, null, response);
            fail();
        } catch (IOException e) {
            // OK
            assertTrue(LogUTUtil.checkError("FileDownload Failed.", e));
        }

        // 判定
        assertTrue(view.isAddResponseHeader);
        assertTrue(in.isClose);
        assertTrue(out.isClose);
    }

    /**
     * testRenderMergedOutputModel05()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：A,G
     * <br><br>
     * 入力値：(引数) model:not null<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) addResponseHeader():
     *         正常に処理を行なうパターン。<br>
     *         (状態) getInputStream():正常に入力ストリームを返すパターン。<br>
     *         (状態) writeResponseStream（）:正常に書き込みを行う<br>
     *         
     * <br>
     * 期待値：(状態変化) addResponseHeader():
     *         メソッドが呼び出されたことを確認する。<br>
     *                    引数を受け取ったことを確認する。<br>
     *         (状態変化) inputStream.close():
     *         メソッドが呼び出されたことを確認する。<br>
     *         (状態変化) outputStream.close():
     *         メソッドが呼び出されたことを確認する。<br>
     *         (状態変化) 例外:IOException<br>
     *         (状態変化) ログ:ログレベル：エラーFileDownload Failed.<br>
     *         
     * <br>
     * 出力ストリームのフラッシュに失敗した場合のテスト。
     * フラッシュ時に投げられた例外をそのままスローする。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testRenderMergedOutputModel05() throws Exception {
        // 前処理
        AbstractFileDownloadViewStub03 view = 
            new AbstractFileDownloadViewStub03();
        AbstractFileDownloadView_ByteArrayInputStreamStub01 in = 
            new AbstractFileDownloadView_ByteArrayInputStreamStub01(
                    "test".getBytes());
        view.setInputStream(in);
        MockHttpServletResponse response =  new MockHttpServletResponse();
        AbstractFileDownloadView_ServletOutputStreamStub02 out = 
            new AbstractFileDownloadView_ServletOutputStreamStub02();
        response.setOutputStream(out);
        Map<String, String> model = new HashMap<String, String>();

        // テスト実施
        try {
            view.renderMergedOutputModel(model, null, response);
            fail();
        } catch (IOException e) {
            // OK
            assertTrue(LogUTUtil.checkError("FileDownload Failed.", e));
        }

        // 判定
        assertTrue(view.isAddResponseHeader);
        assertTrue(in.isClose);
        assertTrue(out.isClose);
    }

    /**
     * testRenderMergedOutputModel06()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) model:not null<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (状態) addResponseHeader():正常に処理を行なうパターン。<br>
     *         (状態) getInputStream():正常に入力ストリームを返すパターン。<br>
     *         (状態) writeResponseStream（）:正常に書き込みを行う<br>
     *         
     * <br>
     * 期待値：(状態変化) response:入力ストリームのデータが書き込まれる。<br>
     *         (状態変化) addResponseHeader():
     *         メソッドが呼び出されたことを確認する。<br>
     *                    引数を受け取ったことを確認する。<br>
     *         (状態変化) inputStream.close():
     *         メソッドが呼び出されたことを確認する。<br>
     *         (状態変化) outputStream.close():
     *         メソッドが呼び出されたことを確認する。<br>
     *         
     * <br>
     * 正常に書き込みが完了し、
     * 入力ストリーム・出力ストリームをクローズするテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testRenderMergedOutputModel06() throws Exception {
        // 前処理
        AbstractFileDownloadViewStub03 view = 
            new AbstractFileDownloadViewStub03();
        byte[] bytearray = "test".getBytes();
        AbstractFileDownloadView_ByteArrayInputStreamStub01 in = 
            new AbstractFileDownloadView_ByteArrayInputStreamStub01(
                    bytearray);
        view.setInputStream(in);
        MockHttpServletResponse response =  new MockHttpServletResponse();
        AbstractFileDownloadView_ServletOutputStreamStub01 out = 
            new AbstractFileDownloadView_ServletOutputStreamStub01();
        response.setOutputStream(out);
        Map<String, String> model = new HashMap<String, String>();

        // テスト実施
        view.renderMergedOutputModel(model, null, response);
        
        // 判定
        for (int i=0;i<bytearray.length;i++) {
            // byte配列の値を確認
            assertEquals(bytearray[i], out.responseData[i]);
        }
        assertTrue(view.isAddResponseHeader);
        assertTrue(in.isClose);
        assertTrue(out.isClose);
    }

    /**
     * testWriteResponseStream01()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) inputStream:null<br>
     *         (引数) outputStream:not null<br>
     *         
     * <br>
     * 期待値：(状態変化) outputStream:変化なし<br>
     *         
     * <br>
     * 入力ストリームがNullの場合のテスト。<br>
     * 何も処理をせず終了する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testWriteResponseStream01() throws Exception {
        // 前処理
        AbstractFileDownloadViewStub01 view = 
            new AbstractFileDownloadViewStub01();
        AbstractFileDownloadView_ServletOutputStreamStub03 out = 
            new AbstractFileDownloadView_ServletOutputStreamStub03();
        
        // テスト実施
        view.writeResponseStream(null, out);
        
        // 判定
        assertFalse(out.isWrite);
    }

    /**
     * testWriteResponseStream02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) inputStream:not null<br>
     *         (引数) outputStream:null<br>
     *         
     * <br>
     * 期待値：(状態変化) outputStream:変化なし<br>
     *         
     * <br>
     * 出力ストリームがNullの場合のテスト。<br>
     * 何も処理をせず終了する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testWriteResponseStream02() throws Exception {
        // 前処理
        AbstractFileDownloadViewStub01 view = 
            new AbstractFileDownloadViewStub01();
        AbstractFileDownloadView_ByteArrayInputStreamStub01 in = 
            new AbstractFileDownloadView_ByteArrayInputStreamStub01(
                    "test".getBytes());
        
        // テスト実施
        view.writeResponseStream(in, null);
        
        // 判定
        assertFalse(in.isRead);
    }

    /**
     * testWriteResponseStream03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,D
     * <br><br>
     * 入力値：(引数) inputStream:not null<br>
     *                ファイルサイズ：０バイト<br>
     *         (引数) outputStream:not null<br>
     *         
     * <br>
     * 期待値：(状態変化) outputStream:変化なし<br>
     *         
     * <br>
     * 入力ストリームのファイルサイズが０の場合のテスト。何も処理をせず終了する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testWriteResponseStream03() throws Exception {
        // 前処理
        AbstractFileDownloadViewStub01 view = 
            new AbstractFileDownloadViewStub01();
        byte[] byteData = new byte[0]; 
        InputStream in = new ByteArrayInputStream(byteData);
        AbstractFileDownloadView_ServletOutputStreamStub04 out = 
            new AbstractFileDownloadView_ServletOutputStreamStub04();
        
        // テスト実施
        view.writeResponseStream(in, out);
        
        // 判定
        assertEquals(0, out.count);
    }

    /**
     * testWriteResponseStream04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,B,D
     * <br><br>
     * 入力値：(引数) inputStream:not null<br>
     *                ファイルサイズ：２５６バイト<br>
     *         (引数) outputStream:not null<br>
     *         
     * <br>
     * 期待値：(状態変化) outputStream:出力ストリームに
     * 入力ストリームのデータを書き込む<br>
     *         
     * <br>
     * 入力ストリームのファイルサイズが２５６の場合のテスト。
     * チャンクサイズ２５６バイトと同じバイト数のファイルを書き込むパターン。
     * 書き込みのループを１回行なう。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testWriteResponseStream04() throws Exception {
        // 前処理
        AbstractFileDownloadViewStub01 view = 
            new AbstractFileDownloadViewStub01();
        byte[] byteData = new byte[256]; 
        for (int i=0;i<256;i++) {
            byteData[i] = 1;
        }
        InputStream in = new ByteArrayInputStream(byteData);
        AbstractFileDownloadView_ServletOutputStreamStub04 out = 
            new AbstractFileDownloadView_ServletOutputStreamStub04();
        
        // テスト実施
        view.writeResponseStream(in, out);
        
        // 判定
        assertEquals(1, out.count);
    }

    /**
     * testWriteResponseStream05()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A,B,D
     * <br><br>
     * 入力値：(引数) inputStream:not null<br>
     *                ファイルサイズ：２５７バイト<br>
     *         (引数) outputStream:not null<br>
     *         
     * <br>
     * 期待値：(状態変化) outputStream:出力ストリームに
     * 入力ストリームのデータを書き込む<br>
     *         
     * <br>
     * 入力ストリームのファイルサイズが２５７の場合のテスト。
     * チャンクサイズ２５６バイトより大きいファイルを書き込むパターン。
     * 書き込みのループを２回行なう。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testWriteResponseStream05() throws Exception {
        // 前処理
        AbstractFileDownloadViewStub01 view = 
            new AbstractFileDownloadViewStub01();
        byte[] byteData = new byte[257]; 
        for (int i=0;i<257;i++) {
            byteData[i] = 1;
        }
        InputStream in = new ByteArrayInputStream(byteData);
        AbstractFileDownloadView_ServletOutputStreamStub04 out = 
            new AbstractFileDownloadView_ServletOutputStreamStub04();
        
        // テスト実施
        view.writeResponseStream(in, out);
        
        // 判定
        assertEquals(2, out.count);
    }

    /**
     * testWriteResponseStream06()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A,B,D
     * <br><br>
     * 入力値：(引数) inputStream:not null<br>
     *                ファイルサイズ：７００バイト<br>
     *         (引数) outputStream:not null<br>
     *         
     * <br>
     * 期待値：(状態変化) outputStream:出力ストリームに
     * 入力ストリームのデータを書き込む<br>
     *         
     * <br>
     * 入力ストリームのファイルサイズが７００の場合のテスト。
     * チャンクサイズ２５６バイトより大きいファイルを書き込むパターン。
     * 書き込みのループを３回行なう。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testWriteResponseStream06() throws Exception {
        // 前処理
        AbstractFileDownloadViewStub01 view = 
            new AbstractFileDownloadViewStub01();
        byte[] byteData = new byte[700]; 
        for (int i=0;i<700;i++) {
            byteData[i] = 1;
        }
        InputStream in = new ByteArrayInputStream(byteData);
        AbstractFileDownloadView_ServletOutputStreamStub04 out = 
            new AbstractFileDownloadView_ServletOutputStreamStub04();
        
        // テスト実施
        view.writeResponseStream(in, out);
        
        // 判定
        assertEquals(3, out.count);
    }

}
