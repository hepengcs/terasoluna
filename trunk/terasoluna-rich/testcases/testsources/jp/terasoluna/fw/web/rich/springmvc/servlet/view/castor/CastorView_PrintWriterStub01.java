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

import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * PrintWriterのスタブクラス。HTTPレスポンスのWriterとして使用する。
 * メソッドの呼び出し確認を行う。
 *
 */
public class CastorView_PrintWriterStub01 extends PrintWriter {

    public CastorView_PrintWriterStub01(OutputStream out) {
        super(out);
    }

    protected boolean isWrite = false;
    protected boolean isClose = false;
    
    @Override
    public void write(String s) {
        isWrite = true;
        super.write(s);
    }
    
    @Override
    public void close() {
        isClose = true;
        super.close();
    }

}
