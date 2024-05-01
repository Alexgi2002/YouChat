package com.yanzhenjie.album.app;

import android.app.Activity;
import android.content.res.Configuration;
import android.view.View;
import android.widget.CompoundButton;

import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFolder;
import com.yanzhenjie.album.api.widget.Widget;
import com.yanzhenjie.album.mvp.BasePresenter;
import com.yanzhenjie.album.mvp.BaseView;

import java.util.List;

public final class Contract {

    public interface AlbumPresenter extends BasePresenter {

        /**
         * Click the folder switch.
         */
        void clickFolderSwitch();

        /**
         * Click camera.
         */
        void clickCamera(View v);

        /**
         * Try to check item.
         *
         * @param button view.
         * @param position position of item.
         */
        void tryCheckItem(CompoundButton button, int position);

        /**
         * Try to preview item.
         *
         * @param position position of item.
         */
        void tryPreviewItem(int position);

        /**
         * Preview the checked items.
         */
        void tryPreviewChecked();

        /**
         * Complete.
         */
        void complete();

    }

    public static abstract class AlbumView extends BaseView<AlbumPresenter> {

        public AlbumView(Activity activity, AlbumPresenter presenter) {
            super(activity, presenter);
        }

        public abstract void setupViews(Widget widget, int column, boolean hasCamera, int choiceMode);

        /**
         * Set the loading visibility.
         *
         * @param display true is displayed, otherwise it is not displayed.
         */
        public abstract void setLoadingDisplay(boolean display);

        /**
         * Should be re-layout.
         *
         * @param newConfig config.
         */
        public abstract void onConfigurationChanged(Configuration newConfig);

        /**
         * Set the complete menu visibility.
         *
         * @param display true is displayed, otherwise it is not displayed.
         */
        public abstract void setCompleteDisplay(boolean display);

        /**
         * Bind folder.
         *
         * @param albumFolder {@link AlbumFolder}.
         */
        public abstract void bindAlbumFolder(AlbumFolder albumFolder);

        /**
         * Notify item was inserted.
         *
         * @param position position of item.
         */
        public abstract void notifyInsertItem(int position);

        /**
         * Notify item was changed.
         *
         * @param position position of item.
         */
        public abstract void notifyItem(int position);

        /**
         * Set checked count.
         *
         * @param count the number of items checked.
         */
        public abstract void setCheckedCount(int count);
    }

    public interface NullPresenter extends BasePresenter {

        /**
         * Take a picture.
         */
        void takePicture();

        /**
         * Take a video.
         */
        void takeVideo();
    }

    public static abstract class NullView extends BaseView<NullPresenter> {

        public NullView(Activity activity, NullPresenter presenter) {
            super(activity, presenter);
        }

        /**
         * Set some properties of the view.
         *
         * @param widget {@link Widget}.
         */
        public abstract void setupViews(Widget widget);

        /**
         * Set the message of page.
         *
         * @param message message.
         */
        public abstract void setMessage(int message);

        /**
         * Set the button visibility.
         *
         * @param display true is displayed, otherwise it is not displayed.
         */
        public abstract void setMakeImageDisplay(boolean display);

        /**
         * Set the button visibility.
         *
         * @param display true is displayed, otherwise it is not displayed.
         */
        public abstract void setMakeVideoDisplay(boolean display);
    }

    public interface GalleryPresenter extends BasePresenter {

        void clickItem(int position);

        void longClickItem(int position);

        /**
         * Set the current position of item .
         */
        void onCurrentChanged(int position);

        /**
         * Try to check the current item.
         */
        void onCheckedChanged();

        /**
         * Complete.
         */
        void complete();
    }

    public static abstract class GalleryView<Data> extends BaseView<GalleryPresenter> {

        public GalleryView(Activity activity, GalleryPresenter presenter) {
            super(activity, presenter);
        }

        /**
         * Set some properties of the view.
         *
         * @param widget {@link Widget}.
         * @param checkable show the checkbox.
         */
        public abstract void setupViews(Widget widget, boolean checkable);

        /**
         * Bind data.
         *
         * @param dataList data.
         */
        public abstract void bindData(List<Data> dataList);

        /**
         * Set the position of the item to be displayed.
         *
         * @param position position.
         */
        public abstract void setCurrentItem(int position);

        /**
         * Set duration visibility.
         *
         * @param display true is displayed, otherwise it is not displayed.
         */
        public abstract void setDurationDisplay(boolean display);

        /**
         * Set duration.
         *
         * @param duration duration.
         */
        public abstract void setDuration(String duration);

        /**
         * Changes the checked state of this button.
         *
         * @param checked true to check the button, false to uncheck it.
         */
        public abstract void setChecked(boolean checked);

        /**
         * Set bottom visibility.
         *
         * @param display true is displayed, otherwise it is not displayed.
         */
        public abstract void setBottomDisplay(boolean display);

        /**
         * Set layer visibility.
         *
         * @param display true is displayed, otherwise it is not displayed.
         */
        public abstract void setLayerDisplay(boolean display);

        /**
         * Set the complete button text.
         *
         * @param text text.
         */
        public abstract void setCompleteText(String text);
    }

}