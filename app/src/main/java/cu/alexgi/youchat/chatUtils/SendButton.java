package cu.alexgi.youchat.chatUtils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;


public class SendButton extends ImageButton
    implements TransportOptions.OnTransportChangedListener,
               TransportOptionsPopup.SelectedListener,
               View.OnLongClickListener
{

  private final TransportOptions transportOptions;

  private Optional<TransportOptionsPopup> transportOptionsPopup = Optional.absent();

  @SuppressWarnings("unused")
  public SendButton(Context context) {
    super(context);
    this.transportOptions = initializeTransportOptions();
    ViewUtil.mirrorIfRtl(this, getContext());
  }

  @SuppressWarnings("unused")
  public SendButton(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.transportOptions = initializeTransportOptions();
    ViewUtil.mirrorIfRtl(this, getContext());
  }

  @SuppressWarnings("unused")
  public SendButton(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    this.transportOptions = initializeTransportOptions();
    ViewUtil.mirrorIfRtl(this, getContext());
  }

  private TransportOptions initializeTransportOptions() {
    TransportOptions transportOptions = new TransportOptions(getContext());
    transportOptions.addOnTransportChangedListener(this);

    setOnLongClickListener(this);

    return transportOptions;
  }

  private TransportOptionsPopup getTransportOptionsPopup() {
    if (!transportOptionsPopup.isPresent()) {
      transportOptionsPopup = Optional.of(new TransportOptionsPopup(getContext(), this, this));
    }
    return transportOptionsPopup.get();
  }

  public boolean isManualSelection() {
    return transportOptions.isManualSelection();
  }

  public void addOnTransportChangedListener(TransportOptions.OnTransportChangedListener listener) {
    transportOptions.addOnTransportChangedListener(listener);
  }

  public TransportOption getSelectedTransport() {
    return transportOptions.getSelectedTransport();
  }

  public void resetAvailableTransports() {
    transportOptions.reset();
  }

  public void disableTransport(TransportOption.Type type) {
    transportOptions.disableTransport(type);
  }

  public void setDefaultTransport(TransportOption.Type type) {
    transportOptions.setDefaultTransport(type);
  }

  @Override
  public void onSelected(TransportOption option) {
    transportOptions.setSelectedTransport(option);
    getTransportOptionsPopup().dismiss();
  }

  @Override
  public void onChange(TransportOption newTransport, boolean isManualSelection) {
    setImageResource(newTransport.getDrawable());
    setContentDescription(newTransport.getDescription());
  }

  @Override
  public boolean onLongClick(View v) {
    if (transportOptions.getEnabledTransports().size() > 1) {
      getTransportOptionsPopup().display(transportOptions.getEnabledTransports());
      return true;
    }

    return false;
  }
}