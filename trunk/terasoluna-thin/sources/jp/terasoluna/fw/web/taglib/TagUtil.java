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

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * カスタムタグ機能全般に通じるユーティリティクラス。
 * 
 */
public class TagUtil {

    /**
     * スコープマップ。
     */
    private static final Map<String, Integer> SCOPES = 
        new HashMap<String, Integer>();

    /**
     * スコープマップの生成を行う。
     */
    static {
        SCOPES.put("application", PageContext.APPLICATION_SCOPE);
        SCOPES.put("session", PageContext.SESSION_SCOPE);
        SCOPES.put("request", PageContext.REQUEST_SCOPE);
        SCOPES.put("page", PageContext.PAGE_SCOPE);
    }

    /**
     * 引数のスコープ名と一致するスコープの定数を返却する。
     * 一致するスコープが存在しない場合は例外を発生させる。
     * 
     * @param scopeName 取得する定数のスコープ名
     * @return 取得したスコープの定数
     * @throws JspException 一致するスコープが存在しない場合
     */
    public static int getScope(String scopeName) throws JspException {

        if (scopeName == null) {
            throw new JspException();
        }

        Integer scope = SCOPES.get(scopeName.toLowerCase());

        if (scope == null) {
            throw new JspException();
        }

        return scope.intValue();
    }

    /**
     * 指定されたBeanを指定されたスコープから取得する。
     * 引数のscopeNameがNullの場合はPageScopeから取得する。
     * 
     * @param pageContext ページコンテキスト
     * @param name 取得するBean名
     * @param scopeName Beanを取得するスコープ名
     * @return 取得したBean
     * @throws JspException 一致するスコープが存在しない場合
     */
    public static Object lookup(PageContext pageContext, String name,
            String scopeName)
            throws JspException {

        if (scopeName == null) {
            return pageContext.findAttribute(name);
        }

        return pageContext.getAttribute(name, getScope(scopeName));

    }

    /**
     * 指定されたBeanを取得し、そのBeanから引数で
     * 指定されたプロパティ値を取得する。
     * 引数のプロパティがNullの場合は取得したBeanを返却する。
     * Beanが取得できなかった場合は、例外を発生させる。
     *  
     * @param pageContext ページコンテキスト
     * @param name 取得するBean名
     * @param property Beanのプロパティ名
     * @param scopeName Beanを取得するスコープ名
     * @return 取得したBean
     * @throws JspException 一致するスコープが存在しない場合,
     * 指定されたBeanが存在しない場合
     */
    public static Object lookup(PageContext pageContext, String name,
            String property, String scopeName) throws JspException {

        //指定されたBeanを取得する。
        Object bean = lookup(pageContext, name, scopeName);

        //BeanがNullの場合は例外とする。
        if (bean == null) {
            throw new JspException();
        }

        //プロパティが指定されていない場合は、Beanを返却する。
        if (property == null) {
            return bean;
        }

        //取得したBeanから指定されたプロパティの値を取得する。
        //例外時はJspExceptionでラップして投げる。
        try {
            return PropertyUtils.getProperty(bean, property);

        } catch (IllegalAccessException e) {
            throw new JspException(e);
        } catch (IllegalArgumentException e) {
            throw new JspException(e);
        } catch (InvocationTargetException e) {
            throw new JspException(e);
        } catch (NoSuchMethodException e) {
            throw new JspException(e);
        }

    }

    /**
     * PageContextからJspWriterを取得して、指定されたテキストを出力する。
     * 改行なし版。
     * 
     * @param pageContext ページコンテキスト
     * @param text 出力するテキスト
     * @throws JspException
     * I/Oエラーが発生した場合のIOExceptionをラップした例外
     */
    public static void write(PageContext pageContext, String text)
            throws JspException {
        JspWriter writer = pageContext.getOut();
        try {
            writer.print(text);
        } catch (IOException e) {
            throw new JspException(e);
        }
    }

    /**
     * PageContextからJspWriterを取得して、指定されたテキストを出力する。
     * 改行あり版。
     * 
     * @param pageContext ページコンテキスト
     * @param text 出力するテキスト
     * @throws JspException
     * I/Oエラーが発生した場合のIOExceptionをラップした例外
     */
    public static void writeln(PageContext pageContext, String text)
            throws JspException {
        JspWriter writer = pageContext.getOut();
        try {
            writer.println(text);
        } catch (IOException e) {
            throw new JspException(e);
        }
    }

    /**
     * HTML出力時にサニタイズを行う。
     * 「<」「>」「&」「"」「'」を置換する。
     * 
     * @param value サニタイズ対象文字列
     * @return 編集済み文字列
     */
    public static String filter(String value) {

        if (value == null) {
            return null;
        }

        char[] content = new char[value.length()];
        value.getChars(0, value.length(), content, 0);
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < content.length; i++) {
            switch (content[i]) {
                case '<':
                    result.append("&lt;");
                    break;
                case '>':
                    result.append("&gt;");
                    break;
                case '&':
                    result.append("&amp;");
                    break;
                case '"':
                    result.append("&quot;");
                    break;
                case '\'':
                    result.append("&#39;");
                    break;
                default:
                    result.append(content[i]);
                    break;
            }
        }

        return result.toString();
    }

}
