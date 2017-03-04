#圆形进度条
Android中自定义的带进度值的圆形进度条

![Alt text](./jdfw.gif)

使用方法：
在最外面的布局中加入
```xml
xmlns:app ="http://schemas.android.com/apk/res-auto"
```
```java
<main.zhaocd.com.circleprogressview.CircleProgressView
        android:id="@+id/my_view"
        android:layout_width="600dp"
        android:layout_height="600dp"
        app:roundWidth="80dp"
        app:roundColor="#7f8d9c"
        app:roundProgressColor="#07ce7e"
        app:textColor="#e21e17"
        app:textSize="40dp"
        />
```
这样就可以设置它的自定义属性了。不设置的话会只用默认的设置

使用起来是不是很方便~~~
