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

package jp.terasoluna.fw.web.rich.springmvc.controller;

import org.springframework.validation.BindException;

/**
 * BindException�̃X�^�u�B�G���[�������ԁB
 *
 */
@SuppressWarnings("serial")
public class TerasolunaController_BindExceptionStub01 extends BindException {

    /**
     * �f�t�H���g�R���X�g���N�^�B
     */
    public TerasolunaController_BindExceptionStub01() {
        super(new Object(), "");
    }

    /**
     * �G���[����B
     */
    @Override
    public boolean hasErrors() {
        return true;
    }

}
