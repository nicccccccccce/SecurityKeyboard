package com.xisha.securitykeyboard.KeyBoard;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.xisha.securitykeyboard.MainActivity;
import com.xisha.securitykeyboard.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by user on 2016/12/7.
 */
public class PasswordKeyBoard extends FrameLayout {
    private Keyboard keyboard_all; // 全键盘对象
    private Keyboard keyboard_number; // 数字键盘对象
    private KeyBoardView mKeyboardView; // 呈现虚拟键盘的视图。它处理渲染键和检测按键和触摸动作
    public boolean isUpper = false;// 是否大写
    private List<Keyboard.Key> currentkey1;//英文
    private List<Keyboard.Key> currentkey2;//数字
    public static final int NUM = 0;
    public static final int EN = 1;
    private int currentType = 0;
    private ImageView iv_password;
    private List<CharSequence> plist = new ArrayList<>();
    private View contentView;


    private OnPasswordCompleteListener mOnPasswordCompleteListener;
    private static String TAG = "PasswordKeyBoard";
    private Context mContext;

    public PasswordKeyBoard(Context context) {
        super(context);
        this.mContext = context;
        init();
        initView();
    }

    public PasswordKeyBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
        initView();
    }

    private void init() {
        keyboard_all = new Keyboard(mContext, R.xml.p_qwerty);
        keyboard_number = new Keyboard(mContext, R.xml.p_symbols);
        currentkey1 = keyboard_all.getKeys();
        currentkey2 = keyboard_number.getKeys();
    }


    public void setOnPasswordCompleteListener(OnPasswordCompleteListener mOnPasswordCompleteListener) {
        this.mOnPasswordCompleteListener = mOnPasswordCompleteListener;
    }


    private void initView() {
        contentView = View.inflate(mContext,
                R.layout.password_keyboard, null);
        ImageView iv_password_back = (ImageView) contentView.findViewById(R.id.iv_password_back);
        iv_password_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
            }
        });
        iv_password = (ImageView) contentView.findViewById(R.id.iv_password);
        mKeyboardView = (KeyBoardView) contentView.findViewById(R.id.keyboard_view);
        this.mKeyboardView.setEnabled(true);
        this.mKeyboardView.setPreviewEnabled(true);
        this.mKeyboardView.setOnKeyboardActionListener(listener);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(contentView, params);
        hideKeyboard();
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
        }
    }

    private void setImg(int length) {
        switch (length) {
            case 1:
                iv_password.setImageResource(R.mipmap.mm01);
                break;
            case 2:
                iv_password.setImageResource(R.mipmap.mm02);
                break;
            case 3:
                iv_password.setImageResource(R.mipmap.mm03);
                break;
            case 4:
                iv_password.setImageResource(R.mipmap.mm04);
                break;
            case 5:
                iv_password.setImageResource(R.mipmap.mm05);
                break;
            case 6:
                iv_password.setImageResource(R.mipmap.mm06);
                break;
            default:
                iv_password.setImageResource(R.mipmap.mm00);
        }
    }


    private KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {

        @Override
        public void swipeUp() {
            // TODO Auto-generated method stub
            Log.e(TAG, "[swipeUp]");
        }

        @Override
        public void swipeRight() {
            // TODO Auto-generated method stub
            Log.e(TAG, "[swipeRight]");
        }

        @Override
        public void swipeLeft() {
            // TODO Auto-generated method stub
            Log.e(TAG, "[swipeLeft]");
        }

        @Override
        public void swipeDown() {
            // TODO Auto-generated method stub
            Log.e(TAG, "[swipeDown]");
        }

        @Override
        public void onText(CharSequence text) {
            // TODO Auto-generated method stub
            Log.e(TAG, "[text]" + text);
        }

        // 按键释放时触发方法
        @Override
        public void onRelease(int primaryCode) {
            // TODO Auto-generated method stub
            Log.e(TAG, "[onRelease]");
//            if (primaryCode == Keyboard.KEYCODE_CANCEL) {// 完成
//                hideKeyboard();
//            }
            if (currentType == NUM) {//当前是数字键盘
//                randomNumKey();
                mKeyboardView.setKeyboard(keyboard_number);
            }

        }

        // 按键点击时触发方法
        @Override
        public void onPress(int primaryCode) {
            // TODO Auto-generated method stub
            Log.e(TAG, "[onPress]");
        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            // TODO Auto-generated method stub
            try {
                Log.e(TAG, "[onKey]");

                switch (primaryCode) {
                    // 按键codes编码
                    //                case Keyboard.KEYCODE_CANCEL:// 完成
                    //                    hideKeyboard();
                    //                    break;
                    case Keyboard.KEYCODE_DELETE:// 删除
                        if (plist.size() > 0)
                            plist.remove(plist.size() - 1);
                        break;
                    case Keyboard.KEYCODE_SHIFT: // 大小写切换
                        changeKey();
                        mKeyboardView.setKeyboard(keyboard_all);
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
                        if (plist.size() < 7) {
                            if (currentType == NUM) {
                                if (primaryCode >= 48 && primaryCode <= 57)

                                    plist.add(getLable(currentkey2, primaryCode));
                                else
                                    plist.add(Character.toString((char) primaryCode));
                            } else if (currentType == EN) {
                                if (primaryCode >= 97 && primaryCode <= 122)
                                    plist.add(getLable(currentkey1, primaryCode));
                                else
                                    plist.add(Character.toString((char) primaryCode));
                            }
                        }
                        break;
                }
                if (plist.size() < 7)
                    setImg(plist.size());
                if (plist.size() == 6) {
                    //结束
                    hideKeyboard();
//                    if (popupWindow != null && popupWindow.isShowing()) {
//                        popupWindow.dismiss();
//                    }

                    if (mOnPasswordCompleteListener != null)
                        mOnPasswordCompleteListener.OnComplete(getPassword());

                }
            } catch (Exception e) {
                new RuntimeException(e.getMessage());
            }
//            Log.e(TAG, "[password]" + getPassword());
        }
    };

    protected String getPassword() {
        StringBuffer stringBuffer = new StringBuffer();
        for (CharSequence c : plist) {
            stringBuffer.append(c);
        }
        return stringBuffer.toString();
    }

    private CharSequence getLable(List<Keyboard.Key> list, int primaryCode) {
        for (Keyboard.Key k : list)
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
            Keyboard.Key key = currentkey1.get(i);
            if (key.codes[0] >= 97 && key.codes[0] <= 122)
//                key.label = strC[ks[key.codes[0] - 97]] + "";
                key.label = strC[key.codes[0] - 97] + "";
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
            Keyboard.Key key = currentkey2.get(i);
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

    /* *
      * 显示键盘
      */
    public void showKeyboard() {
        clearkey();
        setKeyboardInputType(PasswordKeyBoard.NUM);
        int visibility = getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            setVisibility(View.VISIBLE);
        }
    }

    /*  *
       * 隐藏键盘
       */
    public void hideKeyboard() {
        int visibility = getVisibility();
        if (visibility == View.VISIBLE) {
            setVisibility(View.INVISIBLE);
        }
    }

  /*  *
     * 判断当前键盘是否可见
     *
     * @return true为键盘可见，false为键盘不可见*/

    public boolean isShowKeyboard() {
        int visibility = getVisibility();
        if (visibility == View.VISIBLE) {
            return true;
        }
        return false;
    }

    private void clearkey() {
        plist.clear();
        setImg(0);
    }

    private void changeKey() {
        List<Keyboard.Key> keyList = keyboard_all.getKeys();
        if (isUpper) {
            // 如果为真表示当前为大写，需切换为小写
            isUpper = false;
            for (Keyboard.Key key : keyList) {
                if (key.label != null && isWord(key.label.toString())) {
                    key.label = key.label.toString().toLowerCase();
                    key.codes[0] = key.codes[0] + 32;
                }
            }
        } else {
            // 如果为假表示当前为小写，需切换为大写
            isUpper = true;
            for (Keyboard.Key key : keyList) {
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
        List<Keyboard.Key> keyList = keyboard_number.getKeys();
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
        if (id == -8) {// 转英文
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

    public interface OnPasswordCompleteListener {
        void OnComplete(String password);
    }
}
