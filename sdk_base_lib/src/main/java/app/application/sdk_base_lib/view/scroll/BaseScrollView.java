package app.application.sdk_base_lib.view.scroll;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class BaseScrollView extends ScrollView {
	private ScrollViewListener scrollViewListener = null;
	  
    public BaseScrollView(Context context) {
        super(context);  
    }  
  
    public BaseScrollView(Context context, AttributeSet attrs,
                          int defStyle) {
        super(context, attrs, defStyle);  
    }  
  
    public BaseScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);  
    }  
  
    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;  
    }  
  
    @Override  
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {  
        super.onScrollChanged(x, y, oldx, oldy);  
        if (scrollViewListener != null) {  
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);  
        }  
    }
}
