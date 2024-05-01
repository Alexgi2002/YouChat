package cu.alexgi.youchat.views_GI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import cu.alexgi.youchat.YouChatApplication;

@SuppressLint("RestrictedApi")
public class ImageViewBarChatGI extends AppCompatImageView {

    public ImageViewBarChatGI(Context context) {
        super(context);
        super.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor(YouChatApplication.itemTemas.getFont_barchat())));
    }

    public ImageViewBarChatGI(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor(YouChatApplication.itemTemas.getFont_barchat())));
    }


    public ImageViewBarChatGI(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        super.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor(YouChatApplication.itemTemas.getFont_barchat())));
    }

}
