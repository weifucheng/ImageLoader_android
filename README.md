# ImageLoader_android
轻量级图片加载库
## 特点
低耦合，高内聚，结构清晰，轻量级，非常适合初中级开发者进行学习
欢迎大家积极发送pull request，有问题请Issues
## 问题
图片加载存在一定情况下的乱序（已经设置tag）
有些地方还需要优化
## Usage
配置不需要更改时：
例如listView等
```
//初始化ImageLoader
imageLoader=ImageLoader.Builder.getInstance(mContext).
                setLoadingPlaceholder(R.drawable.loading).
                setImageCache(DoubleCache.class).create();
 //需要加载的地方   
imageLoader.displayImage(url,imageView);
```
配置需要经常更改的时候：
比如只加载一次
```
//一句话加载
ImageLoader.Builder.getInstance(mContext).
                setLoadingPlaceholder(R.drawable.loading).
                setImageCache(DoubleCache.class).create().
                displayImage(url, imageView);
```
note:

```
setThreadCount(num)
```
该方法只在第一次Builder时有效，之后需要修改的话，需要先调用

```
imageLoader.stop()
```
然后会重新生效



