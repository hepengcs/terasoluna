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

package jp.terasoluna.fw.batch.springsupport.messages;

import org.springframework.context.support.ApplicationObjectSupport;

import jp.terasoluna.fw.batch.messages.MessageAccessor;

/**
 * ���b�Z�[�W�擾�N���X�̎����N���X<br>
 * Spring Framework�̃A�v���P�[�V�����R���e�L�X�g�ɕێ�����Ă��郁�b�Z�[�W����
 * ������N���X<br>
 * org.springframework.context.support.ApplicationObjectSupport�ɒ�`����Ă���
 * MessageSourceAccesor����getMessage���\�b�h���g�p���Ă���
 * <br><br>
 * <strong>�g�p���@</strong><br>
 * ���̃N���X�𗘗p����ɂ́ABean��`�t�@�C���ɂă��b�Z�[�W�𗘗p����N���X�̃v
 * ���p�e�B�Ƃ��Đݒ肷��B<br>
 * <br>
 * <strong>�ݒ��</strong><br>
 * �r�W�l�X���W�b�N�Ń��b�Z�[�W�擾�p�N���X�𗘗p���邽�߂�Bean��`�t�@�C���̋L
 * �q��<br>
 * 
 * <pre>
 * &lt;bean id = &quot;blogic&quot;
 *   class = &quot;jp.terasoluna.batch.sample.SampleBLogic&quot;&gt;
 *   &lt;property name = &quot;messageAccessor&quot;&gt;
 *     &lt;ref bean = &quot;messageAccessor&quot;&gt;&lt;/ref&gt;
 *   &lt;/property&gt;
 * &lt;/bean&gt;
 * </pre>
 * 
 * �t���[�����[�NBean��`�t�@�C���̒�`<br>
 * 
 * <pre>
 * &lt;bean id = &quot;messageAccessor&quot;
 *   class = &quot;jp.terasoluna.fw.batch.springsupport.messages.MessageAccessorImpl&quot;&gt;&lt;/ref&gt;
 * </pre>
 * 
 * 
 */
public class MessageAccessorImpl extends ApplicationObjectSupport 
    implements MessageAccessor {

    /**
     * ���b�Z�[�W�L�[�Ŏw�肵�����b�Z�[�W���擾����B
     * �w�肳�ꂽ���b�Z�[�WID�ɑΉ����郁�b�Z�[�W�����݂��Ȃ��ꍇ�ɂ�
     * ���b�Z�[�WID��ԋp����B
     * 
     * @param code ���b�Z�[�W�L�[
     * @param args ���b�Z�[�W���̃v���[�X�z���_�ɖ��ߍ��ޕ����� 
     * @return ���b�Z�[�W
     */
    public String getMessage(String code, Object[] args) {
        

         // �f�t�H���g���b�Z�[�W(�f�t�H���g���b�Z�[�W�Ƃ��ă��b�Z�[�WID��ݒ�)
        String defaultMessage = code;
        
        // ���b�Z�[�W��ԋp����
        return 
            getMessageSourceAccessor().getMessage(code, args, defaultMessage);

    }

}
