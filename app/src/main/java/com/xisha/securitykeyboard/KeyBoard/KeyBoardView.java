package com.xisha.securitykeyboard.KeyBoard;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

/**
 * @description 自定义keyboardView控件
 * Created by DELL on 2016/3/7.
 */
public class KeyBoardView extends KeyboardView {

    private Keyboard currentKeyboard;
    private List<Key> keys = new ArrayList<Key>();
    private int lastKeyIndex = -1;
    private Key focusedKey;

    public KeyBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public KeyBoardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

//	@Override
//	public void onDraw(Canvas canvas) {
//		super.onDraw(canvas);
//		currentKeyboard = this.getKeyboard();
//		keys = currentKeyboard.getKeys();
//		focusedKey = keys.get(lastKeyIndex);
//		String label = focusedKey.label == null ? null : adjustCase(focusedKey.label).toString();
//		Drawable keyBackground = getResources().getDrawable(R.drawable.shape_keyboard_press);
//
//		final Rect bounds = keyBackground.getBounds();
//		if (focusedKey.width != bounds.right || focusedKey.height != bounds.bottom) {
//			keyBackground.setBounds(0, 0, focusedKey.width, focusedKey.height);
//		}
//
//		canvas.translate(focusedKey.x, focusedKey.y);
//
//		keyBackground.draw(canvas);
//
//		Paint paint = new Paint();
//
//		if (label != null) {
//
//			paint.setTextSize((float) 30); // 设置文字大小
//
//			if (label.toString().length() > 1) {
//				paint.setTextSize((float) 21); // 设置文字大小
//				paint.setTypeface(Typeface.DEFAULT_BOLD);
//			}
//
//			paint.setTextAlign(Align.CENTER); // 设置文字水平居中
//
//			paint.setColor(Color.WHITE); // 设置文字颜色
//
//			FontMetrics fontMetrics = paint.getFontMetrics();
//			// 计算文字高度
//			float fontHeight = fontMetrics.bottom - fontMetrics.top;
//
//			// 计算文字baseline
//			float textBaseY = focusedKey.height - (focusedKey.height - fontHeight) / 2 - fontMetrics.bottom;
//
//			// 重绘文字
//			canvas.drawText(label, (focusedKey.width) / 2, textBaseY, paint);
//		}
//	}

    private CharSequence adjustCase(CharSequence label) {
        if (currentKeyboard.isShifted() && label != null && label.length() < 3
                && Character.isLowerCase(label.charAt(0))) {
            label = label.toString().toUpperCase();
        }
        return label;
    }

    public int getLastKeyIndex() {
        return lastKeyIndex;
    }

    public void setLastKeyIndex(int index) {
        this.lastKeyIndex = index;
    }

    @Override
    protected boolean onLongPress(Keyboard.Key popupKey) {
        if (popupKey.codes[0] == Keyboard.KEYCODE_DELETE) {
//            KeyBoardInputUtils.mDefineKeyboardUtil.clearEditTextContent();
            // 可使用OnKeyboardActionListener中的各种方法实现该功能
            // getOnKeyboardActionListener().onKey(Keyboard.KEYCODE_DELETE,
            // null);
        }
        return super.onLongPress(popupKey);
    }
}
