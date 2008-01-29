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

package jp.terasoluna.fw.oxm.mapper.castor;

import java.net.URL;

import jp.terasoluna.fw.oxm.mapper.castor.CastorOXMapperImpl;

import org.exolab.castor.mapping.Mapping;
import org.xml.sax.InputSource;

/**
 * CastorOXMapperImplの継承クラス。
 * 
 * <p>
 * Mappingインスタンスを生成する。
 * </p>
 * 
 */
public class CastorOXMapperImpl_CastorOXMapperImplStub03 extends CastorOXMapperImpl {

    protected Class mappingClass = null;

    protected String mappingPath = "";

    protected Mapping mapping = null;
    
    @Override
    protected Mapping getCastorMapping(Class mappingClass) {
        
        this.mappingClass = mappingClass;

        // Castorマッピング定義ファイルを取得する
        URL mappingURL = Thread.currentThread().getContextClassLoader().getResource(mappingPath);

        mapping = new Mapping();
        
        mapping.loadMapping(new InputSource(mappingURL.toExternalForm()));

        return mapping;
    }
    


}
