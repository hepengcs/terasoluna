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

package jp.terasoluna.fw.web.rich.springmvc.bind.creator;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

/**
 * �N�G���`���̃��N�G�X�g�f�[�^�ɑΉ�����ServletRequestDataBinder�����N���X��ԋp����B
 * 
 * <p>
 * Spring�ɒ�`����Ă���ServletRequestDataBinder�������N���X�Ƃ��ĕԋp����B
 * </p>
 * 
 * <p>
 * �}���`�p�[�g�`���Œ�`���ꂽ���N�G�X�g�f�[�^�ɂ��Ή����Ă���B
 * </p>
 * 
 * <p>
 * �{�N���X�̎g�p���@�́A{@link jp.terasoluna.fw.web.rich.springmvc.controller.TerasolunaController}���Q�Ƃ��邱�ƁB
 * </p>
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.controller.TerasolunaController
 * @see org.springframework.web.bind.ServletRequestDataBinder
 * 
 */
public class QueryServletRequestDataBinderCreator implements
        ServletRequestDataBinderCreator {

    /**
     * �N�G���`���̃��N�G�X�g�f�[�^�ɑΉ�����ServletRequestDataBinder�����N���X��ԋp����B
     * 
     * <p>
     * �}���`�p�[�g�`���̃��N�G�X�g�f�[�^���o�C�g�z��ɕϊ����邱�Ƃ��\�ɂ���v���p�e�B�G�f�B�^��ݒ肷��B
     * </p>
     * 
     * @param request �N�G���`���̃��N�G�X�g
     * @param command �R�}���h�I�u�W�F�N�g
     * @param requestName ���N�G�X�g��
     * @return Spring�ɒ�`����Ă���ServletRequestDataBinder
     */
    public ServletRequestDataBinder create(HttpServletRequest request,
            Object command, String requestName) {
        // �N�G���`���̃��N�G�X�g�f�[�^�ɑΉ�����ServletRequestDataBinder�����N���X�𐶐�����B
        ServletRequestDataBinder binder = new ServletRequestDataBinder(command,
                requestName);
        // ServletRequestDataBinder�����N���X�ɁA
        // �}���`�p�[�g�`���̃��N�G�X�g�f�[�^���o�C�g�z��ɕϊ����邱�Ƃ��\�ɂ���v���p�e�B�G�f�B�^�𐶐�����
        binder.registerCustomEditor(byte[].class,
                new ByteArrayMultipartFileEditor());
        return binder;
    }

}
