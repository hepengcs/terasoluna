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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.PropertyTestCase;

import org.apache.commons.validator.Arg;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.Msg;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.ValidatorResources;
import org.apache.commons.validator.Var;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 * {@link jp.terasoluna.fw.web.struts.form.FieldChecksEx} クラスのブラックボックステスト。
 *
 * <p>
 * <h4>【クラスの概要】</h4>
 * Validator追加ルールクラス。
 * <p>
 *
 * @see jp.terasoluna.fw.web.struts.form.FieldChecksEx
 */
public class FieldChecksExTest01 extends PropertyTestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     *
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(FieldChecksExTest01.class);
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
        deleteProperty("validation.hankaku.kana.list");
        deleteProperty("validation.zenkaku.kana.list");
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
    public FieldChecksExTest01(String name) {
        super(name);
    }

    /**
     * testIsHankakuKana01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) c:ｱｲｳｴｵｧｨｩｪｫｶｷｸｹｺｻｼｽｾｿﾀﾁﾂｯﾃﾄﾅﾆﾇﾈﾉﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓﾔﾕﾖｬｭｮﾗﾘﾙﾚﾛ
     *                  ﾜｦﾝﾟﾞｰ･､｡｢｣<br>
     *                ※一文字ずつ確認<br>
     *         (状態) hankakuKanaList:ｱｲｳｴｵｧｨｩｪｫｶｷｸｹｺｻｼｽｾｿﾀﾁﾂｯﾃﾄﾅﾆﾇﾈﾉﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓ
     *                                ﾔﾕﾖｬｭｮﾗﾘﾙﾚﾛﾜｦﾝﾟﾞｰ･､｡｢｣<br>
     *         (状態) プロパティファイル:validation.hankaku.kana.list<br>
     *                が存在しないこと。<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:全ての文字についてtrue<br>
     *
     * <br>
     * 引数に指定した文字がhankakuKanaListに含まれる場合、
     * trueが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testIsHankakuKana01() throws Exception {
        // 入力値の設定
        String hankakuKanaList =
            "ｱｲｳｴｵｧｨｩｪｫｶｷｸｹｺｻｼｽｾｿﾀﾁﾂｯﾃﾄﾅﾆﾇﾈﾉﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓﾔﾕﾖｬｭｮﾗﾘﾙﾚﾛﾜｦﾝﾟﾞｰ･､｡｢｣";

        // isHankakuKana実行
        for (int i = 0; i < hankakuKanaList.length(); i++) {
            assertTrue(FieldChecksEx.isHankakuKana(hankakuKanaList.charAt(i)));
        }
    }

    /**
     * testIsHankakuKana02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) c:'｡'-1<br>
     *                'ﾟ'+1<br>
     *                ※一文字ずつ確認<br>
     *         (状態) hankakuKanaList:ｱｲｳｴｵｧｨｩｪｫｶｷｸｹｺｻｼｽｾｿﾀﾁﾂｯﾃﾄﾅﾆﾇﾈﾉﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓ
     *                                ﾔﾕﾖｬｭｮﾗﾘﾙﾚﾛﾜｦﾝﾟﾞｰ･､｡｢｣<br>
     *         (状態) プロパティファイル:validation.hankaku.kana.list<br>
     *                が存在しないこと。<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:全ての文字についてfalse<br>
     *
     * <br>
     * 引数に指定した文字がhankakuKanaListに含まれない場合、
     * falseが取得できることを確認する。（半角カナの境界テスト）
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testIsHankakuKana02() throws Exception {
        // 入力値の設定
        char chStart = '｡' - 1;
        char chEnd = 'ﾟ' + 1;

        // isHankakuKana実行
        assertFalse(FieldChecksEx.isHankakuKana(chStart));
        assertFalse(FieldChecksEx.isHankakuKana(chEnd));
    }

    /**
     * testIsHankakuKana03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) c:'全'<br>
     *         (状態) hankakuKanaList:ｱｲｳｴｵｧｨｩｪｫｶｷｸｹｺｻｼｽｾｿﾀﾁﾂｯﾃﾄﾅﾆﾇﾈﾉﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓ
     *                                ﾔﾕﾖｬｭｮﾗﾘﾙﾚﾛﾜｦﾝﾟﾞｰ･､｡｢｣<br>
     *         (状態) プロパティファイル:validation.hankaku.kana.list<br>
     *                が存在しないこと。<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *
     * <br>
     * 引数に指定した文字がhankakuKanaListに含まれない場合、
     * falseが取得できることを確認する。（全角文字）
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testIsHankakuKana03() throws Exception {
        // 入力値の設定
        char chZenkaku = '全';

        // isHankakuKana実行
        assertFalse(FieldChecksEx.isHankakuKana(chZenkaku));
    }

    /**
     * testIsZenkakuKana01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) c:アイウヴエオァィゥェォカキクケコヵヶガギグゲゴサシスセソ
     *                  ザジズゼゾタチツテトダヂヅデドナニヌネノハヒフヘホ
     *                  バビブベボパピプペポマミムメモヤユヨャュョラリルレロ
     *                  ワヮヰヱヲッンー<br>
     *                ※一文字ずつ確認<br>
     *         (状態) zenkakuKanaList:アイウヴエオァィゥェォカキクケコヵヶ
     *                                ガギグゲゴサシスセソザジズゼゾタチツテト
     *                                ダヂヅデドナニヌネノハヒフヘホバビブベボ
     *                                パピプペポマミムメモヤユヨャュョラリルレロ
     *                                ワヮヰヱヲッンー<br>
     *         (状態) プロパティファイル:validation.zenkaku.kana.list<br>
     *                が存在しないこと。<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:全ての文字についてtrue<br>
     *
     * <br>
     * 引数に指定した文字がzenkakuKanaListに含まれる場合、
     * trueが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testIsZenkakuKana01() throws Exception {
        // 入力値の設定
        String zenkakuKanaList = "アイウヴエオァィゥェォカキクケコ" +
                "ヵヶガギグゲゴサシスセソザジズゼゾタチツテト" +
                "ダヂヅデドナニヌネノハヒフヘホバビブベボ" +
                "パピプペポマミムメモヤユヨャュョラリルレロ" +
                "ワヮヰヱヲッンー";

        // テスト実行
        for (int i = 0; i < zenkakuKanaList.length(); i++) {
            assertTrue(FieldChecksEx.isZenkakuKana(zenkakuKanaList.charAt(i)));
        }
    }

    /**
     * testIsZenkakuKana02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) c:'ァ' - 1<br>
     *                'ー' + 1<br>
     *                ※一文字ずつ確認<br>
     *         (状態) zenkakuKanaList:アイウヴエオァィゥェォカキクケコヵヶ
     *                                ガギグゲゴサシスセソザジズゼゾタチツテト
     *                                ダヂヅデドナニヌネノハヒフヘホバビブベボ
     *                                パピプペポマミムメモヤユヨャュョラリルレロ
     *                                ワヮヰヱヲッンー<br>
     *         (状態) プロパティファイル:validation.zenkaku.kana.list<br>
     *                が存在しないこと。<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:全ての文字についてfalse<br>
     *
     * <br>
     * 引数に指定した文字がzenkakuKanaListに含まれない場合、
     * falseが取得できることを確認する。（半角カナの境界テスト）
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testIsZenkakuKana02() throws Exception {
        // 入力値の設定
        char chStart = 'ァ' - 1;
        char chEnd = 'ー' + 1;

        // テストの実行
        assertFalse(FieldChecksEx.isZenkakuKana(chStart));
        assertFalse(FieldChecksEx.isZenkakuKana(chEnd));
    }

    /**
     * testIsZenkakuKana03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) c:'あ'<br>
     *         (状態) zenkakuKanaList:アイウヴエオァィゥェォカキクケコヵヶ
     *                                ガギグゲゴサシスセソザジズゼゾタチツテト
     *                                ダヂヅデドナニヌネノハヒフヘホバビブベボ
     *                                パピプペポマミムメモヤユヨャュョラリルレロ
     *                                ワヮヰヱヲッンー<br>
     *         (状態) プロパティファイル:validation.zenkaku.kana.list<br>
     *                が存在しないこと。<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *
     * <br>
     * 引数に指定した文字がzenkakuKanaListに含まれない場合、falseが取得できることを確認する。（全角平仮名）
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testIsZenkakuKana03() throws Exception {
        // 入力値の設定
        char chHiragana = 'あ';

        // テストの実行
        assertFalse(FieldChecksEx.isZenkakuKana(chHiragana));
    }

    /**
     * testIsHankaku01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) c:'\u00ff'<br>
     *                '｡'<br>
     *                'ﾟ'<br>
     *                ※一文字ずつ確認<br>
     *         (状態) hankakuKanaList:ｱｲｳｴｵｧｨｩｪｫｶｷｸｹｺｻｼｽｾｿﾀﾁﾂｯﾃﾄﾅﾆﾇﾈﾉﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓ
     *                                ﾔﾕﾖｬｭｮﾗﾘﾙﾚﾛﾜｦﾝﾟﾞｰ･､｡｢｣<br>
     *         (状態) プロパティファイル:validation.hankaku.kana.list<br>
     *                が存在しないこと。<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:全ての文字についてtrue<br>
     *
     * <br>
     * 引数に指定した文字が文字コード'\00ff'以下且つ、
     * "＼￠￡§¨￢°±´¶×÷"ではなく、hankakuKanaListに含まれる場合、
     * trueが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testIsHankaku01() throws Exception {

        // 入力値の設定
        char chHankakuMax = '\u00ff';
        char chHankakuKanaStart = '｡';
        char chHankakuKanaEnd = 'ﾟ';

        // isHankaku実行
        // 半角文字が設定されたとき、trueが返却されること
        assertTrue(FieldChecksEx.isHankaku(chHankakuMax));
        assertTrue(FieldChecksEx.isHankaku(chHankakuKanaStart));
        assertTrue(FieldChecksEx.isHankaku(chHankakuKanaEnd));
    }

    /**
     * testIsHankaku02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) c:'\u0100'<br>
     *                '｡'-1<br>
     *                'ﾟ'+1<br>
     *                ※一文字ずつ確認<br>
     *         (状態) hankakuKanaList:ｱｲｳｴｵｧｨｩｪｫｶｷｸｹｺｻｼｽｾｿﾀﾁﾂｯﾃﾄﾅﾆﾇﾈﾉﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓ
     *                                ﾔﾕﾖｬｭｮﾗﾘﾙﾚﾛﾜｦﾝﾟﾞｰ･､｡｢｣<br>
     *         (状態) プロパティファイル:validation.hankaku.kana.list<br>
     *                が存在しないこと。<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:全ての文字についてfalse<br>
     *
     * <br>
     * 引数に指定した文字が文字コード'\00ff'以上、または、
     * "＼￠￡§¨￢°±´¶×÷"に含まれる、または、
     * hankakuKanaListに含まれない場合、falseが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testIsHankaku02() throws Exception {

        // 入力値の設定
        char chUpperff = '\u0100';

        char chKanaStart = '｡' - 1;
        char chKanaEnd = 'ﾟ' + 1;

        // isHankaku実行
        assertFalse(FieldChecksEx.isHankaku(chUpperff));
        assertFalse(FieldChecksEx.isHankaku(chKanaStart));
        assertFalse(FieldChecksEx.isHankaku(chKanaEnd));

    }

    /**
     * testIsHankaku03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) c:'ア'<br>
     *                '６'<br>
     *                '＆'<br>
     *                'ａ'<br>
     *                'ｚ'<br>
     *                'Ａ'<br>
     *                'Ｚ'<br>
     *         (状態) hankakuKanaList:ｱｲｳｴｵｧｨｩｪｫｶｷｸｹｺｻｼｽｾｿﾀﾁﾂｯﾃﾄﾅﾆﾇﾈﾉﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓ
     *                                ﾔﾕﾖｬｭｮﾗﾘﾙﾚﾛﾜｦﾝﾟﾞｰ･､｡｢｣<br>
     *         (状態) プロパティファイル:validation.hankaku.kana.list<br>
     *                が存在しないこと。<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *
     * <br>
     * 引数に指定した文字が全角文字である場合、falseが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testIsHankaku03() throws Exception {

        // 入力値の設定
        char[] input = {
            'ア',
            '６',
            '＆',
            'ａ',
            'ｚ',
            'Ａ',
            'Ｚ'
        };

        // isHankaku実行
        // 全角文字が設定されたとき、falseが返却されること
        for (char c : input) {
            assertFalse(FieldChecksEx.isHankaku(c));
        }
    }

    /**
     * testIsHankaku04()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) c:"＼￠￡§¨￢°±´¶×÷"<br>
     *                ※一文字ずつ確認<br>
     *         (状態) hankakuKanaList:ｱｲｳｴｵｧｨｩｪｫｶｷｸｹｺｻｼｽｾｿﾀﾁﾂｯﾃﾄﾅﾆﾇﾈﾉﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓ
     *                                ﾔﾕﾖｬｭｮﾗﾘﾙﾚﾛﾜｦﾝﾟﾞｰ･､｡｢｣<br>
     *         (状態) プロパティファイル:validation.hankaku.kana.list<br>
     *                が存在しないこと。<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *
     * <br>
     * 引数に指定した文字が文字コード'\00ff'以上、または、
     * "＼￠￡§¨￢°±´¶×÷"に含まれる、または、
     * hankakuKanaListに含まれない場合、falseが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testIsHankaku04() throws Exception {
        // 入力値の設定
        String zenkakuBeginU00List = "＼￠￡§¨￢°±´¶×÷";

        // テスト実行
        for (int i = 0; i < zenkakuBeginU00List.length(); i++) {
            assertFalse(FieldChecksEx.isHankaku(zenkakuBeginU00List.charAt(i)));
        }
    }

    /**
     * testIsZenkaku01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) c:'\u0100'<br>
     *                '｡'-1<br>
     *                'ﾟ'+1<br>
     *                ※一文字ずつ確認<br>
     *         (状態) hankakuKanaList:ｱｲｳｴｵｧｨｩｪｫｶｷｸｹｺｻｼｽｾｿﾀﾁﾂｯﾃﾄﾅﾆﾇﾈﾉﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓ
     *                                ﾔﾕﾖｬｭｮﾗﾘﾙﾚﾛﾜｦﾝﾟﾞｰ･､｡｢｣<br>
     *         (状態) プロパティファイル:validation.hankaku.kana.list<br>
     *                が存在しないこと。<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:全ての文字についてtrue<br>
     *
     * <br>
     * 引数に指定した文字が文字コード'\00ff'より大きい、且つ、
     * "＼￠￡§¨￢°±´¶×÷"に含まれるか、
     * hankakuKanaListに含まれない場合、trueが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testIsZenkaku01() throws Exception {

        // 入力値の設定
        char chZenkakuMin = '\u0100';
        char chZenkakuKanaStart = '｡' - 1;
        char chZenkakuKanaEnd = 'ﾟ' + 1;

        // isZenkaku実行
        // 全角文字列が設定されたとき、trueが返却されること
        assertTrue(FieldChecksEx.isZenkaku(chZenkakuMin));
        assertTrue(FieldChecksEx.isZenkaku(chZenkakuKanaStart));
        assertTrue(FieldChecksEx.isZenkaku(chZenkakuKanaEnd));
    }

    /**
     * testIsZenkaku02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) c:'\u00ff'<br>
     *                '｡'<br>
     *                'ﾟ'<br>
     *                ※一文字ずつ確認<br>
     *         (状態) hankakuKanaList:ｱｲｳｴｵｧｨｩｪｫｶｷｸｹｺｻｼｽｾｿﾀﾁﾂｯﾃﾄﾅﾆﾇﾈﾉﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓ
     *                                ﾔﾕﾖｬｭｮﾗﾘﾙﾚﾛﾜｦﾝﾟﾞｰ･､｡｢｣<br>
     *         (状態) プロパティファイル:validation.hankaku.kana.list<br>
     *                が存在しないこと。<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:全ての文字についてfalse<br>
     *
     * <br>
     * 引数に指定した文字が文字コード'\00ff'以下且つ、
     * "＼￠￡§¨￢°±´¶×÷"ではなく、
     * hankakuKanaListに含まれる場合、falseが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testIsZenkaku02() throws Exception {

        // 入力値の設定
        char chZenkakuMin = '\u00ff';
        char chZenkakuKanaStart = '｡';
        char chZenkakuKanaEnd = 'ﾟ';

        // isZenkaku実行
        // 半角文字が設定されたとき、falseが返却されること
        assertFalse(FieldChecksEx.isZenkaku(chZenkakuMin));
        assertFalse(FieldChecksEx.isZenkaku(chZenkakuKanaStart));
        assertFalse(FieldChecksEx.isZenkaku(chZenkakuKanaEnd));

    }

    /**
     * testIsZenkaku03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) c:'ｱ'<br>
     *                '6'<br>
     *                '&'<br>
     *                'a'<br>
     *                'z'<br>
     *                'A'<br>
     *                'Z'<br>
     *         (状態) hankakuKanaList:ｱｲｳｴｵｧｨｩｪｫｶｷｸｹｺｻｼｽｾｿﾀﾁﾂｯﾃﾄﾅﾆﾇﾈﾉﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓ
     *                                ﾔﾕﾖｬｭｮﾗﾘﾙﾚﾛﾜｦﾝﾟﾞｰ･､｡｢｣<br>
     *         (状態) プロパティファイル:validation.hankaku.kana.list<br>
     *                が存在しないこと。<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *
     * <br>
     * 引数に指定した文字が半角文字である場合、falseが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testIsZenkaku03() throws Exception {

        // 入力値の設定
        char[] input = {
            'ｱ',
            '6',
            '&',
            'a',
            'z',
            'A',
            'Z'
        };

        // isZenkaku実行
        // 半角文字が設定されたとき、falseが返却されること
        for (char c : input) {
            assertFalse(FieldChecksEx.isZenkaku(c));
        }
    }

    /**
     * testIsZenkaku04()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) c:"＼￠￡§¨￢°±´¶×÷"<br>
     *                ※一文字ずつ確認<br>
     *         (状態) hankakuKanaList:ｱｲｳｴｵｧｨｩｪｫｶｷｸｹｺｻｼｽｾｿﾀﾁﾂｯﾃﾄﾅﾆﾇﾈﾉﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓ
     *                                ﾔﾕﾖｬｭｮﾗﾘﾙﾚﾛﾜｦﾝﾟﾞｰ･､｡｢｣<br>
     *         (状態) プロパティファイル:validation.hankaku.kana.list<br>
     *                が存在しないこと。<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *
     * <br>
     * 引数に指定した文字が"＼￠￡§¨￢°±´¶×÷"に含まれる場合、
     * trueが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testIsZenkaku04() throws Exception {
        // 入力値の設定
        String zenkakuBeginU00List = "＼￠￡§¨￢°±´¶×÷";

        // テスト実行
        for (int i = 0; i < zenkakuBeginU00List.length(); i++) {
            assertTrue(FieldChecksEx.isZenkaku(zenkakuBeginU00List.charAt(i)));
        }
    }

    /**
     * testIsValid01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) result:Boolean(true)<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *
     * <br>
     * 引数の値がBooleanのtrueの場合、trueが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testIsValid01() throws Exception {

        // 入力値の設定
        Boolean result = new Boolean(true);

        // テスト実行
        Boolean ret = FieldChecksEx.isValid(result);
        assertTrue(ret.booleanValue());
    }

    /**
     * testIsValid02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) result:Boolean(false)<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *
     * <br>
     * 引数の値がBooleanのfalseの場合、falseが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testIsValid02() throws Exception {

        // 入力値の設定
        Boolean result = new Boolean(false);

        // テスト実行
        Boolean ret = FieldChecksEx.isValid(result);
        assertFalse(ret.booleanValue());
    }

    /**
     * testIsValid03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) result:"String"<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *
     * <br>
     * 引数の値がBoolean型ではない場合、trueが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testIsValid03() throws Exception {

        // 入力値の設定
        String result = "String";

        // テスト実行
        Boolean ret = FieldChecksEx.isValid(result);
        assertTrue(ret.booleanValue());
    }

    /**
     * testReplaceIndexString01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) arg:null<br>
     *         (引数) pos:0<br>
     *
     * <br>
     * 期待値：(戻り値) String:null<br>
     *
     * <br>
     * 引数のargがnullの場合、nullが取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testReplaceIndexString01() throws Exception {
        // テスト実行
        String ret = FieldChecksEx.replaceIndexString(null, 0);
        assertNull(ret);
    }

    /**
     * testReplaceIndexString02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) arg:"##INDEX"<br>
     *         (引数) pos:0<br>
     *
     * <br>
     * 期待値：(戻り値) String:"0"<br>
     *
     * <br>
     * 引数のargが"##INDEX"の場合、第二引数で渡したposの値が取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testReplaceIndexString02() throws Exception {
        // テスト実行
        String ret = FieldChecksEx.replaceIndexString("##INDEX", 0);
        assertEquals("0", ret);
    }

    /**
     * testReplaceIndexString03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) arg:"test"<br>
     *         (引数) pos:0<br>
     *
     * <br>
     * 期待値：(戻り値) String:"test"<br>
     *
     * <br>
     * 引数のargが"##INDEX"ではない場合、argの値が取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testReplaceIndexString03() throws Exception {

        // テスト実行
        String ret = FieldChecksEx.replaceIndexString("test", 0);
        assertEquals("test", ret);
    }

    /**
     * testReplaceIndexString04()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) arg:"##index"<br>
     *         (引数) pos:99<br>
     *
     * <br>
     * 期待値：(戻り値) String:"99"<br>
     *
     * <br>
     * 引数のargが"##index"(小文字が含まれる)の場合、第二引数で渡したposの値が取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testReplaceIndexString04() throws Exception {
        // テスト実行
        String ret = FieldChecksEx.replaceIndexString("##index", 99);
        assertEquals("99", ret);
    }

    /**
     * testReplaceIndexString05()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) arg:""<br>
     *         (引数) pos:999<br>
     *
     * <br>
     * 期待値：(戻り値) String:""<br>
     *
     * <br>
     * 引数のargが"##INDEX"ではない場合、argの値が取得できることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testReplaceIndexString05() throws Exception {
        // テスト実行
        String ret = FieldChecksEx.replaceIndexString("", 999);
        assertEquals("", ret);
    }

    /**
     * testGetArrayIndexField01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) field:not null<br>
     *                ※Arg属性が存在しない<br>
     *         (引数) pos:0<br>
     *
     * <br>
     * 期待値：(戻り値) Field:not null<br>
     *                  （状態変化なし）<br>
     *
     * <br>
     * 引数のfieldにArgインスタンスが設定されていない場合、
     * fieldに変化がないことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetArrayIndexField01() throws Exception {

        // 入力値の設定
        Field field = new Field();

        // テスト実行
        Field retField = FieldChecksEx.getArrayIndexField(field, 0);
        assertNull(retField.getArg(0));
        assertNull(retField.getArg(1));
        assertNull(retField.getArg(2));
        assertNull(retField.getArg(3));
    }

    /**
     * testGetArrayIndexField02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) field:not null<br>
     *                {Arg:key="arg0", position=0;<br>
     *                 Arg:key="arg1", position=1;<br>
     *                 Arg:key="arg2", position=2;<br>
     *                 Arg:key="arg3", position=3}<br>
     *         (引数) pos:0<br>
     *
     * <br>
     * 期待値：(戻り値) Field:not null<br>
     *                  {Arg:key="arg0", position=0;<br>
     *                   Arg:key="arg1", position=1;<br>
     *                   Arg:key="arg2", position=2;<br>
     *                   Arg:key="arg3", position=3}<br>
     *                  (状態変化なし)<br>
     *
     * <br>
     * 引数のfieldに設定されたArgインスタンスのkeyの値が"##INDEX"ではない場合、
     * fieldに変化がないことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetArrayIndexField02() throws Exception {

        // 入力値の設定
        Field field = new Field();
        // arg設定
        Arg param0 = new Arg();
        param0.setKey("arg0");
        param0.setPosition(0);
        field.addArg(param0);

        Arg param1 = new Arg();
        param1.setKey("arg1");
        param1.setPosition(1);
        field.addArg(param1);

        Arg param2 = new Arg();
        param2.setPosition(2);
        param2.setKey("arg2");
        field.addArg(param2);

        Arg param3 = new Arg();
        param3.setPosition(3);
        param3.setKey("arg3");
        field.addArg(param3);

        // テスト実行
        Field retField = FieldChecksEx.getArrayIndexField(field, 0);

        assertEquals("arg0", retField.getArg(0).getKey());
        assertEquals("arg1", retField.getArg(1).getKey());
        assertEquals("arg2", retField.getArg(2).getKey());
        assertEquals("arg3", retField.getArg(3).getKey());
    }

    /**
     * testGetArrayIndexField03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) field:not null<br>
     *                {Arg:key="##INDEX", position=0;<br>
     *                 Arg:key="##INDEX", position=1;<br>
     *                 Arg:key="##INDEX", position=2;<br>
     *                 Arg:key="##INDEX", position=3}<br>
     *         (引数) pos:0<br>
     *
     * <br>
     * 期待値：(戻り値) Field:not null<br>
     *                  {Arg:key="1", position=0;<br>
     *                   Arg:key="1", position=1;<br>
     *                   Arg:key="1", position=2;<br>
     *                   Arg:key="1", position=3}<br>
     *
     * <br>
     * 引数のfieldに設定されたArgインスタンスのkeyの値が"##INDEX"である場合、
     * keyの値が第二引数のposの値＋１になることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetArrayIndexField03() throws Exception {

        // 入力値の設定
        Field field = new Field();
        // arg設定
        Arg param0 = new Arg();
        param0.setKey("##INDEX");
        param0.setPosition(0);
        field.addArg(param0);

        Arg param1 = new Arg();
        param1.setKey("##INDEX");
        param1.setPosition(1);
        field.addArg(param1);

        Arg param2 = new Arg();
        param2.setKey("##INDEX");
        param2.setPosition(2);
        field.addArg(param2);

        Arg param3 = new Arg();
        param3.setKey("##INDEX");
        param3.setPosition(3);
        field.addArg(param3);

        // テスト実行
        Field retField = FieldChecksEx.getArrayIndexField(field, 0);

        assertEquals("1", retField.getArg(0).getKey());
        assertEquals("1", retField.getArg(1).getKey());
        assertEquals("1", retField.getArg(2).getKey());
        assertEquals("1", retField.getArg(3).getKey());
    }

    /**
     * testGetArrayIndexField04()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) field:not null<br>
     *                {Arg:key="##INDEX", position=0;<br>
     *                 Arg:key="arg1", position=1;<br>
     *                 Arg:key="arg2", position=2;<br>
     *                 Arg:key="##INDEX", position=3}<br>
     *         (引数) pos:0<br>
     *
     * <br>
     * 期待値：(戻り値) Field:not null<br>
     *                  {Arg:key="1", position=0;<br>
     *                   Arg:key="arg1", position=1;<br>
     *                   Arg:key="arg2", position=2;<br>
     *                   Arg:key="1", position=3}<br>
     *
     * <br>
     * 引数のfieldに設定されたArgインスタンスのkeyの値が"##INDEX"である場合、
     * keyの値が第二引数のposの値＋１になることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetArrayIndexField04() throws Exception {

        // 入力値の設定
        Field field = new Field();
        // arg設定
        Arg param0 = new Arg();
        param0.setKey("##INDEX");
        param0.setPosition(0);
        field.addArg(param0);

        Arg param1 = new Arg();
        param1.setKey("arg1");
        param1.setPosition(1);
        field.addArg(param1);

        Arg param2 = new Arg();
        param2.setKey("arg2");
        param2.setPosition(2);
        field.addArg(param2);

        Arg param3 = new Arg();
        param3.setKey("##INDEX");
        param3.setPosition(3);
        field.addArg(param3);

        // テスト実行
        Field retField = FieldChecksEx.getArrayIndexField(field, 0);

        assertEquals("1", retField.getArg(0).getKey());
        assertEquals("arg1", retField.getArg(1).getKey());
        assertEquals("arg2", retField.getArg(2).getKey());
        assertEquals("1", retField.getArg(3).getKey());
    }

    /**
     * testValidateAlphaNumericString01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:null<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                Msg("message","message")<br>
     *                field:var<br>
     *                name:"mask"<br>
     *                value="^[a-z]*$"<br>
     *                jsType="false"<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *         (状態) validateMask:true<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) field:var：<br>
     *                    name:"mask"<br>
     *                    value="^[a-z]*$"<br>
     *                    jsType="false"<br>
     *         (状態変化) errors:状態変化なし<br>
     *
     * <br>
     * 引数のbeanがnullのとき、trueが返却され、
     * errorsにメッセージが追加されないことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateAlphaNumericString01() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = null;
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        // ++++ 検証フィールド情報
        Field field = new Field();
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setResource(false);
        field.addMsg(msg);
        field.addVar("mask", "^[a-z]*$", "false");
        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        MockHttpServletRequest request = new MockHttpServletRequest();
        Validator validator = new Validator(new ValidatorResources());

        // テスト実行
        boolean result =
            FieldChecksEx.validateAlphaNumericString(
                bean,
                va,
                field,
                errors,
                validator,
                request);
        // テスト結果確認
        // trueが返却されていること。
        assertTrue(result);
        // エラー情報が空であること。
        assertTrue(errors.isEmpty());
        // fieldの状態変化確認
        Var var = field.getVar("mask");
        assertNotNull(var);
        assertEquals("mask", var.getName());
        assertEquals("^[a-z]*$", var.getValue());
        assertEquals("false", var.getJsType());
    }

    /**
     * testValidateAlphaNumericString02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:""<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *         (状態) validateMask:true<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) field:var：<br>
     *                    name="mask"<br>
     *                    がnullであること。<br>
     *         (状態変化) errors:状態変化なし<br>
     *
     * <br>
     * 引数のbeanが空文字のとき、trueが返却され、
     * errorsにメッセージが追加されないことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateAlphaNumericString02() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        // ++++ 検証フィールド情報
        Field field = new Field();
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setResource(false);
        field.addMsg(msg);
        // エラー情報
        ActionMessages errors = new ActionMessages();
        // 擬似HTTPリクエスト
        MockHttpServletRequest request = new MockHttpServletRequest();
        Validator validator = new Validator(new ValidatorResources());

        // テスト実行
        boolean result =
            FieldChecksEx.validateAlphaNumericString(
                bean,
                va,
                field,
                errors,
                validator,
                request);
        // テスト結果確認
        // trueが返却されていること。
        assertTrue(result);
        // エラー情報が空であること。
        assertTrue(errors.isEmpty());
        // fieldの状態変化確認
        Var var = field.getVar("mask");
        assertNull(var);
    }

    /**
     * testValidateAlphaNumericString03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:"a0A"<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                (空の要素)<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *         (状態) validateMask:true<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         (状態変化) field:var：<br>
     *                    name="mask"<br>
     *                    がnullであること。<br>
     *         (状態変化) errors:状態変化なし<br>
     *
     * <br>
     * 引数のbeanが半角英数文字のみで構成されている場合、trueが返却され、
     * errorsにメッセージが追加されないことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateAlphaNumericString03() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        String bean = "a0A";
        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);

        // エラー情報
        ActionMessages errors = new ActionMessages();

        // 擬似HTTPリクエスト
        MockHttpServletRequest request = new MockHttpServletRequest();
        Validator validator = new Validator(new ValidatorResources());

        // テスト実行
        boolean result =
            FieldChecksEx.validateAlphaNumericString(
                bean,
                va,
                field,
                errors,
                validator,
                request);
        // テスト結果確認
        // trueが返却されていること。
        assertTrue(result);
        // エラー情報にエラーオブジェクトが登録されていないこと。
        assertTrue(errors.isEmpty());
        // fieldの状態変化確認
        Var var = field.getVar("mask");
        assertNull(var);
    }

    /**
     * testValidateAlphaNumericString04()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) bean:HashMap<br>
     *                ["field1"="Zg3%"]<br>
     *         (引数) va:not null<br>
     *         (引数) field:not null<br>
     *                property="field1"<br>
     *                Msg("message","message")<br>
     *         (引数) errors:not null<br>
     *                ActionMessage("testMessage")<br>
     *         (引数) validator:not null<br>
     *         (引数) request:not null<br>
     *         (状態) validateMask:false<br>
     *
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         (状態変化) field:var：<br>
     *                    name="mask"<br>
     *                    がnullであること。<br>
     *         (状態変化) errors:ActionMessage("testMessage")<br>
     *                    ActionMessage("message")<br>
     *
     * <br>
     * 引数のbeanがString型ではない場合、
     * fieldから取得したプロパティの値に対してチェックが行われることを確認する。
     * 入力チェックエラーとなったとき、
     * errorsにメッセージが追加されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testValidateAlphaNumericString04() throws Exception {
        //テストデータ設定
        // ++++ beanオブジェクト ++++
        Map<String, String> bean = new HashMap<String, String>();
        bean.put("field1", "Zg3%");

        // ++++ 検証設定オブジェクト
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ 検証フィールド情報
        Field field = new Field();
        field.setProperty("field1");
        // メッセージ設定
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);

        // エラー情報（ActionMessageを1件設定）
        ActionMessages errors = new ActionMessages();
        ActionMessage error = new ActionMessage("testMessage");
        errors.add(Globals.ERROR_KEY, error);

        // 擬似HTTPリクエスト
        MockHttpServletRequest request = new MockHttpServletRequest();
        Validator validator = new Validator(new ValidatorResources());

        // テスト実行
        boolean result =
            FieldChecksEx.validateAlphaNumericString(
                bean,
                va,
                field,
                errors,
                validator,
                request);

        // テスト結果確認
        // falseが返却されていること。
        assertFalse(result);
        // エラー情報にエラーオブジェクトが2つ登録されていること。
        assertEquals(2, errors.size());
        // エラー情報に設定されたメッセージが登録されていること。
        // 既存のActionMessageが上書きされないこと。
        Iterator it = errors.get();
        List<String> list = new ArrayList<String>();
        while (it.hasNext()) {
            ActionMessage retError = (ActionMessage) it.next();
            list.add(retError.getKey());
        }
        assertTrue(list.contains("testMessage"));
        assertTrue(list.contains("message"));
        // fieldの状態変化確認
        Var var = field.getVar("mask");
        assertNull(var);
    }

    /**
     * testGetByteLength01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) value:null<br>
     *
     * <br>
     * 期待値：(戻り値) int:0<br>
     *
     * <br>
     * 引数のvalueがnullのとき、0が取得できることを確認する。<br>
     * その他のパターンはvalidateByteLength、validateByteRangeで包含。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetByteLength01() throws Exception {
        int result = FieldChecksEx.getByteLength(null, "test");
        assertEquals(0, result);
    }

    /**
     * testGetByteLength02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) value:""<br>
     *
     * <br>
     * 期待値：(戻り値) int:0<br>
     *
     * <br>
     * 引数のvalueが空文字のとき、0が取得できることを確認する。<br>
     * その他のパターンはvalidateByteLength、validateByteRangeで包含。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetByteLength02() throws Exception {
        int result = FieldChecksEx.getByteLength("", "test");
        assertEquals(0, result);
    }

}
