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

package jp.terasoluna.fw.file.annotation;

/**
 * �����ϊ������C���^�t�F�[�X
 *
 * <p>
 * �w�肳�ꂽ�������ΏۂɁA�啶���ϊ��E�������ϊ��E���ϊ����s���B<br>
 * </p>
 *
 * 
 */
public interface StringConverter {

    /**
     * �������ϊ�����B
     * 
     * @param s
     *            �ϊ��O�̕�����
     * @return �ϊ���̕�����
     */
    String convert(String s);

}
