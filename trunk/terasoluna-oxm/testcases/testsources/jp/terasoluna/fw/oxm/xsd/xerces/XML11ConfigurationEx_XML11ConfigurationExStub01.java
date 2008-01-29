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

package jp.terasoluna.fw.oxm.xsd.xerces;

import jp.terasoluna.fw.oxm.xsd.xerces.XML11ConfigurationEx;

import org.apache.xerces.xni.parser.XMLComponent;

/**
 * XML11ConfigurationExを継承したクラス。
 * 
 * <p>
 * addCommonComponentメソッドの呼び出し確認を行う。
 * </p>
 * 
 */
public class XML11ConfigurationEx_XML11ConfigurationExStub01 extends XML11ConfigurationEx{

    protected boolean isAddCommonComponent = false;

    @Override
    protected void addCommonComponent(XMLComponent component) {
        isAddCommonComponent = true;        
        super.addCommonComponent(component);
    }

}
