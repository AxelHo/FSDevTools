/*
 *
 * *********************************************************************
 * fsdevtools
 * %%
 * Copyright (C) 2016 e-Spirit AG
 * %%
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
 * *********************************************************************
 *
 */

package com.espirit.moddev.cli.results;

import com.espirit.moddev.cli.api.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * General {@link com.espirit.moddev.cli.api.result.Result} implementation
 * @param <CUSTOM_RESULT_TYPE> Type of the result produced by the command
 * @author e-Spirit AG
 */
public class SimpleResult<CUSTOM_RESULT_TYPE> implements Result<CUSTOM_RESULT_TYPE> {

    /**
     * {@link org.slf4j.Logger} used by this class.
     */
    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /**
     * Result produced by the command.
     */
    protected final CUSTOM_RESULT_TYPE result;

    /**
     * Exception produced by the command.
     */
    protected Exception exception = null;

    /**
     * Creates a new instance using an empty command result.
     */
    public SimpleResult() {
        this((CUSTOM_RESULT_TYPE) null);
    }

    /**
     * Creates a new instance using the given command result.
     *
     * @param result Result produced by the command
     */
    public SimpleResult(CUSTOM_RESULT_TYPE result) {
        this.result = result;
    }

    /**
     * Creates a new error result using the given exception.
     * @param exception Exception produced by the command
     */
    public SimpleResult(Exception exception) {
        this.result = null;
        this.exception = exception;
    }

    @Override
    public boolean isError() {
        return exception != null;
    }

    @Override
    public Exception getError() {
        return exception;
    }

    @Override
    public void log() {
        if(isError()) {
            LOGGER.error("Exception occurred while executing command", exception);
        } else {
            if(result != null) {
                LOGGER.info("Result available: " +  result.getClass());
            } else {
                LOGGER.info("Result available");
            }
        }
    }

    @Override
    public CUSTOM_RESULT_TYPE get() {
        return result;
    }
}