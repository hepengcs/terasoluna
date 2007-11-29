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

package jp.terasoluna.fw.web.taglib;

import java.io.BufferedReader;
import java.text.DateFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import jp.terasoluna.utlib.TagUTUtil;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockPageContext;

/**
 * {@link jp.terasoluna.framework.web.taglib.InputCalendarTag} 
 * クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * 指定されたフィールドに対して選択した日付の入力を行うカレンダー入力機能。
 * <p>
 * 
 * @see jp.terasoluna.framework.web.taglib.InputCalendarTag
 */
public class InputCalendarTagTest extends TestCase {

    /**
     * テスト対象クラス
     */
    private InputCalendarTag calendar = null;

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(InputCalendarTagTest.class);
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
        calendar = (InputCalendarTag) TagUTUtil.create(InputCalendarTag.class);
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
        calendar = null;
    }

    /**
     * コンストラクタ。
     * 
     * @param name このテストケースの名前。
     */
    public InputCalendarTagTest(String name) {
        super(name);
    }

    /**
     * testGetFor01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) forId:"abc"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"abc"<br>
     *         
     * <br>
     * ※正常系一件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetFor01() throws Exception {
        // 前処理
        String param = "abc";
        UTUtil.setPrivateField(calendar, "forId", param);

        // テスト実施
        String value = calendar.getFor();

        // 判定
        assertEquals(param, value);
    }

    /**
     * testSetFor01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) forId:"abc"<br>
     *         (状態) forId:null<br>
     *         
     * <br>
     * 期待値：(状態変化) forId:"abc"<br>
     *         
     * <br>
     * ※正常系一件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetFor01() throws Exception {
        // 前処理
        String param = "abc";
        UTUtil.setPrivateField(calendar, "forId", null);

        // テスト実施
        calendar.setFor(param);

        // 判定
        assertEquals(param, UTUtil.getPrivateField(calendar, "forId"));
    }

    /**
     * testGetFormat01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) format:"abc"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"abc"<br>
     *         
     * <br>
     * ※正常系一件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetFormat01() throws Exception {
        // 前処理
        String param = "abc";
        UTUtil.setPrivateField(calendar, "format", param);

        // テスト実施
        String value = calendar.getFormat();

        // 判定
        assertEquals(param, value);
    }

    /**
     * testSetFormat01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) format:"abc"<br>
     *         (状態) format:null<br>
     *         
     * <br>
     * 期待値：(状態変化) format:"abc"<br>
     *         
     * <br>
     * ※正常系一件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetFormat01() throws Exception {
        // 前処理
        String param = "abc";
        UTUtil.setPrivateField(calendar, "format", null);

        // テスト実施
        calendar.setFormat(param);

        // 判定
        assertEquals(param, UTUtil.getPrivateField(calendar, "format"));
    }

    /**
     * testGetFormatKey01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) formatKey:"abc"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"abc"<br>
     *         
     * <br>
     * ※正常系一件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetFormatKey01() throws Exception {
        // 前処理
        String param = "abc";
        UTUtil.setPrivateField(calendar, "formatKey", param);

        // テスト実施
        String value = calendar.getFormatKey();

        // 判定
        assertEquals(param, value);
    }

    /**
     * testSetFormatKey01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) formatKey:"abc"<br>
     *         (状態) formatKey:null<br>
     *         
     * <br>
     * 期待値：(状態変化) formatKey:"abc"<br>
     *         
     * <br>
     * ※正常系一件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetFormatKey01() throws Exception {
        // 前処理
        String param = "abc";
        UTUtil.setPrivateField(calendar, "formatKey", null);

        // テスト実施
        calendar.setFormatKey(param);

        // 判定
        assertEquals(param, UTUtil.getPrivateField(calendar, "formatKey"));
    }

    /**
     * testDoStartTag01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) calendar.properties<br>
     *                の存在:なし<br>
     *         (状態) getPageContextFlg（）<br>
     *                の戻り値:false<br>
     *         
     * <br>
     * 期待値：(戻り値) int:SKIP_BODY<br>
     *         (状態変化) defineJavaScript（）の呼出:
     *                   以下の引数で呼び出されていることを確認<br>
     *                    引数1：calendarBundle(null)<br>
     *         (状態変化) defineButton（）の呼出:
     *                   以下の引数で呼び出されていることを確認<br>
     *                    引数1：calendarBundle(null)<br>
     *         (状態変化) pageContextのINPUTCALENDAR_FLGの状態:true<br>
     *         
     * <br>
     * 指定されたプロパティファイルが存在しない、かつJavaScriptの出力を行う場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoStartTag01() throws Exception {

        // テスト対象スタブの生成
        InputCalendarTag_InputCalendarTagStub01 calendar
            = (InputCalendarTag_InputCalendarTagStub01)
                TagUTUtil.create(InputCalendarTag_InputCalendarTagStub01.class);

        // 前処理
        // Mockオブジェクトの設定
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(calendar);
        MockHttpServletRequest request
            = (MockHttpServletRequest) pageContext.getRequest();

        pageContext.setAttribute(InputCalendarTag.INPUTCALENDAR_FLG,
                Boolean.valueOf(false));
        request.addLocale(Locale.CANADA);

        // テスト実施
        int value = calendar.doStartTag();

        // 判定
        assertEquals(Tag.SKIP_BODY, value);
        assertTrue(calendar.isDefineJavaScriptCalled());
        assertTrue(calendar.isDefineButtonCalled());
        assertNull(calendar.getDefineJavaScriptCalendarBundle());
        assertNull(calendar.getDefineButtonCalendarBundle());
        boolean bol = ((Boolean) pageContext.getAttribute(
                InputCalendarTag.INPUTCALENDAR_FLG)).booleanValue();
        assertTrue(bol);
    }

    /**
     * testDoStartTag02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) calendar.properties<br>
     *                の存在:あり<br>
     *         (状態) getPageContextFlg（）<br>
     *                の戻り値:true<br>
     *         
     * <br>
     * 期待値：(戻り値) int:SKIP_BODY<br>
     *         (状態変化) defineJavaScript（）の呼出:呼び出されないことを確認<br>
     *         (状態変化) defineButton（）の呼出:
     *                   以下の引数で呼び出されていることを確認<br>
     *                    引数1：calendarBundle<br>
     *         (状態変化) pageContextのINPUTCALENDAR_FLGの状態:true<br>
     *         
     * <br>
     * 指定されたプロパティファイルが存在する、かつJavaScriptの出力を行わない場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoStartTag02() throws Exception {

        // テスト対象スタブの生成
        InputCalendarTag_InputCalendarTagStub01 calendar
            = (InputCalendarTag_InputCalendarTagStub01)
                TagUTUtil.create(InputCalendarTag_InputCalendarTagStub01.class);

        // 前処理
        // Mockオブジェクトの設定
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(calendar);
        MockHttpServletRequest request
            = (MockHttpServletRequest) pageContext.getRequest();

        pageContext.setAttribute(InputCalendarTag.INPUTCALENDAR_FLG,
            Boolean.valueOf(true));
        request.addLocale(Locale.UK);

        // テスト実施
        int value = calendar.doStartTag();

        // 判定
        assertEquals(Tag.SKIP_BODY, value);
        assertFalse(calendar.isDefineJavaScriptCalled());
        assertTrue(calendar.isDefineButtonCalled());
        assertNull(calendar.getDefineJavaScriptCalendarBundle());
        assertNotNull(calendar.getDefineButtonCalendarBundle());
        boolean bol = ((Boolean) pageContext.getAttribute(
                InputCalendarTag.INPUTCALENDAR_FLG)).booleanValue();
        assertTrue(bol);
    }

    /**
     * testDefineJavaScript01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) calendarBundle:null<br>
     *         (状態) pageContext.getRequest().getLocale():Locale.JAPAN<br>
     *         
     * <br>
     * 期待値：(状態変化) writer:<script type="text/javascript"><br>
     *                    <!--<br>
     *                    var localja = true;<br>
     *                    var jscalendarMonthName = new Array("1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月");<br>
     *                    var jscalendarDayName = new Array("日","月","火","水","木","金","土");<br>
     *                    var jscalendarTodayString = "";<br>
     *                    var jscalendarThemePrefix = "BlueStyle";<br>
     *                    var jscalendarImgDir = "img/calendar/BlueStyle/";<br>
     *                    --><br>
     *                    </script><br>
     *                    <script type="text/javascript" src="js/InputCalendar.js" ></script><br>
     *                    <link rel="stylesheet" href="css/BlueStyleInputCalendar.css"  type="text/css"/><br>
     *                    <script type="text/javascript"><br>
     *                    <!--<br>
     *                    <br>
     *                    jscalendarInit();<br>
     *                    --><br>
     *                    </script><br>
     *         
     * <br>
     * メッセージリソースがnull、ロケールが日本の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDefineJavaScript01() throws Exception {
        // 前処理
        // Mockオブジェクトの生成
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(calendar);
        MockHttpServletRequest request
            = (MockHttpServletRequest) pageContext.getRequest();
        request.addLocale(Locale.JAPAN);

        //リソースバンドル生成
        ResourceBundle calendarBundle = null;

        // テスト実施
        calendar.defineJavaScript(calendarBundle);

        // 判定
        BufferedReader reader = TagUTUtil.getOutputReader(calendar);
        assertEquals("<script type=\"text/javascript\">",reader.readLine());
        assertEquals("<!--",reader.readLine());
        assertEquals("var localja = true;",reader.readLine());
        assertEquals("var jscalendarMonthName = new Array(\"1月\",\"2月\",\"" +
                "3月\",\"4月\",\"5月\",\"6月\",\"7月\",\"8月\",\"9月\"," +
                "\"10月\",\"11月\",\"12月\");",reader.readLine());
        assertEquals("var jscalendarDayName = new Array(\"日\",\"月\"," +
                "\"火\",\"水\",\"木\",\"金\",\"土\");",reader.readLine());
        assertEquals("var jscalendarTodayString = \"\";",reader.readLine());
        assertEquals("var jscalendarThemePrefix = \"BlueStyle\";"
                ,reader.readLine());
        assertEquals("var jscalendarImgDir = \"img/calendar/BlueStyle/\";"
                ,reader.readLine());
        assertEquals("-->",reader.readLine());
        assertEquals("</script>",reader.readLine());
        assertEquals("<script type=\"text/javascript\" " +
                "src=\"js/InputCalendar.js\" ></script>",reader.readLine());
        assertEquals("<link rel=\"stylesheet\" " +
                "href=\"css/BlueStyleInputCalendar.css\"  type=\"text/css\"/>"
                ,reader.readLine());
        assertEquals("<script type=\"text/javascript\">",reader.readLine());
        assertEquals("<!--",reader.readLine());
        assertEquals("",reader.readLine());
        assertEquals("jscalendarInit();",reader.readLine());
        assertEquals("-->",reader.readLine());
        assertEquals("</script>",reader.readLine());
        assertNull(reader.readLine());
    }

    /**
     * testDefineJavaScript02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) calendarBundle:not null[calendar_en_GB.properties]<br>
     *         (状態) pageContext.getRequest().getLocale():Locale.CANADA<br>
     *         (状態) メッセージリソース設定:calendar.holiday.1=0,1,3,abs<br>
     *         
     * <br>
     * 期待値：(状態変化) writer:<script type="text/javascript"><br>
     *                    <!--<br>
     *                    var localja = false;<br>
     *                    var jscalendarMonthName = new Array("January","February","March","April","May","June","July","August","September","October","November","December");<br>
     *                    var jscalendarDayName = new Array("Sun","Mon","Tue","Wed","Thu","Fri","Sat");<br>
     *                    var jscalendarTodayString = "";<br>
     *                    var jscalendarThemePrefix = "BlueStyle";<br>
     *                    var jscalendarImgDir = "img/calendar/BlueStyle/";<br>
     *                    --><br>
     *                    </script><br>
     *                    <script type="text/javascript" src="js/InputCalendar.js" ></script><br>
     *                    <link rel="stylesheet" href="css/BlueStyleInputCalendar.css"  type="text/css"/><br>
     *                    <script type="text/javascript"><br>
     *                    <!--<br>
     *                    jscalendarAddHoliday('0', '1', '3', 'abs');<br>
     *                    <br>
     *                    jscalendarInit();<br>
     *                    --><br>
     *                    </script><br>
     *         
     * <br>
     * メッセージリソースに休日定義が一件、ロケールがカナダの場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDefineJavaScript02() throws Exception {
        // 前処理
        // Mockオブジェクトの生成
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(calendar);
        MockHttpServletRequest request
            = (MockHttpServletRequest) pageContext.getRequest();
        request.addLocale(Locale.CANADA);

        //リソースバンドル生成
        ResourceBundle calendarBundle
            = ResourceBundle.getBundle("calendar", Locale.UK);

        // テスト実施
        calendar.defineJavaScript(calendarBundle);

        // 判定
        BufferedReader reader = TagUTUtil.getOutputReader(calendar);
        assertEquals("<script type=\"text/javascript\">",reader.readLine());
        assertEquals("<!--",reader.readLine());
        assertEquals("var localja = false;",reader.readLine());
        assertEquals("var jscalendarMonthName = new Array(\"January\"," +
                "\"February\",\"March\",\"April\",\"May\",\"June\"," +
                "\"July\",\"August\",\"September\",\"October\"," +
                "\"November\",\"December\");",reader.readLine());
        assertEquals("var jscalendarDayName = new Array(\"Sun\"," +
                "\"Mon\",\"Tue\",\"Wed\",\"Thu\",\"Fri\",\"Sat\");"
                ,reader.readLine());
        assertEquals("var jscalendarTodayString = \"\";",reader.readLine());
        assertEquals("var jscalendarThemePrefix = \"BlueStyle\";"
                ,reader.readLine());
        assertEquals("var jscalendarImgDir = \"img/calendar/BlueStyle/\";"
                ,reader.readLine());
        assertEquals("-->",reader.readLine());
        assertEquals("</script>",reader.readLine());
        assertEquals("<script type=\"text/javascript\" " +
                "src=\"js/InputCalendar.js\" ></script>",reader.readLine());
        assertEquals("<link rel=\"stylesheet\" " +
                "href=\"css/BlueStyleInputCalendar.css\"  type=\"text/css\"/>"
                ,reader.readLine());
        assertEquals("<script type=\"text/javascript\">",reader.readLine());
        assertEquals("<!--",reader.readLine());
        assertEquals("jscalendarAddHoliday('0', '1', '3', 'abs');"
                ,reader.readLine());
        assertEquals("",reader.readLine());
        assertEquals("jscalendarInit();",reader.readLine());
        assertEquals("-->",reader.readLine());
        assertEquals("</script>",reader.readLine());
        assertNull(reader.readLine());
    }

    /**
     * testGetHolidayList01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) calendarBundle:null<br>
     *         (状態) -<br>
     *         
     * <br>
     * 期待値：(戻り値) List<Map<String, String>>:要素数0のリスト<br>
     *         
     * <br>
     * 引数がnullの場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetHolidayList01() throws Exception {
        // 前処理
        // リソースバンドル生成
        ResourceBundle calendarBundle = null;

        // テスト実施
        List<Map<String, String>> holidays
            = calendar.getHolidayList(calendarBundle);

        // 判定
        assertTrue(holidays.isEmpty());
    }

    /**
     * testGetHolidayList02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) calendarBundle:not null[calendar_getHolidayList02.properties]<br>
     *         (状態) メッセージリソース設定:calendar.holiday.1が存在しない。<br>
     *         
     * <br>
     * 期待値：(戻り値) List<Map<String, String>>:要素数0のリスト<br>
     *         
     * <br>
     * calendar.holiday.1の設定値がnullの場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetHolidayList02() throws Exception {
        // 前処理
        // リソースバンドル生成
        ResourceBundle calendarBundle
            = ResourceBundle.getBundle("calendar_getHolidayList02"
                    , Locale.JAPAN);

        // テスト実施
        List<Map<String, String>> holidays
            = calendar.getHolidayList(calendarBundle);

        // 判定
        assertTrue(holidays.isEmpty());
    }

    /**
     * testGetHolidayList03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) calendarBundle:not null[calendar_getHolidayList03.properties]<br>
     *         (状態) メッセージリソース設定:calendar.holiday.1=<br>
     *         
     * <br>
     * 期待値：(戻り値) List<Map<String, String>>:要素数0のリスト<br>
     *         
     * <br>
     * calendar.holiday.1の設定値が空文字の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetHolidayList03() throws Exception {
        // 前処理
        // リソースバンドル生成
        ResourceBundle calendarBundle
            = ResourceBundle.getBundle("calendar_getHolidayList03"
                    , Locale.JAPAN);

        // テスト実施
        List<Map<String, String>> holidays
            = calendar.getHolidayList(calendarBundle);

        // 判定
        assertTrue(holidays.isEmpty());
    }

    /**
     * testGetHolidayList04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) calendarBundle:not null[calendar_getHolidayList04.properties]<br>
     *         (状態) メッセージリソース設定:calendar.holiday.1=0,1,3<br>
     *         
     * <br>
     * 期待値：(戻り値) List<Map<String, String>>:要素数0のリスト<br>
     *         
     * <br>
     * calendar.holiday.1の設定値がカンマで区切られた３つの文字列の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetHolidayList04() throws Exception {
        // 前処理
        // リソースバンドル生成
        ResourceBundle calendarBundle
            = ResourceBundle.getBundle("calendar_getHolidayList04"
                    , Locale.JAPAN);

        // テスト実施
        List<Map<String, String>> holidays
            = calendar.getHolidayList(calendarBundle);

        // 判定
        assertTrue(holidays.isEmpty());
    }

    /**
     * testGetHolidayList05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) calendarBundle:not null[calendar_getHolidayList05.properties]<br>
     *         (状態) メッセージリソース設定:calendar.holiday.1=0,1,3,4,5<br>
     *         
     * <br>
     * 期待値：(戻り値) List<Map<String, String>>:要素数0のリスト<br>
     *         
     * <br>
     * calendar.holiday.1の設定値がカンマで区切られた５つの文字列の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetHolidayList05() throws Exception {
        // 前処理
        // リソースバンドル生成
        ResourceBundle calendarBundle
            = ResourceBundle.getBundle("calendar_getHolidayList05"
                    , Locale.JAPAN);

        // テスト実施
        List<Map<String, String>> holidays
            = calendar.getHolidayList(calendarBundle);

        // 判定
        assertTrue(holidays.isEmpty());
    }

    /**
     * testGetHolidayList06()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) calendarBundle:not null[calendar_getHolidayList06.properties]<br>
     *         (状態) メッセージリソース設定:calendar.holiday.1=0,1,3,abc<br>
     *         
     * <br>
     * 期待値：(戻り値) List<Map<String, String>>:
     *                  要素数1、内容を以下とするリスト<br>
     *                  [0]=map{ year="0" month="1" day="3" desc="abc"}<br>
     *         
     * <br>
     * calendar.holiday.1の設定値がカンマで区切られた４つの文字列の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetHolidayList06() throws Exception {
        // 前処理
        // リソースバンドル生成
        ResourceBundle calendarBundle
            = ResourceBundle.getBundle("calendar_getHolidayList06"
                    , Locale.JAPAN);

        // テスト実施
        List<Map<String, String>> holidays
            = calendar.getHolidayList(calendarBundle);

        // 判定
        assertEquals(1, holidays.size());
        Map<String, String> map = holidays.get(0);
        assertEquals("0", map.get("year"));
        assertEquals("1", map.get("month"));
        assertEquals("3", map.get("day"));
        assertEquals("abc", map.get("desc"));
    }

    /**
     * testGetHolidayList07()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) calendarBundle:not null[calendar_getHolidayList07.properties]<br>
     *         (状態) メッセージリソース設定:
     *                 calendar.holiday.1= 0 , 1 , 3 , abc<br>
     *         
     * <br>
     * 期待値：(戻り値) List<Map<String, String>>:
     *                  要素数1、内容を以下とするリスト<br>
     *                  [0]=map{ year="0" month="1" day="3" desc="abc"}<br>
     *         
     * <br>
     * calendar.holiday.1の設定値が空白とカンマで区切られた４つの文字列の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetHolidayList07() throws Exception {
        // 前処理
        // リソースバンドル生成
        ResourceBundle calendarBundle
            = ResourceBundle.getBundle("calendar_getHolidayList07"
                    , Locale.JAPAN);

        // テスト実施
        List<Map<String, String>> holidays
            = calendar.getHolidayList(calendarBundle);

        // 判定
        assertEquals(1, holidays.size());
        Map<String, String> map = holidays.get(0);
        assertEquals("0", map.get("year"));
        assertEquals("1", map.get("month"));
        assertEquals("3", map.get("day"));
        assertEquals("abc", map.get("desc"));
    }

    /**
     * testGetHolidayList08()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) calendarBundle:not null[calendar_getHolidayList08.properties]<br>
     *         (状態) メッセージリソース設定:calendar.holiday.1=,0,1,3,abc<br>
     *         
     * <br>
     * 期待値：(戻り値) List<Map<String, String>>:要素数0のリスト<br>
     *         
     * <br>
     * calendar.holiday.1の設定値がカンマで
     * 区切られた４つの文字列の先頭にカンマがある文字列の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetHolidayList08() throws Exception {
        // 前処理
        // リソースバンドル生成
        ResourceBundle calendarBundle
            = ResourceBundle.getBundle("calendar_getHolidayList08"
                    , Locale.JAPAN);

        // テスト実施
        List<Map<String, String>> holidays
            = calendar.getHolidayList(calendarBundle);

        // 判定
        assertTrue(holidays.isEmpty());
    }

    /**
     * testGetHolidayList09()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) calendarBundle:not null[calendar_getHolidayList09.properties]<br>
     *         (状態) メッセージリソース設定:calendar.holiday.1=0,1,3,abc,<br>
     *         
     * <br>
     * 期待値：(戻り値) List<Map<String, String>>:
     *                  要素数1、内容を以下とするリスト<br>
     *                  [0]=map{ year="0" month="1" day="3" desc="abc"}<br>
     *         
     * <br>
     * calendar.holiday.1の設定値がカンマで
     * 区切られた４つの文字列の末尾にカンマがある文字列の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetHolidayList09() throws Exception {
        // 前処理
        // リソースバンドル生成
        ResourceBundle calendarBundle
            = ResourceBundle.getBundle("calendar_getHolidayList09"
                    , Locale.JAPAN);

        // テスト実施
        List<Map<String, String>> holidays
            = calendar.getHolidayList(calendarBundle);

        // 判定
        assertEquals(1, holidays.size());
        Map<String, String> map = holidays.get(0);
        assertEquals("0", map.get("year"));
        assertEquals("1", map.get("month"));
        assertEquals("3", map.get("day"));
        assertEquals("abc", map.get("desc"));
    }

    /**
     * testGetHolidayList10()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) calendarBundle:not null[calendar_getHolidayList10.properties]<br>
     *         (状態) メッセージリソース設定:calendar.holiday.1が存在しない。<br>
     *                calendar.holiday.2=0,1,3,abc<br>
     *         
     * <br>
     * 期待値：(戻り値) List<Map<String, String>>:要素数0のリスト<br>
     *         
     * <br>
     * calendar.holiday.1の設定値がnull、<br>
     * calendar.holiday.2の設定値がカンマで区切られた４つの文字列の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetHolidayList10() throws Exception {
        // 前処理
        // リソースバンドル生成
        ResourceBundle calendarBundle
            = ResourceBundle.getBundle("calendar_getHolidayList10"
                    , Locale.JAPAN);

        // テスト実施
        List<Map<String, String>> holidays
            = calendar.getHolidayList(calendarBundle);

        // 判定
        assertTrue(holidays.isEmpty());
    }

    /**
     * testGetHolidayList11()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) calendarBundle:not null[calendar_getHolidayList11.properties]<br>
     *         (状態) メッセージリソース設定:calendar.holiday.1=<br>
     *                calendar.holiday.2=0,1,3,abc<br>
     *         
     * <br>
     * 期待値：(戻り値) List<Map<String, String>>:
     *                  要素数1、内容を以下とするリスト<br>
     *                  [0]=map{ year="0" month="1" day="3" desc="abc"}<br>
     *         
     * <br>
     * calendar.holiday.1の設定値が空文字、<br>
     * calendar.holiday.2の設定値がカンマで区切られた４つの文字列の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetHolidayList11() throws Exception {
        // 前処理
        // リソースバンドル生成
        ResourceBundle calendarBundle
            = ResourceBundle.getBundle("calendar_getHolidayList11"
                    , Locale.JAPAN);

        // テスト実施
        List<Map<String, String>> holidays
            = calendar.getHolidayList(calendarBundle);

        // 判定
        assertEquals(1, holidays.size());
        Map<String, String> map = holidays.get(0);
        assertEquals("0", map.get("year"));
        assertEquals("1", map.get("month"));
        assertEquals("3", map.get("day"));
        assertEquals("abc", map.get("desc"));
    }

    /**
     * testGetHolidayList12()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) calendarBundle:not null[calendar_getHolidayList12.properties]<br>
     *         (状態) メッセージリソース設定:calendar.holiday.1=0,1,3,abc<br>
     *                calendar.holiday.2が存在しない。<br>
     *                calendar.holiday.3=1000,10,19,def<br>
     *         
     * <br>
     * 期待値：(戻り値) List<Map<String, String>>:
     *                 要素数1、内容を以下とするリスト<br>
     *                  [0]=map{ year="0" month="1" day="3" desc="abc"}<br>
     *         
     * <br>
     * calendar.holiday.1の設定値がカンマで区切られた４つの文字列、
     * calendar.holiday.2の設定値がnull、<br>
     * calendar.holiday.3の設定値がカンマで区切られた４つの文字列の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetHolidayList12() throws Exception {
        // 前処理
        // リソースバンドル生成
        ResourceBundle calendarBundle
            = ResourceBundle.getBundle("calendar_getHolidayList12"
                    , Locale.JAPAN);

        // テスト実施
        List<Map<String, String>> holidays
            = calendar.getHolidayList(calendarBundle);

        // 判定
        assertEquals(1, holidays.size());
        Map<String, String> map = holidays.get(0);
        assertEquals("0", map.get("year"));
        assertEquals("1", map.get("month"));
        assertEquals("3", map.get("day"));
        assertEquals("abc", map.get("desc"));
    }

    /**
     * testGetHolidayList13()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) calendarBundle:not null[calendar_getHolidayList13.properties]<br>
     *         (状態) メッセージリソース設定:calendar.holiday.1=0,1,3,abc<br>
     *                calendar.holiday.2=<br>
     *                calendar.holiday.3=1000,10,19,def<br>
     *         
     * <br>
     * 期待値：(戻り値) List<Map<String, String>>:
     *                  要素数2、内容を以下とするリスト<br>
     *                  [0]=map{ year="0" month="1" day="3" desc="abc"}<br>
     *                  [1]=map{ year="1000" month="10" day="19" desc="def"}<br>
     *         
     * <br>
     * calendar.holiday.1の設定値がカンマで区切られた４つの文字列、
     * calendar.holiday.2の設定値が空文字、<br>
     * calendar.holiday.3の設定値がカンマで区切られた４つの文字列の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetHolidayList13() throws Exception {
        // 前処理
        // リソースバンドル生成
        ResourceBundle calendarBundle
            = ResourceBundle.getBundle("calendar_getHolidayList13"
                    , Locale.JAPAN);

        // テスト実施
        List<Map<String, String>> holidays
            = calendar.getHolidayList(calendarBundle);

        // 判定
        assertEquals(2, holidays.size());
        Map<String, String> map = holidays.get(0);
        assertEquals("0", map.get("year"));
        assertEquals("1", map.get("month"));
        assertEquals("3", map.get("day"));
        assertEquals("abc", map.get("desc"));
        map = holidays.get(1);
        assertEquals("1000", map.get("year"));
        assertEquals("10", map.get("month"));
        assertEquals("19", map.get("day"));
        assertEquals("def", map.get("desc"));
    }

    /**
     * testGetHolidayList14()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) calendarBundle:not null[calendar_getHolidayList14.properties]<br>
     *         (状態) メッセージリソース設定:calendar.holiday.1=0,1,3,abc<br>
     *                calendar.holiday.2=0,4,2,休み<br>
     *                calendar.holiday.3=2000,10,6,休日<br>
     *         
     * <br>
     * 期待値：(戻り値) List<Map<String, String>>:
     *                  要素数3、内容を以下とするリスト<br>
     *                  [0]=map{ year="0" month="1" day="3" desc="abc"}<br>
     *                  [1]=map{ year="0" month="4" day="2" desc="休み"}<br>
     *                  [2]=map{ year="2000" month="10" day="6" desc="休日"}<br>
     *         
     * <br>
     * プロパティに３つの正常な休日設定がされていた場合。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetHolidayList14() throws Exception {
        // 前処理
        // リソースバンドル生成
        ResourceBundle calendarBundle
            = ResourceBundle.getBundle("calendar_getHolidayList14"
                    , Locale.JAPAN);

        // テスト実施
        List<Map<String, String>> holidays
            = calendar.getHolidayList(calendarBundle);

        // 判定
        assertEquals(3, holidays.size());
        Map<String, String> map = holidays.get(0);
        assertEquals("0", map.get("year"));
        assertEquals("1", map.get("month"));
        assertEquals("3", map.get("day"));
        assertEquals("abc", map.get("desc"));
        map = holidays.get(1);
        assertEquals("0", map.get("year"));
        assertEquals("4", map.get("month"));
        assertEquals("2", map.get("day"));
        assertEquals("休み", map.get("desc"));
        map = holidays.get(2);
        assertEquals("2000", map.get("year"));
        assertEquals("10", map.get("month"));
        assertEquals("6", map.get("day"));
        assertEquals("休日", map.get("desc"));
    }

    /**
     * testDefineButton01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) calendarBundle:null<br>
     *         (状態) formatKey:null<br>
     *         (状態) forId:null<br>
     *         (状態) format:null<br>
     *         
     * <br>
     * 期待値：(状態変化) writer:<input type="button"
     *  onclick="jscalendarPopUpCalendar(this,this.form.
     *  elements['null'],'yyyy/MM/dd');" value="Calendar" /><br>
     *         
     * <br>
     * formatKeyがnullの場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDefineButton01() throws Exception {
        // 前処理
        // リソースバンドル生成
        ResourceBundle calendarBundle = null;
        UTUtil.setPrivateField(calendar, "forId", null);
        UTUtil.setPrivateField(calendar, "format", null);
        UTUtil.setPrivateField(calendar, "formatKey", null);

        // テスト実施
        calendar.defineButton(calendarBundle);

        // 判定
        BufferedReader reader = TagUTUtil.getOutputReader(calendar);
        assertEquals("<input type=\"button\" " +
                "onclick=\"jscalendarPopUpCalendar(" +
                "this,this.form.elements['null'],'yyyy/MM/dd');\" " +
                "value=\"Calendar\" />", reader.readLine());
        assertNull(reader.readLine());
    }

    /**
     * testDefineButton02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) calendarBundle:not null[calendar_en_GB.properties]<br>
     *         (状態) formatKey:"formatKey"<br>
     *         (状態) forId:"id"<br>
     *         (状態) format:null<br>
     *         
     * <br>
     * 期待値：(状態変化) writer:<input type="button" 
     * onclick="jscalendarPopUpCalendar(this,this.form.elements['id'],
     * 'yyyy-MM-dd');" value="CalCal" /><br>
     *         
     * <br>
     * formatKeyがNot nullの場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDefineButton02() throws Exception {
        // 前処理
        // リソースバンドル生成
        ResourceBundle calendarBundle
            = ResourceBundle.getBundle("calendar", Locale.UK);
        UTUtil.setPrivateField(calendar, "forId", "id");
        UTUtil.setPrivateField(calendar, "format", null);
        UTUtil.setPrivateField(calendar, "formatKey", "formatKey");

        // テスト実施
        calendar.defineButton(calendarBundle);

        // 判定
        BufferedReader reader = TagUTUtil.getOutputReader(calendar);
        assertEquals("<input type=\"button\" " +
                "onclick=\"jscalendarPopUpCalendar(" +
                "this,this.form.elements['id'],'yyyy-MM-dd');\" " +
                "value=\"CalCal\" />", reader.readLine());
        assertNull(reader.readLine());
    }

    /**
     * testGetParameter01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) calendarBundle:null<br>
     *         (引数) def:"default"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"default"<br>
     *         
     * <br>
     * 引数calendarBundleがnullの場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetParameter01() throws Exception {
        // 前処理
        String key = null;
        String def = "default";
        // リソースバンドル生成
        ResourceBundle calendarBundle = null;

        // テスト実施
        String value = calendar.getParameter(calendarBundle, key, def);

        // 判定
        assertEquals(def, value);
    }

    /**
     * testGetParameter02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) calendarBundle:not null[calendar_en_GB.properties]<br>
     *         (引数) key:null<br>
     *         (引数) def:"default"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"default"<br>
     *         
     * <br>
     * 引数keyがnullの場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetParameter02() throws Exception {
        // 前処理
        String key = null;
        String def = "default";
        // リソースバンドル生成
        ResourceBundle calendarBundle
            = ResourceBundle.getBundle("calendar", Locale.UK);

        // テスト実施
        String value = calendar.getParameter(calendarBundle, key, def);

        // 判定
        assertEquals(def, value);
    }

    /**
     * testGetParameter03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) calendarBundle:not null[calendar_en_GB.properties]<br>
     *         (引数) key:""<br>
     *         (引数) def:"default"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"default"<br>
     *         
     * <br>
     * 引数keyが空白の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetParameter03() throws Exception {
        // 前処理
        String key = "";
        String def = "default";
        // リソースバンドル生成
        ResourceBundle calendarBundle
            = ResourceBundle.getBundle("calendar", Locale.UK);

        // テスト実施
        String value = calendar.getParameter(calendarBundle, key, def);

        // 判定
        assertEquals(def, value);
    }

    /**
     * testGetParameter04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) calendarBundle:not null[calendar_en_GB.properties]<br>
     *                [値の保持なし。]<br>
     *         (引数) key:"key1"<br>
     *         (引数) def:"default"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"default"<br>
     *         
     * <br>
     * 引数keyに対する値が存在しない場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetParameter04() throws Exception {
        // 前処理
        String key = "key1";
        String def = "default";
        // リソースバンドル生成
        ResourceBundle calendarBundle
            = ResourceBundle.getBundle("calendar", Locale.UK);

        // テスト実施
        String value = calendar.getParameter(calendarBundle, key, def);

        // 判定
        assertEquals(def, value);
    }

    /**
     * testGetParameter05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) calendarBundle:not null[calendar_en_GB.properties]<br>
     *                ["key2"="abc"]<br>
     *         (引数) key:"key2"<br>
     *         (引数) def:"default"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"abc"<br>
     *         
     * <br>
     * 引数keyに対する値が存在する場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetParameter05() throws Exception {
        // 前処理
        String key = "key2";
        String def = "default";
        // リソースバンドル生成
        ResourceBundle calendarBundle
            = ResourceBundle.getBundle("calendar", Locale.UK);

        // テスト実施
        String value = calendar.getParameter(calendarBundle, key, def);

        // 判定
        assertEquals("abc", value);
    }

    /**
     * testDefineStringVariable01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:null<br>
     *         (引数) value:null<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"var null = \"null\";"<br>
     *         
     * <br>
     * nameがnull、valueがnullである場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDefineStringVariable01() throws Exception {
        //テスト実施
        String value = calendar.defineStringVariable(null, null);

        //判定
        assertEquals("var null = \"null\";", value);
    }

    /**
     * testDefineStringVariable02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:""<br>
     *         (引数) value:""<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"var  = \"\";"<br>
     *         
     * <br>
     * nameが空文字、valueが空文字である場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDefineStringVariable02() throws Exception {
        //テスト実施
        String value = calendar.defineStringVariable("", "");

        //判定
        assertEquals("var  = \"\";", value);
    }

    /**
     * testDefineStringVariable03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:"variable"<br>
     *         (引数) value:"abc"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"var variable = \"abc\";"<br>
     *         
     * <br>
     * nameが文字列、valueが文字列の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDefineStringVariable03() throws Exception {
        //テスト実施
        String value = calendar.defineStringVariable("variable", "abc");

        //判定
        assertEquals("var variable = \"abc\";", value);
    }

    /**
     * testDefineObjectVariable01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:null<br>
     *         (引数) value:null<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"var null = null;"<br>
     *         
     * <br>
     * nameがnull、valueがnullである場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDefineObjectVariable01() throws Exception {
        //テスト実施
        String value = calendar.defineObjectVariable(null, null);

        //判定
        assertEquals("var null = null;", value);
    }

    /**
     * testDefineObjectVariable02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:""<br>
     *         (引数) value:""<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"var  = ;"<br>
     *         
     * <br>
     * nameが空文字、valueが空文字である場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDefineObjectVariable02() throws Exception {
        //テスト実施
        String value = calendar.defineObjectVariable("", "");

        //判定
        assertEquals("var  = ;", value);
    }

    /**
     * testDefineObjectVariable03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:"variable"<br>
     *         (引数) value:"abc"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"var variable = abc;"<br>
     *         
     * <br>
     * nameが文字列、valueが文字列の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDefineObjectVariable03() throws Exception {
        //テスト実施
        String value = calendar.defineObjectVariable("variable", "abc");

        //判定
        assertEquals("var variable = abc;", value);
    }

    /**
     * testDefineArrayVariable01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) arrayName:null<br>
     *         (引数) array:要素数0の配列<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"var null = new Array();"<br>
     *         
     * <br>
     * nameがnull、arrayが要素数0の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDefineArrayVariable01() throws Exception {
        //テスト実施
        String value = calendar.defineArrayVariable(null, new String[]{});

        //判定
        assertEquals("var null = new Array();", value);
    }

    /**
     * testDefineArrayVariable02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) arrayName:""<br>
     *         (引数) array:要素数1の以下の配列<br>
     *                [0]="abc"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"var  = new Array(\"abc\");"<br>
     *         
     * <br>
     * nameが空文字、arrayが要素数1の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDefineArrayVariable02() throws Exception {
        //テスト実施
        String value = calendar.defineArrayVariable("", new String[]{"abc"});

        //判定
        assertEquals("var  = new Array(\"abc\");", value);
    }

    /**
     * testDefineArrayVariable03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) arrayName:"variable"<br>
     *         (引数) array:要素数3の以下の配列<br>
     *                [0]="abc"<br>
     *                [1]="def"<br>
     *                [2]="ghi"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"var variable = <br>
     *                  new Array(\"abc\",\"def\",\"ghi\");"<br>
     *         
     * <br>
     * nameが文字列、arrayが要素数3の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDefineArrayVariable03() throws Exception {
        //テスト実施
        String value = calendar.defineArrayVariable(
                        "variable", new String[]{"abc","def","ghi"});

        //判定
        assertEquals("var variable = new Array(\"abc\",\"def\",\"ghi\");"
                , value);
    }

    /**
     * testMapWeekDays01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) symbols:DateFormatSymbols(Locale.JAPANESE)<br>
     *         
     * <br>
     * 期待値：(戻り値) String[]:以下の文字列の配列<br>
     *                  String[0] -> 日<br>
     *                  String[1] -> 月<br>
     *                  String[2] -> 火<br>
     *                  String[3] -> 水<br>
     *                  String[4] -> 木<br>
     *                  String[5] -> 金<br>
     *                  String[6] -> 土<br>
     *         
     * <br>
     * ※正常系1件のみ確認
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testMapWeekDays01() throws Exception {
        // 前処理
        DateFormatSymbols symbols = new DateFormatSymbols(Locale.JAPANESE);

        // テスト実施
        String[] value = calendar.mapWeekdays(symbols);

        // 判定
        assertEquals("日", value[0]);
        assertEquals("月", value[1]);
        assertEquals("火", value[2]);
        assertEquals("水", value[3]);
        assertEquals("木", value[4]);
        assertEquals("金", value[5]);
        assertEquals("土", value[6]);
    }

    /**
     * testMapMonths01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) symbols:DateFormatSymbols(Locale.JAPANESE)<br>
     *         
     * <br>
     * 期待値：(戻り値) String[]:以下の文字列の配列<br>
     *                  String[0] -> 1月<br>
     *                  String[1] -> 2月<br>
     *                  String[2] -> 3月<br>
     *                  String[3] -> 4月<br>
     *                  String[4] -> 5月<br>
     *                  String[5] -> 6月<br>
     *                  String[6] -> 7月<br>
     *                  String[7] -> 8月<br>
     *                  String[8] -> 9月<br>
     *                  String[9] -> 10月<br>
     *                  String[10] -> 11月<br>
     *                  String[11] -> 12月<br>
     *         
     * <br>
     * ※正常系1件のみ確認
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testMapMonths01() throws Exception {
        // 前処理
        DateFormatSymbols symbols = new DateFormatSymbols(Locale.JAPANESE);

        // テスト実施
        String[] value = calendar.mapMonths(symbols);

        // 判定
        assertEquals("1月", value[0]);
        assertEquals("2月", value[1]);
        assertEquals("3月", value[2]);
        assertEquals("4月", value[3]);
        assertEquals("5月", value[4]);
        assertEquals("6月", value[5]);
        assertEquals("7月", value[6]);
        assertEquals("8月", value[7]);
        assertEquals("9月", value[8]);
        assertEquals("10月", value[9]);
        assertEquals("11月", value[10]);
        assertEquals("12月", value[11]);
    }

    /**
     * testGetPageContextFlg01()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) pageContext:not null<br>
     *         (引数) key:null<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:IllegalArgumentException<br>
     *         
     * <br>
     * 引数keyがnullの場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPageContextFlg01() throws Exception {
        // 前処理
        String key = null;

        //MockRunnerのMockPageContextを使用すると、
        //getAttaributeメソッドの引数keyがnullでもNullPointerExceptionが
        //発生しないため、SpringのMockPageContextを使用する。
        org.springframework.mock.web.MockPageContext pageContext
            = new org.springframework.mock.web.MockPageContext();
        calendar.setPageContext(pageContext);

        // テスト実施
        try {
            calendar.getPageContextFlg(pageContext, key);
            fail();
        } catch (IllegalArgumentException e) {
            //例外を確認。
        }
    }

    /**
     * testGetPageContextFlg02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) pageContext:not null<br>
     *         (引数) key:""<br>
     *         (状態) pageContext内で保持している値:"key"=Boolean(true)<br>
     *         
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         
     * <br>
     * 引数keyが空白で、pageContextにBoolean(true)で値が存在する場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPageContextFlg02() throws Exception {
        // 前処理
        String key = "";
        PageContext pageContext = TagUTUtil.getPageContext(calendar);
        pageContext.setAttribute("key",new Boolean(true));

        // テスト実施
        boolean value = calendar.getPageContextFlg(pageContext, key);

        // 判定
        assertFalse(value);
    }

    /**
     * testGetPageContextFlg03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) pageContext:not null<br>
     *         (引数) key:"key"<br>
     *         (状態) pageContext内で保持している値:"key"=String("true")<br>
     *         
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         
     * <br>
     * pageContextにString("true")で値が存在する場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPageContextFlg03() throws Exception {
        // 前処理
        String key = "key";
        PageContext pageContext = TagUTUtil.getPageContext(calendar);
        pageContext.setAttribute("key","true");

        // テスト実施
        boolean value = calendar.getPageContextFlg(pageContext, key);

        // 判定
        assertFalse(value);
    }

    /**
     * testGetPageContextFlg04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) pageContext:not null<br>
     *         (引数) key:"key"<br>
     *         (状態) pageContext内で保持している値:"key"=Boolean(true)<br>
     *         
     * <br>
     * 期待値：(戻り値) boolean:true<br>
     *         
     * <br>
     * pageContextにBoolean(true)で値が存在する場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPageContextFlg04() throws Exception {
        // 前処理
        String key = "key";
        PageContext pageContext = TagUTUtil.getPageContext(calendar);
        pageContext.setAttribute("key",new Boolean(true));

        // テスト実施
        boolean value = calendar.getPageContextFlg(pageContext, key);

        // 判定
        assertTrue(value);
    }

    /**
     * testGetPageContextFlg05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) pageContext:not null<br>
     *         (引数) key:"key"<br>
     *         (状態) pageContext内で保持している値:"key"=null<br>
     *         
     * <br>
     * 期待値：(戻り値) boolean:false<br>
     *         
     * <br>
     * pageContextに設定されている値がnullの場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetPageContextFlg05() throws Exception {
        // 前処理
        String key = "key";
        PageContext pageContext = TagUTUtil.getPageContext(calendar);
        pageContext.setAttribute("key",null);

        // テスト実施
        boolean value = calendar.getPageContextFlg(pageContext, key);

        // 判定
        assertFalse(value);
    }

    /**
     * testSetPageContextFlg01()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) pageContext:not null<br>
     *         (引数) key:null<br>
     *         (状態) pageContext内で保持している値:なし<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:IllegalArgumentException<br>
     *         
     * <br>
     * 引数keyがnullの場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetPageContextFlg01() throws Exception {
        // 前処理
        String key = null;

        //MockRunnerのMockPageContextを使用すると、
        //setAttaributeメソッドの引数keyがnullでもNullPointerExceptionが
        //発生しないため、SpringのMockPageContextを使用する。
        org.springframework.mock.web.MockPageContext pageContext
            = new org.springframework.mock.web.MockPageContext();
        calendar.setPageContext(pageContext);

        // テスト実施
        try {
            calendar.setPageContextFlg(pageContext, key);
            fail();
        } catch (IllegalArgumentException e) {
            //例外を確認。
        }
    }

    /**
     * testSetPageContextFlg02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) pageContext:not null<br>
     *         (引数) key:""<br>
     *         (状態) pageContext内で保持している値:なし<br>
     *         
     * <br>
     * 期待値：(状態変化) pageContext内で保持している値:""=Boolean(true)<br>
     *         
     * <br>
     * 引数keyが空白の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetPageContextFlg02() throws Exception {
        // 前処理
        String key = "";
        PageContext pageContext = TagUTUtil.getPageContext(calendar);

        // テスト実施
        calendar.setPageContextFlg(pageContext, key);

        // 判定
        assertTrue(((Boolean)pageContext.getAttribute(key)).booleanValue());
    }

    /**
     * testSetPageContextFlg03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) pageContext:not null<br>
     *         (引数) key:"key"<br>
     *         (状態) pageContext内で保持している値:なし<br>
     *         
     * <br>
     * 期待値：(状態変化) pageContext内で保持している値:"key"=Boolean(true)<br>
     *         
     * <br>
     * 引数keyが"key"の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetPageContextFlg03() throws Exception {
        // 前処理
        String key = "key";
        PageContext pageContext = TagUTUtil.getPageContext(calendar);

        // テスト実施
        calendar.setPageContextFlg(pageContext, key);

        // 判定
        assertTrue(((Boolean)pageContext.getAttribute(key)).booleanValue());
    }

    /**
     * testCreateFormat01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) format:null<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"yyyy/MM/dd"<br>
     *         
     * <br>
     * 引数がnullの場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateFormat01() throws Exception {
        // 前処理
        String format = null;

        // テスト実施
        String value = calendar.createFormat(format);

        // 判定
        assertEquals("yyyy/MM/dd", value);
    }

    /**
     * testCreateFormat02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) format:""<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"yyyy/MM/dd"<br>
     *         
     * <br>
     * 引数が空白の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateFormat02() throws Exception {
        // 前処理
        String format = "";

        // テスト実施
        String value = calendar.createFormat(format);

        // 判定
        assertEquals("yyyy/MM/dd", value);
    }

    /**
     * testCreateFormat03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) format:"yyyy-MM-dd hh:mm:ss"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"yyyy-MM-dd"<br>
     *         
     * <br>
     * 引数が"yyyy-MM-dd hh:mm:ss"の場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateFormat03() throws Exception {
        // 前処理
        String format = "yyyy-MM-dd hh:mm:ss";

        // テスト実施
        String value = calendar.createFormat(format);

        // 判定
        assertEquals("yyyy-MM-dd", value);
    }

    /**
     * testRelease01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) super.parent:new TagSupport()<br>
     *         (状態) forId:"abc"<br>
     *         (状態) format:"abc"<br>
     *         (状態) formatKey:"abc"<br>
     *         
     * <br>
     * 期待値：(状態変化) super.parent:null<br>
     *         (状態変化) forId:null<br>
     *         (状態変化) format:"yyyy/MM/dd"<br>
     *         (状態変化) formatKey:null<br>
     *         
     * <br>
     * 正常系のみ。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testRelease01() throws Exception {
        // 前処理
        UTUtil.setPrivateField(calendar, "parent", new TagSupport());
        UTUtil.setPrivateField(calendar, "forId", "abc");
        UTUtil.setPrivateField(calendar, "format", "abc");
        UTUtil.setPrivateField(calendar, "formatKey", "abc");

        // テスト実施
        calendar.release();

        // 判定
        assertNull(UTUtil.getPrivateField(calendar, "parent"));
        assertNull(UTUtil.getPrivateField(calendar, "forId"));
        assertEquals(InputCalendarTag.CALENDAR_DEFAULT_FORMAT
                , UTUtil.getPrivateField(calendar, "format"));
        assertNull(UTUtil.getPrivateField(calendar, "formatKey"));
    }

}
