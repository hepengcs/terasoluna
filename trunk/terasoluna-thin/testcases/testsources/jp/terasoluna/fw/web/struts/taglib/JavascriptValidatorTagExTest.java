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

package jp.terasoluna.fw.web.struts.taglib;

import java.io.BufferedReader;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTag;

import jp.terasoluna.utlib.TagUTUtil;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.utlib.exception.Exception_JspWriterImpl;
import junit.framework.TestCase;

import com.mockrunner.mock.web.MockPageContext;

/**
 * {@link jp.terasoluna.fw.web.struts.taglib.JavascriptValidatorTagEx}
 * クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * Strutsが提供する、JavascriptValidatorTagクラスを拡張したクラスのテスト。<br>
 * <br>
 * doStartTag()で、renderKanaList()のテストケースも包含する。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.taglib.JavascriptValidatorTagEx
 */
public class JavascriptValidatorTagExTest extends TestCase {

    /**
     * テスト対象クラス
     */
    private JavascriptValidatorTagEx tag = null;

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(JavascriptValidatorTagExTest.class);
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
        tag = (JavascriptValidatorTagEx) TagUTUtil.create(
                JavascriptValidatorTagEx_JavascriptValidatorTagExStub01.class);
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
    public JavascriptValidatorTagExTest(String name) {
        super(name);
    }

    /**
     * testDoStartTag01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) JavascriptValidatorTag#renderJavascript()の戻り値:"abc"<br>
     *                <br>
     *                JavascriptValidatorTag#renderJavascript()を
     *                オーバーライドして、"abc"を返却するスタブを作成する。<br>
     *                renderJavascript()は、super.doStartTag()から
     *                呼び出されているため、super.doStartTag()の
     *                呼び出し確認として使用する。<br>
     *         (状態) htmlComment<br>
     *                JavascriptValidatorTagのクラス変数:true<br>
     *         
     * <br>
     * 期待値：(戻り値) int:EVAL_BODY_TAG<br>
     *         (状態変化) Writer<br>
     *                    renderKanaList()の呼び出し確認および、
     *                    super.doStartTag()の呼び出し確認を行う。:
     *                    <script type="text/javascript"
     *                     language="Javascript1.1"> <br>
     *                    <br>
     *                    <!-- Begin <br>
     *                    var hankakuKanaList = "ｱｲｳｴｵｧｨｩｪｫｶｷｸｹｺｻｼｽｾｿﾀﾁﾂｯﾃ
     *                    ﾄﾅﾆﾇﾈﾉﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓﾔﾕﾖｬｭｮﾗﾘﾙﾚﾛﾜｦﾝﾟﾞｰ･､｡｢｣";<br>
     *                    var zenkakuKanaList = "アイウヴエオァィゥェォカ
     *                    キクケコヵヶガギグゲゴサシスセソザジズゼゾタチツ
     *                    テトダヂヅデドナニヌネノハヒフヘホバビブベボパピプペ
     *                    ポマミムメモヤユヨャュョラリルレロワヮヰヱヲッンー";<br>
     *                    <br>
     *                    //End --> <br>
     *                    </script><br>
     *                    abc<br>
     *         
     * <br>
     * htmlCommentの値が"true"で、renderKanaList()の返却値を正常に出力し、
     * super.doStartTag()を正常に呼び出した場合<br>
     * <br>
     * renderKanaList()のテストも包含する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("deprecation")
    public void testDoStartTag01() throws Exception {
        // 前処理
        tag.setHtmlComment("true");

        // テスト実施
        int result = tag.doStartTag();

        // 判定
        assertEquals(result, BodyTag.EVAL_BODY_TAG);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("<script type=\"text/javascript\" " +
                "language=\"Javascript1.1\"> ", reader.readLine());
        assertEquals("", reader.readLine());
        assertEquals("<!-- Begin ", reader.readLine());
        assertEquals("var hankakuKanaList = \"ｱｲｳｴｵｧｨｩｪｫｶｷｸｹｺｻｼｽｾｿﾀﾁﾂｯﾃﾄﾅﾆﾇ" +
                "ﾈﾉﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓﾔﾕﾖｬｭｮﾗﾘﾙﾚﾛﾜｦﾝﾟﾞｰ･､｡｢｣\";", reader.readLine());
        assertEquals("var zenkakuKanaList = \"アイウヴエオァィゥェォカキクケコ" +
                "ヵヶガギグゲゴサシスセソザジズゼゾタチツテトダヂヅデドナニヌネ" +
                "ノハヒフヘホバビブベボパピプペポマミムメモヤユヨャュョラリルレ" +
                "ロワヮヰヱヲッンー\";", reader.readLine());
        assertEquals("", reader.readLine());
        assertEquals("//End --> ", reader.readLine());
        assertEquals("</script>", reader.readLine());
        assertEquals("", reader.readLine());
        assertEquals("abc", reader.readLine());
    }

    /**
     * testDoStartTag02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) JavascriptValidatorTag#renderJavascript()の戻り値:"abc"<br>
     *                <br>
     *                JavascriptValidatorTag#renderJavascript()を
     *                オーバーライドして、"abc"を返却するスタブを作成する。<br>
     *                renderJavascript()は、super.doStartTag()から
     *                呼び出されているため、super.doStartTag()の
     *                呼び出し確認として使用する。<br>
     *         (状態) htmlComment<br>
     *                JavascriptValidatorTagのクラス変数:false<br>
     *         
     * <br>
     * 期待値：(戻り値) int:EVAL_BODY_TAG<br>
     *         (状態変化) Writer<br>
     *                    renderKanaList()の呼び出し確認および、
     *                    super.doStartTag()の呼び出し確認を行う。:
     *                    <script type="text/javascript"
     *                     language="Javascript1.1"> <br>
     *                    var hankakuKanaList = "ｱｲｳｴｵｧｨｩｪｫｶｷｸｹｺｻｼｽｾｿﾀﾁﾂ
     *                    ｯﾃﾄﾅﾆﾇﾈﾉﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓﾔﾕﾖｬｭｮﾗﾘﾙﾚﾛﾜｦﾝﾟﾞｰ･､｡｢｣";<br>
     *                    var zenkakuKanaList = "アイウヴエオァィゥェォカキク
     *                    ケコヵヶガギグゲゴサシスセソザジズゼゾタチツテトダヂヅ
     *                    デドナニヌネノハヒフヘホバビブベボパピプペポマミムメモ
     *                    ヤユヨャュョラリルレロワヮヰヱヲッンー";<br>
     *                    <br>
     *                    </script><br>
     *                    abc<br>
     *         
     * <br>
     * htmlCommentの値が"false"で、renderKanaList()の返却値を正常に出力し、
     * super.doStartTag()を正常に呼び出した場合<br>
     * <br>
     * renderKanaList()のテストも包含する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("deprecation")
    public void testDoStartTag02() throws Exception {
        // 前処理
        tag.setHtmlComment("false");

        // テスト実施
        int result = tag.doStartTag();

        // 判定
        assertEquals(result, BodyTag.EVAL_BODY_TAG);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("<script type=\"text/javascript\" " +
                "language=\"Javascript1.1\"> ", reader.readLine());
        assertEquals("var hankakuKanaList = \"ｱｲｳｴｵｧｨｩｪｫｶｷｸｹｺｻｼｽｾｿﾀﾁﾂｯﾃﾄﾅﾆﾇ" +
                "ﾈﾉﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓﾔﾕﾖｬｭｮﾗﾘﾙﾚﾛﾜｦﾝﾟﾞｰ･､｡｢｣\";", reader.readLine());
        assertEquals("var zenkakuKanaList = \"アイウヴエオァィゥェォカキクケコ" +
                "ヵヶガギグゲゴサシスセソザジズゼゾタチツテトダヂヅデドナニヌネ" +
                "ノハヒフヘホバビブベボパピプペポマミムメモヤユヨャュョラリルレ" +
                "ロワヮヰヱヲッンー\";", reader.readLine());
        assertEquals("", reader.readLine());
        assertEquals("</script>", reader.readLine());
        assertEquals("", reader.readLine());
        assertEquals("abc", reader.readLine());
    }

    /**
     * testDoStartTag03()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(状態) JspWriter#print():IOExceptionが発生<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:クラス：JspException<br>
     *                    メッセージ：-<br>
     *                    ラップした例外：-<br>
     *         
     * <br>
     * JspWriterでIOExceptionが発生した場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoStartTag03() throws Exception {
        // 前処理
        // Mockオブジェクトの設定
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(tag);

        // テスト用JspWriterの生成
        Exception_JspWriterImpl out =
            new Exception_JspWriterImpl();
        out.setTrue();

        // 生成・設定したテスト用JspWriterをPageContextにセット
        UTUtil.setPrivateField(pageContext, "jspWriter", out);

        // テスト実施
        try{
            tag.doStartTag();
            fail();
        } catch (JspException e) {
            // メッセージもないため、JspExceptionが発生可否を判定
        }
    }

}
