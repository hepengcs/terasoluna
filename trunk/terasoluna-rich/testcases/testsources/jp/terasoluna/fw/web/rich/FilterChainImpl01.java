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

package jp.terasoluna.fw.web.rich;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * doFilter�Ăяo���m�F�p��FilterChain�����N���X�B
 * 
 */
public class FilterChainImpl01 implements FilterChain {

    /**
     * doFilter�Ăяo���m�F�t���O�B
     */
    public boolean isDoFilter = false;
    
    /**
     * doFilter
     */
    public void doFilter(ServletRequest arg0, ServletResponse arg1)
            throws IOException, ServletException {
        this.isDoFilter = true;
    }

}
