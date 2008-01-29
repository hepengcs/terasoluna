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

/**
 * メッセージ情報クラス。
 * <p>
 *  ビジネスロジックの実行結果を受けて、メッセージを設定する際に生成する。
 *  コンストラクタの第一引数にメッセージのリソースバンドルキー、
 *  第二引数（可変長引数）に置換文字列を指定することで、
 *  メッセージリソースからメッセージを取得できる。
 *  メッセージリソースからメッセージを取得せずに、
 *  第一引数をそのままメッセージ文字列として使用することもでき、
 *  その場合は、第一引数にそのメッセージ文字列、
 *  第二引数にfalseを指定する。
 * </p>
 * <p>
 *  使用例については、AbstractBLogicActionを参照のこと。
 * </p>
 * 
 * @see jp.terasoluna.fw.web.struts.actions.AbstractBLogicAction
 *
 */
public class BLogicMessage implements Serializable {

    /**
     * シリアルバージョンID
     */
    private static final long serialVersionUID = 2885287921533717080L;

    // ----------------------------------------------------------- Constructors
    /**
     * コンストラクタ。
     *
     * @param key メッセージのＫＥＹ
     */
    public BLogicMessage(String key) {
        this(key, (Object[]) null);
    }

    /**
     * コンストラクタ。
     *
     * @param key メッセージのＫＥＹ
     * @param values 置換文字列
     */
    public BLogicMessage(String key, Object... values) {
        this.key = key;
        this.values = values;
        this.resource = true;
    }

    /**
     * コンストラクタ。
     *
     * @param key メッセージのＫＥＹ
     * @param resource キーがバンドルキーかそれとも文字通りの値であるか
     */
    public BLogicMessage(String key, boolean resource) {
 
        this.key = key;
        this.resource = resource;

    }

    // ----------------------------------------------------- Instance Variables

    /**
     * メッセージのＫＥＹ。
     */
    protected String key = null;


    /**
     * 置換文字列を格納した配列。
     */
    protected Object[] values = null;

    /**
     * キーがバンドルキー（true）かそれとも文字通りの値（false）であるか。
     */
    protected boolean resource = true;

    // --------------------------------------------------------- Public Methods

    /**
     * メッセージのＫＥＹを取得する。
     *
     * @return メッセージのＫＥＹ
     */
    public String getKey() {

        return (this.key);

    }

    /**
     * 置換文字列を格納した配列を取得する。
     *
     * @return メッセージを格納した配列
     */
    public Object[] getValues() {

        return (this.values);

    }

    /**
     * キーがバンドルキーかそれとも文字通りの値であるかを判定する。
     *
     * @return キーがバンドルキーならtrue
     */
    public boolean isResource() {

        return (this.resource);

    }
}
