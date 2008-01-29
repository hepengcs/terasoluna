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

package jp.terasoluna.fw.batch.validation;


import org.springframework.validation.BindException;


/**
 * ���̓`�F�b�N���s���ʂ̃n���h���C���^�t�F�[�X�B
 * 
 * <p>���̃C���^�t�F�[�X�́A���̓`�F�b�N�������N������閈�ɁA���ʏ����Ƃ��ċN��
 * �����</p>
 * 
 * <p>���̃C���^�t�F�[�X�̎����ł́A�p�����[�^�Ƃ��ēn���ꂽ<code>BindException
 * </code>�̃G���[�L���𔻒肵�A�L���[�ւ̃f�[�^�ǉ��L�������߂邱�Ƃ��ł���B
 * �܂��A���̓f�[�^�Ɉُ킪����ꍇ�ł��ُ�f�[�^�𐳏�f�[�^�ɏC�����邱�Ƃ�
 * �ُ�f�[�^�̃n���h�����O���\�ł���B
 * <code>false</code>��ԋp�����ꍇ�̓L���[�ւ̃f�[�^�ǉ��͍s��Ȃ��B</p>
 * <p>���̓`�F�b�N�̃G���[���ɏ����𒆒f����ꍇ�͗�O�𔭐�������K�v������B
 * </p>
 *
 */
public interface ValidationResultHandler {

    /**
     * �r�W�l�X���W�b�N���s���ʂ���������B
     *
     * @param bindException �o���f�[�^�ɂ���ď������ꂽ��̃o�C���h��O
     * @param value �r�W�l�X���W�b�N���̓f�[�^
     * @return �w�肳�ꂽ�f�[�^���L���[�C���O���A�㑱�������s���ꍇ�ɂ�
     *  <code>true</code>�B
     *  �w�肳�ꂽ�f�[�^���L���[�C���O�����i�f�[�^���㑱�����ɗ����Ȃ��j�X�L�b�v
     *  ����ꍇ�ɂ́A<code>false</code>
     */
    boolean handle(BindException bindException, Object value);
}
