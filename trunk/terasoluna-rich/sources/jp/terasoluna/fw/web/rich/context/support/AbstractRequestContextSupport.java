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

package jp.terasoluna.fw.web.rich.context.support;

import javax.servlet.http.HttpServletRequest;

import jp.terasoluna.fw.web.rich.context.RequestContext;
import jp.terasoluna.fw.web.rich.context.RequestContextManager;
import jp.terasoluna.fw.web.rich.context.exception.IllegalContextPropertyClassTypeException;

/**
 * 制御情報を扱うための補助ロジックインタフェースを継承した抽象クラス。
 * 
 * <p>
 * 業務コンテキストマネージャより業務コンテキストを取得し、必要な情報を返却する。
 * </p>
 * 
 * <p>
 * RequestContext, RequestContextManagerを利用して
 * 制御情報を扱う実装を提供している。
 * 詳細は、RequestContext, RequestContextManagerを参照すること。
 * </P>
 * 
 * <p>
 * 制御情報に設定する値はHTTPリクエストから取得する。
 * HTTPリクエストから取得する項目は、システムの要件によって変化するため、
 * リクエスト名をリクエストヘッダ以外の箇所から取得する場合や、
 * 業務プロパティを保持したい場合等は、
 * 必要に応じてシステムごとに本クラスの実装クラスを作成し、
 * 制御情報初期化の抽象メソッドを実装すること。
 * </p>
 * 
 * <p>
 * システムとして特に上記のような要件がない場合は、デフォルト実装である、
 * DefaultRequestContextSupportImplを利用すること。
 * </p>
 * 
 * <p>
 * 本クラスを利用する場合、実装クラスをBean定義すること。
 * 定義例は、実装クラスを参照すること。
 * また、以下のプロパティを必ず設定すること。
 * </p>
 * 
 * @see jp.terasoluna.fw.web.rich.context.RequestContext
 * @see jp.terasoluna.fw.web.rich.context.RequestContextManager
 * 
 */
public abstract class AbstractRequestContextSupport
        implements RequestContextSupport {

    /**
     * 実行する業務ロジックを識別するリクエスト名を取得する。
     *
     * @return リクエスト名
     */
    public String getRequestName() {
        return getRequestContext().getRequestName();
    }

    /**
     * 業務プロパティを取得する。
     * 業務ごとに独自に保持したい情報は、このメソッドで取得する。
     *
     * @param key キー
     * @return 業務プロパティ
     */
    public Object getProperty(String key) {
        return getRequestContext().getProperty(key);
    }
    
    /**
     * 返却値の型を指定して、業務プロパティを取得する。
     * 業務ごとに独自に保持したい情報は、このメソッドで取得する。
     * 取得する業務プロパティの型と指定された型が違った場合は、
     * 実行時例外を発生させる。
     *
     * @param key キー
     * @param <E> 返却値の型
     * @return 業務プロパティ
     */
    public <E> E getProperty(String key, Class<E> clazz) {
        E propertyObject = null;
        
        if (clazz == null) {            
        	// 型パラメータが指定されていない
            String message = "Must not use null for clazz of an argument.";
        	throw new IllegalArgumentException(message);
        }
        
        try {
        	propertyObject = clazz.cast(getProperty(key));
        } catch (ClassCastException e) {
            throw new IllegalContextPropertyClassTypeException(e);
        }
    	
        return propertyObject;
    }
    
    /**
     * String型の業務プロパティを取得する。
     * 業務ごとに独自に保持したい情報は、このメソッドで取得する。
     *
     * @param key キー
     * @return 業務プロパティ
     */
    public String getPropertyString(String key) {
        return getProperty(key,String.class);
    }
    
    /**
     * 業務コンテキストを生成する。
     * @param request HTTPリクエスト
     */
    public void generateContext(HttpServletRequest request) {
        RequestContext ctx = doGenerateContext(request);
        RequestContextManager.bindRequestContext(ctx);
    }
    
    /**
     * 制御情報を生成・初期化して返却する。
     * @param request HTTPリクエスト
     * @return 生成した空の制御情報。
     */
    protected abstract RequestContext doGenerateContext(
                                    HttpServletRequest request);
    
    /**
     * 制御情報を破棄する。
     *
     */
    public void destroyContext() {
        if (RequestContextManager.hasRequestContext()) {
            RequestContextManager.unbindRequestContext();
        }
    }
    
    /**
     * 制御情報をマネージャより取得する。
     * @return 制御情報
     */
    protected RequestContext getRequestContext() {
        return RequestContextManager.getRequestContext();
    }
}
