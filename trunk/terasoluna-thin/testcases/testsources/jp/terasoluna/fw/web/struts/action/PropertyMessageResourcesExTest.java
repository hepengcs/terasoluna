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

package jp.terasoluna.fw.web.struts.action;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.struts.action.PropertyMessageResourcesEx} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * システムと業務共通のメッセージリソースを表示可能にする
 * プロパティファイルメッセージリソース。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.action.PropertyMessageResourcesEx
 */
public class PropertyMessageResourcesExTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(PropertyMessageResourcesExTest.class);
    }


    /**
     * コンストラクタ。
     * 
     * @param name このテストケースの名前。
     */
    public PropertyMessageResourcesExTest(String name) {
        super(name);
    }

    /**
     * testGetMessage01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) locale:Locale.JAPAN<br>
     *         (引数) key:"test.message"<br>
     *         (状態) factory:PropertyMessageResourcesExFactoryインスタンス<br>
     *         (状態) config:"PropertyMessageResourcesEx_MessageResources01"<br>
     *                【ファイル内容】<br>
     *                test.message=テストメッセージ０１<br>
     *         (状態) returnNull:false<br>
     *         (状態) system-messagesの中:空<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"テストメッセージ０１"<br>
     *         
     * <br>
     * メッセージリソース定義ファイルからのメッセージ文言が返却されることを
     * 確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetMessage01() throws Exception {
        // 前処理
        PropertyMessageResourcesExFactory factory
            = new PropertyMessageResourcesExFactory();
        PropertyMessageResourcesEx resources 
            = new PropertyMessageResourcesEx(factory,
                PropertyMessageResourcesEx.class.getPackage().getName().replace('.', '/')
                + "/PropertyMessageResourcesEx_MessageResources01",
                false);
        Object fwMessages 
            = UTUtil.getPrivateField(GlobalMessageResources.getInstance(),
                                     "fwMessages");
        Object globalMessages 
            = UTUtil.getPrivateField(GlobalMessageResources.getInstance(),
                                     "globalMessages");
        
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                                 "fwMessages", new HashMap());
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                                 "globalMessages", new HashMap());
        
        
        // テスト実施
        String actual = resources.getMessage(Locale.JAPAN, "test.message");

        // 判定
        assertEquals(actual, "テストメッセージ０１");
        
        // 元に戻す
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                "fwMessages", fwMessages);
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                "globalMessages", globalMessages);
    }

    /**
     * testGetMessage02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) locale:Locale.JAPAN<br>
     *         (引数) key:"test.message"<br>
     *         (状態) factory:PropertyMessageResourcesExFactoryインスタンス<br>
     *         (状態) config:"PropertyMessageResourcesEx_MessageResources02"<br>
     *                （空のファイル）<br>
     *         (状態) returnNull:false<br>
     *         (状態) system-messagesの中:test.message=ＦＷテストメッセージ０１<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"ＦＷテストメッセージ０１"<br>
     *         
     * <br>
     * メッセージリソース定義ファイルに与えられたキーが登録されていないが、
     * GlobalMessageResourcesに登録されているシステムのメッセージキーに
     * 一致するものが含まれる場合、システムのメッセージ文言が返却されることを
     * 確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetMessage02() throws Exception {
        // 前処理
        PropertyMessageResourcesExFactory factory
            = new PropertyMessageResourcesExFactory();
        PropertyMessageResourcesEx resources 
            = new PropertyMessageResourcesEx(factory,
                PropertyMessageResourcesEx.class.getPackage().getName().replace('.', '/')
                + "/PropertyMessageResourcesEx_MessageResources02",
                false);
        Object fwMessages 
            = UTUtil.getPrivateField(GlobalMessageResources.getInstance(),
                                     "fwMessages");
        Object globalMessages 
            = UTUtil.getPrivateField(GlobalMessageResources.getInstance(),
                                     "globalMessages");
        Map<String, String> newFwMessages = new HashMap<String, String>(1);
        newFwMessages.put("test.message", "ＦＷテストメッセージ０１");
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                                 "fwMessages", newFwMessages);
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                                 "globalMessages", new HashMap());
        
        
        // テスト実施
        String actual = resources.getMessage(Locale.JAPAN, "test.message");

        // 判定
        assertEquals(actual, "ＦＷテストメッセージ０１");
        
        // 元に戻す
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                "fwMessages", fwMessages);
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                "globalMessages", globalMessages);
    }

    /**
     * testGetMessage03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) locale:Locale.JAPAN<br>
     *         (引数) key:"test.message"<br>
     *         (状態) factory:PropertyMessageResourcesExFactoryインスタンス<br>
     *         (状態) config:"PropertyMessageResourcesEx_MessageResources01"<br>
     *                【ファイル内容】<br>
     *                test.message=テストメッセージ０１<br>
     *         (状態) returnNull:false<br>
     *         (状態) system-messagesの中:test.message=ＦＷテストメッセージ０１<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"テストメッセージ０１"<br>
     *         
     * <br>
     * メッセージリソース定義ファイルにもGlobalMessageResourcesにも与えられた
     * キーでメッセージリソースが登録されている場合には、メッセージリソース
     * 定義ファイルに定義されたメッセージ文言を返却することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetMessage03() throws Exception {
        // 前処理
        PropertyMessageResourcesExFactory factory
            = new PropertyMessageResourcesExFactory();
        PropertyMessageResourcesEx resources 
            = new PropertyMessageResourcesEx(factory,
                PropertyMessageResourcesEx.class.getPackage().getName().replace('.', '/')
                + "/PropertyMessageResourcesEx_MessageResources01",
                false);
        Object fwMessages 
            = UTUtil.getPrivateField(GlobalMessageResources.getInstance(),
                                     "fwMessages");
        Object globalMessages 
            = UTUtil.getPrivateField(GlobalMessageResources.getInstance(),
                                     "globalMessages");
        Map<String, String> newFwMessages = new HashMap<String, String>(1);
        newFwMessages.put("test.message", "ＦＷテストメッセージ０１");
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                                 "fwMessages", newFwMessages);
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                                 "globalMessages", new HashMap());
        
        
        // テスト実施
        String actual = resources.getMessage(Locale.JAPAN, "test.message");

        // 判定
        assertEquals(actual, "テストメッセージ０１");
        
        // 元に戻す
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                "fwMessages", fwMessages);
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                "globalMessages", globalMessages);
    }

    /**
     * testGetMessage04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) locale:Locale.JAPAN<br>
     *         (引数) key:"aaaaa"<br>
     *         (状態) factory:PropertyMessageResourcesExFactoryインスタンス<br>
     *         (状態) config:"PropertyMessageResourcesEx_MessageResources02"<br>
     *                （空のファイル）<br>
     *         (状態) returnNull:false<br>
     *         (状態) system-messagesの中:空<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"???ja_JP.aaaaa???"<br>
     *         
     * <br>
     * プロパティ、システム双方のメッセージが見つからず、returnNullフィールドが
     * falseであるとき、???ja_JP.aaaaa???の形式で返却される。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetMessage04() throws Exception {
        // 前処理
        PropertyMessageResourcesExFactory factory
            = new PropertyMessageResourcesExFactory();
        PropertyMessageResourcesEx resources 
            = new PropertyMessageResourcesEx(factory,
                PropertyMessageResourcesEx.class.getPackage().getName().replace('.', '/')
                + "/PropertyMessageResourcesEx_MessageResources02",
                false);
        Object fwMessages 
            = UTUtil.getPrivateField(GlobalMessageResources.getInstance(),
                                     "fwMessages");
        Object globalMessages 
            = UTUtil.getPrivateField(GlobalMessageResources.getInstance(),
                                     "globalMessages");
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                                 "fwMessages", new HashMap());
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                                 "globalMessages", new HashMap());
        
        
        // テスト実施
        String actual = resources.getMessage(Locale.JAPAN, "aaaaa");

        // 判定
        assertEquals(actual, "???ja_JP.aaaaa???");
        
        // 元に戻す
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                "fwMessages", fwMessages);
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                "globalMessages", globalMessages);
    }

    /**
     * testGetMessage05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) locale:Locale.JAPAN<br>
     *         (引数) key:"aaaaa"<br>
     *         (状態) factory:PropertyMessageResourcesExFactoryインスタンス<br>
     *         (状態) config:"PropertyMessageResourcesEx_MessageResources02"<br>
     *                （空のファイル）<br>
     *         (状態) returnNull:true<br>
     *         (状態) system-messagesの中:空<br>
     *         
     * <br>
     * 期待値：(戻り値) String:null<br>
     *         
     * <br>
     * プロパティ、システム双方のメッセージが見つからず、returnNullフィールドが
     * trueであるとき、nullが返却される。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetMessage05() throws Exception {
        // 前処理
        PropertyMessageResourcesExFactory factory
            = new PropertyMessageResourcesExFactory();
        PropertyMessageResourcesEx resources 
            = new PropertyMessageResourcesEx(factory,
                PropertyMessageResourcesEx.class.getPackage().getName().replace('.', '/')
                + "/PropertyMessageResourcesEx_MessageResources02",
                true);
        Object fwMessages 
            = UTUtil.getPrivateField(GlobalMessageResources.getInstance(),
                                     "fwMessages");
        Object globalMessages 
            = UTUtil.getPrivateField(GlobalMessageResources.getInstance(),
                                     "globalMessages");
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                                 "fwMessages", new HashMap());
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                                 "globalMessages", new HashMap());
        
        
        // テスト実施
        String actual = resources.getMessage(Locale.JAPAN, "aaaaa");

        // 判定
        assertNull(actual);
        
        // 元に戻す
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                "fwMessages", fwMessages);
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                "globalMessages", globalMessages);
    }

    /**
     * testGetMessage06()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) locale:Locale.ENGLISH<br>
     *         (引数) key:"test.message"<br>
     *         (状態) factory:PropertyMessageResourcesExFactoryインスタンス<br>
     *         (状態) config:"PropertyMessageResourcesEx_MessageResources03"<br>
     *                【"PropertyMessageResourcesEx_MessageResources03_en"の内容】<br>
     *                test.message=Test Message 01<br>
     *                【"PropertyMessageResourcesEx_MessageResources03"の内容】<br>
     *                test.message=テストメッセージ０１<br>
     *         (状態) returnNull:false<br>
     *         (状態) system-messagesの中:空<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"Test Message"<br>
     *         
     * <br>
     * メッセージリソース定義ファイルにロケールを考慮したファイルがあった場合、
     * そちらを優先して返却することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetMessage06() throws Exception {
        // 前処理
        PropertyMessageResourcesExFactory factory
            = new PropertyMessageResourcesExFactory();
        PropertyMessageResourcesEx resources 
            = new PropertyMessageResourcesEx(factory,
                PropertyMessageResourcesEx.class.getPackage().getName().replace('.', '/')
                + "/PropertyMessageResourcesEx_MessageResources03",
                false);
        Object fwMessages 
            = UTUtil.getPrivateField(GlobalMessageResources.getInstance(),
                                     "fwMessages");
        Object globalMessages 
            = UTUtil.getPrivateField(GlobalMessageResources.getInstance(),
                                     "globalMessages");
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                                 "fwMessages", new HashMap());
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                                 "globalMessages", new HashMap());
        
        
        // テスト実施
        String actual = resources.getMessage(Locale.ENGLISH, "test.message");

        // 判定
        assertEquals(actual, "Test Message 01");
        
        // 元に戻す
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                "fwMessages", fwMessages);
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                "globalMessages", globalMessages);
    }

}
