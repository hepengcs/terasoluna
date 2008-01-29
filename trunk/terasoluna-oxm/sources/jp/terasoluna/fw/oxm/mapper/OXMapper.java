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

package jp.terasoluna.fw.oxm.mapper;

import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;

import org.w3c.dom.Document;

/**
 * �I�u�W�F�N�g-XML�ϊ����s�����߂̃C���^�t�F�[�X�B
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.bind.XMLServletRequestDataBinder
 * 
 */
public interface OXMapper {
    /**
     * DOM�c���[����I�u�W�F�N�g�ɕϊ����郁�\�b�h�B
     * @param doc DOM�c���[
     * @param out �ϊ��Ώۂ̃I�u�W�F�N�g
     */
    void unmarshal(Document doc, Object out);
    
    /**
     * �X�g���[������XML�f�[�^�����o���A�I�u�W�F�N�g�ɕϊ�����B
     * @param reader XML�f�[�^�B�����Z�b�g���w�肳��Ă��Ȃ��ꍇ�A
     * VM�̃f�t�H���g�����Z�b�g���g�p�����B
     * @param out XML����ϊ����ꂽ�I�u�W�F�N�g�B
     */
    void unmarshal(Reader reader, Object out);
    
    /**
     * �X�g���[������XML�f�[�^�����o���A�I�u�W�F�N�g�ɕϊ�����B
     * @param is XML�f�[�^�B
     * @param argCharset �����Z�b�g�BNull�܂��͋󕶎��̏ꍇ�AUTF-8���w�肳���B
     * @param out XML����ϊ����ꂽ�I�u�W�F�N�g�B
     */
    void unmarshal(InputStream is, String argCharset, Object out);

    /**
     * �I�u�W�F�N�g����XML�ɕϊ����s���A�X�g���[���ɏo�͂��郁�\�b�h�B
     * @param in �ϊ��Ώۂ̃I�u�W�F�N�g
     * @param writer ���C�^�[
     */
    void marshal(Object in, Writer writer);
}
