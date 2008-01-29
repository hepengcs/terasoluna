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

package jp.terasoluna.fw.web.rich.exception;

import javax.servlet.ServletException;

/**
 * ForbiddenURIFilter�ŁA
 * ������Ȃ�URI�ɑ΂��ă��N�G�X�g���ꂽ�Ɣ��肳�ꂽ
 * �ꍇ�ɁAForbiddenURIFilter����X���[�����N���X�B
 * 
 * �ڍׂ́AForbiddenURIFilter���Q�Ƃ̂��ƁB
 * 
 * @see jp.terasoluna.fw.web.rich.ForbiddenURIFilter
 * 
 *
 */
public class ForbiddenURIException extends ServletException {
    /**
     * �V���A���o�[�W����ID�B
     */
    private static final long serialVersionUID = -369710077653519791L;
}
