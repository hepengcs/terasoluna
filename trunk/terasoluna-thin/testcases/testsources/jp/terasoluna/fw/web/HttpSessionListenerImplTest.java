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

package jp.terasoluna.fw.web;

import java.io.File;
import java.io.FileInputStream;

import jp.terasoluna.fw.util.FileUtil;
import jp.terasoluna.fw.util.PropertyUtil;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpSession;
import jp.terasoluna.utlib.PropertyTestCase;

/**
 * {@link jp.terasoluna.fw.web.HttpSessionListenerImpl} 
 * クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * HTTPセッションのライフサイクルイベントを処理するクラス。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.HttpSessionListenerImpl
 */
public class HttpSessionListenerImplTest extends PropertyTestCase {

    /**
     * 初期化処理を行う。
     * 
     * @throws Exception このメソッドで発生した例外
     * @see jp.terasoluna.utlib.spring.PropertyTestCase#setUpData()
     */
    @Override
    protected void setUpData() throws Exception {
        LogUTUtil.flush();
        
        addProperty("session.dir.base", "c:/sessions");
        
        // ファイル削除
        FileUtil.removeSessionDirectory("dummyID");
    }

    /**
     * 終了処理を行う。
     * 
     * @throws Exception このメソッドで発生した例外
     * @see jp.terasoluna.utlib.spring.PropertyTestCase#cleanUpData()
     */
    @Override
    protected void cleanUpData() throws Exception {
        // ファイル削除
        FileUtil.removeSessionDirectory("dummyID");
    }

    /**
     * コンストラクタ。
     * 
     * @param name このテストケースの名前。
     */
    public HttpSessionListenerImplTest(String name) {
        super(name);
    }

    /**
     * testSessionDestroyed01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) event:not null<br>
     *         (状態) session.getId():"dummyID"<br>
     *         (状態) dir.exists():false<br>
     *         (状態) dir.isDirectory():false<br>
     *         (状態) ディレクトリ
     *         ("c:/sessions/" + FileUtil.getSessionDirectoryName("dummyID"))<br>
     *         :存在しない場合<br>
     *         (状態) プロパティ:session.dir.base=<br>
     *                c:/sessions<br>
     *         
     * <br>
     * 期待値：(状態変化) ディレクトリ
     *         ("c:/sessions/" + FileUtil.getSessionDirectoryName("dummyID"))<br>
     *         :存在しない<br>
     *         
     * <br>
     * セッションディレクトリが存在しない場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSessionDestroyed01() throws Exception {
        // 前処理
        HttpSessionListenerImpl listener = new HttpSessionListenerImpl();
        
        MockHttpSession session = new MockHttpSession();
        // HttpSessionEvent
        HttpSessionListenerImpl_HttpSessionEventStub01 event =
            new HttpSessionListenerImpl_HttpSessionEventStub01(session);
        
        // テスト実施
        listener.sessionDestroyed(event);
        
        // 判定
        File file = new File(PropertyUtil.getProperty("session.dir.base") + "/"
                + FileUtil.getSessionDirectoryName("dummyID"));
        
        assertFalse(file.exists());
    }

    /**
     * testSessionDestroyed02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) event:not null<br>
     *         (状態) session.getId():"dummyID"<br>
     *         (状態) dir.exists():true<br>
     *         (状態) dir.isDirectory():false<br>
     *         (状態) ディレクトリ
     *         ("c:/sessions/" + FileUtil.getSessionDirectoryName("dummyID"))<br>
     *         :存在するが、ディレクトリでない場合<br>
     *         (状態) プロパティ:session.dir.base=<br>
     *                c:/sessions<br>
     *         
     * <br>
     * 期待値：(状態変化) ディレクトリ
     *         ("c:/sessions/" + FileUtil.getSessionDirectoryName("dummyID"))<br>
     *         :削除されない<br>
     *         
     * <br>
     * セッションディレクトリが存在するが、ディレクトリでない場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSessionDestroyed02() throws Exception {
        // 前処理
        // セッションディレクトリ作成
        File file = new File(PropertyUtil.getProperty("session.dir.base")
                + "/" + FileUtil.getSessionDirectoryName("dummyID"));
        
        file.createNewFile();
        
        
        // 前処理
        HttpSessionListenerImpl listener = new HttpSessionListenerImpl();
        
        MockHttpSession session = new MockHttpSession();
        // HttpSessionEvent
        HttpSessionListenerImpl_HttpSessionEventStub01 event =
            new HttpSessionListenerImpl_HttpSessionEventStub01(session);
        
        // テスト実施
        listener.sessionDestroyed(event);
        
        // 判定
        File result = new File(PropertyUtil.getProperty("session.dir.base")
                + "/" + FileUtil.getSessionDirectoryName("dummyID"));
        
        assertTrue(result.exists());
        
        // 後処理：ファイルの削除→ディレクトリでないためcleanUpData()では削除されない
        file.delete();
        
    }

    /**
     * testSessionDestroyed03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) event:not null<br>
     *         (状態) session.getId():"dummyID"<br>
     *         (状態) dir.exists():true<br>
     *         (状態) dir.isDirectory():true<br>
     *         (状態) ディレクトリ
     *         ("c:/sessions/" + FileUtil.getSessionDirectoryName("dummyID"))<br>
     *         :ディレクトリ
     *         (状態) プロパティ:session.dir.base=<br>
     *                c:/sessions<br>
     *         
     * <br>
     * 期待値：(状態変化) ディレクトリ
     *         ("c:/sessions/" + FileUtil.getSessionDirectoryName("dummyID"))<br>
     *         :削除される<br>
     *         
     * <br>
     * セッションディレクトリが存在し、ディレクトリの場合、削除されたかを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSessionDestroyed03() throws Exception {
        // 前処理
        // セッションディレクトリ作成
        FileUtil.makeSessionDirectory("dummyID");
                
        // 前処理
        HttpSessionListenerImpl listener = new HttpSessionListenerImpl();
        
        MockHttpSession session = new MockHttpSession();
        // HttpSessionEvent
        HttpSessionListenerImpl_HttpSessionEventStub01 event =
            new HttpSessionListenerImpl_HttpSessionEventStub01(session);
        
        // テスト実施
        listener.sessionDestroyed(event);
        
        // 判定
        File result = new File(PropertyUtil.getProperty("session.dir.base")
                + "/" + FileUtil.getSessionDirectoryName("dummyID"));
        
        assertFalse(result.exists());
    }
    

    /**
     * testSessionDestroyed04()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) event:not null<br>
     *         (状態) session.getId():"dummyID"<br>
     *         (状態) dir.exists():true<br>
     *         (状態) dir.isDirectory():true<br>
     *         (状態) FileUtil.removeSessionDirectory():true<br>
     *         (状態) ディレクトリ<br>
     *                ("c:/sessions/" +　FileUtil.getSessionDirectoryName("dummyID")):ディレクトリ（削除できない）<br>
     *         (状態) プロパティ:session.dir.base=<br>
     *                c:/sessions<br>
     *         
     * <br>
     * 期待値：(状態変化) ディレクトリ<br>
     *                    ("c:/sessions/" + FileUtil.getSessionDirectoryName("dummyID")):削除されない<br>
     *         (状態変化) ログ:<errorレベル><br>
     *                    メッセージ："can't remove \"" + dir.getPath() + "\"."<br>
     *         
     * <br>
     * セッションディレクトリが存在し、ディレクトリであるが削除できない場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("unused")
    public void testSessionDestroyed04() throws Exception {
        // 前処理
        // セッションディレクトリ作成
        FileUtil.makeSessionDirectory("dummyID");
        // ディレクトリ内にファイル作成
        File file = new File(PropertyUtil.getProperty("session.dir.base")
                + "/" + FileUtil.getSessionDirectoryName("dummyID")
                + "/test.txt");
        file.createNewFile();
        FileInputStream fileInputStream = new FileInputStream(file);

        // 前処理
        HttpSessionListenerImpl listener = new HttpSessionListenerImpl();
        
        MockHttpSession session = new MockHttpSession();
        // HttpSessionEvent
        HttpSessionListenerImpl_HttpSessionEventStub01 event =
            new HttpSessionListenerImpl_HttpSessionEventStub01(session);

        // テスト実施
        listener.sessionDestroyed(event);

        // 判定
        File result = new File(PropertyUtil.getProperty("session.dir.base")
                + "/" + FileUtil.getSessionDirectoryName("dummyID"));

        assertTrue(result.exists());
        assertTrue(LogUTUtil.checkError("can't remove \"" + result.getPath()
                + "\"."));

        // FileInputStreamをクローズ
        fileInputStream.close();
    }
}