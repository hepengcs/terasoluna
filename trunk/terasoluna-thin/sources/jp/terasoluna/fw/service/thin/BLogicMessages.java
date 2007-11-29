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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *  メッセージ情報一覧クラス。
 * <p>
 *  BLogicMessageインスタンスを格納するクラス。
 * </p>
 * <p>
 *  使用例については、AbstractBLogicActionを参照のこと。
 * </p>
 *
 * @see jp.terasoluna.fw.service.thin.BLogicMessage
 * @see jp.terasoluna.fw.web.struts.actions.AbstractBLogicAction
 *
 */
public class BLogicMessages implements Serializable {

    /**
     * シリアルバージョンID
     */
    private static final long serialVersionUID = -7410826431026041043L;

    // ----------------------------------------------------- Instance Variables
    /**
     * BLogicMessageリスト。
     *
     * <p>メッセージグループ名を無視して、単純にadd()した順序で
     * BLogicMessageを保持する。</p>
     */
    protected ArrayList<BLogicMessage> list = new ArrayList<BLogicMessage>();

    /**
     * メッセージグループ名のリスト。
     *
     * <p>単純にadd()した順序でメッセージグループ名を保持する。</p>
     */
    protected ArrayList<String> groupList = new ArrayList<String>();

    // --------------------------------------------------------- Public Methods

    /**
     * コンストラクタ。
     */
    public BLogicMessages() {

        super();
    }

    /**
     * コンストラクタ。
     *
     * @param messages メッセージ情報一覧
     */
    public BLogicMessages(BLogicMessages messages) {
        super();
        this.add(messages);
    }

    /**
     * メッセージを追加する。
     *
     * @param group メッセージグループ名
     * @param message 追加するメッセージ
     */
    public void add(String group, BLogicMessage message) {

        this.list.add(message);
        this.groupList.add(group);
    }

    /**
     * メッセージを追加する。
     *
     * @param messages 追加するBLogicMessagesインスタンス
     */
    public void add(BLogicMessages messages) {

        if (messages == null) {
            return;
        }

        // loop over properties
        Iterator<BLogicMessage> itr = messages.get();
        Iterator<String> groupItr = messages.getGroup();
        while (itr.hasNext()) {
            BLogicMessage message = itr.next();
            String group = groupItr.next();

            this.add(group, message);
        }
    }

    /**
     * 保持しているメッセージをクリアする。
     */
    public void clear() {

        this.list.clear();
        this.groupList.clear();
    }

    /**
     * メッセージが格納されていない場合、trueを返す。
     *
     * @return メッセージが格納されていない場合、true
     */
    public boolean isEmpty() {

        return (list.isEmpty());
    }

    /**
     * 保持しているメッセージ一覧にアクセスするイテレータを取得する。
     * メッセージグループ名は無視して、add()した順序で取得されることが保証される。
     *
     * @return メッセージ一覧のイテレータ
     */
    @SuppressWarnings("unchecked")
    public Iterator<BLogicMessage> get() {
        return ((ArrayList) this.list.clone()).iterator();
    }

    /**
     * 保持しているメッセージグループ名一覧にアクセスするイテレータを取得する。
     * add()した順序で取得されることが保証される。
     *
     * @return メッセージグループ名一覧のイテレータ
     */
    @SuppressWarnings("unchecked")
    public Iterator<String> getGroup() {
        return ((ArrayList) this.groupList.clone()).iterator();
    }

}
