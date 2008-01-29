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

import jp.terasoluna.fw.oxm.xsd.message.ErrorMessages;
import jp.terasoluna.fw.oxm.xsd.xerces.XMLErrorReporterEx;

/**
 * XMLErrorReporterExを継承したクラス。
 * 
 * <p>
 * errorLogメソッドの呼び出し確認を行う。<br>
 * addErrorMessageメソッドに引数が渡されたことの確認を行う。
 * </p>
 * 
 */
public class XMLErrorReporterEx_XMLErrorReporterExStub01 extends XMLErrorReporterEx {

    protected String key = null;
    
    protected String[] options = null;

    protected boolean isErrorLog = false;
    
    public XMLErrorReporterEx_XMLErrorReporterExStub01(ErrorMessages errorMessages) {
        super(errorMessages);
    }
    
    @Override
    protected void errorLog(String key, Object[] options) {
        this.isErrorLog  = true;
    }    

    @Override
    protected void addErrorMessage(String key, String[] options) {
        this.key = key;
        this.options = options;
    }

}
