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

/**
 * �����񂪋�����URI�����肷��`�F�b�J�̃C���^�t�F�[�X�B
 * 
 * <p>
 * ForbiddenURIFilter���A
 * �A�v���P�[�V�����R���e�L�X�g��ʂ��ČĂяo����A���s����邱�Ƃ�z�肷��B
 * 
 * �{�N���X�̎����N���X��Bean��`���邱�ƁB��`��͎����N���X���Q�Ƃ��邱�ƁB
 * �ʏ�͎����N���X�Ƃ��āAForbiddenURICheckerImpl�𗘗p����΂悢�B
 * ForbiddenURICheckerImpl�ł͋Ɩ��̗v�����������Ȃ��ꍇ�ɂ̂݁A
 * �{�C���^�t�F�[�X�����������Ɩ��v���𖞂����N���X���쐬���邱�ƁB
 * </p>

 * 
 * @see jp.terasoluna.fw.web.rich.ForbiddenURIFilter
 * 
 */
public interface ForbiddenURIChecker {
    
    /**
     * ������Ă���URI���`�F�b�N����B
     * 
     * @param requestURI �`�F�b�N�Ώۂ�URI
     * @return �`�F�b�N���ʁi������Ă����true�j
     */
    boolean isAllowedURI(String requestURI);
}
