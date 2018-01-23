# Sample
主要用于新功能尝试、特性收集
### 1、Material Design
* 1、AppBarLayout
* 2、CollapsingToolbarLayout
* 3、CoordinatorLayout，
* 4、FloatingActionButton、
* 5、NavigationView
* 6、Snackbar、
* 7、TabLayout（使用于顶部当航）、BottomNavigationView(使用于底部当航)
* 8、TextInputLayout
* 9、NestedScrollView
* 10、Bottom Sheet
（AppBarLayout、CollapsingToolbarLayout、CoordinatorLayout，FloatingActionButton、NavigationView、Snackbar、TabLayout、TextInputLayout）

### 2、Theme
* 1、主题样式
* 2、通知栏样式（透明显图片、颜色值、白色状态栏黑色值）

计划：
1、RxJava2 使用
2、Retrofit2 使用
3、注解使用
4、权限封装

### res使用
* 1 矢量图使用：
```
    vector xml图片适配效果好，文件小
    ImageView 使用：app:srcCompat

```

* 2 animation-list
```
    animation-list 补间动画
    多张图片组成动画，可设置时间间隔，可设置重复或执行一次
    
```


### 原生API使用


### 原生控件使用
* 1 进度条 android.support.v4.widget.ContentLoadingProgressBar

### SDK新版本适配
- 0、SDK =19、21、23、25 屏幕、主题适配
```
    1、屏幕适配：
        mipmap-xhdpi 系统图标适配
        drawable-xhdpi app本地图片适配
    
    2、主题适配：
        values-19
        values-21
        values-23
    
    3、语言适配：
        中文 -> values/strings.xml
        英文 -> values-en/strings.xml
```

- 1、SDK >= 23 权限适配
```
    ContextCompat.checkSelfPermission() 
    ActivityCompat.requestPermissions() 
    ActivityCompat.OnRequestPermissionsResultCallback 
    ActivityCompat.shouldShowRequestPermissionRationale()

    a、在Application中
    
    b、在Activity中申请
    
    c、在Fragment中申请
    
```

- 2、SDK >= 24 文件适配
```
    res/xml/file_paths.xml
    <resources>
        <paths>
            <root-path name="root_path" path="" />
            <external-path name="external_path" path="."/>
        </paths>
    </resources>
    
    AndroidManifest.xml
    <provider
        android:name="android.support.v4.content.FileProvider"
        android:authorities="${applicationId}.FileProvider"
        android:exported="false"
        android:grantUriPermissions="true">
        <meta-data
            android:name="android.support.FILE_PROVIDER_PATHS"
            android:resource="@xml/file_paths" />
    </provider>

    Uri uri;
    String authority = mContext.getPackageName() + ".FileProvider";
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        uri = FileProvider.getUriForFile(mContext, authority, file);
    } else {
        uri = Uri.fromFile(file);
    }
```