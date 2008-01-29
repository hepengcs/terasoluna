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

package jp.terasoluna.fw.batch.controlbreak;

import java.util.ArrayList;
import java.util.List;

import jp.terasoluna.fw.batch.openapi.ControlBreakHandler;
import jp.terasoluna.fw.batch.openapi.JobContext;

/**
 * �e�u���C�N�L�[���̃n���h���N���X����ێ�����N���X�B
 * 
 */
public class ControlBreakDefItem {

    /**
     * �u���C�N�L�[�B
     */
    private List<String> breakKey = new ArrayList<String>();
    
    /**
     * �R���g���[���u���C�N�n���h���B
     */
    private ControlBreakHandler<JobContext> controlBreakHandler = null;

    /**
     * �u���C�N�L�[���擾����B
     * 
     * @return �u���C�N�L�[ 
     */
    public List<String> getBreakKey() {
        return breakKey;
    }

    /**
     * �u���C�N�L�[��ݒ肷��B
     * 
     * @param breakkey �u���C�N�L�[ 
     */
    public void setBreakKey(List<String> breakkey) {
        this.breakKey = breakkey;
    }

    /**
     * �R���g���[���u���C�N�n���h�����擾����B
     * 
     * @return �R���g���[���u���C�N�n���h��
     */
    public ControlBreakHandler<JobContext> getControlBreakHandler() {
        return controlBreakHandler;
    }

    /**
     * �R���g���[���u���C�N�n���h����ݒ肷��B
     * 
     * @param controlBreakHandler �R���g���[���u���C�N�n���h��
     */
    public void setControlBreakHandler(
            ControlBreakHandler<JobContext> controlBreakHandler) {
        this.controlBreakHandler = controlBreakHandler;
    }
}
