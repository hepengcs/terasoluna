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

package jp.terasoluna.fw.web.rich.springmvc.servlet.view.castor;

import java.io.IOException;
import java.io.PrintWriter;

import jp.terasoluna.utlib.MockHttpServletResponse;

/**
 * MockHttpServletResponseのスタブクラス。メソッドの呼び出し確認を行う。
 *
 */
public class CastorView_MockHttpServletResponseStub02 extends
        MockHttpServletResponse {

    protected boolean isGetWriter = false;
    
    @Override
    public PrintWriter getWriter() throws IOException {
        isGetWriter = true;
        return super.getWriter();
    }
}
