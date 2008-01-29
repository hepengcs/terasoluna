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

package jp.terasoluna.fw.service.rich.exception;

import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.service.rich.exception.ServiceException;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.service.rich.exception.ServiceException}
 * クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * サービス層のクラスで新規発生する例外をあらわすクラス。
 * <p>
 * 
 * @see jp.terasoluna.fw.service.rich.exception.ServiceException
 */
public class ServiceExceptionTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ServiceExceptionTest.class);
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
    public ServiceExceptionTest(String name) {
        super(name);
    }

    /**
     * testServiceException01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C, D
     * <br><br>
     * 入力値：(引数) errorCode:"AAA"<br>
     *         (引数) options:{"AAA", "BBB","CCC"}<br>
     *         
     * <br>
     * 期待値：(状態変化) errorCode:"AAA"<br>
     *         (状態変化) options:{"AAA", "BBB","CCC"}<br>
     *         
     * <br>
     * 引き数の値を正常に属性に設定すること。(エラーコードnot null)
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testServiceException01() throws Exception {
        // 前処理
        // errorCode : "AAA"
        String errorCode = "AAA";
        // options : {"AAA", "BBB", "CCC"}
        String[] options = {"AAA", "BBB", "CCC"};

        // テスト実施
        ServiceException e = new ServiceException(errorCode, options);

        // 判定
        // errorCode
        assertEquals(errorCode, UTUtil.getPrivateField(e, "errorCode"));
        
        // options
        String[] getOptions = (String[]) UTUtil.getPrivateField(e, "options");
        for(int i=0; i<options.length; i++) {
            assertEquals(options[i], getOptions[i]);
        }
        assertEquals(3, getOptions.length);
        
        // optionsが同じインスタンスかをチェック
        // 以下からはこの判定は省略
        assertSame(options, getOptions);
    }

    /**
     * testServiceException02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C, D
     * <br><br>
     * 入力値：(引数) errorCode:""<br>
     *         (引数) options:{"AAA", "BBB","CCC"}<br>
     *         
     * <br>
     * 期待値：(状態変化) errorCode:""<br>
     *         (状態変化) options:{"AAA", "BBB","CCC"}<br>
     *         
     * <br>
     * 引き数の値を正常に属性に設定すること。(エラーコード空文字)
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testServiceException02() throws Exception {
        // 前処理
        // errorCode : ""
        String errorCode = "";
        // options : {"AAA", "BBB", "CCC"}
        String[] options = {"AAA", "BBB", "CCC"};

        // テスト実施
        ServiceException e = new ServiceException(errorCode, options);

        // 判定
        // errorCode
        assertEquals(errorCode, UTUtil.getPrivateField(e, "errorCode"));
        
        // options
        String[] getOptions = (String[]) UTUtil.getPrivateField(e, "options");
        for(int i=0; i<options.length; i++) {
            assertEquals(options[i], getOptions[i]);
        }
        assertEquals(3, getOptions.length);
    }

    /**
     * testServiceException03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C, D
     * <br><br>
     * 入力値：(引数) errorCode:null<br>
     *         (引数) options:{"AAA", "BBB","CCC"}<br>
     *         
     * <br>
     * 期待値：(状態変化) errorCode:null<br>
     *         (状態変化) options:{"AAA", "BBB","CCC"}<br>
     *         
     * <br>
     * 引き数の値を正常に属性に設定すること。(エラーコード null)
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testServiceException03() throws Exception {
        // 前処理
        // errorCode : null
        String errorCode = null;
        // options : {"AAA", "BBB", "CCC"}
        String[] options = {"AAA", "BBB", "CCC"};

        // テスト実施
        ServiceException e = new ServiceException(errorCode, options);

        // 判定
        // errorCode
        assertNull(UTUtil.getPrivateField(e, "errorCode"));
        
        // options
        String[] getOptions = (String[]) UTUtil.getPrivateField(e, "options");
        for(int i=0; i<options.length; i++) {
            assertEquals(options[i], getOptions[i]);
        }
        assertEquals(3, getOptions.length);
    }

    /**
     * testServiceException04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C, D
     * <br><br>
     * 入力値：(引数) errorCode:"AAA"<br>
     *         (引数) options:{"AAA"}<br>
     *         
     * <br>
     * 期待値：(状態変化) errorCode:"AAA"<br>
     *         (状態変化) options:{"AAA"}<br>
     *         
     * <br>
     * 引き数の値を正常に属性に設定すること。(options not null)
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testServiceException04() throws Exception {
        // 前処理
        // errorCode : "AAA"
        String errorCode = "AAA";
        // options : {"AAA"}
        String[] options = {"AAA"};

        // テスト実施
        ServiceException e = new ServiceException(errorCode, options);

        // 判定
        // errorCode
        assertEquals(errorCode, UTUtil.getPrivateField(e, "errorCode"));
        
        // options
        String[] getOptions = (String[]) UTUtil.getPrivateField(e, "options");
        for(int i=0; i<options.length; i++) {
            assertEquals(options[i], getOptions[i]);
        }
        assertEquals(1, getOptions.length);
    }

    /**
     * testServiceException05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C, D
     * <br><br>
     * 入力値：(引数) errorCode:"AAA"<br>
     *         (引数) options:{}<br>
     *         
     * <br>
     * 期待値：(状態変化) errorCode:"AAA"<br>
     *         (状態変化) options:{}<br>
     *         
     * <br>
     * 引き数の値を正常に属性に設定すること。(options 空文字)
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testServiceException05() throws Exception {
        // 前処理
        // errorCode : "AAA"
        String errorCode = "AAA";
        // options : {}
        String[] options = {};

        // テスト実施
        ServiceException e = new ServiceException(errorCode, options);

        // 判定
        // errorCode
        assertEquals(errorCode, UTUtil.getPrivateField(e, "errorCode"));
        
        // options
        String[] getOptions = (String[]) UTUtil.getPrivateField(e, "options");
        assertEquals(0, getOptions.length);
    }

    /**
     * testServiceException06()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C, D
     * <br><br>
     * 入力値：(引数) errorCode:"AAA"<br>
     *         (引数) options:null<br>
     *         
     * <br>
     * 期待値：(状態変化) errorCode:"AAA"<br>
     *         (状態変化) options:null<br>
     *         
     * <br>
     * 引き数の値を正常に属性に設定すること。(options  null)
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testServiceException06() throws Exception {
        // 前処理
        // errorCode : "AAA"
        String errorCode = "AAA";
        // options : null
        String[] options = null;

        // テスト実施
        ServiceException e = new ServiceException(errorCode, options);

        // 判定
        // errorCode
        assertEquals(errorCode, UTUtil.getPrivateField(e, "errorCode"));
        
        // options
        String[] getOptions = (String[]) UTUtil.getPrivateField(e, "options");
        assertNull(getOptions);
    }

    /**
     * testServiceException07()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C, D
     * <br><br>
     * 入力値：(引数) errorCode:"AAA"<br>
     *         (引数) options:指定しない<br>
     *         
     * <br>
     * 期待値：(状態変化) errorCode:"AAA"<br>
     *         (状態変化) options:{}<br>
     *         
     * <br>
     * 引き数の値を正常に属性に設定すること。(optionsを指定しない)
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testServiceException07() throws Exception {
        // 前処理
        // errorCode : "AAA"
        String errorCode = "AAA";

        // テスト実施
        ServiceException e = new ServiceException(errorCode);

        // 判定
        // errorCode
        assertEquals(errorCode, UTUtil.getPrivateField(e, "errorCode"));
        
        // options
        String[] getOptions = (String[]) UTUtil.getPrivateField(e, "options");
        assertEquals(0, getOptions.length);
    }

    /**
     * testGetErrorCode01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) なし:－<br>
     *         (状態) errorCode:"AAA"<br>
     *         
     * <br>
     * 期待値：(戻り値) errorCode:"AAA"<br>
     *         
     * <br>
     * 属性に設定されている値を正常に取得すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetErrorCode01() throws Exception {
        // 前処理
        // errorCode : "AAA"
        String errorCode = "AAA";
        
        ServiceException e = new ServiceException(errorCode);

        // テスト実施
        String getErrorCode = e.getErrorCode();

        // 判定
        assertEquals(errorCode, getErrorCode);
    }

    /**
     * testGetOptions01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(引数) なし:－<br>
     *         (状態) options:["aaa", "bbb"]<br>
     *         
     * <br>
     * 期待値：(戻り値) options:属性と同一のインスタンス<br>
     *         
     * <br>
     * 属性に設定されている値を正常に取得すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetOptions01() throws Exception {
        // 前処理
        // errorCode : "AAA"
        String errorCode = "AAA";
        // options : {"aaa", "bbb"}
        String[] options = {"aaa", "bbb"};
        
        ServiceException e = new ServiceException(errorCode, options);

        // テスト実施
        String[] getOptions = e.getOptions();

        // 判定
        assertSame(options, getOptions);
    }

}
