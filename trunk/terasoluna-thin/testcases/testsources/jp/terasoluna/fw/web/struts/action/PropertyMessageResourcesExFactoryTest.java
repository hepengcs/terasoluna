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

import org.apache.struts.util.MessageResources;

import jp.terasoluna.utlib.PropertyTestCase;
import jp.terasoluna.utlib.UTUtil;

/**
 * {@link jp.terasoluna.fw.web.struts.action.PropertyMessageResourcesExFactory} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * PropertyMessageResourcesExを生成するファクトリクラス。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.action.PropertyMessageResourcesFactoryEx
 */
public class PropertyMessageResourcesExFactoryTest extends PropertyTestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(PropertyMessageResourcesExFactoryTest.class);
    }

    /**
     * 初期化処理を行う。
     * 
     * @throws Exception このメソッドで発生した例外
     * @see jp.terasoluna.utlib.spring.PropertyTestCase#setUpData()
     */
    @Override
    protected void setUpData() throws Exception {
    }

    /**
     * 終了処理を行う。
     * 
     * @throws Exception このメソッドで発生した例外
     * @see jp.terasoluna.utlib.spring.PropertyTestCase#cleanUpData()
     */
    @Override
    protected void cleanUpData() throws Exception {
    }

    /**
     * コンストラクタ。
     * 
     * @param name このテストケースの名前。
     */
    public PropertyMessageResourcesExFactoryTest(String name) {
        super(name);
    }

    /**
     * testCreateResources01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) config:"aaaaa"<br>
     *         
     * <br>
     * 期待値：(戻り値) PropertyMessageResourcesEx:factory->戻り値を生成したPropertyMessageResourcesExFactoryインスタンス自身<br>
     *                  config->"aaaaa"<br>
     *                  returnNull->MessageResourcesFactory.getReturnNull()<br>
     *         
     * <br>
     * PropertyMessageResourcesExが生成されていることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateResources01() throws Exception {
        // 前処理
        PropertyMessageResourcesExFactory factory 
            = new PropertyMessageResourcesExFactory();
        
        // テスト実施
        MessageResources resources 
            = factory.createResources("aaaaa");
       
        // 判定
        assertEquals(resources.getClass().getName(),
                     PropertyMessageResourcesEx.class.getName());
        assertSame(factory,
                   UTUtil.getPrivateField(resources, "factory"));
        assertEquals("aaaaa",
                     UTUtil.getPrivateField(resources, "config"));
        assertEquals(factory.getReturnNull(),
                     UTUtil.getPrivateField(resources, "returnNull"));

    }

    /**
     * testCreateResources02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) config:""<br>
     *         
     * <br>
     * 期待値：(戻り値) PropertyMessageResourcesEx:factory->戻り値を生成したPropertyMessageResourcesExFactoryインスタンス自身<br>
     *                  config->""<br>
     *                  returnNull->MessageResourcesFactory.getReturnNull()<br>
     *         
     * <br>
     * PropertyMessageResourcesExが生成されていることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateResources02() throws Exception {
        // 前処理
        PropertyMessageResourcesExFactory factory 
            = new PropertyMessageResourcesExFactory();
        
        // テスト実施
        MessageResources resources 
            = factory.createResources("");
       
        // 判定
        assertEquals(resources.getClass().getName(),
                     PropertyMessageResourcesEx.class.getName());
        assertSame(factory, UTUtil.getPrivateField(resources, "factory"));
        assertEquals("", UTUtil.getPrivateField(resources, "config"));
        assertEquals(factory.getReturnNull(),
                     UTUtil.getPrivateField(resources, "returnNull"));   
    }

    /**
     * testCreateResources03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) config:null<br>
     *         
     * <br>
     * 期待値：(戻り値) PropertyMessageResourcesEx:factory->戻り値を生成したPropertyMessageResourcesExFactoryインスタンス自身<br>
     *                  config->null<br>
     *                  returnNull->MessageResourcesFactory.getReturnNull()<br>
     *         
     * <br>
     * PropertyMessageResourcesExが生成されていることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateResources03() throws Exception {
        // 前処理
        PropertyMessageResourcesExFactory factory 
            = new PropertyMessageResourcesExFactory();
        
        // テスト実施
        MessageResources resources 
            = factory.createResources(null);
       
        // 判定
        assertEquals(resources.getClass().getName(),
                     PropertyMessageResourcesEx.class.getName());
        assertSame(factory,
                   UTUtil.getPrivateField(resources, "factory"));
        assertNull(UTUtil.getPrivateField(resources, "config"));
        assertEquals(factory.getReturnNull(),
                     UTUtil.getPrivateField(resources, "returnNull"));  
    }

}
