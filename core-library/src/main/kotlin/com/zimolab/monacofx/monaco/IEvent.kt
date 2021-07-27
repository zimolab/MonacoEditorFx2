package com.zimolab.monacofx.monaco

interface IEvent<T> {
    operator fun invoke(listener: (e: T) -> Any, thisArg: Any): IDisposable
}