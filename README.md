# LoadManager
页面加载重载空白页一键集成

> 关于我，欢迎关注  
  邮箱：437220638@qq.com
 
# Screenshots
![image](/screenshots/video1.gif)

#特性
- 支持Loading页的动画
- 一键切换Loading,Retry,Content,Empty页面
- 重载方法即可，方便集成开发

#原理说明
- 动态为主页面(内容页)加上Loading,Retry,Empty页面
- 支持Activity,Fragment,任何View（Fragment还有待完善）


#使用方法
 - 在Application中初始化布局
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
 - 在指定Activity或者Fragment或者View中注册Manager
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
- 通过mLoadingAndRetryManager.showLoading(delay) mLoadingAndRetryManager.showContent(delay) mLoadingAndRetryManager.showRetry(delay);去显示指定页面

#TODO
-集成更多动画,增加页面切换过渡效果支持,优化Fragment下集成
