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

package jp.terasoluna.fw.web.rich.springmvc.servlet.view.filedownload;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * ByteArrayInputStreamクラスのスタブ。メソッド呼び出し確認用。
 *
 */
public class AbstractFileDownloadView_ByteArrayInputStreamStub01 extends
        ByteArrayInputStream {

    protected boolean isClose = false;
    protected boolean isRead = false;
    
    public AbstractFileDownloadView_ByteArrayInputStreamStub01(byte[] buf) {
        super(buf);
    }
    
    @Override
    public void close() throws IOException {
        isClose = true;
//        super.close();
        throw new IOException();
    }
    
    @Override
    public int read(byte[] b) throws IOException {
        isRead = true;
        return super.read(b);
    }

}
