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

package jp.terasoluna.fw.service.thin;

import java.util.ArrayList;
import java.util.Iterator;

import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.service.thin.BLogicMessages} クラスの
 * ブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * メッセージ情報一覧クラス。<br>
 * BLogicMessageインスタンスを格納するクラス。
 * <p>
 * 
 * @see jp.terasoluna.fw.service.thin.BLogicMessages
 */
public class BLogicMessagesTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(BLogicMessagesTest.class);
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
    public BLogicMessagesTest(String name) {
        super(name);
    }

    /**
     * testBLogicMessagesBLogicMessages01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) messages:list:メッセージキー{"message.test01"}のBLogicMessageを設定<br>
     *                groupList:グループ名{"group.test01"}を設定<br>
     *         
     * <br>
     * 期待値：(戻り値) なし:－<br>
     *         (状態変化) this.list:{"message.test01"}のBLogicMessage<br>
     *         (状態変化) this.groupList:{"group.test01"}<br>
     *         
     * <br>
     * 生成されたBLogicMessagesのlistおよびgroupListに引数messagesに格納された値が格納されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testBLogicMessagesBLogicMessages01() throws Exception {
        // --------------------------------------------------引数生成
        // list設定
        ArrayList<BLogicMessage> list = new ArrayList<BLogicMessage>();
        list.add(new BLogicMessage("message.test01"));

        // groupList設定
        ArrayList<String> groupList = new ArrayList<String>();
        groupList.add("group.test01");

        // 引数BLogicMessages設定
        BLogicMessages messages = new BLogicMessages();
        UTUtil.setPrivateField(messages, "list", list);
        UTUtil.setPrivateField(messages, "groupList", groupList);
        
        // --------------------------------------------------準備完了
        
        // テスト実施
        BLogicMessages newMessages = new BLogicMessages(messages);
        
        // 判定
        assertEquals(1, newMessages.list.size());
        assertEquals("message.test01", list.get(0).getKey());
        assertEquals(1, newMessages.groupList.size());
        assertEquals("group.test01", groupList.get(0));
    }

    /**
     * testClear01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) なし:－<br>
     *         (状態) list:メッセージ{"message.test01"、"message.test02"、"message.test03"}を順番に設定<br>
     *         (状態) groupList:グループ名{"group.test01"、"group.test02"、"group.test03"}を順番に設定<br>
     *         
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) list:空のリスト<br>
     *         (状態変化) groupList:空のリスト<br>
     *         
     * <br>
     * メッセージ及びグループ名のリストがクリアされること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testClear01() throws Exception {

        // --------------------------------------------------list生成
        // key準備
        String[] keyArray = new String[3];
        keyArray[0] = "message.test01";
        keyArray[1] = "message.test02";
        keyArray[2] = "message.test03";

        // list設定
        ArrayList<BLogicMessage> list = new ArrayList<BLogicMessage>();
        for (int i = 0; i < keyArray.length; i++) {
            // BLogicMessage格納
            list.add(new BLogicMessage(keyArray[i]));
        }

        // --------------------------------------------------grouplist生成
        // group準備
        String[] groupArray = new String[3];
        groupArray[0] = "group.test01";
        groupArray[1] = "group.test02";
        groupArray[2] = "group.test03";
        
        // groupList設定
        ArrayList<String> groupList = new ArrayList<String>();
        for (int i = 0; i < groupArray.length; i++) {
            groupList.add(groupArray[i]);
        }
        
        // --------------------------------------------------BLogicMessages生成
        // BLogicMessages設定
        BLogicMessages messages = new BLogicMessages();
        UTUtil.setPrivateField(messages, "list", list);
        UTUtil.setPrivateField(messages, "groupList", groupList);
        
        // --------------------------------------------------準備完了
        
        // テスト実施
        messages.clear();
        
        // 判定
        assertTrue(messages.list.isEmpty());
        assertTrue(messages.groupList.isEmpty());

    }

    /**
     * testClear02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) なし:－<br>
     *         (状態) list:空のリスト<br>
     *         (状態) groupList:空のリスト<br>
     *         
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) list:空のリスト<br>
     *         (状態変化) groupList:空のリスト<br>
     *         
     * <br>
     * メッセージ及びグループ名のリストがクリアされること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testClear02() throws Exception {

        // --------------------------------------------------list生成        
        // list設定
        ArrayList<BLogicMessage> list = new ArrayList<BLogicMessage>();
        
        // --------------------------------------------------groupList生成
        // groupList設定
        ArrayList<String> groupList = new ArrayList<String>();
        
        // --------------------------------------------------BLogicMessages生成
        // BLogicMessages設定
        BLogicMessages messages = new BLogicMessages();
        UTUtil.setPrivateField(messages, "list", list);
        UTUtil.setPrivateField(messages, "groupList", groupList);
        
        // --------------------------------------------------準備完了
        
        // テスト実施
        messages.clear();
        
        // 判定
        assertTrue(messages.list.isEmpty());
        assertTrue(messages.groupList.isEmpty());
    }

    /**
     * testIsEmpty01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) なし:－<br>
     *         (状態) list:メッセージ{"message.test01"、"message.test02"、"message.test03"}を順番に設定<br>
     *         
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         
     * <br>
     * メッセージリストにメッセージが格納されている場合、falseが返却されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testIsEmpty01() throws Exception {

        // --------------------------------------------------list生成        
        // key準備
        String[] keyArray = new String[3];
        keyArray[0] = "message.test01";
        keyArray[1] = "message.test02";
        keyArray[2] = "message.test03";

        // list設定
        ArrayList<BLogicMessage> list = new ArrayList<BLogicMessage>();
        for (int i = 0; i < keyArray.length; i++) {
            // BLogicMessage格納
            list.add(new BLogicMessage(keyArray[i]));
        }

        // --------------------------------------------------BLogicMessages生成
        // BLogicMessages設定
        BLogicMessages messages = new BLogicMessages();
        UTUtil.setPrivateField(messages, "list", list);
        
        // --------------------------------------------------準備完了
        
        // テスト実施・判定
        assertFalse(messages.isEmpty());
        
    }

    /**
     * testIsEmpty02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) なし:－<br>
     *         (状態) list:空のリスト<br>
     *         
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         
     * <br>
     * メッセージリストにメッセージが格納されていない場合、trueが返却されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testIsEmpty02() throws Exception {

        // --------------------------------------------------list生成        
        // list設定
        ArrayList<BLogicMessage> list = new ArrayList<BLogicMessage>();
        
        // --------------------------------------------------BLogicMessages生成
        // BLogicMessages設定
        BLogicMessages messages = new BLogicMessages();
        UTUtil.setPrivateField(messages, "list", list);
        
        // --------------------------------------------------準備完了
        
        // テスト実施・判定
        assertTrue(messages.isEmpty());
    }

    /**
     * testGet01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(引数) なし:－<br>
     *         (状態) list:メッセージキー{"message.test01"、"message.test02"、"message.test03"}のBLogicMessageを順番に設定<br>
     *         
     * <br>
     * 期待値：(戻り値) Iterator:設定した順番通りにBLogicMessageを取り出せるイテレータ<br>
     *         
     * <br>
     * 複数メッセージを設定した時、順番通りにメッセージを取得できること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGet01() throws Exception {

        // --------------------------------------------------list生成                
        // key準備
        String[] keyArray = new String[3];
        keyArray[0] = "message.test01";
        keyArray[1] = "message.test02";
        keyArray[2] = "message.test03";
        
        // BLogicMessage設定
        BLogicMessage[] blogicMessageArray = new BLogicMessage[keyArray.length];
        for (int i = 0; i < keyArray.length; i++) {
            blogicMessageArray[i] = new BLogicMessage(keyArray[i]);
        }
        
        // list設定
        ArrayList<BLogicMessage> list = new ArrayList<BLogicMessage>();
        for (int i = 0; i < blogicMessageArray.length; i++) {
            list.add(blogicMessageArray[i]);
        }
        
        // --------------------------------------------------BLogicMessages生成
        // BLogicMessages設定
        BLogicMessages messages = new BLogicMessages();
        UTUtil.setPrivateField(messages, "list", list);
        
        // --------------------------------------------------準備完了
        
        // テスト実施
        Iterator<BLogicMessage> ite = messages.get();
        
        // 判定
        int j = 0;
        while (ite.hasNext()) {
            BLogicMessage message = ite.next();
            assertEquals(keyArray[j], message.getKey());
            j++;
        }
        assertEquals(j, keyArray.length);

    }

    /**
     * testGet02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(引数) なし:－<br>
     *         (状態) list:メッセージキー{"message.test01"}のBLogicMessageを設定<br>
     *         
     * <br>
     * 期待値：(戻り値) Iterator:設定した順番通りにBLogicMessageを取り出せるイテレータ<br>
     *         
     * <br>
     * メッセージを1つ設定した時、順番通りにメッセージを取得できること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGet02() throws Exception {

        // --------------------------------------------------list生成        
        // key準備
        String[] keyArray = new String[1];
        keyArray[0] = "message.test01";

        // list設定
        ArrayList<BLogicMessage> list = new ArrayList<BLogicMessage>();
        for (int i = 0; i < keyArray.length; i++) {
            // BLogicMessage格納
            list.add(new BLogicMessage(keyArray[i]));
        }

        // --------------------------------------------------BLogicMessages生成
        // BLogicMessages設定
        BLogicMessages messages = new BLogicMessages();
        UTUtil.setPrivateField(messages, "list", list);
        
        // --------------------------------------------------準備完了
        
        // テスト実施
        Iterator<BLogicMessage> ite = messages.get();
        
        // 判定
        int j = 0;
        while (ite.hasNext()) {
            BLogicMessage message = ite.next();
            assertEquals(keyArray[j], message.getKey());
            j++;
        }
        assertEquals(j, keyArray.length);
    }

    /**
     * testGet03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(引数) なし:－<br>
     *         (状態) list:空のリスト<br>
     *         
     * <br>
     * 期待値：(戻り値) Iterator:空のメッセージ一覧イテレータ<br>
     *         
     * <br>
     * メッセージリストが空のリストの場合、空のメッセージ一覧イテレータが取得できること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGet03() throws Exception {

        // --------------------------------------------------list生成    
        // list設定
        ArrayList<BLogicMessage> list = new ArrayList<BLogicMessage>();
        
        // --------------------------------------------------BLogicMessages生成
        // BLogicMessages設定
        BLogicMessages messages = new BLogicMessages();
        UTUtil.setPrivateField(messages, "list", list);
        
        // --------------------------------------------------準備完了
        
        // テスト実施
        Iterator<BLogicMessage> ite = messages.get();
        
        // 判定
        assertFalse(ite.hasNext());
    }

    /**
     * testGetGroup01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(引数) なし:－<br>
     *         (状態) groupList:グループ名{"group.test01"、"group.test02"、"group.test03"}を順番に設定<br>
     *         
     * <br>
     * 期待値：(戻り値) Iterator:設定した順番通りにグループ名（String）を取り出せるイテレータ<br>
     *         
     * <br>
     * 複数グループ名を設定した時、順番通りにグループ名を取得できること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetGroup01() throws Exception {

        // --------------------------------------------------groupList生成  
        // group準備
        String[] groupArray = new String[3];
        groupArray[0] = "group.test01";
        groupArray[1] = "group.test02";
        groupArray[2] = "group.test03";
        
        // groupList設定
        ArrayList<String> groupList = new ArrayList<String>();
        for (int i = 0; i < groupArray.length; i++) {
            groupList.add(groupArray[i]);
        }
        
        // --------------------------------------------------BLogicMessages生成
        // BLogicMessages設定
        BLogicMessages messages = new BLogicMessages();
        UTUtil.setPrivateField(messages, "groupList", groupList);
        
        // --------------------------------------------------準備完了
        
        // テスト実施
        Iterator<String> ite = messages.getGroup();
        
        // 判定
        int j = 0;
        while (ite.hasNext()) {
            String group = ite.next();
            assertEquals(groupArray[j], group);
            j++;
        }
        assertEquals(j, groupArray.length);

    }

    /**
     * testGetGroup02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(引数) なし:－<br>
     *         (状態) groupList:グループ名{"group.test01"}を設定<br>
     *         
     * <br>
     * 期待値：(戻り値) Iterator:設定した順番通りにグループ名（String）を取り出せるイテレータ<br>
     *         
     * <br>
     * グループ名を1つ設定した時、順番通りにグループ名を取得できること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetGroup02() throws Exception {

        // --------------------------------------------------groupList生成  
        // group準備
        String[] groupArray = new String[1];
        groupArray[0] = "group.test01";
        
        // groupList設定
        ArrayList<String> groupList = new ArrayList<String>();
        for (int i = 0; i < groupArray.length; i++) {
            groupList.add(groupArray[i]);
        }
        
        // --------------------------------------------------BLogicMessages生成
        // BLogicMessages設定
        BLogicMessages messages = new BLogicMessages();
        UTUtil.setPrivateField(messages, "groupList", groupList);
        
        // --------------------------------------------------準備完了
        
        // テスト実施
        Iterator<String> ite = messages.getGroup();
        
        // 判定
        int j = 0;
        while (ite.hasNext()) {
            String group = ite.next();
            assertEquals(groupArray[j], group);
            j++;
        }
        assertEquals(j, groupArray.length);
    }

    /**
     * testGetGroup03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(引数) なし:－<br>
     *         (状態) groupList:空のリスト<br>
     *         
     * <br>
     * 期待値：(戻り値) Iterator:空のグループ名一覧イテレータ<br>
     *         
     * <br>
     * グループ名リストが空のリストの場合、空のグループ名一覧イテレータが取得できること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetGroup03() throws Exception {

        // --------------------------------------------------groupList生成  
        // group準備
        ArrayList<String> groupList = new ArrayList<String>();
        
        // --------------------------------------------------BLogicMessages生成
        // BLogicMessages設定
        BLogicMessages messages = new BLogicMessages();
        UTUtil.setPrivateField(messages, "groupList", groupList);
        
        // --------------------------------------------------準備完了
        
        // テスト実施
        Iterator<String> ite = messages.getGroup();
        
        // 判定
        assertFalse(ite.hasNext());
    }

    /**
     * testAddStringBLogicMessage01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(引数) group:"group.test00"<br>
     *         (引数) message:メッセージキー{"message.test00"}のBLogicMessage<br>
     *         (状態) list:メッセージキー{"message.test01"、"message.test02"、"message.test03"}のBLogicMessageを順番に設定<br>
     *         (状態) groupList:グループ名{"group.test01"、"group.test02"、"group.test03"}を順番に設定<br>
     *         
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) list:メッセージキー{"message.test01"、"message.test02"、"message.test03"、"message.test00"}のBLogicMessageが順番に格納されたリスト<br>
     *         (状態変化) groupList:グループ名{"group.test01"、"group.test02"、"group.test03"、"group.test00"}が順番に格納されたリスト<br>
     *         
     * <br>
     * メッセージリスト、グループ名リストにメッセージ、グループ名が既に設定されていた時、メッセージ、グループ名が追加で最後に格納されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddStringBLogicMessage01() throws Exception {

        // --------------------------------------------------list生成
        // key準備
        String[] keyArray = new String[3];
        keyArray[0] = "message.test01";
        keyArray[1] = "message.test02";
        keyArray[2] = "message.test03";
        
        // 判定に用いるkeyの配列
        String[] expectedKeyArray = new String[keyArray.length + 1];

        // list設定
        ArrayList<BLogicMessage> list = new ArrayList<BLogicMessage>();
        for (int i = 0; i < keyArray.length; i++) {
            // BLogicMessage格納
            list.add(new BLogicMessage(keyArray[i]));
            
            // 判定に用いるkeyの配列にコピー
            expectedKeyArray[i] = keyArray[i];
        }

        // --------------------------------------------------groupList生成
        // group準備
        String[] groupArray = new String[3];
        groupArray[0] = "group.test01";
        groupArray[1] = "group.test02";
        groupArray[2] = "group.test03";
        
        // 判定に用いるgroupの配列
        String[] expectedGroupArray = new String[groupArray.length + 1];
        
        // groupList設定
        ArrayList<String> groupList = new ArrayList<String>();
        for (int i = 0; i < groupArray.length; i++) {
            groupList.add(groupArray[i]);
            
            // 判定に用いるgroupの配列にコピー
            expectedGroupArray[i] = groupArray[i];
        }
        
        // --------------------------------------------------BLogicMessages生成
        // BLogicMessages設定
        BLogicMessages messages = new BLogicMessages();
        UTUtil.setPrivateField(messages, "list", list);
        UTUtil.setPrivateField(messages, "groupList", groupList);
        
        // --------------------------------------------------引数生成
        // 引数message設定
        String key = "message.test00";
        BLogicMessage message = new BLogicMessage(key);

        // 判定に用いるkeyの配列にコピー
        expectedKeyArray[keyArray.length] = key;
        
        // 引数group設定
        String group = "group.test00";
        
        // 判定に用いるgroupの配列にコピー
        expectedGroupArray[groupArray.length] = group;
        
        // --------------------------------------------------準備完了
        
        // テスト実施
        messages.add(group, message);
        
        // 判定
        Iterator<BLogicMessage> resultItr = messages.list.iterator();
        int j = 0;
        while (resultItr.hasNext()) {
            BLogicMessage resultMessage = resultItr.next();
            assertEquals(expectedKeyArray[j], resultMessage.getKey());
            j++;
        }
        assertEquals(j, expectedKeyArray.length);
        
        int k = 0;
        Iterator<String> resultItr2 = messages.groupList.iterator();
        while (resultItr2.hasNext()) {
            String resultGroup = resultItr2.next();
            assertEquals(expectedGroupArray[k], resultGroup);
            k++;
        }
        assertEquals(k, expectedGroupArray.length);
    }

    /**
     * testAddStringBLogicMessage02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(引数) group:"group.test00"<br>
     *         (引数) message:メッセージキー{"message.test00"}のBLogicMessage<br>
     *         (状態) list:空のリスト<br>
     *         (状態) groupList:空のリスト<br>
     *         
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) list:メッセージキー{"message.test00"}のBLogicMessageが格納されたリスト<br>
     *         (状態変化) groupList:グループ名{"group.test00"}が格納されたリスト<br>
     *         
     * <br>
     * メッセージリスト、グループ名リストにメッセージ、グループ名が設定されていない時、メッセージ、グループ名が格納されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddStringBLogicMessage02() throws Exception {

        // --------------------------------------------------list生成
        // list設定
        ArrayList<BLogicMessage> list = new ArrayList<BLogicMessage>();
        
        // --------------------------------------------------groupList生成
        // groupList設定
        ArrayList<String> groupList = new ArrayList<String>();
        
        // --------------------------------------------------BLogicMessages生成
        // BLogicMessages設定
        BLogicMessages messages = new BLogicMessages();
        UTUtil.setPrivateField(messages, "list", list);
        UTUtil.setPrivateField(messages, "groupList", groupList);
        
        // --------------------------------------------------引数生成
        // 引数message設定
        String key = "message.test00";
        BLogicMessage message = new BLogicMessage(key);
        
        // 引数group設定
        String group = "group.test00";
        
        // --------------------------------------------------準備完了
        
        // テスト実施
        messages.add(group, message);
        
        // 判定
        assertTrue(messages.list.size() == 1);
        assertEquals(key, messages.list.get(0).getKey());
        assertTrue(messages.groupList.size() == 1);
        assertEquals(group, messages.groupList.get(0));
    }

    /**
     * testAddStringBLogicMessage03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) group:""<br>
     *         (引数) message:メッセージキー{"message.test00"}のBLogicMessage<br>
     *         (状態) list:空のリスト<br>
     *         (状態) groupList:空のリスト<br>
     *         
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) list:メッセージキー{"message.test00"}のBLogicMessageが格納されたリスト<br>
     *         (状態変化) groupList:グループ名{""}が格納されたリスト<br>
     *         
     * <br>
     * 引数のgroupが空文字の時、グループ名リストに空文字が格納されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddStringBLogicMessage03() throws Exception {

        // --------------------------------------------------list生成
        // list設定
        ArrayList<BLogicMessage> list = new ArrayList<BLogicMessage>();
        
        // --------------------------------------------------groupList生成
        // groupList設定
        ArrayList<String> groupList = new ArrayList<String>();
        
        // --------------------------------------------------BLogicMessages生成
        // BLogicMessages設定
        BLogicMessages messages = new BLogicMessages();
        UTUtil.setPrivateField(messages, "list", list);
        UTUtil.setPrivateField(messages, "groupList", groupList);
        
        // --------------------------------------------------引数生成
        // 引数message設定
        String key = "message.test00";
        BLogicMessage message = new BLogicMessage(key);
        
        // 引数group設定
        String group = "";
        
        // --------------------------------------------------準備完了
        
        // テスト実施
        messages.add(group, message);

        // 判定
        assertTrue(messages.list.size() == 1);
        assertEquals(key, messages.list.get(0).getKey());
        assertTrue(messages.groupList.size() == 1);
        assertEquals(group, messages.groupList.get(0));
    }

    /**
     * testAddStringBLogicMessage04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) group:null<br>
     *         (引数) message:null<br>
     *         (状態) list:空のリスト<br>
     *         (状態) groupList:空のリスト<br>
     *         
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) list:nullが格納されたリスト<br>
     *         (状態変化) groupList:グループ名{null}が格納されたリスト<br>
     *         
     * <br>
     * 引数のgroupがnull、messagesがnullの時、グループ名リストにnull、メッセージリストにnullが格納されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddStringBLogicMessage04() throws Exception {

        // --------------------------------------------------list生成
        // list設定
        ArrayList<BLogicMessage> list = new ArrayList<BLogicMessage>();
        
        // --------------------------------------------------groupList生成
        // groupList設定
        ArrayList<String> groupList = new ArrayList<String>();
        
        // --------------------------------------------------BLogicMessages生成
        // BLogicMessages設定
        BLogicMessages messages = new BLogicMessages();
        UTUtil.setPrivateField(messages, "list", list);
        UTUtil.setPrivateField(messages, "groupList", groupList);
        
        // --------------------------------------------------引数生成
        // 引数message設定
        BLogicMessage message = null;
        
        // 引数group設定
        String group = null;
        
        // --------------------------------------------------準備完了
        
        // テスト実施
        messages.add(group, message);
        
        // 判定
        assertTrue(messages.list.size() == 1);
        assertNull(messages.list.get(0));
        assertTrue(messages.groupList.size() == 1);
        assertNull(messages.groupList.get(0));
    }

    /**
     * testAddBLogicMessages01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(引数) messages:list:メッセージキー{"message.test10"、"message.test20"、"message.test30"}のBLogicMessageを順番に設定<br>
     *                groupList:グループ名{"group.test10"、"group.test20"、"group.test30"}を順番に設定<br>
     *         (状態) list:メッセージキー{"message.test01"、"message.test02"、"message.test03"}のBLogicMessageを順番に設定<br>
     *         (状態) groupList:グループ名{"group.test01"、"group.test02"、"group.test03"}を順番に設定<br>
     *         
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) list:メッセージキー{"message.test01"、"message.test02"、"message.test03"、"message.test10"、"message.test20"、"message.test30"}のBLogicMessageが順番に格納されたリスト<br>
     *         (状態変化) groupList:グループ名{"group.test01"、"group.test02"、"group.test03"、"group.test10"、"group.test20"、"group.test30"}が順番に格納されたリスト<br>
     *         
     * <br>
     * メッセージリスト、グループ名リストにメッセージ、グループ名が既に設定されていた時、メッセージ、グループ名が追加で最後に格納されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddBLogicMessages01() throws Exception {

        // --------------------------------------------------list生成        
        // key準備
        String[] keyArray = new String[3];
        keyArray[0] = "message.test01";
        keyArray[1] = "message.test02";
        keyArray[2] = "message.test03";
        
        // 引数用key準備　引数生成で使用
        String[] keyArray2 = new String[3];
        keyArray2[0] = "message.test10";
        keyArray2[1] = "message.test20";
        keyArray2[2] = "message.test30";
        
        // 判定に用いるkeyの配列
        String[] expectedKeyArray = new String[keyArray.length
                + keyArray2.length];

        // list設定
        ArrayList<BLogicMessage> list = new ArrayList<BLogicMessage>();
        for (int i = 0; i < keyArray.length; i++) {
            // BLogicMessage格納
            list.add(new BLogicMessage(keyArray[i]));
            
            // 判定に用いるkeyの配列にコピー
            expectedKeyArray[i] = keyArray[i];
        }

        // --------------------------------------------------groupList生成
        // group準備
        String[] groupArray = new String[3];
        groupArray[0] = "group.test01";
        groupArray[1] = "group.test02";
        groupArray[2] = "group.test03";
        
        // 引数用group準備　引数生成で使用
        String[] groupArray2 = new String[3];
        groupArray2[0] = "group.test10";
        groupArray2[1] = "group.test20";
        groupArray2[2] = "group.test30";
        
        // 判定に用いるgroupの配列
        String[] expectedGroupArray = new String[groupArray.length
                + groupArray2.length];
        
        // groupList設定
        ArrayList<String> groupList = new ArrayList<String>();
        for (int i = 0; i < groupArray.length; i++) {
            groupList.add(groupArray[i]);
            
            // 判定に用いるgroupの配列にコピー
            expectedGroupArray[i] = groupArray[i];
        }
        
        // --------------------------------------------------BLogicMessages生成
        // BLogicMessages設定
        BLogicMessages messages = new BLogicMessages();
        UTUtil.setPrivateField(messages, "list", list);
        UTUtil.setPrivateField(messages, "groupList", groupList);
        
        // --------------------------------------------------引数生成
        // list設定
        ArrayList<BLogicMessage> list2 = new ArrayList<BLogicMessage>();
        for (int i = 0; i < keyArray2.length; i++) {
            // BLogicMessage格納
            list2.add(new BLogicMessage(keyArray2[i]));
            
            // 判定に用いるkeyの配列にコピー
            expectedKeyArray[keyArray.length + i] = keyArray2[i];
        }

        // groupList設定
        ArrayList<String> groupList2 = new ArrayList<String>();
        for (int i = 0; i < groupArray2.length; i++) {
            groupList2.add(groupArray2[i]);
            
            // 判定に用いるgroupの配列にコピー
            expectedGroupArray[groupArray.length + i] = groupArray2[i];
        }
        
        // 引数BLogicMessages設定
        BLogicMessages messages2 = new BLogicMessages();
        UTUtil.setPrivateField(messages2, "list", list2);
        UTUtil.setPrivateField(messages2, "groupList", groupList2);
        
        // --------------------------------------------------準備完了
        
        // テスト実施
        messages.add(messages2);
        
        // 判定
        Iterator<BLogicMessage> resultItr = messages.list.iterator();
        int j = 0;
        while (resultItr.hasNext()) {
            BLogicMessage resultMessage = resultItr.next();
            assertEquals(expectedKeyArray[j], resultMessage.getKey());
            j++;
        }
        assertEquals(j, expectedKeyArray.length);
        
        int k = 0;
        Iterator<String> resultItr2 = messages.groupList.iterator();
        while (resultItr2.hasNext()) {
            String resultGroup = resultItr2.next();
            assertEquals(expectedGroupArray[k], resultGroup);
            k++;
        }
        assertEquals(k, expectedGroupArray.length);
    }

    /**
     * testAddBLogicMessages02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(引数) messages:list:メッセージキー{"message.test10"}のBLogicMessageを順番に設定<br>
     *                groupList:グループ名{"group.test10"}を順番に設定<br>
     *         (状態) list:空のリスト<br>
     *         (状態) groupList:空のリスト<br>
     *         
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) list:メッセージキー{"message.test10"}のBLogicMessageが格納されたリスト<br>
     *         (状態変化) groupList:グループ名{"group.test10"}が格納されたリスト<br>
     *         
     * <br>
     * メッセージリスト、グループ名リストにメッセージ、グループ名が設定されていない時、メッセージ、グループ名が格納されること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddBLogicMessages02() throws Exception {

        // --------------------------------------------------list生成
        // list設定
        ArrayList<BLogicMessage> list = new ArrayList<BLogicMessage>();
        
        // --------------------------------------------------groupList生成
        // groupList設定
        ArrayList<String> groupList = new ArrayList<String>();
        
        // --------------------------------------------------BLogicMessages生成
        // BLogicMessages設定
        BLogicMessages messages = new BLogicMessages();
        UTUtil.setPrivateField(messages, "list", list);
        UTUtil.setPrivateField(messages, "groupList", groupList);
        
        // --------------------------------------------------引数生成
        // key準備
        String[] keyArray2 = new String[1];
        keyArray2[0] = "message.test10";
        
        // list設定
        ArrayList<BLogicMessage> list2 = new ArrayList<BLogicMessage>();
        for (int i = 0; i < keyArray2.length; i++) {
            // BLogicMessage格納
            list2.add(new BLogicMessage(keyArray2[i]));
        }
        
        // group準備
        String[] groupArray2 = new String[1];
        groupArray2[0] = "group.test10";
        
        // groupList設定
        ArrayList<String> groupList2 = new ArrayList<String>();
        for (int i = 0; i < groupArray2.length; i++) {
            groupList2.add(groupArray2[i]);
        }
        
        // 引数BLogicMessages設定
        BLogicMessages messages2 = new BLogicMessages();
        UTUtil.setPrivateField(messages2, "list", list2);
        UTUtil.setPrivateField(messages2, "groupList", groupList2);
        
        // --------------------------------------------------準備完了
        
        // テスト実施
        messages.add(messages2);
        
        // 判定
        assertTrue(messages.list.size() == 1);
        assertEquals(keyArray2[0], messages.list.get(0).getKey());
        assertTrue(messages.groupList.size() == 1);
        assertEquals(groupArray2[0], messages.groupList.get(0));

    }

    /**
     * testAddBLogicMessages03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) messages:null<br>
     *         (状態) list:メッセージキー{"message.test01"、"message.test02"、"message.test03"}のBLogicMessageを順番に設定<br>
     *         (状態) groupList:グループ名{"group.test01"、"group.test02"、"group.test03"}を順番に設定<br>
     *         
     * <br>
     * 期待値：(戻り値) void:－<br>
     *         (状態変化) list:メッセージキー{"message.test01"、"message.test02"、"message.test03"}のBLogicMessageが順番に格納されたリスト<br>
     *         (状態変化) groupList:グループ名{"group.test01"、"group.test02"、"group.test03"}が順番に格納されたリスト<br>
     *         
     * <br>
     * messagesにnullを設定した場合、何もせずに処理を終了すること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddBLogicMessages03() throws Exception {

        // --------------------------------------------------list生成        
        // key準備
        String[] keyArray = new String[3];
        keyArray[0] = "message.test01";
        keyArray[1] = "message.test02";
        keyArray[2] = "message.test03";

        // list設定
        ArrayList<BLogicMessage> list = new ArrayList<BLogicMessage>();
        for (int i = 0; i < keyArray.length; i++) {
            // BLogicMessage格納
            list.add(new BLogicMessage(keyArray[i]));
        }

        // --------------------------------------------------groupList生成
        // group準備
        String[] groupArray = new String[3];
        groupArray[0] = "group.test01";
        groupArray[1] = "group.test02";
        groupArray[2] = "group.test03";
        
        // groupList設定
        ArrayList<String> groupList = new ArrayList<String>();
        for (int i = 0; i < groupArray.length; i++) {
            groupList.add(groupArray[i]);
        }
        
        // --------------------------------------------------BLogicMessages生成
        // BLogicMessages設定
        BLogicMessages messages = new BLogicMessages();
        UTUtil.setPrivateField(messages, "list", list);
        UTUtil.setPrivateField(messages, "groupList", groupList);
        
        // --------------------------------------------------準備完了
        
        // テスト実施
        messages.add(null);
        
        // 判定
        Iterator<BLogicMessage> resultItr = messages.list.iterator();
        int j = 0;
        while (resultItr.hasNext()) {
            BLogicMessage resultMessage = resultItr.next();
            assertEquals(keyArray[j], resultMessage.getKey());
            j++;
        }
        assertEquals(j, keyArray.length);
        
        int k = 0;
        Iterator<String> resultItr2 = messages.groupList.iterator();
        while (resultItr2.hasNext()) {
            String resultGroup = resultItr2.next();
            assertEquals(groupArray[k], resultGroup);
            k++;
        }
        assertEquals(k, groupArray.length);
    }

}
