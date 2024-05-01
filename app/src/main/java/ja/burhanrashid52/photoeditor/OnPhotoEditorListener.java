package ja.burhanrashid52.photoeditor;

import android.view.View;


public interface OnPhotoEditorListener {

    void onEditTextChangeListener(View rootView, String text, int colorCode);

    void onAddViewListener(ViewType viewType, int numberOfAddedViews);

    void onRemoveViewListener(ViewType viewType, int numberOfAddedViews);

    void onStartViewChangeListener(ViewType viewType);

    void onStopViewChangeListener(ViewType viewType);
}