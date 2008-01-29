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

import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �����񂪋�����URI�����肷��`�F�b�J�̃f�t�H���g�����N���X�B
 * 
 * <p>
 * �������̕����񂪁A
 * ���炩���ߐݒ肳�ꂽ��������̃��X�g�ɂ��Ă͂܂邪�ǂ������`�F�b�N����B
 * </p>
 * 
 * <p>
 * �{�N���X�ł́A�V�X�e���̗v���𖞂����Ȃ��ꍇ�̂݁A
 * �{�N���X���g�p����ForbiddenURIChecker�̎����N���X��V�K�쐬���A
 * �`�F�b�N���\�b�h���������邱�ƁB
 * �ڍׂ́AForbiddenURIChecker���Q�Ƃ��邱�ƁB
 * </p>
 * 
 * <p>
 * �{�N���X�𗘗p����ꍇ�ABean��`���邱�ƁB
 * ��`���@�́AForbiddenURIChecker���Q�Ƃ��邱�ƁB
 * �܂��A�ȉ��̃v���p�e�B��K���ݒ肷�邱�ƁB
 * </p>
 * <p>
 *   <table border="1" CELLPADDING="8">
 *     <th>������</th>
 *     <th>�K�{</th>
 *     <th>����</th>
 *     
 *     <tr>
 *       <td align=center><b>allowedURISet</b></td>
 *       <td>��</td>
 *       <td>������URI(�Z�b�g�`��)</td>
 *     </tr>
 *  </table>
 * </p>
 * 
 * <p>
 * �y<code>Bean��`�t�@�C��</code>�̐ݒ��z<br>
 * <code><pre>
 * &lt;bean id="forbiddenURIChecker"
 *      class="jp.terasoluna.fw.web.rich.ForbiddenURICheckerImpl"&gt;
 *   &lt;property name="allowedURISet"&gt;
 *     &lt;set&gt;
 *       &lt;value&gt;/secure/blogic.do&lt;/value&gt;
 *     &lt;/set&gt;
 *   &lt;/property&gt;
 * &lt;/bean&gt;
 * </pre></code>
 * </p>
 * 
 */
public class ForbiddenURICheckerImpl implements ForbiddenURIChecker {
    /**
     * ���O�B
     */
    private static Log logger
        = LogFactory.getLog(ForbiddenURICheckerImpl.class);

    /**
     * ������URI�̃Z�b�g�B
     */
    private Set<String> allowedURISet = null;
    
    /**
     * ������URI�̃��X�g��ݒ肷��B
     * 
     * @param allowedURISet ������URI�̃��X�g
     */
    public void setAllowedURISet(Set<String> allowedURISet) {
        this.allowedURISet = allowedURISet;
    }
    
    /**
     * ������Ă���URI���`�F�b�N����B
     * 
     * @param requestURI �`�F�b�N�Ώۂ�URI
     * @return �`�F�b�N���ʁi������Ă����true�j
     */
    public boolean isAllowedURI(String requestURI) {
        if (allowedURISet == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("allowedURISet is null.");
            }
            return false;
        }
        
        // URI�̋��۔��菈��
        return allowedURISet.contains(requestURI);
    }
}
