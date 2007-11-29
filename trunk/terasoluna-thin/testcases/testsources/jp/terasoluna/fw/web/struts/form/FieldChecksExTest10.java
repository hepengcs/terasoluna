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

package jp.terasoluna.fw.web.struts.form;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.PropertyTestCase;

/**
 * {@link jp.terasoluna.fw.web.struts.form.FieldChecksEx}
 * クラスのブラックボックステスト。
 *
 * <p>
 * static節のテスト。</br>
 * validation.hankaku.kana.list</br>
 * validation.zenkaku.kana.list</br>
 * をプロパティから正常に読み込むかテストする。</br>
 * このクラスは他（01～09）のFieldChecksExのテストとは別のVM
 * で実行する必要がある。
 *
 * @see jp.terasoluna.fw.web.struts.form.FieldChecksEx
 */
public class FieldChecksExTest10 extends PropertyTestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     *
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(FieldChecksExTest10.class);
    }

    /**
     * 初期化処理を行う。
     *
     * @throws Exception このメソッドで発生した例外
     * @see jp.terasoluna.utlib.spring.PropertyTestCase#setUpData()
     */
    @Override
    protected void setUpData() throws Exception {
        LogUTUtil.flush();
        addProperty("validation.hankaku.kana.list", "hankaku.kana");
        addProperty("validation.zenkaku.kana.list", "zenkaku.kana");
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
    public FieldChecksExTest10(String name) {
        super(name);
    }

    /**
     * testGetHankakuKanaList01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(状態) プロパティファイル:validation.hankaku.kana.list<br>
     *                =hankaku.kana<br>
     *
     * <br>
     * 期待値：(戻り値) String:hankaku.kana<br>
     *
     * <br>
     * クラス変数hankakuKanaListの値が取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetHankakuKanaList01() throws Exception {
        assertEquals("hankaku.kana",
                FieldChecksEx.getHankakuKanaList());
    }

    /**
     * testGetZenkakuKanaList01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(状態) プロパティファイル:validation.zenkaku.kana.list<br>
     *                =zenkaku.kana<br>
     *
     * <br>
     * 期待値：(戻り値) String:zenkaku.kana<br>
     *
     * <br>
     * クラス変数zenkakuKanaListの値が取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetZenkakuKanaList01() throws Exception {
        assertEquals("zenkaku.kana",
                FieldChecksEx.getZenkakuKanaList());
    }

}
