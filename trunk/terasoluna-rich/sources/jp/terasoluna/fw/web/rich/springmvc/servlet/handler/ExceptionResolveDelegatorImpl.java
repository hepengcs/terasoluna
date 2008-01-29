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

package jp.terasoluna.fw.web.rich.springmvc.servlet.handler;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import jp.terasoluna.fw.web.rich.springmvc.Constants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

/**
 * ExceptionResolveDelegatorのデフォルト実装クラス。
 * <p>
 * 本クラスは、{@link jp.terasoluna.fw.web.rich.springmvc.servlet.handler.SimpleMappingExceptionResolverEx}で使用されることを前提としている。
 * </p>
 * 
 * <p>
 * エラー種別をレスポンスヘッダに、エラーコードをModelインスタンスに設定する。<br>
 * 本クラスを使用する場合、SimpleMappingExceptionResolverExのlinkedExceptionMappings属性の値に、
 * ビュー名とエラー情報をカンマで区切った文字列を設定（Bean定義）する必要がある。
 * （※Bean定義ファイルの記述方法に関しては、SimpleMappingExceptionResolverExのjavadocを参照すること）<br>
 * カンマで区切った文字列の１番目にビュー名、２番目にエラー種別、３番目にエラーコードを記述すること。<br>
 * ビュー名とエラー種別の設定は必須である。<br>
 * エラーコードの設定は任意であり、省略することができる。<br>
 * モデルにビュー名（"bindException"）とエラーコード（"8004C002"）、レスポンスヘッダにエラー種別（"kind01"）を設定する場合、以下の文字列を設定（Bean定義）する。
 * 「bindException,kind01,8004C002」<br>
 * モデルにビュー名（"bindException"）、レスポンスヘッダにエラー種別（"kind01"）を設定する場合、以下の文字列を設定（Bean定義）する。
 * 「bindException,kind01」
 * </p>
 * 
 */
public class ExceptionResolveDelegatorImpl implements ExceptionResolveDelegator {
    /**
     * ログクラス。
     */
    private static Log log = LogFactory
            .getLog(ExceptionResolveDelegatorImpl.class);

    /**
     * 例外が発生した場合にレスポンスヘッダに設定するキー名のデフォルト値。
     */
    protected static final String EXCEPTION_KEY = "exception";
    
    /**
     * 設定ファイルにレスポンスヘッダのエラータイプのキー名のキー。
     */
    protected static final String ERROR_TYPE_HEADER_NAME_KEY = "errorTypeHeaderName";

    /**
     * 例外の型。
     */
    protected String mappingKey = null;

    /**
     * ビュー名とエラー情報が格納された文字列。
     */
    protected Object mappingValues = null;

    /**
     * ビュー名。
     */
    protected String viewName = null;
    
    /**
     * エラー種別。
     */
    protected String errorType = null;

    /**
     * エラーコード。
     */
    protected String errorCode = null;
    
    /**
     * レスポンスヘッダのエラータイプのキー名(デフォルト値：EXCEPTION_KEY)
     */
    protected String errorTypeHeaderName = EXCEPTION_KEY;

    /**
     * 例外の型とビュー名（＆エラー情報）を属性に格納する。
     * 格納する前に、ビュー名（＆エラー情報）の正当性をチェックし、
     * レスポンスヘッダにヘッダのエラータイプのキーの有効性をチェックする。
     * paramsにキーERROR_TYPE_HEADER_NAME_KEYが存在し、その値がnullと空文字列以外の場合は、
     * 属性errorTypeHeaderNameにその値を設定する。
     * 
     * @param mappingKey 例外の型
     * @param mappingValues ビュー名とエラー情報（カンマ区切りの文字列）
     * @param params ヘッダのエラータイプのキーなど情報のキーと値を格納するMap
     */
    public void initMapping(String mappingKey, Object mappingValues, 
            Map<String,String> params) {

        // mappingValuesがnullの場合、例外をスローする。
        if (mappingValues == null) {
            String message = "linkedExceptionMappings[" + mappingKey
                    + "] value is null. "
                    + "Check Spring Bean definition file.";
            log.error(message);
            throw new IllegalStateException(message);
        }

        // mappingValuesがString型でない場合、例外をスローする。
        if (!(mappingValues instanceof String)) {
            String message = "linkedExceptionMappings[" + mappingKey
            + "] value is not String type. "
            + "Check Spring Bean definition file.";
            log.error(message);
            throw new IllegalStateException(message);
        }
        
        // paramsにキーERROR_TYPE_HEADER_NAME_KEYが存在し、その値が空文字列以外の場合
        if (params != null){
            String errorTypeName = params.get(ERROR_TYPE_HEADER_NAME_KEY);
            if(errorTypeName != null && errorTypeName.length() != 0){
                // 属性errorTypeHeaderNameにその値を設定する。
                this.errorTypeHeaderName = errorTypeName;
            }
        }
        
        String[] mappingValueArray = StringUtils
                .commaDelimitedListToStringArray((String) mappingValues);

        // mappingValuesをカンマで分割した値が２つ以下の場合、例外をスローする
        if (mappingValueArray.length < 2) {
            String message = "linkedExceptionMappings[" + mappingKey
                    + "] value is insufficient. Two values are necessary. "
                    + "Check Spring Bean definition file.";
            log.error(message);
            throw new IllegalStateException(message);
        }

        // mappingValuesをカンマで分割した値が空白の場合、例外をスローする
        for (int i = 0; i < mappingValueArray.length; i++) {
            mappingValueArray[i] = mappingValueArray[i].trim();
            if ("".equals(mappingValueArray[i])) {
                String message = "linkedExceptionMappings[" + mappingKey
                        + "] value[" + i + "] is empty. "
                        + "Check Spring Bean definition file.";
                log.error(message);
                throw new IllegalStateException(message);
            }
        }

        this.mappingKey = mappingKey;
        this.mappingValues = mappingValues;

        // mappingValuesをカンマで分割した値を属性に格納する
        this.viewName = mappingValueArray[0];
        this.errorType = mappingValueArray[1];
        if (mappingValueArray.length > 2) {
            this.errorCode = mappingValueArray[2];
        }

    }

    /**
     * レスポンスヘッダにエラー種別を設定する。
     * 
     * @param response HTTPレスポンス
     */
    public void setHeader(HttpServletResponse response) {
        response.setHeader(errorTypeHeaderName, this.errorType);
    }

    /**
     * ModelAndViewにエラーコードを設定する。
     * 
     * @param mv ModelAndView ModelAndViewオブジェクト
     */
    public void addObjectToModel(ModelAndView mv) {
        if (this.errorCode != null) {
            mv.addObject(Constants.ERRORCODE_KEY, this.errorCode);
        }
    }

    /**
     * ビュー名を取得する。
     * @return ビュー名
     */
    public String getViewName() {
        return this.viewName;
    }

}
