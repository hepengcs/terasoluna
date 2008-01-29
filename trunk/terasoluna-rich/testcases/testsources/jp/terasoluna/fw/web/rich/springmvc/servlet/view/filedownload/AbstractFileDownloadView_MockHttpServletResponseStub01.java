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

import jp.terasoluna.utlib.MockHttpServletResponse;

/**
 * MockHttpServletResponse�̃X�^�u�B�o�̓X�g���[���擾���ɗ�O�𓊂���B
 *
 */
public class AbstractFileDownloadView_MockHttpServletResponseStub01 extends
        MockHttpServletResponse {

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        throw new IOException();
    }

}
