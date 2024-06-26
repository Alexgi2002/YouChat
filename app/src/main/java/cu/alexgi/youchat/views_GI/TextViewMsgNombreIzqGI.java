package cu.alexgi.youchat.views_GI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.vanniktech.emoji.EmojiTextView;

import cu.alexgi.youchat.YouChatApplication;

@SuppressLint("RestrictedApi")
public class TextViewMsgNombreIzqGI extends EmojiTextView {

    public TextViewMsgNombreIzqGI(Context context) {
        super(context);
        super.setTextColor(Color.parseColor(YouChatApplication.itemTemas.getColor_msg_izq_audio()));
    }

    public TextViewMsgNombreIzqGI(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setTextColor(Color.parseColor(YouChatApplication.itemTemas.getColor_msg_izq_audio()));
    }

    public TextViewMsgNombreIzqGI(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        super.setTextColor(Color.parseColor(YouChatApplication.itemTemas.getColor_msg_izq_audio()));
    }
}
