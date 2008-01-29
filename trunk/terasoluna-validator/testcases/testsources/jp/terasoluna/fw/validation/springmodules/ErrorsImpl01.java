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

package jp.terasoluna.fw.validation.springmodules;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

/**
 * Errors�����N���X
 * 
 */
public class ErrorsImpl01 implements Errors {

    /**
     * errors���X�g
     */
    private List<Object> errors = new ArrayList<Object>();
    
    /**
     * rejectValue�Ăяo���m�F�t���O
     */
    public boolean isRejectValue = false;
    
    /**
     * rejectValue�̈���field�̌ďo�m�F
     */
    public String field = null;
    
    /**
     * rejectValue�̈���errorCode�̌ďo�m�F
     */
    public String errorCode = null;
    
    /**
     * rejectValue�̈���errorArgs�̌ďo�m�F
     */
    public Object[] errorArgs = null;
    
    /**
     * rejectValue�̈���defaultMessage�̌ďo�m�F
     */
    public String defaultMessage = null;
    
    /**
     * �Ăяo���m�F����
     */
    public void rejectValue(@SuppressWarnings("hiding") String field, 
            @SuppressWarnings("hiding") String errorCode, 
            @SuppressWarnings("hiding") Object[] errorArgs,
            @SuppressWarnings("hiding") String defaultMessage) {
        this.isRejectValue = true;
        
        this.field = field;
        this.errorCode = errorCode;
        this.errorArgs = errorArgs;
        this.defaultMessage = defaultMessage;
    }
    
    /**
     * errors�T�C�Y��Ԃ�
     */
    public int getErrorCount() {
        return errors.size();
    }

    /**
     * errors��Ԃ�
     */
    public List getAllErrors() {
        return errors;
    }
    
    public String getObjectName() {
        return null;
    }

    public void setNestedPath(String nestedPath) {
    }

    public String getNestedPath() {
        return null;
    }

    public void pushNestedPath(String subPath) {
    }

    public void popNestedPath() throws IllegalStateException {
    }

    public void reject(@SuppressWarnings("hiding") String errorCode) {
    }

    public void reject(@SuppressWarnings("hiding") String errorCode, @SuppressWarnings("hiding") String defaultMessage) {
    }

    public void reject(String errorCode, Object[] errorArgs,
            String defaultMessage) {
    }

    public void rejectValue(String field, @SuppressWarnings("hiding") String errorCode) {
    }

    public void rejectValue(String field, String errorCode,
            String defaultMessage) {
    }

    public void addAllErrors(Errors errors) {
    }

    public boolean hasErrors() {
        return false;
    }

    public boolean hasGlobalErrors() {
        return false;
    }

    public int getGlobalErrorCount() {
        return 0;
    }

    public List getGlobalErrors() {
        return null;
    }

    public ObjectError getGlobalError() {
        return null;
    }

    public boolean hasFieldErrors(String field) {
        return false;
    }

    public int getFieldErrorCount(String field) {
        return 0;
    }

    public List getFieldErrors(String field) {
        return null;
    }

    public FieldError getFieldError(String field) {
        return null;
    }

    public Object getFieldValue(String field) {
        return null;
    }

    public FieldError getFieldError() {

        return null;
    }

    public int getFieldErrorCount() {

        return 0;
    }

    public List getFieldErrors() {

        return null;
    }

    public Class getFieldType(String field) {

        return null;
    }

    public boolean hasFieldErrors() {

        return false;
    }
}