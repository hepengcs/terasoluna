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

import java.io.IOException;

import javax.servlet.ServletOutputStream;

/**
 * ServletOutputStream�N���X�̃X�^�u�Bwrite���\�b�h��
 * �������݂̉񐔂��`�F�b�N����B
 *
 */
public class AbstractFileDownloadView_ServletOutputStreamStub04 extends
        ServletOutputStream {
    
    protected int count = 0;

    @Override
    public void write(int b) throws IOException {
    }
    
    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        count++;
        super.write(b, off, len);
    }
}
