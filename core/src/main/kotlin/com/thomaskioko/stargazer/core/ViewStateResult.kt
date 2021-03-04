package com.thomaskioko.stargazer.core

/**
 * Describes state of the view at any
 * point of time.
 */
sealed class ViewStateResult<ResultType> {

    /**
     * Describes success state of the UI with
     * [data] shown
     */
    data class Success<ResultType>(val data: ResultType) : ViewStateResult<ResultType>()

    /**
     * Describes loading state of the UI
     */
    class Loading<ResultType> : ViewStateResult<ResultType>() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            return true
        }

        override fun hashCode(): Int = javaClass.hashCode()
    }

    /**
     *  Describes error state of the UI
     */
    data class Error<ResultType>(val message: String = "") : ViewStateResult<ResultType>()

    companion object {
        /**
         * Creates [ViewStateResult] object with [Success] state and [data].
         */
        fun <ResultType> success(data: ResultType): ViewStateResult<ResultType> = Success(data)

        /**
         * Creates [ViewStateResult] object with [Loading] state to notify
         * the UI to showing loading.
         */
        fun <ResultType> loading(): ViewStateResult<ResultType> = Loading()

        /**
         * Creates [ViewStateResult] object with [Error] state and [message].
         */
        fun <ResultType> error(message: String): ViewStateResult<ResultType> = Error(message)
    }
}
