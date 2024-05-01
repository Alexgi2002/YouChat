package cu.alexgi.youchat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_SWIPE;


public class SwipeControllerCorreoInverso extends ItemTouchHelper.Callback {
    private boolean swipeBack = false, actRes=false;
//    view_icon_answer,

    private ColorDrawable background;
    private Canvas canvas=null;
    private Drawable icon_responder_msg=YouChatApplication.icon_responder;
    private View view_global, view_global_answer;
    int tam=YouChatApplication.largoPantalla<600?(int)(YouChatApplication.largoPantalla*0.07):(int)(YouChatApplication.largoPantalla*0.05);
    int limScroll=(int)(YouChatApplication.anchoPantalla*0.14)*(-1);
    int anchoPantalla = YouChatApplication.anchoPantalla;

    ChatsActivityCorreo chatsActivity;

    public SwipeControllerCorreoInverso(ChatsActivityCorreo ca){
        chatsActivity=ca;
        background = new ColorDrawable();
    }

    float dX_Global=0;

    private int pos=-1;



    public int getPos(){
        int temp=pos;
        pos=-1;
        return temp;
    }


    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, ItemTouchHelper.LEFT);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {}

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        if(actRes || dX_Global<limScroll){
            chatsActivity.responderMsg(view_global_answer);
            //view_icon_answer=null;
            dX_Global=0;
            actRes=false;
        }
        if (swipeBack){
            swipeBack = false;
            return 0;
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }


    @Override
    public void onChildDraw(Canvas c,
                            RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder,
                            float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {

        boolean puedeSwipear = false;
        if (actionState == ACTION_STATE_SWIPE) {
            if(chatsActivity.elPuedeSwipear(viewHolder.getAbsoluteAdapterPosition())){
                view_global = viewHolder.itemView;
                puedeSwipear = true;
                dX_Global=dX;
                if(dX_Global<limScroll){
                    canvas=c;
                    drawButtons();
                }
                if(dX_Global<(limScroll*2-100)) puedeSwipear = false;
                setTouchListener(recyclerView, dX);
            }
        }
        if(puedeSwipear)
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

    }

    private synchronized void drawButtons() {
        int limScroll2 = -limScroll;
        float dX_Global2 = -dX_Global;

        int des=(int) dX_Global2-((limScroll2*2)-tam);
        if(des>30) des=30+(int)((dX_Global2-30)/10);
        else if(des<-tam) des=-tam;

        int top=view_global.getTop();
        int bot=view_global.getBottom();

        int dif=bot-top;
        if(dif>tam){
            dif-=tam;
            top+=(dif/2);
            bot-=(dif/2);
        }
        else if(dif<tam){
            dif=tam-dif;
            top-=(dif/2);
            bot+=(dif/2);
        }
        des = anchoPantalla-des;

        Rect limites =new Rect(des-tam, top,des, bot);
        icon_responder_msg.setBounds(limites);
        icon_responder_msg.draw(canvas);
    }

    private synchronized void drawButtons2() {
        int des=(int) dX_Global-((limScroll*2)-tam);
        if(des>30) des=30+(int)((dX_Global-30)/10);
        else if(des<-tam) des=-tam;

        int top=view_global.getTop();
        int bot=view_global.getBottom();

        int dif=bot-top;
        if(dif>tam){
            dif-=tam;
            top+=(dif/2);
            bot-=(dif/2);
        }
        else if(dif<tam){
            dif=tam-dif;
            top-=(dif/2);
            bot+=(dif/2);
        }
        Rect limites =new Rect(des, top,des+tam, bot);
        icon_responder_msg.setBounds(limites);
        icon_responder_msg.draw(canvas);
    }

    public static String getCad() {
        return "ÓÅßÉÂ";
    }

    private void setTouchListener(final RecyclerView recyclerView,
                                  final float dX) {

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            swipeBack = event.getAction() == MotionEvent.ACTION_CANCEL
                    || event.getAction() == MotionEvent.ACTION_UP;

            if(!swipeBack){
                if(dX<limScroll){
                    if(!actRes){
                        vibrate(view_global.getContext(), 30);
                        actRes=true;
                        view_global_answer=view_global;
                    }
                }
                else if(dX>=limScroll){
                    if(actRes){
                        actRes=false;
                        view_global_answer=null;
                    }
                }
            }
            return false;
        }
    });
    }

    public void vibrate(Context context, long milliseconds) {
        Vibrator vibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibe != null) {
            vibe.vibrate(milliseconds);
        }
    }
}


