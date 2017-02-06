package com.xisha.securitykeyboard;

import android.app.Activity;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;


import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.xisha.securitykeyboard.KeyBoard.KeyBoardInputUtils;
import com.xisha.securitykeyboard.KeyBoard.KeyBoardView;
import com.xisha.securitykeyboard.KeyBoard.PasswordKeyBoard;


public class MainActivity extends Activity implements View.OnClickListener {


//    private KeyboardView mKeyboardView;

    private EditText password_edit;
    private LinearLayout layout_keyboard;
    private PasswordKeyBoard mPasswordKeyBoard;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mKeyboardView = (KeyboardView) findViewById(R.id.keyboard_view);

//        password_edit = (EditText) findViewById(R.id.password_edit);

        mButton = (Button) findViewById(R.id.setting);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPasswordKeyBoard.showKeyboard();
            }
        });
        mPasswordKeyBoard = (PasswordKeyBoard) findViewById(R.id.password_keyboard);

    }

    public void init() {
    }
//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        // TODO Auto-generated method stub
//        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus) {
//            mPasswordKeyBoard.initPop();
//        }
//    }
///*

    public static int[] randomCommon(int min, int max, int n) {
        if (n > (max - min + 1) || max < min) {
            return null;
        }
        int[] result = new int[n];
        int count = 0;
        while (count < n) {
            int num = (int) (Math.random() * (max - min)) + min;
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if (num == result[j]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                result[count] = num;
                count++;
            }
        }
        return result;
    }

   /* static int[] fun(int[] num) {

        int size = num.length;
        int[] temp = new int[size];

        //下面转为ArrayList
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < size; i++)
            list.add(num[i]);

        Random r = new Random();
        int listlen = size;
        for (int i = 0; i < size; i++) {
            int n = r.nextInt(listlen--);
            temp[i] = list.get(n);
            list.remove(n);//取一个移除一个
        }

        return temp;
    }

    public int random() {
        Random rand = new Random();
        int i = rand.nextInt(); //int范围类的随机数
        i = rand.nextInt(9); //生成0-100以内的随机数

        i = (int) (Math.random() * 9);
        return i;
    }*/


    @Override
    public void onClick(View view) {
      /*  switch (view.getId()) {
            case R.id.iv_password:

                break;
        }*/
    }
}
