package com.xinran.testviewgroup;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.ll_bottom)
    LinearLayout llBottom;
    @Bind(R.id.windows_layout)
    QxCustomViewGroup windowsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.ll_bottom, R.id.windows_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_bottom:
                popWindowShow(view);
                break;
            case R.id.windows_layout:
                break;
        }
    }

    private void popWindowShow(View view) {
      int [] location=new int[2] ;
        /**
         * <p>Computes the coordinates of this view on the screen. The argument
         * must be an array of two integers. After the method returns, the array
         * contains the x and y location in that order.</p>
         *
         * @param location an array of two integers in which to hold the coordinates
         */
        view.getLocationOnScreen(location);
        View popView= View.inflate(this,R.layout.pop_window_layout, null);
        PopupWindow popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e0000000")));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        int height=popView.getMeasuredHeight();
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,location[0],location[1]-height);

    }
}
