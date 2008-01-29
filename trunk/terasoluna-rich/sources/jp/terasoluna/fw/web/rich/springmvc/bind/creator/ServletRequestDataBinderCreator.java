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

/**
 * ServletRequestDataBinder��ԋp����N���X���������ׂ��C���^�t�F�[�X�B
 * 
 * <p>
 * XML�`���Œ�`���ꂽ���N�G�X�g�������ꍇ�A�ʏ�͎����N���X�Ƃ��āA
 * XMLServletRequestDataBinderCreator�𗘗p����΂悢�B <br>
 * �N�G���`���Œ�`���ꂽ���N�G�X�g�������ꍇ�A�ʏ�͎����N���X�Ƃ��āA
 * QueryServletRequestDataBinderCreator�𗘗p����΂悢�B <br>
 * ���`���Œ�`���ꂽ���N�G�X�g�������ꍇ�A�������̓f�t�H���g�ŗp�ӂ���Ă�������N���X�ł͋Ɩ��̗v�����������Ȃ��ꍇ�ɂ̂݁A
 * �{�C���^�t�F�[�X�����������Ɩ��v���𖞂����N���X���쐬���邱�ƁB
 * </p>
 * 
 * @see QueryServletRequestDataBinderCreator
 * @see XMLServletRequestDataBinderCreator
 * @see org.springframework.web.bind.ServletRequestDataBinder
 */
public interface ServletRequestDataBinderCreator {

    /**
     * ServletRequestDataBinder�𐶐�����N���X��ԋp����B
     * 
     * @param request ���N�G�X�g
     * @param command �R�}���h�I�u�W�F�N�g
     * @param requestName ���N�G�X�g��
     * @return ServletRequestDataBinder�𐶐�����N���X
     */
    ServletRequestDataBinder create(HttpServletRequest request, Object command,
            String requestName);
}
