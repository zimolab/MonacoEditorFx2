# 项目说明
本项目旨在将开源的代码编辑器Monaco Editor（js）包装为一个JavaFx控件，最终达到在JavaFx项目中如同使用原生控件一样使用Monaco Editor的目标。
本项目实质上是一个JS/Kotlin的混合项目，底层使用webview来加载和渲染Monaco Editor实例，并通过一个使用JS/TS编写的中间层来封装Monaco Editor的API，并暴露给（或者说映射到）Kotlin层，使我们在Kotlin中能够如同调用原生函数一样调用这些API。

## core-library模块
核心功能的实现，对外提供各功能模块的API。

### src/main/resources/dist
这个目录下是web层面的Monaco Editor所需的静态资源，包括js、html、css、字体文件等等资源。 使用JS/TS编写的从web到kotlin的中间层代码也被打包到此目录下的monacofx.bundle.js文件中。
web层面的实现，特别是TS/JS编写的中间层代码在[这个仓库](https://github.com/zimolab/monacofx-js) 。

## api-demo模块
作为对core-library中的API的演示。同时也演示了在JavaFx中使用MonacoEditorFx控件的基本方式。

## jsobject-processor
使用KSP技术编写的注解处理器，用于在编译时生成一些必要的代码。

# 目前状态：WIP
## 目前已经完成
1. 对monaco.editor.IStandaloneEditor中大多数接口的映射
2. 对monaco.editor.ITextModel中大多数接口的映射
3. 对monaco.editor.IStandaloneEditor中大多数事件的桥接
4. 对monaco.editor.ITextModel中大多数事件的桥接
5. 对Editor和TextModel部分功能的演示

## 尚待完成（完善）的地方
1. 完善API测试与Demo
2. 代码整理、清理和统一化
3. 优化项目构架，减少重复代码，合理拆分代码、组织功能模块，使单个文件长度保存在合适尺度，便于后期维护
4. 完善文档
5. 其他

## 特别说明
1. 本项目依赖monaco editor、tornadofx、fastjson、ksp等第三方项目
2. 由于处在早期开发阶段，本项目中各个功能模块的API尚未完全定型，还处在一个随时可能发生变得的阶段，因此目前还无法保证向前、向后的兼容性

# 一些截图
![avatar](./screenshot/2021-08-05%20003725.png)
![avatar](./screenshot/2021-08-05%20003918.png)
![avatar](./screenshot/2021-08-05%20003956.png)
![avatar](./screenshot/2021-08-05%20004054.png)
![avatar](./screenshot/2021-08-05%20004148.png)
![avatar](./screenshot/2021-08-05%20004255.png)
![avatar](./screenshot/2021-08-05%20004331.png)