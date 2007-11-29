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

package jp.terasoluna.fw.web;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import jp.terasoluna.fw.util.HashUtil;
import jp.terasoluna.fw.util.StringUtil;

/**
 * Requestに関するユーティリティクラス。
 *
 */
public class RequestUtil {

    /**
     * <code>ServletRequest</code> クラスインスタンスより、
     * コンテキストパス以降のURI<code>pathInfo</code> を取得します。
     * なお、ここでいうpathInfoとは通常の拡張パス情報とは異なることに注意すること。
     *
     * @param request
     * <code>pathInfo</code> の算出元となる <code>ServletRequest</code> クラスインスタンス。
     * @return
     * コンテキストを含まない、スラッシュから始まるパス。引数のrequestにnullが渡された場合は
     * null。コンテキスト以降の指定が無かった場合空文字列。
     */
    public static String getPathInfo(ServletRequest request) {
        if (request == null) {
            return null;
        }
        // pathInfoの取得
        return ((HttpServletRequest) request).getRequestURI().replaceFirst(
            ((HttpServletRequest) request).getContextPath(), "");
    }

    /**
     * サーブレットコンテキストを取得します
     * @param request リクエスト情報
     * @return サーブレットコンテキスト。引数のrequestにnullが渡された場合は
     * null。
     */
    public static ServletContext getServletContext(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        //ServletContextを取得
        return request.getSession(true).getServletContext();
    }

    /**
     * 別業務に移ったかどうかパスチェックを行い、判定する。
     *
     * @param request リクエスト
     * @return 別業務に移っていれば<code>true</code>。
     * 引数のrequestにnullが渡された場合はtrue。
     */
    public static boolean isChanged(ServletRequest request) {

        if (request == null) {
            return true;
        }

        //pathInfoの取得
        String pathInfo = getPathInfo(request);

        //pathInfoの取得
        String prevPathInfo = (String) request.getAttribute("PREV_PATH_INFO");
        //パスがnullの場合
        if (prevPathInfo == null
            || pathInfo == null
            || !toCompareStr(prevPathInfo).equals(toCompareStr(pathInfo))) {
            return true;
        }

        return false;
    }

    /**
     * 指定されたパス情報から業務コンテキストパスを取得する。
     *
     * @param str パス情報
     * @return 業務コンテキストパス
     */
    private static String toCompareStr(String str) {
        try {
            int beginIndex = str.indexOf('/') + 1;
            int endIndex = str.indexOf('/', beginIndex);
            return str.substring(beginIndex, endIndex);
        } catch (IndexOutOfBoundsException e) {
            return str;
        }
    }

    /**
     * HTTPセッションIDのハッシュ値を取得する。
     *
     * @param req HTTPリクエスト
     * @return ハッシュ値。引数のrequestにnullが渡された場合は
     * null。
     */
    public static String getSessionHash(HttpServletRequest req) {
        if (req == null) {
            return null;
        }
        byte[] hash = HashUtil.hashSHA1(req.getSession(true).getId());
        return StringUtil.toHexString(hash, "");
    }

    /**
     * リクエストパラメータとリクエスト属性をダンプする。
     *
     * @param req HTTPリクエスト
     * @return ダンプした文字列
     */
    public static String dumpRequest(HttpServletRequest req) {
        return dumpRequestParameters(req) + " , " + dumpRequestAttributes(req);
    }

    /**
     * リクエスト属性をダンプする。
     *
     * @param req HTTPリクエスト
     * @return ダンプした文字列。引数のrequestにnullが渡された場合は
     * null。
     */
    public static String dumpRequestAttributes(HttpServletRequest req) {
        if (req == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(" RequestAttributes {");
        Enumeration enumeration = req.getAttributeNames();
        while (enumeration.hasMoreElements()) {
            String key = (String) enumeration.nextElement();
            Object value = req.getAttribute(key);
            builder.append(key);
            builder.append(" = ");
            builder.append(value);
            if (enumeration.hasMoreElements()) {
                builder.append(" , ");
            }
        }
        builder.append("}");
        return builder.toString();
    }


    /**
     * リクエストパラメータをダンプする。
     *
     * @param req HTTPリクエスト
     * @return ダンプした文字列。引数のrequestにnullが渡された場合は
     * null。
     */
    public static String dumpRequestParameters(HttpServletRequest req) {
        if (req == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(" RequestParameters {");
        Enumeration enumeration = req.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String key = (String) enumeration.nextElement();
            String[] values = req.getParameterValues(key);
            for (int i = 0; i < values.length; i++) {
                    builder.append(key);
                    builder.append("[");
                    builder.append(i);
                    builder.append("] = ");
                    builder.append(values[i]);
                    if (i < values.length - 1) {
                        builder.append(" , ");
                    }
            }
            if (enumeration.hasMoreElements()) {
                builder.append(" , ");
            }
        }
        builder.append("}");
        return builder.toString();
    }

    /**
     * 引数urlに引数keyのパラメータが付加されていた場合は削除して返却する。
     * 例：削除前：test.do?no=2&r=8331352040140757427&no=1
     * 　　削除後：test.do?no=2&no=1
     * 
     * @param url 削除を行うURL
     * @param key 削除対象のキーとなる文字列
     * @return 削除後URL
     */
    public static String deleteUrlParam(String url, String key) {

        //url,keyがNULLまたは空白の場合はurlをそのまま返却する。
        if (url == null || "".equals(url) || key == null || "".equals(key)) {
            return url;
        }

        //？で始まるインデックスを取得。
        int start = url.indexOf("?");

        //返却用StringBuilder。
        StringBuilder returnUrl = new StringBuilder(url);

        //パラメータが存在する場合は、処理を行う。
        if (start >= 0) {

            //パラメータ以降の文字列を取得
            String tmp = url.substring(start + 1);

            //パラメータ以外の文字列を返却用StringBuilderに格納。
            returnUrl = new  StringBuilder(url.substring(0, start));

            //パラメータを配列に変換
            String[] params = tmp.split("&");

            //一つずつパラメータを判定して、
            //指定されたキーのパラメータ以外を返却用StringBuilderに格納する。
            for (int i = 0; i < params.length; i++) {
                String param = params[i];

                //ランダムIDの確認
                if (!param.startsWith(key + "=")) {

                    //最初に付加する場合は？を付加する。
                    //複数パラメータの場合は&を付加する。
                    if (returnUrl.indexOf("?") < 0
                            && !returnUrl.toString().endsWith("?")) {
                        returnUrl.append("?");
                    } else if (!returnUrl.toString().endsWith("&")) {
                        returnUrl.append("&");
                    }

                    //返却用StringBuilderに格納。
                    returnUrl.append(param);
                }
            }
        }

        //削除後のURLを返却する。
        return returnUrl.toString();
    }

}
