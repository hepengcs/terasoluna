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

import java.net.MalformedURLException;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.JspException;

import org.apache.struts.Globals;
import org.apache.struts.taglib.logic.IterateTag;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.TagUTUtil;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * LinkTag ブラックボックステスト。<br>
 * 前提条件<br>
 * なし<br>
 * <br>
 */
public class LinkTagTest extends TestCase {

    // テスト対象
    LinkTag tag = null;

    /**
     * Constructor for LinkTagTest.
     * @param arg0
     */
    public LinkTagTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tag = (LinkTag) TagUTUtil.create(LinkTag.class);
    }

    /*
     * @see TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * testCalculateURL01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) IterateTag:設定あり、<br>
     *                started=true<br>
     *                lengthCount=6<br>
     *         (状態) href:not null("/href.do")<br>
     *         (状態) indexed:true<br>
     *         (状態) indexId:not null("number")<br>
     *         
     * <br>
     * 期待値：(戻り値) string:「/href.do?r=[ランダムな数値]&amp;number=5」<br>
     *         
     * <br>
     * IterateTagがnot null、indexed属性がtrue、indexId属性が"number"の場合、URLにキャッシュ避け用ランダムID、パラメータ名"number"でインデックス番号が付与されていること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCalculateURL01() throws Exception {

        IterateTag iterateTag = new IterateTag();

        // IterateTagのインデックス番号を設定する
        UTUtil.setPrivateField(iterateTag, "started", Boolean.TRUE);
        UTUtil.setPrivateField(iterateTag, "lengthCount", Integer.valueOf("6"));

        // IterateTagを設定
        tag.setParent(iterateTag);

        // LinkTag設定
        tag.setHref("/href.do");
        tag.setIndexed(true);
        tag.setIndexId("number");

        // テスト実行
        String url = tag.calculateURL();
        assertEquals(
            "/href.do?r=",
            url.substring(0, url.indexOf("r=") + 2));
        assertEquals(
            "&amp;number=5",
            url.substring(url.indexOf("&amp;")));

    }

    /**
     * testCalculateURL02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) IterateTag:設定あり、<br>
     *                started=true<br>
     *                lengthCount=6<br>
     *         (状態) href:not null("/href.do")<br>
     *         (状態) indexed:true<br>
     *         (状態) indexId:null<br>
     *         
     * <br>
     * 期待値：(戻り値) string:「/href.do?index=5&amp;r=[ランダムな数値]」<br>
     *         
     * <br>
     * IterateTagがnot null、indexed属性がtrue、indexId属性がnullの場合、URLにキャッシュ避け用ランダムID、パラメータ名"index"でインデックス番号が付与されていること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCalculateURL02() throws Exception {

        IterateTag iterateTag = new IterateTag();

        // IterateTagのインデックス番号を設定する
        UTUtil.setPrivateField(iterateTag, "started", Boolean.TRUE);
        UTUtil.setPrivateField(iterateTag, "lengthCount", Integer.valueOf("6"));

        // IterateTagを設定
        tag.setParent(iterateTag);

        // LinkTag設定
        tag.setHref("/href.do");
        tag.setIndexed(true);
        tag.setIndexId(null);

        // テスト実行
        String url = tag.calculateURL();
        assertEquals(
            "/href.do?index=5&amp;r=",
            url.substring(0, url.indexOf("r=") + 2));

    }

    /**
     * testCalculateURL03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) IterateTag:設定なし。<br>
     *         (状態) href:not null("/href.do")<br>
     *         (状態) indexed:false<br>
     *         (状態) indexId:null<br>
     *         
     * <br>
     * 期待値：(戻り値) string:「/href.do?r=[ランダムな数値]」<br>
     *         
     * <br>
     * IterateTagの設定なし、indexed属性がfalse、indexId属性がnullの場合、URLにキャッシュ避け用ランダムIDが付与されていること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCalculateURL03() throws Exception {

        // LinkTag設定
        tag.setHref("/href.do");
        tag.setIndexed(false);
        tag.setIndexId(null);

        // テスト実行
        String url = tag.calculateURL();
        assertEquals(
            "/href.do?r=",
            url.substring(0, url.indexOf("r=") + 2));
        assertNotNull(url.substring(url.indexOf("r=")));

    }

    /**
     * testCalculateURL04()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(状態) IterateTag:設定なし。<br>
     *         (状態) href:not null("/href.do")<br>
     *         (状態) indexed:true<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:JspException<br>
     *                    メッセージ："indexed="true"の指定はiterateタグで囲まれた中でのみ有効です"<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ："iterateTag is null."<br>
     *         
     * <br>
     * IterateTagの設定なし、indexed属性がtrueの場合、JspExceptionが投げられ、エラーメッセージが格納されていること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCalculateURL04() throws Exception {

        // LinkTag設定
        tag.setHref("/href.do");
        tag.setIndexed(true);

        // テスト実行
        try {
            tag.calculateURL();
            fail();
        } catch (JspException e) {
            assertEquals(e.getMessage(),
                    "indexed=\"true\"の指定はiterateタグで囲まれた中でのみ有効です");
            PageContext pageContext = (PageContext) UTUtil.getPrivateField(tag,
                    "pageContext");
            Throwable throwable = (Throwable) pageContext.getAttribute(
                    Globals.EXCEPTION_KEY, PageContext.REQUEST_SCOPE);
            assertEquals(JspException.class.getName(), throwable.getClass()
                    .getName());
            assertTrue(LogUTUtil.checkError("iterateTag is null."));
        }

    }

    /**
     * testCalculateURL05()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(状態) IterateTag:設定なし。<br>
     *         (状態) href:null<br>
     *         (状態) indexId:null<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:JspException<br>
     *                    メッセージ："リライトURLを生成できません: java.net.MalformedURLException:  "forward","href","page","action"のうちの一つを指定する必要があります"<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ："Malformed URL has occurred."<br>
     *         
     * <br>
     * forward、href、page、action属性がいずれも設定していなかった場合、JspExceptionが投げられる、エラーメッセージが格納されていること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCalculateURL05() throws Exception {

        // テスト実行
        try {
            tag.calculateURL();
            fail();
        } catch (JspException e) {
            assertEquals(e.getMessage(), "リライトURLを生成できません: "
                    + "java.net.MalformedURLException: "
                    + "\"forward\", \"href\",\"page\",\"action\""
                    + " のうちの一つを指定する必要があります");
            PageContext pageContext = (PageContext) UTUtil.getPrivateField(tag,
                    "pageContext");
            Throwable throwable = (Throwable) pageContext.getAttribute(
                    Globals.EXCEPTION_KEY, PageContext.REQUEST_SCOPE);
            assertEquals(MalformedURLException.class.getName(), throwable
                    .getClass().getName());
            LogUTUtil.checkError("Malformed URL has occurred.");
        }

    }

    /**
     * testCalculateURL06()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(状態) href:"/href.do"<br>
     *         (状態) action:"/action.do"<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:JspException<br>
     *                    メッセージ："リライトURLを生成できません: java.net.MalformedURLException:  "forward","href","page","action"のうちの一つを指定する必要があります"<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ："Malformed URL has occurred."<br>
     *         
     * <br>
     * forward、href、page、action属性が複数設定してある場合、<br>
     * JspExceptionが投げられること、エラーメッセージが格納されていること。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCalculateURL06() throws Exception {

        // LinkTag設定
        tag.setHref("/href.do");
        tag.setAction("/action.do");

        // テスト実行
        try {
            tag.calculateURL();
            fail();
        } catch (JspException e) {
            assertEquals(e.getMessage(), "リライトURLを生成できません: "
                    + "java.net.MalformedURLException: "
                    + "\"forward\", \"href\",\"page\",\"action\""
                    + " のうちの一つを指定する必要があります");
            PageContext pageContext = (PageContext) UTUtil.getPrivateField(tag,
                    "pageContext");
            Throwable throwable = (Throwable) pageContext.getAttribute(
                    Globals.EXCEPTION_KEY, PageContext.REQUEST_SCOPE);
            assertEquals(MalformedURLException.class.getName(), throwable
                    .getClass().getName());
            LogUTUtil.checkError("Malformed URL has occurred.");
        }

    }

}
