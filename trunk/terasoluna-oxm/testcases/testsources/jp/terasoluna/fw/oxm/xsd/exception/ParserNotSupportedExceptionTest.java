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

package jp.terasoluna.fw.oxm.xsd.exception;

import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.oxm.xsd.exception.ParserNotSupportedException;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.oxm.xsd.exception.ParserNotSupportedException} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * Castorのマッピング定義ファイルが不正な場合に、スローされるバインド処理例外。
 * <p>
 * 
 * @see jp.terasoluna.fw.oxm.xsd.exception.ParserNotSupportedException
 */
public class ParserNotSupportedExceptionTest extends TestCase {
    
    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ParserNotSupportedExceptionTest.class);
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
    public ParserNotSupportedExceptionTest(String name) {
        super(name);
    }

    /**
     * testParserNotSupportedException01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) cause:Throwableインスタンス<br>
     *         
     * <br>
     * 期待値：(状態変化) cause:引数と同一のThrowableインスタンス<br>
     *         
     * <br>
     * 引数のインスタンスが属性に設定されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testParserNotSupportedException01() throws Exception {
        
        // 入力値の設定。
        Throwable cause = new Throwable();
        // テスト対象コンストラクタの実行。
        ParserNotSupportedException ce = new ParserNotSupportedException(cause);

        // 出力値の確認。
        Throwable throwCause =
            ((Throwable) UTUtil.getPrivateField(ce, "cause"));
        
        assertSame(cause, throwCause);

    }

    /**
     * testParserNotSupportedException02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A,C
     * <br><br>
     * 入力値：(引数) cause:null<br>
     *         
     * <br>
     * 期待値：(状態変化) cause:null<br>
     *         
     * <br>
     * 引数がnullの場合、属性にnullが設定されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testParserNotSupportedException02() throws Exception {
        
        // テスト対象コンストラクタの実行。
        ParserNotSupportedException ce = new ParserNotSupportedException(null);

        // 出力値の確認。
        Throwable throwCause =
            ((Throwable) UTUtil.getPrivateField(ce, "cause"));
        
        assertNull(throwCause);

    }

}
