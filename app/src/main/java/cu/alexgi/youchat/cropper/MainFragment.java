// "Therefore those skilled at the unorthodox
// are infinite as heaven and earth,
// inexhaustible as the great rivers.
// When they come to an end,
// they begin again,
// like the days and months;
// they die and are reborn,
// like the four seasons."
//
// - Sun Tsu,
// "The Art of War"

package cu.alexgi.youchat.cropper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import cu.alexgi.youchat.R;
import cu.alexgi.youchat.Utils;

/** The fragment that will show the Image Cropping UI by requested preset. */
public final class MainFragment extends Fragment
    implements CropImageView.OnSetImageUriCompleteListener,
        CropImageView.OnCropImageCompleteListener {

  // region: Fields and Consts

  private CropDemoPreset mDemoPreset;

  private CropImageView mCropImageView;
  private String rutaImg;
  // endregion

  /** Returns a new instance of this fragment for the given section number. */
  public static MainFragment newInstance(CropDemoPreset demoPreset) {
    MainFragment fragment = new MainFragment();
    Bundle args = new Bundle();
    args.putString("DEMO_PRESET", demoPreset.name());
    fragment.setArguments(args);
    return fragment;
  }

  /** Set the image to show for cropping. */
  public void setImageUri(Uri imageUri) {
    mCropImageView.setImageUriAsync(imageUri);
    //        CropImage.activity(imageUri)
    //                .start(getContext(), this);
  }

/*  *//** Set the options of the crop image view to the given values. *//*
  public void setCropImageViewOptions(CropImageViewOptions options) {
    mCropImageView.setScaleType(options.scaleType);
    mCropImageView.setCropShape(options.cropShape);
    mCropImageView.setGuidelines(options.guidelines);
    mCropImageView.setAspectRatio(options.aspectRatio.first, options.aspectRatio.second);
    mCropImageView.setFixedAspectRatio(options.fixAspectRatio);
    mCropImageView.setMultiTouchEnabled(options.multitouch);
    mCropImageView.setShowCropOverlay(options.showCropOverlay);
    mCropImageView.setShowProgressBar(options.showProgressBar);
    mCropImageView.setAutoZoomEnabled(options.autoZoomEnabled);
    mCropImageView.setMaxZoom(options.maxZoomLevel);
    mCropImageView.setFlippedHorizontally(options.flipHorizontally);
    mCropImageView.setFlippedVertically(options.flipVertically);
  }*/

/*  *//** Set the initial rectangle to use. *//*
  public void setInitialCropRect() {
    mCropImageView.setCropRect(new Rect(100, 300, 500, 1200));
  }

  *//** Reset crop window to initial rectangle. *//*
  public void resetCropRect() {
    mCropImageView.resetCropRect();
  }*/

  public void updateCurrentCropViewOptions() {
    CropImageViewOptions options = new CropImageViewOptions();
    options.scaleType = mCropImageView.getScaleType();
    options.cropShape = mCropImageView.getCropShape();
    options.guidelines = mCropImageView.getGuidelines();
    options.aspectRatio = mCropImageView.getAspectRatio();
    options.fixAspectRatio = mCropImageView.isFixAspectRatio();
    options.showCropOverlay = mCropImageView.isShowCropOverlay();
    options.showProgressBar = mCropImageView.isShowProgressBar();
    options.autoZoomEnabled = mCropImageView.isAutoZoomEnabled();
    options.maxZoomLevel = mCropImageView.getMaxZoom();
    options.flipHorizontally = mCropImageView.isFlippedHorizontally();
    options.flipVertically = mCropImageView.isFlippedVertically();
    ((CropActivity) getActivity()).setCurrentOptions(options);
  }

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_main_rect, container, false);
    /*switch (mDemoPreset) {
      case RECT:
        rootView = inflater.inflate(R.layout.fragment_main_rect, container, false);
        break;
      case CUSTOM:
        rootView = inflater.inflate(R.layout.fragment_main_rect, container, false);
        break;
      default:
        throw new IllegalStateException("Unknown preset: " + mDemoPreset);
    }*/
    return rootView;
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    mCropImageView = view.findViewById(R.id.cropImageView);
    mCropImageView.setOnSetImageUriCompleteListener(this);
    mCropImageView.setOnCropImageCompleteListener(this);

    updateCurrentCropViewOptions();

    rutaImg=((CropActivity) getActivity()).getRutaImg();
    mCropImageView.setImageUriAsync(Uri.fromFile(new File(rutaImg)));
    /*if (savedInstanceState == null) {
      if (mDemoPreset == CropDemoPreset.SCALE_CENTER_INSIDE) {
        mCropImageView.setImageResource(R.drawable.cat_small);
      } else {
        mCropImageView.setImageResource(R.drawable.cat);
      }
    }*/
  }

  public void cropper() { mCropImageView.getCroppedImageAsync(); }
  public void rotate() { mCropImageView.rotateImage(90); }
  public void flipHorizontally() { mCropImageView.flipImageHorizontally(); }
  public void flipVertically() { mCropImageView.flipImageVertically(); }

  /*@Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.main_action_crop) {
      mCropImageView.getCroppedImageAsync();
      return true;
    } else if (item.getItemId() == R.id.main_action_rotate) {
      mCropImageView.rotateImage(90);
      return true;
    } else if (item.getItemId() == R.id.main_action_flip_horizontally) {
      mCropImageView.flipImageHorizontally();
      return true;
    } else if (item.getItemId() == R.id.main_action_flip_vertically) {
      mCropImageView.flipImageVertically();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }*/

  @Override
  public void onAttach (Activity activity) {
    super.onAttach(activity);
    mDemoPreset = CropDemoPreset.valueOf(getArguments().getString("DEMO_PRESET"));
    ((CropActivity) activity).setCurrentFragment(this);
  }

  @Override
  public void onDetach() {
    super.onDetach();
    if (mCropImageView != null) {
      mCropImageView.setOnSetImageUriCompleteListener(null);
      mCropImageView.setOnCropImageCompleteListener(null);
    }
  }

  @Override
  public void onSetImageUriComplete(CropImageView view, Uri uri, Exception error) {
    /*if (error == null) {
      To/ast.make/Text(getActivity(), "Image load successful", Toast.LENGTH_SHORT).show();
    } else {
      Log.e("AIC", "Failed to load image by URI", error);
      Toa/st.make/Text(getActivity(), "Image load failed: " + error.getMessage(), Toast.LENGTH_LONG)
          .show();
    }*/
  }

  @Override
  public void onCropImageComplete(CropImageView view, CropImageView.CropResult result) {
    handleCropResult(result);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
      CropImage.ActivityResult result = CropImage.getActivityResult(data);
      handleCropResult(result);
    }
  }

  private void handleCropResult(CropImageView.CropResult result) {
    if (result.getError() == null) {
      /*Intent intent = new Intent(getActivity(), CropResultActivity.class);
      intent.putExtra("SAMPLE_SIZE", result.getSampleSize());
      if (result.getUri() != null) {
        intent.putExtra("URI", result.getUri());
      } else {
        CropResultActivity.mImage =
            mCropImageView.getCropShape() == CropImageView.CropShape.OVAL
                ? CropImage.toOvalBitmap(result.getBitmap())
                : result.getBitmap();
      }
      startActivity(intent);*/
//      String path = result.getBitmap().;
//      ImageLoader.init().guardarImagen(path,rutaImg);
      try {
        result.getBitmap().compress(Bitmap.CompressFormat.JPEG,100,new FileOutputStream(rutaImg));
      }catch (FileNotFoundException e){
        e.printStackTrace();
      }

      getActivity().finish();
    } else {
      Log.e("AIC", "Failed to crop image", result.getError());
      Utils.ShowToastAnimated(getActivity(),"Ocurrió un error al recortar la imagen",R.raw.error);
    }
  }
}
