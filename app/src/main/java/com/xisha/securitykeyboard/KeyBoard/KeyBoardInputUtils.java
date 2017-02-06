package com.xisha.securitykeyboard.KeyBoard;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.xisha.securitykeyboard.R;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * @description 自定义工具类，用于处理键盘事件
 * Created by DELL on 2016/3/7.
 */
public class KeyBoardInputUtils {

    private Context mContext;
    private EditText mEditText;
    private Keyboard keyboard_all; // 全键盘对象
    private Keyboard keyboard_number; // 数字键盘对象
    private Keyboard keyboard_sign; // 符号键盘对象
    private KeyBoardView mKeyboardView; // 呈现虚拟键盘的视图。它处理渲染键和检测按键和触摸动作
    private LinearLayout view;//
    //    public boolean isNum = false;// 是否数据键盘
    public boolean isUpper = false;// 是否大写
    private boolean isPsw = false;//是否是密码输入
    private List<Key> currentkey1;//英文
    private List<Key> currentkey2;//数字
    private List<Key> currentkey3;//符号
    private InputMethodManager mImm;
    public static final int NUM = 0;
    public static final int EN = 1;
    public static final int SIGN = 2;
    private int currentType = 0;

    /*   keyboard_double_input.xml 放于布局底部配合EditText调用，光标有点问题*/
    public KeyBoardInputUtils(Context mContext, LinearLayout view) {
        this.mContext = mContext;
        this.view = view;
        this.mKeyboardView = (KeyBoardView) view.findViewById(R.id.keyboard_view);
        keyboard_all = new Keyboard(mContext, R.xml.qwerty);
        keyboard_number = new Keyboard(mContext, R.xml.symbols);
        keyboard_sign = new Keyboard(mContext, R.xml.signs);
        currentkey1 = keyboard_all.getKeys();
        currentkey2 = keyboard_number.getKeys();
        currentkey3 = keyboard_sign.getKeys();
        this.mKeyboardView.setEnabled(true);
        this.mKeyboardView.setPreviewEnabled(true);
        this.mKeyboardView.setOnKeyboardActionListener(listener);
        mImm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        view.findViewById(R.id.tv_down).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
            }
        });

    }

    public void setKeyboardInputType(int type) {
//        this.isNum = isNum;
        currentType = type;
        if (NUM == type) {
//            randomNumKey();
            changeK2Order();
            this.mKeyboardView.setKeyboard(keyboard_number);
        } else if (EN == type) {
            changeK1OutOfOrder();
            this.mKeyboardView.setKeyboard(keyboard_all);
        } else {
            changeSignOutOfOrder();
            this.mKeyboardView.setKeyboard(keyboard_sign);
        }
    }

    public void setIsPassword(boolean isPsw) {
        this.isPsw = isPsw;
    }

    public void disableShowSoftInput(EditText editText) {
        if (android.os.Build.VERSION.SDK_INT <= 10) {
            editText.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method method;
            try {
                method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(editText, false);
            } catch (Exception e) {
                // TODO: handle exception
            }

            try {
                method = cls.getMethod("setSoftInputShownOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(editText, false);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }


    public void setInitEditText(final EditText mEditText) {
        this.mEditText = mEditText;
        disableShowSoftInput(mEditText);
    }

    public void initEditText(final Activity activity, final EditText mEditText, final int inputType, final boolean isPsw) {
        mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    setKeyboardInputType(inputType);
                    setIsPassword(isPsw);
                    setInitEditText(mEditText);
//                    if (keyBoardIsActive(activity))
                    hideInput(mContext, mEditText);
//                    if (!isShowKeyboard())
                    showKeyboard();
                    mEditText.requestFocus();
                } else {
                    mEditText.clearFocus();
//                    if (keyBoardIsActive(activity))
                    hideKeyboard();
                }
            }
        });
        mEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inputType = mEditText.getInputType();
//                if (keyBoardIsActive(activity))
                hideInput(mContext, mEditText);
//                if (!isShowKeyboard())
                showKeyboard();
                mEditText.requestFocus();
                mEditText.setInputType(inputType);
                return false;
            }
        });
    }


    public boolean keyBoardIsActive(Activity context) {
        //判断隐藏软键盘是否弹出
        if (context.getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED)

        {
            //隐藏软键盘
//            context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            return true;

        }
        return false;
    }

    public void showInput(View view, Context context) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 强制隐藏输入法键盘
     */
    public void hideInput(Context context, View view) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {

        @Override
        public void swipeUp() {
            // TODO Auto-generated method stub

        }

        @Override
        public void swipeRight() {
            // TODO Auto-generated method stub

        }

        @Override
        public void swipeLeft() {
            // TODO Auto-generated method stub

        }

        @Override
        public void swipeDown() {
            // TODO Auto-generated method stub

        }

        @Override
        public void onText(CharSequence text) {
            // TODO Auto-generated method stub

        }

        // 按键释放时触发方法
        @Override
        public void onRelease(int primaryCode) {
            // TODO Auto-generated method stub
            if (primaryCode == Keyboard.KEYCODE_CANCEL) {// 完成
                hideKeyboard();
            }
            if (currentType == NUM && isPsw) {//当前是数字键盘
//                randomNumKey();
                mKeyboardView.setKeyboard(keyboard_number);
            }

        }

        // 按键点击时触发方法
        @Override
        public void onPress(int primaryCode) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            // TODO Auto-generated method stub
            Editable editable = mEditText.getText();
            int start = mEditText.getSelectionStart();
            switch (primaryCode) {
                // 按键codes编码
                case Keyboard.KEYCODE_CANCEL:// 完成
                    hideKeyboard();
                    break;
                case Keyboard.KEYCODE_DELETE:// 删除
                    if (editable != null && editable.length() > 0) {
                        if (start > 0) {
                            editable.delete(start - 1, start);// 开始，结束位置
                        }
                    }
                    break;
                case Keyboard.KEYCODE_SHIFT: // 大小写切换
                    changeKey();
                    mKeyboardView.setKeyboard(keyboard_all);
                    break;
              /*  case Keyboard.KEYCODE_MODE_CHANGE:
                    changeKeyTONum();
                    break;*/
                case -6:
                    //切换到字符
//                    changeKeyTOSign();
                    changeKeyTONum(-6);
                    break;
                case -8:
                    //切换到字母
//                    changeKeyTOSign();
                    changeKeyTONum(-8);
                    break;
                case -9:
                    //切换到数字
//                    changeKeyTOSign();
                    changeKeyTONum(-9);
                    break;
                default:
//                    editable.insert(start, Character.toString((char) primaryCode));
                    if (currentType == NUM) {
                        if (primaryCode >= 48 && primaryCode <= 57)
                            editable.insert(start, getLable(currentkey2, primaryCode));
                        else
                            editable.insert(start, Character.toString((char) primaryCode));
//                    Log.e("fff", "----" + getLable(currentkey2, primaryCode));
                    } else if (currentType == EN) {
                        if (primaryCode >= 97 && primaryCode <= 122)
                            editable.insert(start, getLable(currentkey1, primaryCode));
                        else
                            editable.insert(start, Character.toString((char) primaryCode));
//                    Log.e("fff", "----" + getLable(currentkey1, primaryCode));
                    } else {
                        if (primaryCode >= 201 && primaryCode <= 232)
                            editable.insert(start, getLable(currentkey3, primaryCode));
                        else
                            editable.insert(start, Character.toString((char) primaryCode));
//                        Log.e("fff", "----" + getLable(currentkey3, primaryCode));
                    }
                    break;
            }
        }
    };

    public void hideSoftInputFromWindow(View view) {
        mImm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
    }

    /**
     * 隐藏系统键盘 Edittext不显示系统键盘；并且要有光标； 4.0以上TYPE_NULL，不显示系统键盘，但是光标也没了；
     */
    public void hideSoftInputMethod(EditText et) {
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        String methodName = null;
        if (currentVersion >= 16) {
            // 4.2
            methodName = "setShowSoftInputOnFocus";
            // 19 setShowSoftInputOnFocus
        } else if (currentVersion >= 14) {
            // 4.0
            methodName = "setSoftInputShownOnFocus";
        }

        if (methodName == null) {
            et.setInputType(InputType.TYPE_NULL);
        } else {
            Class<TextView> cls = TextView.class;
            Method setShowSoftInputOnFocus;
            try {
                setShowSoftInputOnFocus = cls.getMethod(methodName, boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(this, false);
            } catch (Exception e) {
                et.setInputType(InputType.TYPE_NULL);
                e.printStackTrace();
            }
        }
    }

    private CharSequence getLable(List<Key> list, int primaryCode) {
        for (Key k : list)
            if (k.codes[0] == primaryCode)
                return k.label;
        return "";
    }


    /**
     * 英文键盘顺序
     */
    private void changeK1OutOfOrder() {

//        int ks[] = get(26);
//        int ks2[] = fun(ks);
        String wordStr = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z";
        String[] strC = wordStr.split(",");//97-122
        for (int i = 0; i < currentkey1.size(); i++) {
            Key key = currentkey1.get(i);
            if (key.codes[0] >= 97 && key.codes[0] <= 122)
//                key.label = strC[ks[key.codes[0] - 97]] + "";
                key.label = strC[key.codes[0] - 97] + "";
        }
    }


    /* 字符键盘顺序*/
    private void changeSignOutOfOrder() {

//        int ks[] = get(32);
//        int ks2[] = fun(ks);
        String wordStr = "~z`z!z@z#z$z%z^z&z*z(z)z_z-z+z=z{z}z[z]z|z\\\\z:z;z\"z\'z<z,z>z.z?z/";
        String[] strC = wordStr.split("z");//201-232
        for (int i = 0; i < currentkey3.size(); i++) {
            Key key = currentkey3.get(i);
            if (key.codes[0] >= 201 && key.codes[0] <= 232)
//                key.label = strC[ks2[key.codes[0] - 201]] + "";
                key.label = strC[key.codes[0] - 201] + "";
        }
    }


    private int[] get(int size) {
        int ch[] = new int[size];
        for (int i = 0; i < ch.length; i++) {
            ch[i] = i;
        }
        return ch;
    }

    /**
     * 数字键盘顺序
     */
    private void changeK2Order() {
        int ks[] = get(10);
        int ks2[] = fun(ks);
        for (int i = 0; i < currentkey2.size(); i++) {
            Key key = currentkey2.get(i);
            if (key.codes[0] >= 48 && key.codes[0] <= 57)
                key.label = ks2[key.codes[0] - 48] + "";
        }

    }

    //数组乱序
    static int[] fun(int[] num) {

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

    /**
     * 清空输入框所有内容
     */
    public void clearEditTextContent() {
        if (mEditText != null) {
            Editable editable = mEditText.getText();
            int start = mEditText.getSelectionStart();
            if (start > 0) {
                editable.clear();
            }
        }

    }

    /**
     * 显示键盘
     */
    public void showKeyboard() {
        int visibility = view.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 隐藏键盘
     */
    public void hideKeyboard() {
        int visibility = view.getVisibility();
        if (visibility == View.VISIBLE) {
            view.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 判断当前键盘是否可见
     *
     * @return true为键盘可见，false为键盘不可见
     */
    public boolean isShowKeyboard() {
        int visibility = mKeyboardView.getVisibility();
        if (visibility == View.VISIBLE) {
            return true;
        }
        return false;
    }

    /**
     * 切换键盘大小写字母 按照ascii码表可知，小写字母 = 大写字母+32;
     */
    private void changeKey() {
        List<Key> keyList = keyboard_all.getKeys();
        if (isUpper) {
            // 如果为真表示当前为大写，需切换为小写
            isUpper = false;
            for (Key key : keyList) {
                if (key.label != null && isWord(key.label.toString())) {
                    key.label = key.label.toString().toLowerCase();
                    key.codes[0] = key.codes[0] + 32;
                }
            }
        } else {
            // 如果为假表示当前为小写，需切换为大写
            isUpper = true;
            for (Key key : keyList) {
                if (key.label != null && isWord(key.label.toString())) {
                    key.label = key.label.toString().toUpperCase();
                    key.codes[0] = key.codes[0] - 32;
                }
            }
        }
    }

    /**
     * 随机数字键盘,随机键盘LABEL中不能存在图片，否则在随机换位过程中会报错
     */
    private void randomNumKey() {
        List<Key> keyList = keyboard_number.getKeys();
        int size = keyList.size();
        for (int i = 0; i < size - 2; i++) {
            int random_a = (int) (Math.random() * (size));
            int random_b = (int) (Math.random() * (size));

            int code = keyList.get(random_a).codes[0];
            int code2 = keyList.get(random_b).codes[0];
            if (code == -2 || code == -5 || code2 == -2 || code2 == -5) {
                continue;
            } else {
                CharSequence label = keyList.get(random_a).label;
                keyList.get(random_a).codes[0] = keyList.get(random_b).codes[0];
                keyList.get(random_a).label = keyList.get(random_b).label;
                keyList.get(random_b).codes[0] = code;
                keyList.get(random_b).label = label;
            }
        }
    }

    /**
     * 数字键盘切换
     */
    private void changeKeyTONum(int id) {
        if (id == -6) { // 转字符
            currentType = SIGN;
            changeSignOutOfOrder();
            mKeyboardView.setKeyboard(keyboard_sign);
        } else if (id == -8) {// 转英文
            currentType = EN;
            changeK1OutOfOrder();
            mKeyboardView.setKeyboard(keyboard_all);

        } else {
            //-9 转数字
            currentType = NUM;
            changeK2Order();
            mKeyboardView.setKeyboard(keyboard_number);
        }
    }

    /**
     * 判断是否为字母
     *
     * @param str 需判断的字符串
     */
    private boolean isWord(String str) {
        String wordStr = "abcdefghijklmnopqrstuvwxyz";
        if (wordStr.indexOf(str.toLowerCase()) > -1) {
            return true;
        }
        return false;
    }
}
