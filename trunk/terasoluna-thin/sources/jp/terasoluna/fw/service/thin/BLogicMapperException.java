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

package jp.terasoluna.fw.service.thin;

/**
 *  {@link jp.terasoluna.fw.service.thin.AbstractBLogicMapper}
 *  または、その継承クラスにて、マッピング処理に失敗した場合に発生する例外。
 * <p>
 *  {@link jp.terasoluna.fw.service.thin.AbstractBLogicMapper}
 *  では、この例外を
 *  {@link jp.terasoluna.fw.exception.SystemException}
 *  がラップしてスローする。
 *  {@link jp.terasoluna.fw.service.thin.BLogic}
 *  のマッピング処理に関しては、
 *  {@link jp.terasoluna.fw.service.thin.AbstractBLogicMapper}、
 *  {@link jp.terasoluna.fw.service.thin.BLogicMapper}、
 *  {@link jp.terasoluna.fw.web.struts.plugins.BLogicIOPlugIn}
 *  を参照のこと。
 * </p>
 *
 * @see jp.terasoluna.fw.service.thin.AbstractBLogicMapper
 * @see jp.terasoluna.fw.service.thin.BLogicMapper
 * @see jp.terasoluna.fw.service.thin.BLogic
 * @see jp.terasoluna.fw.service.thin.BLogicIO
 * @see jp.terasoluna.fw.service.thin.BLogicProperty
 * @see jp.terasoluna.fw.service.thin.BLogicResources
 * @see jp.terasoluna.fw.service.thin.BLogicResult
 * @see jp.terasoluna.fw.web.struts.plugins.BLogicIOPlugIn
 * @see jp.terasoluna.fw.web.struts.actions.AbstractBLogicAction
 * @see jp.terasoluna.fw.web.struts.actions.BLogicAction
 *
 */
public class BLogicMapperException extends Exception {

    /**
     * シリアルバージョンID
     */
    private static final long serialVersionUID = -2391490636688083481L;

    /**
     * コンストラクタ。
     */
    public BLogicMapperException() {
        super();
    }

    /**
     * コンストラクタ。
     *
     * @param cause 原因となった例外。
     */
    public BLogicMapperException(Throwable cause) {
        super(cause);
    }

}
