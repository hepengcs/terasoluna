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

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import jp.terasoluna.fw.util.PropertyUtil;
import jp.terasoluna.fw.web.struts.form.ActionFormUtil;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.taglib.TagUtils;

/**
 * ページ単位にページを遷移する機能。
 * <p/>
 * Strutsの<code>&lt;logic:iterate&gt;</code>要素によって定義された一覧の
 * ページ遷移のリンクを表示する。
 * リンクは、同一JSPであれば対応する一覧の上下左右のどこへでも表示することが可能である。
 * <p/>
 * ページリンク機能を使用する場合は、
 * アクションフォームに以下のフィールドを用意して、
 * この機能の属性に対して指定する必要がある。
 * <p/>
 * <ul>
 * <li>表示行数を保持するフィールド</li>
 * <li>表示開始インデックスを保持するフィールド</li>
 * <li>一覧情報全件数を保持するフィールド</li>
 * </ul>
 * <br/><br/>
 * <strong>タグがサポートする属性</strong>
 * <p>ページリンク機能では、以下の属性をサポートする。</p>
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
 *    <td><code>id</code></td>
 *    <td>-</td>
 *    <td><code>false</code></td>
 *    <td><code>true</code></td>
 *    <td align="left">
 *      この属性に文字列が指定された場合、
 *      ページリンクの出力先を画面ではなくページコンテキストに保存する。
 *      この属性は保存するキーとなる。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>action</code></td>
 *    <td>-</td>
 *    <td><code>false</code></td>
 *    <td><code>true</code></td>
 *    <td align="left">
 *      一覧表示画面の表示を行うアクションパス名を指定する。<br/>
 *      submit属性がfalseの場合は必須属性となる。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>name</code></td>
 *    <td>-</td>
 *    <td><code>false</code></td>
 *    <td><code>true</code></td>
 *    <td align="left">
 *      表示行数、開始行インデックス、一覧情報全行数を取得するBeanを指定する。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>rowProperty</code></td>
 *    <td>-</td>
 *    <td><code>true</code></td>
 *    <td><code>true</code></td>
 *    <td align="left">
 *      表示行数を保持するフィールドを指定する。
 *      name属性が指定されていない場合は直接値を取得する。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>indexProperty</code></td>
 *    <td>-</td>
 *    <td><code>true</code></td>
 *    <td><code>true</code></td>
 *    <td align="left">
 *      表示開始インデックスを保持するフィールドを指定する。
 *      name属性が指定されていない場合は直接値を取得する。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>totalProperty</code></td>
 *    <td>-</td>
 *    <td><code>true</code></td>
 *    <td><code>true</code></td>
 *    <td align="left">
 *      <code>&lt;logic:iterate&gt;</code>要素によって
 *      定義された一覧情報の全行数を保持するフィールドを指定する。
 *      name属性が指定されていない場合は直接値を取得する。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>scope</code></td>
 *    <td>-</td>
 *    <td><code>false</code></td>
 *    <td><code>true</code></td>
 *    <td align="left">
 *      name属性で指定したBeanを取得するスコープを指定する。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>submit</code></td>
 *    <td><code>false</code></td>
 *    <td><code>false</code></td>
 *    <td><code>true</code></td>
 *    <td align="left">
 *      リンクではなく、サブミットを行う場合はtrueを指定する。デフォルトはfalse。
 *      falseの場合、action属性が必須属性となる。
 *      なお、この属性をtrueに設定するとaction属性は無効となる。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>forward</code></td>
 *    <td><code>false</code></td>
 *    <td><code>false</code></td>
 *    <td><code>true</code></td>
 *    <td align="left">
 *      TERASOLUNAのDispatchActionを使用してフォワードによる振り分けを行う場合に
 *      使用する属性。trueを指定するとevent属性に設定された値のHiddenタグを出力する。
 *      また、そのHiddenタグのvalue属性を"forward_pageLinks"とする。
 *      デフォルトはfalse。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>event</code></td>
 *    <td><code>"event"</code></td>
 *    <td><code>false</code></td>
 *    <td><code>true</code></td>
 *    <td align="left">
 *      TERASOLUNAのDispatchActionを使用してフォワードによる振り分けを行う場合に
 *      使用する属性。forward属性をtrueにした場合、この属性に指定した名前の
 *      Hiddenタグが生成される。デフォルトは"event"となる。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>resetIndex</code></td>
 *    <td><code>false</code></td>
 *    <td><code>false</code></td>
 *    <td><code>true</code></td>
 *    <td align="left">
 *      サブミット時に有効になる属性で、trueに設定すると
 *      指定範囲リセットを行うためのstartIndexとendIndexのHiddenタグを出力する。
 *      デフォルトはfalse。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>currentPageIndex</code></td>
 *    <td><code>"currentPageIndex"</code></td>
 *    <td><code>false</code></td>
 *    <td><code>true</code></td>
 *    <td align="left">
 *      対応する一覧の現在ページ数をページコンテキストに保存する際のキーとなる。
 *      デフォルトは"currentPageIndex"となる。
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>totalPageCount</code></td>
 *    <td><code>"totalPageCount"</code></td>
 *    <td><code>false</code></td>
 *    <td><code>true</code></td>
 *    <td align="left">
 *      対応する一覧の総ページ数をページコンテキストに保存する際のキーとなる。
 *      デフォルトは"totalPageCount"となる。
 *    </td>
 *   </tr>
 *  </table>
 * </div>
 * <br/>
 *
 * <br/>
 *
 * <strong>カスタムタグのスクリプティング変数</strong>
 * <p>
 *  <code>PageLinksTag</code> では以下の変数をサポートする。
 * </p>
 * <div align="center">
 * <table width="90%" border="1">
 *  <tr>
 *   <td><b>変数名</b></td>
 *   <td><b>型</b></td>
 *   <td><b>有効範囲</b></td>
 *   <td><b>記述</b></td>
 *  </tr>
 *  <tr>
 *   <td>
 *     カスタムタグの<code>id</code>属性で指定された名前。
 *   </td>
 *   <td><code>java.lang.String</code></td>
 *   <td>カスタムタグ以降</td>
 *   <td align="left">
 *    画面へ出力するページリンク(<a>タグなど)を保持する。
 *   </td>
 *  </tr>
 *  <tr>
 *   <td>
 *     カスタムタグの<code>currentPageIndex</code>属性で指定された名前、
 *     またはデフォルト値。
 *   </td>
 *   <td><code>java.lang.Integer</code></td>
 *   <td>カスタムタグ以降</td>
 *   <td align="left">
 *    対応する一覧情報の現在のページ番号を保持する。
 *   </td>
 *  </tr>
 *  <tr>
 *   <td>
 *     カスタムタグの<code>totalPageCount</code>属性で指定された名前、
 *     またはデフォルト値。
 *   </td>
 *   <td><code>java.lang.Integer</code></td>
 *   <td>カスタムタグ以降</td>
 *   <td align="left">
 *    対応する一覧情報の総ページ数を保持する。
 *   </td>
 *  </tr>
 * </table>
 * </div>
 *
 * <br/>
 *
 * <strong>使用方法</strong>
 * <li>プロパティファイルの設定</li>
 * <p/>
 * 画面に出力するリンクの設定はプロパティファイルに記述を行う。
 * 記述するのは、現在のページからジャンプページ数と、表示記号の設定である。
 * なお、リンクは以下の書式にそっていれば複数記述することも可能である。
 * <p/>
 * <br/>
 * <li>プロパティファイル設定の書式解説</li>
 * <p>
 * &nbsp;&nbsp;<code><b>pageLinks.<i>&lt;遷移方向&gt;</i><i>&lt;遷移
 * ページ数&gt;</i>.char=<i>&lt;表示文字列&gt;</i></b></code><br/>
 * &nbsp;&nbsp;<i>遷移方向</i>&nbsp：
 * 現在の表示ページ番号よりも前方（<i>next</i>の場合は後方）。<br/>
 * &nbsp;&nbsp;<i>遷移ページ数</i>&nbsp;：
 * 現在の表示ページ番号からのジャンプページ数。数値は自由に設定可能。<br/>
 * &nbsp;&nbsp;<i>表示文字列</i>&nbsp;：
 * 画面に表示させる文字。この文字がリンク対象となる。
 * </p>
 * <br/>
 * <p>
 * &nbsp;&nbsp;<b><code>pageLinks.maxDspLinkSize =
 * <i>&lt;表示ページ数&gt;</i></code></b><br/>
 * &nbsp;&nbsp;<i>表示ページ数</i>&nbsp;：
 * ページ数を直接指定するリンクの最大表示数
 * </p>
 * <p/>
 * <br/>
 * <li>プロパティファイル設定例</li>
 * <code>
 * &nbsp;&nbsp;pageLinks.prev10.char=&amp;lt;&amp;lt;<br/>
 * &nbsp;&nbsp;pageLinks.prev1.char=&amp;lt;<br/>
 * &nbsp;&nbsp;pageLinks.next1.char=&amp;gt;<br/>
 * &nbsp;&nbsp;pageLinks.next10.char=&amp;gt;&amp;gt;<br/>
 * &nbsp;&nbsp;pageLinks.maxDirectLinkCount=10
 * </code>
 * <p/>
 * <br/>
 * <br/>
 * <li>一覧情報をページ単位にデータベースから取得する使用例</li>
 * <p>
 * 以下に、ページリンク機能を使用した例を示す。この例は、ページを切り替えるごとに
 * データベースにアクセスを行い表示する一覧情報のみを取得する例である。
 * この例の場合は、一覧表示画面へ最初に遷移するアクションと、
 * ページリンク機能のアクション(action属性)が同一でも問題ない。
 * 以下の例では、ページリンクに設定するフィールドにStringを使用しているが、
 * String以外のオブジェクトの設定も可能である。
 * </p>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>
 * Struts設定ファイルの例
 * </legend>
 * <p/>
 * <code>
 * &lt;form-beans&gt;<br/>
 * &nbsp;&nbsp;&lt;form-bean name="dynaFormBean"<br/>
 * &nbsp;&nbsp;&nbsp;
 * type="org.apache.struts.action.DynaActionForm" &gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;!-- 取得した一覧情報を保持するフィールド --&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;form-property name="<b>userBeans</b>"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * type="jp.terasoluna.strutspring.blogic.UserBean[]"/&gt;<br/>
 * &nbsp;&nbsp;&nbsp;
 * &lt;!-- 1ページに表示する件数を保持したフィールド --&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;form-property name="<b>row</b>"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * type="java.lang.String" initial="10"/&gt;<br/>
 * &nbsp;&nbsp;&nbsp;
 * &lt;!-- 表示するページの開始インデックスを保持したフィールド --&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;form-property name="<b>startIndex</b>"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * type="java.lang.String" initial="0"/&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;!-- 一覧情報の全件数を保持したフィールド --&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;form-property name="<b>totalCount</b>"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;type="java.lang.String"/&gt;<br/>
 * &nbsp;&nbsp;&lt;/form-bean&gt;<br/>
 * &lt;/form-beans&gt;<br/>
 * <br/>
 * &lt;!-- ページ単位の一覧情報を取得するアクション --&gt;<br/>
 * &lt;action path="<b>/list</b>"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * type="jp.terasoluna.strutspring.action.ListAction"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * name="dynaFormBean" scope="session"&gt;<br/>
 * &nbsp;&nbsp;&lt;forward name="success" path="/listSRC.do"/&gt;<br/>
 * &lt;/action&gt;<br/>
 * &lt;action path="/listSRC"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * type="org.apache.struts.actions.ForwardAction"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;parameter="/daoTestList.jsp"&gt;<br/>
 * &lt;/action&gt;
 * </code>
 * </fieldset>
 * <p/>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>
 * JSPの例
 * </legend>
 * <p/>
 * <code>
 * <b>&lt;ts:pageLinks action="/list"
 * name="dynaFormBean" rowProperty="row"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;totalProperty="totalCount"
 * indexProperty="startIndex" /&gt;</b><br/>
 * &lt;table border="1" frame="box"&gt;<br/>
 * &nbsp;&nbsp;<b>&lt;logic:iterate id="userBean"
 * name="dynaFormBean" property="userBeans"&gt;</b><br/>
 * &nbsp;&nbsp;&lt;tr&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;td&gt;&lt;bean:write
 * name="userBean" property="id"/&gt;&lt;/td&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;td&gt;&lt;bean:write
 * name="userBean" property="name"/&gt;&lt;/td&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;td&gt;&lt;bean:write
 * name="userBean" property="age"/&gt;&lt;/td&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;td&gt;&lt;bean:write
 * name="userBean" property="param1"/&gt;&lt;/td&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;td&gt;&lt;bean:write
 * name="userBean" property="param2"/&gt;&lt;/td&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;td&gt;&lt;bean:write
 * name="userBean" property="param3"/&gt;&lt;/td&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;td&gt;&lt;bean:write
 * name="userBean" property="param4"/&gt;&lt;/td&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;td&gt;&lt;bean:write
 * name="userBean" property="param5"/&gt;&lt;/td&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;td&gt;&lt;bean:write
 * name="userBean" property="param6"/&gt;&lt;/td&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;td&gt;&lt;bean:write
 * name="userBean" property="param7"/&gt;&lt;/td&gt;<br/>
 * &nbsp;&nbsp;&lt;/tr&gt;<br/>
 * &nbsp;&nbsp;<b>&lt;/logic:iterate&gt;</b><br/>
 * &lt;/table&gt;
 * </code>
 * </fieldset>
 * <p/>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>
 * サービス層の例
 * </legend>
 * <p/>
 * <code>
 * DynaActionForm dynaForm = (DynaActionForm) form;<br/>
 * 
 * //表示件数の取得<br/>
 * String strRow = (String) dynaForm.get("<b>row</b>");<br/>
 * //開始行インデックスの取得<br/>
 * String strIndex = (String) dynaForm.get("<b>startIndex</b>");<br/>
 * int row = 10;<br/>
 * int startIndex = 0;<br/>
 * <br/>
 * //intへの変換処理<br/>
 * ......<br/>
 * <br/>
 * //全体件数取得<br/>
 * String totalCount<br/>
 *     = <b>dao.executeForObject("getUserCount", null, String.class);</b><br/>
 * <br/>
 * //一覧情報取得<br/>
 * UserBean[] bean = <b>dao.executeForObjectList("getUserList", null,
 * UserBean.class, startIndex, row);</b><br/>
 * <br/>
 * //アクションフォームへの設定<br/>
 * dynaForm.set("<b>totalCount</b>", totalCount);<br/>
 * dynaForm.set("<b>userBeans</b>", bean);
 * </code>
 * </fieldset>
 * <p/>
 * <br/>
 * <br/>
 * <li>一覧情報をアクションフォームから取得する使用例</li>
 * <p>
 * 以下に、ページリンク機能を使用した例を示す。この例は、ページを切り替えるときは
 * すでに取得済みの一覧情報(全件数)をアクションフォームから取得する例である。
 * この例の場合は、一覧表示画面へ最初に遷移するアクションと、
 * ページリンク機能のアクション(action属性)は別のアクションとする。
 * 一覧表示画面へ最初に遷移するアクションでは、一覧情報の全件数を取得し、
 * ページリンク機能のアクションはあくまで画面を表示するのみのアクションとする。
 * 以下の例では、ページリンクに設定するフィールドにStringを使用しているが、
 * String以外のオブジェクトの設定も可能である。
 * </p>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>
 * Struts設定ファイルの例
 * </legend>
 * <p/>
 * <code>
 * &lt;form-beans&gt;<br/>
 * &nbsp;&nbsp;&lt;form-bean name="dynaFormBean"<br/>
 * &nbsp;&nbsp;&nbsp;
 * type="org.apache.struts.action.DynaActionForm" &gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;!-- 取得した一覧情報を保持するフィールド --&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;form-property name="<b>userBeans</b>"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * type="jp.terasoluna.strutspring.blogic.UserBean[]"/&gt;<br/>
 * &nbsp;&nbsp;&nbsp;
 * &lt;!-- 1ページに表示する件数を保持したフィールド --&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;form-property name="<b>row</b>"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * type="java.lang.String" initial="10"/&gt;<br/>
 * &nbsp;&nbsp;&nbsp;
 * &lt;!-- 表示するページの開始インデックスを保持したフィールド --&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;form-property name="<b>startIndex</b>"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * type="java.lang.String" initial="0"/&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;!-- 一覧情報の全件数を保持したフィールド --&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;form-property name="<b>totalCount</b>"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;type="java.lang.String"/&gt;<br/>
 * &nbsp;&nbsp;&lt;/form-bean&gt;<br/>
 * &lt;/form-beans&gt;<br/>
 * <br/>
 * &lt;!-- 一覧情報すべてを取得するアクション --&gt;<br/>
 * &lt;action path="/list"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * type="jp.terasoluna.strutspring.action.ListAction"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * name="dynaFormBean" scope="session"&gt;<br/>
 * &nbsp;&nbsp;&lt;forward name="success" path="/listSRC.do"/&gt;<br/>
 * &lt;/action&gt;<br/>
 * &lt;!-- 画面表示のみのアクション --&gt;<br/>
 * &lt;action path="<b>/listSRC</b>"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * type="org.apache.struts.actions.ForwardAction"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;parameter="/daoTestList.jsp"&gt;<br/>
 * &lt;/action&gt;
 * </code>
 * </fieldset>
 * <p/>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>
 * JSPの例
 * </legend>
 * <p/>
 * <code>
 * <b>&lt;ts:pageLinks action="/listSRC"
 * name="dynaFormBean" rowProperty="row"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;totalProperty="totalCount"
 * indexProperty="startIndex" /&gt;</b><br/>
 * &lt;table border="1" frame="box"&gt;<br/>
 * &nbsp;&nbsp;<b>&lt;bean:define id="startIndex" name="dynaFormBean"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;property="startIndex"
 * type="java.lang.String" /&gt;<br/>
 * &nbsp;&nbsp;&lt;logic:iterate id="userBean"
 * name="dynaFormBean" length="10"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;property="userBeans"
 * offset="&lt;%=startIndex%&gt;"&gt;</b><br/>
 * &nbsp;&nbsp;&lt;tr&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;td&gt;&lt;bean:write
 * name="userBean" property="id"/&gt;&lt;/td&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;td&gt;&lt;bean:write
 * name="userBean" property="name"/&gt;&lt;/td&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;td&gt;&lt;bean:write
 * name="userBean" property="age"/&gt;&lt;/td&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;td&gt;&lt;bean:write
 * name="userBean" property="param1"/&gt;&lt;/td&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;td&gt;&lt;bean:write
 * name="userBean" property="param2"/&gt;&lt;/td&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;td&gt;&lt;bean:write
 * name="userBean" property="param3"/&gt;&lt;/td&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;td&gt;&lt;bean:write
 * name="userBean" property="param4"/&gt;&lt;/td&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;td&gt;&lt;bean:write
 * name="userBean" property="param5"/&gt;&lt;/td&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;td&gt;&lt;bean:write
 * name="userBean" property="param6"/&gt;&lt;/td&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;td&gt;&lt;bean:write
 * name="userBean" property="param7"/&gt;&lt;/td&gt;<br/>
 * &nbsp;&nbsp;&lt;/tr&gt;<br/>
 * &nbsp;&nbsp;<b>&lt;/logic:iterate&gt;</b><br/>
 * &lt;/table&gt;
 * </code>
 * </fieldset>
 * <p/>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>
 * サービス層の例（最初の一覧画面表示のみ）
 * </legend>
 * <p/>
 * <code>
 * DynaActionForm dynaForm = (DynaActionForm) form;<br/>
 * <br/>
 * //全体件数取得<br/>
 * String totalCount<br/>
 *     = <b>dao.executeForObject("getUserCount", null, String.class);</b><br/>
 * <br/>
 * //一覧情報取得<br/>
 * UserBean[] bean = <b>dao.executeForObjectList("getUserList", null,
 * UserBean.class);</b><br/>
 * <br/>
 * //アクションフォームへの設定<br/>
 * dynaForm.set("<b>totalCount</b>", totalCount);<br/>
 * dynaForm.set("<b>userBeans</b>", bean);
 * </code>
 * </fieldset>
 * <p/>
 * <br/><br/>
 * <li>サブミットを行いたい場合の使用例</li>
 * <p/>
 * ページリンク機能は、デフォルトでは&lt;a&gt;を使用して、指定されたアクションへの
 * リンクを作成するため、サブミットを行わない。
 * サブミットを行う場合は、<code>submit</code>属性を<code>true</code>に設定する。
 * この設定を行うと、ページリンク押下時に、JavaScriptによるサブミット処理を行う。
 * なお、<code>submit</code>属性を<code>true</code>に設定すると
 * <code>action</code>属性は無効となる。
 * <p/>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>
 * JSPの例（一部）
 * </legend>
 * <p/>
 * <code>
 * &lt;ts:pageLinks <b>submit="true"</b>
 * name="dynaFormBean" rowProperty="row"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;totalProperty="totalCount"
 * indexProperty="startIndex" /&gt;<br/>
 * </code>
 * </fieldset>
 * <p/>
 * <br/><br/>
 * <li>DispatchActionを使用してフォワードを行う場合の使用例</li>
 * <p/>
 * TERASOLUNAのDispatchActionを使用してフォワードを行う場合は、
 * forward属性を"true"にする必要がある。forward属性を"true"とした場合、画面に
 * 「&lt;input type="hidden" name="event" value="forward_pageLinks" /&gt;」を
 * 出力する。また、出力するHiddenタグのname属性の値はevent属性に指定された値となる。
 * デフォルトは"event"であるため、指定しない場合は上記のHiddenタグが出力される。
 * <p/>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>
 * JSPの例（一部）
 * </legend>
 * <p/>
 * <code>
 * &lt;ts:pageLinks submit="true" name="dynaFormBean"
 * rowProperty="row"<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;
 * totalProperty="totalCount"
 * indexProperty="startIndex"<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;
 * <b>forward="true" event="forwardParameter" /&gt;</b><br/>
 * </code>
 * </fieldset>
 * <p/>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>
 * 上記のJSPによって出力されたHTMLの例（Hiddenタグ）
 * </legend>
 * <p/>
 * <code>
 * &lt;input type="hidden" name="<b>forwardParameter</b>"
 * value="<b>forward_pageLinks</b>" /&gt;
 * </code>
 * </fieldset>
 * <p/>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>
 * Struts設定ファイルの例
 * </legend>
 * <p/>
 * <code>
 * &lt;action path="/list"<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * name="dynaFormBean" scope="request"&gt;<br>
 * &nbsp;&nbsp;&nbsp;
 * &lt;forward name="<b>pageLinks</b>" path="/pageLinks.do" /&gt;<br>
 * &nbsp;&nbsp;&nbsp;
 * &lt;forward name="regist"&nbsp;path="/regist.do"&nbsp;/&gt;<br>
 * &lt;/action&gt;<br>
 * </code>
 * </fieldset>
 * <p/>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>
 * Bean定義ファイルの例
 * </legend>
 * <p/>
 * <code>
 * &lt;bean name="/list"<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * class="jp.terasoluna.fw.web.struts.actions.DispatchAction"&gt;<br>
 * &nbsp;&nbsp;&nbsp;
 * &lt;property name="event"&gt;
 * &lt;value&gt;forwardParameter&lt;/value&gt;
 * &lt;/property&gt;<br>
 * &lt;/bean&gt;<br>
 * </code>
 * </fieldset>
 * <p/>

 * <br/><br/>
 * <li>現在ページ数、総ページ数の出力</li>
 * <p/>
 * ページリンク機能では、現在ページ数と総ページ数を<code>pageContext</code>に
 * 保存する。保存するキーは、<code>currentPageIndex</code>属性と
 * <code>totalPageCount</code>属性にて指定可能である。
 * 属性のデフォルト値は上記を参照のこと。
 * 現在ページおよび総ページ数を画面に表示する場合は、以下のように使用する。
 * <p/>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>
 * JSPの例（一部）
 * </legend>
 * <p/>
 * <code>
 * &lt;ts:pageLinks action="/pageLink" rowProperty="row"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;totalProperty="totalCount"
 * indexProperty="startIndex" /&gt;<br/>
 * ･･･<br>
 * 現在は［&lt;bean:write name="<b>currentPageIndex</b>"/&gt;］ページです。<br>
 * 全部で［&lt;bean:write name="<b>totalPageCount</b>"/&gt;］ページあります。
 * </code>
 * </fieldset>
 * <br/><br/>
 * <li>id属性の使用例</li>
 * <p/>
 * id属性を指定すると、ページリンクを画面に出力せずに、指定された文字列をキーで
 * <code>pageContext</code>に保存するため、出力場所を自由に変更することができる。
 * 現在ページや総ページ数をページリンクの上部に出力したいときなどに使用する。
 * <p/>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>
 * JSPの例（一部）
 * </legend>
 * <p/>
 * <code>
 * &lt;ts:pageLinks action="/pageLink" <b>id="reservePageLinks"</b>
 * rowProperty="row"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;totalProperty="totalCount"
 * indexProperty="startIndex"<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;currentPageIndex="reserveCurrentPageIndex"
 * totalPageCount="reserveTotalPageCount" /&gt;<br/>
 * ･･･<br>
 * 現在は［&lt;bean:write name="reserveCurrentPageIndex"/&gt;］ページです。<br>
 * 全部で［&lt;bean:write name="reserveTotalPageCount"/&gt;］ページあります。<br>
 * &lt;bean:write name="<b>reservePageLinks</b>"/&gt;
 * </code>
 * </fieldset>
 * <p/>
 * <br/>
 * <br/>
 *
 */
public class PageLinksTag extends TagSupport {

    /**
	 * シリアルバージョンID
	 */
	private static final long serialVersionUID = 9017738370826462823L;

	/**
     * ログクラス。
     */
    private static Log log =
         LogFactory.getLog(PageLinksTag.class);

    /**
     * 出力先変更用ID。
     */
    protected String id = null;

    /**
     * ページリンク押下時に起動するアクション名。
     */
    protected String action = null;

    /**
     * 表示開始インデックスと全件数を保持したBean名。
     */
    protected String name = null;

    /**
     * 表示行数のフィールド名。
     */
    protected String rowProperty = null;

    /**
     * 表示開始インデックスのフィールド名。
     */
    protected String indexProperty = null;

    /**
     * 全件数のフィールド名。
     */
    protected String totalProperty = null;

    /**
     * 取得するBeanのスコープ。
     */
    protected String scope = null;

    /**
     * サブミットフラグ。
     */
    protected boolean submit = false;

    /**
     * フォワードフラグ。
     */
    protected boolean forward = false;

    /**
     * イベントパラメータ。
     */
    protected String event = DEFAULT_EVENT;

    /**
     * 指定範囲インデックス出力フラグ。
     */
    protected boolean resetIndex = false;

    /**
     * 現在ページ番号保存用パラメータ。
     */
    protected String currentPageIndex = CURRENT_PAGE_INDEX;

    /**
     * 現在ページ番号保存用パラメータ。
     */
    protected String totalPageCount = TOTAL_PAGE_COUNT;

    /**
     * 設定されているid属性値を返却する。
     * @return 設定されている値
     */
    @Override
    public String getId() {
        return this.id;
    }

    /**
     * id属性に値を設定する。
     * @param id 設定する値
     */
    @Override
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 設定されているaction属性値を返却する。
     * @return 設定されている値
     */
    public String getAction() {
        return this.action;
    }

    /**
     * action属性に値を設定する。
     * @param action 設定する値
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * 設定されているid属性値を返却する。
     * @return 設定されている値
     */
    public String getName() {
        return name;
    }

    /**
     * name属性に値を設定する。
     * @param name 設定する値
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 設定されているrowProperty属性値を返却する。
     * @return 設定されている値
     */
    public String getRowProperty() {
        return rowProperty;
    }

    /**
     * rowProperty属性に値を設定する。
     * @param rowProperty 設定する値
     */
    public void setRowProperty(String rowProperty) {
        this.rowProperty = rowProperty;
    }

    /**
     * 設定されているindexProperty属性値を返却する。
     * @return 設定されている値
     */
    public String getIndexProperty() {
        return indexProperty;
    }

    /**
     * indexProperty属性に値を設定する。
     * @param indexProperty 設定する値
     */
    public void setIndexProperty(String indexProperty) {
        this.indexProperty = indexProperty;
    }

    /**
     * 設定されているtotalProperty属性値を返却する。
     * @return 設定されている値
     */
    public String getTotalProperty() {
        return totalProperty;
    }

    /**
     * totalProperty属性に値を設定する。
     * @param totalProperty 設定する値
     */
    public void setTotalProperty(String totalProperty) {
        this.totalProperty = totalProperty;
    }

    /**
     * 設定されているscope属性値を返却する。
     * @return 設定されている値
     */
    public String getScope() {
        return scope;
    }

    /**
     * scope属性に値を設定する。
     * @param scope 設定する値
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * 設定されているsubmit属性値を返却する。
     * @return 設定されている値
     */
    public boolean getSubmit() {
        return submit;
    }

    /**
     * submit属性に値を設定する。
     * @param submit 設定する値
     */
    public void setSubmit(boolean submit) {
        this.submit = submit;
    }

    /**
     * 設定されているforward属性値を返却する。
     * @return 設定されている値
     */
    public boolean getForward() {
        return forward;
    }

    /**
     * forward属性に値を設定する。
     * @param forward 設定する値
     */
    public void setForward(boolean forward) {
        this.forward = forward;
    }

    /**
     * 設定されているevent属性値を返却する。
     * @return 設定されている値
     */
    public String getEvent() {
        return this.event;
    }

    /**
     * event属性に値を設定する。
     * @param event 設定する値
     */
    public void setEvent(String event) {
        this.event = event;
    }

    /**
     * 設定されているresetIndex属性値を返却する。
     * @return 設定されている値
     */
    public boolean getResetIndex() {
        return resetIndex;
    }

    /**
     * resetIndex属性に値を設定する。
     * @param resetIndex 設定する値
     */
    public void setResetIndex(boolean resetIndex) {
        this.resetIndex = resetIndex;
    }

    /**
     * 設定されているcurrentPageIndex属性値を返却する。
     * @return 設定されている値
     */
    public String getCurrentPageIndex() {
        return this.currentPageIndex;
    }

    /**
     * currentPageIndex属性に値を設定する。
     * @param currentPageIndex 設定する値
     */
    public void setCurrentPageIndex(String currentPageIndex) {
        this.currentPageIndex = currentPageIndex;
    }

    /**
     * 設定されているtotalPageCount属性値を返却する。
     * @return 設定されている値
     */
    public String getTotalPageCount() {
        return this.totalPageCount;
    }

    /**
     * totalPageCount属性に値を設定する。
     * @param totalPageCount 設定する値
     */
    public void setTotalPageCount(String totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    /**
     * アクション属性が必須のエラーメッセージ。
     */
    protected static String ERROR_MESSAGE_ACTION_REQUIRED =
    "Action attribute is required when submit attribute is \"false\".";

    /**
     * 取得した表示行数(row)が0以下の場合のエラーメッセージ。
     */
    protected static String WARN_MESSAGE_ILLEGAL_ROW =
    "Row param is illegal.";

    /**
     * <code>ApplicationResources</code> ファイルにおける
     * ページジャンプ用リンクのプロパティ名のプリフィックス。
     */
    protected static String PAGE_LINKS_PREFIX = "pageLinks.";

    /**
     * <code>ApplicationResources</code> ファイルにおける前方への
     * ページジャンプ用リンクのプロパティ名の構成要素。
     */
    protected static String PREV_LINKS = "prev";

    /**
     * <code>ApplicationResources</code> ファイルにおける後方への
     * ページジャンプ用リンクのプロパティ名の構成要素。
     */
    protected static String NEXT_LINKS = "next";

    /**
     * <code>ApplicationResources</code> ファイルにおける記号表示の
     * ページジャンプ用リンクのプロパティ名の構成要素。
     */
    protected static String CHAR_LINKS = ".char";

    /**
     * <code>ApplicationResources</code> ファイルにおける
     * ページジャンプ用リンクの直接番号指定の表示最大数の
     * プロパティ名の構成要素。
     */
    protected static String MAX_DSP_SIZE = "maxDirectLinkCount";

    /**
     * ページリンク機能で出力するJavaScriptの出力フラグ
     */
    protected static String PAGELINKS_JAVASCRIPT_KEY
        = "pageLinksJavaScriptKey";

    /**
     * フォワード名。
     */
    protected static String FORWARD_NAME = "forward_pageLinks";

    /**
     * デフォルトイベントパラメータ。
     */
    protected static String DEFAULT_EVENT = "event";

    /**
     * 総ページ数をページコンテキストに登録するキー。
     */
    protected static String TOTAL_PAGE_COUNT = "totalPageCount";

    /**
     * 現在ページ数をページコンテキストに登録するキー。
     */
    protected static String CURRENT_PAGE_INDEX = "currentPageIndex";

    /**
     * プロパティファイルのリンク登録IDをキーとして表示リンクを格納するマップ。
     */
    protected Map<String, String> links = new HashMap<String, String>();

    /**
     * 最大ページジャンプ数。
     */
    protected int maxLinkNo = 1;

    /**
     * 最大直接指定リンク番号数。
     */
    protected int maxPageCount = 10;

    /**
     * タグ評価開始時に呼ばれるメソッド。
     *
     * @return 処理制御指示
     * @throws JspException JSP例外
     */
    @Override
    public int doStartTag() throws JspException {

        //属性チェック
        if (!submit && (action == null || "".equals(action))) {
            log.error(ERROR_MESSAGE_ACTION_REQUIRED);
            throw new JspException(ERROR_MESSAGE_ACTION_REQUIRED);
        }

        // プロパティファイルよりページジャンプ用リンクタグ情報取得
        getLinkProperty();

        //表示行数を取得
        Object objRow = lookup(pageContext, name, rowProperty, scope);
        int row = getInt(objRow);

        //取得した表示行数が0以下の場合は処理を終了する。
        if (row <= 0) {
            if (log.isWarnEnabled()) {
                log.warn(WARN_MESSAGE_ILLEGAL_ROW);
            }
            return EVAL_BODY_INCLUDE;
        }

        //開始行インデックスを取得
        Object objIndex = lookup(pageContext, name, indexProperty, scope);
        int startIndex = getInt(objIndex);

        //全件数を取得
        Object objTotal = lookup(pageContext, name, totalProperty, scope);
        int totalCount = getInt(objTotal);

        //StringBuilderの生成
        StringBuilder sb = new StringBuilder();

        //現在ページ数、総ページ数を設定する。
        attributePageCount(
                getPageIndex(row, startIndex), getPageCount(row, totalCount));

        if (submit) {
            //submit属性がtrueのときは、サブミットを行うページリンクを出力する。

            //表示行数、開始インデックスのタグを出力
            defineHtml(row, startIndex, totalCount);

            //前ページリンクの設定
            addPrevSubmit(sb, row, startIndex, totalCount);

            //ページ番号リンクの設定
            addDirectSubmit(sb, row, startIndex, totalCount);

            //次ページリンクの設定
            addNextSubmit(sb, row, startIndex, totalCount);

        } else {
            //submit属性がfalseの場合

            //前ページリンクの設定
            addPrevLink(sb, row, startIndex, totalCount);

            //ページ番号リンクの設定
            addDirectLink(sb, row, startIndex, totalCount);

            //次ページリンクの設定
            addNextLink(sb, row, startIndex, totalCount);
        }

        //StringBuilderにためた内容を出力する。
        if (id == null || "".equals(id)) {
            try {
                JspWriter writer = pageContext.getOut();
                writer.println(sb.toString());
            } catch (IOException e) {
                log.error(e.getMessage());
                throw new JspTagException(e.toString());
            }
        } else {
            pageContext.setAttribute(id, sb.toString());
        }

        return EVAL_BODY_INCLUDE;
    }

    /**
     * HTMLの定義を出力する。
     * 
     * @param row 表示行数
     * @param startIndex 表示開始インデックス
     * @param totalCount 全件数
     * @throws JspException JSP例外
     */
    protected void defineHtml(int row, int startIndex, int totalCount)
        throws JspException {

        JspWriter writer = pageContext.getOut();
        try {

            //同じ名前のHiddenタグを出力しないため、フラグを確かめる。
            if (!getPageContextFlg(pageContext, PAGELINKS_JAVASCRIPT_KEY
                    + rowProperty)) {

                //表示件数のHiddenタグを追加
                writer.println("<input type=\"hidden\" name=\""
                        + rowProperty + "\" value=\"" + row + "\"/>");

                //一回出力したらフラグを立てる。
                setPageContextFlg(pageContext, PAGELINKS_JAVASCRIPT_KEY
                        + rowProperty);
            }

            //同じ名前のHiddenタグを出力しないため、フラグを確かめる。
            if (!getPageContextFlg(pageContext, PAGELINKS_JAVASCRIPT_KEY
                    + indexProperty)) {

                //表示開始インデックスのHiddenタグを追加
                writer.println("<input type=\"hidden\" name=\""
                        + indexProperty + "\" value=\"" + startIndex + "\"/>");

                //一回出力したらフラグを立てる。
                setPageContextFlg(pageContext, PAGELINKS_JAVASCRIPT_KEY
                        + indexProperty);
            }

            //同じ名前のHiddenタグを出力しないため、フラグを確かめる。
            if (!getPageContextFlg(pageContext, PAGELINKS_JAVASCRIPT_KEY
                    + event) && forward) {

                //表示開始インデックスのHiddenタグを追加
                writer.println("<input type=\"hidden\" name=\"" + event
                        + "\" value=\"\"/>");

                //一回出力したらフラグを立てる。
                setPageContextFlg(pageContext, PAGELINKS_JAVASCRIPT_KEY
                        + event);
            }

            //同じ名前のHiddenタグを出力しないため、フラグを確かめる。
            if (!getPageContextFlg(pageContext, PAGELINKS_JAVASCRIPT_KEY
                    + "resetIndex") && resetIndex) {

                //startIndexのHiddenタグを追加
                if (!"startIndex".equals(indexProperty)) {
                    writer.println("<input type=\"hidden\" name=\"" +
                            "startIndex\" value=\"" + startIndex + "\"/>");
                }

                //endIndexのHiddenタグを追加
                int endIndex = startIndex + row - 1;
                if (endIndex > totalCount) {
                    writer.println("<input type=\"hidden\" name=\"" +
                            "endIndex\" value=\"" + (totalCount - 1) + "\"/>");
                } else {
                    writer.println("<input type=\"hidden\" name=\"" +
                            "endIndex\" value=\"" + endIndex + "\"/>");
                }

                //一回出力したらフラグを立てる。
                setPageContextFlg(pageContext, PAGELINKS_JAVASCRIPT_KEY
                        + "resetIndex");
            }

            //フォーム名を取得
            String formName = ActionFormUtil
                    .getActionFormName((HttpServletRequest) pageContext
                            .getRequest());

            //サブミットを行うJavaScriptを追加する。
            //ただし、ページリンクタグが複数記述されている場合は１回のみ
            if (!getPageContextFlg(pageContext, PAGELINKS_JAVASCRIPT_KEY)) {
                writer.println("<script type=\"text/javascript\">");
                writer.println("<!--");
                writer.println("  function pageLinkSubmit(rowObj, indexObj,"
                        + " row, startIndex){");
                writer.println("    rowObj.value = row;");
                writer.println("    indexObj.value = startIndex;");

                //forward属性がtrueの場合は、event属性のHiddenタグに
                //パラメータを設定する。
                if (forward) {
                    writer.print("    document.");
                    writer.print(formName);
                    writer.print(".");
                    writer.print(event);
                    writer.print(".value = \"");
                    writer.print(FORWARD_NAME);
                    writer.println("\";");
                }

                writer.print("    document.");
                writer.print(formName);
                writer.println(".submit();");
                writer.println("  }");
                writer.println("// -->");
                writer.println("</script>");

                //一回出力したらフラグを立てる。
                setPageContextFlg(pageContext, PAGELINKS_JAVASCRIPT_KEY);
            }

        } catch (IOException e) {
            log.error(e.getMessage());
            throw new JspTagException(e.toString());
        }
    }

    /**
     * 前ページに遷移するリンクを引数のStringBuilderに追加する。
     * 
     * @param sb 追加対象のStringBuilder
     * @param row 表示行数
     * @param startIndex 表示開始インデックス
     * @param totalCount 一覧情報の全件数
     */
    protected void addPrevSubmit(StringBuilder sb, int row, int startIndex,
            int totalCount) {

        //フォーム名を取得
        String formName = ActionFormUtil
                .getActionFormName((HttpServletRequest) pageContext
                        .getRequest());

        //前ページリンクの生成
        for (int i = maxLinkNo; i > 0; i--) {
            String linkKey = PREV_LINKS + i + CHAR_LINKS;
            String linkValue = links.get(linkKey);
            if (linkValue == null
                    || "".equals(linkValue)) {
                continue;
            }
            int index = startIndex - (i * row);
            if (index < 0) {
                sb.append(linkValue + "&nbsp;");
            } else {
                sb.append("<a href=\"#\" onclick=\"pageLinkSubmit(");
                sb.append("document.");
                sb.append(formName);
                sb.append(".");
                sb.append(rowProperty);
                sb.append(",");
                sb.append("document.");
                sb.append(formName);
                sb.append(".");
                sb.append(indexProperty);
                sb.append(",");
                sb.append(row);
                sb.append(",");
                sb.append(index);
                sb.append(")\">");
                sb.append(linkValue);
                sb.append("</a>&nbsp;");
            }
        }
    }

    /**
     * ページ番号リンクを引数のStringBuilderに追加する。
     * 
     * @param sb 追加対象のStringBuilder
     * @param row 表示行数
     * @param startIndex 表示開始インデックス
     * @param totalCount 一覧情報の全件数
     */
    protected void addDirectSubmit(StringBuilder sb, int row, int startIndex,
            int totalCount) {

        //フォーム名を取得
        String formName = ActionFormUtil
                .getActionFormName((HttpServletRequest) pageContext
                        .getRequest());

        //ページジャンプ用リンクの直接番号指定の表示最大数を取得
        String directLinkNo = links.get(MAX_DSP_SIZE);
        if (directLinkNo != null) {
            try {
                maxPageCount = Integer.parseInt(directLinkNo);
            } catch (NumberFormatException e) {
                // NumberFormatExceptionが発生した場合、
                // そのプロパティは無視されmaxDirectLinkCountには
                // デフォルト値が使用される
            }
        }

        //全ページ数を取得する。
        int pageCount = getPageCount(row, totalCount);

        //現在のページインデックスを取得する。
        int pageIndex = getPageIndex(row, startIndex);

        //表示最終ページおよび表示開始ページ
        int startPage = 0;
        int endPage = 0;

        //全ページ数が、ページ番号リンクの表示数より大きく、
        //かつ、表示するページインデックスが、ページ番号リンクの表示数の半分より
        //大きい場合は、表示開始ページインデックスを、表示するページインデックスに
        //合わせて変動させる。
        //例として、全ページ数：１０ページ、ページ番号リンクの表示数：５、
        //表示するページインデックス：５の場合、startPageが２となり
        //endPageが５となる。その場合、画面に表示するリンクは「3 4 5 6 7」となる。
        if (pageCount > maxPageCount && pageIndex > (maxPageCount / 2)) {

            //表示最終ページを最大ページ数とする。
            endPage = maxPageCount;

            startPage = (pageIndex - (endPage / 2)) - 1;
            if (startPage + endPage > pageCount) {
                startPage = pageCount - endPage;
            }
        } else {
            endPage = pageCount < maxPageCount ? pageCount : maxPageCount;
            startPage = 0;
        }

        //ページ番号リンクの生成ループ
        int size = startPage + endPage;
        for (int i = startPage; i < size; i++) {
            int idx = i + 1;
            if (pageIndex == idx) {
                sb.append("<b>");
                sb.append(idx);
                sb.append("</b>&nbsp;");
            } else {
                sb.append("<a href=\"#\" onclick=\"pageLinkSubmit(");
                sb.append("document.");
                sb.append(formName);
                sb.append(".");
                sb.append(rowProperty);
                sb.append(",");
                sb.append("document.");
                sb.append(formName);
                sb.append(".");
                sb.append(indexProperty);
                sb.append(",");
                sb.append(row);
                sb.append(",");
                sb.append(i * row);
                sb.append(")\">");
                sb.append(idx);
                sb.append("</a>&nbsp;");
            }
        }
    }

    /**
     * 次ページに遷移するリンクを引数のStringBuilderに追加する。
     * 
     * @param sb 追加対象のStringBuilder
     * @param row 表示行数
     * @param startIndex 表示開始インデックス
     * @param totalCount 一覧情報の全件数
     */
    protected void addNextSubmit(StringBuilder sb, int row, int startIndex,
            int totalCount) {

        //フォーム名を取得
        String formName = ActionFormUtil
                .getActionFormName((HttpServletRequest) pageContext
                        .getRequest());

        //次ページリンクの生成
        for (int i = 1; i <= maxLinkNo; i++) {
            String linkKey = NEXT_LINKS + i + CHAR_LINKS;
            String linkValue = links.get(linkKey);

            if (linkValue == null
                    || "".equals(linkValue)) {
                continue;
            }

            int index = startIndex + (i * row);
            if (index > (totalCount - 1)) {
                sb.append(linkValue + "&nbsp;");
            } else {
                sb.append("<a href=\"#\" onclick=\"pageLinkSubmit(");
                sb.append("document.");
                sb.append(formName);
                sb.append(".");
                sb.append(rowProperty);
                sb.append(",");
                sb.append("document.");
                sb.append(formName);
                sb.append(".");
                sb.append(indexProperty);
                sb.append(",");
                sb.append(row);
                sb.append(",");
                sb.append(index);
                sb.append(")\">");
                sb.append(linkValue);
                sb.append("</a>&nbsp;");
            }
        }
    }

    /**
     * 前ページに遷移するリンクを引数のStringBuilderに追加する。
     * 
     * @param sb 追加対象のStringBuilder
     * @param row 表示行数
     * @param startIndex 表示開始インデックス
     * @param totalCount 一覧情報の全件数
     */
    protected void addPrevLink(StringBuilder sb, int row, int startIndex,
            int totalCount) {

        //TagUtilsのインスタンス生成
        TagUtils tagUtils = TagUtils.getInstance();

        // レスポンスパラメータの取得
        HttpServletResponse response
            = (HttpServletResponse) pageContext.getResponse();

        //アクションURLの取得
        String url = null;
        url = response.encodeURL(
                tagUtils.getActionMappingURL(action, pageContext));

        //前ページリンクの生成
        for (int i = maxLinkNo; i > 0; i--) {
            String linkKey = PREV_LINKS + i + CHAR_LINKS;
            String linkValue = links.get(linkKey);

            if (linkValue == null
                    || "".equals(linkValue)) {
                continue;
            }

            int index = startIndex - (i * row);
            if (index < 0) {
                sb.append(linkValue + "&nbsp;");
            } else {
                sb.append("<a href=\"" + url);
                if (url.indexOf("?") < 0) {
                    sb.append("?");
                } else {
                    sb.append("&");
                }
                sb.append(rowProperty);
                sb.append("=");
                sb.append(row);
                sb.append("&");
                sb.append(indexProperty);
                sb.append("=");
                sb.append(index);
                sb.append("\">");
                sb.append(linkValue);
                sb.append("</a>&nbsp;");
            }
        }
    }

    /**
     * ページ番号リンクを引数のStringBuilderに追加する。
     * 
     * @param sb 追加対象のStringBuilder
     * @param row 表示行数
     * @param startIndex 表示開始インデックス
     * @param totalCount 一覧情報の全件数
     */
    protected void addDirectLink(StringBuilder sb, int row, int startIndex,
            int totalCount) {

        //TagUtilsのインスタンス生成
        TagUtils tagUtils = TagUtils.getInstance();

        // レスポンスパラメータの取得
        HttpServletResponse response
            = (HttpServletResponse) pageContext.getResponse();

        //アクションURLの取得
        String url = null;
        url = response.encodeURL(
                tagUtils.getActionMappingURL(action, pageContext));

        //ページジャンプ用リンクの直接番号指定の表示最大数を取得
        String directLinkNo = links.get(MAX_DSP_SIZE);
        if (directLinkNo != null) {
            try {
                maxPageCount = Integer.parseInt(directLinkNo);
            } catch (NumberFormatException e) {
                // NumberFormatExceptionが発生した場合、
                // そのプロパティは無視されmaxDirectLinkCountには
                // デフォルト値が使用される
            }
        }

        //全ページ数を取得する。
        int pageCount = getPageCount(row, totalCount);

        //現在のページインデックスを取得する。
        int pageIndex = getPageIndex(row, startIndex);

        //表示最終ページおよび表示開始ページ
        int startPage = 0;
        int endPage = 0;

        //全ページ数が、ページ番号リンクの表示数より大きく、
        //かつ、表示するページインデックスが、ページ番号リンクの表示数の半分より
        //大きい場合は、表示開始ページインデックスを、表示するページインデックスに
        //合わせて変動させる。
        //例として、全ページ数：１０ページ、ページ番号リンクの表示数：５、
        //表示するページインデックス：５の場合、startPageが２となり
        //endPageが５となる。その場合、画面に表示するリンクは「3 4 5 6 7」となる。
        if (pageCount > maxPageCount && pageIndex > (maxPageCount / 2)) {

            //表示最終ページを最大ページ数とする。
            endPage = maxPageCount;

            startPage = (pageIndex - (endPage / 2)) - 1;
            if (startPage + endPage > pageCount) {
                startPage = pageCount - endPage;
            }
        } else {
            endPage = pageCount < maxPageCount ? pageCount : maxPageCount;
            startPage = 0;
        }

        //ページ番号リンクの生成ループ
        int size = startPage + endPage;
        for (int i = startPage; i < size; i++) {
            int idx = i + 1;
            if (pageIndex == idx) {
                sb.append("<b>");
                sb.append(idx);
                sb.append("</b>&nbsp;");
            } else {
                // ページジャンプ用リンクタグをセット
                sb.append("<a href=\"" + url);
                if (url.indexOf("?") < 0) {
                    sb.append("?");
                } else {
                    sb.append("&");
                }
                sb.append(rowProperty);
                sb.append("=");
                sb.append(row);
                sb.append("&");
                sb.append(indexProperty);
                sb.append("=");
                sb.append(i * row);
                sb.append("\">");
                sb.append(idx);
                sb.append("</a>&nbsp;");
            }
        }
    }

    /**
     * 次ページに遷移するリンクを引数のStringBuilderに追加する。
     * 
     * @param sb 追加対象のStringBuilder
     * @param row 表示行数
     * @param startIndex 表示開始インデックス
     * @param totalCount 一覧情報の全件数
     */
    protected void addNextLink(StringBuilder sb, int row, int startIndex,
            int totalCount) {

        //TagUtilsのインスタンス生成
        TagUtils tagUtils = TagUtils.getInstance();

        // レスポンスパラメータの取得
        HttpServletResponse response
            = (HttpServletResponse) pageContext.getResponse();

        //アクションURLの取得
        String url = null;
        url = response.encodeURL(
                tagUtils.getActionMappingURL(action, pageContext));

        //次ページリンクの生成
        for (int i = 1; i <= maxLinkNo; i++) {
            String linkKey = NEXT_LINKS + i + CHAR_LINKS;
            String linkValue = links.get(linkKey);

            if (linkValue == null
                    || "".equals(linkValue)) {
                continue;
            }

            int index = startIndex + (i * row);
            if (index > (totalCount - 1)) {
                sb.append(linkValue + "&nbsp;");
            } else {

                sb.append("<a href=\"" + url);
                if (url.indexOf("?") < 0) {
                    sb.append("?");
                } else {
                    sb.append("&");
                }
                sb.append(rowProperty);
                sb.append("=");
                sb.append(row);
                sb.append("&");
                sb.append(indexProperty);
                sb.append("=");
                sb.append(index);
                sb.append("\">");
                sb.append(linkValue);
                sb.append("</a>&nbsp;");
            }
        }
    }

    /**
     * プロパティファイルに定義されているページジャンプ用リンク定義を取得する。
     * 取得したクラス変数に格納する。
     */
    private void getLinkProperty() {
        // プロパティファイルに定義されているリンク表示を登録する
        Enumeration enume
            = PropertyUtil.getPropertyNames(PAGE_LINKS_PREFIX);
        while (enume.hasMoreElements()) {
            String propName = (String) enume.nextElement();
            String id = propName.substring(PAGE_LINKS_PREFIX.length());
            String link = PropertyUtil.getProperty(propName);

            // 最大ページジャンプ数を設定
            if ((id != null)
                    && (id.startsWith(PREV_LINKS)
                    || id.startsWith(NEXT_LINKS))) {
                String strLinkNo = id.substring(4, id.lastIndexOf(CHAR_LINKS));
                int intLinkNo = 0;
                try {
                    intLinkNo = Integer.parseInt(strLinkNo);
                } catch (NumberFormatException e) {
                    // 何もしない
                    continue;
                }
                if (intLinkNo > maxLinkNo) {
                    maxLinkNo = intLinkNo;
                }
                links.put(id, link);
            } else if (MAX_DSP_SIZE.equals(id)) {
                links.put(id, link);
            }
        }
    }

    /**
     * 表示ページ番号を算出して返却する。
     * 
     * @param row 表示行数
     * @param startIndex 現在表示されているページの表示開始インデックス
     * @return 算出した表示ページ番号
     */
    protected int getPageIndex(int row, int startIndex) {

        //表示ページ番号の算出
        int pageIndex = 0;
        if (row > 0) {
            pageIndex = startIndex / row + 1;
        } else {
            pageIndex = 0;
        }
        if (row > 0 && startIndex % row > 0) {
            pageIndex++;
        }

        return pageIndex;

    }

    /**
     * ページ数を算出して返却する。
     * 
     * @param row 表示行数
     * @param totalCount 全件数
     * @return 算出したページ数
     */
    protected int getPageCount(int row, int totalCount) {

        //表示ページ番号の算出
        int pageCount = 0;
        if (row > 0) {
            pageCount = totalCount / row;
            if (totalCount % row > 0) {
                pageCount++;
            }
        } else {
            pageCount = 1;
        }

        return pageCount;

    }

    /**
     * 指定されたKEYにて取得した値を真偽値に変換して返却する。
     * なお、引数keyがnullの場合は、IllegalArgumentExceptionが発生する。
     * 
     * @param pageContext ページコンテキスト
     * @param key FLGを取得するKEY
     * @return 指定されたKEYにて取得した出力状態フラグ
     */
    protected boolean getPageContextFlg(
            PageContext pageContext, String key) {
        //ページコンテキストからフラグを取得する。
        Object obj = pageContext.getAttribute(key);
        Boolean bol = new Boolean(false);
        if (obj != null && obj instanceof Boolean) {
           bol = (Boolean) obj;
        }
        return bol.booleanValue();
    }

    /**
     * ページコンテキストに対して、指定されたKEYのフラグを設定する。
     * なお、引数keyがnullの場合は、IllegalArgumentExceptionが発生する。
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
     * nameが指定されてない場合は、propertyの値を直接取得する。
     * 
     * @param pageContext ページコンテキスト
     * @param name プロパティを保持したBean名
     * @param property プロパティ
     * @param scope スコープ
     * @return 取得した値
     * @throws JspException JSP例外
     */
    protected Object lookup(PageContext pageContext, String name, 
            String property, String scope) throws JspException{
        if (property == null || "".equals(property)) {
            return null;
        }
        Object retObj = null;
        if (name != null && !"".equals(name)) {
            retObj = TagUtils.getInstance().lookup(pageContext, name,
                    property, scope);
        } else {
            retObj = TagUtils.getInstance().lookup(pageContext,
                    property, scope);
        }
        return retObj;
    }

    /**
     * 引数のオブジェクトをintに変換して返却する。
     * 
     * @param obj intに変換するオブジェクト 
     * @return 取得した値
     * @throws JspException JSP例外
     */
    protected int getInt(Object obj) throws JspException{
        int retInt = 0;
        String value = ObjectUtils.toString(obj);
        if (!"".equals(value)) {
            try {
                retInt = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                log.error(e.getMessage());
                throw new JspException(e);
            }
        }
        return retInt;
    }

    /**
     * 現在ページ数、総ページ数をページコンテキストに保存する。
     * 
     * @param now 現在ページ数
     * @param total 総ページ数
     */
    protected void attributePageCount(
            int now, int total) {

        if (total <= 0) {
            now = 0;
        }

        //現在ページ数をページコンテキストに保存する。
        if (currentPageIndex != null && !"".equals(currentPageIndex)) {
            pageContext.setAttribute(currentPageIndex, now);
        } else {
            pageContext.setAttribute(CURRENT_PAGE_INDEX , now);
        }

        //総ページ数をページコンテキストに保存する。
        if (totalPageCount != null && !"".equals(totalPageCount)) {
            pageContext.setAttribute(totalPageCount, total);
        } else {
            pageContext.setAttribute(TOTAL_PAGE_COUNT, total);
        }
    }

    /**
     * すべてのアロケートされた資源を解放する。
     */
    @Override
    public void release() {
        super.release();
        id = null;
        action = null;
        name = null;
        rowProperty = null;
        indexProperty = null;
        totalProperty = null;
        scope = null;
        submit = false;
        forward = false;
        event = DEFAULT_EVENT;
        resetIndex = false;
        currentPageIndex = CURRENT_PAGE_INDEX;
        totalPageCount = TOTAL_PAGE_COUNT;
    }

}
