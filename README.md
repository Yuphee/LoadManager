# LoadManager
网络请求前,页面加载等待页(Loading Page)重载(Reload Page)空白页(Empty Page)一键集成

> 关于我，欢迎关注  
  邮箱：437220638@qq.com
 
# Screenshots
![image](/screenshots/video1.gif)

##Getting started
Add it in your root build.gradle at the end of repositories:
 ```java
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
 ```
 Add the dependency
  ```java
 dependencies {
        compile 'com.github.Yuphee:LoadManager:VERSION_CODE'
}
 ```
 replace VERSION_CODE with real version name such as released in [Here](https://github.com/Yuphee/LoadManager/releases)
 
 notice: Both have to write, or else can not be loaded successfully.

##版本 
- v1.0.1 增加页面显示过渡动画,可自定义
- v1.0.0 增加loading基础框架

##特性
- 支持Loading页的动画
- 一键切换Loading,Retry,Content,Empty页面
- 重载方法即可，方便集成开发

##原理说明
- 动态为主页面(内容页)加上Loading,Retry,Empty页面
- 支持Activity,Fragment,任何View（Fragment还有待完善）


##使用方法
 - 在Application中初始化布局
 ```java
 public class MApplication extends Application{

    private static MApplication instance = null;

    public static MApplication getInstance() {
        if (null == instance) {
            instance = new MApplication();
        }
        return instance;
    }

    public MApplication() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initLoadManager();
    }

    private void initLoadManager() {
        PrePageManager.initManagerLayout(R.layout.layout_load_view,R.layout.layout_reload_view,
                R.layout.empty_view);
    }
}
```
 - 在指定Activity或者Fragment或者View中注册Manager,前提是继承BaseActivity,你可以根据具体项目已有的BaseActivity或者BaseFragment模仿Demo里面的写法,加入到你的项目中
 
 ```java
  @Override
    protected void initPreManager() {
        super.initPreManager();
        mPrePageManager = PrePageManager.generate(this, new DefaultLoadListener() {
            @Override
            public void onRetryClick(View retryView) {
                // Do Something
                loadData();
            }

        });
    }
 ```
注: DefaultLoadListener已经经过另一层封装自定义了，原始的回调接口是OnLoadingAndRetryListener，在各个界面的显示前后都会有回调，如果想针对具体的某个Activity或者Fragment或者View进行布局，可以重载下面这些方法来替换Application中默认的布局

 ```java
@Override
    public int generateLoadingLayoutId() {
        return super.generateLoadingLayoutId();
    }

    @Override
    public int generateRetryLayoutId() {
        return super.generateRetryLayoutId();
    }

    @Override
    public int generateEmptyLayoutId() {
        return super.generateEmptyLayoutId();
    }
 ```
 
 - 通过</br>
mPrePageManager.showLoading(delay,anim)</br>
mPrePageManager.showContent(delay,anim)</br>
mPrePageManager.showRetry(delay,anim)</br>
去显示指定页面

##TODO
-集成更多动画,增加页面切换过渡效果支持,优化Fragment下集成,注：上面的gif中activity之间的过渡动画是transition framework的动画并非框架动画（最近兴趣刚研究的），框架的动画添加是在Loading结束时显示内容页或者重载页的时候出现的动画。可以自定义成你想要的效果，demo中只是简陋的平移和渐变，后续会加入一些好看的默认动画！谢谢大家支持！

