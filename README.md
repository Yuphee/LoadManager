# LoadManager
页面加载重载空白页一键集成

> 关于我，欢迎关注  
  邮箱：437220638@qq.com
 
# Screenshots
![image](/screenshots/video1.gif)

##版本 
- v1.1 增加页面显示过渡动画,可自定义
- v1.0 增加loading基础框架

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
        LoadingAndRetryManager.initManagerLayout(R.layout.layout_load_view,R.layout.layout_reload_view,
                R.layout.empty_view);
    }
}
```
 - 在指定Activity或者Fragment或者View中注册Manager,前提是继承BaseActivity,你可以根据具体项目已有的BaseActivity或者BaseFragment模仿Demo里面的写法,加入到你的项目中
 
 ```java
  @Override
  protected void initLoadManager() {
      super.initLoadManager();
      mLoadingAndRetryManager = LoadingAndRetryManager.generate(this, new DefaultLoadListener() {
          @Override
          public void onRetryClick(View retryView) {
              // Do Something
              loadData();
          }

      });
  }
 ```
 - 通过</br>
mLoadingAndRetryManager.showLoading(delay,anim)</br>
mLoadingAndRetryManager.showContent(delay,anim)</br>
mLoadingAndRetryManager.showRetry(delay,anim)</br>
去显示指定页面

##TODO
-集成更多动画,增加页面切换过渡效果支持,优化Fragment下集成,注：上面的gif中activity之间的过渡动画是transition framework的动画并非框架动画（最近兴刚研究的），框架的动画添加是在Loading结束时显示内容页或者重载页的时候出现的动画。可以自定义成你想要的效果，demo中只是简陋的平移和渐变，后续会加入一些好看的默认动画！谢谢大家支持！
##感谢
https://github.com/hongyangAndroid/LoadingAndRetryManager</br>
https://github.com/dinuscxj/LoadingDrawable
