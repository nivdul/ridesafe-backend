/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.ridesafe.backend.controllers

import io.ridesafe.backend.models.ProvidedData
import io.ridesafe.backend.services.DataService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

/**
 * Created by evoxmusic on 12/04/16.
 */
@RestController
@RequestMapping("/api/v1/datas")
class DatasController : MyController() {

    @Autowired
    var dataService: DataService? = null

    @RequestMapping(method = arrayOf(RequestMethod.POST))
    fun save(@RequestBody @Valid datas: List<ProvidedData>): Map<String, String> {
        res?.status = CREATED
        dataService?.create(datas)
        return mapOf("result" to "ok")
    }

}