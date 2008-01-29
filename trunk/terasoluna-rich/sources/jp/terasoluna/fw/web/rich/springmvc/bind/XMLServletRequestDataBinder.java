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

package jp.terasoluna.fw.web.rich.springmvc.bind;

import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletRequest;
import jp.terasoluna.fw.oxm.mapper.OXMapper;
import jp.terasoluna.fw.oxm.xsd.SchemaValidator;
import jp.terasoluna.fw.oxm.xsd.message.ErrorMessage;
import jp.terasoluna.fw.oxm.xsd.message.ErrorMessages;
import jp.terasoluna.fw.web.rich.springmvc.bind.creator.exception.XMLRequestIOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.w3c.dom.Document;

/**
 * XML形式のリクエストデータをコマンドオブジェクトにバインドするクラス。
 * <p>
 * 以下の処理を順番に行う。
 * <ol>
 * <li>XMLデータの形式チェック（※省略可能）</li>
 * <li>XML→オブジェクトへの変換処理</li>
 * </ol>
 * </p>
 * <p>
 * XMLデータの形式チェックにはXMLスキーマを使用する。 <br>
 * 実際の形式チェック部分はSchemaValidatorに処理を委譲する。<br>
 * 詳細は{@link jp.terasoluna.fw.oxm.xsd.SchemaValidator}を参照すること。
 * </p>
 * <p>
 * XMLデータをオブジェクトに変換する機能はCastorを使用する。 詳細はCastorOXMapperImplクラスを参照すること。
 * 実際のXMLからオブジェクトへ変換する部分はOXMapperに処理を委譲する。<br>
 * 詳細は{@link jp.terasoluna.fw.oxm.mapper.OXMapper}を参照すること。
 * </p>
 * <p>
 * データバインド処理で発生するエラーは２種類あり、適切なエラーハンドリングを行う必要がある。<br>
 * エラーの一覧を以下に記す<br>
 * <ol>
 * <li>形式チェックエラー</li>
 * <li>OXMappingException</li>
 * </ol>
 * </p>
 * <p>
 * <u>形式チェックエラーのハンドリング</u><br>
 * 形式チェックエラーで生成されるエラーメッセージを、BindExceptionに格納する。<br>
 * エラーの詳細は{@link jp.terasoluna.fw.oxm.xsd.xerces.XMLErrorReporterEx}を参照すること。
 * </p>
 * <p>
 * 【形式チェックのリソースバンドル設定例】<br>
 * <code><pre>
 *           typeMismatch.number= {0}には{1}値を入力してください.
 *           typeMismatch.boolean= {0}にはboolean値を入力してください.
 *           typeMismatch.date= {0}には正しい日付を入力してください.
 *           typeMismatch.numberMinRange= {0}には{1}以上の{2}値を入力してください.
 *           typeMismatch.numberMaxRange= {0}には{1}以下の{2}値を入力してください.
 * </pre></code>
 * </p>
 * <p>
 * <u>OXMappingExceptionのハンドリング</u><br>
 * データバインド処理で発生する例外はすべて実行時例外であり、 基本的にハンドリングする必要はない。<br>
 * 必要に応じて、例外ハンドラの定義にOXMappingException、 またはそのサブクラスをエントリすること。
 * OXMappingExceptionの詳細は{@link jp.terasoluna.fw.oxm.exception.OXMappingException}を参照すること。
 * 例外ハンドリングの詳細は{@link org.springframework.web.servlet.handler.SimpleMappingExceptionResolver}を参照すること。
 * </p>
 * <p>
 * 【OXMappingExceptionのBean定義例】 <br>
 * <code><pre>
 *          &lt;bean id=&quot;handlerExceptionResolver&quot;
 *                class=&quot;jp.terasoluna.fw.web.rich.springmvc.servlet.handler.SimpleMappingExceptionResolverEx&quot;&gt;
 *              &lt;property name=&quot;linkedExceptionMappings&quot;&gt;
 *                  &lt;map&gt;
 *                     &lt;entry key=&quot;jp.terasoluna.fw.oxm.exception.OXMappingException&quot;&gt;
 *                          &lt;value&gt;oxmException,8004C028&lt;/value&gt;
 *                      &lt;/entry&gt;
 *                                      ・
 *                                      ・
 *                                      ・
 *                  &lt;/map&gt;
 *              &lt;/property&gt;
 *          &lt;/bean&gt;
 * </pre></code>
 * </p>
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.bind.creator.XMLServletRequestDataBinderCreator
 * @see jp.terasoluna.fw.oxm.xsd.message.ErrorMessage
 * @see jp.terasoluna.fw.oxm.xsd.message.ErrorMessages
 * @see jp.terasoluna.fw.oxm.exception.OXMappingException
 * @see jp.terasoluna.fw.oxm.xsd.SchemaValidator
 * @see jp.terasoluna.fw.oxm.mapper.OXMapper
 * @see org.springframework.web.servlet.handler.SimpleMappingExceptionResolver
 */
public class XMLServletRequestDataBinder extends ServletRequestDataBinder {
    
    /**
     * ログクラス。
     */
    private static Log log = LogFactory
            .getLog(XMLServletRequestDataBinder.class);

    /**
     * OXMapper。
     */
    private OXMapper oxmapper = null;

    /**
     * SchemaValidator。
     */
    private SchemaValidator schemaValidator = null;

    /**
     * XMLServletRequestDataBinderを生成する。
     * 
     * @param target コマンドオブジェクト
     * @param oxmapper OXMapper
     * @param schemaValidator SchemaValidator
     * @param objectName オブジェクト名
     */
    public XMLServletRequestDataBinder(Object target, OXMapper oxmapper,
            SchemaValidator schemaValidator,String objectName) {
        super(target, objectName);
        this.oxmapper = oxmapper;
        this.schemaValidator = schemaValidator;
    }
    
    /**
     * XML形式で定義されたリクエストデータをバインドする。
     * <p>
     * 実際のデータバインド部分は、OXMapperに処理を委譲する。
     * </p>
     * <p>
     * SchemaValidatorがDIされている場合、形式チェックを実行する。
     * </p>
     * 
     * @param request XML形式で定義されたリクエストデータ
     */
    @Override
    public void bind(ServletRequest request) {

        // XML形式で定義されたリクエストデータの入力ストリームを取得する
        InputStream in = null;

        try {
            in = request.getInputStream();

            // SchemaValidatorがDIされている場合、形式チェックを実行する。
            if (schemaValidator != null) {

                Document doc = validate(in);

                // 形式チェックでエラーが発生した場合、処理を中止する
                if (getBindingResult().hasErrors()) {
                    return;
                }
                // 形式チェック済みのDOMツリーを使用して、アンマーシャルを実行する
                oxmapper.unmarshal(doc, getTarget());
            } else {
                oxmapper.unmarshal(in, request.getCharacterEncoding(),
                        getTarget());
            }
        } catch (IOException e) {
            // ストリーム取得の際に、入出力例外が発生した場合
            log.error("Request stream error.", e);
            throw new XMLRequestIOException(e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                log.error("Failed to close request stream.", e);
            }
        }

    }

    /**
     * リクエストデータの形式チェックを行う。
     * <p>
     * 実際の形式チェック部分は、SchemaValidatorに処理を委譲する。
     * </p>
     * <p>
     * 形式チェックエラーが発生した場合、 BindExceptionにエラーを格納する。
     * </p>
     * 
     * @param in XMLデータ
     * @return Document DOMツリー
     */
    protected Document validate(InputStream in) {

        if (in == null) {
            log.error("InputStream is null.");
            throw new IllegalArgumentException("InputStream is null.");
        }
        
        // スキーマ定義による形式チェック
        ErrorMessages errorMessages = new ErrorMessages();

        Document doc = schemaValidator.validate(in, getTarget(), errorMessages);

        BindingResult errors = this.getBindingResult();

        // エラーメッセージがある場合、BindingResultにエラー情報を詰め替える
        for (ErrorMessage errorMessage : errorMessages.getErrorMessages()) {
            
            // BindingResultに格納するためのエラーを生成する
            FieldError fe = new FieldError(getObjectName(), errorMessage
                    .getField(), null, false, errors.resolveMessageCodes(
                    errorMessage.getKey(), errorMessage.getField()),
                    createReplaceValues(errorMessage.getField(), errorMessage
                            .getReplaceValues()), null);
            
            // BindingResultにエラーをセットする
            errors.addError(fe);
        }

        return doc;

    }

    /**
     * 置換文字列を生成する。
     * <p>
     * 置換文字列の最初にフィールド情報を追加し、最後にエラー値を格納する。
     * </p>
     * 
     * @param field フィールド値
     * @param replaceValues 置換文字列
     * @return 配列の０番目にフィールド情報が付加された置換文字列
     */
    protected String[] createReplaceValues(
            String field, String[] replaceValues) {

        // フィールド値がnullの場合は空文字に変換する
        if (field == null) {
            field = "";
        }

        // 置換文字列がnullまたは空のリストの場合、
        // フィールド値のみを格納した置換文字列を返却する
        if (replaceValues == null || replaceValues.length == 0) {
            return new String[] { field };
        }

        String[] resultReplaceValues = new String[replaceValues.length + 1];

        // 配列のコピー
        System.arraycopy(replaceValues, 0, resultReplaceValues, 0,
                replaceValues.length);

        // フィールド情報は置換文字列の最初に格納する
        resultReplaceValues[0] = field;

        // エラー値は置換文字列の最後に格納する
        resultReplaceValues[resultReplaceValues.length - 1] = replaceValues[0];

        return resultReplaceValues;
    }
}
