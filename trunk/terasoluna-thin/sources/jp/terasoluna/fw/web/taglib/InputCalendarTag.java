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

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import jp.terasoluna.fw.util.StringUtil;

/**
 * 指定されたフィールドに対して選択した日付の入力を
 * 行うカレンダー入力機能。
 * 
 * <p>
 * 以下の項目をメッセージリソースファイルに記述することで、
 * カレンダー入力機能の設定を変更することができる。<br>
 * なお、メッセージリソースファイル名は「calendar」固定とする。<br>
 * 例：「calendar.properties」、「calendar_en.properties」<br>
 * </p>
 * <ul>
 *   <li>休日定義</li>
 * </ul>
 * <div style="text-align: center;">
 * <table style="width:90%;border:1pt;" frame="box">
 *   <tr>
 *     <td style="font-weight:bold;width:60px;">機能詳細</td>
 *     <td style="text-align: left;">
 *       メッセージリソースファイルに以下のように記述することで
 *       カレンダーの休日を指定することができる。
 *       メッセージリソースキーは、「calendar.holiday.」部分を固定として、
 *       その後に、「1」から連番を振ることとする。
 *       パラメータは、「年」「月」「日」「休日概要」を「,(カンマ)」で
 *       区切って記述することとする。
 *       毎年同じ日付の休日定義は「年」を「0」と
 *       指定することで毎年と認識する。
 *     </td>
 *   </tr>
 *   <tr>
 *     <td style="font-weight:bold;">設定例</td>
 *     <td style="text-align: left;">
 *       calendar.holiday.1=0,1,1,元旦<br/>
 *       calendar.holiday.2=0,2,11,建国記念日<br/>
 *       calendar.holiday.3=0,4,29,みどりの日<br/>
 *       calendar.holiday.4=2005,1,10,成人の日<br/>
 *       calendar.holiday.5=2005,3,20,春分の日<br/>
 *       calendar.holiday.6=2005,9,19,敬老の日<br/>
 *       calendar.holiday.7=2005,9,23,秋分の日<br/>
 *       calendar.holiday.8=2005,10,10,体育の日<br/>
 *     </td>
 *   </tr>
 * </table>
 * </div>
 * <br/>
 * <ul>
 *   <li>ボタン表示文字列変更</li>
 * </ul>
 * <div style="text-align: center;">
 * <table style="width:90%;border:1pt;" frame="box">
 *   <tr>
 *     <td style="font-weight:bold;width:60px;">機能詳細</td>
 *     <td style="text-align: left;">
 *       メッセージリソースファイルに以下のように記述することで
 *       カレンダーを表示するボタンの文字列を変更することができる。
 *       メッセージリソースキーは、「calendar.button.string」固定とする。
 *       デフォルトは「Calendar」となる。
 *     </td>
 *   </tr>
 *   <tr>
 *     <td style="font-weight:bold;">設定例</td>
 *     <td style="text-align: left;">
 *       calendar.button.string=カレンダー
 *     </td>
 *   </tr>
 * </table>
 * </div>
 * <br/>
 * <ul>
 *   <li>スタイルプレフィックス変更</li>
 * </ul>
 * <div style="text-align: center;">
 * <table style="width:90%;border:1pt;" frame="box">
 *   <tr>
 *     <td style="font-weight:bold;width:60px;">機能詳細</td>
 *     <td style="text-align: left;">
 *       メッセージリソースファイルに以下のように記述することで
 *       カレンダーにて使用するスタイルシートのプレフィックス、
 *       および画像ファイルのプレフィックスを変更することができる。
 *       <br/>
 *       メッセージリソースキーは、「calendar.style.themeprefix」固定とする。
 *       デフォルトは「BlueStyle」となる。
 *     </td>
 *   </tr>
 *   <tr>
 *     <td style="font-weight:bold;">設定例</td>
 *     <td style="text-align: left;">
 *       calendar.style.themeprefix=BlueStyle
 *     </td>
 *   </tr>
 * </table>
 * </div>
 * <br/>
 * <ul>
 *   <li>現在日付表示文字列変更</li>
 * </ul>
 * <div style="text-align: center;">
 * <table style="width:90%;border:1pt;" frame="box">
 *   <tr>
 *     <td style="font-weight:bold;width:60px;">機能詳細</td>
 *     <td style="text-align: left;">
 *       メッセージリソースファイルに以下のように記述することで
 *       カレンダーの下部に表示される現在日付に付与する文字列を
 *       変更することができる。
 *       <br/>
 *       メッセージリソースキーは、「calendar.today.string」固定とする。
 *       デフォルトは「Today is 」となる。
 *     </td>
 *   </tr>
 *   <tr>
 *     <td style="font-weight:bold;">設定例</td>
 *     <td style="text-align: left;">calendar.today.string=Today is </td>
 *   </tr>
 * </table>
 * </div>
 * <br/>
 * <ul>
 *   <li>カレンダー画像保存場所変更</li>
 * </ul>
 * <div style="text-align: center;">
 * <table style="width:90%;border:1pt;" frame="box">
 *   <tr>
 *     <td style="font-weight:bold;width:60px;">機能詳細</td>
 *     <td style="text-align: left;">
 *       メッセージリソースファイルに以下のように記述することで
 *       カレンダー入力機能にて使用する画像の保存場所を変更することができる。
 *       最後は「/」で終わる必要がある。
 *       画像の保存場所は変更可能だが、画像ファイルの名前は変更することができない。
 *       <br/>
 *       メッセージリソースキーは、「calendar.img.dir」固定とする。
 *       デフォルトは「img/calendar/」となる。
 *     </td>
 *   </tr>
 *   <tr>
 *     <td style="font-weight:bold;">設定例</td>
 *     <td style="text-align: left;">calendar.img.dir=image/</td>
 *   </tr>
 * </table>
 * </div>
 * <br/>
 * <ul>
 *   <li>スタイルシート保存場所変更</li>
 * </ul>
 * <div style="text-align: center;">
 * <table style="width:90%;border:1pt;" frame="box">
 *   <tr>
 *     <td style="font-weight:bold;width:60px;">機能詳細</td>
 *     <td style="text-align: left;">
 *       メッセージリソースファイルに以下のように記述することで
 *       カレンダー入力機能にて使用するスタイルシートの保存場所を
 *       変更することができる。最後は「/」で終わる必要がある。
 *       この機能で使用するスタイルシートのファイル名は、
 *       「<プレフィックス> + InputCalendar.css」である。
 *       <br/>
 *       メッセージリソースキーは、「calendar.stylesheet.dir」固定とする。
 *       デフォルトは「css/」となる。
 *     </td>
 *   </tr>
 *   <tr>
 *     <td style="font-weight:bold;">設定例</td>
 *     <td style="text-align: left;">calendar.stylesheet.dir=style/</td>
 *   </tr>
 * </table>
 * </div>
 * <br/>
 * <ul>
 *   <li>外部JavaScriptファイル保存場所変更</li>
 * </ul>
 * <div style="text-align: center;">
 * <table style="width:90%;border:1pt;" frame="box">
 *   <tr>
 *     <td style="font-weight:bold;width:60px;">機能詳細</td>
 *     <td style="text-align: left;">
 *       メッセージリソースファイルに以下のように記述することで
 *       カレンダー入力機能にて使用する外部JavaScriptの保存場所を
 *       変更することができる。最後は「/」で終わる必要がある。
 *       この機能で使用するJavaScriptのファイル名は、
 *       「InputCalendar.js」である。
 *       <br/>
 *       メッセージリソースキーは、「calendar.javascript.dir」固定とする。
 *       デフォルトは「js/」となる。
 *     </td>
 *   </tr>
 *   <tr>
 *     <td style="font-weight:bold;">設定例</td>
 *     <td style="text-align: left;">calendar.javascript.dir=javascript/</td>
 *   </tr>
 * </table>
 * </div>
 * <p/>
 * <strong>カレンダー入力機能の使用例</strong>
 * <p>
 * 以下のように、入力フィールドと対となるように記述する。
 * カレンダー入力機能のfor属性には、対象となる入力フィールドの名前を
 * 指定する。以下の例では、text要素のproperty属性が入力フィールドの
 * 名前となるため、for属性には、text要素のproperty属性と同じ値を
 * 指定する。
 * </p>
 * <code>
 *   &lt;html:text property="value" /&gt;<br>
 *   &lt;t:inputCalendar for="value" format="yyyy-MM-dd" /&gt;
 * </code>
 * <br/>
 * <p/>
 * <strong>タグがサポートする属性</strong>
 * <p>
 * カレンダー入力機能では、以下の属性をサポートする。
 * </p>
 * <div align="center">
 *  <table width="90%" border="1">
 *   <tr>
 *    <td><b>属性名</b></td>
 *    <td><b>デフォルト値</b></td>
 *    <td><b>必須性</b></td>
 *    <td><b>実行時式</b></td>
 *    <td><b>概要</b></td>
 *   </tr>
 *   <tr>
 *    <td><code>for</code></td>
 *    <td><code>-</code></td>
 *    <td><code>true</code></td>
 *    <td><code>true</code></td>
 *    <td style="text-align: left;">
 *      選択した日付を入力する入力フィールドを指定する。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>format</code></td>
 *    <td><code>yyyy/MM/dd</code></td>
 *    <td><code>false</code></td>
 *    <td><code>true</code></td>
 *    <td style="text-align: left;">
 *      カレンダーのフォーマットを指定する。<br/>
 *      指定できる日付形式は「y(年)」「M(月)」「d(日)」、
 *      区切文字としては「/」「-」「.」「半角スペース」
 *      のいずれかである。また、区切り文字は、一文字のみを使用すること。
 *      「yyyy/MM-dd」のように複数の区切り文字を使用することはできない。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>formatKey</code></td>
 *    <td><code>-</code></td>
 *    <td><code>false</code></td>
 *    <td><code>true</code></td>
 *    <td style="text-align: left;">
 *      カレンダーのフォーマットをメッセージリソースから
 *      取得するためのキー値を指定する。
 *    </td>
 *   </tr>
 *  </table>
 * </div>
 * <p/>
 * 
 */
public class InputCalendarTag extends TagSupport {

    /**
	 * シリアルバージョンID
	 */
	private static final long serialVersionUID = -3163804374592471898L;

	/**
     * 対応テキストフィールド指定属性。
     * 選択した日付を入力するフィールド名を指定する。
     */
    private String forId = null;

    /**
     * フォーマット指定属性。
     * フィールドに入力する日付のフォーマットを指定する。
     * 「y」「M」「d」の3文字を使用。
     * 区切り文字は「/」「-」「.」「 」の４つが使用可能である。
     */
    private String format = CALENDAR_DEFAULT_FORMAT;

    /**
     * フォーマットキー指定属性。
     * フィールドに入力する日付のフォーマットの
     * メッセージリソースのキーを指定する。
     */
    private String formatKey = null;

    /**
     * 対応テキストフィールド指定属性の値を返却する。
     * @return 対応テキストフィールド指定属性の値
     */
    public String getFor() {
        return forId;
    }

    /**
     * 対応テキストフィールド指定属性の値を設定する。
     * @param forId 設定する値
     */
    public void setFor(String forId) {
        this.forId = forId;
    }

    /**
     * フォーマット指定属性の値を返却する。
     * @return フォーマット指定属性の値
     */
    public String getFormat() {
        return format;
    }

    /**
     * フォーマット指定属性の値を設定する。
     * @param format 設定する値
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * フォーマットキー指定属性の値を返却する。
     * @return フォーマットキー指定属性の値
     */
    public String getFormatKey() {
        return formatKey;
    }

    /**
     * フォーマットキー指定属性の値を設定する。
     * @param formatKey 設定する値
     */
    public void setFormatKey(String formatKey) {
        this.formatKey = formatKey;
    }

    /**
     * カレンダー入力機能にて使用するカレンダー画像保存場所を
     * メッセージリソースから取得するための取得キー。
     */
    protected static final String
        CALENDAR_IMG_DIR = "calendar.img.dir";

    /**
     * カレンダー入力機能にて使用する現在日付付与文字列を
     * メッセージリソースから取得するための取得キー。
     */
    protected static final String
        CALENDAR_TODAY_STRING = "calendar.today.string";

    /**
     * カレンダー入力機能にて使用するボタン表示文字列を
     * メッセージリソースから取得するための取得キー。
     */
    protected static final String
        CALENDAR_BUTTON_VALUE = "calendar.button.value";

    /**
     * カレンダー入力機能にて使用するスタイルプレフィックスを
     * メッセージリソースから取得するための取得キー。
     */
    protected static final String
        CALENDAR_STYLE_THEMEPREFIX = "calendar.style.themeprefix";

    /**
     * カレンダー入力機能にて使用するスタイルシート保存場所を
     * メッセージリソースから取得するための取得キー。
     */
    protected static final String
        CALENDAR_STYLESHEET_DIR = "calendar.stylesheet.dir";

    /**
     * カレンダー入力機能にて使用するカレンダー外部JavaScript保存場所を
     * メッセージリソースから取得するための取得キー。
     */
    protected static final String
        CALENDAR_JAVASCRIPT_DIR = "calendar.javascript.dir";

    /**
     * カレンダー入力機能にて使用する休日定義情報を
     * メッセージリソースから取得するための取得キー。
     */
    protected static final String
        CALENDAR_HOLIDAY_PREFIX = "calendar.holiday";

    /**
     * デフォルトのフォーマット。
     */
    protected static final String
        CALENDAR_DEFAULT_FORMAT = "yyyy/MM/dd";

    /**
     * デフォルトスタイルプレフィックス名。
     */
    protected static final String
        CALENDAR_DEFAULT_STYLEPREFIX_NAME = "BlueStyle";

    /**
     * デフォルトの画像保存場所パス。
     */
    protected static final String
        CALENDAR_DEFAULT_IMAGE_PATH = "img/calendar/";

    /**
     * デフォルトのカレンダーボタン表示文字列。
     */
    protected static final String
        CALENDAR_DEFAULT_BUTTON_VALUE = "Calendar";

    /**
     * デフォルトのスタイルシート保存場所。
     */
    protected static final String
        CALENDAR_DEFAULT_STYLESHEET_DIR = "css/";

    /**
     * デフォルトのカレンダー外部JavaScript保存場所。
     */
    protected static final String
        CALENDAR_DEFAULT_JAVASCRIPT_DIR = "js/";

    /**
     * カレンダー入力機能して使用するJavaScriptのファイル名。
     */
    protected static final String
        CALENDAR_JAVASCRIPT_FILE_NAME = "InputCalendar.js";

    /**
     * カレンダー入力機能して使用するスタイルシートのファイル名(プレフィックス無し)。
     */
    protected static final String
        CALENDAR_STYLESHEET_FILE_NAME = "InputCalendar.css";

    /**
     * 出力状態フラグキー。
     */
    protected static final String
        INPUTCALENDAR_FLG = "InputCalendarFlg";

    /**
     * 対応しているフォーマット文字群。
     */
    protected static final String
        FORMAT_VALUE = "yMd ./-";

    /**
     * タグ評価開始時に呼ばれるメソッド。
     * 国際化対応のための当機能専用のリソースバンドルを取得する。
     * 当機能を使用するためのJavaScriptおよびボタンの出力を行う。
     * 
     * @return 処理制御指示
     * @exception JspException JSP例外
     */
    @Override
    public int doStartTag() throws JspException {

        //リソースバンドル生成
        ResourceBundle calendarBundle = null;

        //ロケール取得
        Locale locale = pageContext.getRequest().getLocale();

        //カレンダー入力機能専用のリソースバンドルを取得する。
        try {
            calendarBundle
                = ResourceBundle.getBundle("calendar", locale);
        } catch (MissingResourceException e) {
            //メッセージリソースが設定されていない場合は何もしない。
            //必要な値がデフォルトが使用される。
        }

        //カレンダー入力機能の要素が複数記述されていても、
        //JavaScript定義はJSPで一度出力したら、出力しない。
        //出力をしたらpageContextにフラグを立てて判断する。
        if (!getPageContextFlg(pageContext, INPUTCALENDAR_FLG)) {
            //カレンダー入力機能で使用するJavaScript定義の出力
            defineJavaScript(calendarBundle);

            //一度出力をしたのでフラグを立てる。
            setPageContextFlg(pageContext, INPUTCALENDAR_FLG);
        }

        //カレンダー画面を出力するためのボタンを出力する。
        defineButton(calendarBundle);

        return SKIP_BODY;
    }

    /**
     * JavaScriptの定義部分を出力する。
     * 日本語ロケールフラグ、現在日付付加文字列、スタイルプレフィックス、
     * カレンダー画像場所指定、カレンダースタイルシート場所指定、
     * カレンダーJavaScript場所指定の出力を行う。
     * また、スタイルシートの&lt;link&gt;要素、
     * カレンダー入力機能の&lt;script&gt;要素の出力を行う。
     * 
     * @param calendarBundle カレンダー入力機能用リソースバンドル
     * @throws JspException JSP例外
     */
    protected void defineJavaScript(ResourceBundle calendarBundle)
            throws JspException {

        //ロケールの取得
        Locale currentLocale = pageContext.getRequest().getLocale();

        //日本語ロケールフラグ
        boolean jpFlg = false;
        if ((Locale.JAPAN.getLanguage()).equals(currentLocale.getLanguage())) {
            jpFlg = true;
        }

        //DateFormatSymbolsの生成および、ロケールに対応した曜日、月の取得を行う。
        DateFormatSymbols symbols = new DateFormatSymbols(currentLocale);
        String[] weekdays = mapWeekdays(symbols);
        String[] months = mapMonths(symbols);

        //現在日付付加文字列をメッセージリソースから取得。
        String todayString = getParameter(calendarBundle,
                CALENDAR_TODAY_STRING, "");

        //スタイルプレフィックスをメッセージリソースから取得。
        String styleThemePrefix = getParameter(calendarBundle,
                CALENDAR_STYLE_THEMEPREFIX, CALENDAR_DEFAULT_STYLEPREFIX_NAME);

        //カレンダー画像場所指定をメッセージリソースから取得。
        String imageDir = getParameter(calendarBundle, CALENDAR_IMG_DIR,
                CALENDAR_DEFAULT_IMAGE_PATH);
        imageDir = imageDir + styleThemePrefix + "/";

        //カレンダースタイルシート場所指定をメッセージリソースから取得。
        String styleFileName = styleThemePrefix + CALENDAR_STYLESHEET_FILE_NAME;
        String styleSheetDir = getParameter(calendarBundle,
                CALENDAR_STYLESHEET_DIR, CALENDAR_DEFAULT_STYLESHEET_DIR);

        //カレンダーJavaScript場所指定をメッセージリソースから取得。
        String javaScriptFileName = CALENDAR_JAVASCRIPT_FILE_NAME;
        String javaScriptDir = getParameter(calendarBundle,
                CALENDAR_JAVASCRIPT_DIR, CALENDAR_DEFAULT_JAVASCRIPT_DIR);

        //script要素の開始タグを出力
        TagUtil.writeln(
                pageContext, "<script type=\"text/javascript\">");
        TagUtil.writeln(pageContext, "<!--");

        //日本語ロケールフラグの出力
        TagUtil.writeln(pageContext, 
                defineObjectVariable("localja", String.valueOf(jpFlg)));

        //月リストの出力
        TagUtil.writeln(pageContext, 
                defineArrayVariable("jscalendarMonthName", months));

        //曜日リストの出力
        TagUtil.writeln(pageContext, 
                defineArrayVariable("jscalendarDayName", weekdays));

        //現在日付付加文字列の出力
        TagUtil.writeln(pageContext, 
                defineStringVariable("jscalendarTodayString",
                todayString));

        //スタイルプレフィックスの出力
        TagUtil.writeln(pageContext, 
                defineStringVariable("jscalendarThemePrefix",
                styleThemePrefix));

        //カレンダー画像場所指定の出力
        TagUtil.writeln(pageContext, 
                defineStringVariable("jscalendarImgDir", imageDir));

        //script要素の閉じタグを出力
        TagUtil.writeln(pageContext, "-->");
        TagUtil.writeln(pageContext, "</script>");

        //カレンダー入力機能のJavaScript外部ファイルを
        //取り込むscript要素の出力
        TagUtil.write(pageContext, "<script type=\"text/javascript\" src=\"");
        TagUtil.write(pageContext, javaScriptDir);
        TagUtil.write(pageContext, javaScriptFileName);
        TagUtil.writeln(pageContext, "\" ></script>");

        //カレンダー入力機能の外部スタイルシートの
        //取り込みを行うlink要素の出力
        TagUtil.writeln(pageContext, 
                "<link rel=\"stylesheet\" href=\"" + styleSheetDir
                + styleFileName + "\"  type=\"text/css\"/>");

        //script要素の開始タグを出力
        TagUtil.writeln(pageContext,
                "<script type=\"text/javascript\">");
        TagUtil.writeln(pageContext, "<!--");

        //休日定義情報を取得する。
        List<Map<String, String>> holidays = getHolidayList(calendarBundle);

        //取得した休日定義を出力
        for (int i = 0; i < holidays.size() ; i++) {
            Map<String, String> map = holidays.get(i);
            TagUtil.write(pageContext, "jscalendarAddHoliday('");
            TagUtil.write(pageContext, map.get("year"));
            TagUtil.write(pageContext, "', '");
            TagUtil.write(pageContext, map.get("month"));
            TagUtil.write(pageContext, "', '");
            TagUtil.write(pageContext, map.get("day"));
            TagUtil.write(pageContext, "', '");
            TagUtil.write(pageContext, map.get("desc"));
            TagUtil.writeln(pageContext, "');");
        }
        TagUtil.writeln(pageContext, "");

        //JavaScriptの初期化を行うscript要素の出力
        TagUtil.writeln(pageContext, "jscalendarInit();");

        //script要素の閉じタグを出力
        TagUtil.writeln(pageContext, "-->");
        TagUtil.writeln(pageContext, "</script>");

    }

    /**
     * メッセージリソースに定義された休日定義を取得する。
     * 
     * @param calendarBundle カレンダー入力機能用リソースバンドル
     * @return 取得した休日定義リスト
     */
    protected List<Map<String, String>> getHolidayList(
            ResourceBundle calendarBundle) {

        //休日定義リスト生成
        List<Map<String, String>> holidays
            = new ArrayList<Map<String, String>>();

        if (calendarBundle == null) {
            return holidays;
        }

        //取得したキー値から定義されている休日を取得する。
        //休日定義方法は
        //「component.calendar.holiday.1=2004,11,23,勤労感謝の日」
        //のように「年」「月」「日」「概要」を「,」くぎりで記述すること。
        for (int i = 1; ; i++) {

            //定義情報保持MAP
            Map<String, String> map = new HashMap<String, String>();

            //メッセージリソースキーの生成
            String key = CALENDAR_HOLIDAY_PREFIX + "." + i;

            //メッセージリソースキーからパラメータを取得する。
            String param = null;
            try {
                param = calendarBundle.getString(key);
            } catch (MissingResourceException e) {
                //NULLの場合はこれ以上定義がないものとしてループを終了
                break;
            }

            //PARAMが取得できた場合は「,」くぎりに分離する。
            String[] paramSprit = param.split(",");

            //分離後、4つに分かれていなければ以下の作業を行わない。
            if (paramSprit.length != 4) {
                continue;
            }

            //正しく定義されていた場合は、MAPに格納して、定義リストに格納する。
            map.put("year",  StringUtil.trim(paramSprit[0]));
            map.put("month", StringUtil.trim(paramSprit[1]));
            map.put("day",   StringUtil.trim(paramSprit[2]));
            map.put("desc",  StringUtil.trim(paramSprit[3]));
            holidays.add(map);

        }

        //休日定義リストを返却する。
        return holidays;

    }

    /**
     * カレンダー画面を表示するためのボタンを出力する。
     *
     * @param calendarBundle カレンダー入力機能用リソースバンドル
     * @throws JspException JSP例外
     */
    protected void defineButton(ResourceBundle calendarBundle)
            throws JspException {

        //日付フォーマット生成
        String dateFormat = null;

        //タグ属性にて指定された日付フォーマットを取得して、
        //JSファイルにて対応している型か否か判定する。
        //また、日付フォーマットが取得できず、日付フォーマットキーが
        //指定されていた場合はキーからパラメータを取得してから
        //JSファイルに対応しているか判定を行う。
        if (formatKey != null) {
            dateFormat = createFormat(getParameter(calendarBundle, formatKey,
                    CALENDAR_DEFAULT_FORMAT));
        } else {
            dateFormat = createFormat(format);
        }

        //ボタン表示文字列の取得
        String buttonValue = getParameter(calendarBundle,
                CALENDAR_BUTTON_VALUE, 
                CALENDAR_DEFAULT_BUTTON_VALUE);

        TagUtil.writeln(pageContext, 
                "<input type=\"button\" onclick=\""
                + "jscalendarPopUpCalendar(this,this.form.elements['"
                + forId + "'],'" + dateFormat + "');\" value=\""
                + buttonValue + "\" />");
    }

    /**
     * メッセージリソースからキーを元に値を取得して返却する。
     * キーがnullまたは空白の場合、取得した値がnullの場合は
     * デフォルト値を返却する。
     * @param calendarBundle カレンダー入力機能用リソースバンドル
     * @param key メッセージリソースのキー
     * @param def デフォルトとなる値
     * @return メッセージリソースから取得した値、またはデフォルト値
     */
    protected String getParameter(ResourceBundle calendarBundle,
            String key, String def) {

        if (calendarBundle == null || key == null || "".equals(key)) {
            return def;
        }

        String retValue = null;

        //取得したリソースバンドルからキーをもとに値を取得する。
        try {
            retValue = calendarBundle.getString(key);
        } catch (MissingResourceException e) {
            //指定されたキーが存在しないためデフォルトを返却する。
            return def;
        }

        return retValue;
    }

    /**
     * 引数にて渡された値を<code>JavaScript</code>の
     * <code>String</code>宣言として返却する。<br>
     * 例：変数名「<code>kanji</code>」、値「漢字」⇒
     * 「<code>var kanji = "漢字";\n</code>」となる。
     * 
     * @param name <code>JavaScript</code>で宣言する変数名
     * @param value 変数の値
     * @return 編集後文字列
     */
    protected String defineStringVariable(String name, String value) {
        StringBuilder retValue = new StringBuilder("var ");
        retValue.append(name);
        retValue.append(" = \"");
        retValue.append(value);
        retValue.append("\";");
        return retValue.toString();
    }

    /**
     * 引数にて渡された値を<code>JavaScript</code>の
     * <code>Object</code>宣言として、返却する。<br>
     * 例：変数名「<code>obj1</code>」、
     * 値「<code>document.forms[0]</code>」
     * ⇒「<code>var obj1 = document.forms[0];\n</code>」となる。
     * 
     * @param name <code>JavaScript</code>で宣言する変数名
     * @param value 変数の値
     * @return 編集後文字列
     */
    protected String defineObjectVariable(String name, String value) {
        StringBuilder retValue = new StringBuilder("var ");
        retValue.append(name);
        retValue.append(" = ");
        retValue.append(value);
        retValue.append(";");
        return retValue.toString();
    }

    /**
     * 引数にて渡された配列を<code>JavaScript</code>の
     * <code>Array</code>型として編集して返却する。<br>
     * 例：変数名「<code>array</code>」、値「<code>10,20,30</code>」
     * ⇒「<code>var obj1 = new Array("10","20","30");\n</code>」<br>
     * なお、引数arrayがnullの場合は、NullPointerExceptionが発生する。
     * 
     * @param arrayName <code>JavaScript</code>で宣言する変数名
     * @param array <code>Array</code>として作成する値を保持する配列
     * @return 編集後文字列
     */
    protected String defineArrayVariable(String arrayName, String[] array) {
        StringBuilder retValue = new StringBuilder("var ");
        retValue.append(arrayName);
        retValue.append(" = new Array(");
        for (int i = 0; i < array.length; i++) {
            if (i != 0) {
                retValue.append(",");
            }
            retValue.append("\"");
            retValue.append(array[i]);
            retValue.append("\"");
        }
        retValue.append(");");
        return retValue.toString();
    }

    /**
     * 指定されたデートフォーマットシンボルから「曜日」を取得して返却する。
     * 
     * @param symbols デートフォーマットシンボル(ロケール設定済み)
     * @return 取得した「曜日」を格納した配列
     */
    protected String[] mapWeekdays(DateFormatSymbols symbols) {
        String[] weekdays = new String[7];
        String[] localeWeekdays = symbols.getShortWeekdays();
        weekdays[0] = localeWeekdays[Calendar.SUNDAY];
        weekdays[1] = localeWeekdays[Calendar.MONDAY];
        weekdays[2] = localeWeekdays[Calendar.TUESDAY];
        weekdays[3] = localeWeekdays[Calendar.WEDNESDAY];
        weekdays[4] = localeWeekdays[Calendar.THURSDAY];
        weekdays[5] = localeWeekdays[Calendar.FRIDAY];
        weekdays[6] = localeWeekdays[Calendar.SATURDAY];
        return weekdays;
    }

    /**
     * 指定されたデートフォーマットシンボルから「月」を取得して返却する。
     * 
     * @param symbols デートフォーマットシンボル(ロケール設定済み)
     * @return 取得した「月」を格納した配列
     */
    protected String[] mapMonths(DateFormatSymbols symbols) {
        String[] months = new String[12];
        String[] localeMonths = symbols.getMonths();
        months[0]  = localeMonths[Calendar.JANUARY];
        months[1]  = localeMonths[Calendar.FEBRUARY];
        months[2]  = localeMonths[Calendar.MARCH];
        months[3]  = localeMonths[Calendar.APRIL];
        months[4]  = localeMonths[Calendar.MAY];
        months[5]  = localeMonths[Calendar.JUNE];
        months[6]  = localeMonths[Calendar.JULY];
        months[7]  = localeMonths[Calendar.AUGUST];
        months[8]  = localeMonths[Calendar.SEPTEMBER];
        months[9]  = localeMonths[Calendar.OCTOBER];
        months[10] = localeMonths[Calendar.NOVEMBER];
        months[11] = localeMonths[Calendar.DECEMBER];
        return months;
    }

    /**
     * 指定されたKEYにて取得した値を真偽値に変換して返却する。
     * なお、引数keyがnullの場合は、NullPointerExceptionが発生する。
     * 
     * @param pageContext ページコンテキスト
     * @param key FLGを取得するKEY
     * @return 指定されたKEYにて取得した出力状態フラグ
     */
    protected boolean getPageContextFlg(
            PageContext pageContext, String key) {
        //ページコンテキストからフラグを取得する。
        Object obj = pageContext.getAttribute(key);
        Boolean bol = Boolean.valueOf(false);
        if (obj != null && obj instanceof Boolean) {
           bol = (Boolean) obj;
        }
        return bol.booleanValue();
    }

    /**
     * ページコンテキストに対して、指定されたKEYのフラグを設定する。
     * なお、引数keyがnullの場合は、NullPointerExceptionが発生する。
     * 
     * @param pageContext ページコンテキスト
     * @param key FLGを設定するKEY
     */
    protected void setPageContextFlg(
            PageContext pageContext, String key) {
        //ページコンテキストにフラグを立てる。
        pageContext.setAttribute(key, Boolean.valueOf(true));
    }

    /**
     * 引数の文字列に対してデートフォーマットの精査を行う。
     * 文字列が<code>null</code>、または空白の場合は、
     * そのまま返却する。
     * また、文字列がデートフォーマットに対応していない場合は、無視する。
     * 
     * @param format デートフォーマット
     * @return 精査後のフォーマット文字列
     */
    protected String createFormat(String format) {

        //NULLの場合は、デフォルトのデートフォーマットとする。
        if (format == null || "".equals(format)) {
            return CALENDAR_DEFAULT_FORMAT;
        }

        //文字列を一文字つづ対応する文字か否か確認する。
        //対応していない場合は、排除してから返却する。
        StringBuilder retValue = new StringBuilder("");
        for (int i = 0; i < format.length(); i++) {
            char c = format.charAt(i);
            if (FORMAT_VALUE.indexOf(String.valueOf(c)) != -1) {
                retValue.append(c);
            }
        }

        //精査した結果を返却する。
        return StringUtil.trim(retValue.toString());

    }

    /**
     * すべてのアロケートされた資源を解放する。
     */
    @Override
    public void release() {
        super.release();
        forId = null;
        format = CALENDAR_DEFAULT_FORMAT;
        formatKey = null;
    }

}
