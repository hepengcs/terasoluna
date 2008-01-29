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

package jp.terasoluna.fw.web.rich.springmvc.servlet.handler;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
/**
 * �G���[�������X�|���X�w�b�_��ModelAndView�ɔ��f���邽�߂̃C���^�t�F�[�X�B
 * <p>
 * �ێ��������e���A���X�|���X�w�b�_�y��ModelAndView�ɐݒ肷��@�\���񋟂���B
 * </p>
 * 
 */
public interface ExceptionResolveDelegator {
    
    /**
     * ��O�̌^�ƃr���[���i���G���[���j�𑮐��Ɋi�[����B
     * @param mappingKey  ��O�̌^
     * @param mappingValues �r���[���ƃG���[���
     * @param params �ʂ̏��̃L�[�ƒl���i�[����Map
     */
    void initMapping (String mappingKey, Object mappingValues, 
            Map<String, String> params);
    
    /**
     * ���X�|���X�w�b�_�ɃG���[����ݒ肷��B
     * @param response HTTP���X�|���X
     */
    void setHeader(HttpServletResponse response);
    
    /**
     * ModelAndView�ɃG���[����ݒ肷��B
     * @param mv ModelAndView ModelAndView�I�u�W�F�N�g
     */
    void addObjectToModel(ModelAndView mv);
    
    /**
     * �r���[�����擾����B
     * @return �r���[��
     */    
    String getViewName();

}
