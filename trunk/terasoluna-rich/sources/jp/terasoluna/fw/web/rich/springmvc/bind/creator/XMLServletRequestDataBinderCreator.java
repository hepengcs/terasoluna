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

package jp.terasoluna.fw.web.rich.springmvc.bind.creator;

import javax.servlet.http.HttpServletRequest;
import jp.terasoluna.fw.oxm.mapper.OXMapper;
import jp.terasoluna.fw.oxm.xsd.SchemaValidator;
import jp.terasoluna.fw.web.rich.springmvc.bind.XMLServletRequestDataBinder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.bind.ServletRequestDataBinder;

/**
 * XML形式で定義されたリクエストデータをバインドするクラスを生成する役割を持つクラス。
 * <p>
 * Springに定義されているServletRequestDataBinderを実装クラスとして返却する。
 * </p>
 * <p>
 * 本クラスを使用する場合は、バインド処理を行う{@link jp.terasoluna.fw.oxm.mapper.OXMapper}の実装クラスをDIすること。<br>
 * XMLスキーマを使用した形式チェックを行う場合は、{@link jp.terasoluna.fw.oxm.xsd.SchemaValidator}の実装クラスをDIすること。
 * </p>
 * <p>
 * 【Bean定義ファイルの設定例（形式チェックを行う場合）】<br>
 * <code><pre>
 * &lt;bean id=&quot;oxmapper&quot; class=&quot;jp.terasoluna.fw.oxm.mapper.castor.CastorOXMapperImpl&quot; /&gt;
 * 
 * &lt;bean id=&quot;schemaValidator&quot; class=&quot;jp.terasoluna.fw.oxm.xsdvalidator.impl.SchemaValidatorImpl&quot; /&gt;
 * 
 * &lt;bean id=&quot;xmlDataBinderCreator&quot;
 *   class=&quot;jp.terasoluna.fw.web.rich.springmvc.bind.creator.XMLServletRequestDataBinderCreator&quot;&gt;
 *   &lt;property name=&quot;oxmapper&quot;&gt;&lt;ref local=&quot;oxmapper&quot;/&gt;&lt;/property&gt;
 *   &lt;property name=&quot;schemaValidator&quot;&gt;&lt;ref local=&quot;schemaValidator&quot;/&gt;&lt;/property&gt;
 * &lt;/bean&gt;
 * </pre></code>
 * </p>
 * <p>
 * 本クラスの使用方法は、{@link jp.terasoluna.fw.web.rich.springmvc.controller.TerasolunaController}を参照すること。
 * </p>
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.controller.TerasolunaController
 * @see jp.terasoluna.fw.web.rich.springmvc.bind.XMLServletRequestDataBinder
 */
public class XMLServletRequestDataBinderCreator implements
        ServletRequestDataBinderCreator, InitializingBean {
    /**
     * ログクラス。
     */
    private static Log log = LogFactory
            .getLog(XMLServletRequestDataBinderCreator.class);

    /**
     * OXMapper。
     */
    private OXMapper oxmapper = null;

    /**
     * SchemaValidator。
     */
    private SchemaValidator schemaValidator = null;

    /**
     * XML形式のリクエストデータに対応したServletRequestDataBinder実装クラスを返却する。
     * 
     * @param request XML形式のリクエスト
     * @param command コマンドオブジェクト
     * @param requestName リクエスト名
     * @return XMLServletRequestDataBinder
     */
    public ServletRequestDataBinder create(HttpServletRequest request,
            Object command, String requestName) {
        return new XMLServletRequestDataBinder(command, oxmapper,
                schemaValidator, requestName);
    }
    
    /**
     * DIコンテナ起動時、本クラスがインスタンス化された直後に呼ばれるメソッド。
     * OXMapper（XML形式のデータをバインドするクラス）が設定されていない場合、 例外を投げる。
     */
    public void afterPropertiesSet() {
        // XMLDataBinderが設定されていない場合、例外とする
        if (oxmapper == null) {
            log.error("OXMapper isn't set in ServletRequestDataBinder. "
                    + "Check Spring Bean definition file.");
            throw new IllegalStateException(
                    "OXMapper isn't set in ServletRequestDataBinder. "
                    + "Check Spring Bean definition file.");
        }
    }

    /**
     * OXMapperを取得する。
     * 
     * @return OXMapper OXMapper
     */
    public OXMapper getOxmapper() {
        return oxmapper;
    }

    /**
     * OXMapperを設定する。
     * 
     * @param oxmapper OXMapper
     */
    public void setOxmapper(OXMapper oxmapper) {
        this.oxmapper = oxmapper;
    }

    /**
     * SchemaValidatorを取得する。
     * 
     * @return SchemaValidator SchemaValidator
     */
    public SchemaValidator getSchemaValidator() {
        return schemaValidator;
    }

    /**
     * SchemaValidatorを設定する
     * 
     * @param schemaValidator SchemaValidator
     */
    public void setSchemaValidator(SchemaValidator schemaValidator) {
        this.schemaValidator = schemaValidator;
    }
            
}
