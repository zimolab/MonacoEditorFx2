package com.zimolab.monacofx.monaco

interface CancellationToken {
    var isCancellationRequested: Boolean
    var onCancellationRequested: (listener: (e: Any) -> Any, thisArgs: Any, disposables: Array<IDisposable>) -> IDisposable
}