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

package jp.terasoluna.fw.web.jndi;

/**
 * <p>JNDI�֘A�̃��[�e�B���e�B�C���^�t�F�[�X�B</p>
 * 
 * WebAP�R���e�i��JNDI���\�[�X���������߂ɂ͂��̃C���^�t�F�[�X����������
 * �K�v������B<br>
 * TERASOLUNA�̓f�t�H���g�����N���X�Ƃ���{@link DefaultJndiSupport}��񋟂���B
 * <br>
 * <br>
 * @see jp.terasoluna.fw.web.jndi.DefaultJndiSupport
 */
public interface JndiSupport {
    
    /**
     * DI�R���e�i����擾����JndiSupport�����N���X�̃L�[
     */
    public static final String JNDI_SUPPORT_KEY = "jndiSupport";
    
    /**
     * �w�肳�ꂽ�I�u�W�F�N�g���擾����B
     *
     * @param name �I�u�W�F�N�g��
     * @return �I�u�W�F�N�g
     */
    public Object lookup(String name);
    
    /**
     * ���O���I�u�W�F�N�g�Ƀo�C���h���āA
     * �����̃o�C���f�B���O���㏑������B
     *
     * @param name �I�u�W�F�N�g��
     * @param obj �o�C���h�����I�u�W�F�N�g
     */
    public void rebind(String name, Object obj);
    
    /**
     * �w�肳�ꂽ�I�u�W�F�N�g���A���o�C���h����B
     * 
     * @param name �I�u�W�F�N�g��
     */
    public void unbind(String name);
}
