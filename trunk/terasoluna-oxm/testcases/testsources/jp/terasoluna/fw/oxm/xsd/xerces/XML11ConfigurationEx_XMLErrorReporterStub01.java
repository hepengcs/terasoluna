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

import org.apache.xerces.impl.XMLErrorReporter;
import org.apache.xerces.util.MessageFormatter;

/**
 * 
 * XMLErrorReporterのスタブ
 *
 * <p>
 * putMessageFormatterメソッドの呼び出し確認を行う。
 * </p>
 *
 */

public class XML11ConfigurationEx_XMLErrorReporterStub01 extends XMLErrorReporter{

    boolean isPutMessageFormatter = false;
    
    public XML11ConfigurationEx_XMLErrorReporterStub01() {
        super();
    }

    @Override
    public void putMessageFormatter(String domain, MessageFormatter messageFormatter) {
        isPutMessageFormatter = true;
        super.putMessageFormatter(domain, messageFormatter);
    }
    
}
