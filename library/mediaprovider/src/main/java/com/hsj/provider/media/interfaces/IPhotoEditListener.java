/*******************************************************************************
 * Copyright (C) 2017.  HSJ.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed To in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 ******************************************************************************/
package com.hsj.provider.media.interfaces;

/**
 * @Author:HSJ
 * @E-mail:shengjunhu@foxmail.com
 * @Date:2017/9/14/17:04
 * @Class:IPhotoEditListener
 * @Description:图片编辑
 */
public interface IPhotoEditListener {

    void onPhotoEditStart();

    void onPhotoEditProgress(long currentPosition, long allPosition);

    void onPhotoEditException(Exception e);

    void onPhotoEditStop();

}
